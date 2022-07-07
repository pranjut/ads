package com.db.repositories

import com.db.actions.{AdAction, AdImpressionAction, AdImpressionClicksAction}
import com.db.slick.SlickSpec

object InMemObjs extends SlickSpec {
  val adAction = new AdAction()
  val impressionAction = new AdImpressionAction()
  val impressionClicksAction = new AdImpressionClicksAction()
}