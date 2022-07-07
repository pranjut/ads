package com.db.repositories

import com.db.actions.AdImpressionAction
import com.db.models.AdImpression
import com.db.slick.{DBComponent, SlickRepository}

import scala.concurrent.{ExecutionContext, Future}

class AdImpressionRepo(adImpressionAction: AdImpressionAction)(implicit ex: ExecutionContext, dbComp: DBComponent) extends SlickRepository {
  import adImpressionAction._
  import jdbcProfile.api._

  override val dBComponent: DBComponent = dbComp

  def get(id: Long): Future[Option[AdImpression]] = {
    db.run(tableQuery.filter(_.id === id).result).map(_.headOption)
  }

  def insert(ad: AdImpression): Future[AdImpression] = {
    val query = tableQuery.returning(tableQuery.map(primaryColumn)) += ad
    db.run(query).map(insertedId => ad.copy(id = insertedId))
  }

  def delete(id: Long): Future[Int] = {
    val query = tableQuery.filter(_.id === id).delete
    db.run(query)
  }
}
