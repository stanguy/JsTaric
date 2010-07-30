package net.dthg.taric;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class TaricChooserTest {

    @Test
    public void testIsDateCorrectlyFormattedWithNoData() {
        Assert.assertFalse( TaricChooser.isDateCorrectlyFormatted( null ) );
        Assert.assertFalse( TaricChooser.isDateCorrectlyFormatted( "" ) );
    }
    
    @Test
    public void isDateCorrectlyFormattedWithCorrectData() {
        Assert.assertTrue( TaricChooser.isDateCorrectlyFormatted( "20100731" ) );
        Assert.assertTrue( TaricChooser.isDateCorrectlyFormatted( "20080229" ) );
    }
    
    @Test
    public void isDateCorrectlyFormattedWithIncorrectData() {
        Assert.assertFalse( TaricChooser.isDateCorrectlyFormatted( "Yo?" ) );
        Assert.assertFalse( TaricChooser.isDateCorrectlyFormatted( "200802292" ) );
        Assert.assertFalse( TaricChooser.isDateCorrectlyFormatted( "10080229" ) );
        Assert.assertFalse( TaricChooser.isDateCorrectlyFormatted( "20084229" ) );
        Assert.assertFalse( TaricChooser.isDateCorrectlyFormatted( "20080249" ) );
    }
    

}
