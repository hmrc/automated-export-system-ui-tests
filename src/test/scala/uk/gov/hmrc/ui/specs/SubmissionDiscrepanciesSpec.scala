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

class SubmissionDiscrepanciesSpec extends BaseSpec {

  Feature("IE507(a) Submission with discrepancies") {

    Scenario("E2E Journey: Submit a IE507(a) Declaration and declare a split discrepancy") {

      val mrn                          = "26GB0000X6524786A9"
      val ducr                         = "5GB000000000000-12345"
      val mucr                         = "GB/000000000000-12345"
      val locationQualifier            = "Authorisation number"
      val unlocode                     = "UN123"
      val locationAdditionalIdentifier = "AD01"
      val authorisationReferenceNumber = "AUTH12345"
      val officeOfExit                 = "Larne (GB000142)"
      val containerId                  = "CONT1234567890123"
      val numberOfSeals                = "2"
      val sealIdentifier               = "SEAL123"
      val goodsReference               = "123"
      val transportType                = "10"
      val transportIdNumber            = "IDNUMBER123"
      val countryOfRegistration        = "GB"
      val documentType                 = "1"
      val documentReferenceNumber      = "1234"
      val goodsItemNumber              = "4"
      val newGrossMass                 = "30"
      val newNetMass                   = "28"
      val packagingCode                = "BX"
      val numberOfPackages             = "3"
      val shippingMarks                = "MARKS123"

      Given("I login with ID GB12345679")
      andILoginWithIDX("GB12345679")

      And("I am on the page titled 'Submit an IE507(a) Arrival at Exit pre-notification'")
      AutomatedExportSystemPage.loadPage()

      When("I click on the 'Start Now' button")
      startNewSubmissionByButton()

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

      When("I select Larne (GB000142) from the dropdown list")
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

      Then("I am on the page titled 'What is the seal identifier?'")
      SealIdentifierPage.loadPage()

      When("I enter a valid seal identifier")
      SealIdentifierPage.fillInput(sealIdentifier)

      And("I click the Continue button")
      SealIdentifierPage.submitPageByType()

      Then("I am on the page titled 'What is the Declaration Goods Reference?'")
      DeclarationGoodsRefPage.loadPage()

      When("I enter a valid reference")
      DeclarationGoodsRefPage.fillInput(goodsReference)

      And("I click the Continue button")
      DeclarationGoodsRefPage.submitPageByType()

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

      Then("I am on the page titled 'Tell us what’s changed'")
      TellUsWhatChangedPage.loadPage()

      When("I enter valid changed details")
      TellUsWhatChangedPage.fillInputById("declarationGoodsItemNumber", goodsItemNumber)
      TellUsWhatChangedPage.fillInputById("declarationUniqueConsignmentReference", mucr)
      TellUsWhatChangedPage.fillInputById("newGrossMass", newGrossMass)
      TellUsWhatChangedPage.fillInputById("newNetMass", newNetMass)

      And("I click the Continue button")
      TellUsWhatChangedPage.submitPageByType()

      Then("I am on the page titled 'Packing details'")
      PackingDetailsPage.loadPage()

      When("I enter the valid packing details")
      PackingDetailsPage.fillInputById("packagingCode", packagingCode)
      PackingDetailsPage.fillInputById("numberOfPackages", numberOfPackages)
      PackingDetailsPage.fillInputById("shippingMarks", shippingMarks)

      And("I click the Continue button")
      PackingDetailsPage.submitPageByType()

      Then("I am on the Check Your Answers page")
      CheckYourAnswersPage.loadPage()

      When("I accept and submit the declaration")
      CheckYourAnswersPage.clickAcceptAndSubmit()

      Then("I am shown the submission confirmation page")
      SubmissionConfirmationPage.loadPage()
    }
  }
}
