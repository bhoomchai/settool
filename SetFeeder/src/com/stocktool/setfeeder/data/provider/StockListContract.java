package com.stocktool.setfeeder.data.provider;

import android.net.Uri;

public class StockListContract {
	public static final String AUTHORITY = "com.stocktool.setfeeder.data.provider";
	public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY + "/");

	public static final String STOCKS_TABLE_NAME = "stocks";

	// The URI for this table.
	public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI,
			STOCKS_TABLE_NAME);

	public static final String SYMBOL = "symbol";

}
