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
    
    // Split by spaces to check each word
    String[] words = trimmed.split("\\s+");
    
    for (String word : words) {
      if (!isProperNameWord(word)) {
        return false;
      }
    }
    
    return true;
  }
  
  /**
   * Checks if a single word is a proper name.
   */
  private static boolean isProperNameWord(String word) {
    if (word.length() == 0) {
      return false;
    }
    
    for (int i = 0; i < word.length(); i++) {
      char c = word.charAt(i);
      if (!Character.isLetter(c) && c != '\'' && c != '-') {
        return false;
      }
    }
    
    return true;
  }
}
