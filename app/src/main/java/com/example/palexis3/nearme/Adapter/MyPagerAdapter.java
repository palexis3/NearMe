package com.example.palexis3.nearme.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.palexis3.nearme.Fragments.MyListFragment;

/**
 MyPagerAdaper is used to seperate the two Fragments.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 2;

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        // Returns the fragment to display for that page
        switch (position) {
            case 0: // ListFragment
                return MyListFragment.newInstance("List");
            case 1:
                return MyListFragment.newInstance("List");
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? "List" : "Map";
    }
}
