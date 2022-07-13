package com.pranjut.db.slick.postgres

import com.pranjut.db.slick.DBComponent
import com.pranjut.db.slick.DBComponent
import com.typesafe.config.ConfigFactory
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

object PostgresDBComponent {

  private lazy val config = ConfigFactory.load()
  lazy val databaseConfig: DatabaseConfig[JdbcProfile] =
    DatabaseConfig.forConfig[JdbcProfile]("postgres.config", config)

  implicit lazy val postgresComponent = new DBComponent {
    override val driver: JdbcProfile = databaseConfig.profile
    import driver.api._
    override val db: Database = databaseConfig.db
  }

}
