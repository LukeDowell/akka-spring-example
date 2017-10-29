package org.badgrades.wordswithsalt.api.domain

case class SaltyWord(
                    id: Long,
                    phrase: String,
                    description: String
                    ) {

  def this() { this(0, "", "") }
}
