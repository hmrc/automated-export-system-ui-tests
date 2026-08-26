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

import uk.gov.hmrc.ui.pages.CommonPages.RadioPage

object TypeofLocationPage extends RadioPage {

  override def title(args: String*): String = "What type of location are the goods at?"

  override def select(answer: String): this.type = {
    val value = answer match {
      case "Designated location" => "designatedLocation"
      case "Authorised place"    => "authorisedPlace"
      case "Approved place"      => "approvedPlace"
      case "Other"               => "other"
    }
    clickRadioBtn(value)
    this
  }

}
