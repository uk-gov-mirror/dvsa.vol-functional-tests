package org.dvsa.testing.framework.runner;

import activesupport.IllegalBrowserException;
import cucumber.api.Scenario;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import activesupport.driver.Browser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;

public class Hooks {

    private static File directory = new File("img");

    private void createDirectory() throws IOException {
        FileUtils.forceMkdir(directory);
    }


    @Attachment(value = "Screenshot on failure", type = "image/png")
    public byte[] attach(Scenario scenario) throws IOException, IllegalBrowserException {
        createDirectory();
        File screenshot = new File(String.format(directory + "/error%s.png", Instant.now().getEpochSecond()));
        byte[] attachment = new byte[0];
        if (scenario.isFailed()) {
            FileOutputStream screenshotStream = new FileOutputStream(screenshot);
            attachment = ((TakesScreenshot) Browser.navigate())
                    .getScreenshotAs(OutputType.BYTES);
            screenshotStream.write(attachment);
            screenshotStream.close();
        }
        return attachment;
    }

    private void deleteDirectory() throws IOException {
        FileUtils.deleteDirectory(directory);
    }

    @AfterAll
    public void tearDown() throws IOException {
        if (Browser.isBrowserOpen()) {
            Browser.quit();
            deleteDirectory();
        }
    }
}