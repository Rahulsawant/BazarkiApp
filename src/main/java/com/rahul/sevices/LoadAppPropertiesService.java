package com.rahul.sevices;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

/**
 * @author Rahul Sawant
 * Class loads property file and returns HashMap
 */
public class LoadAppPropertiesService {

    private Properties prop = new Properties();
    private HashMap<String,Object> convertedMap=new HashMap<>();

    /**
     * @return Map with key as propertyName & value as Property Value
     */
    public HashMap<String,Object> getAppProperties(String fileName){

        InputStream input = null;
        try {
            if(fileName == null){
                input = new FileInputStream("src/main/resources/application.properties");
            }
            else{
                input = new FileInputStream(fileName);
            }
            prop.load(input);
            Set<Object> keys=getAllKeys();
            keys.forEach(a->{
                convertedMap.put((String)a,getPropertyValue((String) a));
                    }
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertedMap;
    }

    private Set<Object> getAllKeys(){
        Set<Object> keys = prop.keySet();
        return keys;
    }

    private String getPropertyValue(String key){
        return this.prop.getProperty(key);
    }
}