package com.stocktool.setfeeder;

import com.stocktool.setfeeder.data.Stock;
import com.stocktool.setfeeder.service.StockPriceService;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	private static final int ADD_STOCK_REQUST = 0;
	private StockListAdapter mAdapter;
	private static ArrayAdapter<String> mAllStocksAdapter;
	private GestureDetectorCompat mGestureDetector;
	
	// action bar
    private ActionBar actionBar;
    // Refresh menu item
    private static MenuItem refreshMenuItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mAdapter = new StockListAdapter(getApplicationContext());
		mAllStocksAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		
		getListView().setFooterDividersEnabled(true);
		LayoutInflater inflater = getLayoutInflater();
		TextView footerView = (TextView) inflater.inflate(R.layout.footer_view, null);
		View headerView = inflater.inflate(R.layout.header_view, null);
		getListView().addHeaderView(headerView);
		getListView().addFooterView(footerView);
		footerView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), AddStockActivity.class);
				startActivityForResult(intent, ADD_STOCK_REQUST);	
			}
		});		
		getListView().setAdapter(mAdapter);
		setupGestureDetector();
		getListView().setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				boolean handled = false;
				if(mGestureDetector != null) {
					handled = mGestureDetector.onTouchEvent(event);
				}
				return handled;
			}
		});
	}
	
	private void setupGestureDetector() {
		mGestureDetector = new GestureDetectorCompat(this, 
				new GestureDetector.SimpleOnGestureListener () {
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
						if (velocityX < -1000.0f && e1.getX()-e2.getX() > 500) {						
							int position = getListView().pointToPosition((int)e1.getX(), (int)e1.getY());						
							removeSwipeItem(position);
						}					
						return true;
					}
					 @Override
			        public boolean onDown(MotionEvent event) {  
			            return true;
			        }
				}
			);
	}
	
	private void removeSwipeItem(int position) {
		if(position != -1) {
			Toast.makeText(this, ((Stock)getListView().getAdapter().getItem(position)).getSymbol() + " is removed", Toast.LENGTH_SHORT).show();
			mAdapter.remove((Stock)getListView().getAdapter().getItem(position));
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.mGestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	};
	

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
		refreshMenuItem = (MenuItem)menu.findItem(R.id.action_refresh);
		updatePrice();
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_refresh:
			refreshMenuItem = item;
			updatePrice();
			return true;
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mAdapter.getCount() != 0 && refreshMenuItem != null)
			updatePrice();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		saveItems();

	}

	private void loadItems() {

	}
	
	private void saveItems() {
		
	}
	
	private void updatePrice() {
		if(mAdapter != null)
			new StockPriceService().execute(mAdapter, mAllStocksAdapter);
	}
	
	public static ArrayAdapter<String> getAllStocksAdapter() {
		return mAllStocksAdapter;
	}
	
	public static MenuItem getRefreshMenuItem() {
		return refreshMenuItem;
	}
}
