package com.example.jack.showcase;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by jack on 2014/10/7.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private long id;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            id = getArguments().getLong(ARG_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (id > 0) {
            Uri contentUri = ContentUris.withAppendedId(DataProvider.CONTENT_URI_DATA, id);
            Cursor c = getActivity().getContentResolver().query(contentUri, null, null, null, null);
            if (c != null) {
                if (c.moveToFirst()) {
                    String content = c.getString(c.getColumnIndex(DataProvider.COL_CONTENT));
                    Data data = new Data(content);

                    String title = data.getTitle();
                    String desc = data.getDescription();
                    String iconUrl = data.getIcon();
                    String downloadUrl = data.getDownloadUrl();
                    String tutorialUrl = data.getTutorialUrl();

                    ((TextView) rootView.findViewById(R.id.textView1)).setText(title);
                    ((TextView) rootView.findViewById(R.id.item_detail)).setText(desc);

                    NetworkImageView iconView = (NetworkImageView) rootView.findViewById(R.id.imageView1);
                    iconView.setDefaultImageResId(R.drawable.ic_launcher);
                    iconView.setImageUrl(iconUrl, App.getInstance().getImageLoader());

                    if (!TextUtils.isEmpty(downloadUrl)) {
                        TextView downloadLink = (TextView) rootView.findViewById(R.id.downloadLink);
                        downloadLink.setMovementMethod(LinkMovementMethod.getInstance());
                        downloadLink.setText(Html.fromHtml("<a href='" + downloadUrl + "'>Download</a>"));
                    }

                    if (!TextUtils.isEmpty(tutorialUrl)) {
                        TextView tutorialLink = (TextView) rootView.findViewById(R.id.tutorialLink);
                        tutorialLink.setMovementMethod(LinkMovementMethod.getInstance());
                        tutorialLink.setText(Html.fromHtml("<a href='"+tutorialUrl+"'>Read tutorial</a>"));
                    }
                }
                c.close();
            }
        }

        return rootView;
    }
}

