package net.dthg.taric;

public class Chapter {
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle( String title ) {
        this.title = title;
    }
    public int getCode() {
        return code;
    }
    public void setCode( int code ) {
        this.code = code;
    }
    private int code;
    
    public String toString() {
        return title;
    }

}
