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

import uk.gov.hmrc.ui.specs.BaseSpec
import uk.gov.hmrc.ui.pages.Amend.AmendSubmissionPage
import uk.gov.hmrc.ui.steps.LoginSteps.*
import uk.gov.hmrc.ui.steps.AmendSteps.*
import uk.gov.hmrc.ui.steps.SubmissionSteps.*
import uk.gov.hmrc.ui.pages.Submission.*
import uk.gov.hmrc.ui.util.TestDataGenerators

class AmendSpec extends BaseSpec {

  Feature("IE507(a) Amendment") {

    Scenario("E2E Journey: Successfully amend a submitted IE507(a)") {

      val eori                          = TestDataGenerators.generateEori()
      val mrn                           = TestDataGenerators.generateMrn()
      val ducr                          = "5GB000000000000-12345"
      val locationQualifier             = "Authorisation number"
      val unlocode                      = "UN123"
      val locationAdditionalIdentifier  = "AD01"
      val locationAdditionalIdentifier2 = "AD02"
      val authorisationReferenceNumber  = "AUTH12345"
      val authorisationReferenceNumber2 = "AUTH54321"
      val officeOfExit                  = "Warrenpoint (XI005160)"

      Given(s"I login with ID $eori")
      andILoginWithIDX(eori)

      And("I am on the page titled 'Submit an IE507(a) Arrival at Exit pre-notification'")
      AutomatedExportSystemPage.loadPage()

      When("I click on the 'Submit an IE507(a) Submission'")
      startNewSubmissionByLink()

      Then("I am on the page titled 'What is the Movement Reference Number(MRN)?'")
      MRNPage.loadPage()

      When(s"I enter a valid MRN $mrn")
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

      When("I select Warrenpoint Office from the dropdown")
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

      When("I select the submission I want to amend")
      clickSubmission(mrn)

      Then("I am on the page titled 'IE507(a) pre-notification details'")
      SubmissionDetailsPage.loadPage()

      When("I click the 'Amend this submission' button")
      clickAmendSubmissionButton()

      Then("I am on the page titled 'Review and amend your submission'")
      AmendSubmissionPage.loadPage()

      When("I click Change link to amend location details")
      AmendSubmissionPage.clickChangeLocationId()

      Then("I am on the page titled 'Identify the location'")
      IdentifyLocationPage.loadPage()

      When("I amend the location details")
      IdentifyLocationPage.amendLocationDetails(locationAdditionalIdentifier2, authorisationReferenceNumber2)

      And("I click the Continue button")
      IdentifyLocationPage.submitPageByType()

      Then("I return to the amendment summary page")
      AmendSubmissionPage.loadPage()

      When("I click the 'Amend this submission' button")
      AmendSubmissionPage.clickSubmitAmendment()

      Then("I am on the confirmation page and I have successfully amended the submission")
      SubmissionConfirmationPage.loadPage()

    }
  }
}
