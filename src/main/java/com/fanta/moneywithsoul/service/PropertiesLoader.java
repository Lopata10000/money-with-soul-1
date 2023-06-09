package com.fanta.moneywithsoul.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * The type Properties loader.
 */
public class PropertiesLoader {

    /**
     * Load properties properties.
     *
     * @return the properties
     * @throws IOException the io exception
     */
    public Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        String filePath = System.getProperty("user.dir") + "/file.properties";

        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }
}
