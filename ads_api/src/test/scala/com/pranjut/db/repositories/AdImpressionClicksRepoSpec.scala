package com.pranjut.db.repositories

import com.pranjut.db.models.{ Ad, AdImpression, AdImpressionClicks }
import com.pranjut.db.slick.SlickSpec
import org.scalatest.BeforeAndAfterEach
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.SpanSugar.convertIntToGrainOfTime

import java.sql.Timestamp
import scala.concurrent.Await

class AdImpressionClicksRepoSpec extends SlickSpec
  with ScalaFutures
  with BeforeAndAfterEach {

  override def beforeEach(): Unit = {
    Await.result(InMemObjs.adAction.createSchema, 1.minute)
    Await.result(InMemObjs.impressionAction.createSchema, 1.minute)
    Await.result(InMemObjs.impressionClicksAction.createSchema, 1.minute)

  }
  override def afterEach(): Unit = {
    Await.result(InMemObjs.adAction.deleteSchema, 1.minute)
    Await.result(InMemObjs.impressionAction.deleteSchema, 1.minute)
    Await.result(InMemObjs.impressionClicksAction.deleteSchema, 1.minute)
  }

  val adRepo = new AdsRepo(InMemObjs.adAction)
  val impressionRepo = new AdImpressionRepo(InMemObjs.impressionAction)
  val impressionClicksRepo = new AdImpressionClickRepo(InMemObjs.impressionClicksAction)

  it should "be able to insert and get" in {
    val ad = Ad(0, "Buy Apple Laptop", 1)
    val impression = AdImpression(0, "customer_id", 0, new Timestamp(System.currentTimeMillis()))
    val impressionClick = AdImpressionClicks(0, 0, new Timestamp(System.currentTimeMillis()))

    val allRes = for {
      insertedAd <- adRepo.insert(ad)
      insertedImpression <- impressionRepo.insert(impression.copy(ad_id = insertedAd.id))
      insertedImpressionClicks <- impressionClicksRepo.insert(impressionClick.copy(impressionId = insertedImpression.id))
      result <- impressionClicksRepo.get(insertedImpressionClicks.id)
    } yield result
    allRes.map {
      case res =>
        res.head shouldEqual impressionClick.copy(id = 1, impressionId = 1)
    }
  }

}
