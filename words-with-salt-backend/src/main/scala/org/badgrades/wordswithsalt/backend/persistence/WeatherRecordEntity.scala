package org.badgrades.wordswithsalt.backend.persistence

import javax.persistence.{Entity, GeneratedValue, Id, Table}

import org.badgrades.wordswithsalt.api.domain.WeatherRecord

@Entity
@Table(name = "WeatherRecords")
class WeatherRecordEntity {

  @Id
  @GeneratedValue
  var id: Long = _

}
