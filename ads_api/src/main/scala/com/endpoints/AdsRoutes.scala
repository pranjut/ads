package com.endpoints

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import com.db.models.{Ad, MessageResponse}
import com.modules.ServiceModule

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

trait AdsRoutes extends JsonSupport{

  import akka.http.scaladsl.server.Directives._

  val serviceModule: ServiceModule

  def adRoutes(implicit ex: ExecutionContext): Route = {
      pathPrefix("insert" / "ad") {
          pathEnd {
            post {
              entity(as[Ad]) { ad: Ad =>

                onComplete(serviceModule.adService.insert(ad)) {
                  _ match {
                    case Success(value) => complete(StatusCodes.OK, MessageResponse("Successfully Inserted"))
                    case Failure(exception) =>
                      exception.printStackTrace()
                      complete(StatusCodes.InternalServerError,MessageResponse("There was some internal problem"))
                  }
                }
              }
            }
          }
      } ~ pathPrefix("delete" / "ad" / LongNumber ) {id: Long =>
          pathEnd {
            delete {
              {
                onComplete(serviceModule.adService.delete(id)) {
                  _ match {
                    case Success(value) => complete(StatusCodes.OK, MessageResponse("Successfully deleted"))
                    case Failure(exception) =>
                      exception.printStackTrace()
                      complete(StatusCodes.InternalServerError,MessageResponse("There was some internal problem"))
                  }
                }
              }
            }
          }
      } ~ pathPrefix("get" / "ad" / LongNumber ) {id: Long =>
          pathEnd {
            get {
              {
                onComplete(serviceModule.adService.get(id)) {
                  _ match {
                    case Success(Some(value)) => complete(StatusCodes.OK, value)
                    case Success(None) => complete(StatusCodes.NotFound, MessageResponse("No Ad could be found"))
                    case Failure(exception) =>
                      exception.printStackTrace()
                      complete(StatusCodes.InternalServerError,MessageResponse("There was some internal problem"))
                  }
                }
              }
            }
          }
      }
    }

}
