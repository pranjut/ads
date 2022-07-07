package com.db.slick

trait SlickRepository {

  val dBComponent: DBComponent

  def db: dBComponent.driver.api.Database = dBComponent.db

}
