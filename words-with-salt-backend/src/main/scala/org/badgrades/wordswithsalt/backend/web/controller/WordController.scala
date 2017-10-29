package org.badgrades.wordswithsalt.backend.web.controller

import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, PoisonPill}
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.scalalogging.StrictLogging
import org.badgrades.wordswithsalt.api.domain.SaltyWord
import org.badgrades.wordswithsalt.api.web.template.WordTemplate
import org.badgrades.wordswithsalt.backend.concurrency.word.WordPersistenceActor
import org.badgrades.wordswithsalt.backend.concurrency.word.WordPersistenceActor._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

import scala.concurrent.Await

@RestController
class WordController @Autowired()(
                                   actorSystem: ActorSystem
                                 ) extends WordTemplate with StrictLogging {

  implicit val timeout: Timeout = Timeout(1, TimeUnit.SECONDS)

  override def getWordById(id: Long) = {
    val wordActorRef = actorSystem.actorOf(WordPersistenceActor.props())
    val wordFuture = wordActorRef ? RetrieveWordById(id)
    wordActorRef ! PoisonPill

    Await.result(wordFuture, timeout.duration) match {
      case WordEntityResponse(wordEntity) => ResponseEntity.ok(wordEntity.toDomain)
      case _ => ResponseEntity.notFound().build()
    }
  }

  override def postWord(saltyWord: SaltyWord) = {
    val wordActorRef = actorSystem.actorOf(WordPersistenceActor.props())
    val wordFuture = wordActorRef ? WriteWord(saltyWord)
    wordActorRef ! PoisonPill

    Await.result(wordFuture, timeout.duration) match {
      case WordEntityResponse(wordEntity) => ResponseEntity.ok(wordEntity.toDomain)
      case _ => ResponseEntity.notFound().build()
    }
  }

  override def getAllWords() = {
    val wordActorRef = actorSystem.actorOf(WordPersistenceActor.props())
    val wordFuture = wordActorRef ? RetrieveAllWords()
    wordActorRef ! PoisonPill

    Await.result(wordFuture, timeout.duration) match {
      case AllWordsResponse(words) => ResponseEntity.ok(words.map(word => word.toDomain))
      case _ => ResponseEntity.notFound().build()
    }
  }
}
