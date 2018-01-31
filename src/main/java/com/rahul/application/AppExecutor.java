package com.rahul.application;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author rahul sawant
 */
public class AppExecutor {

    static Integer bazarkiAlertCount;
    static Integer bazarkiAlertCountNearAmdocs;
    public static void main(String[] args) {

        System.out.println("Running App...");
       final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        HashMap<String,Object> tempMap=new HashMap<String,Object>(){
            {
                put("price_max",1200);
                put("type_view","card");
                put("ordering","newest");
            }
        };
        Runnable runMethod1=new BazarkiAlert("https://www.bazaraki.com/real-estate/houses-and-villas-rent/lemesos-district-limassol/",tempMap,"Rahul.Sawant@amdocs.com","New Apartments List");
        scheduler.scheduleAtFixedRate(runMethod1, 0, 1, TimeUnit.MINUTES);

        Runnable runMethod2=new BazarkiAlertNearAmdocs("Rahul.Sawant@amdocs.com","Zakaki & Omonia New Apartments List");
        scheduler.scheduleAtFixedRate(runMethod2, 0, 1, TimeUnit.MINUTES);
    }
}
