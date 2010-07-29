package net.dthg.taric;

public class SectionsFetch extends Fetcher {

    private String date;
    
    public SectionsFetch( String d ) {
        date = d;
    }
    
    @Override
    protected String buildUrl() {
        
        return BASE_URL + "_" + BASE_LANG + "_" + date + ".js";
    }

}
