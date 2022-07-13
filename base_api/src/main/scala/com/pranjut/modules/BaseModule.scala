package com.pranjut.modules

import com.typesafe.scalalogging.LazyLogging

trait BaseModule extends LazyLogging {

  val coreModule: CoreModules

}
