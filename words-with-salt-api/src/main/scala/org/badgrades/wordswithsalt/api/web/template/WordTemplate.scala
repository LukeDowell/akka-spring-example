package org.badgrades.wordswithsalt.api.web.template

import org.badgrades.wordswithsalt.api.domain.SaltyWord
import org.springframework.http.{MediaType, ResponseEntity}
import org.springframework.web.bind.annotation.{PathVariable, RequestMapping, RequestMethod}

object WordTemplate {
  // Params
  final val ID_PARAM = "id"

  // Paths - Can't do string templates due to scala / annotation params interaction
  final val ROOT_PATH = "/word"
  final val GET_WORD_BY_ID_PATH = "/{" + ID_PARAM + "}"
}

@RequestMapping(Array(WordTemplate.ROOT_PATH))
trait WordTemplate {

  /**
    *
    * @param id
    * @return
    */
  @RequestMapping(
    method = Array(RequestMethod.GET),
    value = Array(WordTemplate.GET_WORD_BY_ID_PATH),
    produces = Array(MediaType.APPLICATION_JSON_VALUE)
  )
  def getWordById(@PathVariable(WordTemplate.ID_PARAM) id: Long): ResponseEntity[SaltyWord]

  /**
    *
    * @param saltyWord
    * @return
    */
  @RequestMapping(
    method = Array(RequestMethod.POST),
    produces = Array(MediaType.APPLICATION_JSON_VALUE),
    consumes = Array(MediaType.APPLICATION_JSON_VALUE)
  )
  def postWord(saltyWord: SaltyWord): ResponseEntity[SaltyWord]

  /**
    *
    * @return
    */
  @RequestMapping(
    method = Array(RequestMethod.GET),
    produces = Array(MediaType.APPLICATION_JSON_VALUE)
  )
  def getAllWords(): ResponseEntity[Seq[SaltyWord]]
}

