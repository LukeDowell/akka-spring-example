package org.badgrades.wordswithsalt.backend.persistence

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
trait SaltyWordRepository extends CrudRepository[SaltyWordEntity, java.lang.Long] {

  def findByPhrase(phrase: String): SaltyWordEntity
  def findById(id: Long): SaltyWordEntity
}
