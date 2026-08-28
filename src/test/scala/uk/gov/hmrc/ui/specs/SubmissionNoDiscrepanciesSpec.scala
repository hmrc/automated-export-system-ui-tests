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

import uk.gov.hmrc.test.ui.specs.BaseSpec
import uk.gov.hmrc.ui.steps.LoginSteps.*
import uk.gov.hmrc.ui.steps.SubmissionSteps.*
import uk.gov.hmrc.ui.pages.Submission.*

class SubmissionNoDiscrepanciesSpec extends BaseSpec {

  Feature("IE507(a) Submission with no discrepancies") {

    Scenario("E2E Journey: Complete a IE507(a) Declaration with no discrepancies, then view it in the dashboard") {

      val mrn                          = "26GB0000X6524786A9"
      val ducr                         = "5GB000000000000-12345"
      val locationQualifier            = "Authorisation number"
      val unlocode                     = "UN123"
      val locationAdditionalIdentifier = "AD01"
      val authorisationReferenceNumber = "AUTH12345"
      val officeOfExit                 = "Belfast (GB000051)"
      val status                       = "Awaiting decision" // backend assigns this status to every new submission by default

      Given("I login with ID GB12345679")
      andILoginWithIDX("GB12345679")

      And("I am on the page titled 'Submit an IE507(a) Arrival at Exit pre-notification'")
      AutomatedExportSystemPage.loadPage()

      When("I click on the 'Submit an IE507(a) Submission'")
      startNewSubmissionByLink()

      Then("I am on the page titled 'What is the Movement Reference Number(MRN)?'")
      MRNPage.loadPage()

      When("I enter a valid MRN")
      MRNPage.fillInput(mrn)

      And("I click the Continue button")
      MRNPage.submitPageByType()

      Then("I am on the page titled 'What is the Declaration Unique Consignment Reference (DUCR)?'")
      DUCRPage.loadPage()

      When("I enter a valid DUCR")
      DUCRPage.fillInput(ducr)

      And("I click the Continue button")
      DUCRPage.submitPageByType()

      Then("I am on the page titled 'Is this part of a consolidation?'")
      IsThisConsolidationPage.loadPage()

      When("I click 'No - this is a standalone consignment")
      IsThisConsolidationPage.select("No")

      And("I click the Continue button")
      IsThisConsolidationPage.submitPageByType()

      Then("I am on the page titled 'What type of location are the goods at?'")
      TypeofLocationPage.loadPage()

      When("I click 'Designated location' option")
      TypeofLocationPage.select("Designated location")

      And("I click the Continue button")
      TypeofLocationPage.submitPageByType()

      Then("I am on the page titled 'Identify the location'")
      IdentifyLocationPage.loadPage()

      When("I enter valid location details")
      selectLocationType(locationQualifier)
      IdentifyLocationPage.fillInputById("unlocode", unlocode)
      IdentifyLocationPage.fillInputById("locationAdditionalIdentifier", locationAdditionalIdentifier)
      IdentifyLocationPage.fillInputById("authorisationReferenceNumber", authorisationReferenceNumber)

      And("I click the Continue button")
      IdentifyLocationPage.submitPageByType()

      Then("I am on the page titled 'Where do you expect the goods to exit the UK?'")
      ExitOfGoodsPage.loadPage()

      When("I select Belfast Office from the dropdown")
      selectCustomsOffice(officeOfExit)

      And("I click the Continue button")
      ExitOfGoodsPage.submitPageByType()

      Then("I am on the page titled 'Is this a split exit?'")
      IsThisSplitExitPage.loadPage()

      When("I click No")
      IsThisSplitExitPage.select("No")

      And("I click the Continue button")
      IsThisSplitExitPage.submitPageByType()

      Then("I am on the discrepancies page")
      AreThereAnyDiscrepanciesPage.loadPage()

      When("I select No")
      AreThereAnyDiscrepanciesPage.select("No")

      And("I click the Continue button")
      AreThereAnyDiscrepanciesPage.submitPageByType()

      Then("I am on the Check Your Answers page")
      CheckYourAnswersPage.loadPage()

      When("I accept and submit the declaration")
      CheckYourAnswersPage.clickAcceptAndSubmit()

      Then("I am shown the submission confirmation page")
      SubmissionConfirmationPage.loadPage()

      When("I click 'View your submissions'")
      viewMySubmissions()

      Then("I am on the page titled 'Your IE507(a) submissions'")
      ViewSubmissionsPage.loadPage()

      And("I can see my submitted IE507(a) notification with the correct details")
      iCanSeeMySubmissionDetails(mrn, officeOfExit, status)

    }

    Scenario("View submissions page shows empty state for an EORI with no submissions") {

      // unique per run to guarantee a clean EORI with no submission history
      val eori = s"GB${System.currentTimeMillis().toString.takeRight(9)}"

      Given(s"I login with a fresh EORI $eori")
      andILoginWithIDX(eori)

      And("I am on the page titled 'Submit an IE507(a) Arrival at Exit pre-notification'")
      AutomatedExportSystemPage.loadPage()

      When("I click 'View, change or cancel an existing submission'")
      viewExistingSubmissionsFromHomepage()

      Then("I am on the page titled 'Your IE507(a) submissions'")
      ViewSubmissionsPage.loadPage()

      And("I see the no submissions message")
      iSeeNoSubmissionsMessage()
    }
  }
}
