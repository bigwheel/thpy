name := "thpy"
organization := "com.github.bigwheel.thpy"
version := "1.0"

scalaVersion in ThisBuild := "2.12.1"

lazy val macroSub = project in file("macro")

lazy val main = (project in file(".")).dependsOn(macroSub)
