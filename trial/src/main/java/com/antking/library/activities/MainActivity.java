package com.antking.library.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.antking.library.R;
import com.antking.library.fragments.FragmentTextBody;
import com.antking.library.fragments.FragmentTextCaption;
import com.antking.library.fragments.FragmentTextDisplay;
import com.antking.library.fragments.FragmentTextHeadline;
import com.antking.library.fragments.FragmentTextSubHeading;
import com.antking.library.fragments.FragmentTextTitle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private Activity context = this;
    private ArrayList<String> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        slider = (Slider) findViewById(R.id.slider);
        toolbar     = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager   = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager();

        tabLayout   = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
//        setupViewPager();
    }

    private void setupViewPager(){
//        data.add("1");
//        data.add("2");
//        data.add("3");
//        data.add("4");
//        data.add("5");
//        adapter = new SliderAdapter(context, data) {
//            @Override
//            public View getView(View elementView, int position) {
//                if (elementView == null){
//                    elementView = View.inflate(context, R.layout.layout_slider_item, null);
//                }
////                ((TextView)elementView.findViewById(R.id.tv_text)).setText((String)getElement(position));
//                return elementView;
//            }
//        };
//        slider.setAdapter(adapter);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter{

        private final ArrayList<Fragment> fragments = new ArrayList<>();
        private final ArrayList<String> fragmentTitles = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(new FragmentTextCaption());
            fragmentTitles.add("Caption");
            fragments.add(new FragmentTextBody());
            fragmentTitles.add("Body");
            fragments.add(new FragmentTextSubHeading());
            fragmentTitles.add("SubHeading");
            fragments.add(new FragmentTextTitle());
            fragmentTitles.add("Title");
            fragments.add(new FragmentTextHeadline());
            fragmentTitles.add("Headline");
            fragments.add(new FragmentTextDisplay());
            fragmentTitles.add("Display");
        }

        @Override
        public Fragment getItem(int position) {
            if (fragments != null){
                return fragments.get(position);
            }
            return null;
        }

        @Override
        public int getCount() {
            if (fragments != null){
                return fragments.size();
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitles.add(title);
        }
    }
}
