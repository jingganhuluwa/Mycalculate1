package coding.android.mycalculate;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CountMain extends AppCompatActivity {
    static StringBuffer strBuff = new StringBuffer("");
    static StringBuffer strBuff2 = new StringBuffer("");
    private  EditText msg1,msg2;
    private  Button button;
    private ArrayAdapter adapter1,adapter2;
    private List alltime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_main);

        try {
            //屏蔽真机的Menu来显示右边overflow button按钮
            ViewConfiguration mconfig = ViewConfiguration.get(this);
            Field menuKeyField;
            menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(mconfig, false);
            }
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        final Spinner sp1=(Spinner)findViewById(R.id.spinner1);
        final Spinner sp2=(Spinner)findViewById(R.id.spinner2);
        final EditText m1=(EditText)findViewById(R.id.msg1);
        final EditText m2=(EditText)findViewById(R.id.msg2);
        final Button bt=(Button)findViewById(R.id.bt);
//        tranin(bt,m1,sp1,sp2);
        alltime=new ArrayList();
        adapter1=new ArrayAdapter(this, android.R.layout.simple_spinner_item,alltime);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sp1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView arg0, View arg1,int arg2, long arg3)
//            {
//				/* 将所选mySpinner的值带入myTextView中 */
//                m1.setText(arg0.getSelectedItem().toString());
//            }
//            @Override
//            public void onNothingSelected(AdapterView arg0)
//            {
//            }
//        });


        //计算器
        final EditText t1=(EditText)findViewById(R.id.msg);
        //功能按键
        final Button btnClr=(Button)findViewById(R.id.clear);
        final Button btnDel=(Button)findViewById(R.id.del);
        final Button btnPlu=(Button)findViewById(R.id.plus);
        final Button btnMin=(Button)findViewById(R.id.min);
        final Button btnMul=(Button)findViewById(R.id.mul);
        final Button btnDiv=(Button)findViewById(R.id.div);
        final Button btnEqu=(Button)findViewById(R.id.equ);

        //数字按键
        final Button num0=(Button)findViewById(R.id.num0);
        final Button num1 = (Button)findViewById(R.id.num1);
        final Button num2 = (Button)findViewById(R.id.num2);
        final Button num3 = (Button)findViewById(R.id.num3);
        final Button num4 = (Button)findViewById(R.id.num4);
        final Button num5 = (Button)findViewById(R.id.num5);
        final Button num6 = (Button)findViewById(R.id.num6);
        final Button num7 = (Button)findViewById(R.id.num7);
        final Button num8 = (Button)findViewById(R.id.num8);
        final Button num9 = (Button)findViewById(R.id.num9);
        final Button dot = (Button)findViewById(R.id.dot);

        //监听
        show1(dot,t1);
        show1(num0,t1);
        show1(num1,t1);
        show1(num2,t1);
        show1(num3,t1);
        show1(num4,t1);
        show1(num5,t1);
        show1(num6,t1);
        show1(num7,t1);
        show1(num8,t1);
        show1(num9,t1);
        jisuan(btnClr,t1);
        jisuan(btnDel,t1);
        jisuan(btnPlu,t1);
        jisuan(btnMin,t1);
        jisuan(btnMul,t1);
        jisuan(btnDiv,t1);
        jisuan(btnEqu,t1);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            // 弹出当前时间操作
            case R.id.action_time:
                setContentView(R.layout.trans);
            case R.id.action_speed:
                break;
            case R.id.action_count:
                setContentView(R.layout.activity_count_main);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.abar, menu);
        return true;
    }

