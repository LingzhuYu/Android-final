package comp3717.bcit.ca.myapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class killme extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_killme);
        sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
        String num =sharedPreferences.getString("num", "");
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(num);
    }

    public void btnClick(View v){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        TextView tv = (TextView) findViewById(R.id.textView);
        String n = tv.getText().toString();
        int i = Integer.valueOf(n).intValue();
        i++;
        n = Integer.toString(i);
        editor.putString("num", n);
        editor.commit();

        System.exit(0);
    }

    public void changeColor(View v){
        Button btn = (Button) findViewById(R.id.button2);
        String n2 = btn.getTag().toString();
        Intent intent = new Intent();
        intent.putExtra("message", n2);
        sendBroadcast(intent);
    }
}
