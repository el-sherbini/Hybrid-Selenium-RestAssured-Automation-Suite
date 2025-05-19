package api.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PayloadUtil {

    public static String loadPayload(String fileName) {
        String path = "src/test/resources/api/payloads/" + fileName;
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load payload: " + fileName);
        }
    }
}
