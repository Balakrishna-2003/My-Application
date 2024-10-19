package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class fill_detail extends AppCompatActivity {

    EditText timee,namee,deste;
    public static File data = new File("storage/emulated/0/Alarms/newfle.txt");



    @Override
    public void onBackPressed() {
        Intent n = new Intent(fill_detail.this,MainActivity.class);

        startActivity(n);
        finish();
        super.onBackPressed();
    }

    //sorting the file based on date and time
    public void sorting(){
        int ch,count=0;
        String s = "";
        try{
            //Reading all the data and storing it to the String s
            FileReader f1 = new FileReader(data.getPath());
            while ((ch=f1.read())!=-1) {
                if(ch == '\n'){
                    count++;
                    s+=".";
                }else{
                    s += (char) ch;
                }
            }
            System.out.println("s her "+s);

            String arr[][] = new String[count][2];
            int ac = 0;
            int cn = 0;
            String temp = "",temp1 = "";


            try {
                while(cn < s.length())
                {
                    try{
                        while(s.charAt(cn) != '.')
                        {
                                if(s.charAt(cn) >= '0' && s.charAt(cn) <= '9'){
                                    temp1 += s.charAt(cn);
                                }
                                else if(s.charAt(cn) != ':'){
                                    temp+=s.charAt(cn);
                            }
                            cn++;

                        }
                    }catch(Exception e){
                        System.out.println("inner Exception 1: "+e);
                    }



                    arr[ac][0] = temp1;
                    arr[ac][1] = temp;
                    temp = temp1 = "";
                    ac++;
                    cn++;
                }
            }catch(Exception e){
                System.out.println("Exception 1: "+e);
            }



            int atemp[] = new int[count];
            for(int i = 0; i < count; i++){
                atemp[i] = Integer.parseInt(arr[i][0]);
            }
            Arrays.sort(atemp);
//            for(int i = 0; i <  atemp.length; i++){
//                System.out.println(atemp[i]);
//            }

            int t = 0;
//            int it = 0;
            String tarr[] = new String[count];
            try{
                for(int i = 0; i < count; i++){
//                String result ="";
                    while(!String.valueOf(atemp[i]).equals(arr[t][0])){
                        t++;
                    }

                    String tres = arr[t][0];
//                int trick = Integer.parseInt(tres);
//                if(trick > 1259){
//                    tres = String.valueOf(trick - 1200);
////                    System.out.println(trick+" "+tres);
//                }

//                it = 0;
//                if(tres.length() > 3){
//                    try {
//                        while(it < tres.length()){
//                            if(it == 2){
//                                result+=':';
//                            }
//                            result+=tres.charAt(it);
//                            it++;
//                        }
//                    }catch(Exception e){
//                        System.out.println("Exception0 "+e);
//                    }
//                }
//                else{
//                    try{
//                        while(it < tres.length()){
//                            if(it == 1){
//                                result += ':';
//                            }
//                            result += tres.charAt(it);
//                            it++;
//                        }
//                    }catch(Exception e){
//                        System.out.println("Exception 2 "+e);
//                    }
//                }




                    tarr[i] = arr[t][1];
//                it=0;
                    t = 0;
                }
            }catch(Exception e ){
                System.out.println("this error "+e);
            }

//
            FileWriter nfile = new FileWriter(data.getPath());
            nfile.write("");
            nfile = new FileWriter(data.getPath(),true);
            try{
                for(int i = 0; i < count; i++){
                    nfile.append(tarr[i]+"\n");
                }
            }catch(Exception e){
                System.out.println("inner most error");
            }

            nfile.close();

        }catch(Exception e){
            Toast.makeText(this, "file error", Toast.LENGTH_SHORT).show();
            System.out.println(e);
        }

    }
    //end of sorting


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fill_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        Toast.makeText(this, "test "+data.length(), Toast.LENGTH_SHORT).show();






        if(!data.exists()){
            try {
                data.createNewFile();
            } catch (IOException e) {
                Toast.makeText(fill_detail.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }

        Date n = new Date();
        String time;

        String hour = String.valueOf(n.getHours());
        String minutes = String.valueOf(n.getMinutes());

        if(n.getMinutes() < 10){
            minutes = "";
            minutes += '0';
            minutes += n.getMinutes();
        }

        if(n.getHours() > 12){
            time = hour+":"+minutes+"pm";
        }else{
            time = hour+":"+minutes+"am";
        }
        System.out.println("time here "+time);

//        String stime = time;
//        if(n.getHours() > 12){
//            stime = String.valueOf(n.getHours()-12)+":"+minutes+" PM";
//        }


        timee = findViewById(R.id.timeID);
        SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mma");
        SimpleDateFormat sdf24 = new SimpleDateFormat("HH:mm");
        String time12h = time;

        //10:20am
        // Parse the 12-hour format time
        Date date = null;
        try {
            date = sdf12.parse(time12h);
        } catch (Exception e) {
            System.out.println("here "+e);
        }
        // Format it to 24-hour format
        String time24h = sdf24.format(date);
        if(time12h.charAt(time12h.length()-2) == 'a'|| time12h.charAt(time12h.length()-2) == 'A'){
            time24h +="am";
        } else if (time12h.charAt(time12h.length()-2) == 'p' || time12h.charAt(time12h.length()-2) == 'P') {
            time24h += "pm";
        }
        System.out.println(time24h);
        timee.setText(time24h);
        namee = findViewById(R.id.nameID);
        deste = findViewById(R.id.destID);



        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!timee.getText().toString().isEmpty() && !namee.getText().toString().isEmpty() && !deste.getText().toString().isEmpty()){
                    try {
                        FileWriter writ = new FileWriter(data.getPath(),true);
                        String time12h = String.valueOf(timee.getText());
                        String bName = String.valueOf(namee.getText());
                        String bDest = String.valueOf(deste.getText());

                        SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mma");
                        SimpleDateFormat sdf24 = new SimpleDateFormat("HH:mm");

                        // Parse the 12-hour format time
                        java.util.Date date = sdf12.parse(time12h);
                        // Format it to 24-hour format
                        String time24h = sdf24.format(date);
                        if(time12h.charAt(time12h.length()-2) == 'a'|| time12h.charAt(time12h.length()-2) == 'A'){
                            time24h +="am";
                        } else if (time12h.charAt(time12h.length()-2) == 'p' || time12h.charAt(time12h.length()-2) == 'P') {
                            time24h += "pm";
                        }
                        System.out.println("time there"+time24h);
                        writ.append(time24h+" "+bName+" "+bDest+"\n");
//                      writ.append(String.valueOf(timee.getText())).append(" ").append(String.valueOf(namee.getText())).append(" ").append(String.valueOf(deste.getText())).append("\n");
                        writ.close();
                        sorting();

                    } catch (Exception e) {
                        System.out.println("outer Exception "+e);
                    }
                    Intent inte = new Intent(fill_detail.this,MainActivity.class);
//                    sorting();
                    startActivity(inte);
                    finish();

                }else{
                    Toast.makeText(fill_detail.this,"Please enter all the details" ,Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}