package comp3717.bcit.ca.button;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public final void buttonClick(View view){
        Button btn = (Button)findViewById(R.id.button);
        String s = btn.getText().toString();
        int i = Integer.valueOf(s).intValue();
        i++;
        String news = Integer.toString(i);
        btn.setText(news);

        AbsoluteLayout.LayoutParams absParams =
                (AbsoluteLayout.LayoutParams)btn.getLayoutParams();

        Random r = new Random();

        absParams.x =  r.nextInt(1000);
        absParams.y =  r.nextInt(1800);
        btn.setLayoutParams(absParams);
    }
}
