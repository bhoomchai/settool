package com.stocktool.setfeeder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class AddStockActivity extends Activity {
	public static final String STOCK_SYMBOL = "symbol";
	private static AutoCompleteTextView mStockText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.add_stock);
			
		mStockText = (AutoCompleteTextView) findViewById(R.id.stockSymbolEditText);
		mStockText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
		mStockText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});		
		// set adapter using allStocksAdapter from MainActivity
		mStockText.setAdapter(MainActivity.getAllStocksAdapter());
		final Button cancelButton = (Button) findViewById(R.id.addStockCancel);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED, new Intent());
				finish();
			}
		});
		
		final Button submitButton = (Button) findViewById(R.id.addStockSubmit);
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String stockSymbol = mStockText.getText().toString();
				Intent data = new Intent();
				data.putExtra(STOCK_SYMBOL, stockSymbol);
				setResult(RESULT_OK, data);
				finish();
			}
		});
		
	}
	

}
