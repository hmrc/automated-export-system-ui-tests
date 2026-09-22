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

import java.time.Duration

import org.openqa.selenium.By
import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import uk.gov.hmrc.selenium.webdriver.Driver
import uk.gov.hmrc.test.ui.specs.BaseSpec
import uk.gov.hmrc.ui.steps.LoginSteps.*
import uk.gov.hmrc.ui.steps.SubmissionSteps.*
import uk.gov.hmrc.ui.pages.Submission.*
import uk.gov.hmrc.ui.util.TestDataGenerators

class MrnDucrValidationSpec extends BaseSpec {

  // valid MRN, used to reach the DUCR page in the two DUCR scenarios below
  val mrn = TestDataGenerators.generateMrn()

  // invalid MRN: fails the year group (must be 24-29 or 30-99)
  val invalidMrn = "23AB123456789012A1"

  // invalid DUCR: "!" is not allowed by any DUCR pattern
  val invalidDucr = "abc123!"

  /*
   * The title doesn't change when the error page loads, so loadPage() alone
   * isn't a safe wait - it can return before the new page finishes loading,
   * causing a stale element. We wait for the error box instead, since it only
   * appears once the new page has actually loaded.
   */
  private def waitForErrorSummary(): Unit =
    new WebDriverWait(Driver.instance, Duration.ofSeconds(10))
      .until(ExpectedConditions.visibilityOfElementLocated(By.className("govuk-error-summary")))

  Feature("IE507(a) MRN and DUCR field validation") {

    Scenario("Submitting an empty MRN shows an error and does not progress") {

      Given("I login with ID GB12345679")
      andILoginWithIDX("GB12345679")

      And("I am on the page titled 'Submit an IE507(a) Arrival at Exit pre-notification'")
      AutomatedExportSystemPage.loadPage()

      When("I click on the 'Submit an IE507(a) Submission'")
      startNewSubmissionByLink()

      Then("I am on the page titled 'What is the Movement Reference Number(MRN)?'")
      MRNPage.loadPage()

      When("I click the Continue button without entering an MRN")
      MRNPage.submitPageByType()

      And("I wait for the error page to load")
      waitForErrorSummary()

      Then("I remain on the page titled 'What is the Movement Reference Number(MRN)?'")
      MRNPage.loadPage()

      // AES-881: raw message key shown instead of readable text - update once fixed.
      And("I see the error 'Enter `enterMrn`'")
      MRNPage.checkForContent("Enter `enterMrn`")
    }

    Scenario("Submitting an invalid-format MRN shows an error and does not progress") {

      Given("I login with ID GB12345679")
      andILoginWithIDX("GB12345679")

      And("I am on the page titled 'Submit an IE507(a) Arrival at Exit pre-notification'")
      AutomatedExportSystemPage.loadPage()

      When("I click on the 'Submit an IE507(a) Submission'")
      startNewSubmissionByLink()

      Then("I am on the page titled 'What is the Movement Reference Number(MRN)?'")
      MRNPage.loadPage()

      When(s"I enter an invalid-format MRN $invalidMrn")
      MRNPage.fillInput(invalidMrn)

      And("I click the Continue button")
      MRNPage.submitPageByType()

      And("I wait for the error page to load")
      waitForErrorSummary()

      Then("I remain on the page titled 'What is the Movement Reference Number(MRN)?'")
      MRNPage.loadPage()

      And("I see the invalid format error message")
      MRNPage.checkForContent("EnterMrn must be in the correct format, for example 24AB123456789012A1")
    }

    Scenario("Submitting an empty DUCR shows an error and does not progress") {

      Given("I login with ID GB12345679")
      andILoginWithIDX("GB12345679")

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

      When("I click the Continue button without entering a DUCR")
      DUCRPage.submitPageByType()

      And("I wait for the error page to load")
      waitForErrorSummary()

      Then("I remain on the page titled 'What is the Declaration Unique Consignment Reference (DUCR)?'")
      DUCRPage.loadPage()

      // AES-881: raw message key shown instead of readable text - update once fixed.
      And("I see the error 'Enter enterDucr'")
      DUCRPage.checkForContent("Enter enterDucr")
    }

    Scenario("Submitting an invalid-format DUCR shows an error and does not progress") {

      Given("I login with ID GB12345679")
      andILoginWithIDX("GB12345679")

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

      When(s"I enter an invalid-format DUCR $invalidDucr")
      DUCRPage.fillInput(invalidDucr)

      And("I click the Continue button")
      DUCRPage.submitPageByType()

      And("I wait for the error page to load")
      waitForErrorSummary()

      Then("I remain on the page titled 'What is the Declaration Unique Consignment Reference (DUCR)?'")
      DUCRPage.loadPage()

      And("I see the invalid format error message")
      DUCRPage.checkForContent("EnterDucr must only include letters and numbers")
    }
  }
}
