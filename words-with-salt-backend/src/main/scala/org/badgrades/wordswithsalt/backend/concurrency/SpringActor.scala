package org.badgrades.wordswithsalt.backend.concurrency

import akka.actor.Actor
import org.springframework.context.ConfigurableApplicationContext

trait SpringActor extends Actor {
  def springContext(): ConfigurableApplicationContext = SpringExtension(context.system).applicationContext()
}
