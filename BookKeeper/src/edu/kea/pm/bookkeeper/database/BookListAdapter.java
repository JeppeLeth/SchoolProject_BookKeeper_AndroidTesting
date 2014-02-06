package edu.kea.pm.bookkeeper.database;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import edu.kea.pm.bookkeeper.R;

public class BookListAdapter extends CursorAdapter {

	/**
	 * ViewHolder class for layout.<br />
	 * <br />
	 * Auto-created on 2014-02-06 11:43:14 by Android Layout Finder
	 * (http://www.buzzingandroid.com/tools/android-layout-finder)
	 */
	private static class ViewHolder {
		public final TextView title;
		public final TextView authors;
		public final TextView year;
		public final View statusIndication;
	
		private ViewHolder(TextView title, TextView authors, TextView year, View statusIndication) {
			this.title = title;
			this.authors = authors;
			this.year = year;
			this.statusIndication = statusIndication;
		}
	
		public static ViewHolder create(RelativeLayout rootView) {
			TextView title = (TextView)rootView.findViewById( R.id.title );
			TextView authors = (TextView)rootView.findViewById( R.id.authors );
			TextView year = (TextView)rootView.findViewById( R.id.year );
			View statusIndication = (View)rootView.findViewById( R.id.status_indication );
			return new ViewHolder( title, authors, year, statusIndication );
		}
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		final ViewHolder vh = (ViewHolder)view.getTag();

		// TODO Bind your data to the views here
		
//		vh.title = cursor.getString(cursor.getColumnIndex(BookTable.TITLE);
//		
//		if ( TextUtils.isEmpty(cursor.getString(cursor.getColumnIndex(Loaner.NAME))) {
//			vh.statusIndication.setBackgroundColor(Color.GREEN);
//		} else {
//			vh.statusIndication.setBackgroundColor(Color.RED);
//		}
	}
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = mInflater.inflate( R.layout.book_list_item, parent, false );
		view.setTag( ViewHolder.create( (RelativeLayout)view ) );
		return view;
	}

	private LayoutInflater mInflater;

	// Constructors
	public BookListAdapter(Context context) {
		super(context, null, true);
		this.mInflater = LayoutInflater.from( context );
	}
	public BookListAdapter(Context context, Cursor c) {
		super(context, c, true);
		this.mInflater = LayoutInflater.from( context );
	}
}