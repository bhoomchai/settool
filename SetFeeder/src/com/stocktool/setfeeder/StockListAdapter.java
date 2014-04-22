package com.stocktool.setfeeder;

import java.util.ArrayList;
import java.util.List;
import com.stocktool.setfeeder.data.Stock;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StockListAdapter extends BaseAdapter {

	// List of Stock 
	private final List<Stock> mItems = new ArrayList<Stock>();
	
	Context mContext;
	
	StockListAdapter(Context context) {
		mContext = context;
		mItems.add(new Stock("AP"));
		mItems.add(new Stock("ASP"));
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
		RelativeLayout itemLayout = (RelativeLayout) inflater.inflate(R.layout.stock_item, null);
		
		final TextView titleView = (TextView) itemLayout.findViewById(R.id.titleView);
		titleView.setText(stock.getSymbol());
		
		
		
		return null;
	}

}
