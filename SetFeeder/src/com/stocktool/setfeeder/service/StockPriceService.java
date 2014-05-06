package com.stocktool.setfeeder.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import android.os.AsyncTask;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.stocktool.setfeeder.MainActivity;
import com.stocktool.setfeeder.R;
import com.stocktool.setfeeder.StockListAdapter;
import com.stocktool.setfeeder.data.Stock;

public class StockPriceService extends AsyncTask<Adapter, Integer, Adapter> {
	
	private URL setUrl, maiUrl, warUrl;
	private String setRawPage = "";
	String maiRawPage = "";
	String warRawPage = "";
	
	private void getPrices(List<Stock> stocks) throws IOException{	
		setUrl = new URL("http://www.settrade.com/C13_MarketSummaryStockType.jsp?type=S");		
		maiUrl = new URL("http://www.settrade.com/C13_MarketSummarySET.jsp?command=MAI");
		warUrl = new URL("http://www.settrade.com/C13_MarketSummaryStockType.jsp?type=W");
		URLConnection connection;
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
	
	private void getAllStockList(ArrayAdapter<String> stockAdapter)  throws IOException{
		setUrl = new URL("http://www.settrade.com/C13_MarketSummaryStockType.jsp?type=S");		
		maiUrl = new URL("http://www.settrade.com/C13_MarketSummarySET.jsp?command=MAI");
		warUrl = new URL("http://www.settrade.com/C13_MarketSummaryStockType.jsp?type=W");
		URLConnection connection;
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
		
		// Parse setRawPage and extract stock symbol to stockList
		while(setRawPage.indexOf("jsp?txtSymbol=")!=-1) {
			setRawPage = setRawPage.substring(setRawPage.indexOf("jsp?txtSymbol=")+14);
			stockAdapter.add(setRawPage.substring(0, setRawPage.indexOf("\"")));			
		}	
		// Parse maiRawPage and extract stock symbol to stockList
		while(setRawPage.indexOf("jsp?txtSymbol=")!=-1) {
			maiRawPage = maiRawPage.substring(maiRawPage.indexOf("jsp?txtSymbol=")+14);
			stockAdapter.add(maiRawPage.substring(0, maiRawPage.indexOf("\"")));			
		}
		// Parse warRawPage and extract stock symbol to stockList
		while(warRawPage.indexOf("jsp?txtSymbol=")!=-1) {
			warRawPage = warRawPage.substring(warRawPage.indexOf("jsp?txtSymbol=")+14);
			stockAdapter.add(warRawPage.substring(0, warRawPage.indexOf("\"")));			
		}
	}

	@Override
	protected Adapter doInBackground(Adapter... params) {
		try {			
			if(params.length>1 && params[1] instanceof ArrayAdapter && params[1].isEmpty())
				getAllStockList((ArrayAdapter<String>)params[1]);
			getPrices(((StockListAdapter)params[0]).getStockList());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return params[0];
	}
	
	@Override
	protected void onPreExecute() {		
		if(MainActivity.getRefreshMenuItem() != null) { 
			MainActivity.getRefreshMenuItem().setActionView(R.layout.action_progressbar);
			MainActivity.getRefreshMenuItem().expandActionView(); 
		}
	}
	
	
	@Override
	protected void onPostExecute(Adapter result) {
		super.onPostExecute(result);
		((StockListAdapter)result).notifyDataSetChanged();
		if(MainActivity.getRefreshMenuItem() != null) {
			MainActivity.getRefreshMenuItem().collapseActionView();
			MainActivity.getRefreshMenuItem().setActionView(null);
		}
	}
}
