package com.example.ranjitha.memorytag;


import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class MemoryJSONConverter {

    private Context context;
    private String fileName;

    public MemoryJSONConverter(Context c,String file){
        context = c;
        fileName = file;
    }

    public void saveMemory(ArrayList<Memory> memories) throws IOException, JSONException {

        JSONArray array = new JSONArray();

        for (Memory m: memories)
        {
            array.put(m.toJSON());
        }

        Writer writer = null;
        try {

            OutputStream out = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        }finally {
            if (writer!=null){
                writer.close();
            }
        }

    }

    public ArrayList<Memory> loadMemories() throws IOException, JSONException {

        ArrayList<Memory> memories = new ArrayList<Memory>();
        BufferedReader reader = null;
        try {
            InputStream in = context.openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;

            while ((line = reader.readLine())!= null){

                jsonString.append(line);
            }

            JSONArray jsonArray = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();

            for (int i=0; i<jsonArray.length();++i){
                memories.add(new Memory(jsonArray.getJSONObject(i)));
            }
        }finally {
            if (reader != null){

                reader.close();
            }
        }
        return memories;
    }

}
