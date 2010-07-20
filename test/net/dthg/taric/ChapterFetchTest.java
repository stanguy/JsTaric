package net.dthg.taric;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ChapterFetchTest {

    class MockedChapterFetch extends ChapterFetch {

        private HttpURLConnection mock_;
        
        
        public void setMock( HttpURLConnection mock ) {
            this.mock_ = mock;
        }

        @Override
        public HttpURLConnection getConnection(String date, int chapter) {
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
        
        MockedChapterFetch fetcher = new MockedChapterFetch();
        fetcher.setMock( conn );
        String str = fetcher.fetch( "20100720", 3 );
        Assert.assertEquals( jscode + "\n", str );
    }

}
