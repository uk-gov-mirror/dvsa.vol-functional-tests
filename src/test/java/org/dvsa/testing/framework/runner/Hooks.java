package org.dvsa.testing.framework.runner;

import activesupport.IllegalBrowserException;
import activesupport.MissingDriverException;
import activesupport.driver.Browser;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.FileOutputStream;
import java.time.Instant;

public class Hooks {

    static File directory = new File("img");

    public void main(String[] args) throws MissingDriverException, IllegalBrowserException {
        attach();
        teardown();
        closeBrowser();
    }

    private void createDirectory() {
        if (!directory.exists()) {
            directory.mkdir();
        } else {
            System.out.println(directory + " folder already exists");
        }
    }

    @Attachment(value = "Screenshot on failure", type = "image/png")
    public byte[] attach() {
        createDirectory();
        File screenshot = new File(String.format(directory + "/errorScreenShot%s.png", Instant.now().getEpochSecond()));
        byte[] bytes = new byte[0];
        try {

            Browser.navigate();
            if (Browser.isBrowserOpen()) {
                FileOutputStream screenshotStream = new FileOutputStream(screenshot);
                bytes = ((TakesScreenshot) Browser.navigate())
                        .getScreenshotAs(OutputType.BYTES);
                screenshotStream.write(bytes);
                screenshotStream.close();
            }
        } catch (Exception e) {
            System.err.println("Unable to write "
                    + screenshot.getAbsolutePath());
            e.printStackTrace();
        }
        return bytes;
    }

    private void teardown() {
        if (directory.exists()) {
            try {
                directory.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void closeBrowser() throws IllegalBrowserException, MissingDriverException {
        if (Browser.isBrowserOpen()) {
            Browser.quit();
        }
    }
}