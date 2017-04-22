package com.example.caitlin.crealchemy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class CreateActivity extends Activity{

    Button buttonCreateParents, buttonDone;
    TextView textViewName;
    EditText editTextName;
    Element newElement;
    ElementList list;

    private static final String DEFAULT_TEXT = "Name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        CurrentState senderCreate = (CurrentState)getApplication();
        list = senderCreate.getElementList();
        if(senderCreate.getElement() == null){
            newElement = new Element("Name", GroupType.DEFAULT);
        } else newElement = senderCreate.getElement();
        textViewName = (TextView) findViewById(R.id.textViewElementName);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    textViewName.setText(editTextName.getText());
                } else {
                    textViewName.setText(DEFAULT_TEXT);
                }
            }
        });
        editTextName.setText(newElement.getName());
        buttonDone = (Button)findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add code here to add new Element to database if it has parents and a name
                if(newElement.hasParents() && newElement.hasName()){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    Intent intent1 = new Intent(getApplicationContext(), InsertNewElementIntentService.class);
                    newElement.setName(editTextName.getText().toString());
                    newElement.setWasCreated(true);
                    list.add(newElement);
                    CurrentState senderDone = (CurrentState)getApplication();
                    senderDone.setElement(newElement);
                    senderDone.setElementList(list);
                    startService(intent1);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.toast_invalid_element, Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonCreateParents = (Button) findViewById(R.id.buttonCreateParents);
        buttonCreateParents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCreateParents = new Intent(getApplicationContext(), CreateParentsActivity.class);
                if (textViewName.getText().toString().length() > 0) {
                    newElement.setName(textViewName.getText().toString());
                } else newElement.setName("name");
                CurrentState senderCreateParents = (CurrentState) getApplicationContext();
                senderCreateParents.setElement(newElement);
                senderCreateParents.setElementList(list);
                startActivity(intentCreateParents);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create, menu);
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
}
