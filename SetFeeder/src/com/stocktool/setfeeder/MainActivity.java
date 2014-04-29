package com.stocktool.setfeeder;

import java.io.IOException;

import com.stocktool.setfeeder.data.Stock;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	private static final int ADD_STOCK_REQUST = 0;
	StockListAdapter mAdapter;
	private GestureDetectorCompat mGestureDetector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mAdapter = new StockListAdapter(getApplicationContext());
		
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
						if (velocityX < -10.0f) {						
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
