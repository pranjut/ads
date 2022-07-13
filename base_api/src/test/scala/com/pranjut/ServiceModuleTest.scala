package com.pranjut

import com.pranjut.db.slick.DBComponent
import com.pranjut.modules.CoreModules
import com.typesafe.config.ConfigFactory
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

import java.io.File
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

object ServiceModuleTest {

  def createdb = {
    Await.result(coreModule.adAction.createSchema, 1.minute)
    Await.result(coreModule.impressionAction.createSchema, 1.minute)
    Await.result(coreModule.impressionClicksAction.createSchema, 1.minute)

  }

  val coreModule = new CoreModules {
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

    val databaseConfig: DatabaseConfig[JdbcProfile] = DatabaseConfig.forConfig[JdbcProfile]("h2mem", config)

    override implicit val dbComponent = new DBComponent {
      override val driver: JdbcProfile = databaseConfig.profile
      override val db: driver.api.Database = databaseConfig.db
    }
  }
}
