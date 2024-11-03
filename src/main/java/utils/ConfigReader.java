package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigReader {
    public Properties loadConfig(String fileName) throws IOException {
        Properties prop = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream(fileName);
        if (input == null) {
            throw new FileNotFoundException("config.properties file not found in the classpath");
        }
        prop.load(input);
//        System.out.println("Loaded properties: " +fileName+": " +prop);
        return prop;

    }
}