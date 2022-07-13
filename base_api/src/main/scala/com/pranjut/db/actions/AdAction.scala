package com.pranjut.db.actions

import com.pranjut.db.slick.SlickAction
import com.pranjut.db.models.Ad
import com.pranjut.db.slick.{ DBComponent, SlickAction }
import slick.jdbc.JdbcProfile
import slick.sql.SqlProfile.ColumnOption.SqlType

import scala.concurrent.Future

class AdAction(implicit db: DBComponent) extends SlickAction[Ad, Long] {
  override val jdbcProfile: JdbcProfile = db.driver

  import jdbcProfile.api._

  override type EntityTable = AdsTable

  override def primaryColumn(table: EntityTable): Rep[Long] = table.id

  override def tableQuery: TableQuery[AdsTable] =
    TableQuery[AdsTable]

  class AdsTable(tag: Tag) extends Table[Ad](tag, "ads") {
    def id = column[Long]("id", SqlType("BIGSERIAL"), O.PrimaryKey, O.AutoInc)

    def text = column[String]("text")

    def product_id = column[Long]("product_id")

    override def * = (id, text, product_id) <> (Ad.tupled, Ad.unapply)
  }

  def createSchema: Future[Unit] = db.db.run(tableQuery.schema.create)

  def deleteSchema: Future[Unit] = db.db.run(tableQuery.schema.dropIfExists)

}
