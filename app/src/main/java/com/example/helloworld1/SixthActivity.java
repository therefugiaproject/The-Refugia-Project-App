package com.example.helloworld1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SixthActivity extends AppCompatActivity
        implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixth);
        Button button = findViewById(R.id.button6);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                goToMainActivity();
            }

        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        goToMainActivity();
    }
}