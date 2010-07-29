package net.dthg.taric;


import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mozilla.javascript.NativeArray;

public class ChapterTransformerTest {

    private ChapterTransformer ct;
    
    @Before
    public void setUp() throws Exception {
        ct = new ChapterTransformer();
    }

    @Test
    public void none() {
        String r = ct.transform( null );
        Assert.assertEquals( "", r );
    }
    
    @Test
    public void singleLevel() {
        String[] data =  { "false", "0", "01000000", "80", "0", "LIVE ANIMALS", null, null };
        NativeArray na = new NativeArray( data );
        NativeArray na_wrap = new NativeArray( 1 );
        na_wrap.put(  0, na_wrap, na );
        String r = ct.transform( na_wrap );
        Assert.assertEquals( "01000000;LIVE ANIMALS\n", r );
    }
    
    @Test
    public void doubleLevel() {

        String[] data =  { "false", "0", "01000000", "80", "0", "LIVE ANIMALS", null, null };
        NativeArray na = new NativeArray(data.length+1);
        for ( int i = 0; i < data.length; i++ ) {
            na.put(  i, na, data[i] );
        }
        String[] data2 = { "false","1","0101000000","80","0","Live horses, asses, mules and hinnies",null,null };
        NativeArray na2 = new NativeArray( data2 );
        NativeArray na_int = new NativeArray( 1 );

        
        na_int.put( 0, na_int, na2 );
        na.put( 7, na, na_int );

        NativeArray na_wrap = new NativeArray( 1 );
        na_wrap.put(  0, na_wrap, na );
        
        String r = ct.transform( na_wrap );
        Assert.assertEquals( "01000000;LIVE ANIMALS\n0101000000;Live horses, asses, mules and hinnies\n", r );
    }
    
}
