import Dependencies.{akka_http_deps, akka_stream_deps, mockito_sugar, scala_test, slick_deps, typesafeConfig}

ThisBuild / scalaVersion := "2.13.8"

lazy val ads_api = (project in file("ads_api")).
  settings(libraryDependencies ++= akka_http_deps ++ slick_deps ++ Seq(typesafeConfig, scala_test, mockito_sugar) ++ akka_stream_deps)


lazy val root = (project in file(".")).aggregate(ads_api)

Test / parallelExecution := false