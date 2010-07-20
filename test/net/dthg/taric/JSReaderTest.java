package net.dthg.taric;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mozilla.javascript.NativeArray;

public class JSReaderTest {

    private JSReader jr;
    
    @Before
    public void setUp() throws Exception {
        jr = new JSReader();
    }

    @Test
    public void emptyData() {
        Object o = jr.interpretAndFetch( "", "foo" );
        Assert.assertNull( o );
    }
    @Test
    public void correctVariable() {
        Object o = jr.interpretAndFetch( "var foo = 12;", "foo" );
        Assert.assertNotNull( o );
    }
    @Test
    public void arrayVariable() {
        Object o = jr.interpretAndFetch( "var foo = [ 12, 42 ]", "foo" );
        Assert.assertNotNull( o );
        Assert.assertTrue( o instanceof NativeArray );
    }
}
