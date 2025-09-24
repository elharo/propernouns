package com.elharo.propernames;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for the Names utility class.
 */
public class NamesTest {
    
    @Test
    public void testIsProperNameWithValidNames() {
        assertTrue("John should be a proper name", Names.isProperName("John"));
        assertTrue("Smith should be a proper name", Names.isProperName("Smith"));
        assertTrue("James should be a proper name", Names.isProperName("James"));
        assertTrue("Clark should be a proper name", Names.isProperName("Clark"));
        assertTrue("Susan should be a proper name", Names.isProperName("Susan"));
        assertTrue("Beth should be a proper name", Names.isProperName("Beth"));
        assertTrue("Leroy should be a proper name", Names.isProperName("Leroy"));
        assertTrue("Johnson should be a proper name", Names.isProperName("Johnson"));
        assertTrue("Anderson should be a proper name", Names.isProperName("Anderson"));
    }
    
    @Test
    public void testIsProperNameWithFullNames() {
        assertTrue("John Smith should be a proper name", Names.isProperName("John Smith"));
        assertTrue("James Clark should be a proper name", Names.isProperName("James Clark"));
        assertTrue("Susan Johnson should be a proper name", Names.isProperName("Susan Johnson"));
    }
    
    @Test
    public void testIsProperNameWithInvalidNames() {
        assertFalse("lowercase john should not be a proper name", Names.isProperName("john"));
        assertFalse("JOHN should not be a proper name", Names.isProperName("JOHN"));
        assertFalse("john smith should not be a proper name", Names.isProperName("john smith"));
    }
    
    @Test
    public void testIsProperNameWithSpecialCharacters() {
        assertTrue("O'Connor should be a proper name", Names.isProperName("O'Connor"));
        assertTrue("Mary-Jane should be a proper name", Names.isProperName("Mary-Jane"));
        assertTrue("Dr. Smith should be a proper name", Names.isProperName("Dr. Smith"));
    }
    
    @Test
    public void testIsProperNameWithInvalidInputs() {
        assertFalse("null should not be a proper name", Names.isProperName(null));
        assertFalse("empty string should not be a proper name", Names.isProperName(""));
        assertFalse("whitespace should not be a proper name", Names.isProperName("   "));
        assertFalse("numbers should not be a proper name", Names.isProperName("John123"));
        assertFalse("special chars should not be a proper name", Names.isProperName("John@Smith"));
    }
}