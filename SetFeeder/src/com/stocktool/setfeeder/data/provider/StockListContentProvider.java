package com.stocktool.setfeeder.data.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class StockListContentProvider extends ContentProvider {

	private SQLiteDatabase database;
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "Stocks";
	
	private static final String CREATE_STOCKS_TABLE = " CREATE TABLE "
			+ StockListContract.STOCKS_TABLE_NAME + " (" + StockListContract.SYMBOL
			+ " TEXT PRIMARY KEY); ";

	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_STOCKS_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + StockListContract.STOCKS_TABLE_NAME);
			onCreate(db);
		}

	}
	
	@Override
	public boolean onCreate() {
		DatabaseHelper dbHelper = new DatabaseHelper(getContext());
		database = dbHelper.getWritableDatabase();
		return (database != null);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {	
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();	
		qb.setTables(StockListContract.STOCKS_TABLE_NAME);	
		Cursor c = qb.query(database, null, null, null, null, null, null);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public String getType(Uri uri) {
		// unimplemented
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long rowID = database.insert(StockListContract.STOCKS_TABLE_NAME, "", values);

		if (rowID > 0) {
			Uri _uri = ContentUris.withAppendedId(
					StockListContract.CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(_uri, null);
			return _uri;
		}
		throw new SQLException("Failed to add record into" + uri);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int rowsDeleted = database.delete(StockListContract.STOCKS_TABLE_NAME, null, null);
		getContext().getContentResolver().notifyChange(StockListContract.CONTENT_URI, null);
		return rowsDeleted;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// unimplemented
		return 0;
	}

}
