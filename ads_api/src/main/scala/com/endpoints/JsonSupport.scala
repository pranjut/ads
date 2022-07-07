package com.endpoints

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.db.models.{Ad, MessageResponse}
import spray.json.{DefaultJsonProtocol, JsNumber, JsValue, JsonFormat}

import java.sql.Timestamp
import java.time.Instant

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val timestampFormat: JsonFormat[Timestamp] = new JsonFormat[Timestamp] {
    override def write(obj: Timestamp): JsValue = JsNumber(obj.getTime)

    override def read(json: JsValue): Timestamp = json match {
      case JsNumber(x) => Timestamp.from(Instant.ofEpochMilli(x.toLong))
      case _ =>
        throw new IllegalArgumentException(
          s"Can not parse json value [$json] to a timestamp object")
    }
  }

  implicit val adJsonFormat = jsonFormat3(Ad)
  implicit val responseJsonFormat = jsonFormat1(MessageResponse)

}
