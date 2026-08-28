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

package uk.gov.hmrc.ui.steps
import uk.gov.hmrc.ui.pages.Submission.*
object SubmissionSteps {
  // Homepage
  def startNewSubmissionByLink(): Unit                                                    =
    AutomatedExportSystemPage
      .clickByPartialLinkText("Submit a new IE507(a) pre-notification")
  def startNewSubmissionByButton(): Unit                                                  =
    AutomatedExportSystemPage
      .clickByPartialLinkText("Start now")
  def viewExistingSubmissionsFromHomepage(): Unit                                         =
    AutomatedExportSystemPage
      .clickByPartialLinkText("View, change or cancel an existing submission")
  // Office of Exit dropdown
  def selectCustomsOffice(office: String): Unit                                           =
    ExitOfGoodsPage.selectById("value", office)
  // Type of Location dropdown
  def selectLocationType(locationType: String): Unit                                      =
    IdentifyLocationPage.selectById("locationType", locationType)
  // Confirmation page
  def viewMySubmissions(): Unit                                                           =
    SubmissionConfirmationPage.clickViewYourSubmissions()
  // View submissions page assertions
  def iCanSeeMySubmissionDetails(mrn: String, officeOfExit: String, status: String): Unit =
    ViewSubmissionsPage.assertSubmissionDetails(mrn, officeOfExit, status)
  def iSeeNoSubmissionsMessage(): Unit                                                    =
    ViewSubmissionsPage.assertNoSubmissionsMessage()
}
