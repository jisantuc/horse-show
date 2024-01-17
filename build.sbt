import scala.sys.process._
import scala.language.postfixOps

import sbtwelcome._

Global / semanticdbEnabled := true

Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.5.0"

ThisBuild / organization := "io.github.jisantuc"
ThisBuild / scalaVersion := "3.3.1"
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
  val circeVersion     = "0.14.1"
  val declineVersion   = "2.4.1"
  val fs2Version       = "3.6.1"
  val fs2AwsVersion    = "6.1.1"
  val http4sVersion    = "0.23.23"
  val ip4sVersion      = "3.4.0"
  val munitVersion     = "0.7.29"
}

lazy val root =
  (project in file("."))
    .aggregate(etl, horseshow, modelJS, modelJVM)
    .dependsOn(etl)

lazy val etl =
  (project in file("./etl"))
    .dependsOn(model.jvm)
    .settings(
      libraryDependencies ++= Seq(
        "co.fs2"        %% "fs2-core"       % Versions.fs2Version,
        "co.fs2"        %% "fs2-io"         % Versions.fs2Version,
        "com.monovore"  %% "decline"        % Versions.declineVersion,
        "com.monovore"  %% "decline-effect" % Versions.declineVersion,
        "org.scalameta" %% "munit"          % "0.7.29" % Test
      ),
      testFrameworks += new TestFramework("munit.Framework"),
      scalafixOnCompile := false,
      semanticdbEnabled := true,
      semanticdbVersion := scalafixSemanticdb.revision,
      autoAPIMappings   := true
    )

lazy val model =
  crossProject(JVMPlatform, JSPlatform)
    .crossType(CrossType.Pure)
    .settings(
      name := "model",
      libraryDependencies ++= Seq(
        "org.typelevel" %%% "cats-parse" % Versions.catsParseVersion,
        "io.circe"      %%% "circe-core" % Versions.circeVersion,
        "org.scalameta" %%% "munit"      % Versions.munitVersion % Test
      ),
      testFrameworks += new TestFramework("munit.Framework")
    )

val modelJS  = model.js
val modelJVM = model.jvm

lazy val horseshow =
  (project in file("./application"))
    .enablePlugins(ScalaJSPlugin)
    .dependsOn(modelJS)
    .settings( // Normal settings
      name := "horseshow",
      libraryDependencies ++= Seq(
        "dev.optics"      %%% "monocle-core"        % "3.2.0",
        "io.indigoengine" %%% "tyrian-io"           % "0.6.1",
        "co.fs2"          %%% "fs2-core"            % Versions.fs2Version,
        "co.fs2"          %%% "fs2-io"              % Versions.fs2Version,
        "com.comcast"     %%% "ip4s-core"           % Versions.ip4sVersion,
        "io.circe"        %%% "circe-parser"           % Versions.circeVersion,
        "org.scalameta"   %%% "munit"               % "0.7.29" % Test
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
