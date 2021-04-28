package org.ga4gh.starterkit.passport.controller;

import org.ga4gh.starterkit.passport.App;
import org.ga4gh.starterkit.passport.AppConfig;
import org.ga4gh.starterkit.passport.model.PassportVisa;
import org.ga4gh.starterkit.passport.utils.hibernate.PassportHibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import testutils.ResourceLoader;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ContextConfiguration(classes = {App.class, AppConfig.class, Visas.class})
@WebAppConfiguration
public class VisasTest extends AbstractTestNGSpringContextTests {

    private static final String API_PREFIX = "/admin/ga4gh/passport/v1/visas";

    private static final String RESPONSE_DIR = "/responses/visas/";

    private static final String PAYLOAD_DIR = "/payloads/visas/";

    @Autowired
    private PassportHibernateUtil hibernateUtil;

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @BeforeMethod
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @DataProvider(name = "indexVisasCases")
    public Object[][] indexVisasCases() {
        return new Object[][] {
            {
                status().isOk(),
                true,
                "00.json",
                null
            }
        };
    }

    @DataProvider(name = "showVisaCases")
    public Object[][] showVisaCases() {
        return new Object[][] {
            {
                "670cc2e7-9a9c-4273-9334-beb40d364e5c",
                status().isOk(),
                true,
                "00.json",
                null
            },
            {
                "00000000-0000-0000-0000-000000000000",
                status().isNotFound(),
                false,
                null,
                "No PassportVisa exists at id 00000000-0000-0000-0000-000000000000"
            }
        };
    }

    @DataProvider(name = "createVisaCases")
    public Object[][] createVisaCases() {
        return new Object[][] {
            {
                "00.json",
                status().isOk(),
                true,
                "00.json",
                null
            },
            {
                "00.json",
                status().isConflict(),
                false,
                null,
                "A(n) PassportVisa already exists at id 407e4891-2463-4f6f-b428-fe0f1850cf0c"
            }
        };
    }

    @DataProvider(name = "updateVisaCases")
    public Object[][] updateVisaCases() {
        return new Object[][] {
            {
                "407e4891-2463-4f6f-b428-fe0f1850cf0c",
                "00.json",
                status().isOk(),
                true,
                "00.json",
                null
            },
            {
                "670cc2e7-9a9c-4273-9334-beb40d364e5c",
                "00.json",
                status().isBadRequest(),
                false,
                null,
                "Update requested at id 670cc2e7-9a9c-4273-9334-beb40d364e5c, but new PassportVisa has an id of 407e4891-2463-4f6f-b428-fe0f1850cf0c"
            },
            {
                "00000000-0000-0000-0000-000000000000",
                "01.json",
                status().isConflict(),
                false,
                null,
                "No PassportVisa at id 00000000-0000-0000-0000-000000000000"
            }
        };
    }

    @DataProvider(name = "deleteVisaCases")
    public Object[][] deleteVisaCases() {
        return new Object[][] {
            {
                "407e4891-2463-4f6f-b428-fe0f1850cf0c",
                status().isOk(),
                true,
                null
            },
            {
                "407e4891-2463-4f6f-b428-fe0f1850cf0c",
                status().isConflict(),
                false,
                "No PassportVisa at id 407e4891-2463-4f6f-b428-fe0f1850cf0c"
            }
        };
    }

    private void createTestEntities() throws Exception {
        String[] payloadFiles = {
            "/payloads/visas/create/00.json"
        };
        for (String payloadFile: payloadFiles) {
            String payloadBody = ResourceLoader.load(payloadFile);
            ObjectMapper objectMapper = new ObjectMapper();
            PassportVisa passportVisa = objectMapper.readValue(payloadBody, PassportVisa.class);
            hibernateUtil.createEntityObject(PassportVisa.class, passportVisa);
        }
    }

    private void deleteTestEntities() throws Exception {
        hibernateUtil.deleteEntityObject(PassportVisa.class, "407e4891-2463-4f6f-b428-fe0f1850cf0c");
    }

