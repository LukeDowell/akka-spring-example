package org.badgrades.wordswithsalt.backend.concurrency.weather

import akka.actor.{Actor, ActorLogging}
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class WeatherScrapingActor extends Actor with ActorLogging {
  override def preStart(): Unit = log.info(s"${self.path} Starting up")
  override def receive = ???
}

object WeatherScrapingActor {
  val restTemplate = new RestTemplate()
}