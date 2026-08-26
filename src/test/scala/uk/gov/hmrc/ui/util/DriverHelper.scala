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

package uk.gov.hmrc.ui.util

import org.openqa.selenium.{By, WebDriver, WebElement}
import org.openqa.selenium.support.ui.{ExpectedConditions, FluentWait, WebDriverWait}
import uk.gov.hmrc.ui.driver.Driver

import java.time.Duration

trait DriverHelper extends Driver {

  def fluentWait(implicit driver: WebDriver): FluentWait[WebDriver] =
    new FluentWait(driver)
      .withTimeout(Duration.ofSeconds(10))
      .pollingEvery(Duration.ofMillis(500))
      .ignoring(classOf[Exception])

  def explicitWait(implicit driver: WebDriver): WebDriverWait =
    new WebDriverWait(driver, Duration.ofSeconds(10))

  def find(by: By)(implicit driver: WebDriver): WebElement =
    fluentWait.until(ExpectedConditions.presenceOfElementLocated(by))

  def click(by: By)(implicit driver: WebDriver): Unit =
    fluentWait.until(ExpectedConditions.elementToBeClickable(by)).click()
}
