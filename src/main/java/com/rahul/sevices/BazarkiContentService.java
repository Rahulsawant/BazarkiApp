package com.rahul.sevices;

import com.rahul.beans.Apartment;
import com.rahul.beans.Bazarki;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author rahul sawant
 */
public class BazarkiContentService {

    List<Apartment> listOfApartments=new ArrayList<>();

    /**
     * Proxy settings to run this app from amdocs network..
     */
    static {
        ProxySelector.setDefault(new ProxySelector() {
            @Override
            public List<Proxy> select(java.net.URI uri) {
                return Arrays
                        .asList(new Proxy(Proxy.Type.HTTP,
                                new InetSocketAddress("genproxy.amdocs.com" ,
                                        8080)));
            }
            @Override
            public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
                throw new RuntimeException("Proxy connect failed", ioe);
            }
        });
    }

    /**
     * @param url bazarki rent base url .. if null default from bazarki bean will be used
     * @param filterMap filters to apply.. if null default from bazarki bean will be used
     * @return Final Url to HIT
     */
    private String buildWebUrl(String url,HashMap<String,Object> filterMap){
        Bazarki bazarki=new Bazarki();

        if(url == null) //if null user default filters
            url=bazarki.getWebSiteUrl();
        if(filterMap == null) //if null user default filters
            filterMap=bazarki.getFilters();

        //System.out.println("Filter Map"+filterMap);
        final String filter[]=new String[1];
        filter[0]="";
        filterMap.forEach(
                (k,v)->{
                    if(v instanceof List){ //check if list of districts is passed
                        ((List) v).forEach(
                                a->{
                                    filter[0]=filter[0]+k+"="+a+"&";
                                }
                        );
                    }
                    else{
                        filter[0]=filter[0]+k+"="+v+"&";
                    }
                }
        );
        filter[0]=filter[0].substring(0,filter[0].length()-1); //remove & present at end..
        //System.out.println("Url"+url+"?"+filter[0]);
        return url+"?"+filter[0];
    }

    /**
     * @param url build using buildWebUrl function(actual url to HIT)
     * @return parsed content of website (apartment names and Cost)
     */
    private List<Apartment> fetchAndParseBazarkiData(String url){
        Document doc= null;
        Element link=null;
        try {
            doc = Jsoup.connect(url).get();
            link=doc.select("div.js-list").first();
            Elements noOfElements=link.select("div.card");

            noOfElements.forEach(a->{ //get apartment details and store in list
                        Apartment apt=new Apartment();
                        apt.setApartmentName(a.select("a.card__title-link").first().ownText());
                        apt.setCostOfRent((a.select("span.card__title-price").first().ownText()));
                        apt.setDetailsUrl(a.select("a.card__title-link").first().attr("abs:href"));
                        listOfApartments.add(apt);
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfApartments;
    }

    public List<Apartment> getBazarkiContent(){
        String urlToProcess=buildWebUrl(null,null);
        return fetchAndParseBazarkiData(urlToProcess);
    }

    public List<Apartment> getBazarkiContentWithFilters(String url,HashMap<String,Object> filters){
        String urlToProcess=buildWebUrl(url,filters);
        return fetchAndParseBazarkiData(urlToProcess);
    }
}
