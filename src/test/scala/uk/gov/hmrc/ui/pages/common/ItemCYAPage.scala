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

package uk.gov.hmrc.ui.pages.common

/** "Check your answers" page for one item of a list (seal, goods reference, transport equipment...). */
trait ItemCYAPage extends Page {

  protected def urlSegment: String

  override def title(args: String*): String = "Check your answers"

  override def urlPart(args: String*): Option[String] = Some(s"/$urlSegment/${args.head}")

  def clickContinue(): Unit = clickContinueButton()

}
