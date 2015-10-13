package com.example.ranjitha.memorytag;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;


import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class MemoryPagerActivity extends FragmentActivity{

    private ViewPager memoryPager;
    private ArrayList<Memory> memories;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        memoryPager = new ViewPager(this);
        memoryPager.setId(R.id.viewPager);
        setContentView(memoryPager);

        try {
            memories = MemoryList.get(this).getMemories();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        FragmentManager fm = getSupportFragmentManager();
        memoryPager.setAdapter(new FragmentStatePagerAdapter(fm){

            @Override
            public int getCount() {
                return memories.size();
            }

            @Override
            public Fragment getItem(int position) {
                //Memory m = memories.get(position);
                return MemoryFragment.newInstance(memories.get(position).getMemoryId());
            }
        });

        UUID id = (UUID)getIntent().getSerializableExtra(MemoryFragment.EXTRA_MEMORY_ID);
        for (int i=0;i<memories.size();++i)
        {
            if (memories.get(i).getMemoryId().equals(id))
            {
                memoryPager.setCurrentItem(i);
            }
        }

        memoryPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Memory m = memories.get(position);

                if (m.getMemoryTitle()!=null){
                    setTitle(m.getMemoryTitle());
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
