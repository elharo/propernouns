package com.elharo.propernouns;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Utility class to check whether a string is likely to be a proper name.
 */
public class Names {
  
  /**
   * Set of known names loaded from names.txt resource file.
   * TODO: Consider using a more optimized data structure like a trie for better performance.
   */
  private static final Set<String> KNOWN_NAMES;
  
  static {
    // TODO: Consider asynchronous loading of the resource file for better performance.
    KNOWN_NAMES = loadNamesFromResource();
  }
  
  /**
   * Private constructor to prevent instantiation.
   */
  private Names() {
    // Utility class - prevent instantiation
  }
  
  /**
   * Loads names from the names.txt resource file into a Set.
   */
  private static Set<String> loadNamesFromResource() {
    Set<String> names = new HashSet<>();
    try (InputStream in = Names.class.getResourceAsStream("/names.txt");
         BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
      
      String line;
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (!line.isEmpty()) {
          names.add(line.toLowerCase(Locale.ROOT)); // Store in lowercase for case-insensitive comparison
        }
      }
    } catch (IOException e) {
      // If resource loading fails, return the partial set of names loaded so far.
      // This allows the class to still function with whatever names were successfully loaded.
      System.err.println("Warning: Could not load names.txt resource: " + e.getMessage());
    }
    return names;
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
    
    // Convert to lowercase once for all case-insensitive comparisons
    word = word.toLowerCase(Locale.ROOT);
    
    // Check if the word is in our known names set
    if (KNOWN_NAMES.contains(word)) {
      return true;
    }
    
    // Heuristic: Names that begin with "o'" (like O'Connell, O'Hara)
    // Must have at least one letter after "o'"
    if (word.startsWith("o'") && word.length() >= 4) {
      return true;
    }
    
    // Heuristic: Names that begin with "mc" (like McDonald, McTavish)
    // Must have at least one letter after "mc"
    if (word.startsWith("mc") && word.length() >= 4) {
      return true;
    }
    
    // Default to false - not a name unless proven otherwise
    return false;
  }
}
