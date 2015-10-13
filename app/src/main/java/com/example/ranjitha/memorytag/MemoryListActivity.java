package com.example.ranjitha.memorytag;


import android.support.v4.app.Fragment;

public class MemoryListActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {

        return new MemoryFragmentList();
    }

}
