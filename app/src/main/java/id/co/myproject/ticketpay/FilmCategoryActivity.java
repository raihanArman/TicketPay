package id.co.myproject.ticketpay;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import id.co.myproject.ticketpay.Fragment.AtTeaterFilmFragment;
import id.co.myproject.ticketpay.Fragment.IncomingFilmFragment;

public class FilmCategoryActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdaper adaper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_category);
        adaper = new ViewPagerAdaper(this.getSupportFragmentManager());
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.vp_tab);
        viewPager.setAdapter(adaper);

        tabLayout.setupWithViewPager(viewPager);
        String getcategory = getIntent().getStringExtra("category");

        if (getcategory.equals("0")){
            viewPager.setCurrentItem(0);
        }else {
            viewPager.setCurrentItem(1);
        }
    }

    public class ViewPagerAdaper extends FragmentPagerAdapter{

        private ArrayList<Fragment> mFragment;
        private ArrayList<String> mTitle;
        public ViewPagerAdaper(FragmentManager fm) {
            super(fm);

            mFragment = new ArrayList<>();
            mFragment.add(new AtTeaterFilmFragment());
            mFragment.add(new IncomingFilmFragment());

            mTitle = new ArrayList<>();
            mTitle.add("Sedang Tayang");
            mTitle.add("Akan Datang");
        }

        @Override
        public Fragment getItem(int position) {
            return mFragment.get(position);
        }

        @Override
        public int getCount() {
            return mFragment.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle.get(position);
        }
    }
}
