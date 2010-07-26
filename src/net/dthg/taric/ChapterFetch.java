package net.dthg.taric;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Formatter;

public class ChapterFetch {
    public String fetch( String date, int chapter ) {
        try {
            HttpURLConnection conn = getConnection( date, chapter ); 
            conn.setReadTimeout( 15 * 1000 );
            conn.connect();

            // read the output from the server
            BufferedReader reader = new BufferedReader( new InputStreamReader(
                    conn.getInputStream(), "UTF-8" ) );
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ( ( line = reader.readLine() ) != null ) {
                stringBuilder.append( line + "\n" );
            }
            return stringBuilder.toString();
        } catch ( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
    public HttpURLConnection getConnection(String date, int chapter) {
        URL remote;
        try {
            remote = new URL( this.buildUrl( date, chapter ) );
            HttpURLConnection conn = (HttpURLConnection)remote.openConnection();
            return conn;
        } catch ( MalformedURLException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch ( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    private String BASE_URL = "http://ec.europa.eu/taxation_customs/dds2/taric/nomenclaturetree/nomenclaturetree";
    private String BASE_LANG = "en";
    private String buildUrl( String date, int chapter ) {
        Formatter format = new Formatter();
        StringBuilder sb = new StringBuilder();
        sb.append( BASE_URL )
          .append( '_' ).append( BASE_LANG )
          .append( '_' ).append( date )
          .append( '_' ).append( format.format( "%02d", chapter ) )
          .append( ".js" );
        return sb.toString();
    }
}
