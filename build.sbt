import Dependencies.{akka_http_deps, akka_stream_deps, common_deps, mockito_sugar, scala_test, slick_deps, typesafeConfig}

ThisBuild / scalaVersion := "2.13.8"

lazy val base_api = (project in file("base_api")).
  settings(libraryDependencies ++= akka_http_deps ++ slick_deps ++ Seq(typesafeConfig, scala_test, mockito_sugar) ++ akka_stream_deps ++ common_deps)
  .settings(Test / parallelExecution := false)


lazy val ads = (project in file(".")).aggregate(base_api)

Test / parallelExecution := false