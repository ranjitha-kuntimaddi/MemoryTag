package com.example.ranjitha.memorytag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.UUID;

public class MemoryActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {

        //return new MemoryFragment();

        UUID id = (UUID)getIntent().getSerializableExtra(MemoryFragment.EXTRA_MEMORY_ID);

        return MemoryFragment.newInstance(id);
    }

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if (fragment== null){
            fragment = new MemoryFragment();
            fm.beginTransaction().add(R.id.fragmentContainer,fragment).commit();
        }
    }*/





}
