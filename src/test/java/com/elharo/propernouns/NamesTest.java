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
    // Test names from names.txt (case sensitive)
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
    // Test names not in names.txt
    assertFalse("Unknown single name should not be recognized", Names.isName("Zxcvbnm"));
    assertFalse("Mixed known/unknown should not be recognized", Names.isName("John Zxcvbnm"));
  }
}
