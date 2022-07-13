import sbt._

object Dependencies {

  lazy val akkaHttpVersion  = "10.2.9"
  lazy val akkaVersion      = "2.6.19"

  val actor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akka_http = "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
  val akka_http_test = "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test
  val akka_http_spray_json = "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion

  val akka_http_deps = Seq(akka_http, akka_http_test, akka_http_spray_json, actor)

  val akka_stream = "com.typesafe.akka" %% "akka-stream" % akkaVersion
  val akka_stream_test = "com.typesafe.akka" %% "akka-stream-testkit"  % akkaVersion  % Test

  val akka_stream_deps = Seq(akka_stream, akka_stream_test)


  val typesafeConfig = "com.typesafe" % "config" % "1.4.2"


  val slick = "com.typesafe.slick" %% "slick" % "3.3.3"
  val slickCodeGen = "com.typesafe.slick" %% "slick-codegen" % "3.3.3"
  val hikariCP = "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3"
  val postgresql = "org.postgresql" % "postgresql" % "42.2.19"
  val h2 = "com.h2database" % "h2" % "1.4.199" % Test
  val caffine = "com.github.cb372" %% "scalacache-caffeine" % "0.28.0"

  val scala_test = "org.scalatest" %% "scalatest" % "3.2.9" % Test
  val mockito_sugar = "org.mockito" %% "mockito-scala-cats" % "1.16.46" % Test

  val slick_deps = Seq(slick, slickCodeGen, postgresql, h2, hikariCP, caffine)

  val logback = "ch.qos.logback" % "logback-classic" % "1.2.11" % Test
  val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5"


  val common_deps = Seq(logback, scalaLogging)

}
