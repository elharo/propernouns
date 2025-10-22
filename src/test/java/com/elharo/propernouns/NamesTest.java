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
  public void testGermanNounsEndingInKeit() {
    // Test German nouns ending in keit
    assertTrue("Freiheit should be recognized as a proper noun", Names.isName("Freiheit"));
    assertTrue("Geschwindigkeit should be recognized as a proper noun", Names.isName("Geschwindigkeit"));
    assertTrue("Möglichkeit should be recognized as a proper noun", Names.isName("Möglichkeit"));
    assertTrue("Einsamkeit should be recognized as a proper noun", Names.isName("Einsamkeit"));
    
    // Test case insensitivity
    assertTrue("freiheit should be recognized as a proper noun (lowercase)", Names.isName("freiheit"));
    assertTrue("GESCHWINDIGKEIT should be recognized as a proper noun (uppercase)", Names.isName("GESCHWINDIGKEIT"));
    
    // Edge cases - standalone suffix should not be a noun
    assertFalse("keit alone should not be a noun", Names.isName("keit"));
    assertFalse("KEIT alone should not be a noun", Names.isName("KEIT"));
    
    // Short prefixes should still work
    assertTrue("Akeit should be recognized as a proper noun", Names.isName("Akeit"));
  }
  
  @Test
  public void testGermanNounsEndingInHeit() {
    // Test German nouns ending in heit
    assertTrue("Schönheit should be recognized as a proper noun", Names.isName("Schönheit"));
    assertTrue("Wahrheit should be recognized as a proper noun", Names.isName("Wahrheit"));
    assertTrue("Weisheit should be recognized as a proper noun", Names.isName("Weisheit"));
    assertTrue("Krankheit should be recognized as a proper noun", Names.isName("Krankheit"));
    
    // Test case insensitivity
    assertTrue("schönheit should be recognized as a proper noun (lowercase)", Names.isName("schönheit"));
    assertTrue("WAHRHEIT should be recognized as a proper noun (uppercase)", Names.isName("WAHRHEIT"));
    
    // Edge cases - standalone suffix should not be a noun
    assertFalse("heit alone should not be a noun", Names.isName("heit"));
    assertFalse("HEIT alone should not be a noun", Names.isName("HEIT"));
    
    // Short prefixes should still work
    assertTrue("Aheit should be recognized as a proper noun", Names.isName("Aheit"));
  }
  
  @Test
  public void testGermanNounsEndingInSchaft() {
    // Test German nouns ending in schaft
    assertTrue("Freundschaft should be recognized as a proper noun", Names.isName("Freundschaft"));
    assertTrue("Gesellschaft should be recognized as a proper noun", Names.isName("Gesellschaft"));
    assertTrue("Wissenschaft should be recognized as a proper noun", Names.isName("Wissenschaft"));
    assertTrue("Mannschaft should be recognized as a proper noun", Names.isName("Mannschaft"));
    
    // Test case insensitivity
    assertTrue("freundschaft should be recognized as a proper noun (lowercase)", Names.isName("freundschaft"));
    assertTrue("GESELLSCHAFT should be recognized as a proper noun (uppercase)", Names.isName("GESELLSCHAFT"));
    
    // Edge cases - standalone suffix should not be a noun
    assertFalse("schaft alone should not be a noun", Names.isName("schaft"));
    assertFalse("SCHAFT alone should not be a noun", Names.isName("SCHAFT"));
    
    // Short prefixes should still work
    assertTrue("Aschaft should be recognized as a proper noun", Names.isName("Aschaft"));
  }
}
