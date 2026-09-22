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

import java.util.UUID

object TestDataGenerators {

  // UUID-based to avoid collisions across parallel/repeated test runs,
  // since test-only/delete-all currently 404s and never cleans up old data
  def generateMrn(): String = {
    val digits = UUID.randomUUID().toString.replace("-", "").filter(_.isDigit).take(12).padTo(12, '0')
    s"26GB${digits}A9"
  }

  def generateEori(): String = {
    val digits = UUID.randomUUID().toString.replace("-", "").filter(_.isDigit).take(9).padTo(9, '0')
    s"GB$digits"
  }
}