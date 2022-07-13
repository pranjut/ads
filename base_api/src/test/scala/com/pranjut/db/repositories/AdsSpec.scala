package com.pranjut.db.repositories

import com.pranjut.db.models.Ad
import com.pranjut.db.repositories.AdsRepo
import com.pranjut.db.slick.SlickSpec
import org.scalatest.BeforeAndAfterEach
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.SpanSugar.convertIntToGrainOfTime

import scala.concurrent.Await

class AdsSpec extends SlickSpec
  with ScalaFutures
  with BeforeAndAfterEach {

  override def beforeEach(): Unit = {
    Await.result(InMemObjs.adAction.createSchema, 1.minute)

  }
  override def afterEach(): Unit = {
    Await.result(InMemObjs.adAction.deleteSchema, 1.minute)
  }

  val adRepo = new AdsRepo(InMemObjs.adAction)

  it should "be able to insert and get" in {
    val ad = Ad(0, "Buy Apple Laptop", 1)
    val allRes = for {
      ad <- adRepo.insert(ad)
      result <- adRepo.get(ad.id)
    } yield (ad, result)
    allRes.map {
      case (q, res) =>
        res.head shouldEqual ad.copy(id = 1)
    }
  }

  it should "be able to delete" in {
    val ad = Ad(0, "Buy Apple Laptop", 1)
    val allRes = for {
      insertedAd <- adRepo.insert(ad)
      ad <- adRepo.insert(ad)
      _ <- adRepo.delete(ad.id)
      result <- adRepo.get(ad.id)
    } yield result
    allRes.map {
      case res =>
        res shouldEqual None
    }
  }

}
