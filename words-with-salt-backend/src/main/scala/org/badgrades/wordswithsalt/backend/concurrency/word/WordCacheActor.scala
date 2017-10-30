package org.badgrades.wordswithsalt.backend.concurrency.word

import akka.actor.{Actor, ActorLogging, Props}

class WordCacheActor extends Actor with ActorLogging {


  override def receive = {
    case _ => log.warning("Unknown message received")
  }
}

object WordCacheActor {
  def props: Props = Props(new WordCacheActor())
}
