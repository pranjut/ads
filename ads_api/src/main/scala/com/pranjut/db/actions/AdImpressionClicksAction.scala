package com.pranjut.db.actions

import com.pranjut.db.models.AdImpressionClicks
import com.pranjut.db.slick.{ DBComponent, SlickAction }
import com.pranjut.db.models.{ AdImpression, AdImpressionClicks }
import com.pranjut.db.slick.{ DBComponent, SlickAction }
import com.pranjut.db.models.AdImpressionClicks
import com.pranjut.db.slick.DBComponent
import slick.jdbc.JdbcProfile
import slick.sql.SqlProfile.ColumnOption.SqlType

import java.sql.Timestamp
import scala.concurrent.Future

class AdImpressionClicksAction(implicit db: DBComponent) extends SlickAction[AdImpressionClicks, Long] {
  override val jdbcProfile: JdbcProfile = db.driver

  import jdbcProfile.api._

  override type EntityTable = AdImpressionClicksTable

  override def primaryColumn(table: EntityTable): Rep[Long] = table.id

  override def tableQuery: TableQuery[AdImpressionClicksTable] =
    TableQuery[AdImpressionClicksTable]

  class AdImpressionClicksTable(tag: Tag) extends Table[AdImpressionClicks](tag, "ad_impression_clicks") {
    def id = column[Long]("id", SqlType("BIGSERIAL"), O.PrimaryKey, O.AutoInc)

    def impression_id = column[Long]("impression_id")

    def click_time = column[Timestamp]("click_time")

    override def * = (id, impression_id, click_time) <> (AdImpressionClicks.tupled, AdImpressionClicks.unapply)
  }

  def createSchema: Future[Unit] = db.db.run(tableQuery.schema.create)

  def deleteSchema: Future[Unit] = db.db.run(tableQuery.schema.dropIfExists)

}
