package com.modules

import com.db.actions.{AdAction, AdImpressionAction, AdImpressionClicksAction}
import com.db.repositories.{AdImpressionClickRepo, AdImpressionRepo, AdsRepo}
import com.db.slick.DBComponent
import com.services.AdService

trait DBModules {

  implicit val dbComponent: DBComponent
  //The execution context can be different here with but to save time I am using the default one
  import scala.concurrent.ExecutionContext.Implicits.global

  lazy val adAction = new AdAction()
  lazy val impressionAction = new AdImpressionAction()
  lazy val impressionClicksAction = new AdImpressionClicksAction()

  lazy val adRepo = new AdsRepo(adAction)
  lazy val impressionRepo = new AdImpressionRepo(impressionAction)
  lazy val impressionClicksRepo = new AdImpressionClickRepo(impressionClicksAction)

}

trait ServiceModule{
  implicit val dbModules: DBModules
  lazy val adService = new AdService()
}
