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
   * Checks whether the given string is likely to be a name.
   *
   * @param s the string to check
   * @return true if the string is likely to be a name, false otherwise
   */
  public static boolean isName(String s) {
    if (s == null || s.trim().isEmpty()) {
      return false;
    }
    
    String trimmed = s.trim();
    
    // Split by spaces to check each word
    String[] words = trimmed.split("\\s+");
    
    for (String word : words) {
      if (!isNameWord(word)) {
        return false;
      }
    }
    
    return true;
  }
  
  /**
   * Checks if a single word is a proper name.
   */
  private static boolean isNameWord(String word) {
    if (word.length() == 0) {
      return false;
    }
    
    // Must be at least 2 characters for a valid name word
    if (word.length() < 2) {
      return false;
    }
    
    for (int i = 0; i < word.length(); i++) {
      char c = word.charAt(i);
      if (!Character.isLetter(c) && c != '\'' && c != '-') {
        return false;
      }
    }
    
    // Default to false - not a name unless proven otherwise
    // This will require sophisticated name detection logic to be implemented
    // to return true for actual names
    return false;
  }
}
