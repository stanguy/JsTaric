package net.dthg.taric;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mozilla.javascript.NativeArray;

public class SectionTransformer {
    public List<Section> transform( NativeArray array ) {
        if( null == array ) {
            return null;
        }
        List<Section> r = new ArrayList<Section>();
        for( Object o: array.getIds() ) {
            Integer i = (Integer)o;
            NativeArray subarr = (NativeArray)array.get( i, null );
            Section current_section = new Section();
            current_section.setTitle( (String)subarr.get( 1, null ) + " " + (String)subarr.get( 2, null ) );
            r.add( current_section );
            Object ochapters = subarr.get( 3, null );
            if( null != ochapters ) {
                NativeArray chapters = (NativeArray)ochapters;
                List<Chapter> list_of_chapters = new ArrayList<Chapter>();
                for( Object p: chapters.getIds() ) {
                    Integer j = (Integer)p;
                    NativeArray chapter = (NativeArray)chapters.get( j, null );
                    Chapter c = new Chapter();
                    c.setTitle( (String)chapter.get( 1, null ) + " " + (String)chapter.get( 2, null ) );
                    String code_str = (String)chapter.get( 3, null );
                    c.setCode( Integer.parseInt( code_str.substring( 0, 2 ) ) );
                    list_of_chapters.add( c );
                }
                current_section.setChapters( list_of_chapters );
            }
        }
        return r;
    }
}
