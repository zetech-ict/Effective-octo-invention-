package com.shanidev.blissDB;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.android.material.tabs.TabLayout;
import com.shanidev.zesapp.R;

public class SearchFragment extends Fragment {
    private FragmentActivity listener;

    SearchFragmentStatePagerAdapter adapter;
    ViewPager viewPager;

    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(int page, String title) {
        SearchFragment fragment = new SearchFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) view.findViewById(R.id.vpSearchViewPager);
        adapter = new SearchFragmentStatePagerAdapter(getChildFragmentManager(),
                getActivity());
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tlSlidingTabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (FragmentActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.menuSearch);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Fetch the data remotely
                int position = viewPager.getCurrentItem();
                Fragment fragment = adapter.getPageTitle(int position);

                if (position == 0) {
                    ((SearchFragment) fragment).search(query);
                } else if (position == 1) {
                    ((SearchTagsResultFragment) fragment).search(query);
                }

                // Reset SearchView
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                // Set activity title to search query
                getActivity().setTitle(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void search(String query) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.menuSearch:
                // Handle this selection
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class SearchFragmentStatePagerAdapter extends SearchFragmentAdapter {
        private Context context;
        private static int NUM_ITEMS = 2;
        private int[] titleIds = {
                R.string.tab_search_user,
                R.string.tab_search_tag
        };

        public SearchFragmentStatePagerAdapter(FragmentManager fragmentManager, Context context) {
            super(fragmentManager);
            this.context = context;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return SearchFragmentAdapter.newInstance(0, "Page # 1");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return SearchFragmentAdapter.newInstance(1, "Page # 2");
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return context.getResources().getString(titleIds[position]);
        }
    }

}
