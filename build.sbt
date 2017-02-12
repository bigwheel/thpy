name := "thpy"
organization := "com.github.bigwheel"
version in Global := "1.0.0"
scalaVersion in Global := "2.12.1"
scalacOptions in Global ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies in Global ++= Seq(
  "org.scala-lang" % "scala-compiler" % scalaVersion.value,
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.scalactic" %% "scalactic" % "3.0.1"
)

lazy val macroSub = (project in file("macro")).settings(
  scalacOptions ++= Seq("-language:experimental.macros", "-language:implicitConversions"),
  libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
)

lazy val main = (project in file(".")).dependsOn(macroSub).settings(
  libraryDependencies += "com.lihaoyi" %% "sourcecode" % "0.1.3"
)