    @AfterGroups("createVisa")
    public void cleanupCreate() throws Exception {
        deleteTestEntities();
    }

    @BeforeGroups("updateVisa")
    public void setupUpdate() throws Exception {
        createTestEntities();
    }

    @AfterGroups("updateVisa")
    public void cleanupUpdate() throws Exception {
        deleteTestEntities();
    }

    @BeforeGroups("deleteVisa")
    public void setupDelete() throws Exception {
        createTestEntities();
    }

    private void genericAdminApiRequestTest(MvcResult result, boolean expSuccess, String expSubdir, String expFilename, String expMessage) throws Exception {
        if (expSuccess) {
            String responseBody = result.getResponse().getContentAsString();
            String expFile = RESPONSE_DIR + expSubdir + "/" + expFilename;
            String expResponseBody = ResourceLoader.load(expFile);
            Assert.assertEquals(responseBody, expResponseBody);
        } else {
            String message = result.getResolvedException().getMessage();
            Assert.assertEquals(message, expMessage);
        }
    }

    @Test(dataProvider = "indexVisasCases", groups = "indexVisa")
    public void testGetPassportVisas(ResultMatcher expStatus, boolean expSuccess, String expFilename, String expMessage) throws Exception {
        MvcResult result = mockMvc.perform(get(API_PREFIX))
            .andExpect(expStatus)
            .andReturn();
        genericAdminApiRequestTest(result, expSuccess, "index", expFilename, expMessage);
    }

    @Test(dataProvider = "showVisaCases", groups = "showVisa")
    public void testShowPassportVisa(String id, ResultMatcher expStatus, boolean expSuccess, String expFilename, String expMessage) throws Exception {
        MvcResult result = mockMvc.perform(get(API_PREFIX + "/" + id))
            .andExpect(expStatus)
            .andReturn();
        genericAdminApiRequestTest(result, expSuccess, "show", expFilename, expMessage);
    }

    @Test(dataProvider = "createVisaCases", groups = "createVisa")
    public void testCreatePassportVisa(String payloadFilename, ResultMatcher expStatus, boolean expSuccess, String expFilename, String expMessage) throws Exception {
        String expSubdir = "create";
        String payloadFile = PAYLOAD_DIR + expSubdir + "/" + payloadFilename;
        String payloadBody = ResourceLoader.load(payloadFile);

        MvcResult result = mockMvc.perform(
            post(API_PREFIX)
            .content(payloadBody)
            .header("Content-Type", "application/json")
        )
            .andExpect(expStatus)
            .andReturn();
        genericAdminApiRequestTest(result, expSuccess, expSubdir, expFilename, expMessage);
    }

    @Test(dataProvider = "updateVisaCases", groups = "updateVisa")
    public void testUpdatePassportVisa(String id, String payloadFilename, ResultMatcher expStatus, boolean expSuccess, String expFilename, String expMessage) throws Exception {
        String expSubdir = "update";
        String payloadFile = PAYLOAD_DIR + expSubdir + "/" + payloadFilename;
        String payloadBody = ResourceLoader.load(payloadFile);

        MvcResult result = mockMvc.perform(
            put(API_PREFIX + "/" + id)
            .content(payloadBody)
            .header("Content-Type", "application/json")
        )
            .andExpect(expStatus)
            .andReturn();
        genericAdminApiRequestTest(result, expSuccess, expSubdir, expFilename, expMessage);
    }

    @Test(dataProvider = "deleteVisaCases", groups = "deleteVisa")
    public void testDeletePassportVisa(String id, ResultMatcher expStatus, boolean expSuccess, String expMessage) throws Exception {
        MvcResult result = mockMvc.perform(
            delete(API_PREFIX + "/" + id)
        )
            .andExpect(expStatus)
            .andReturn();
        if (!expSuccess) {
            String message = result.getResolvedException().getMessage();
            Assert.assertEquals(message, expMessage);
        }
    }
}
