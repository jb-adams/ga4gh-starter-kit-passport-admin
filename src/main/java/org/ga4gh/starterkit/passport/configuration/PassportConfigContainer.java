package org.ga4gh.starterkit.passport.configuration;

public class PassportConfigContainer {

    private PassportConfig passportConfig;

    public PassportConfigContainer() {
        passportConfig = new PassportConfig();
    }

    public PassportConfigContainer(PassportConfig passportConfig) {
        this.passportConfig = passportConfig;
    }

    public void setPassportConfig(PassportConfig passportConfig) {
        this.passportConfig = passportConfig;
    }

    public PassportConfig getPassportConfig() {
        return passportConfig;
    }
}
