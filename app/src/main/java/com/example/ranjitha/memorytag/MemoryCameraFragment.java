package com.example.ranjitha.memorytag;



import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MemoryCameraFragment extends Fragment {

    private Camera memoryCamera;
    private SurfaceView memoryImage;
    private Button captureButton;

    @Override
    @SuppressWarnings("deprecation")
    public View onCreateView(LayoutInflater inflater,ViewGroup parent,Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment_memory_camera, parent, false);

        memoryImage = (SurfaceView)v.findViewById(R.id.memory_camera_surfaceview);
        SurfaceHolder holder = memoryImage.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        captureButton = (Button)v.findViewById(R.id.memory_camera_captureButton);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return v;
    }


    @Override
    @SuppressWarnings("deprecation")
    public void onResume(){

        memoryCamera = Camera.open(0);
    }

    @Override
    public void onPause(){
        if (memoryCamera!=null){
            memoryCamera.release();
            memoryCamera = null;
        }
    }



}
