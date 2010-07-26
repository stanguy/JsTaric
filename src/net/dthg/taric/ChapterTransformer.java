package net.dthg.taric;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Scriptable;

public class ChapterTransformer {

    public void transform( PrintStream ps, NativeArray array ) {
        if( null == array ) {
            return;
        }
        for( Object o: array.getIds() ) {
            Integer i = (Integer)o;
            NativeArray subarr = (NativeArray)array.get( i, null );
            ps.print( Context.toString( subarr.get( 2, null ) ) );
            ps.print( ';' );
            ps.println( Context.toString( subarr.get( 5, null ) ) );
            Object next_array = subarr.get( 7, null );
            if( Scriptable.NOT_FOUND != next_array && null != next_array ) {
                this.transform( ps, (NativeArray)next_array );
            }
        }
    }
    public String transform( NativeArray array ) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream( baos );
        transform( ps, array );
        return baos.toString();
    }
}
