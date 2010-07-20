package net.dthg.taric;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Scriptable;

public class JSReader {

    public Object interpretAndFetch( String jscode, String variable_name ) {
        Context cx = Context.enter();
        try {
            Scriptable scope = cx.initStandardObjects();
            cx.evaluateString( scope, jscode, "<cmd>", 1, null );
            Object x = scope.get( variable_name, scope );
            
            if( Scriptable.NOT_FOUND != x ) { 
                return x;
            }
        } catch( EvaluatorException e ) {
            e.printStackTrace();
        }

        return null;
    }
    
}
