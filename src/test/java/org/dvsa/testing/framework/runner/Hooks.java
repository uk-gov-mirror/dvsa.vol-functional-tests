package org.dvsa.testing.framework.runner;

import io.qameta.allure.Attachment;
import org.dvsa.testing.lib.browser.Browser;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.FileOutputStream;
import java.time.Instant;

public class Hooks {

    static File directory = new File("img");

    public void main(String[] args) {
        attach();
        teardown();
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
        try {
            if (Browser.isInitialised()) {
                FileOutputStream screenshotStream = new FileOutputStream(screenshot);
                byte[] bytes = ((TakesScreenshot) Browser.getDriver())
                        .getScreenshotAs(OutputType.BYTES);
                screenshotStream.write(bytes);
                screenshotStream.close();
                return bytes;
            }
        } catch (Exception e) {
            System.err.println("Unable to write "
                    + screenshot.getAbsolutePath());
            e.printStackTrace();
        }
        return null;
    }

    private void teardown() {
        if (directory.exists()) {
            try {
                directory.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (Browser.isInitialised()) {
            Browser.quit();
        }
    }
}