package net.dthg.taric;

import org.mozilla.javascript.NativeArray;

public class Taric {

    /**
     * @param args
     */
    public static void main( String[] args ) {
        ChapterFetch fetch = new ChapterFetch();
        String data = fetch.fetch( "20100720", 1 );
        ChapterTransformer transformer = new ChapterTransformer();
        JSReader reader = new JSReader();
        Object na = reader.interpretAndFetch( data, "chaptertree" );
        String output = transformer.transform( (NativeArray)na );
        System.out.println( output );
    }

}
