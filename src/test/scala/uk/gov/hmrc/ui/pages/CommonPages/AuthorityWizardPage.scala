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

package uk.gov.hmrc.ui.pages.CommonPages

import uk.gov.hmrc.ui.conf.TestConfiguration

object AuthorityWizardPage extends Page {

  override def title(args: String*): String = "Authority Wizard"

  override def loadPage(args: String*): AuthorityWizardPage.this.type = {
    navigateTo(TestConfiguration.authorityWizardPage)
    super.loadPage(args*)
  }

  def fillInputs(eoriNumber: String): this.type = {
    val redirectionUrl = TestConfiguration.url("automated-export-system-frontend")
    findById("redirectionUrl").sendKeys(redirectionUrl)
    findById("credentialStrength").sendKeys("strong")
    findById("confidenceLevel").sendKeys("50")
    findById("affinityGroupSelect").sendKeys("Organisation")
    findById("enrolment[0].name").sendKeys("HMRC-CUS-ORG")
    findById("input-0-0-name").sendKeys("EORINumber")
    findById("input-0-0-value").sendKeys(eoriNumber)
    this

  }
}
