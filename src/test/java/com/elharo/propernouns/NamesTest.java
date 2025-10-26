package com.elharo.propernouns;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for the Names utility class.
 */
public class NamesTest {
  
  @Test
  public void testIsName_withInvalidNames() {
    assertFalse("numbers should not be a proper name", Names.isName("John123"));
    assertFalse("special chars should not be a proper name", Names.isName("John@Smith"));
  }
  
  @Test
  public void testIsName_withInvalidInputs() {
    assertFalse("null should not be a proper name", Names.isName(null));
    assertFalse("empty string should not be a proper name", Names.isName(""));
    assertFalse("whitespace should not be a proper name", Names.isName("   "));
    assertFalse("periods should not be a proper name", Names.isName("Dr. Smith"));
  }
  
  @Test
  public void testIsName_withKnownNames() {
    // Test names from names.txt
    assertTrue("John should be recognized as a proper name", Names.isName("John"));
    assertTrue("Smith should be recognized as a proper name", Names.isName("Smith"));
    assertTrue("Beth should be recognized as a proper name", Names.isName("Beth"));
    assertTrue("James should be recognized as a proper name", Names.isName("James"));
    assertTrue("Susan should be recognized as a proper name", Names.isName("Susan"));
  }
  
  @Test
  public void testIsName_caseInsensitive() {
    // Test case insensitive matching
    assertTrue("john should be recognized as a proper name (lowercase)", Names.isName("john"));
    assertTrue("SMITH should be recognized as a proper name (uppercase)", Names.isName("SMITH"));
    assertTrue("Anderson should be recognized as a proper name (mixed case)", Names.isName("Anderson"));
  }
  
  @Test
  public void testIsName_withFullNames() {
    // Test full names (multiple words, all must be recognized)
    assertTrue("John Smith should be recognized as a proper name", Names.isName("John Smith"));
    assertTrue("James Anderson should be recognized as a proper name", Names.isName("James Anderson"));
    assertTrue("Beth Johnson should be recognized as a proper name", Names.isName("Beth Johnson"));
    
    // Test mixed case full names
    assertTrue("john smith should be recognized (lowercase)", Names.isName("john smith"));
  }
  
  @Test
  public void testIsName_withUnknownNames() {
    // Test names not in names.txt that also don't match heuristics
    assertFalse("Unknown single name should not be recognized", Names.isName("Zxcvbnm"));
    assertFalse("Mixed known/unknown should not be recognized", Names.isName("John Zxcvbnm"));
  }
  
  @Test
  public void testOApostropheNames() {
    // Test with straight apostrophe
    assertTrue("O'Connell should be recognized as a name", Names.isName("O'Connell"));
    assertTrue("O'Hara should be recognized as a name", Names.isName("O'Hara"));
    assertTrue("O'Brien should be recognized as a name", Names.isName("O'Brien"));
    assertTrue("o'connell should be recognized as a name (case insensitive)", Names.isName("o'connell"));
    assertTrue("O'HARA should be recognized as a name (case insensitive)", Names.isName("O'HARA"));
    
    // Test with curly apostrophe (U+2019)
    assertTrue("O\u2019Connell should be recognized as a name", Names.isName("O\u2019Connell"));
    assertTrue("O\u2019Hara should be recognized as a name", Names.isName("O\u2019Hara"));
    assertTrue("O\u2019Brien should be recognized as a name", Names.isName("O\u2019Brien"));
    assertTrue("o\u2019connell should be recognized as a name (case insensitive)", Names.isName("o\u2019connell"));
    assertTrue("O\u2019HARA should be recognized as a name (case insensitive)", Names.isName("O\u2019HARA"));
    
    // Edge cases for O' names with straight apostrophe
    assertFalse("O' alone should not be a name", Names.isName("O'"));
    assertFalse("O'X should not be a name (too short)", Names.isName("O'X"));
    
    // Edge cases for O' names with curly apostrophe (U+2019)
    assertFalse("O\u2019 alone should not be a name", Names.isName("O\u2019"));
    assertFalse("O\u2019X should not be a name (too short)", Names.isName("O\u2019X"));
  }
  
  @Test
  public void testMcNames() {
    assertTrue("McDonald should be recognized as a name", Names.isName("McDonald"));
    assertTrue("McTavish should be recognized as a name", Names.isName("McTavish"));
    assertTrue("McCoy should be recognized as a name", Names.isName("McCoy"));
    assertTrue("mcdonald should be recognized as a name (case insensitive)", Names.isName("mcdonald"));
    assertTrue("MCTAVISH should be recognized as a name (case insensitive)", Names.isName("MCTAVISH"));
    
    // Edge cases for Mc names
    assertFalse("Mc alone should not be a name", Names.isName("Mc"));
    assertFalse("McX should not be a name (too short)", Names.isName("McX"));
  }
  
  @Test
  public void testMultipleWordNames() {
    assertTrue("O'Connell McDonald should be recognized as a name", Names.isName("O'Connell McDonald"));
    assertTrue("McTavish O'Hara should be recognized as a name", Names.isName("McTavish O'Hara"));
    assertFalse("O'Connell 123 should not be a name", Names.isName("O'Connell 123"));
    assertFalse("McDonald invalid@ should not be a name", Names.isName("McDonald invalid@"));
    
    // Mixed names from file and heuristics
    assertTrue("John O'Connell should be recognized (John from names.txt, O'Connell from heuristic)", Names.isName("John O'Connell"));
    assertTrue("McDonald Smith should be recognized (McDonald from heuristic, Smith from names.txt)", Names.isName("McDonald Smith"));
  }
  
