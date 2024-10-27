package utilities;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Collectors;

public class PropertiesLoader {
    private final Properties properties = new Properties();

    public PropertiesLoader() {
        try {
            loadProperties("src/main/resources/properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadProperties(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
        Files.walk(path, 1)
                .filter(Files::isRegularFile)
                .filter(p -> p.toString().endsWith(".properties"))
                .forEach(this::loadPropertyFile);
    }

    private void loadPropertyFile(Path filePath) {
        try (InputStream input = Files.newInputStream(filePath)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void main(String[] args) {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        String baseUrl = propertiesLoader.getProperty("baseUrl");
        System.out.println("baseUrl: " + baseUrl);
    }
}

