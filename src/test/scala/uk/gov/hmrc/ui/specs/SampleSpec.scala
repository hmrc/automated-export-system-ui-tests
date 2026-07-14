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

package uk.gov.hmrc.test.ui.specs

import org.openqa.selenium.By
import uk.gov.hmrc.selenium.webdriver.Driver
import uk.gov.hmrc.ui.conf.TestConfiguration

class SampleSpec extends BaseSpec {

  Feature("UI smoke test and environment connectivity validation") {

    Scenario("Open a page") {

      Given("browser is running")

      When("user goes to a page")
      Driver.instance.get("https://www.google.com")

      Then("page title should contain Google")
      Driver.instance.getTitle should include("Google")
    }

    Scenario("Application can be reached using configured environment") {

      Given("browser is running")

      When("user navigates to configured environment")

      Driver.instance.get(
        TestConfiguration.url("automated-export-system-frontend")
      )

      Then("the automated export system frontend page should be displayed")

      val serviceHeader =
        Driver.instance
          .findElement(By.className("govuk-header__service-name"))
          .getText

      serviceHeader shouldBe "automated-export-system-frontend"
    }
  }
}
