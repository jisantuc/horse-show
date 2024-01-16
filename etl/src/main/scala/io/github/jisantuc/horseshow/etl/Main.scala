package io.github.jisantuc.horseshow.etl

import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.Resource
import cats.syntax.either._
import cats.syntax.show._
import com.monovore.decline.Opts
import com.monovore.decline.effect.CommandIOApp
import fs2.Stream
import fs2.io.file.Files
import fs2.io.file.Path
import fs2.io.readInputStream
import fs2.io.stdout
import fs2.text
import fs2.text.decodeWithCharset
import fs2.text.lines
import fs2.text.utf8
import io.circe.syntax._
import io.github.jisantuc.horseshow.model.ResultLine

import java.net.URL
import java.nio.charset.StandardCharsets

object Main
    extends CommandIOApp(
      "horse-show-etl",
      "Transform Derby City Classic results CSV to ndjson",
      true
    ) {
  val resultsCsvUrl = new URL(
    "http://results.derbycityclassic.com/LiveMatchesandResults/DCCCompletedMatches.csv"
  )
  def csvLinesStream(outPath: String) = readInputStream[IO](
    IO.delay(resultsCsvUrl.openConnection.getInputStream),
    4096,
    closeAfterUse = true
  )
    .through(text.decodeWithCharset(StandardCharsets.UTF_8))
    .through(lines)
    .drop(10)
    .filter(s => !s.isEmpty())
    .flatMap(line =>
      Stream.eval {
        IO.fromEither(
          ResultLine.parser
            .parseAll(line)
            .leftMap(err => new Exception(s"Line: ${line};\nErr:\n${err.show}"))
        )
      }
    )
    .map(_.asJson.noSpaces)
    .intersperse("\n")
    .through(utf8.encode)
    .through(Files[IO].writeAll(Path(outPath)))

  override def main: Opts[IO[ExitCode]] =
    Opts.argument[String](metavar = "output-file-name").map { outFile =>
      csvLinesStream(outFile).compile.drain.as(ExitCode.Success)
    }
}
