package com.example.ranjitha.memorytag;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class MemoryFragment extends Fragment {

    private Memory memory;
    private EditText memoryTitle;
    private Button memoryDate;
    private EditText memoryLocation;
    private EditText memoryNotes;
    public static final String EXTRA_MEMORY_ID = "Extra to pass in the memory id";
    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 10;
    private static final int REQUEST_TIME = 91;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getActivity().setTitle("Memory");
        setHasOptionsMenu(true);
        //memory = new Memory();
        //UUID memoryId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_MEMORY_ID);

        UUID memoryId = (UUID)getArguments().getSerializable(EXTRA_MEMORY_ID);
        try {
            memory = MemoryList.get(getActivity()).getMemory(memoryId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup parent,Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment_memory,parent,false);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

        //registerForContextMenu(v);

        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }*/

        memoryTitle = (EditText)v.findViewById(R.id.memory_Title);
        memoryTitle.setText(memory.getMemoryTitle());
        memoryTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                memory.setMemoryTitle(s.toString());
                //Toast.makeText(getActivity(), s.toString()+" is the new object to remember", Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        memoryDate = (Button)v.findViewById(R.id.memory_date);
        //memoryDate.setText(memory.getMemoryDate().toString());
        updateDate();
        memoryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                //DatePickerFragment dialog = new DatePickerFragment();
                DatePickerFragment dialog = DatePickerFragment.newInstance(memory.getMemoryDate());
                dialog.setTargetFragment(MemoryFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);

            }
        });


        memoryLocation = (EditText)v.findViewById(R.id.memory_location);
        memoryLocation.setText(memory.getLocation());
        memoryLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                memory.setLocation(s.toString());
                //Toast.makeText(getActivity(), s.toString() + " is the new location of the object", Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        memoryNotes = (EditText)v.findViewById(R.id.memory_notes);
        memoryNotes.setText(memory.getNotes());
        memoryNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                memory.setNotes(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;

    }

    @Override
    public void onPause(){
        super.onPause();
        try {
            MemoryList.get(getActivity()).saveMemory();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                /*
                Intent i = new Intent(getActivity(),MemoryListActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);*/
                if (NavUtils.getParentActivityName(getActivity())!=null){
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void updateDate(){
        memoryDate.setText(memory.getMemoryDate().toString());
    }


    public static MemoryFragment newInstance(UUID id){

        Bundle args = new Bundle();
        args.putSerializable(EXTRA_MEMORY_ID, id);
        //args.putCharSequence();

        MemoryFragment fragment = new MemoryFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){

        if (resultCode!= Activity.RESULT_OK){
            return;
        }

        if (requestCode== REQUEST_DATE){
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);

            memory.setMemoryDate(date);
            updateDate();
        }


    }





}



