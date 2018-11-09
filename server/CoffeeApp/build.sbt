
name := "CoffeeApp"

version := "1.0"

lazy val myProject = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  guice,
  evolutions,
  jdbc,
  ehcache,
  ws,
  javaJdbc,
  "com.typesafe.play" %% "play-json" % "2.6.0",
  "com.typesafe.play" %% "anorm" % "2.5.1",
  "org.avaje" % "ebean" % "2.7.3",
  "javax.persistence" % "persistence-api" % "1.0.2",
  "mysql" % "mysql-connector-java" % "5.1.38",
  "com.typesafe.play" % "play-mailer_2.11" % "5.0.0",
  "io.jsonwebtoken" % "jjwt" % "0.7.0",
  "org.apache.jclouds" % "jclouds-all" % "2.1.0",
  "org.apache.tika" % "tika-core" % "1.16",

  "com.fasterxml.jackson.dataformat" % "jackson-dataformat-xml" % "2.9.6",
  "com.fasterxml.jackson.datatype" % "jackson-datatype-jdk8" % "2.9.6",
  filters
)


resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

