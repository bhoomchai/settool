package com.stocktool.setfeeder.data;


public class Stock {
	
	private String mSymbol;
	private double mPrice;
	private double mChange;
	
	public Stock(String symbol) {
		mSymbol = symbol;
		mPrice = Math.round((Math.random()*12)*100);
		mPrice = mPrice/100;
		mChange = Math.round(((Math.random()-0.5)*2)*100);
		mChange = mChange/100;
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

}
