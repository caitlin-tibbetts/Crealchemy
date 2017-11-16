package com.example.caitlin.crealchemy;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Assert;

import java.util.ArrayList;

public class GameActivity extends Activity {
    ElementList listAllElements, listFoundElements = new ElementList();
    ImageTextArrayAdapter gameArrayAdapter;
    RelativeLayout relativeLayoutGame;
    ListView listViewFoundElements;
    TextView textViewParent1, textViewParent2, textViewOne, textViewTwo;
    ImageView imageViewParent1, imageViewParent2;
    Button buttonCombine, buttonDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toast.makeText(getApplicationContext(), "Long-click and drag the elements to the numbers to play.", Toast.LENGTH_LONG).show();
        CurrentState senderGame = (CurrentState)getApplication();
        listAllElements = senderGame.getElementList();
        buttonDone = (Button)findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Intent intent1 = new Intent(getApplicationContext(), UpdateElementListIntentService.class);
                CurrentState senderGame = (CurrentState) getApplication();
                if (senderGame.getElement() == null) {
                    senderGame.setElement(new Element("name", GroupType.DEFAULT));
                }
                senderGame.setElementList(listAllElements);
                startService(intent1);
                startActivity(intent);
            }
        });
        relativeLayoutGame = (RelativeLayout)findViewById(R.id.relativeLayoutGame);
        textViewOne = (TextView)findViewById(R.id.textViewOne);
        textViewTwo = (TextView)findViewById(R.id.textViewTwo);
        imageViewParent1 = (ImageView)findViewById(R.id.imageViewParent1);
        imageViewParent1.setVisibility(View.INVISIBLE);
        imageViewParent2 = (ImageView)findViewById(R.id.imageViewParent2);
        imageViewParent2.setVisibility(View.INVISIBLE);
        textViewParent1 = (TextView)findViewById(R.id.textViewParent1);
        textViewParent1.setVisibility(View.INVISIBLE);
        textViewParent2 = (TextView)findViewById(R.id.textViewParent2);
        textViewParent2.setVisibility(View.INVISIBLE);
        buttonCombine = (Button)findViewById(R.id.buttonCombine);
        buttonCombine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Element> children = new ArrayList<>();
                for (Element e : listAllElements) {
                    for (Element[] e1 : e.getAllParents()) {
                        if ((e1[0].getName().equals(textViewParent1.getText()) && e1[1].getName().equals(textViewParent2.getText()) || (e1[1].getName().equals(textViewParent1.getText()) && e1[0].getName().equals(textViewParent2.getText())))) {
                            if (!children.contains(e)) {
                                children.add(e);
                            }
                        }
                    }
                }
                if (children.size() > 0) {
                    StringBuilder build = new StringBuilder();
                    for (Element e2 : children) {
                        build.append(e2.getName());
                        build.append(" ");
                        listAllElements.get(listAllElements.indexOf(e2)).setIsFound(true);
                        if (!listFoundElements.contains(e2)) {
                            listFoundElements.add(e2);
                        }
                    }
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_found_element, build.toString()), Toast.LENGTH_SHORT).show();
                    AchievementList curList = new AchievementList(listAllElements);
                    CurrentState cur = (CurrentState)getApplication();
                    AchievementList oldList = cur.getAchievementList();
                    if(oldList == null || !oldList.equals(curList)){
                        Toast.makeText(getApplicationContext(), getString(R.string.toast_new_achievement), Toast.LENGTH_SHORT).show();
                    }
                    cur.setAchievementList(curList);
                } else
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_not_such_element), Toast.LENGTH_SHORT).show();
                gameArrayAdapter.notifyDataSetChanged();
                textViewOne.setVisibility(View.VISIBLE);
                textViewTwo.setVisibility(View.VISIBLE);
                textViewParent1.setVisibility(View.INVISIBLE);
                textViewParent2.setVisibility(View.INVISIBLE);
                imageViewParent1.setVisibility(View.INVISIBLE);
                imageViewParent2.setVisibility(View.INVISIBLE);
            }
        });

        listViewFoundElements = (ListView)findViewById(R.id.listViewGameList);
        gameArrayAdapter = new ImageTextArrayAdapter(this, R.layout.vertical_image_text_list_item_0, listFoundElements);
        listViewFoundElements.setAdapter(gameArrayAdapter);
        if(listAllElements != null && listAllElements.size() > 0){
            for(Element e : listAllElements){
                if(e.getIsFound() == 1 || (e.getName().equals("Water") || e.getName().equals("Fire") || e.getName().equals("Earth") || e.getName().equals("Air"))){
                    if(!listFoundElements.contains(e)) {
                        listFoundElements.add(e);
                    }
                }
            }
        }
        gameArrayAdapter.notifyDataSetChanged();

        listViewFoundElements.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
             @RequiresApi(api = Build.VERSION_CODES.N)
             @Override
             public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                 Element selectedItem = (Element) (parent.getItemAtPosition(position));
                 ElementToPass passObj = new ElementToPass(selectedItem);
                 ClipData data = ClipData.newPlainText("", "");
                 View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                 view.startDrag(data, shadowBuilder, passObj, 0);
                 return false;
            }
        });

         listViewFoundElements.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(textViewOne.getVisibility() == View.VISIBLE){
                    textViewParent1.setText(((Element)parent.getItemAtPosition(position)).getName());
                    textViewOne.setVisibility(View.INVISIBLE);
                    if (getDrawable(getApplicationContext(), ((Element)parent.getItemAtPosition(position)).getName()) == 0) {
                        imageViewParent1.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.placeholder));
                    } else {
                        imageViewParent1.setImageBitmap(BitmapFactory.decodeResource(getResources(), getDrawable(getApplicationContext(), ((Element)parent.getItemAtPosition(position)).getName())));
                    }
                    textViewParent1.setVisibility(View.VISIBLE);
                    imageViewParent1.setVisibility(View.VISIBLE);
                } else if(textViewTwo.getVisibility() == View.VISIBLE){
                    textViewParent2.setText(((Element)parent.getItemAtPosition(position)).getName());
                    textViewTwo.setVisibility(View.INVISIBLE);
                    if (getDrawable(getApplicationContext(), ((Element)parent.getItemAtPosition(position)).getName()) == 0) {
                        imageViewParent2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.placeholder));
                    } else {
                        imageViewParent2.setImageBitmap(BitmapFactory.decodeResource(getResources(), getDrawable(getApplicationContext(), ((Element)parent.getItemAtPosition(position)).getName())));
                    }
                    textViewParent2.setVisibility(View.VISIBLE);
                    imageViewParent2.setVisibility(View.VISIBLE);
                }
            }
        });

        textViewOne.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        // do nothing
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        break;
                    case DragEvent.ACTION_DROP:
                        ElementToPass passObj = (ElementToPass) event.getLocalState();
                        textViewParent1.setText(passObj.getElement().getName());
                        textViewOne.setVisibility(View.INVISIBLE);
                        if (getDrawable(getApplicationContext(), passObj.getElement().getName()) == 0) {
                            imageViewParent1.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.placeholder));
                        } else {
                            imageViewParent1.setImageBitmap(BitmapFactory.decodeResource(getResources(), getDrawable(getApplicationContext(), passObj.getElement().getName())));
                        }
                        textViewParent1.setVisibility(View.VISIBLE);
                        imageViewParent1.setVisibility(View.VISIBLE);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        textViewTwo.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        // do nothing
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        break;
                    case DragEvent.ACTION_DROP:
                        ElementToPass passObj = (ElementToPass) event.getLocalState();
                        textViewParent2.setText(passObj.getElement().getName());
                        textViewTwo.setVisibility(View.INVISIBLE);
                        if(getDrawable(getApplicationContext(), passObj.getElement().getName()) == 0){
                            imageViewParent2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.placeholder));
                        } else {
                            imageViewParent2.setImageBitmap(BitmapFactory.decodeResource(getResources(), getDrawable(getApplicationContext(), passObj.getElement().getName())));
                        }
                        textViewParent2.setVisibility(View.VISIBLE);
                        imageViewParent2.setVisibility(View.VISIBLE);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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

    public static int getDrawable(Context context, String name) {
        Assert.assertNotNull(context);
        Assert.assertNotNull(name);
        return context.getResources().getIdentifier(name.toLowerCase().replaceAll(" ", ""),
                "drawable", context.getPackageName());
    }

    private class ElementToPass {
        Element element;

        ElementToPass(Element element){
            this.element = element;
        }

        public Element getElement(){
            return element;
        }
    }
}