  @Test
  public void testIcelandicDottirNames() {
    // Test Icelandic names ending in dóttir (with accented ó)
    assertTrue("Björksdóttir should be recognized as a name", Names.isName("Björksdóttir"));
    assertTrue("Eriksdóttir should be recognized as a name", Names.isName("Eriksdóttir"));
    assertTrue("Gunnarsdóttir should be recognized as a name", Names.isName("Gunnarsdóttir"));
    assertTrue("björksdóttir should be recognized as a name (case insensitive)", Names.isName("björksdóttir"));
    assertTrue("ERIKSDÓTTIR should be recognized as a name (case insensitive)", Names.isName("ERIKSDÓTTIR"));
    
    // Test Icelandic names ending in dottir (without accent)
    assertTrue("Bjorksdottir should be recognized as a name", Names.isName("Bjorksdottir"));
    assertTrue("Eriksdottir should be recognized as a name", Names.isName("Eriksdottir"));
    assertTrue("Gunnarsdottir should be recognized as a name", Names.isName("Gunnarsdottir"));
    assertTrue("bjorksdottir should be recognized as a name (case insensitive)", Names.isName("bjorksdottir"));
    assertTrue("ERIKSDOTTIR should be recognized as a name (case insensitive)", Names.isName("ERIKSDOTTIR"));
    
    // Edge cases - standalone suffixes should not be names
    assertFalse("dóttir alone should not be a name", Names.isName("dóttir"));
    assertFalse("dottir alone should not be a name", Names.isName("dottir"));
    assertFalse("DÓTTIR alone should not be a name", Names.isName("DÓTTIR"));
    assertFalse("DOTTIR alone should not be a name", Names.isName("DOTTIR"));
    
    // Short prefixes should still work
    assertTrue("Adóttir should be recognized as a name", Names.isName("Adóttir"));
    assertTrue("Bdottir should be recognized as a name", Names.isName("Bdottir"));
  }
    
  @Test
  public void testMixedApostropheTypes() {
    // Test mixed apostrophe types in multi-word names
    assertTrue("O'Connell O\u2019Hara should be recognized (mixed apostrophe types)", Names.isName("O'Connell O\u2019Hara"));
    assertTrue("O\u2019Brien O'Malley should be recognized (mixed apostrophe types)", Names.isName("O\u2019Brien O'Malley"));
    
    // Test that both apostrophe types work in character validation with simple apostrophe names
    assertTrue("Name with straight apostrophe should be valid", Names.isName("O'Test"));
    assertTrue("Name with curly apostrophe should be valid", Names.isName("O\u2019Test")); 
  }
  
  @Test
  public void testWeekdayNames() {
    // Test all weekday names
    assertTrue("Monday should be recognized as a proper name", Names.isName("Monday"));
    assertTrue("Tuesday should be recognized as a proper name", Names.isName("Tuesday"));
    assertTrue("Wednesday should be recognized as a proper name", Names.isName("Wednesday"));
    assertTrue("Thursday should be recognized as a proper name", Names.isName("Thursday"));
    assertTrue("Friday should be recognized as a proper name", Names.isName("Friday"));
    assertTrue("Saturday should be recognized as a proper name", Names.isName("Saturday"));
    assertTrue("Sunday should be recognized as a proper name", Names.isName("Sunday"));
    
    // Test case insensitive
    assertTrue("monday should be recognized as a proper name (lowercase)", Names.isName("monday"));
    assertTrue("TUESDAY should be recognized as a proper name (uppercase)", Names.isName("TUESDAY"));
  }
  
  @Test
  public void testMonthNames() {
    // Test all month names
    assertTrue("January should be recognized as a proper name", Names.isName("January"));
    assertTrue("February should be recognized as a proper name", Names.isName("February"));
    assertTrue("March should be recognized as a proper name", Names.isName("March"));
    assertTrue("April should be recognized as a proper name", Names.isName("April"));
    assertTrue("May should be recognized as a proper name", Names.isName("May"));
    assertTrue("June should be recognized as a proper name", Names.isName("June"));
    assertTrue("July should be recognized as a proper name", Names.isName("July"));
    assertTrue("August should be recognized as a proper name", Names.isName("August"));
    assertTrue("September should be recognized as a proper name", Names.isName("September"));
    assertTrue("October should be recognized as a proper name", Names.isName("October"));
    assertTrue("November should be recognized as a proper name", Names.isName("November"));
    assertTrue("December should be recognized as a proper name", Names.isName("December"));
    
    // Test case insensitive
    assertTrue("january should be recognized as a proper name (lowercase)", Names.isName("january"));
    assertTrue("DECEMBER should be recognized as a proper name (uppercase)", Names.isName("DECEMBER"));
  }
  
  @Test
  public void testLanguageNames() {
    // Test language names that are not common nouns
    assertTrue("Chinese should be recognized as a proper name", Names.isName("Chinese"));
    assertTrue("Dutch should be recognized as a proper name", Names.isName("Dutch"));
    assertTrue("English should be recognized as a proper name", Names.isName("English"));
    assertTrue("French should be recognized as a proper name", Names.isName("French"));
    assertTrue("German should be recognized as a proper name", Names.isName("German"));
    assertTrue("Italian should be recognized as a proper name", Names.isName("Italian"));
    assertTrue("Japanese should be recognized as a proper name", Names.isName("Japanese"));
    assertTrue("Russian should be recognized as a proper name", Names.isName("Russian"));
    
    // Test case insensitive
    assertTrue("chinese should be recognized as a proper name (lowercase)", Names.isName("chinese"));
    assertTrue("FRENCH should be recognized as a proper name (uppercase)", Names.isName("FRENCH"));
  }
}
