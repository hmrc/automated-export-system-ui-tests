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

package uk.gov.hmrc.ui.specs
package submission.discrepancies

import uk.gov.hmrc.ui.pages.submission.goods.*
import uk.gov.hmrc.ui.pages.submission.transport.*
import uk.gov.hmrc.ui.util.TestDataGenerators

class SubmissionDiscrepanciesRemoveSpec extends BaseSpec with DiscrepancyJourney {

  Feature("IE507(a) Submission with discrepancies - remove answers") {

    Scenario("E2E Journey: Remove one of two seals and submit the declaration") {
      startJourneyUpToContainerDetails(TestDataGenerators.generateMrn())

      addTransportEquipment("1")
      answerAddAnother(AddAnotherTransportEquipmentPage, "No")

      addSeal("1", "SEAL001")
      answerAddAnother(AddAnotherSealPage, "Yes")
      addSeal("2", "SEAL002")

      When("I click Remove for the 1st seal")
      AddAnotherSealPage.clickRemove("1")

      Then("I am on the remove seal page")
      RemoveSealPage.loadPage("1")

      When("I confirm the removal")
      RemoveSealPage.select("Yes")
      RemoveSealPage.confirm()

      Then("I am back on the add another seal page with 1 seal")
      AddAnotherSealPage.loadPage("1")
      answerAddAnother(AddAnotherSealPage, "No")

      addGoodsReference("1", "123")
      answerAddAnother(AddAnotherGoodsReferencePage, "No")

      enterTransportAndGoodsDetails()
      addPackaging("1", "BX", "1", "MARKS123")
      PackagingSummaryListPage.select("No")
      PackagingSummaryListPage.submitPageByType()

      submitDeclaration()
    }

    Scenario("E2E Journey: Remove one of two Declaration Goods References and submit the declaration") {
      startJourneyUpToContainerDetails(TestDataGenerators.generateMrn())

      addTransportEquipment("1")
      answerAddAnother(AddAnotherTransportEquipmentPage, "No")

      addSeal("1", "SEAL001")
      answerAddAnother(AddAnotherSealPage, "No")

      addGoodsReference("1", "123")
      answerAddAnother(AddAnotherGoodsReferencePage, "Yes")
      addGoodsReference("2", "456")

      When("I click Remove for the 1st Declaration Goods Reference")
      AddAnotherGoodsReferencePage.clickRemove("1")

      Then("I am on the remove Declaration Goods Reference page")
      RemoveGoodsReferencePage.loadPage("1")

      When("I confirm the removal")
      RemoveGoodsReferencePage.select("Yes")
      RemoveGoodsReferencePage.confirm()

      Then("I am back on the add another page with 1 Declaration Goods Reference")
      AddAnotherGoodsReferencePage.loadPage("1")
      answerAddAnother(AddAnotherGoodsReferencePage, "No")

      enterTransportAndGoodsDetails()
      addPackaging("1", "BX", "1", "MARKS123")
      PackagingSummaryListPage.select("No")
      PackagingSummaryListPage.submitPageByType()

      submitDeclaration()
    }

    Scenario("E2E Journey: Remove one of two packaging details and submit the declaration") {
      startJourneyUpToContainerDetails(TestDataGenerators.generateMrn())

      addTransportEquipment("1")
      answerAddAnother(AddAnotherTransportEquipmentPage, "No")

      addSeal("1", "SEAL001")
      answerAddAnother(AddAnotherSealPage, "No")

      addGoodsReference("1", "123")
      answerAddAnother(AddAnotherGoodsReferencePage, "No")

      enterTransportAndGoodsDetails()

      addPackaging("1", "BX", "1", "MARKS123")
      PackagingSummaryListPage.select("Yes")
      PackagingSummaryListPage.submitPageByType()
      addPackaging("2", "CT", "3", "MARKS456")

      When("I click Remove for the 1st packaging detail")
      PackagingSummaryListPage.clickRemove("1")

      Then("I am on the remove packaging detail page")
      RemovePackagingDetailPage.loadPage("1")

      When("I confirm the removal")
      RemovePackagingDetailPage.select("Yes")
      RemovePackagingDetailPage.confirm()

      Then("I am back on the packaging summary list page with 1 packaging detail")
      PackagingSummaryListPage.loadPage("1")
      PackagingSummaryListPage.select("No")
      PackagingSummaryListPage.submitPageByType()

      submitDeclaration()
    }
  }
}
