package com.db.repositories

import com.db.actions.AdAction
import com.db.models.Ad
import com.db.slick.{DBComponent, SlickRepository}

import scala.concurrent.{ExecutionContext, Future}

class AdsRepo(adAction: AdAction)(implicit ex: ExecutionContext, dbComp: DBComponent) extends SlickRepository {
  import adAction._
  import jdbcProfile.api._

  override val dBComponent: DBComponent = dbComp

  def get(id: Long): Future[Option[Ad]] = {
    db.run(tableQuery.filter(_.id === id).result).map(_.headOption)
  }

  def insert(ad: Ad): Future[Ad] = {
    val query = tableQuery.returning(tableQuery.map(primaryColumn)) += ad
    db.run(query).map(insertedId => ad.copy(id = insertedId))
  }

  def delete(id: Long): Future[Int] = {
    val query = tableQuery.filter(_.id === id).delete
    db.run(query)
  }
}
