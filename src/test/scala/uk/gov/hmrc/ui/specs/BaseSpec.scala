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

import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfterEach, GivenWhenThen}
import uk.gov.hmrc.selenium.webdriver.{Browser, Driver, ScreenshotOnFailure}

import java.net.URI
import java.net.http.{HttpClient, HttpRequest, HttpResponse}

// AES-856: runs exactly once across the whole JVM/test run, no matter how many suites
// or scenarios trigger it. Scala's `lazy val` guarantees this - the first thread to
// touch it runs the block, every other thread just gets the already-computed result.
// This avoids wiping data mid-test that another suite is still relying on.
private object TestDataCleanup {

  lazy val runOnce: Unit = {
    val httpClient = HttpClient.newHttpClient()
    val request    = HttpRequest
      .newBuilder()
      .uri(URI.create("http://localhost:5000/automated-export-system/test-only/delete-all"))
      .GET()
      .build()
    httpClient.send(request, HttpResponse.BodyHandlers.discarding())
    ()
  }

}

trait BaseSpec
    extends AnyFeatureSpec
    with GivenWhenThen
    with Matchers
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  override def beforeEach(): Unit = {
    TestDataCleanup.runOnce
    startBrowser()
    Driver.instance.manage().deleteAllCookies()
  }

  override def afterEach(): Unit =
    quitBrowser()

}
