
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
  "be.objectify" %% "deadbolt-java" % "2.6.1",
  "org.pac4j" %% "play-pac4j" % "6.0.0-RC1",
  "org.pac4j" % "pac4j-oauth" % "2.3.1",
  "org.pac4j" % "pac4j-http" % "2.3.1",
  "org.avaje" % "ebean" % "2.7.3",
  "javax.persistence" % "persistence-api" % "1.0.2",
  "mysql" % "mysql-connector-java" % "5.1.38",
  "org.modelmapper" % "modelmapper" % "0.7.7",
  "com.typesafe.play" % "play-mailer_2.11" % "5.0.0",
  "io.jsonwebtoken" % "jjwt" % "0.7.0",
  "org.apache.jclouds" % "jclouds-all" % "2.0.1",
  "com.fasterxml.jackson.dataformat" % "jackson-dataformat-xml" % "2.9.4",
  filters
)


resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

