package org.badgrades.wordswithsalt.backend.concurrency.word

import akka.actor.{ActorLogging, Props}
import org.badgrades.wordswithsalt.api.domain.SaltyWord
import org.badgrades.wordswithsalt.backend.concurrency.SpringActor
import org.badgrades.wordswithsalt.backend.concurrency.word.WordPersistenceActor.{RetrieveWordById, RetrieveWordByPhrase, WordEntityResponse, WriteWord}
import org.badgrades.wordswithsalt.backend.persistence.{SaltyWordEntity, SaltyWordRepository}

class WordPersistenceActor() extends SpringActor with ActorLogging {
  val saltyWordRepository: SaltyWordRepository = springContext().getBean(classOf[SaltyWordRepository])

  override def preStart(): Unit = log.info(s"${self.path} Starting up")
  override def postStop(): Unit = log.info(s"${self.path} Stopping...")

  override def receive = {
    case WriteWord(word) =>
      val saltyEntity = SaltyWordEntity()
      saltyEntity.phrase = word.phrase
      saltyEntity.description = word.description
      log.info(s"Writing word $saltyEntity")
      sender() ! WordEntityResponse(saltyWordRepository.save(saltyEntity))

    case RetrieveWordById(id) => sender() ! WordEntityResponse(saltyWordRepository.findById(id))

    case RetrieveWordByPhrase(phrase) => sender() ! WordEntityResponse(saltyWordRepository.findByPhrase(phrase))

    case _ => log.warning("Unknown message retrieved")
  }
}

object WordPersistenceActor {
  def props(): Props = Props(new WordPersistenceActor())

  case class WriteWord(saltyWord: SaltyWord)

  case class RetrieveAllWords()
  case class RetrieveWordByPhrase(phrase: String)
  case class RetrieveWordById(id: Long)

  case class AllWordsResponse(saltyWordEntity: Seq[SaltyWordEntity])
  case class WordEntityResponse(saltyWordEntity: SaltyWordEntity)

  case class WordNotFound()
}
