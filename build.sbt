import scala.sys.process._
import scala.language.postfixOps

import sbtwelcome._

Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.5.0"

ThisBuild / organization := "io.github.jisantuc"
ThisBuild / scalaVersion := "3.2.1"
ThisBuild / version      := "0.0.1"

usefulTasks := Seq(
  UsefulTask("", "fastOptJS", "Rebuild the JS (use during development)"),
  UsefulTask(
    "",
    "fullOptJS",
    "Rebuild the JS and optimise (use in production)"
  ),
  UsefulTask("", "code", "Launch VSCode")
)
logo             := "🐎 Show (v" + version.value + ")"
logoColor        := scala.Console.MAGENTA
aliasColor       := scala.Console.BLUE
commandColor     := scala.Console.CYAN
descriptionColor := scala.Console.WHITE

val Versions = new {
  val catsParseVersion = "0.3.9"
  val declineVersion   = "2.4.1"
  val circeFs2Version  = "0.14.1"
  val fs2Version       = "3.6.1"
}

lazy val root =
  (project in file("."))
    .aggregate(etl, horseshow)

lazy val etl =
  (project in file("./etl"))
    .enablePlugins(ScalaJSPlugin)
    .settings(
      libraryDependencies ++= Seq(
        "co.fs2"        %%% "fs2-core"  % Versions.fs2Version,
        "io.circe"      %%% "circe-fs2" % Versions.circeFs2Version,
        "org.scalameta" %%% "munit"     % "0.7.29" % Test
      ),
      testFrameworks += new TestFramework("munit.Framework"),
      scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) },
      scalafixOnCompile := true,
      semanticdbEnabled := true,
      semanticdbVersion := scalafixSemanticdb.revision,
      autoAPIMappings   := true
    )

lazy val horseshow =
  (project in file("./application"))
    .enablePlugins(ScalaJSPlugin)
    .settings( // Normal settings
      name := "horseshow",
      libraryDependencies ++= Seq(
        "dev.optics"      %%% "monocle-core" % "3.2.0",
        "io.indigoengine" %%% "tyrian-io"    % "0.6.1",
        "co.fs2"          %%% "fs2-core"     % Versions.fs2Version,
        "io.circe"        %%% "circe-fs2"    % Versions.circeFs2Version,
        "org.scalameta"   %%% "munit"        % "0.7.29" % Test
      ),
      testFrameworks += new TestFramework("munit.Framework"),
      scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) },
      scalafixOnCompile := true,
      semanticdbEnabled := true,
      semanticdbVersion := scalafixSemanticdb.revision,
      autoAPIMappings   := true
    )
    .settings( // Welcome message
    )
