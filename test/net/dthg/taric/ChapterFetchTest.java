package net.dthg.taric;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

public class ChapterFetchTest {

    class MockedChapterFetch extends ChapterFetch {

        private HttpURLConnection mock_;
        
        public MockedChapterFetch( String d, int c ) { super(d,c); }
        
        public void setMock( HttpURLConnection mock ) {
            this.mock_ = mock;
        }

        @Override
        public HttpURLConnection getConnection() {
            return this.mock_;
        }
        
    }
    
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testFetch() {
        HttpURLConnection conn = mock(HttpURLConnection.class);
        
        String jscode = "var foo = 12;";
        
        ByteArrayInputStream bs = new ByteArrayInputStream( jscode.getBytes());
        try {
            when(conn.getInputStream()).thenReturn( bs );
        } catch ( IOException e ) {}
        
        MockedChapterFetch fetcher = new MockedChapterFetch( "20100720", 3 );
        fetcher.setMock( conn );
        String str = fetcher.fetch( );
        assertEquals( jscode + "\n", str );
    }

}
