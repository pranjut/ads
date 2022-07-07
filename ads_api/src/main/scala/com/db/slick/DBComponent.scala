package com.db.slick

import slick.jdbc.JdbcProfile

trait DBComponent {
  val driver: JdbcProfile
  import driver.api._
  val db: Database
}

trait DBComponentProfile {
  val dBComponent: DBComponent
}
