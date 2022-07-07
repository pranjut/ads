package com

import com.db.slick.DBComponent
import com.modules.{DBModules, ServiceModule}
import com.typesafe.config.ConfigFactory
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

import java.io.File
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

object ServiceModuleTest {

  def createdb = {
    Await.result(serviceModule.dbModules.adAction.createSchema, 1.minute)
    Await.result(serviceModule.dbModules.impressionAction.createSchema, 1.minute)
    Await.result(serviceModule.dbModules.impressionClicksAction.createSchema, 1.minute)

  }

  val serviceModule = new ServiceModule {
    private val defaultConfigString =
      """
  h2mem {
  profile = "slick.jdbc.H2Profile$"
  db {
    url = "jdbc:h2:mem:contacting;DB_CLOSE_DELAY=-1"
    driver = "org.h2.Driver"
    connectionPool = "HikariCP"
  }
  }
  """

    private val config = ConfigFactory
      .parseFile(new File("src/test/resources/application.conf"))
      .withFallback(ConfigFactory.parseString(defaultConfigString))

    val databaseConfig = DatabaseConfig.forConfig[JdbcProfile]("h2mem", config)

    implicit val h2DBComponent = new DBComponent {
      override val driver: JdbcProfile = databaseConfig.profile
      override val db: driver.api.Database = databaseConfig.db
    }
    override implicit val dbModules: DBModules = new DBModules {
      override implicit val dbComponent: DBComponent = h2DBComponent
    }
  }
}
