package com.example.ranjitha.memorytag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class MemoryFragmentList extends ListFragment {


    private ArrayList<Memory> memories;
    private static final String TAG = "MemoryFragmentList";
    private boolean subtitleVisability;
    private static final int MEMORY_RETURN = 1005;
    TextView memTitle;
    TextView memLocation;
    TextView memDate;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        subtitleVisability = false;
        getActivity().setTitle(R.id.memories_Title);
        try {
            memories = MemoryList.get(getActivity()).getMemories();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //ArrayAdapter<Memory> arrayAdapter = new ArrayAdapter<Memory>(getActivity(),android.R.layout.simple_list_item_1,memories);
        //setListAdapter(arrayAdapter);

        MemoryAdapter memoryAdapter = new MemoryAdapter(memories);
        setListAdapter(memoryAdapter);


    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup parent,Bundle savedInstanceState){

        View v = super.onCreateView(inflater,parent,savedInstanceState);
        if (subtitleVisability){
            getActivity().getActionBar().setSubtitle(R.string.Subtitle);
        }

        ListView listView = (ListView) v.findViewById(android.R.id.list);
        //registerForContextMenu(listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {


            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.memory_list_item_context,menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.menu_item_delete_memory:
                        MemoryAdapter memoryAdapter = (MemoryAdapter) getListAdapter();

                        for (int i=0;i<memoryAdapter.getCount();++i)
                        {
                            if (getListView().isItemChecked(i))
                            {
                                try {
                                    MemoryList.get(getActivity()).deleteMemory(memoryAdapter.getItem(i));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        mode.finish();
                        memoryAdapter.notifyDataSetChanged();
                        return true;

                    default:
                        return false;
                }

            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
        return v;

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,View v,ContextMenu.ContextMenuInfo menuInfo){

        getActivity().getMenuInflater().inflate(R.menu.memory_list_item_context,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){

        AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        MemoryAdapter memoryAdapter = (MemoryAdapter)getListAdapter();
        Memory memory = memoryAdapter.getItem(position);

        switch (item.getItemId()){

            case R.id.menu_item_delete_memory:
                try {
                    MemoryList.get(getActivity()).deleteMemory(memory);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                memoryAdapter.notifyDataSetChanged();
                return true;
        }
        
        return super.onContextItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v,int position,long id){

        //Memory m = (Memory) (getListAdapter()).getItem(position);
        Memory m = ((MemoryAdapter)getListAdapter()).getItem(position);
        //Log.d(TAG, m.getMemoryTitle() + " is selected ");

        //Intent i = new Intent(getActivity(),MemoryActivity.class);

        Intent i = new Intent(getActivity(),MemoryPagerActivity.class);
        i.putExtra(MemoryFragment.EXTRA_MEMORY_ID,m.getMemoryId());
        startActivityForResult(i, MEMORY_RETURN);
        Toast.makeText(getActivity(),m.getMemoryTitle()+" is selected ",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MemoryAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.list_memory_fragment, menu);
        MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if (subtitleVisability && showSubtitle != null) {
            showSubtitle.setTitle(R.string.hide_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        switch (menuItem.getItemId())
        {
            case R.id.menu_item_new_memory:
                Memory memory = new Memory();
                try {
                    MemoryList.get(getActivity()).addMemory(memory);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent i  = new Intent(getActivity(),MemoryPagerActivity.class);
                i.putExtra(MemoryFragment.EXTRA_MEMORY_ID,memory.getMemoryId());
                startActivityForResult(i, MEMORY_RETURN);
                Toast.makeText(getActivity(),"New Memory is created",Toast.LENGTH_LONG).show();
                return true;

            case R.id.menu_item_show_subtitle:
                if (getActivity().getActionBar().getSubtitle()== null){
                    getActivity().getActionBar().setSubtitle(R.string.Subtitle);
                    menuItem.setTitle(R.string.hide_subtitle);
                    subtitleVisability = true;
                }else {
                    getActivity().getActionBar().setSubtitle(null);
                    menuItem.setTitle(R.string.show_subtitle);
                    subtitleVisability = false;
                }
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if (requestCode == MEMORY_RETURN){
            Toast.makeText(getActivity(),"Activity returned",Toast.LENGTH_LONG).show();
        }
    }

    private class MemoryAdapter extends ArrayAdapter<Memory>{

        public MemoryAdapter(ArrayList<Memory> memories) {
            super(getActivity(),0,memories);
        }

        @Override
        public View getView(int position,View convertView,ViewGroup parent){

            if(convertView==null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.memory_list_item,null);
            }
            Memory m = getItem(position);
            memTitle = (TextView)convertView.findViewById(R.id.list_item_memoryTitle);
            memTitle.setText(m.getMemoryTitle());

            memLocation = (TextView)convertView.findViewById(R.id.list_item_memoryLocation);
            memLocation.setText(m.getLocation());

            memDate = (TextView)convertView.findViewById(R.id.list_item_memoryDate);
            memDate.setText(m.getMemoryDate().toString());
            return convertView;
        }

    }


}
