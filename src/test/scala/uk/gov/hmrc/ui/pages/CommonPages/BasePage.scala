/*
 * Copyright 2023 HM Revenue & Customs
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

import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.{By, WebElement}
import uk.gov.hmrc.selenium.webdriver.Driver
import uk.gov.hmrc.ui.util.DriverHelper

trait BasePage extends DriverHelper {

  def findById(id: String): WebElement = find(By.id(id))

  def findByCssSelector(cssSelector: String): WebElement = find(By.cssSelector(cssSelector))

  def clickById(id: String): Unit = click(By.id(id))

  def clickByPartialLinkText(linkText: String): Unit = click(By.partialLinkText(linkText))

  def submitPage(): Unit = clickById("submit")

  def submitPageByType(): Unit = click(By.cssSelector("button[type='submit']"))

  def checkForContent(content: String): Unit = assert(findById("main-content").getText.contains(content))

  def navigateTo(url: String): Unit = Driver.instance.navigate().to(url)

  def selectById(id: String, text: String): Unit = {
    val select = new Select(findById(id))
    select.selectByVisibleText(text)
  }

  def fillInputById(id: String, text: String): Unit = {
    val input = findById(id)
    clearInput(input)
    input.sendKeys(text)
  }

  def clearInput(input: WebElement): Unit = input.clear()

  // Accept & Submit

  def clickAcceptAndSubmit(): Unit =
    clickByPartialLinkText("Accept and submit")

}
