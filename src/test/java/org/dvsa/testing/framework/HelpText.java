package org.dvsa.testing.framework;

import java.util.regex.Pattern;

public class HelpText {

    public static Pattern checkEmail() {
        return
            Pattern.compile("We have sent an email to .+\\@.+\\..+ containing a temporary password. Once you’ve signed in using the temporary password you will need to create a new password");
    }

    public static String signingInProblems() {
        return "If our email does not appear in your inbox within 5 minutes, check your Spam or Junk folder or contact your IT department for help. If our email is in your Spam or Junk folder, please change your email settings so that further emails from us are directed to your inbox. If you have issues signing in or didn’t receive our email you will need to contact notifications@vehicle-operator-licensing.service.gov.uk.";
    }
}
