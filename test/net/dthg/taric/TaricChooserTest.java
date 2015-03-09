package net.dthg.taric;

import static org.junit.Assert.*;

import org.junit.Test;

public class TaricChooserTest {

    @Test
    public void testIsDateCorrectlyFormattedWithNoData() {
        assertFalse( TaricChooser.isDateCorrectlyFormatted( null ) );
        assertFalse( TaricChooser.isDateCorrectlyFormatted( "" ) );
    }
    
    @Test
    public void isDateCorrectlyFormattedWithCorrectData() {
        assertTrue( TaricChooser.isDateCorrectlyFormatted( "20100731" ) );
        assertTrue( TaricChooser.isDateCorrectlyFormatted( "20080229" ) );
    }
    
    @Test
    public void isDateCorrectlyFormattedWithIncorrectData() {
        assertFalse( TaricChooser.isDateCorrectlyFormatted( "Yo?" ) );
        assertFalse( TaricChooser.isDateCorrectlyFormatted( "200802292" ) );
        assertFalse( TaricChooser.isDateCorrectlyFormatted( "10080229" ) );
        assertFalse( TaricChooser.isDateCorrectlyFormatted( "20084229" ) );
        assertFalse( TaricChooser.isDateCorrectlyFormatted( "20080249" ) );
    }
    

}
