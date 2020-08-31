package com.example.helloworld1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class FourthActivity extends AppCompatActivity
        implements View.OnClickListener{

    String pathToFile;
    ImageView imageView;
    Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        Button button = findViewById(R.id.button4);
        Button takePicture = findViewById(R.id.btncamera);
        if (Build.VERSION.SDK_INT>=23){
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        imageView = findViewById(R.id.image);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                goToFifthActivity();
            }

        });
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchPictureTakerAction();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 1){
                Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private void dispatchPictureTakerAction() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getPackageManager())!=null){

            File photoFile = null;
            photoFile = createPhotoFile();

            if (photoFile!=null){
                pathToFile = photoFile.getAbsolutePath();
                photoURI = FileProvider.getUriForFile(FourthActivity.this, "com.refugia.helloworld1.fileprovider", photoFile);
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePicture, 1);
            }

        }
    }

    private File createPhotoFile() {
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try{
            image = File.createTempFile(name, ".jpg", storageDir);
        }  catch (IOException e) {
            Log.d("mylog", "Excep:" + e.toString());
        }
        return image;
    }

    private void goToFifthActivity() {

        Intent intent = new Intent(this, FifthActivity.class);
        if (photoURI == null){
            intent.putExtra("uri", "Photo not taken");
        }
        else{
            intent.putExtra("uri", photoURI.toString());
        }
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        goToFifthActivity();
    }
}