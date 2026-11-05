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

import uk.gov.hmrc.ui.pages.submission.transport.*
import uk.gov.hmrc.ui.pages.submission.goods.*
import uk.gov.hmrc.ui.steps.LoginSteps.*
import uk.gov.hmrc.ui.steps.SubmissionSteps.*
import uk.gov.hmrc.ui.pages.submission.*
import uk.gov.hmrc.ui.util.TestDataGenerators

class SubmissionDiscrepanciesLoopSpec extends BaseSpec {

  Feature("IE507(a) Submission with discrepancies - add another answers") {

    Scenario("E2E Journey: Submit a IE507(a) Declaration and declare a split discrepancy") {

      val mrn                          = TestDataGenerators.generateMrn()
      val ducr                         = "5GB000000000000-12345"
      val mucr                         = "GB/000000000000-12345"
      val locationQualifier            = "Authorisation number"
      val unlocode                     = "UN123"
      val locationAdditionalIdentifier = "AD01"
      val authorisationReferenceNumber = "AUTH12345"
      val officeOfExit                 = "Larne (XI005220)"
      val containerId                  = "CONT1234567890123"
      val numberOfSeals                = "2"
      val sealIdentifier               = "SEAL123"
      val sealIdentifier2              = "SEAL456"
      val goodsReference               = "123"
      val goodsReference2              = "456"
      val transportType                = "10"
      val transportIdNumber            = "IDNUMBER123"
      val countryOfRegistration        = "GB"
      val documentType                 = "1"
      val documentReferenceNumber      = "1234"

      Given("I login with ID GB12345679")
      andILoginWithIDX("GB12345679")

      And("I am on the page titled 'Submit an IE507(a) Arrival at Exit pre-notification'")
      AutomatedExportSystemPage.loadPage()

      When("I click on the 'Start Now' button")
      startNewSubmissionByButton()

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

      When("I click 'Yes - enter the MUCR'")
      IsThisConsolidationPage.select("Yes")

      And("I enter the MUCR")
      IsThisConsolidationPage.fillInputById("mucr", mucr)

      And("I click the Continue button")
      IsThisConsolidationPage.submitPageByType()

      Then("I am on the page titled 'What type of location are the goods at?'")
      TypeofLocationPage.loadPage()

      When("I click 'Approved place' option")
      TypeofLocationPage.select("Approved place")

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

      When(s"I select $officeOfExit from the dropdown list")
      selectCustomsOffice(officeOfExit)

      And("I click the Continue button")
      ExitOfGoodsPage.submitPageByType()

      Then("I am on the page titled 'Is this a split exit?'")
      IsThisSplitExitPage.loadPage()

      When("I click 'Yes, this is a split exit'")
      IsThisSplitExitPage.select("Yes")

      And("I click the Continue button")
      IsThisSplitExitPage.submitPageByType()

      Then("I am on the page titled 'How will the goods cross the border?'")
      ModeOfTransportBorderPage.loadPage()

      When("I click 'Rail'")
      ModeOfTransportBorderPage.select("Rail")

      And("I click the Continue button")
      ModeOfTransportBorderPage.submitPageByType()

      Then("I am on the page titled 'Container details'")
      ContainerDetailsPage.loadPage()

      When("I enter container identification number & number of seals")
      ContainerDetailsPage.fillInputById("containerId", containerId)
      ContainerDetailsPage.fillInputById("numberOfSeals", numberOfSeals)

      And("I click the Continue button")
      ContainerDetailsPage.submitPageByType()

      Then("I am on the Transport Equipment Check Your Answers page")
      TransportEquipmentCYAPage.loadPage("1")

      When("I click the Continue button on the Transport Equipment Check Your Answers page")
      TransportEquipmentCYAPage.clickContinue()

      Then("I am on the 'You have added 1 transport equipment' page")
      AddAnotherTransportEquipmentPage.loadPage("1")

      When("I click 'No' to not add another transport equipment")
      AddAnotherTransportEquipmentPage.select("No")

      And("I click the Continue button")
      AddAnotherTransportEquipmentPage.clickContinueButton()

      Then("I am on the page titled 'What is the seal identifier?'")
      SealIdentifierPage.loadPage()

      When("I enter a valid seal identifier for the 1st seal")
      SealIdentifierPage.fillInput(sealIdentifier)

      And("I click the Continue button")
      SealIdentifierPage.submitPageByType()

      // Note: the Seal CYA page shows "Seal 1", "Seal 2" rather than full seal identifier details (confirmed
      // behaviour in ticket 921), so only the URL and title are asserted here.
      Then("I am on the Seals Check Your Answers page for the 1st seal")
      SealsCYAPage.loadPage("1")

      When("I click the Continue button on the Seals Check Your Answers page")
      SealsCYAPage.clickContinue()

      Then("I am on the 'You have added 1 seal' page")
      AddAnotherSealPage.loadPage("1")

      When("I click 'Yes' to add another seal")
      AddAnotherSealPage.select("Yes")

      And("I click the Continue button")
      AddAnotherSealPage.clickContinueButton()

      Then("I am on the page titled 'What is the seal identifier?' for the 2nd seal")
      SealIdentifierPage.loadPage("2")

      When("I enter a valid seal identifier for the 2nd seal")
      SealIdentifierPage.fillInput(sealIdentifier2)

      And("I click the Continue button")
      SealIdentifierPage.submitPageByType()

      Then("I am on the Seals Check Your Answers page for the 2nd seal")
      SealsCYAPage.loadPage("2")

      When("I click the Continue button on the Seals Check Your Answers page")
      SealsCYAPage.clickContinue()

      Then("I am on the 'You have added 2 seals' page")
      AddAnotherSealPage.loadPage("2")

      When("I click 'No' to not add another seal")
      AddAnotherSealPage.select("No")

      And("I click the Continue button")
      AddAnotherSealPage.clickContinueButton()

      Then("I am on the page titled 'What is the Declaration Goods Reference?'")
      DeclarationGoodsRefPage.loadPage()

      When("I enter a valid reference for the 1st reference")
      DeclarationGoodsRefPage.fillInput(goodsReference)

      And("I click the Continue button")
      DeclarationGoodsRefPage.submitPageByType()

      Then("I am on the Declaration Goods Reference Check Your Answers page for the 1st reference")
      GoodsReferenceCYAPage.loadPage("1")

      When("I click the Continue button on the Declaration Goods Reference Check Your Answers page")
      GoodsReferenceCYAPage.clickContinue()

      Then("I am on the 'You have added 1 Declaration Goods Reference' page")
      AddAnotherGoodsReferencePage.loadPage("1")

      When("I click 'Yes' to add another Declaration Goods Reference")
      AddAnotherGoodsReferencePage.select("Yes")

      And("I click the Continue button")
      AddAnotherGoodsReferencePage.clickContinueButton()

      Then("I am on the page titled 'What is the Declaration Goods Reference?' for the 2nd reference")
      DeclarationGoodsRefPage.loadPage("2")

      When("I enter a valid reference for the 2nd reference")
      DeclarationGoodsRefPage.fillInput(goodsReference2)

      And("I click the Continue button")
      DeclarationGoodsRefPage.submitPageByType()

      Then("I am on the Declaration Goods Reference Check Your Answers page for the 2nd reference")
      GoodsReferenceCYAPage.loadPage("2")

      When("I click the Continue button on the Declaration Goods Reference Check Your Answers page")
      GoodsReferenceCYAPage.clickContinue()

      Then("I am on the 'You have added 2 Declaration Goods References' page")
      AddAnotherGoodsReferencePage.loadPage("2")

      When("I click 'No' to not add another Declaration Goods Reference")
      AddAnotherGoodsReferencePage.select("No")

      And("I click the Continue button")
      AddAnotherGoodsReferencePage.clickContinueButton()

      Then("I am on the page titled 'Transport across the border'")
      TransportAcrossBorderPage.loadPage()

      When("I enter valid transport details")
      TransportAcrossBorderPage.fillInputById("transportType", transportType)
      TransportAcrossBorderPage.fillInputById("transportIdNumber", transportIdNumber)
      TransportAcrossBorderPage.fillInputById("countryOfRegistration", countryOfRegistration)

      And("I click the Continue button")
      TransportAcrossBorderPage.submitPageByType()

      Then("I am on the page titled 'Document details'")
      DocumentDetailsPage.loadPage()

      When("I enter valid document details")
      DocumentDetailsPage.fillInputById("documentType", documentType)
      DocumentDetailsPage.fillInputById("referenceNumber", documentReferenceNumber)

      And("I click the Continue button")
      DocumentDetailsPage.submitPageByType()

      Then("I am on the Transport Document Check Your Answers page")
      TransportDocCYAPage.loadPage("1")

      // TODO: Transport Document CYA currently links straight to the final Check Your Answers page (AES-1164).
      // Once the "add another transport document" page is built (future ticket), add that step here.
      // 'Tell us what’s changed' and 'Packing details' are covered by the "no transport document" scenario below.
      When("I click the Continue button on the Transport Document Check Your Answers page")
      TransportDocCYAPage.clickContinue()

      Then("I am on the Check Your Answers page")
      CheckYourAnswersPage.loadPage()

      When("I accept and submit the declaration")
      CheckYourAnswersPage.clickAcceptAndSubmit()

      Then("I am shown the submission confirmation page")
      SubmissionConfirmationPage.loadPage()
    }
  }
}
