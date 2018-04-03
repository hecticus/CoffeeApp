
name := "CoffeeApp"

version := "1.0"

lazy val `root` = (project in file(".")).enablePlugins(
  PlayJava,
  PlayEbean)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  guice,
  jdbc,
  ehcache,
  ws,
  javaJdbc,
  "org.avaje" % "ebean" % "2.7.3",
  "javax.persistence" % "persistence-api" % "1.0.2",
  //specs2 % Test,
  "mysql" % "mysql-connector-java" % "5.1.38",
  "org.modelmapper" % "modelmapper" % "0.7.7",
  "com.typesafe.play" % "play-mailer_2.11" % "5.0.0",
  "io.jsonwebtoken" % "jjwt" % "0.7.0",
  "org.apache.jclouds" % "jclouds-all" % "2.0.1",
  "com.fasterxml.jackson.dataformat" % "jackson-dataformat-xml" % "2.9.4",
  filters
)


resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
