import Dependencies._
import sbt._
import sbt.Keys._

val commonSettings = Seq(
  organization := "io.rxgithub",
  version := "0.0.1",
  scalacOptions := Seq("-unchecked", "-deprecation"),
  scalaVersion  := "2.10.3",
  resolvers ++= Seq (
    "Typesafe repo" at "http://repo.typesafe.com/typesafe/releases/",
    "Spray repo" at "http://repo.spray.io/"
  )
)

lazy val all = Project("all-rxgithub", file(".")).
  settings(commonSettings:_*).
  aggregate(client, core)

def project(name: String) = Project(
  id = name,
  base = file(name)).
  settings(commonSettings:_*)


lazy val core = project("core").
  settings(libraryDependencies ++= coreDependencies)

lazy val client = project("client").
  settings(libraryDependencies ++= clientDependencies).
  dependsOn(core)
