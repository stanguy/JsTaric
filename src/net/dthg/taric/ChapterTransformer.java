package net.dthg.taric;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Scriptable;

public class ChapterTransformer {

    public String transform( NativeArray array ) {
        if( null == array ) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for( Object o: array.getIds() ) {
            Integer i = (Integer)o;
            NativeArray subarr = (NativeArray)array.get( i, null );
            sb.append( Context.toString( subarr.get( 2, null ) ) )
              .append( ';' )
              .append( Context.toString( subarr.get( 5, null ) ) )
              .append( "\n" );
            Object next_array = subarr.get( 7, null );
            if( Scriptable.NOT_FOUND != next_array && null != next_array ) {
                sb.append(  this.transform( (NativeArray)next_array ) );
            }
        }
        return sb.toString();
    }
}
