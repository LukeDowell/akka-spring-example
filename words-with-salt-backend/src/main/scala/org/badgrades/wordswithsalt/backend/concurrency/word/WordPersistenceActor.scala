package org.badgrades.wordswithsalt.backend.concurrency.word

import scala.collection.JavaConverters._
import akka.actor.{Actor, ActorLogging, Props}
import org.badgrades.wordswithsalt.api.domain.SaltyWord
import org.badgrades.wordswithsalt.backend.concurrency.word.WordPersistenceActor.
{WriteWord, WordEntityResponse, RetrieveAllWords, RetrieveWordById, RetrieveWordByPhrase}
import org.badgrades.wordswithsalt.backend.persistence.{SaltyWordEntity, SaltyWordRepository}
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class WordPersistenceActor(saltyWordRepository: SaltyWordRepository) extends Actor with ActorLogging {

  override def preStart(): Unit = log.info(s"${self.path} Starting up")
  override def postStop(): Unit = log.info(s"${self.path} Stopping...")

  override def receive = {
    case WriteWord(word) => {
      val saltyEntity = SaltyWordEntity(word.phrase, word.description)
      sender() ! WordEntityResponse(saltyWordRepository.save(saltyEntity))
    }

    case RetrieveAllWords => {
      val allWords: Seq[SaltyWordEntity] = saltyWordRepository.findAll().asScala
    }

    case RetrieveWordById(id) => sender() ! WordEntityResponse(saltyWordRepository.findById(id))

    case RetrieveWordByPhrase(phrase) => sender() ! WordEntityResponse(saltyWordRepository.findByPhrase(phrase))

    case _ => log.warning("Unknown message retrieved")
  }
}

object WordPersistenceActor {
  def props(wordRepo: SaltyWordRepository): Props = Props(new WordPersistenceActor(wordRepo))

  case class WriteWord(saltyWord: SaltyWord)

  case class RetrieveAllWords()
  case class RetrieveWordByPhrase(phrase: String)
  case class RetrieveWordById(id: Long)

  case class AllWordsResponse(saltyWordEntity: Seq[SaltyWordEntity])
  case class WordEntityResponse(saltyWordEntity: SaltyWordEntity)

  case class WordNotFound()
}