//    public void tranin(final Button bt,final EditText m1,final Spinner sp1,final Spinner sp2){
//        bt.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//    }

    public void show1(final Button but,final EditText t1){
        but.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断开始是否可以按操作符
                if (but.getText().toString().equals(".")) {
                    if (strBuff.indexOf("+") != -1
                            || strBuff.indexOf("-") != -1
                            || strBuff.indexOf("×") != -1
                            || strBuff.indexOf("÷") != -1) {
                        strBuff.append(but.getText().toString());
                        t1.setText(strBuff);
                        //判断一个操作数中是否可以按多个点
                    } else {
                        if (strBuff.indexOf(".") == -1 && strBuff.length() != 0) {
                            strBuff.append(but.getText().toString());
                            t1.setText(strBuff);
                        }
                    }

                    //判断第一个按零，接着再按整数的情况时，屏幕的显示
                } else if (strBuff.indexOf("0") == 0
                        && strBuff.indexOf(".") == -1) {
                    strBuff.delete(0, strBuff.length());
                    strBuff.append(but.getText().toString());
                    t1.setText(strBuff);
                } else {
                    strBuff.append(but.getText().toString());
                    t1.setText(strBuff);
                    t1.setSelection(t1.getText().length());
                }
            }
        });
    }
    // 符号监听
    public void jisuan(final Button but1, final EditText t1) {
        but1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //存放输入的字符
                String newchar1 = but1.getText().toString();
                //判断开始是否为等号，输入一个数字后在输入等号没有反映
                String newchar = "";
                if (!(newchar1.equals("="))) {
                    newchar = newchar1;
                }

                float per1 = 0;//用来存放截取字符串的第一个操作数
                float per2 = 0;//用来存放截取字符串的第二个操作数
                //判断按的字符是否为重置键
                if (newchar.equalsIgnoreCase("c")) {
                    strBuff.delete(0, strBuff.length());
                    t1.setText("0");
                    return;
                }else if(newchar.equalsIgnoreCase("del")){
                    newchar=newchar.substring(0,newchar1.length()-3);
                    if (strBuff.length()>1)
                        strBuff.delete(strBuff.length()-1,strBuff.length());
                    else
                        strBuff.delete(0, strBuff.length());
                        t1.setText("0");
                }
                //判断按的操作符是什么计算字符
                //判断是否为加法
                if (strBuff.indexOf("+") != -1
                        && strBuff.indexOf("+") < strBuff.length() - 1
                        && strBuff.indexOf("+") != 0) {
                    int i = strBuff.indexOf("+");
                    per1 = Float.parseFloat(strBuff.substring(0, i));
                    per2 = Float.parseFloat(strBuff.substring(i + 1, strBuff
                            .length()));
                    float sum = per1 + per2;
                    strBuff.delete(0, strBuff.length());
                    strBuff.append(sum);
                    strBuff.append(newchar);
                    //判断是否为减法
                } else if (strBuff.indexOf("-") != -1
                        && strBuff.indexOf("-") < strBuff.length() - 1
                        && strBuff.indexOf("-") != 0) {
                    int i = strBuff.indexOf("-");
                    per1 = Float.parseFloat(strBuff.substring(0, i));
                    per2 = Float.parseFloat(strBuff.substring(i + 1, strBuff
                            .length()));
                    float sum = per1 - per2;
                    strBuff.delete(0, strBuff.length());
                    strBuff.append(sum);
                    strBuff.append(newchar);
                    //判断是否为乘法
                } else if (strBuff.indexOf("×") != -1
                        && strBuff.indexOf("×") < strBuff.length() - 1
                        && strBuff.indexOf("×") != 0) {
                    int i = strBuff.indexOf("×");
                    per1 = Float.parseFloat(strBuff.substring(0, i));
                    per2 = Float.parseFloat(strBuff.substring(i + 1, strBuff
                            .length()));
                    float sum = per1 * per2;
                    strBuff.delete(0, strBuff.length());
                    strBuff.append(sum);
                    strBuff.append(newchar);
                    //判断是否为除法
                } else if (strBuff.indexOf("÷") != -1
                        && strBuff.indexOf("÷") < strBuff.length() - 1
                        && strBuff.indexOf("÷") != 0) {
                    int i = strBuff.indexOf("÷");
                    per1 = Float.parseFloat(strBuff.substring(0, i));
                    per2 = Float.parseFloat(strBuff.substring(i + 1, strBuff
                            .length()));
                    //判断第二个操作数是否为零，为零则把显示框清零
                    if (per2 != 0) {
                        float sum = per1 / per2;
                        strBuff.delete(0, strBuff.length());
                        strBuff.append(sum);
                        strBuff.append(newchar);
                    } else {
                        strBuff.delete(0, strBuff.length());
                        strBuff.append("0");
                    }
                } else {
                    //判断操作符是否能接连不断的点击
                    if (strBuff.length() > 0
                            && ('0' <= strBuff.charAt(strBuff.length() - 1))
                            && ('9' >= strBuff.charAt(strBuff.length() - 1))) {
                        strBuff.append(newchar);
                    }
                }
                t1.setText(strBuff);
                t1.setSelection(t1.getText().length());
            }
        });

    }

    //public void trans
}
