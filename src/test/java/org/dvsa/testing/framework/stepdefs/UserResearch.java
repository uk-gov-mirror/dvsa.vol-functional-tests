package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import activesupport.number.Int;
import activesupport.string.Str;
import cucumber.api.PendingException;
import cucumber.api.java8.En;

public class UserResearch implements En {
    public UserResearch(World world) {
        Given("^^I have applied for \"([^\"]*)\" \"([^\"]*)\" licences$", (String licenceType, String operator) -> {
            world.createLicence.registerUser();
            world.createLicence.getUserDetails();
            world.createLicence.setNoOfVehiclesRequired(3);
            for (String ta : trafficAreaList()) {
                for (int i = 0; i < trafficAreaList().length - 1; ) {
                    if (world.createLicence.getApplicationNumber() == null) {
                        System.out.println(ta + "=========================");
                        System.out.println(i + "==========");
                        world.createLicence.setPostcode(postCodes(ta));
                        world.createLicence.setOperatorType(operator);
                        world.createLicence.setLicenceType(licenceType);
                        world.createLicence.setTrafficArea(ta);
                        world.createLicence.setEnforcementArea(enforcementArea(ta));
                        world.APIJourneySteps.registerAndGetUserDetails();
                        world.APIJourneySteps.createApplication();
                        world.APIJourneySteps.submitApplication();
                        i++;
                    }
                }
            }
        });
        And("^the licence has been submitted and granted$", () -> {
            world.APIJourneySteps.grandLicenceAndPayFees();
            world.createLicence.setApplicationNumber(null);
        });
        And("^i add an external TM$", () -> {
            String randWord = Str.randomWord(4);
            String externalTmUserName;
            externalTmUserName = "apiTM".concat(randWord);
            world.createLicence.setForeName("External");
            world.createLicence.setFamilyName("TM");
            world.createLicence.setTmUserName(externalTmUserName);
            world.createLicence.addTransportManager();
        });
    }

    private String[] trafficAreaList() {
        return new String[]{"B", "C", "D", "F", "G", "H", "K", "M"};
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