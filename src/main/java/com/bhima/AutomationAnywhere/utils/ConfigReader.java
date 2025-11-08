package com.bhima.AutomationAnywhere.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties props = new Properties();
    static {
        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) props.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) { return props.getProperty(key); }

    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}
