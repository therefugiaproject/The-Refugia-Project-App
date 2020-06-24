package com.example.helloworld1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FourthActivity extends AppCompatActivity
        implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        Button button = findViewById(R.id.button4);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                goToFifthActivity();
            }

        });
    }

    private void goToFifthActivity() {

        Intent intent = new Intent(this, FifthActivity.class);

        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        goToFifthActivity();
    }
}