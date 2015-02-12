package com.example.jack.showcase;

import android.support.v4.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jack on 2014/10/7.
 */
public class ItemPagerFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private CursorPagerAdapter adapter;

    public ItemPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_item_pager, container, false);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        adapter = new CursorPagerAdapter(getFragmentManager(), null);
        mPager.setAdapter(adapter);

        getLoaderManager().initLoader(0, null, this);
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(getActivity(),
                DataProvider.CONTENT_URI_DATA,
                new String[]{DataProvider.COL_ID},
                null,
                null,
                null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }


    /**
     * A simple pager adapter.
     */
    private class CursorPagerAdapter extends FragmentStatePagerAdapter {

        private Cursor mCursor;

        public CursorPagerAdapter(FragmentManager fm, Cursor c) {
            super(fm);
            mCursor = c;
        }

        @Override
        public Fragment getItem(int position) {
            if (mCursor.moveToPosition(position)) {
                Bundle arguments = new Bundle();
                arguments.putLong(ItemDetailFragment.ARG_ITEM_ID, mCursor.getLong(mCursor.getColumnIndex(DataProvider.COL_ID)));
                ItemDetailFragment fragment = new ItemDetailFragment();
                fragment.setArguments(arguments);
                return fragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            if (mCursor != null) {
                return mCursor.getCount();
            }
            return 0;
        }

        public void swapCursor(Cursor cursor) {
            mCursor = cursor;
            notifyDataSetChanged();
        }
    }

}

