package com.rahul.application;

import com.rahul.beans.Apartment;
import com.rahul.sevices.BazarkiContentService;
import com.rahul.sevices.SendMailService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

/**
 * @author rahul sawant
 * Main Thread class to execute alert code
 */
public class BazarkiAlert implements Runnable{

    HashMap<String,Object> filtersBazarkiAlert;
    String htmlBody;
    String sendTo;
    String subject;
    String url;


    public BazarkiAlert(String sendTo,String subject){
        this.sendTo=sendTo;
        this.subject=subject;
    }
    public BazarkiAlert(String url,HashMap<String,Object> filters,String sendTo,String subject){
        filtersBazarkiAlert=filters;
        this.sendTo=sendTo;
        this.subject=subject;
        this.url=url;
    }

    String htmlInitialContent="<!DOCTYPE html><html><head><style>"+
            "table {"+
            "		font-family: 'Calibri',arial, sans-serif;"+
            " 	border-collapse: collapse;"+
            "		width: 100%;}"+
            "td, th {"+
            "    border: 2px solid #dddddd;"+
            "    text-align: left;"+
            "    padding: 4px;}"+
            "tr:nth-child(even) {"+
            "    background-color: #dddddd;"+
            "}</style> </head><body><h3>Changed Apartment List Order by Latest (Top 10)</h3><table border=1>";

    private void setHtmlBodyContent(){
        htmlBody=htmlInitialContent;
    }
    @Override
    public void run() {

        System.out.println("Running Bazarki Alert -- start");
        setHtmlBodyContent(); //set htmlbody content..

        BazarkiContentService service=new BazarkiContentService();
        List<Apartment> listOfApt;

        if(filtersBazarkiAlert == null){
            listOfApt=service.getBazarkiContent();
        }
        else{
            listOfApt=service.getBazarkiContentWithFilters(url,filtersBazarkiAlert);
        }

        //generate alert Part
        int listOfAptCount=listOfApt.size();
        if(AppExecutor.bazarkiAlertCount == null || AppExecutor.bazarkiAlertCount != listOfAptCount){

            htmlBody=htmlBody+"<tr><th bgcolor=#C0C0C0>Apartment Name</th><th bgcolor=#C0C0C0>Cost</th><th bgcolor=#C0C0C0>Details</th></tr>";
            listOfApt.forEach(a->{
                htmlBody=htmlBody+"<tr>"+"<td>"+a.getApartmentName()+"</td>"+"<td>"+a.getCostOfRent()+"</td>"+"<td>"+a.getDetailsUrl()+"</td>"+"</tr>";
            });
            htmlBody=htmlBody+"</table></body></html>";
            SendMailService.sendEmail(sendTo,subject+"-"+getCOnvertedCurrentDate("yyyy-MM-dd HH:mm"),htmlBody);
        }
        AppExecutor.bazarkiAlertCount=listOfAptCount;
        System.out.println("Running Bazarki Alert -- end");
    }

    private String getCOnvertedCurrentDate(String format){
        //Get current date time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return now.format(formatter);
    }
}
