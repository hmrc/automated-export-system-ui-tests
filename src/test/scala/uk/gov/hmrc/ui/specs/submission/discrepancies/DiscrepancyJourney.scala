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

import uk.gov.hmrc.ui.pages.common.YesNoPage
import uk.gov.hmrc.ui.pages.submission.*
import uk.gov.hmrc.ui.pages.submission.goods.*
import uk.gov.hmrc.ui.pages.submission.transport.*
import uk.gov.hmrc.ui.steps.LoginSteps.*
import uk.gov.hmrc.ui.steps.SubmissionSteps.*

trait DiscrepancyJourney { self: BaseSpec =>

  protected val ducr                         = "5GB000000000000-12345"
  protected val mucr                         = "GB/000000000000-12345"
  protected val locationQualifier            = "Authorisation number"
  protected val unlocode                     = "UN123"
  protected val locationAdditionalIdentifier = "AD01"
  protected val authorisationReferenceNumber = "AUTH12345"
  protected val officeOfExit                 = "Larne (XI005220)"

  protected def startJourneyUpToContainerDetails(mrn: String): Unit = {
    Given("I login with ID GB12345679")
    andILoginWithIDX("GB12345679")

    And("I am on the start page")
    AutomatedExportSystemPage.loadPage()

    When("I click on the 'Start Now' button")
    startNewSubmissionByButton()

    Then("I enter a valid MRN and DUCR")
    MRNPage.loadPage()
    MRNPage.fillInput(mrn)
    MRNPage.submitPageByType()
    DUCRPage.loadPage()
    DUCRPage.fillInput(ducr)
    DUCRPage.submitPageByType()

    When("I say it is part of a consolidation and enter the MUCR")
    IsThisConsolidationPage.loadPage()
    IsThisConsolidationPage.select("Yes")
    IsThisConsolidationPage.fillInputById("mucr", mucr)
    IsThisConsolidationPage.submitPageByType()

    And("I enter the location details")
    TypeofLocationPage.loadPage()
    TypeofLocationPage.select("Approved place")
    TypeofLocationPage.submitPageByType()
    IdentifyLocationPage.loadPage()
    selectLocationType(locationQualifier)
    IdentifyLocationPage.fillInputById("unlocode", unlocode)
    IdentifyLocationPage.fillInputById("locationAdditionalIdentifier", locationAdditionalIdentifier)
    IdentifyLocationPage.fillInputById("authorisationReferenceNumber", authorisationReferenceNumber)
    IdentifyLocationPage.submitPageByType()

    And("I select the office of exit and say it is a split exit")
    ExitOfGoodsPage.loadPage()
    selectCustomsOffice(officeOfExit)
    ExitOfGoodsPage.submitPageByType()
    IsThisSplitExitPage.loadPage()
    IsThisSplitExitPage.select("Yes")
    IsThisSplitExitPage.submitPageByType()

    And("I select Rail as the way the goods cross the border")
    ModeOfTransportBorderPage.loadPage()
    ModeOfTransportBorderPage.select("Rail")
    ModeOfTransportBorderPage.submitPageByType()
  }

  protected def addTransportEquipment(index: String): Unit = {
    When(s"I add transport equipment $index")
    ContainerDetailsPage.loadPage()
    ContainerDetailsPage.fillInputById("containerId", s"CONT123456789012$index")
    ContainerDetailsPage.fillInputById("numberOfSeals", "1")
    ContainerDetailsPage.submitPageByType()

    Then(s"I am on the Transport Equipment Check Your Answers page for equipment $index")
    TransportEquipmentCYAPage.loadPage(index)
    TransportEquipmentCYAPage.clickContinue()
    AddAnotherTransportEquipmentPage.loadPage(index)
  }

  protected def addSeal(index: String, identifier: String): Unit = {
    When(s"I add seal $index")
    SealIdentifierPage.loadPage(index)
    SealIdentifierPage.fillInput(identifier)
    SealIdentifierPage.submitPageByType()

    Then(s"I am on the Seals Check Your Answers page for seal $index")
    SealsCYAPage.loadPage(index)
    SealsCYAPage.clickContinue()
    AddAnotherSealPage.loadPage(index)
  }

  protected def addGoodsReference(index: String, reference: String): Unit = {
    When(s"I add Declaration Goods Reference $index")
    DeclarationGoodsRefPage.loadPage(index)
    DeclarationGoodsRefPage.fillInput(reference)
    DeclarationGoodsRefPage.submitPageByType()

    Then(s"I am on the Goods Reference Check Your Answers page for reference $index")
    GoodsReferenceCYAPage.loadPage(index)
    GoodsReferenceCYAPage.clickContinue()
    AddAnotherGoodsReferencePage.loadPage(index)
  }

  protected def answerAddAnother(page: YesNoPage, answer: String): Unit = {
    When(s"I click '$answer' on the add another page")
    page.select(answer)
    page.clickContinueButton()
  }

  protected def enterTransportAndGoodsDetails(): Unit = {
    When("I enter valid transport details")
    TransportAcrossBorderPage.loadPage()
    TransportAcrossBorderPage.fillInputById("transportType", "10")
    TransportAcrossBorderPage.fillInputById("transportIdNumber", "IDNUMBER123")
    TransportAcrossBorderPage.fillInputById("countryOfRegistration", "GB")
    TransportAcrossBorderPage.submitPageByType()

    And("I leave the document details empty")
    DocumentDetailsPage.loadPage()
    DocumentDetailsPage.submitPageByType()

    And("I enter valid changed details")
    TellUsWhatChangedPage.loadPage()
    TellUsWhatChangedPage.fillInputById("declarationGoodsItemNumber", "4")
    TellUsWhatChangedPage.fillInputById("declarationUniqueConsignmentReference", ducr)
    TellUsWhatChangedPage.fillInputById("newGrossMass", "30")
    TellUsWhatChangedPage.fillInputById("newNetMass", "28")
    TellUsWhatChangedPage.submitPageByType()
  }

  protected def addPackaging(index: String, code: String, packages: String, marks: String): Unit = {
    When(s"I add packaging detail $index")
    PackingDetailsPage.loadPage()
    PackingDetailsPage.fillInputById("packagingCode", code)
    PackingDetailsPage.fillInputById("numberOfPackages", packages)
    PackingDetailsPage.fillInputById("shippingMarks", marks)
    PackingDetailsPage.submitPageByType()

    Then(s"I am on the Packaging Check Your Answers page for packaging $index")
    PackagingCYAPage.loadPage()
    PackagingCYAPage.clickContinue()
    PackagingSummaryListPage.loadPage(index)
  }

  protected def submitDeclaration(): Unit = {
    Then("I am on the Check Your Answers page")
    CheckYourAnswersPage.loadPage()

    When("I accept and submit the declaration")
    CheckYourAnswersPage.clickAcceptAndSubmit()

    Then("I am shown the submission confirmation page")
    SubmissionConfirmationPage.loadPage()
  }

  protected def enterChangedDetails(ducr: String): Unit = {
    When("I enter valid changed details")
    TellUsWhatChangedPage.loadPage()
    TellUsWhatChangedPage.fillInputById("declarationGoodsItemNumber", "4")
    TellUsWhatChangedPage.fillInputById("declarationUniqueConsignmentReference", ducr)
    TellUsWhatChangedPage.fillInputById("newGrossMass", "30")
    TellUsWhatChangedPage.fillInputById("newNetMass", "28")
    TellUsWhatChangedPage.submitPageByType()
  }

}
