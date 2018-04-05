package org.dvsa.testing.framework.stepdefs;

import activesupport.aws.s3.S3;
import activesupport.database.DBUnit;
import activesupport.file.Files;
import activesupport.jenkins.Jenkins;
import activesupport.jenkins.JenkinsParameterKey;
import activesupport.system.Properties;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java8.En;
import org.dbunit.dataset.IDataSet;
import org.junit.Assert;

import java.nio.file.Paths;
import java.util.HashMap;

public class NIDataExport {

    @Given("^Northern Ireland goods vehicles data has been exported$")
    public void northernIrelandGoodsVehiclesDataHasBeenExported() throws Throwable {
        HashMap<String, String> jenkinsParams = new HashMap<>();
        jenkinsParams.put(JenkinsParameterKey.NODE.toString(), String.format("api&&%s&&olcs", Properties.get("env", true)));
        jenkinsParams.put(JenkinsParameterKey.REPORT.toString(), "ni-operator-licence");

        Jenkins.trigger(Jenkins.Job.NI_EXPORT, jenkinsParams);
    }

    @When("^I download the generated CSV file for Northern Ireland$")
    public void iDownloadTheGeneratedCSVFileForNorthernIreland() {
        // Here for eligibility purposes
    }

    @And("^check the contents of the CSV file$")
    public void checkTheContentsOfTheCSVFile() {
        // Here for eligibility purposes
    }

    @Then("^I expect to only see Northern Ireland goods vehicle data$")
    public void iExpectToOnlySeeNorthernIrelandGoodsVehicleData() throws Throwable {
        String tableName = "OLCS_RDS_OLCSDB.data_dva_ni_operator_licence_view";
        String fileName = tableName + ".csv";
        String CSVDirectory = String.format("%s/target/resources/", System.getProperty("user.dir"));
        String tableOrderingFile = CSVDirectory + "table-ordering.txt";
        String NIDataFilePath = CSVDirectory + fileName;

        Files.delete(NIDataFilePath);
        Files.delete(tableOrderingFile);

        Files.write(tableOrderingFile, tableName);
        Files.write(Paths.get(NIDataFilePath), S3.getLatestNIGVExportContents());

        String query = "SELECT * FROM OLCS_RDS_OLCSDB.data_dva_ni_operator_licence_view";

        IDataSet expectedDBValues = DBUnit.readCSV(Paths.get(NIDataFilePath).toFile());
        IDataSet actualDBValues = DBUnit.queryDatabase(query, tableName);

        Assert.assertTrue(DBUnit.equals(expectedDBValues, actualDBValues, tableName));
    }

}
