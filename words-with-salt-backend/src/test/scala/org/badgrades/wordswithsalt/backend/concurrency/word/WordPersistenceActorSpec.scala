package org.badgrades.wordswithsalt.backend.concurrency.word

import org.badgrades.wordswithsalt.api.domain.SaltyWord
import org.badgrades.wordswithsalt.backend.ActorTestSuite
import org.badgrades.wordswithsalt.backend.concurrency.word.WordPersistenceActor.{RetrieveWordById, WordEntityResponse, WriteWord}
import org.badgrades.wordswithsalt.backend.persistence.{SaltyWordEntity, SaltyWordRepository}
import org.mockito.Mockito
import org.mockito.Mockito._
import org.mockito.Matchers._
import org.mockito.AdditionalAnswers.returnsFirstArg
import org.scalatest.FunSpecLike

class WordPersistenceActorSpec extends ActorTestSuite with FunSpecLike {

  describe("A WordPersistenceActor with a mocked repository") {
    val mockedRepository = Mockito.mock(classOf[SaltyWordRepository])
    when(applicationContext.getBean(classOf[SaltyWordRepository])).thenReturn(mockedRepository)

    // Must be created AFTER dependencies are mocked
    val actorRef = system.actorOf(WordPersistenceActor.props())

    it ("Responds with WordEntityResponse when repository contains word with given id") {
      val testId = 1L
      val saltyEntity = SaltyWordEntity()
      saltyEntity.id = 1L
      saltyEntity.description = "SomeDescription"
      saltyEntity.phrase = "SomePhrase"

      when(mockedRepository.findById(testId)).thenReturn(saltyEntity)
      actorRef ! RetrieveWordById(testId)
      expectMsgType[WordEntityResponse]
    }

    it ("Saves an entity with the phrase and description provided to the receive method") {
      val someDesc = "SomeDescription"
      val somePhrase = "SomePhrase"
      val saltyWord = SaltyWord(phrase = somePhrase, description = someDesc)

      when(mockedRepository.save(any[SaltyWordEntity])).thenAnswer(returnsFirstArg())
      actorRef ! WriteWord(saltyWord)
      expectMsgPF() {
        case WordEntityResponse(SaltyWordEntity(_, `somePhrase`, `someDesc`)) => ()
        case _ => fail()
      }
    }
  }
}
