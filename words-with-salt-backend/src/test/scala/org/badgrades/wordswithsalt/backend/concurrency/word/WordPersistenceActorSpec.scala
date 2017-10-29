package org.badgrades.wordswithsalt.backend.concurrency.word

import org.badgrades.wordswithsalt.backend.ActorTestSuite
import org.badgrades.wordswithsalt.backend.concurrency.word.WordPersistenceActor.{RetrieveWordById, WordEntityResponse}
import org.badgrades.wordswithsalt.backend.persistence.{SaltyWordEntity, SaltyWordRepository}
import org.mockito.Mockito
import org.mockito.Mockito._
import org.scalatest.FunSpecLike

class WordPersistenceActorSpec extends ActorTestSuite with FunSpecLike {

  describe("A WordPersistenceActor with a mocked repository") {
    val actorRef = system.actorOf(WordPersistenceActor.props())
    val mockedRepository = Mockito.mock(classOf[SaltyWordRepository])

    when(applicationContext.getBean(classOf[SaltyWordRepository])).thenReturn(mockedRepository)

    it ("Responds with WordEntityResponse when repository contains word with given id") {
      val testId = 1L
      val saltyEntity = SaltyWordEntity("SomePhrase", "SomeDescription")
      when(mockedRepository.findById(testId)).thenReturn(saltyEntity)
      actorRef ! RetrieveWordById(testId)
      expectMsg(WordEntityResponse(saltyEntity))
    }
  }
}
