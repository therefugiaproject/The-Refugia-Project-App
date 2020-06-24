package com.example.helloworld1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ExtraActivity extends AppCompatActivity
        implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);
        Button button = findViewById(R.id.button9);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                goToThirdActivity();
            }

        });

    }

    private void goToThirdActivity() {
        Intent intent = new Intent(this, ThirdActivity.class);

        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        goToThirdActivity();
    }
}