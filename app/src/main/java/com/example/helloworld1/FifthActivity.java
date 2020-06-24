package com.example.helloworld1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FifthActivity extends AppCompatActivity
        implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);
        Button button = findViewById(R.id.button5);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                goToSixthActivity();
            }

        });
    }

    private void goToSixthActivity() {
        Intent intent = new Intent(this, SixthActivity.class);

        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        goToSixthActivity();
    }
}