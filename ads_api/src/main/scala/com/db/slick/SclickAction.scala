package com.db.slick

trait SclickAction {

}

import slick.jdbc.JdbcProfile
import slick.relational.RelationalProfile

import scala.concurrent.Future

trait SlickAction[Entity, Id] {

  val jdbcProfile: JdbcProfile

  import jdbcProfile.api._

  type EntityTable <: RelationalProfile#Table[Entity]

  def tableQuery: TableQuery[EntityTable]

  def primaryColumn(table: EntityTable): Rep[Id]

  def createSchema: Future[Unit]
  def deleteSchema: Future[Unit]

}