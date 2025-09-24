package com.elharo.propernames;

/**
 * Utility class to check whether a string is likely to be a proper name.
 */
public class Names {
    
    /**
     * Private constructor to prevent instantiation.
     */
    private Names() {
        // Utility class - prevent instantiation
    }
    
    /**
     * Checks whether the given string is likely to be a proper name.
     *
     * @param name the string to check
     * @return true if the string is likely to be a proper name, false otherwise
     */
    public static boolean isProperName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        
        String trimmed = name.trim();
        if (trimmed.length() == 0) {
            return false;
        }
        
        // Split by spaces to check each word
        String[] words = trimmed.split("\\s+");
        
        for (String word : words) {
            if (!isValidProperNameWord(word)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Checks if a single word is a valid proper name word.
     * A valid proper name word should:
     * - Start with an uppercase letter
     * - Have remaining letters in lowercase (except for special cases like O'Connor)
     * - Only contain letters, apostrophes, periods, and hyphens
     */
    private static boolean isValidProperNameWord(String word) {
        if (word.length() == 0) {
            return false;
        }
        
        // First character must be uppercase
        if (!Character.isUpperCase(word.charAt(0))) {
            return false;
        }
        
        boolean foundUpperAfterFirst = false;
        boolean afterApostrophe = false;
        
        for (int i = 1; i < word.length(); i++) {
            char c = word.charAt(i);
            
            if (Character.isLetter(c)) {
                if (Character.isUpperCase(c)) {
                    // Uppercase is only allowed right after apostrophe (like O'Connor)
                    // or after hyphen (like Mary-Jane)
                    if (!afterApostrophe && (i == 0 || (word.charAt(i-1) != '\'' && word.charAt(i-1) != '-'))) {
                        foundUpperAfterFirst = true;
                    }
                }
                afterApostrophe = false;
            } else if (c == '\'') {
                afterApostrophe = true;
            } else if (c == '-' || c == '.') {
                afterApostrophe = false;
            } else {
                // Invalid character
                return false;
            }
        }
        
        // If we found uppercase letters after the first character (not after apostrophe or hyphen),
        // it's not a proper name (like "JOHN")
        return !foundUpperAfterFirst;
    }
}