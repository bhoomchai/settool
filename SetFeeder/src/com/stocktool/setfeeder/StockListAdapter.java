package com.stocktool.setfeeder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.stocktool.setfeeder.data.Stock;
import com.stocktool.setfeeder.service.StockPriceService;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class StockListAdapter extends BaseAdapter {

	// List of Stock 
	private final List<Stock> mItems = new ArrayList<Stock>();
	
	Context mContext;
	
	StockListAdapter(Context context) {
		mContext = context;
		mItems.add(new Stock("AP"));
		mItems.add(new Stock("ASP"));
		mItems.add(new Stock("CPF"));
		mItems.add(new Stock("CPALL"));
	}
	
	public void add(Stock stock) {
		mItems.add(stock);
		notifyDataSetChanged();
	}
	
	public void remove(Stock stock) {
		mItems.remove(stock);
		notifyDataSetChanged();
	}
	
	public void clear() {
		mItems.clear();
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Object getItem(int index) {		
		return mItems.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int index, View convertView, ViewGroup parent) {

		final Stock stock = (Stock)getItem(index);
		
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout itemLayout = (LinearLayout) inflater.inflate(R.layout.stock_item, null);
		
		final TextView symbolView = (TextView) itemLayout.findViewById(R.id.stockSymbol);
		symbolView.setText(stock.getSymbol());
		
		final TextView priceView = (TextView) itemLayout.findViewById(R.id.stockPrice);
		priceView.setText(stock.getPrice());
		priceView.setTextColor(Color.BLACK);
		
		final TextView changeView = (TextView) itemLayout.findViewById(R.id.stockChange);
		changeView.setText(stock.getChange()+" ("+stock.getPercentChange()+"%)");
		changeView.setTextColor(getTextColorFromString(stock.getChange()));
				
		
		return itemLayout;
	}
	
	
	private int getTextColorFromString(String text) {
		if(text.contains("+")) {
			return Color.rgb(0, 100, 0);
		}
		else if(text.contains("-")){
			return Color.rgb(150, 0, 0);
		}
		else
			return Color.rgb(200, 100, 0);			
	}
	
	
	public List<Stock> getStockList() {
		return mItems;
	}

}
