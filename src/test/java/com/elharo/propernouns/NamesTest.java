package com.elharo.propernouns;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for the Names utility class.
 */
public class NamesTest {
  
  @Test
  public void testIsProperNameWithInvalidNames() {
    assertFalse("numbers should not be a proper name", Names.isName("John123"));
    assertFalse("special chars should not be a proper name", Names.isName("John@Smith"));
  }
  
  @Test
  public void testIsProperNameWithInvalidInputs() {
    assertFalse("null should not be a proper name", Names.isName(null));
    assertFalse("empty string should not be a proper name", Names.isName(""));
    assertFalse("whitespace should not be a proper name", Names.isName("   "));
    assertFalse("periods should not be a proper name", Names.isName("Dr. Smith"));
  }
  
  @Test
  public void testOApostropheNames() {
    assertTrue("O'Connell should be recognized as a name", Names.isName("O'Connell"));
    assertTrue("O'Hara should be recognized as a name", Names.isName("O'Hara"));
    assertTrue("O'Brien should be recognized as a name", Names.isName("O'Brien"));
    assertTrue("o'connell should be recognized as a name (case insensitive)", Names.isName("o'connell"));
    assertTrue("O'HARA should be recognized as a name (case insensitive)", Names.isName("O'HARA"));
    
    // Edge cases for O' names
    assertFalse("O' alone should not be a name", Names.isName("O'"));
    assertFalse("O'X should not be a name (too short)", Names.isName("O'X"));
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
    
    // Names that don't match the heuristics should return false
    assertFalse("O'Connell Smith should not be recognized (Smith doesn't match heuristics)", Names.isName("O'Connell Smith"));
    assertFalse("John McDonald should not be recognized (John doesn't match heuristics)", Names.isName("John McDonald"));
  }
}
