package com.rahul.beans;

import java.util.Arrays;
import java.util.HashMap;
public class Bazarki {

    String webSiteUrl = "https://www.bazaraki.com/real-estate/houses-and-villas-rent/";
    HashMap<String,Object> filters = new HashMap<String,Object>(){
        {
            put("price_max",1200);
            put("city_districts", Arrays.asList(5694,5699));
            put("type_view","card");
            put("ordering","newest");
        }
    };

    public String getWebSiteUrl() {
        return webSiteUrl;
    }

    public void setWebSiteUrl(String webSiteUrl) {
        this.webSiteUrl = webSiteUrl;
    }

    public HashMap<String, Object> getFilters() {
        return filters;
    }

    public void setFilters(HashMap<String, Object> filters) {
        this.filters = filters;
    }

    @Override
    public String toString() {
        return "Bazarki{" +
                "webSiteUrl='" + webSiteUrl + '\'' +
                ", filters=" + filters +
                '}';
    }
}
