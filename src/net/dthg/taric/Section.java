package net.dthg.taric;

import java.util.List;

public class Section {
    private String title;
    private List<Chapter> chapters;
    
    public String getTitle() {
        return title;
    }
    public void setTitle( String title ) {
        if( title.length() > 40 ) {
            this.title = title.substring( 0, 40 ) + "...";
        } else {
            this.title = title;
        }
    }
    public List< Chapter > getChapters() {
        return chapters;
    }
    public void setChapters( List< Chapter > chapters ) {
        this.chapters = chapters;
    }
    public String toString() {
        return title;
    }
}
