package org.badgrades.wordswithsalt.backend.concurrency

import akka.actor.{AbstractExtensionId, ActorSystem, ExtendedActorSystem, Extension, ExtensionId, ExtensionIdProvider}
import akka.io.UdpExt
import org.springframework.context.{ApplicationContext, ConfigurableApplicationContext}

/**
  * Example Project:
  * https://github.com/typesafehub/activator-akka-java-spring/blob/master/src/main/java/sample/SpringExtension.java
  *
  * Tutorial:
  * http://www.baeldung.com/akka-with-spring
  *
  * Akka Documentation:
  * https://doc.akka.io/api/akka/current/akka/actor/Extension.html
  */
object SpringExtension extends ExtensionId[SpringExtension] with ExtensionIdProvider {

  override def createExtension(system: ExtendedActorSystem) = new SpringExtension
  override def lookup: SpringExtension.type = SpringExtension
}

class SpringExtension extends Extension {
  private var context: ConfigurableApplicationContext = _

  def applicationContext(): ConfigurableApplicationContext = context

  def initialize(applicationContext: ConfigurableApplicationContext): Unit = {
    require(applicationContext != null, "ApplicationContext cannot be null")
    require(context == null, "ApplicationContext has already been set")

    context = applicationContext
  }
}