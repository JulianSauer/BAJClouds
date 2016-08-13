package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Helper class for reading account data from files.
 * <p>
 * Requires the file BAJclouds/src/main/resources/accounts.properties
 * with key value pairs in the format:
 * key=value
 */
public class Accounts {

    private Properties properties;

    public Accounts() {

        try {
            File file = new File("src/main/resources/accounts.properties");
            FileInputStream fis = new FileInputStream(file);
            properties = new Properties();
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }

}
