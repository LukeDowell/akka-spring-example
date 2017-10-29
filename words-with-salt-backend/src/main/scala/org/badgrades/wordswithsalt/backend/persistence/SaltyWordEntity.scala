package org.badgrades.wordswithsalt.backend.persistence

import javax.persistence._

import org.badgrades.wordswithsalt.api.domain.SaltyWord

@Entity
@Table(name = "SaltyWords")
case class SaltyWordEntity(
                          var phrase: String,
                          var description: String
                          ) {

  def this() = this("", "")

  @Id
  @GeneratedValue
  var id: Long = _

  def toDomain() = SaltyWord(id, phrase, description)
}
