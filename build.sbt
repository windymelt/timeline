val ScalatraVersion = "2.7.0"

val json4sVersion = "3.6.7"

organization := "windymelt"

name := "Timeline"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.13.4"

resolvers += Classpaths.typesafeReleases

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
  "org.scalatra" %% "scalatra-json" % ScalatraVersion,
  "org.json4s" %% "json4s-jackson" % json4sVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "9.4.28.v20200408" % "container;compile",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  "com.github.nscala-time" %% "nscala-time" % "2.24.0",
  // DB
  "mysql" % "mysql-connector-java" % "8.0.22",
  "org.scalikejdbc" %% "scalikejdbc" % "3.5.0",
  "com.h2database" % "h2" % "1.4.200", // for test purpose
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.scalikejdbc" %% "scalikejdbc-test" % "3.5.0" % "test",
  "org.scalikejdbc" %% "scalikejdbc-config" % "3.5.0" // to read application.conf
)

lazy val root =
  (project in file(".")).enablePlugins(SbtTwirl).enablePlugins(ScalatraPlugin)

TwirlKeys.templateImports += "javax.servlet.http.HttpServletRequest" // to import HttpServletRequest
