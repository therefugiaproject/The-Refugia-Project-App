package com.example.helloworld1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;
import android.net.Uri;

public class SixthActivity extends AppCompatActivity
{
    EditText sendto, subject, body;
    Button button;
    String longitude, latitude, altitude, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixth);

        Intent intent1 = getIntent();

        latitude = "Latitude is " + intent1.getStringExtra("latitude");
        longitude = "Longitude is " + intent1.getStringExtra("longitude");
        altitude = "Altitude is " + intent1.getStringExtra("altitude");
        address = "Address is " + intent1.getStringExtra("address");
        final String uriString = intent1.getStringExtra("uri");

        // Getting instance of edittext and button
        subject = findViewById(R.id.editText2);
        body = findViewById(R.id.editText3);
        button = findViewById(R.id.button6);// attach setOnClickListener to button

        // with Intent object define in it
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view)
            {
                String emailsend = "jmckinnon@therefugiaproject.org";
                String emailsubject = subject.getText().toString();
                String emailbody = body.getText().toString() + "\n" + "\n" + "\n" + "LOCATION:" + "\n"
                                    + "\n" + latitude + "\n" + longitude
                                    + "\n" + altitude + "\n" + address;
                // define Intent object
                // with action attribute as ACTION_SEND
                Intent intent = new Intent(Intent.ACTION_SEND);

                // add three files to intent using putExtra function
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { emailsend });
                intent.putExtra(Intent.EXTRA_SUBJECT, emailsubject);
                intent.putExtra(Intent.EXTRA_TEXT, emailbody);
                if (uriString.equals("Photo not taken")){
                    //do nothing
                }
                else{
                    final Uri fileUri = Uri.parse(uriString);
                    intent.putExtra(Intent.EXTRA_STREAM, fileUri);
                }

                // set type of intent
                intent.setType("message/rfc822");

                // startActivity with intent with chooser
                // as Email client using createChooser function
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });

        //Button button = findViewById(R.id.button6);

       /* button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                goToMainActivity();
            }

        });*/
    }

    /*private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        goToMainActivity();
    }*/
}