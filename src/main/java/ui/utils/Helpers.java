package ui.utils;

import java.util.Locale;

public class Helpers {
    public static String cleanPriceString(String priceText) {
        if (priceText == null) {
            return "";
        }

        return priceText.toUpperCase(Locale.ROOT)
                .replace("EGP", "")
                .replace(",", "")
                .replace("\u00A0", "")
                .replaceAll("\\s+", "")
                .trim();
    }

    public static double parsePrice(String priceText) throws NumberFormatException {
        String cleaned = cleanPriceString(priceText);
        return cleaned.isEmpty() ? 0.0 : Double.parseDouble(cleaned);
    }
}
