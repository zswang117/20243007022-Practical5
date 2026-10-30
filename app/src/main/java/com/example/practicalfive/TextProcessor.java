package com.example.practicalfive;

/**
 * Regular (non-Activity) helper class that validates and normalises
 * a municipality name entered by the user.
 */
public class TextProcessor {

    /**
     * Validates and normalises a municipality name.
     *
     * @param municipality the raw name entered by the user
     * @return the correctly capitalised name ("Beijing", "Shanghai",
     *         "Tianjin" or "Chongqing"), or {@code null} when the input
     *         is null, empty, whitespace-only, or not one of the four
     *         supported municipalities.
     */
    public static String processMunicipality(String municipality) {
        if (municipality == null) {
            return null;
        }
        String trimmed = municipality.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        switch (trimmed.toLowerCase()) {
            case "beijing":
                return "Beijing";
            case "shanghai":
                return "Shanghai";
            case "tianjin":
                return "Tianjin";
            case "chongqing":
                return "Chongqing";
            default:
                return null;
        }
    }
}
