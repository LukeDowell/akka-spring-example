package org.badgrades.wordswithsalt.backend.persistence

import javax.persistence._

import org.badgrades.wordswithsalt.api.domain.SaltyWord

@Entity
@Table(name = "SaltyWords")
case class SaltyWordEntity() {

  @Id
  @GeneratedValue
  var id: Long = _

  var phrase: String = _

  var description: String = _

  def toDomain() = {
    val domain = SaltyWord(
      id,
      phrase,
      description
    )
  }
}
