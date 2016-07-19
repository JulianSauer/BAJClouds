package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Accounts {

    private Properties properties;

    public Accounts() {

        try {
            File file = new File("accounts.properties");
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
