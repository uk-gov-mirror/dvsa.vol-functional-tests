package org.dvsa.testing.framework.runner;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.*;
import java.time.Instant;
import java.util.Properties;

import static org.dvsa.testing.lib.browser.Browser.getDriver;


public class Hooks {

    static File directory = new File("img");


    public static void main(String[] args) throws IOException {

        String filename = "src/test/resources/config.properties";

        FileInputStream propFile =
                new FileInputStream( filename);
        Properties p =
                new Properties(System.getProperties());
        p.load(propFile);

        // set the system properties
        System.setProperties(p);

    }

    @Attachment(value = "Screenshot on failure", type = "image/png")
    public byte[] attach() {
        File screenshot = new File(String.format(directory + "/errorScreenShot%s.png", Instant.now().getEpochSecond()));
        try {
            FileOutputStream screenshotStream = new FileOutputStream(screenshot);
            byte[] bytes = ((TakesScreenshot) getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            screenshotStream.write(bytes);
            screenshotStream.close();
            return bytes;
        } catch (Exception e) {
            System.err.println("Unable to write "
                    + screenshot.getAbsolutePath());
            e.printStackTrace();
        }
        return null;
    }

    public static void teardown() {
        if (directory.exists()) {
            try {
                directory.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}