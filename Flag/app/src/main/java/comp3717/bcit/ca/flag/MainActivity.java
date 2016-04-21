package comp3717.bcit.ca.flag;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick (View view){
        Locale current = getResources().getConfiguration().locale;
        Toast.makeText(MainActivity.this, current.toString(), Toast.LENGTH_SHORT).show();
        ImageView flag = (ImageView) findViewById(R.id.flag);
        TextView country = (TextView) findViewById(R.id.country);
        if (current.toString().equals("zh_CN")){
            flag.setImageResource(R.drawable.flag_china);
            country.setText("China");
        } else if (current.toString().equals("en_US")){
            flag.setImageResource(R.drawable.flag_us);
            country.setText("America");
        } else {
            flag.setImageDrawable(null);
            country.setText("");
        }
    }
}
