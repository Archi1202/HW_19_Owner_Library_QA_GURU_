package config;

import com.codeborne.selenide.Configuration;
import config.web.WebDriverConfig;
import io.restassured.RestAssured;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ProjectConfiguration {
    private final WebDriverConfig webDriverConfig;

    public ProjectConfiguration(config.web.WebDriverConfig webDriverConfig) {
        this.webDriverConfig = webDriverConfig;
    }

    public void apiConfig(){
        RestAssured.baseURI = webDriverConfig.baseUrl();
    }

    public void webConfig(){
        Configuration.baseUrl = webDriverConfig.baseUrl();
        Configuration.browser = webDriverConfig.browserName().toString();
        Configuration.browserVersion = webDriverConfig.browserVersion();
        Configuration.browserSize = webDriverConfig.browserSize();
        if (webDriverConfig.isRemote()) {
            com.codeborne.selenide.Configuration.remote = webDriverConfig.remoteURL().toString();
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", true);
        }

    }

}
