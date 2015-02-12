package com.example.jack.showcase;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by jack on 2014/10/7.
 */
public class DataAdapter extends SimpleCursorAdapter {

    public DataAdapter(Context context, int layout) {
        super(context, layout, null, new String[]{DataProvider.COL_CONTENT}, new int[]{android.R.id.text1}, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String content = cursor.getString(cursor.getColumnIndex(DataProvider.COL_CONTENT));

        Data data = new Data(content);

        String title = data.getTitle();
        String iconUrl = data.getIcon();

        TextView titleText = (TextView) view.findViewById(android.R.id.text1);
        titleText.setText(title);

        NetworkImageView iconView = (NetworkImageView) view.findViewById(R.id.imageView1);
        iconView.setDefaultImageResId(R.drawable.ic_launcher);
        iconView.setImageUrl(iconUrl, App.getInstance().getImageLoader());
    }

}
