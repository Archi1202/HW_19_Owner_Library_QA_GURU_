package config;

import com.codeborne.selenide.Configuration;
import config.web.WebDriverConfig;
import io.restassured.RestAssured;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

public class ProjectConfiguration {
    private final WebDriverConfig webDriverConfig;

    public ProjectConfiguration(WebDriverConfig webDriverConfig) {
        this.webDriverConfig = webDriverConfig;
    }

    public void configureApi() {
        RestAssured.baseURI = webDriverConfig.baseUrl();
    }

    public void configureWeb() {
        Configuration.baseUrl = webDriverConfig.baseUrl();
        Configuration.browser = webDriverConfig.browserName().toString();
        Configuration.browserVersion = webDriverConfig.browserVersion();
        Configuration.browserSize = webDriverConfig.browserSize();

        if (webDriverConfig.remoteURL() != null) {
            Configuration.remote = webDriverConfig.remoteURL().toString();
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability
                    ("selenoid:options", Map.<String, Object>of(
                            "enableVNC", true,
                            "enableVideo", true));

            Configuration.browserCapabilities = capabilities;
        }
    }
}
