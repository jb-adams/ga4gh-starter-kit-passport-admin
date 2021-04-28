package org.ga4gh.starterkit.passport.model;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class PassportVisaAssertionTest {

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {
                0L,
                "active",
                new PassportUser() {{
                    setId("85ff5a54-48b9-4294-a91d-2be50bd2a77d");
                }},
                new PassportVisa() {{
                    setId("670cc2e7-9a9c-4273-9334-beb40d364e5c");
                }}
            }
        };
    }

    @Test(dataProvider = "cases")
    public void testPassportVisaAssertionNoArgsConstructor(
        Long id, String status, PassportUser passportUser, PassportVisa passportVisa
    ) {
        PassportVisaAssertion assertion = new PassportVisaAssertion();
        assertion.setId(id);
        assertion.setStatus(status);
        assertion.setPassportUser(passportUser);
        assertion.setPassportVisa(passportVisa);

        Assert.assertEquals(assertion.getId(), id);
        Assert.assertEquals(assertion.getStatus(), status);
        Assert.assertEquals(assertion.getPassportUser(), passportUser);
        Assert.assertEquals(assertion.getPassportVisa(), passportVisa);
    }

    @Test(dataProvider = "cases")
    public void testPassportVisaAssertionAllArgsConstructor(
        Long id, String status, PassportUser passportUser, PassportVisa passportVisa
    ) {
        PassportVisaAssertion assertion = new PassportVisaAssertion(id, status);
        assertion.setPassportUser(passportUser);
        assertion.setPassportVisa(passportVisa);

        Assert.assertEquals(assertion.getId(), id);
        Assert.assertEquals(assertion.getStatus(), status);
        Assert.assertEquals(assertion.getPassportUser(), passportUser);
        Assert.assertEquals(assertion.getPassportVisa(), passportVisa);

    }
    
}
