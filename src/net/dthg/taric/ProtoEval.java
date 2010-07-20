package net.dthg.taric;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Scriptable;

public class ProtoEval {
    
    static void taricExport( PrintStream ps, NativeArray na ) {
        String code = Context.toString( na.get( 2, null ) );
        String libe = Context.toString( na.get( 5, null ) );
        ps.println( code + ";" + libe );
        Object subarr = na.get( 7, null );
        if( Scriptable.NOT_FOUND != subarr && null != subarr ) {
            for( Object o: ((NativeArray)subarr).getIds() ) {
                Integer i = (Integer)o;
                taricExport( ps, (NativeArray)((NativeArray)subarr).get( i, null ) );
            }
        }
    }
    
    public static void main( String args[] ) {
        Context cx = Context.enter();
        try {
            Scriptable scope = cx.initStandardObjects();

            // Collect the arguments into a single string.
            
            File source = new File( "/home/zif/workspace/JsTaric/js-data/nomenclaturetree_en_20100718_20.js" );
            FileInputStream fis = new FileInputStream( source );
            InputStreamReader isr = new InputStreamReader( fis, "UTF-8" );
            BufferedReader in = new BufferedReader( isr );
            String str;
            String s = "";
            while ((str = in.readLine()) != null) {
                s += str;
            }

            // Now evaluate the string we've collected. We'll ignore the result.
            cx.evaluateString( scope, s, "<cmd>", 1, null );

            
            // Print the value of variable "x"
            Object x = scope.get( "chaptertree", scope );
            /*Object[] ids = scope.getIds();
            for ( int i = 0; i < ids.length; i++ ) {
                System.out.println( ids[i] );
            }
            System.out.println( x.getClass() );
           
            if ( x == Scriptable.NOT_FOUND ) {
                System.out.println( "x is not defined." );
            } else {
                System.out.println( "x = " + Context.toString( x ) );
            }*/
            NativeArray ax = (NativeArray)((NativeArray)x).get( 0, null );
            /*for( Object o: ax.getIds() ) {
                Integer i = (Integer)o;
                Object y = ax.get( i, null );
                if ( y == Scriptable.NOT_FOUND ) {
                    System.out.println( "y is not defined." );
                } else {
                    System.out.println( "y = " + Context.toString( y ) );
                }
            }*/
            
            PrintStream so = new PrintStream( new FileOutputStream( "/tmp/taric.csv" ));
            //PrintStream so = System.out;
            taricExport( so, ax );

        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            Context.exit();
        }
    }
}
