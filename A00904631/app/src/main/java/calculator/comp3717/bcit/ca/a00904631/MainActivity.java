package calculator.comp3717.bcit.ca.a00904631;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    String lastStr = "", num = "0", result, operator = "";
    double newNum = 0.0, curNum;
    CharSequence c;

    public void btnClick(final View view) {
        final String str;
        final TextView textView;

        str = (String) view.getTag();
        textView = (TextView) findViewById(R.id.textView2);

        if (str.equals("=")) {
            curNum = Double.parseDouble(num);
            switch (operator) {
                case ("+"):
                    newNum += curNum;
                    break;
                case ("-"):
                    newNum -= curNum;
                    break;
                case ("*"):
                    newNum *= curNum;
                    break;
                case ("/"):
                    newNum /= curNum;
                    break;
                default:
                    newNum = curNum;
                    break;
            }
            if(newNum - (int)newNum == 0){
                int n = (int)newNum;
                result = Integer.toString(n);
            }else{
                result = Double.toString(newNum);
            }
            num = result;
            textView.setText(result);
            operator = str;
            lastStr = str;
        }else if(str.equals(",")){
            lastStr = "";
            num = "0";
            operator = "";
            newNum = 0.0;
            textView.setText("");
        }else{
            if (str.equals("+")||str.equals("-")||str.equals("*")||str.equals("/")) {
                if (!lastStr.equals("+") && !lastStr.equals("-")
                        && !lastStr.equals("*") && !lastStr.equals("/")) {
                    curNum = Double.parseDouble(num);
                    num = "";
                    lastStr = str;
                    switch (operator) {
                        case ("+"):
                            newNum += curNum;
                            break;
                        case ("-"):
                            newNum -= curNum;
                            break;
                        case ("*"):
                            newNum *= curNum;
                            break;
                        case ("/"):
                            newNum /= curNum;
                            break;
                        default:
                            newNum = curNum;
                            break;
                    }
                    operator = str;
                    c = textView.getText();
                    textView.setText(textView.getText() + str);
                }else{
                    textView.setText(c + str);
                    operator = str;
                }
            } else {
                lastStr = str;
                num += str;
                textView.setText(textView.getText() + str);
            }
        }
    }
}
