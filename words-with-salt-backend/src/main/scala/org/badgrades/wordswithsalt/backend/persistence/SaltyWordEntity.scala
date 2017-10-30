package org.badgrades.wordswithsalt.backend.persistence

import javax.persistence._

import org.badgrades.wordswithsalt.api.domain.SaltyWord

@Entity
@Table(name = "SaltyWords")
case class SaltyWordEntity(
                            @Id
                            @GeneratedValue
                            var id: Long = 0,
                            var phrase: String = "",
                            var description: String = ""
                          ) {
  def toDomain = SaltyWord(
      id,
      phrase,
      description
    )
}
