package config;

import org.aeonbits.owner.Config;

import java.net.URL;

@Config.Sources({
        "classpath:properties/${env}.properties",
        "classpath:properties/local.properties"
})

public interface WebDriverConfig extends Config {

    @Key("browser.name")
    @Config.DefaultValue("CHROME")
    String getBrowserName();

    @Key("browser.version")
    @Config.DefaultValue("125.0")
    String getBrowserVersion();

    @Key("browser.size")
    @Config.DefaultValue("1920x1080")
    String getBrowserSize();

    @Key("remoteUrl")
    URL getRemoteURL();


}
