package org.ga4gh.starterkit.passport.model;

import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class PassportUserTest {

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {
                "85ff5a54-48b9-4294-a91d-2be50bd2a77d",
                "aresearcher",
                "Ana",
                "Researcher",
                "ana.researcher@institute.org",
                "hOZHxY0qEv1f20BMHUklpS4OywV4YXu1ZzjRO8LkHbdLjX7FF0C75N5fqRg9sqvI",
                "60f77e6b5826e56ba8b2456557a3f877a6a94c19c4b427d1ce54d4e92fd13c90",
                new ArrayList<PassportVisaAssertion>() {{
                    add(new PassportVisaAssertion(0L, "active"));
                }}
            }
        };
    }

    @Test(dataProvider = "cases")
    public void testPassportUserNoArgsConstructor(
        String id, String username, String firstName, String lastName,
        String email, String passwordSalt, String passwordHash,
        List<PassportVisaAssertion> passportVisaAssertions
    ) {
        PassportUser user = new PassportUser();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPasswordSalt(passwordSalt);
        user.setPasswordHash(passwordHash);
        user.setPassportVisaAssertions(passportVisaAssertions);

        Assert.assertEquals(user.getId(), id);
        Assert.assertEquals(user.getUsername(), username);
        Assert.assertEquals(user.getFirstName(), firstName);
        Assert.assertEquals(user.getLastName(), lastName);
        Assert.assertEquals(user.getEmail(), email);
        Assert.assertEquals(user.getPasswordSalt(), passwordSalt);
        Assert.assertEquals(user.getPasswordHash(), passwordHash);
        Assert.assertEquals(user.getPassportVisaAssertions(), passportVisaAssertions);
    }

    @Test(dataProvider = "cases")
    public void testPassportUserAllArgsConstructor(
        String id, String username, String firstName, String lastName,
        String email, String passwordSalt, String passwordHash,
        List<PassportVisaAssertion> passportVisaAssertions
    ) {
        PassportUser user = new PassportUser(id, username, firstName, lastName, email, passwordSalt, passwordHash);
        user.setPassportVisaAssertions(passportVisaAssertions);
        
        Assert.assertEquals(user.getId(), id);
        Assert.assertEquals(user.getUsername(), username);
        Assert.assertEquals(user.getFirstName(), firstName);
        Assert.assertEquals(user.getLastName(), lastName);
        Assert.assertEquals(user.getEmail(), email);
        Assert.assertEquals(user.getPasswordSalt(), passwordSalt);
        Assert.assertEquals(user.getPasswordHash(), passwordHash);
        Assert.assertEquals(user.getPassportVisaAssertions(), passportVisaAssertions);
    }
}
