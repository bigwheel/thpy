name := "thpy"
organization := "com.github.bigwheel"
version in Global := "1.0"
scalaVersion in Global := "2.12.1"
scalacOptions in Global ++= Seq("-unchecked", "-deprecation", "-feature")

lazy val macroSub = (project in file("macro")).settings(
  scalacOptions += "-language:experimental.macros",
  libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value
)

lazy val main = (project in file(".")).dependsOn(macroSub).settings(
  libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value
)
