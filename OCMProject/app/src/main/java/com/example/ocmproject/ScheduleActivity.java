package com.example.ocmproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import org.opencv.core.Core;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity {

    // Yavuz was here
    // Also alp was
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_upload_screen);
        Button scheduleUploadButton = findViewById(R.id.scheduleUploadButton);
        Button manualScheduleUploadButton = findViewById(R.id.manualScheduleUploadButton);
        Button geciciButon = findViewById(R.id.geciciButon);
        Button geciciButon2 = findViewById(R.id.geciciButon2);

        OpenCVLoader.initDebug();
//        for(String course: courses){
//            System.out.println(course);

        scheduleUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });

        manualScheduleUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScheduleActivity.this, ManualScheduleActivity.class));
            }
        });

        geciciButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScheduleActivity.this, ProfileActivity.class));
            }
        });
        geciciButon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScheduleActivity.this, CollectionsActivity.class));
            }
        });

    }

    // The image is shown
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null){
            Uri imageChoosen = data.getData();
            Log.i("Image string", data.toString());
            ImageView scheduleView = findViewById(R.id.scheduleView);
            scheduleView.setImageURI(imageChoosen);
            //scheduleView.setImageBitmap();
            Bitmap photo = null;
            try {
                photo = (Bitmap) MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageChoosen);
            }
            catch (Exception e)
            {
                Log.e("Exception", e.toString());
            }
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // must be in main
            ScheduleReader scheduler = new ScheduleReader(photo);
            scheduler.runReader();

            //scheduler.newThread();
            }


        }

    }













