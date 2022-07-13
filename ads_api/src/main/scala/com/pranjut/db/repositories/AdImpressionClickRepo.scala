package com.pranjut.db.repositories

import com.pranjut.db.actions.AdImpressionClicksAction
import com.pranjut.db.models.AdImpressionClicks
import com.pranjut.db.slick.{ DBComponent, SlickRepository }
import com.pranjut.db.actions.AdImpressionClicksAction
import com.pranjut.db.models.AdImpressionClicks
import com.pranjut.db.slick.{ DBComponent, SlickRepository }
import com.pranjut.db.actions.AdImpressionClicksAction
import com.pranjut.db.models.AdImpressionClicks
import com.pranjut.db.slick.{ DBComponent, SlickRepository }

import scala.concurrent.{ ExecutionContext, Future }

class AdImpressionClickRepo(adImpressionClicksAction: AdImpressionClicksAction)(implicit ex: ExecutionContext, dbComp: DBComponent) extends SlickRepository {
  import adImpressionClicksAction._
  import jdbcProfile.api._

  override val dBComponent: DBComponent = dbComp

  def get(id: Long): Future[Option[AdImpressionClicks]] = {
    db.run(tableQuery.filter(_.id === id).result).map(_.headOption)
  }

  def insert(ad: AdImpressionClicks): Future[AdImpressionClicks] = {
    val query = tableQuery.returning(tableQuery.map(primaryColumn)) += ad
    db.run(query).map(insertedId => ad.copy(id = insertedId))
  }

  def delete(id: Long): Future[Int] = {
    val query = tableQuery.filter(_.id === id).delete
    db.run(query)
  }
}
