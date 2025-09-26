package com.elharo.propernouns;

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
    if (s == null) {
      return false;
    }
    
    s = s.trim();
    if (s.isEmpty()) {
      return false;
    }
    
    // Split by spaces to check each word
    String[] words = s.split("\\s+");
    
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
    
    // Convert to lowercase for case-insensitive matching
    String lowerWord = word.toLowerCase();
    
    // Heuristic: Names that begin with "o'" (like O'Connell, O'Hara)
    // Must have at least one letter after "o'"
    if (lowerWord.startsWith("o'") && word.length() >= 4) {
      return true;
    }
    
    // Heuristic: Names that begin with "mc" (like McDonald, McTavish)
    // Must have at least one letter after "mc"
    if (lowerWord.startsWith("mc") && word.length() >= 4) {
      return true;
    }
    
    // Default to false - not a name unless proven otherwise
    return false;
  }
}
