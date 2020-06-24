package com.example.helloworld1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity
    implements View.OnClickListener{

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_second);
            Button button = findViewById(R.id.button2);
            Button button1 = findViewById(R.id.button8);

            button.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    goToThirdActivity();
                }

            });
            button1.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    goToExtraActivity();
                }

            });
        }

    private void goToExtraActivity() {
        Intent intent = new Intent(this, ExtraActivity.class);

        startActivity(intent);
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