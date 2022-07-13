package com.pranjut.modules

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContext

object AkkaModule {
  implicit val system: ActorSystem = ActorSystem("Adds")
  implicit val executionContext: ExecutionContext = system.dispatcher

}
