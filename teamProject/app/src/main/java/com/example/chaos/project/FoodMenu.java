package com.example.chaos.project;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chaos on 2016/1/25.
 */
public class FoodMenu extends AppCompatActivity {
    private ListView foodList = null;
    String name = null;
    String foodName[] =  new String[1024];
    Toolbar toolbar;
    TextView toolbarTitle;


    Object picture[] = { R.drawable.k1, R.drawable.k2, R.drawable.k3, R.drawable.k4
            , R.drawable.k5, R.drawable.k6, R.drawable.k7
            , R.drawable.k8, R.drawable.k9, R.drawable.k10
            , R.drawable.k11};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        View v = findViewById(R.id.toolbar);
        if (v != null) {
            toolbar = (Toolbar) v;
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.back_icon);
            toolbar.setNavigationOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Dialog a = new AlertDialog.Builder(FoodMenu.this)
                                    .setTitle("Warning")
                                    .setMessage("leave will delete all items in cart")
                                    .setPositiveButton("accept", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                                    .setNegativeButton("cansel", null)
                                    .show();
                        }
                    }
            );
            toolbarTitle = (TextView) v.findViewById(R.id.toolbar_title);

            if (toolbarTitle != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }

            Intent intent = getIntent();
                /*取出Intent中附加的数据*/
            name = intent.getStringExtra("name");
            // Toast.makeText(this, name, Toast.LENGTH_LONG).show();
            setTitle(name);
            setupViewComponent();
        }
    }
    private void setupViewComponent(){
        Resources res =getResources();
        if(name.equalsIgnoreCase("1")) {
             foodName =res.getStringArray(R.array.KFC);
        }else{
            foodName =res.getStringArray(R.array.Mcdonalds);
        }
        ArrayList<HashMap<String, Object>> imagelist = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 11; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("img",picture[i] );
            map.put("item",foodName[i]);
            imagelist.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, imagelist, R.layout.food_list,
                new String[] { "img", "item" },
                new int[] {R.id.img, R.id.item }
        );



        foodList =(ListView)findViewById(R.id.foodList);
        foodList.setAdapter(simpleAdapter);

        foodList.setTextFilterEnabled(true);

        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    final View     view,
                                    final int      position,
                                    final long     id) {

                Intent disIntent=new Intent(FoodMenu.this,ItemsActivity.class);
                disIntent.putExtra("item", foodName[position]);
                startActivity(disIntent);
            }

        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        //handle presses on the action bar item
        switch (item.getItemId()) {

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.action_cart:
                startActivity(new Intent(this, CheckOut.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cart, menu);
        return true;
    }
    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (toolbarTitle != null) {
            toolbarTitle.setText(title);
        }
    }
}
