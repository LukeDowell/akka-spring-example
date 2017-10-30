package org.badgrades.wordswithsalt.backend.config

import akka.actor.ActorSystem
import org.badgrades.wordswithsalt.backend.concurrency.SpringContextExtension
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.{Bean, Configuration}

/**
  * https://github.com/scalaspring/akka-spring-boot/blob/master/src/main/scala/com/github/scalaspring/akka/AkkaAutoConfiguration.scala
  */
@Configuration
class AkkaConfig() {

  @Bean(destroyMethod = "terminate")
  def actorSystem(context: ConfigurableApplicationContext): ActorSystem = {
    val system = ActorSystem("words-with-salt-system")
    system.registerExtension(SpringContextExtension).initialize(context)
    system
  }

}
