
name := "CoffeeApp"

version := "1.0"

lazy val `root` = (project in file(".")).enablePlugins(
  PlayJava,
  PlayEbean)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  javaWs,
  specs2 % Test,
  guice,
  openId,
  "com.typesafe.play" %% "play-json" % "2.6.0",
  "com.typesafe.play" %% "play-iteratees" % "2.6.1",
  "com.typesafe.play" %% "play-iteratees-reactive-streams" % "2.6.1",
  "mysql" % "mysql-connector-java" % "5.1.38",
  "org.modelmapper" % "modelmapper" % "0.7.7",
  "com.typesafe.play" % "play-mailer_2.11" % "5.0.0",
  "io.jsonwebtoken" % "jjwt" % "0.7.0",
  filters
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

routesImport += "models.manager.requestUtils.requestObject.DateParameter"
