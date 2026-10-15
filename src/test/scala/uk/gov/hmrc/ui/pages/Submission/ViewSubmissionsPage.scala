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

package uk.gov.hmrc.ui.pages.Submission

import org.openqa.selenium.{By, NoSuchElementException, TimeoutException, WebDriver}
import org.openqa.selenium.support.ui.FluentWait
import uk.gov.hmrc.selenium.webdriver.Driver
import uk.gov.hmrc.ui.pages.CommonPages.Page

import java.time.Duration

object ViewSubmissionsPage extends Page {

  override def title(args: String*): String = "Your IE507(a) submissions"

  private val statusTimeout = Duration.ofSeconds(30)
  private val pollInterval  = Duration.ofSeconds(2)

  // Status is updated asynchronously (e.g. 'Awaiting decision' -> 'Accepted') and the
  // dashboard does not auto-refresh. Find the row for this MRN and refresh until it
  // shows the expected status, so other submissions on the page cannot cause a false pass.
  def assertSubmissionDetails(mrn: String, officeOfExit: String, status: String): Unit = {
    val rowLocator  = By.xpath(s"//tr[.//*[contains(normalize-space(.), '$mrn')]]")
    var lastRowText = "<row not found>"

    val wait = new FluentWait[WebDriver](Driver.instance)
      .withTimeout(statusTimeout)
      .pollingEvery(pollInterval)
      .ignoring(classOf[NoSuchElementException])

    try
      wait.until { (d: WebDriver) =>
        d.navigate().refresh()
        lastRowText = d.findElement(rowLocator).getText
        lastRowText.contains(status)
      }
    catch
      case _: TimeoutException =>
        throw new AssertionError(
          s"Submission $mrn did not reach status '$status' within ${statusTimeout.getSeconds}s. " +
            s"Last row: '$lastRowText'"
        )

    assert(lastRowText.contains(mrn), s"MRN '$mrn' not in row: '$lastRowText'")
    assert(lastRowText.contains(officeOfExit), s"Office of exit '$officeOfExit' not in row: '$lastRowText'")
    assert(lastRowText.contains(status), s"Status '$status' not in row: '$lastRowText'")
  }

  def assertNoSubmissionsMessage(): Unit =
    checkForContent("You have no IE507(a) submissions")

  // was matched on MRN + 'Awaiting decision'; now AES-906 moves status on within ms, so match on MRN only
  def clickSubmission(mrn: String): Unit =
    clickByXPath(s"//tr[.//a[text()='$mrn']]//a[text()='$mrn']")

}
