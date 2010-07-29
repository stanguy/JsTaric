package net.dthg.taric;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class Fetcher {
    public String fetch() {
        try {
            HttpURLConnection conn = getConnection(); 
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
        return null;
    }
    public HttpURLConnection getConnection() {
        URL remote;
        try {
            remote = new URL( this.buildUrl() );
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
    protected String BASE_URL = "http://ec.europa.eu/taxation_customs/dds2/taric/nomenclaturetree/nomenclaturetree";
    protected String BASE_LANG = "en";
    abstract protected String buildUrl();
}
