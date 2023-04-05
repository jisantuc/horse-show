package io.github.jisantuc.horseshow.etl

import cats.effect.ExitCode
import cats.effect.IO
import com.monovore.decline.Opts
import com.monovore.decline.effect.CommandIOApp

object Main
    extends CommandIOApp(
      "horse-show-etl",
      "Transform Derby City Classic results CSV to ndjson"
    ) {
  // add kantan.csv
  // take file input url as input
  // rewrite to ndjson
  // (gonna need a new module for the model)
  override def main: Opts[IO[ExitCode]] = ???
}
