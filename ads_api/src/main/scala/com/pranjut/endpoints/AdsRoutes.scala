package com.pranjut.endpoints

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import com.pranjut.db.models.{Ad, MessageResponse}
import com.pranjut.modules.BaseModule

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

trait AdsRoutes extends JsonSupport with BaseModule {

  import akka.http.scaladsl.server.Directives._

  def adRoutes(implicit ex: ExecutionContext): Route = {
    pathPrefix("insert" / "ad") {
        post {
          entity(as[Ad]) { ad: Ad =>
            onComplete(coreModule.adService.insert(ad)) {
              _ match {
                case Success(value) =>
                  logger.info(s"Successfully inserted new ad with id ${value.id}")
                  complete(StatusCodes.OK, MessageResponse("Successfully Inserted"))
                case Failure(exception) =>
                  logger.error("Exception while ad insertion", exception)
                  complete(StatusCodes.InternalServerError, MessageResponse("There was some internal problem"))
              }
            }
          }
        }
    } ~ pathPrefix("delete" / "ad" / LongNumber) { id: Long =>
        delete {
          {
            onComplete(coreModule.adService.delete(id)) {
              _ match {
                case Success(value) => complete(StatusCodes.OK, MessageResponse("Successfully deleted"))
                case Failure(exception) =>
                  logger.error("Exception while ad deletion", exception)
                  complete(StatusCodes.InternalServerError, MessageResponse("There was some internal problem"))
              }
            }
          }
      }
    } ~ pathPrefix("get" / "ad" / LongNumber) { id: Long =>
        get {
          {
            onComplete(coreModule.adService.get(id)) {
              _ match {
                case Success(Some(value)) => complete(StatusCodes.OK, value)
                case Success(None) => complete(StatusCodes.NotFound, MessageResponse("No Ad could be found"))
                case Failure(exception) =>
                  logger.error("Exception while ad retrieval", exception)
                  complete(StatusCodes.InternalServerError, MessageResponse("There was some internal problem"))
              }
            }
          }
        }
    }
  }

}
