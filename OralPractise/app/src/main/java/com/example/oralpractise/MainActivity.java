package com.example.oralpractise;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public Context context;
    public Button mainbutton,play;
    public Myadapter myadapter;
    public ListView mlistview;




    private static final String LOG_TAG = "AudioRecordTest";




     public MediaRecorder recorder;

public File file;
    private MediaPlayer player = null;




    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        {

            int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
            if (ContextCompat.checkSelfPermission(this,

                    Manifest.permission.RECORD_AUDIO)

                    != PackageManager.PERMISSION_GRANTED) {


                // Should we show an explanation?

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,

                        Manifest.permission.RECORD_AUDIO)) {


                    // Show an expanation to the user *asynchronously* -- don't block

                    // this thread waiting for the user's response! After the user

                    // sees the explanation, try again to request the permission.


                } else {


                    // No explanation needed, we can request the permission.


                    ActivityCompat.requestPermissions(this,

                            new String[]{Manifest.permission.RECORD_AUDIO},

                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);


                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an

                    // app-defined int constant. The callback method gets the

                    // result of the request.


                }

            }
        }
        {

            int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
            if (ContextCompat.checkSelfPermission(this,

                    Manifest.permission.WRITE_EXTERNAL_STORAGE)

                    != PackageManager.PERMISSION_GRANTED) {


                // Should we show an explanation?

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,

                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {


                    // Show an expanation to the user *asynchronously* -- don't block

                    // this thread waiting for the user's response! After the user

                    // sees the explanation, try again to request the permission.


                } else {


                    // No explanation needed, we can request the permission.


                    ActivityCompat.requestPermissions(this,

                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},

                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);


                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an

                    // app-defined int constant. The callback method gets the

                    // result of the request.


                }

            }
        }



        context= getApplicationContext();
        mlistview = findViewById(R.id.medialist);
        myadapter = new Myadapter(context);
        mlistview.setAdapter(myadapter);


        mainbutton = findViewById(R.id.recordbutton);
        mainbutton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (mainbutton.getText() != "stop record"){

                recorder= new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                String filename =new SimpleDateFormat("hh-mm-ss'.mp3'").format(new Date());
                 file = new File(getFilesDir(), filename);
                recorder.setOutputFile(file);
                try {
                    recorder.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.println(Log.VERBOSE,"pre","failed");
                }




                recorder.start();
                Toast.makeText(context,"Started",Toast.LENGTH_SHORT).show();
                mainbutton.setText("stop record");}
                else { recorder.stop();
                mainbutton.setText("start record");
                myadapter.addmedia(file);
                }


            }
                       }
        );

    }

    @Override
    protected void onPause() {
        while (myadapter.getCount()>0){myadapter.remove(0);}
        super.onPause();
    }
}