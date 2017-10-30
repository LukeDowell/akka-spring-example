package org.badgrades.wordswithsalt.backend.concurrency.word

import akka.actor.{Actor, ActorLogging, Props}
import org.badgrades.wordswithsalt.api.domain.SaltyWord

class WordCacheActor extends Actor with ActorLogging {

  private var idToWord = Map.empty[Long, SaltyWord]
  private var phraseToWord = Map.empty[String, SaltyWord]

  override def receive = {
    case _ => log.warning("Unknown message received")
  }

  private def cacheWord(saltyWord: SaltyWord): Unit = {

  }
}

object WordCacheActor {
  def props: Props = Props(new WordCacheActor())
}
