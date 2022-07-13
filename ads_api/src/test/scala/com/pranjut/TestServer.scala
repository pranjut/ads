package com.pranjut

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.pranjut.endpoints.AdsRoutes
import com.typesafe.config.ConfigFactory

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.{ Failure, Success }

object TestServer extends App with AdsRoutes {

  val port = ConfigFactory.load().getInt("ads.port")

  //The execution context can be different here with but to save time I am using the default one
  //  import scala.concurrent.ExecutionContext.Implicits.global
  implicit val system: ActorSystem = ActorSystem("WWOAkkaHttpServer")
  implicit val executionContext: ExecutionContext = system.dispatcher

  val allRoutes = adRoutes

  val serverBinding: Future[Http.ServerBinding] =
    Http()
      .newServerAt("0.0.0.0", port)
      .bind(allRoutes)

  serverBinding
    .onComplete {
      case Success(bound) =>
        ServiceModuleTest.createdb
        println(s"Server online at http://${bound.localAddress.getHostString}:${bound.localAddress.getPort}/")
      case Failure(e) =>
        Console.err.println(s"Server could not start!")
        e.printStackTrace()
        system.terminate()
    }
  override val coreModule = ServiceModuleTest.coreModule
}
