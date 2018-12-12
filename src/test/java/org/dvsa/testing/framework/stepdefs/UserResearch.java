package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import activesupport.aws.s3.S3;
import activesupport.driver.Browser;
import activesupport.number.Int;
import activesupport.string.Str;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java8.En;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;
import org.dvsa.testing.framework.runner.Hooks;
import org.dvsa.testing.lib.pages.BasePage;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Scanner;

import static org.dvsa.testing.framework.Utils.Generic.GenericUtils.getRandomNumberInts;

public class UserResearch extends BasePage implements En {
    String fileName = "src/test/resources/";

    public UserResearch(World world) {
        Given("^^I have applied for \"([^\"]*)\" \"([^\"]*)\" licences$", (String licenceType, String operator) -> {
            world.APIJourneySteps.registerAndGetUserDetails();
            world.createLicence.setNoOfVehiclesRequired(3);
            for (int i = 0; i < trafficAreaList().length - 1; ) {
                for (String ta : trafficAreaList()) {
                    System.out.println(ta);
                    world.createLicence.setPostcode(postCodes(ta));
                    world.createLicence.setOperatorType(operator);
                    world.createLicence.setLicenceType(licenceType);
                    world.createLicence.setTrafficArea(ta);
                    world.createLicence.setEnforcementArea(enforcementArea(ta));
                    world.APIJourneySteps.createApplication();
                    world.APIJourneySteps.submitApplication();
                    world.APIJourneySteps.grandLicenceAndPayFees();
                    world.createLicence.setApplicationNumber(null);
                    i++;
                }
            }
        });

        Given("^I have applied for \"([^\"]*)\" \"([^\"]*)\" TM application$", (String licenceType, String operator) -> {
            String password;
            world.APIJourneySteps.registerAndGetUserDetails();
            world.createLicence.setNoOfVehiclesRequired(3);
            for (int i = 0; i < trafficAreaList().length - 1; ) {
                for (String ta : trafficAreaList()) {
                    world.createLicence.setPostcode(postCodes(ta));
                    world.createLicence.setOperatorType(operator);
                    world.createLicence.setLicenceType(licenceType);
                    world.createLicence.setTrafficArea(ta);
                    world.createLicence.setEnforcementArea(enforcementArea(ta));
                    world.APIJourneySteps.createApplication();
                    String randWord = Str.randomWord(4);
                    String externalTmUserName;
                    externalTmUserName = "apiTM".concat(randWord);
                    world.createLicence.setForeName("External");
                    world.createLicence.setFamilyName("TM");
                    world.createLicence.setTmUserName(externalTmUserName);
                    world.createLicence.setTransManEmailAddress("externalTM".concat(String.valueOf(getRandomNumberInts(0, 99999))).concat("@vol.org"));
                    world.createLicence.addTransportManager();
                    password = S3.getTempPassword(world.createLicence.getTransManEmailAddress());
                    world.genericUtils.writeToFile(world.createLicence.getTmUserName(), password, fileName.concat("TM.csv"));
                    world.createLicence.setApplicationNumber(null);
                    i++;
                }
            }
        });
        Then("^the licence should be created and granted$", () -> {
            world.genericUtils.writeToFile(world.createLicence.getLoginId(), world.UIJourneySteps.getExternalPassword(), fileName.concat("Operator.csv"));
        });

        Then("^i should be able login as a TM$", () -> {
            Reader in = new FileReader(fileName.concat("TM.csv"));
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader().parse(in);
            for (CSVRecord record : records) {
                String username = record.get("Username");
                String password = record.get("Password");
                world.UIJourneySteps.navigateToExternalUserLogin(username, password);
                world.genericUtils.writeToFile(world.createLicence.getLoginId(), world.UIJourneySteps.getExternalPassword(), fileName.concat("TmUsers.csv"));
            }
        });
        File file = new File(fileName.concat("Operator.csv"));
        if(file.delete()){
            System.out.println("Gooooooooooooooooooooooooooooone");
        }
    }

    private String[] trafficAreaList() {
        return new String[]{"B"};
    }

    private String postCodes(String trafficArea) {
        String postCode;
        switch (trafficArea) {
            case "B":
                postCode = "BD162UA";
                break;
            case "C":
                postCode = "M446TL";
                break;
            case "D":
                postCode = "B440TA";
                break;
            case "F":
                postCode = "IP138ES";
                break;
            case "G":
                postCode = "CF116EE";
                break;
            case "H":
                postCode = "OX11BY";
                break;
            case "K":
                postCode = "E72EW";
                break;
            case "M":
                postCode = "EH139DY";
                break;
            default:
                postCode = "NG23HX";
        }
        return postCode;
    }

    private String enforcementArea(String trafficArea) {
        String enforcementArea;
        switch (trafficArea) {
            case "B":
                enforcementArea = "EA-B";
                break;
            case "C":
                enforcementArea = "EA-C";
                break;
            case "D":
                enforcementArea = "EA-D";
                break;
            case "F":
                enforcementArea = "EA-F";
                break;
            case "G":
                enforcementArea = "EA-E";
                break;
            case "H":
                enforcementArea = "EA-J";
                break;
            case "K":
                enforcementArea = "EA-H";
                break;
            case "M":
                enforcementArea = "EA-A";
                break;
            default:
                enforcementArea = "EA-D";
        }
        return enforcementArea;
    }
}