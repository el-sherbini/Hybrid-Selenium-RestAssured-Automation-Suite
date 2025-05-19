package ui.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class DataProviders {
    private static final Logger logger = LoggerFactory.getLogger(DataProviders.class);

    @DataProvider(name = "shippingData")
    public static Object[][] shippingDataProvider() {
        try {
            logger.info("Reading shipping data from JSON file");

            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = ClassLoader.getSystemResourceAsStream("ui/ShippingData.json");

            if (inputStream == null) {
                throw new RuntimeException("ShippingData.json file not found in resources.");
            }

            List<Map<String, String>> data = mapper.readValue(inputStream, new TypeReference<>() {});
            Object[][] testData = new Object[data.size()][7];

            for (int i = 0; i < data.size(); i++) {
                Map<String, String> entry = data.get(i);
                testData[i] = new Object[]{
                        entry.getOrDefault("fullName", ""),
                        entry.getOrDefault("phoneNumber", ""),
                        entry.getOrDefault("stName", ""),
                        entry.getOrDefault("buildingNameNo", ""),
                        entry.getOrDefault("cityArea", ""),
                        entry.getOrDefault("district", ""),
                        entry.getOrDefault("landmark", ""),
                };
            }
            return testData;
        } catch (Exception e) {
            logger.error("Failed to read ShippingData.json", e);
            throw new RuntimeException("Failed to read ShippingData.json: " + e.getMessage(), e);
        }
    }
}
