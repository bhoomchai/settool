package com.stocktool.setfeeder.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.stocktool.setfeeder.data.Stock;

public class StockPriceService {
		
	public StockPriceService() throws MalformedURLException{
				
	}

	public static void getPrices(List<Stock> stocks) throws IOException{
		URL setUrl = new URL("http://www.settrade.com/C13_MarketSummaryStockType.jsp?type=S");		
		URL maiUrl = new URL("http://www.settrade.com/C13_MarketSummarySET.jsp?command=MAI");
		URL warUrl = new URL("http://www.settrade.com/C13_MarketSummaryStockType.jsp?type=W");
		URLConnection connection;		
		String setRawPage = "";
		String maiRawPage = "";
		String warRawPage = "";
		String line;
		StringBuffer buffer;
		
		// get SET info and keep in setRawPage
		connection = setUrl.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		line = "";
		buffer = new StringBuffer();
		while((line = in.readLine()) != null) {
			buffer.append(line);			
		}
		setRawPage = buffer.toString();
		in.close();
		
		// get MAI info and keep in maiRawPage
		connection = maiUrl.openConnection();
		in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		line = "";
		buffer = new StringBuffer();
		while((line = in.readLine()) != null) {
			buffer.append(line);			
		}		
		maiRawPage = buffer.toString();
		in.close();
		
		// get Warrant info and keep in setWarPage
		connection = warUrl.openConnection();
		in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		line = "";
		buffer = new StringBuffer();
		while((line = in.readLine()) != null) {
			buffer.append(line);			
		}
		warRawPage = buffer.toString();
		in.close();
				
		double price = 100;
		double change = 5;
		double percentChange = 6;
		String symbol;
	    for (int i=0; i<stocks.size(); i++) {
	    	try {
	    			symbol = stocks.get(i).getSymbol();
	    			String temp;
	    			if(setRawPage.indexOf(">"+symbol+"<") != -1)
	    				temp = setRawPage.substring(setRawPage.indexOf(">"+symbol+"<"));
	    			else if(maiRawPage.indexOf(">"+symbol+"<") != -1)
	    				temp = maiRawPage.substring(maiRawPage.indexOf(">"+symbol+"<"));
	    			else if(warRawPage.indexOf(">"+symbol+"<") != -1)
	    				temp = warRawPage.substring(warRawPage.indexOf(">"+symbol+"<"));
	    			else
	    				temp = "";
	    			if(temp != "") {
					    temp = temp.substring(0, temp.indexOf("a href="));
					    temp = temp.substring(temp.indexOf("top:3px;")+10);	      
					    temp = temp.substring(temp.indexOf("top:3px;")+10);
					    temp = temp.substring(temp.indexOf("top:3px;")+10);
					    temp = temp.substring(temp.indexOf("top:3px;")+10);
				    	price = Double.valueOf(temp.substring(0, temp.indexOf("<")));
					    temp = temp.substring(temp.indexOf("top:3px;")+10);
					    change = Double.valueOf(temp.substring(0, temp.indexOf("<")));
					    temp = temp.substring(temp.indexOf("top:3px;")+10);
					    percentChange = Double.valueOf(temp.substring(0, temp.indexOf("<")));
					    stocks.get(i).update(price, change, percentChange);
	    			} else {
	    				// Cannot find stock info
	    				stocks.get(i).update(0, 0, 0);
	    			}
	    	} catch (NumberFormatException e) {
	    		stocks.get(i).update(0, 0, 0);
	    	}	      
	    }
	}
}
