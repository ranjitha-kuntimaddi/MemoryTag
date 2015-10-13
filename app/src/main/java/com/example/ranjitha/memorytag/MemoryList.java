package com.example.ranjitha.memorytag;


import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class MemoryList {
    private static MemoryList sMemoryList;
    private ArrayList<Memory> memories;
    private Context appContext;

    private static final String fileName="memories.json";
    private MemoryJSONConverter memoryJSONConverter;


    private MemoryList(Context context) throws IOException, JSONException {

        appContext = context;
        //memories = new ArrayList<Memory>();
        memoryJSONConverter = new MemoryJSONConverter(appContext,fileName);

        try {
            memories = memoryJSONConverter.loadMemories();
        }catch (FileNotFoundException e){
            memories = new ArrayList<Memory>();
        }
        /*
        for (int i=0;i<100;++i)
        {
            Memory m = new Memory();
            m.setMemoryTitle("New title " + (i+1));
            m.setLocation("New Location");
            m.setNotes("Object " + (i + 1) + "Description");
            memories.add(m);
        }*/
    }

    public static MemoryList get(Context c) throws IOException, JSONException {

        if (sMemoryList==null){
            sMemoryList = new MemoryList(c.getApplicationContext());
        }
        return sMemoryList;
    }

    public ArrayList<Memory> getMemories(){

        return memories;
    }

    public void addMemory(Memory m){

        memories.add(m);
    }

    public void deleteMemory(Memory m){

        memories.remove(m);

    }

    public Memory getMemory(UUID id){

        for (Memory m: memories){
            if(m.getMemoryId().equals(id)){
                return m;
            }
        }
        return null;
    }

    public boolean saveMemory(){

        try{
            memoryJSONConverter.saveMemory(memories);
            Toast.makeText(appContext,"Memory is saved",Toast.LENGTH_LONG).show();
            return true;
        }catch (Exception e){
            Toast.makeText(appContext,"Memory could not be saved",Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
