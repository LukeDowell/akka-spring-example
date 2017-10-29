package org.badgrades.wordswithsalt.backend

import akka.actor.ActorSystem
import org.scalatest.{BeforeAndAfterAll, FunSpec}

import scala.concurrent.ExecutionContext

trait ActorTestSuite extends FunSpec with BeforeAndAfterAll {
  implicit val testSystem: ActorSystem = ActorSystem("test-system")
  implicit val ec: ExecutionContext = testSystem.dispatcher

  override protected def afterAll {
    testSystem.terminate()
    super.afterAll()
  }
}
