package com.pranjut

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.pranjut.endpoints.AdsRoutes
import com.pranjut.modules.{ CoreModules, CoreModulesWithPostgres }
import com.typesafe.config.ConfigFactory

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.{ Failure, Success }

object Server extends App with AdsRoutes {

  val port = ConfigFactory.load().getInt("ads.port")

  //The execution context can be different here with but to save time I am using the default one
  //  import scala.concurrent.ExecutionContext.Implicits.global
  implicit val system: ActorSystem = ActorSystem("AdsHttpServer")
  implicit val executionContext: ExecutionContext = system.dispatcher

  val allRoutes = adRoutes

  val serverBinding: Future[Http.ServerBinding] =
    Http()
      .newServerAt("0.0.0.0", port)
      .bind(allRoutes)

  serverBinding
    .onComplete {
      case Success(bound) =>
        logger.info(s"Server online at http://${bound.localAddress.getHostString}:${bound.localAddress.getPort}/")
      case Failure(e) =>
        logger.error("Server could not be started", e)
        system.terminate()
    }

  override val coreModule: CoreModules = CoreModulesWithPostgres

}
