package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.ConfigReader;
import config.ProjectConfiguration;
import config.web.WebDriverConfig;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    private static final WebDriverConfig webDriverConfig = ConfigReader.Instance.read();

    @BeforeAll
    static void setUp() {

        Configuration.pageLoadStrategy = "eager";

        ProjectConfiguration projectConfiguration = new ProjectConfiguration(webDriverConfig);
        projectConfiguration.apiConfig();
        projectConfiguration.webConfig();

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
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