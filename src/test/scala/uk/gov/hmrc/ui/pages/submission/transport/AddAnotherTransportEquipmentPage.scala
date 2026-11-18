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

package uk.gov.hmrc.ui.pages.submission.transport

import uk.gov.hmrc.ui.pages.common.YesNoPage

object AddAnotherTransportEquipmentPage extends YesNoPage {

  override def title(args: String*): String =
    s"You have added ${args.head} transport equipment"

  override def urlPart(args: String*): Option[String] = Some("/add-another-transport-equipment")

  def clickChange(containerId: String): Unit =
    clickByXPath(s"//a[normalize-space()='Change transport equipment $containerId']")

  def clickRemove(containerId: String): Unit =
    clickByXPath(s"//a[normalize-space()='Remove transport equipment $containerId']")

  def clickNext(): Unit =
    clickByXPath("//a[contains(@class,'govuk-pagination__link') and contains(normalize-space(),'Next')]")

  def clickPrevious(): Unit =
    clickByXPath("//a[contains(@class,'govuk-pagination__link') and contains(normalize-space(),'Previous')]")

}
