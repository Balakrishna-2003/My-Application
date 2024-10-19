package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnLongClickListener;

import androidx.activity.EdgeToEdge;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

//    private final String storage = Manifest.permission.READ_MEDIA_IMAGES;
private static final String storage = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static String s,sr = "",s1,r = "";
    public static int c = 0,cn = 0;
    public static TextView ntext;



@SuppressLint("MissingSuperCall")
@Override
public void onBackPressed() {
    finish();
//    super.onBackPressed();
}

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        Toast.makeText(this,"long pressed", Toast.LENGTH_SHORT).show();
        return super.onKeyLongPress(keyCode, event);
    }


    public String convertDate(String text){
        String time12h = text;
        SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mma");
        SimpleDateFormat sdf24 = new SimpleDateFormat("HH:mm");

        try {
            // Parse the 12-hour format time
            java.util.Date date = sdf12.parse(time12h);

            // Format it to 24-hour format
            String time24h = sdf24.format(date);
            if(time12h.charAt(time12h.length()-2) == 'a'|| time12h.charAt(time12h.length()-2) == 'A'){
                time24h +="am";
            } else if (time12h.charAt(time12h.length()-2) == 'p' || time12h.charAt(time12h.length()-2) == 'P') {
                time24h += "pm";
            }
            return time24h;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



        LinearLayout li = findViewById(R.id.linear);
        li.removeAllViews();
        li.setBackgroundColor(
                Color.rgb(0,0,0)
        );

        Button btn = findViewById(R.id.addTextView);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            if(!Environment.isExternalStorageManager()){
                try{
                    Intent intent1 = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    intent1.addCategory("android.intent.category.DEFAULT");
                    intent1.setData(Uri.parse(String.format("packagae:%s",getApplicationContext().getPackageName())));
                    startActivityIfNeeded(intent1,101);
                }catch(Exception e){
                    Intent intent2 = new Intent();
                    intent2.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivityIfNeeded(intent2,101);
                    Toast.makeText(MainActivity.this,"error "+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }

        try {
            FileReader f = new FileReader("storage/emulated/0/Alarms/newfle.txt");
            s = "";
            int count = 0;
            int ch;

            //reads all the data from file
            while ((ch=f.read())!=-1) {
                s += (char) ch;
            }
            //adds fullStop(.) to the end of the line
            while(count <= s.length()-1){

                if(s.charAt(count) == '\n'){
                    sr += ".";
                }
                else{
                    sr += s.charAt(count);
                }
                count++;
            }
            // close the file
            f.close();
        } catch (Exception e) {
            Toast.makeText(this,"error here "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }


        String temp = "",rtemp ="";
        int cont = 0;
            while(c <= sr.length()-1) {
                try {
                    while (sr.charAt(c) != '.' && c < sr.length()-1) {
                       if(cont == 0 && sr.charAt(c) != ' '){
                           rtemp += sr.charAt(c);
                       }else{
                           temp += sr.charAt(c);
                           cont++;
                       }
                        c++;
                    }
                    cont = 0;
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "enw " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }


                String insertText = convertDate(rtemp);
                rtemp = "";

                ntext = new TextView(MainActivity.this);
                ntext.setTextSize(20);
                ntext.setBackground(getDrawable(R.drawable.border_black));
                ntext.setPadding(40, 20, 20, 25);
                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                buttonLayoutParams.setMargins(5, 15, 5, 0);
                ntext.setLayoutParams(buttonLayoutParams);
                ntext.setTextColor(getColor(android.R.color.white));
                ntext.setText(insertText+temp);
                ntext.setClickable(true);
                temp = "";
                ntext.setMinWidth(100);
                ntext.setMaxWidth(170);
                li.addView(ntext);

                c++;




                ntext.setOnLongClickListener(new OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View v) {

                        AlertDialog.Builder build = new AlertDialog.Builder(MainActivity.this);
                        build.setMessage("do you really want to delete this Note ").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String r1 =  ((TextView) v).getText().toString();
                                li.removeView(v);

                                try {
                                    FileReader re = new FileReader(fill_detail.data.getPath());
                                    s1 = "";
                                    r = "";
                                    int ch;

                                    while ((ch = re.read()) != -1) {
                                        s1 += (char) ch;
                                    }
                                    int count = 0;
                                    while (count <= s1.length() - 1) {
                                        if (s1.charAt(count) == '\n') {
                                            r += ".";
                                        } else {
                                            r += s1.charAt(count);
                                        }
                                        count++;
                                    }
                                    re.close();

                                } catch (Exception e) {
                                    Toast.makeText(MainActivity.this,"error "+e,Toast.LENGTH_LONG).show();
                                }
                                try {
                                    String temp1 = "";
                                    int cn = 0;
                                    FileWriter update = new FileWriter(fill_detail.data.getPath());
                                    update.write("");
                                    update = new FileWriter(fill_detail.data.getPath(), true);


                                    while (cn < r.length()) {
                                        while (r.charAt(cn) != '.' && cn < r.length()) {
                                            temp1 += r.charAt(cn);
                                            cn++;
                                        }
                                        if (!temp1.equals(r1)) {
                                            update.append(temp1+"\n");
                                        }

                                        temp1 = "";
//                                    if(cn <= s1.length()-1)
//                                    {
                                        cn++;
//                                    }

                                    }
                                    Toast.makeText(MainActivity.this, "note deleted", Toast.LENGTH_SHORT).show();

                                    update.close();
                                }catch(Exception e){
                                    Toast.makeText(MainActivity.this, "error "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }


                            };
                        }).setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(MainActivity.this, "canceled the event", Toast.LENGTH_SHORT).show();
                            }
                        });
                        AlertDialog nbi = build.create();
                        nbi.show();

                        return true;

                    }
                });





//                ntext.setOnTouchListener(new View.OnTouchListener() {
//                    GestureDetector ges = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
//
//
//                        @Override
//                        public void onLongPress( MotionEvent e) {
//                            Toast.makeText(MainActivity.this, "you have long pressed the button", 0).show();
//
//                            super.onLongPress(e);
//                        }
//
//
//                        public boolean onDoubleTap(View v, MotionEvent e) {
//                            Toast.makeText(getApplicationContext(), ntext.getText(), 0).show();
//                            return super.onDoubleTap(e);
//                        }
//
//
//                    });
//
//
//
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
////                        li.removeView(v);
//                        ges.onTouchEvent(event);
//                        return false;
//                    }
//                });


            }




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intes = new Intent(MainActivity.this,fill_detail.class);
                startActivity(intes);
}
        });


    }
//    public void showToast(String text){
//        TextView tem = findViewById(R.id.toastid);
//        tem.setText(text);
//        Context conste = getApplicationContext();
//        LayoutInflater inflater = getLayoutInflater();
//        View ToastRoot = inflater.inflate(R.layout.toast,null);
//        Toast toastf = new Toast(conste);
//        toastf.setView(ToastRoot);
//        toastf.setDuration(Toast.LENGTH_LONG);
//        toastf.show();
//    }
}