name := "thpy"
organization := "com.github.bigwheel"
// append "-SNAPSHOT" to avoid publishLocal warning
// http://stackoverflow.com/a/26089552
version in Global := "0.1.0-SNAPSHOT"
scalaVersion in Global := "2.12.1"
scalacOptions in Global ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies in Global ++= Seq(
  "org.scala-lang" % "scala-compiler" % scalaVersion.value,
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.scalactic" %% "scalactic" % "3.0.1"
)

// refer following url for macro project structure
// http://www.scala-sbt.org/0.13/docs/Macro-Projects.html#Distribution

lazy val macroSub = (project in file("macro")).settings(
  scalacOptions ++= Seq("-language:experimental.macros", "-language:implicitConversions"),
  libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  publish := {},
  publishLocal := {}
)

lazy val core = (project in file(".")).dependsOn(macroSub).settings(
  libraryDependencies += "com.lihaoyi" %% "sourcecode" % "0.1.3",
  mappings in (Compile, packageBin) ++= mappings.in(macroSub, Compile, packageBin).value,
  mappings in (Compile, packageSrc) ++= mappings.in(macroSub, Compile, packageSrc).value
)
