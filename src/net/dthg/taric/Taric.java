package net.dthg.taric;

import java.util.Calendar;
import java.util.Formatter;


import org.mozilla.javascript.NativeArray;

public class Taric {

    public static String getDefaultDate() {
        Formatter formatter = new Formatter();
        Calendar rightNow = Calendar.getInstance();
        
        String output = formatter.format( "%d%02d%02d", 
                rightNow.get( Calendar.YEAR ),
                rightNow.get( Calendar.MONTH ) + 1,
                rightNow.get( Calendar.DAY_OF_MONTH ) ).toString();
        formatter.close();
        return output;
    }
    
    protected void run() {
        String default_date = getDefaultDate();
        ChapterFetch fetch = new ChapterFetch( default_date, 1 );
        String data = fetch.fetch();
        ChapterTransformer transformer = new ChapterTransformer();
        JSReader reader = new JSReader();
        Object na = reader.interpretAndFetch( data, "chaptertree" );
        String output = transformer.transform( (NativeArray)na );
        System.out.println( output );
    }
    
    
    public Taric() {
        TaricChooser tc = new TaricChooser();
        tc.pack();
        tc.setVisible( true );
        System.exit( 0 );
    }
    
    /**
     * @param args
     */
    public static void main( String[] args ) {
        new Taric();
    }

}
