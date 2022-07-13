package com.pranjut.modules

import com.pranjut.db.actions.{ AdAction, AdImpressionAction, AdImpressionClicksAction }
import com.pranjut.db.repositories.{ AdImpressionClickRepo, AdImpressionRepo, AdsRepo }
import com.pranjut.db.slick.DBComponent
import com.pranjut.services.AdService

trait CoreModules {

  implicit val dbComponent: DBComponent
  //The execution context can be different here with but to save time I am using the default one
  import scala.concurrent.ExecutionContext.Implicits.global

  lazy val adAction = new AdAction()
  lazy val impressionAction = new AdImpressionAction()
  lazy val impressionClicksAction = new AdImpressionClicksAction()

  lazy val adRepo = new AdsRepo(adAction)
  lazy val impressionRepo = new AdImpressionRepo(impressionAction)
  lazy val impressionClicksRepo = new AdImpressionClickRepo(impressionClicksAction)

  lazy val adService = new AdService(this)
}

object CoreModulesWithPostgres extends CoreModules {
  import com.pranjut.db.slick.postgres.PostgresDBComponent._
  override implicit val dbComponent: DBComponent = postgresComponent
}