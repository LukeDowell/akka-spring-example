package org.badgrades.wordswithsalt.backend.concurrency

import akka.actor.Actor
import org.springframework.context.ConfigurableApplicationContext

trait SpringContextAwareActor extends Actor {
  def springContext(): ConfigurableApplicationContext = SpringContextExtension(context.system).applicationContext()
  def getBean[T](clazz: Class[T]): T = springContext().getBean(clazz)
  def getBeanByName[T](name: String): T = springContext().getBean(name).asInstanceOf[T]
}
