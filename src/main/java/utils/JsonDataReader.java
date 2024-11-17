package utils;

import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonDataReader {

    private JSONObject jsonData;

    public JsonDataReader(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            jsonData = new JSONObject(content);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON file", e);
        }
    }

    public String getTestData(String key) {
        return jsonData.getString(key);
    }
}

