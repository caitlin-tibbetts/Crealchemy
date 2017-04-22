package com.example.caitlin.crealchemy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class CreateParentsActivity extends Activity {
    Element newElement, p1, p2;
    ElementList list;
    Button buttonDone, buttonAdd;
    TextArrayAdapter adapterParents;
    ArrayList<Element[]> parentWIPList = new ArrayList<>();
    ImageTextArrayAdapter adapterElements;
    Spinner spinnerAddParents1, spinnerAddParents2;
    ListView listViewParents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_parents);
        CurrentState senderCreateParents = (CurrentState)getApplication();
        newElement = senderCreateParents.getElement();
        list = senderCreateParents.getElementList();
        buttonDone = (Button)findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
                newElement.addAllParents(parentWIPList);
                CurrentState senderCreate = (CurrentState) getApplication();
                senderCreate.setElement(newElement);
                senderCreate.setElementList(list);
                startActivity(intent);
            }
        });
        adapterElements = new ImageTextArrayAdapter(this, R.layout.horizontal_image_text_list_item_0, R.id.listTextView, list);
        spinnerAddParents1 = (Spinner)findViewById(R.id.spinnerParent1);
        spinnerAddParents1.setAdapter(adapterElements);
        spinnerAddParents1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                p1 = (Element) spinnerAddParents1.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerAddParents2 = (Spinner)findViewById(R.id.spinnerParent2);
        spinnerAddParents2.setAdapter(adapterElements);
        spinnerAddParents2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                p2 = (Element) spinnerAddParents2.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        listViewParents = (ListView)findViewById(R.id.listViewParents);
        adapterParents = new TextArrayAdapter(this, R.layout.text_list_item_0, parentWIPList);
        listViewParents.setAdapter(adapterParents);
        registerForContextMenu(listViewParents);
        buttonAdd = (Button)findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Element[] x = {p1, p2};
                parentWIPList.add(x);
                adapterParents.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_parents, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create_parents_utils, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.itemDelete:
                parentWIPList.remove(info.position);
                adapterParents.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
