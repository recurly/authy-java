package com.authy.api;

import com.authy.AuthyUtil;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by sanjeev on 5/5/17.
 */
public class TestUser {
    private static Properties properties;
    private static com.authy.api.Users subject;


    @Before
    public void setUp() throws IOException {

        properties = AuthyUtil.loadProperties("test.properties", TestUser.class);

        // Let's configure the API Client with the properties defined at the test.properties file.
        org.junit.Assert.assertNotNull(properties.getProperty("api_key"));
        org.junit.Assert.assertNotNull(properties.getProperty("api_url"));

        subject = new com.authy.api.Users(properties.getProperty("api_url"), properties.getProperty("api_key"), true);
    }

    @Test
    public void itTestsCreateUser() {
        com.authy.api.User result = subject.createUser(properties.getProperty("user_test_email"),
                properties.getProperty("phone_verification_test_number"), "1");

        Assert.assertEquals(true, result.isOk());
        Assert.assertEquals("User created successfully.", result.getMessage());
        Assert.assertEquals(null, result.getError());
        Assert.assertEquals(200, result.getStatus());
    }

    @Test
    public void itTestsCreateInvalidUser() {
        com.authy.api.User result = subject.createUser("user_test_email", "1234", "1");

        Assert.assertEquals(false, result.isOk());
        Assert.assertEquals(true, result.getError().getMessage().contains("User was not valid"));
        Assert.assertEquals("60027", result.getError().getErrorCode());
        Assert.assertEquals(400, result.getStatus());
    }
}
