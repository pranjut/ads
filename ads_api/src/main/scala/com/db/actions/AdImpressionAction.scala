package com.db.actions

import com.db.models.AdImpression
import com.db.slick.{DBComponent, SlickAction}
import slick.jdbc.JdbcProfile
import slick.sql.SqlProfile.ColumnOption.SqlType

import java.sql.Timestamp
import scala.concurrent.Future

class AdImpressionAction(implicit db: DBComponent) extends SlickAction[AdImpression, Long] {
  override val jdbcProfile: JdbcProfile = db.driver

  import jdbcProfile.api._

  override type EntityTable = AdImpressionTable

  override def primaryColumn(table: EntityTable): Rep[Long] = table.id

  override def tableQuery: TableQuery[AdImpressionTable] =
    TableQuery[AdImpressionTable]

  class AdImpressionTable(tag: Tag) extends Table[AdImpression](tag, "ad_impression") {
    def id = column[Long]("id", SqlType("BIGSERIAL"), O.PrimaryKey, O.AutoInc)

    def customer_id = column[String]("customer_id")

    def ad_id = column[Long]("ad_id")

    def impression_time = column[Timestamp]("impression_time")

    override def * = (id, customer_id, ad_id, impression_time) <> (AdImpression.tupled, AdImpression.unapply)
  }

  def createSchema: Future[Unit] = db.db.run(tableQuery.schema.create)

  def deleteSchema: Future[Unit] = db.db.run(tableQuery.schema.dropIfExists)

}
