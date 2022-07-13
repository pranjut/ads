package com.pranjut.endpoints

import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.pranjut.endpoints.JsonSupport
import com.pranjut.modules.{ BaseModule, CoreModules }
import org.mockito.MockitoSugar
import org.mockito.MockitoSugar._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

trait BaseRoutes extends BaseModule {
  override val coreModule: CoreModules = mock[CoreModules]
}

trait BaseEndpointsSpec extends AnyWordSpec with Matchers with ScalatestRouteTest with MockitoSugar with JsonSupport