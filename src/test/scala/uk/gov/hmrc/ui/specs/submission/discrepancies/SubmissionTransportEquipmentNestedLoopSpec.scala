/*
 * Copyright 2026 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ui.specs
package submission.discrepancies

import uk.gov.hmrc.ui.pages.submission.*
import uk.gov.hmrc.ui.pages.submission.goods.*
import uk.gov.hmrc.ui.pages.submission.transport.*
import uk.gov.hmrc.ui.util.TestDataGenerators

class SubmissionTransportEquipmentNestedLoopSpec extends BaseSpec with DiscrepancyJourney {

  private val container1 = "CONT1234567890121"
  private val container2 = "CONT1234567890122"

  Feature("IE507(a) Submission with discrepancies - seals and goods references under each transport equipment") {

    Scenario("Add several seals and goods references to a transport equipment and return to the list") {
      startJourneyUpToContainerDetails(TestDataGenerators.generateMrn())

      addContainerDetails("1")

      When("I add two seals")
      addSeal("1", "SEAL001")
      answerAddAnother(AddAnotherSealPage, "Yes")
      addSeal("2", "SEAL002")

      Then("the seal list shows 2 seals")
      AddAnotherSealPage.checkForContent("You have added 2 seals")
      answerAddAnother(AddAnotherSealPage, "No")

      When("I add two goods references")
      addGoodsReference("1", "123")
      answerAddAnother(AddAnotherGoodsReferencePage, "Yes")
      addGoodsReference("2", "456")

      Then("the goods reference list shows 2 Declaration Goods References")
      AddAnotherGoodsReferencePage.checkForContent("You have added 2 Declaration Goods References")
      answerAddAnother(AddAnotherGoodsReferencePage, "No")

      Then("I am back on the transport equipment list with 1 item")
      AddAnotherTransportEquipmentPage.loadPage("1")
      AddAnotherTransportEquipmentPage.checkForContent(s"Transport equipment $container1")
    }

    Scenario("Seals and goods references are listed only under their own transport equipment") {
      startJourneyUpToContainerDetails(TestDataGenerators.generateMrn())

      When("I add two seals and two goods references to the first transport equipment")
      addContainerDetails("1")
      addSeal("1", "SEAL001")
      answerAddAnother(AddAnotherSealPage, "Yes")
      addSeal("2", "SEAL002")
      answerAddAnother(AddAnotherSealPage, "No")
      addGoodsReference("1", "123")
      answerAddAnother(AddAnotherGoodsReferencePage, "Yes")
      addGoodsReference("2", "456")
      answerAddAnother(AddAnotherGoodsReferencePage, "No")
      AddAnotherTransportEquipmentPage.loadPage("1")
      answerAddAnother(AddAnotherTransportEquipmentPage, "Yes")

      And("I add one seal and one goods reference to the second transport equipment")
      addContainerDetails("2")
      addSeal("1", "SEAL003")

      Then("the second transport equipment seal list starts again with 1 seal")
      AddAnotherSealPage.loadPage("1")
      answerAddAnother(AddAnotherSealPage, "No")
      addGoodsReference("1", "789")

      Then("the second transport equipment goods reference list starts again with 1 reference")
      AddAnotherGoodsReferencePage.loadPage("1")
      answerAddAnother(AddAnotherGoodsReferencePage, "No")

      Then("the transport equipment list shows both items")
      AddAnotherTransportEquipmentPage.loadPage("2")
      AddAnotherTransportEquipmentPage.checkForContent(s"Transport equipment $container1")
      AddAnotherTransportEquipmentPage.checkForContent(s"Transport equipment $container2")
    }
  }
}
