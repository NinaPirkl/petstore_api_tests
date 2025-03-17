package com.veeam.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.Properties;

public class TestConfiguration {
    private static Properties properties;

    static {
        try (InputStream input = new FileInputStream("src/resources/config.properties")) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("api.url", "https://petstore.swagger.io");
    }
    
    public static int getTimeout() {
        return Integer.parseInt(properties.getProperty("timeout", "5000"));
    }
    
    public static int getRetryCount() {
        return Integer.parseInt(properties.getProperty("retryCount", "3"));
    }
}
