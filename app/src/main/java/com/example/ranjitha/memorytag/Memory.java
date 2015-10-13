package com.example.ranjitha.memorytag;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

public class Memory {

    private static final String JSON_NOTES = "notes";
    private static final String JSON_TITLE = "title";
    private static final String JSON_LOCATION = "location";
    private static final String JSON_ID = "id";
    private static final String JSON_DATE = "date";

    private String memoryTitle;
    private String location;
    private String notes;
    private Date memoryDate;
    private UUID memoryId;

    public Memory(){
        memoryId = UUID.randomUUID();
        memoryDate = new Date();
        notes = "";
        memoryTitle = "";
        location = "";
    }

    public Memory(JSONObject json) throws JSONException {

        memoryId = UUID.fromString(json.getString(JSON_ID));
        //if(json.has(JSON_TITLE)){
            memoryTitle = json.getString(JSON_TITLE);

        // }
        notes = json.getString(JSON_NOTES);
        location = json.getString(JSON_LOCATION);
        memoryDate = new Date(json.getLong(JSON_DATE));
    }

    public void setMemoryTitle(String title){

        memoryTitle = title;
    }

    public void setLocation(String place){

        location = place;
    }

    public void setMemoryDate(Date date){
        memoryDate = date;
    }

    public void setMemoryId(UUID id){
        memoryId = id;
    }

    public String getMemoryTitle(){
         return memoryTitle;
     }

    public String getLocation(){
        return location;
    }

    public String getNotes(){
        return notes;
    }

    public void setNotes(String data){
        notes = data;
    }

    public UUID getMemoryId(){

        return memoryId;
    }

    public Date getMemoryDate(){

        return memoryDate;
    }

    @Override
    public String toString(){
        return memoryTitle;
    }


    public JSONObject toJSON()throws JSONException{

        JSONObject json = new JSONObject();
        json.put(JSON_TITLE,memoryTitle);
        json.put(JSON_LOCATION,location);
        json.put(JSON_NOTES,notes);
        json.put(JSON_ID,memoryId.toString());
        json.put(JSON_DATE,memoryDate.getTime());
        return json;

    }


}
