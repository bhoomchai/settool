package com.stocktool.setfeeder.data;


public class Stock {
	
	private String mSymbol;
	private double mPrice;
	private double mChange;
	private double mPercentChange;
	
	public Stock(String symbol) {
		mSymbol = symbol;
		mPrice = 0;
		mChange = 0;
		mPercentChange = 0;
	}
	
	public void update(double price, double change, double percentChange) {
		mPrice = price;
		mChange = change;
		mPercentChange = percentChange;
	}
	
	public String getSymbol() {
		return mSymbol;
	}
	
	public String getPrice() {
		return String.valueOf(mPrice);
	}
	
	public String getChange() {
		if(mChange > 0)
			return "+"+String.valueOf(mChange);
		else
			return String.valueOf(mChange);
	}
	
	public String getPercentChange() {
		if(mPercentChange > 0)
			return "+"+String.valueOf(mPercentChange);
		else
			return String.valueOf(mPercentChange);
	}

}
