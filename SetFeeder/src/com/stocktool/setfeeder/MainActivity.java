package com.stocktool.setfeeder;

import com.stocktool.setfeeder.data.Stock;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	private static final int ADD_STOCK_REQUST = 0;
	StockListAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdapter = new StockListAdapter(getApplicationContext());
		getListView().setFooterDividersEnabled(true);
		LayoutInflater inflater = getLayoutInflater();
		TextView footerView = (TextView) inflater.inflate(R.layout.footer_view, null);
		getListView().addFooterView(footerView);
		footerView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), AddStockActivity.class);
				startActivityForResult(intent, ADD_STOCK_REQUST);	
			}
		});
		
		getListView().setAdapter(mAdapter);		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == ADD_STOCK_REQUST 
				&& resultCode == RESULT_OK) {
			mAdapter.add(new Stock(data.getStringExtra(AddStockActivity.STOCK_SYMBOL)));			
		}

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mAdapter.getCount() == 0)
			loadItems();
	}
	
	@Override
	protected void onPause() {
		super.onPause();

		// Save ToDoItems

		saveItems();

	}
	private void loadItems() {
		
	}
	
	private void saveItems() {
	
	}
}
