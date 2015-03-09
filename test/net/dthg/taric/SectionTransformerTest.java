package net.dthg.taric;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mozilla.javascript.NativeArray;

public class SectionTransformerTest {

    private SectionTransformer st;
    
    @Before
    public void setUp() throws Exception {
        st = new SectionTransformer();
    }

    @Test
    public void shouldReturnNullIfNullProvided() {
        Object o = st.transform( null );
        assertNull( o );
    }
    
    @Test
    public void testTransform() {
        String[] data =  { "true", "Section I", "Live animals; animal products", null };
        NativeArray na = new NativeArray( data );
        NativeArray na_wrap = new NativeArray( 1 );
        na_wrap.put(  0, na_wrap, na );
        List<Section> l = st.transform( na_wrap );
        assertNotNull( l );
        assertEquals( 1, l.size() );
        assertEquals( data[1] + " " + data[2], l.get( 0 ).getTitle() );
        assertNull( l.get( 0 ).getChapters() );
    }

    @Test
    public void testTransformWithLongTitle() {
        String[] data =  { "true", "Section I", "Live animals; animal products and stuff", null };
        NativeArray na = new NativeArray( data );
        NativeArray na_wrap = new NativeArray( 1 );
        na_wrap.put(  0, na_wrap, na );
        List<Section> l = st.transform( na_wrap );
        assertNotNull( l );
        assertEquals( 1, l.size() );
        String title = (data[1] + " " + data[2]).substring( 0, 40 ) + "...";
        
        assertEquals( title, l.get( 0 ).getTitle() );
        assertNull( l.get( 0 ).getChapters() );
    }
    
    
    @Test
    public void transformWithChapters() {
        String[] data =  { "true", "Section I", "Live animals; animal products", null };
        NativeArray na = new NativeArray( data.length );
        for ( int i = 0; i < data.length; i++ ) {
            na.put(  i, na, data[i] );
        }
        String[] data_chapter = { "false", " Chapter 01", "LIVE ANIMALS", "0100000000" };
        NativeArray na_chapter = new NativeArray( data_chapter.length );
        for ( int i = 0; i < data_chapter.length; i++ ) {
            na_chapter.put(  i, na_chapter, data_chapter[i] );
        }
        
        NativeArray na_int = new NativeArray( 1 );
        na_int.put( 0, na_int, na_chapter );
        
        na.put( 3, na, na_int );
        NativeArray na_wrap = new NativeArray( 1 );
        na_wrap.put(  0, na_wrap, na );
        
        List<Section> l = st.transform( na_wrap );
        assertNotNull( l );
        assertEquals( 1, l.size() );
        assertEquals( data[1] + " " + data[2], l.get( 0 ).getTitle() );
        assertEquals( 1, l.get( 0 ).getChapters().size() );
        Chapter first_chapter = l.get( 0 ).getChapters().get( 0 );
        assertEquals( data_chapter[1] + " " + data_chapter[2], first_chapter.getTitle() );
        assertEquals( 1, first_chapter.getCode() );
    }

}
