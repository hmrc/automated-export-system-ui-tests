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
import uk.gov.hmrc.ui.steps.CancellationSteps.*
import uk.gov.hmrc.ui.pages.Cancellation.*

class CancellationSpec extends BaseSpec {

  Feature("IE507(a) Cancellation") {

    Scenario("E2E Journey: Successfully cancel a submitted IE507(a)") {

      val mrn          = "26GB0000X6524786A9"
      val officeOfExit = "Belfast (GB000051)"

      Given("I login with ID GB12345679")
      andILoginWithIDX("GB12345679")

      And("I am on the page titled 'Submit an IE507(a) Arrival at Exit pre-notification'")
      AutomatedExportSystemPage.loadPage()

      When("I click on the 'View,change or cancel an existing submission' link")
      viewExistingSubmissionsFromHomepage()

      Then("I am on the page titled 'Your IE507(a) submissions'")
      ViewSubmissionsPage.loadPage()

      When("I select the submission I want to cancel")
      clickSubmission(mrn)

      Then("I am on the page titled 'IE507(a) pre-notification details'")
      SubmissionDetailsPage.loadPage()

      When("I click the 'Cancel this submission' button")
      clickCancelSubmissionButton()

      Then("I am on the page titled 'Are you sure you want to cancel this submission?'")
      AreYouSureCancelPage.loadPage()

      When("I click 'Cancel this submission' button")
      AreYouSureCancelPage.submitPageByType()

      Then("I am shown the Cancelled submission confirmation page")
      CancellationConfirmationPage.loadPage()

      When("I click 'Return to your submissions'")
      returnToSubmissions()

      Then("I am on the page titled 'Your IE507(a) submissions'")
      ViewSubmissionsPage.loadPage()

      And("I can view that the submission now has a status of 'Cancelled'")
      iCanSeeMySubmissionDetails(mrn, officeOfExit, "Cancelled")
    }
  }
}
