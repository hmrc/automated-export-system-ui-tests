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

import uk.gov.hmrc.ui.pages.common.StringPage

object SealIdentifierPage extends StringPage {

  override def title(args: String*): String = "What is the seal identifier?"

  // loadPage("2") also checks that the URL is for the 2nd seal
  override def urlPart(args: String*): Option[String] =
    args.headOption.map(index => s"/discrepancy-seals/$index")

}
