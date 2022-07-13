package com.pranjut.endpoints

import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import com.pranjut.db.models.{ Ad, MessageResponse }
import com.pranjut.db.repositories.InMemObjs.convertScalaFuture
import com.pranjut.services.AdService

import scala.concurrent.Future

class AdsRoutesSpec extends BaseEndpointsSpec {

  import spray.json._

  val adRoutes = new AdsRoutes with BaseRoutes
  val adService = mock[AdService]
  when(adRoutes.coreModule.adService).thenReturn(adService)

  "AdsRoutes" should {
    "successfully insert ad" in {
      val ad = Ad(0, "Chocolate Ad", 11)

      when(adService.insert(ad)).thenReturn(Future.successful(ad.copy(id = 1)))

      val adEntity = Marshal(ad).to[MessageEntity].futureValue
      val request = Post("/insert/ad").withEntity(adEntity)

      request ~> adRoutes.adRoutes ~> check {
        status shouldEqual StatusCodes.OK

        contentType should ===(ContentTypes.`application/json`)

        val res: String = responseAs[String]
        val parsedjson: JsValue = res.parseJson
        val jsonRes = parsedjson.convertTo[MessageResponse]
        jsonRes.message shouldBe "Successfully Inserted"
      }
    }

    "successfully get ad" in {
      val ad = Ad(1, "Chocolate Ad", 11)

      when(adService.get(1)).thenReturn(Future.successful(Some(ad)))

      val adEntity = Marshal(ad).to[MessageEntity].futureValue
      val request = Get("/get/ad/1")

      request ~> adRoutes.adRoutes ~> check {
        status shouldEqual StatusCodes.OK
        contentType should ===(ContentTypes.`application/json`)

        val res: String = responseAs[String]
        val parsedjson: JsValue = res.parseJson
        val jsonRes = parsedjson.convertTo[Ad]
        jsonRes shouldBe ad
      }
    }

    "Delete ad" in {
      val ad = Ad(1, "Chocolate Ad", 11)

      when(adService.delete(1)).thenReturn(Future.successful(1))

      val adEntity = Marshal(ad).to[MessageEntity].futureValue
      val request = Delete("/delete/ad/1")

      request ~> adRoutes.adRoutes ~> check {
        status shouldEqual StatusCodes.OK
        contentType should ===(ContentTypes.`application/json`)

        val res: String = responseAs[String]
        val parsedjson: JsValue = res.parseJson
        val jsonRes = parsedjson.convertTo[MessageResponse]
        jsonRes.message shouldBe "Successfully deleted"
      }
    }

  }
}

