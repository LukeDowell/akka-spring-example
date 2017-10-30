package org.badgrades.wordswithsalt.backend

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import org.badgrades.wordswithsalt.backend.concurrency.SpringContextExtension
import org.mockito.Mockito
import org.scalatest.{BeforeAndAfterAll, TestSuite}
import org.springframework.context.ConfigurableApplicationContext

abstract class ActorTestSuite extends TestKit(ActorSystem("test-system"))
  with TestSuite
  with ImplicitSender
  with BeforeAndAfterAll {

  val applicationContext: ConfigurableApplicationContext = Mockito.mock(classOf[ConfigurableApplicationContext])
  system.registerExtension(SpringContextExtension).initialize(applicationContext)

  override def afterAll {
    system.terminate()
    super.afterAll()
  }
}
