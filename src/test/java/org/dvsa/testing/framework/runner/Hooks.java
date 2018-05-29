package org.dvsa.testing.framework.runner;

import io.qameta.allure.Attachment;
import org.dvsa.testing.lib.browser.Browser;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;

public class Hooks {

    static File directory = new File("img");

    public static void setup(){
        if (System.getProperty("env").isEmpty()){
            System.setProperty("env","da");
            System.setProperty("browser","chrome");
            System.setProperty("licenceType","standard_international");
            System.setProperty("businessType","limited_company");
            System.setProperty("operatorType","goods");
        }
    }

    @Attachment(value = "Screenshot on failure", type = "image/png")
    public byte[] attach() {
        File screenshot = new File(String.format(directory+"/errorScreenShot%s.png",Instant.now().getEpochSecond()));
        try {
            FileOutputStream screenshotStream = new FileOutputStream(screenshot);
            byte[] bytes = ((TakesScreenshot) Browser.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            screenshotStream.write(bytes);
            screenshotStream.close();
            return bytes;
        } catch (IOException unableToWriteScreenshot) {
            System.err.println("Unable to write "
                    + screenshot.getAbsolutePath());
            unableToWriteScreenshot.printStackTrace();
        }
        return null;
    }

    public static void teardown(){
        if(directory.exists()){
            try {
                directory.delete();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}