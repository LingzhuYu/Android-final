package com.example.chaos.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chaos on 2016/1/25.
 */
public class Resturant extends AppCompatActivity {
    String name[] = {"KFC","A&W","Mcdonalds","pizza hut"};
    Object picture[] = { R.drawable.kfc ,R.drawable.aw,R.drawable.mcdonalds,R.drawable.pizzahut};
    Toolbar toolbar;
    TextView toolbarTitle;
    private  GridView gridview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant);

        View v = findViewById(R.id.toolbar);
        if (v != null) {
            toolbar = (Toolbar) v;
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.back_icon);
            toolbar.setNavigationOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    }
            );
            toolbarTitle = (TextView) v.findViewById(R.id.toolbar_title);
            setTitle("Restaurants");
            if (toolbarTitle != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }

        gridview = (GridView) findViewById(R.id.resturantView);
        ArrayList<HashMap<String, Object>> imagelist = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 4; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("image",picture[i] );
            map.put("text",name[i]);
            imagelist.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, imagelist,
                R.layout.grid_list_item, new String[] { "image", "text" }, new int[] {
                R.id.image, R.id.title });

        gridview.setAdapter(simpleAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent disIntent=new Intent(Resturant.this,FoodMenu.class);
                disIntent.putExtra("name", name[position]);
                startActivity(disIntent);

            }});
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (toolbarTitle != null) {
            toolbarTitle.setText(title);
        }
    }

}
