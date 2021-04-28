package org.ga4gh.starterkit.passport.model;

import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class PassportVisaTest {

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {
                "670cc2e7-9a9c-4273-9334-beb40d364e5c",
                "Controlled Datasets Dev",
                new ArrayList<PassportVisaAssertion>() {{
                    add(new PassportVisaAssertion(0L, "active"));
                }}
            }
        };
    }

    @Test(dataProvider = "cases")
    public void testPassportVisaNoArgsConstructor(
        String id, String visaName, List<PassportVisaAssertion> passportVisaAssertions
    ) {
        PassportVisa visa = new PassportVisa();
        visa.setId(id);
        visa.setVisaName(visaName);
        visa.setPassportVisaAssertions(passportVisaAssertions);

        Assert.assertEquals(visa.getId(), id);
        Assert.assertEquals(visa.getVisaName(), visaName);
        Assert.assertEquals(visa.getPassportVisaAssertions(), passportVisaAssertions);
    }

    @Test(dataProvider = "cases")
    public void testPassportVisaAllArgsConstructor(
        String id, String visaName, List<PassportVisaAssertion> passportVisaAssertions
    ) {
        PassportVisa visa = new PassportVisa(id, visaName);
        visa.setPassportVisaAssertions(passportVisaAssertions);

        Assert.assertEquals(visa.getId(), id);
        Assert.assertEquals(visa.getVisaName(), visaName);
        Assert.assertEquals(visa.getPassportVisaAssertions(), passportVisaAssertions);
    }
}
