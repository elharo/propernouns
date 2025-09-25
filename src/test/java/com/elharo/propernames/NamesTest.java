package com.elharo.propernames;

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
}
