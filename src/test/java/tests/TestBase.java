package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import helpers.Attach;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

public class TestBase {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://demoqa.com/";
        Configuration.pageLoadStrategy = "eager";
        if (System.getProperty("remote") != null) {
            Configuration.remote = "https://" + System.getProperty("login") + "@" + System.getProperty("remote");
        } else {
            Configuration.remote = null;
        }
        Configuration.timeout = 10000;
        Configuration.browser = System.getProperty("browserName","chrome");
        Configuration.browserSize = System.getProperty("browserSize");
        Configuration.browserVersion = System.getProperty("browserVersion");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;
    }

    @AfterEach
    protected void tearDown() {
        if (System.getProperty("remote") != null) {
            Attach.screenshotAs("Last Screenshot");
            Attach.pageSource();
            Attach.browserConsoleLogs();
            Attach.addVideo();
        }
        Selenide.closeWebDriver();
    }
}