package org.dvsa.testing.framework.stepdefs.dataretention;

import activesupport.database.DBUnit;
import activesupport.jenkins.Jenkins;
import activesupport.jenkins.JenkinsParameterKey;
import activesupport.system.Properties;
import cucumber.api.java8.En;
import org.dbunit.dataset.ITable;
import org.dvsa.testing.framework.DRActionType;
import org.dvsa.testing.framework.DRQuery;
import org.dvsa.testing.framework.util.World;
import org.dvsa.testing.lib.pages.enums.AdminOption;
import org.dvsa.testing.lib.pages.enums.dataretention.ActionType;
import org.dvsa.testing.lib.pages.internal.NavigationBar;
import org.dvsa.testing.lib.pages.internal.dataretention.DataRetentionPage;
import org.dvsa.testing.lib.pages.internal.dataretention.RecordsPage;
import org.dvsa.testing.lib.pages.internal.dataretention.ReviewPage;
import org.dvsa.testing.lib.pages.internal.dataretention.RuleAdminPage;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;

public class Common implements En {

    private World WORLD;

    public Common(World WORLD) {
        this.WORLD = WORLD;

        Given("the data retention job for (populate|delete) has been ran", (String DRAction) -> {
            HashMap<String, String> jenkinsParams = new HashMap<>();
            jenkinsParams.put(JenkinsParameterKey.NODE.toString(), String.format("%s&&api&&olcs", Properties.get("env", true)));
            jenkinsParams.put(JenkinsParameterKey.COMMAND.toString(), "data-retention-rule " + DRAction);

            Jenkins.trigger(Jenkins.Job.BATCH_RUN_CLI, jenkinsParams);
        });

        Then("^I expect records that met the criteria for (.*) of action type (review|automate) to be present in the populate$", (String DRRule, String actionType) -> {
            ITable expectedDRPopulateTable = DBUnit.queryDatabase(
                    DRQuery.getEnum(DRRule).getQuery(DRActionType.getEnum(actionType)),
                    "expected"
            ).getTable("expected");

            ITable actualDRPopulateTable = DBUnit.queryDatabase(
                    DRQuery.POPULATE.getQuery((String) WORLD.get("rule_id")),
                    "actual"
            ).getTable("actual");

            int numRows = actualDRPopulateTable.getRowCount();

            for (int i = 0; i <= numRows; i++) {
                String expectedEntityPK = String.valueOf(actualDRPopulateTable.getValue(i, "entity_pk"));
                boolean entityPKFound = DBUnit.include(expectedDRPopulateTable, "entity_pk", expectedEntityPK);

                Assert.assertTrue(entityPKFound);
            }

        });

        Then("^I expect to see the records shown in review records in the data_retention table$", () -> {
            ITable actualDRPopulateTable = DBUnit.queryDatabase(DRQuery.POPULATE.getQuery((String) WORLD.get("rule_id")), "actual").getTable("actual");
            List<RecordsPage.DREntity> DREntries = (List<RecordsPage.DREntity>) WORLD.get("dr_entities");

            for (int i = 0; i < DREntries.size(); i++) {
                boolean foundRecord = DBUnit.include(actualDRPopulateTable, "entity_pk", DREntries.get(i).getEntityPK());
                Assert.assertTrue(foundRecord);
            }
        });

        And("^the rule (.*) has been enabled and set to (review|automate)$", (String dataRetentionRule, String DRActionType) -> {
            WORLD.put("dr_rule", dataRetentionRule);
            WORLD.put("dr_action_type", DRActionType);

            NavigationBar.administratorList(AdminOption.DATA_RETENTION);
            DataRetentionPage.ruleAdmin();
            RuleAdminPage.selectRule(dataRetentionRule);
            RuleAdminPage.EditModel.untilOnModel();
            RuleAdminPage.EditModel.enable();
            WORLD.put("rule_id", RuleAdminPage.EditModel.extractRuleID());
            RuleAdminPage.EditModel.actionType(ActionType.getEnum(DRActionType));
            RuleAdminPage.EditModel.save();
        });

        When("^I review the populated records for (.*)$", (String dataRetentionRule) -> {
            DataRetentionPage.review();
            ReviewPage.untilOnPage();
            ReviewPage.selectRule(dataRetentionRule);
            RecordsPage.untilOnPage();
            WORLD.put("dr_entities", RecordsPage.extractRecordIdentifiers());
        });

        Then("^the records that where populated should be deleted from the database$", () -> {
            // Won't do due to change of plan by PO and team.
        });

    }

}
