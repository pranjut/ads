package com.pranjut.db.repositories

import com.pranjut.db.actions.{ AdAction, AdImpressionAction, AdImpressionClicksAction }
import com.pranjut.db.slick.SlickSpec

object InMemObjs extends SlickSpec {
  val adAction = new AdAction()
  val impressionAction = new AdImpressionAction()
  val impressionClicksAction = new AdImpressionClicksAction()
}