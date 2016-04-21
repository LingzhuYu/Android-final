package com.example.chaos.project;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ItemsActivity extends AppCompatActivity {
    EditText t;
    TextView t3;
    Toolbar toolbar;
    TextView toolbarTitle;
    DBManager mgr;
    private String name;
    private Cursor c;
    private Integer oldcount;
    private float oriprice;
    private float price = 0;
    private String strPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.items);

        t = (EditText)findViewById(R.id.number);
        t3 = (TextView) findViewById(R.id.textView3);
        mgr = new DBManager(this);
        ImageView iv = (ImageView)findViewById(R.id.ItemImageView);
        View v = findViewById(R.id.toolbar);
        Intent intent = getIntent();
                /*取出Intent中附加的数据*/
        String title = intent.getStringExtra("item");
        name = title;
        if(name.equals("Hot Wings (5 pieces)")){
            iv.setImageResource( R.drawable.k1);
        }else if(name.equals("Hot Wings (10 pieces)")){
            iv.setImageResource( R.drawable.k2);
        }else if(name.equals("Chicken Bowl")){
            iv.setImageResource( R.drawable.k3);
        }else if(name.equals("Big Crunch")){
            iv.setImageResource( R.drawable.k4);
        }else if(name.equals("Big Crunch BBQ Bacon")){
            iv.setImageResource( R.drawable.k5);
        }else if(name.equals("Spicy Doublicious")){
            iv.setImageResource( R.drawable.k6);
        }else if(name.equals("Toasted Twister")){
            iv.setImageResource( R.drawable.k7);
        }else if(name.equals("Fries")){
            iv.setImageResource( R.drawable.k8);
        }else if(name.equals("Popcorn Cicken Snack Box")){
            iv.setImageResource( R.drawable.k9);
        }else if(name.equals("Strawberry Swirl Cheesecake")){
            iv.setImageResource( R.drawable.k10);
        }else{
            iv.setImageResource( R.drawable.k11);
        }
        TextView t2 = (TextView) findViewById(R.id.textView);
        t2.setText(name);
        c = mgr.queryOneCursor(name);
        oriprice = Float.parseFloat(t3.getText().toString());
        TextView t4 = (TextView) findViewById(R.id.textView2);

        if(c.getCount()!=0) {
            c.moveToFirst();
            oldcount =(int) c.getInt(2);
            price = (float) c.getFloat(3);

            t.setText(oldcount + "");
            t4.setText(price + "");

        }

        if (v != null) {
            toolbar = (Toolbar) v;
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.back_icon);
            toolbar.setNavigationOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Dialog a = new AlertDialog.Builder(ItemsActivity.this)
                                    .setTitle("Warning")
                                    .setMessage("leave will disable this order")
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
            setTitle(title);
            if (toolbarTitle != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }
    }
    public void add(View v){
        TextView t4 = (TextView) findViewById(R.id.textView2);
        price = Float.parseFloat(t4.getText().toString());
        price += oriprice;
        strPrice = Float.toString(price);
        t4.setText(strPrice);

        int n = Integer.parseInt(t.getText().toString());
        n++;
        t.setText(n + "");
    }

    public void update(View v){
        Cart carts = new Cart();
        TextView t4 = (TextView) findViewById(R.id.textView2);
        int n = Integer.parseInt(t.getText().toString());
        float p = Float.parseFloat(t4.getText().toString());

        carts.item_name = name;
        carts.count = n;
        carts.price = p;
        //Toast.makeText(this,carts.count+"",Toast.LENGTH_SHORT).show();

        if(oldcount != null) {
            mgr.update(carts);
        }else {
            mgr.add(carts);
        }

        finish();
    }
    public void minus(View v){
        TextView t4 = (TextView) findViewById(R.id.textView2);
        price = Float.parseFloat(t4.getText().toString());
        if(price > 0) {
            price -= oriprice;
            strPrice = Float.toString(price);
            t4.setText(strPrice);
        }

        t.setInputType(InputType.TYPE_CLASS_NUMBER);
        int n = Integer.parseInt(t.getText().toString());
        if(n > 0){
            n--;
            t.setText(n + "");
        }

    }
    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (toolbarTitle != null) {
            toolbarTitle.setText(title);
        }
    }
}
