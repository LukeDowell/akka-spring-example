package org.badgrades.wordswithsalt.backend.config

import akka.actor.ActorSystem
import org.badgrades.wordswithsalt.backend.concurrency.SpringExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.{Bean, Configuration}

/**
  * https://github.com/scalaspring/akka-spring-boot/blob/master/src/main/scala/com/github/scalaspring/akka/AkkaAutoConfiguration.scala
  * @param context The spring application context
  */
@Configuration
class AkkaConfig @Autowired()(context: ConfigurableApplicationContext) {

  @Bean(destroyMethod = "terminate")
  def actorSystem: ActorSystem = {
    val system = ActorSystem("words-with-salt-system")
    system.registerExtension(SpringExtension).initialize(context)
    system
  }

}
