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

class SubmissionTransportEquipmentLoopSpec extends BaseSpec with DiscrepancyJourney {

  private val container1 = "CONT1234567890121"
  private val container2 = "CONT1234567890122"

  Feature("IE507(a) Submission with discrepancies - transport equipment loop") {

    Scenario("Add two transport equipment items and see them on the list page") {
      startJourneyUpToContainerDetails(TestDataGenerators.generateMrn())

      addTransportEquipment("1")
      answerAddAnother(AddAnotherTransportEquipmentPage, "Yes")
      addTransportEquipment("2")

      Then("the list page shows both items")
      AddAnotherTransportEquipmentPage.checkForContent("You have added 2 transport equipment entries")
      AddAnotherTransportEquipmentPage.checkForContent(s"Transport equipment $container1")
      AddAnotherTransportEquipmentPage.checkForContent(s"Transport equipment $container2")
    }

    Scenario("Change a transport equipment item from the list page without creating a duplicate") {
      startJourneyUpToContainerDetails(TestDataGenerators.generateMrn())

      addTransportEquipment("1")
      answerAddAnother(AddAnotherTransportEquipmentPage, "Yes")
      addTransportEquipment("2")

      When("I click Change for the first item and change the container id")
      AddAnotherTransportEquipmentPage.clickChange(container1)
      ContainerDetailsPage.loadPage()
      ContainerDetailsPage.fillInputById("containerId", "CHANGED001")
      ContainerDetailsPage.submitPageByType()

      Then("the list still has 2 items and shows the new container id")
      AddAnotherTransportEquipmentPage.loadPage("2")
      AddAnotherTransportEquipmentPage.checkForContent("You have added 2 transport equipment entries")
      AddAnotherTransportEquipmentPage.checkForContent("Transport equipment CHANGED001")
      AddAnotherTransportEquipmentPage.checkForContent(s"Transport equipment $container2")
    }

    Scenario("Remove one of two transport equipment items") {
      startJourneyUpToContainerDetails(TestDataGenerators.generateMrn())

      addTransportEquipment("1")
      answerAddAnother(AddAnotherTransportEquipmentPage, "Yes")
      addTransportEquipment("2")

      When("I click Remove for the second item")
      AddAnotherTransportEquipmentPage.clickRemove(container2)

      Then("I am on the remove page with the warning about seals and goods references")
      RemoveTransportEquipmentPage.loadPage("2", container2)
      RemoveTransportEquipmentPage.checkForContent("This will also remove associated seals and goods references.")

      When("I confirm the removal")
      RemoveTransportEquipmentPage.select("Yes")
      RemoveTransportEquipmentPage.confirm()

      Then("the list has one item left")
      AddAnotherTransportEquipmentPage.loadPage("1")
      AddAnotherTransportEquipmentPage.checkForContent(s"Transport equipment $container1")
    }

    Scenario("Cancel the removal of a transport equipment item") {
      startJourneyUpToContainerDetails(TestDataGenerators.generateMrn())

      addTransportEquipment("1")
      answerAddAnother(AddAnotherTransportEquipmentPage, "Yes")
      addTransportEquipment("2")

      When("I click Remove for the second item and select No")
      AddAnotherTransportEquipmentPage.clickRemove(container2)
      RemoveTransportEquipmentPage.loadPage("2", container2)
      RemoveTransportEquipmentPage.select("No")
      RemoveTransportEquipmentPage.confirm()

      Then("the list still has 2 items")
      AddAnotherTransportEquipmentPage.loadPage("2")
      AddAnotherTransportEquipmentPage.checkForContent("You have added 2 transport equipment entries")
    }

    Scenario("Remove the last transport equipment item") {
      startJourneyUpToContainerDetails(TestDataGenerators.generateMrn())

      addTransportEquipment("1")

      When("I remove the only item")
      AddAnotherTransportEquipmentPage.clickRemove(container1)
      RemoveTransportEquipmentPage.loadPage("1", container1)
      RemoveTransportEquipmentPage.select("Yes")
      RemoveTransportEquipmentPage.confirm()

      Then("I am taken back to the container details page")
      ContainerDetailsPage.loadPage()
    }

    Scenario("Select No on the list page and go to the transport across the border page") {
      startJourneyUpToContainerDetails(TestDataGenerators.generateMrn())

      addTransportEquipment("1")
      answerAddAnother(AddAnotherTransportEquipmentPage, "No")

      Then("I am on the transport across the border page")
      TransportAcrossBorderPage.loadPage()
    }

    Scenario("Submit an empty container details page when no transport equipment was added") {
      startJourneyUpToContainerDetails(TestDataGenerators.generateMrn())

      When("I leave the container details empty")
      ContainerDetailsPage.loadPage()
      ContainerDetailsPage.submitPageByType()

      Then("I am on the first seal page")
      SealIdentifierPage.loadPage("1")
    }

    Scenario("Submit an empty container details page when a transport equipment item already exists") {
      startJourneyUpToContainerDetails(TestDataGenerators.generateMrn())

      addTransportEquipment("1")
      answerAddAnother(AddAnotherTransportEquipmentPage, "Yes")

      When("I leave the container details empty")
      ContainerDetailsPage.loadPage()
      ContainerDetailsPage.submitPageByType()

      Then("I am back on the list page with the existing item")
      AddAnotherTransportEquipmentPage.loadPage("1")
    }

    Scenario("The list page requires an answer") {
      startJourneyUpToContainerDetails(TestDataGenerators.generateMrn())

      addTransportEquipment("1")

      When("I click Continue without selecting Yes or No")
      AddAnotherTransportEquipmentPage.clickContinueButton()

      Then("I see an error and stay on the list page")
      AddAnotherTransportEquipmentPage.checkForContent("Select yes if you want to add another transport equipment")
      AddAnotherTransportEquipmentPage.loadPage("1")
    }

    Scenario("The list page shows pagination when there are more than 25 items") {
      startJourneyUpToContainerDetails(TestDataGenerators.generateMrn())

      When("I add 26 transport equipment items")
      (1 to 26).foreach { i =>
        ContainerDetailsPage.loadPage()
        ContainerDetailsPage.fillInputById("containerId", s"CONT$i")
        ContainerDetailsPage.fillInputById("numberOfSeals", "1")
        ContainerDetailsPage.submitPageByType()
        addSeal("1", "SEAL001")
        answerAddAnother(AddAnotherSealPage, "No")
        addGoodsReference("1", "123")
        answerAddAnother(AddAnotherGoodsReferencePage, "No")
        AddAnotherTransportEquipmentPage.loadPage(i.toString)
        if (i < 26) answerAddAnother(AddAnotherTransportEquipmentPage, "Yes")
      }

      Then("the first page shows the first 25 items")
      AddAnotherTransportEquipmentPage.checkForContent("Transport equipment CONT25")

      When("I click Next")
      AddAnotherTransportEquipmentPage.clickNext()

      Then("the second page shows the 26th item")
      AddAnotherTransportEquipmentPage.checkForContent("Transport equipment CONT26")

      When("I click Previous")
      AddAnotherTransportEquipmentPage.clickPrevious()

      Then("I am back on the first page")
      AddAnotherTransportEquipmentPage.checkForContent("Transport equipment CONT1")
    }
  }
}
