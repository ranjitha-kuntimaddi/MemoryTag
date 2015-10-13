package com.example.ranjitha.memorytag;


import android.support.v4.app.Fragment;

public class MemoryCameraActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new MemoryCameraFragment();
    }
}
