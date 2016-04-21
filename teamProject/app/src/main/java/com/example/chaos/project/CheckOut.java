package com.example.chaos.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckOut extends AppCompatActivity {
    Object picture[] = { R.drawable.k1, R.drawable.k2, R.drawable.k3, R.drawable.k4
            , R.drawable.k5, R.drawable.k6, R.drawable.k7
            , R.drawable.k8, R.drawable.k9, R.drawable.k10
            , R.drawable.k11};
    private ListView cartList = null;
    Toolbar toolbar;
    TextView toolbarTitle;
    TextView tp;
    //private DBManager mgr;//!!!
    private Double total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_out);

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
            setTitle("cart");
            if (toolbarTitle != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }

        setupViewComponent();
    }
    private void setupViewComponent(){
        ArrayList<HashMap<String, Object>> imagelist = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("img",picture[4] );
        map.put("item","Big Crunch BBQ Bacon");
        map.put("number","5");
        imagelist.add(map);


        SimpleAdapter simpleAdapter = new SimpleAdapter(this, imagelist,
                R.layout.cart_item, new String[] { "img", "item","number" }, new int[] {
                R.id.cartimage, R.id.carttitle,R.id.cartnumber });

        cartList =(ListView)findViewById(R.id.cartItem);
        cartList.setAdapter(simpleAdapter);

        cartList.setTextFilterEnabled(true);

    }

    public void confirm(View v){
        Toast.makeText(this,"Ordered successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (toolbarTitle != null) {
            toolbarTitle.setText(title);
        }
    }
}
