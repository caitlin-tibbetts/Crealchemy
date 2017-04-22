package com.example.caitlin.crealchemy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class DeleteActivity extends Activity {
    Button buttonDone;
    ListView listViewElementsCreated;
    ElementList listAllElements, listCreatedElements, children;
    Element toDelete;
    Intent intent;
    ImageTextArrayAdapter gameArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        CurrentState sender = (CurrentState)getApplication();
        listAllElements = sender.getElementList();
        listCreatedElements = new ElementList();
        for(Element e : listAllElements){
            if(e.getWasCreated() == 1){
                listCreatedElements.add(e);
            }
        }
        buttonDone = (Button)findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Intent intent1 = new Intent(getApplicationContext(), UpdateElementListIntentService.class);
                CurrentState sender = (CurrentState)getApplication();
                sender.setElementList(listAllElements);
                startService(intent1);
                startActivity(intent);
            }
        });
        listViewElementsCreated = (ListView)findViewById(R.id.listViewElementsCreated);
        gameArrayAdapter = new ImageTextArrayAdapter(this, R.layout.horizontal_image_text_list_item_0, R.id.listTextView, listCreatedElements);
        listViewElementsCreated.setAdapter(gameArrayAdapter);
        registerForContextMenu(listViewElementsCreated);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delete, menu);
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
        inflater.inflate(R.menu.menu_delete_utils, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.itemDelete:
                intent = new Intent(getApplicationContext(), DeleteElementIntentService.class);
                CurrentState sender = (CurrentState)getApplication();
                for (Element e : listAllElements) {
                    for (Element[] e1 : e.getAllParents()) {
                        if ((e1[0].getName().equals(listCreatedElements.get(info.position).getName()) || e1[1].getName().equals(listCreatedElements.get(info.position).getName()))) {
                            if(!children.contains(e)){
                                children.add(e);
                            }
                        }
                    }
                }
                toDelete = listCreatedElements.get(info.position);
                sender.setElement(toDelete);
                sender.setToDelete(children);
                if(children.size() > 0){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle("Are you sure?");
                    String mes = "This action will also delete ";
                    for(Element e : children){
                        mes += e.getName() + " ";
                    }
                    alertDialogBuilder.setMessage(mes);
                    alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            listAllElements.removeAll(children);
                            listAllElements.remove(toDelete);
                            listCreatedElements.remove(toDelete);
                            listCreatedElements.removeAll(children);
                            gameArrayAdapter.notifyDataSetChanged();
                            startService(intent);
                        }
                    });
                    alertDialogBuilder.setCancelable(true);
                    alertDialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            children.clear();
                        }
                    });
                    alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
                    alertDialogBuilder.show();
                }
                listCreatedElements.remove(toDelete);
                listAllElements.remove(toDelete);
                gameArrayAdapter.notifyDataSetChanged();
                startService(intent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}
