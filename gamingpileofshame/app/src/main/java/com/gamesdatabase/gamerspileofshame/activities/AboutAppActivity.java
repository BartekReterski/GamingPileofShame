package com.gamesdatabase.gamerspileofshame.activities;

import android.content.Intent;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.devs.readmoreoption.ReadMoreOption;
import com.gamesdatabase.gamerspileofshame.R;

public class AboutAppActivity extends AppCompatActivity {
    Button sendEmailButton;
    EditText emailAddress;
    EditText emailSubject;
    EditText message;
    TextView credits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        getSupportActionBar().setTitle("About Application");


        emailAddress =  findViewById(R.id.email);
        emailSubject =  findViewById(R.id.subject);
        message =  findViewById(R.id.message);
        sendEmailButton =  findViewById(R.id.send_button);
        credits=findViewById(R.id.creditsTextView);



        ReadMoreOption readMoreOption = new ReadMoreOption.Builder(this)
                .textLength(3, ReadMoreOption.TYPE_LINE) // OR
                //.textLength(300, ReadMoreOption.TYPE_CHARACTER)
                .moreLabel("More")
                .lessLabel("Less")
                .moreLabelColor(Color.RED)
                .lessLabelColor(Color.BLUE)
                .labelUnderLine(true)
                .expandAnimation(true)
                .build();


        readMoreOption.addReadMoreTo(credits, getString(R.string.credits));




        // przesłanie wpisanych danych do aplikacji zewnętrznej obsługującej wysyłanie mejli

        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toemailAddress = emailAddress.getText().toString();
                String msubject = emailSubject.getText().toString();
                String mmessage = message.getText().toString();

                Intent emailApp = new Intent(Intent.ACTION_SEND);
                emailApp.putExtra(Intent.EXTRA_EMAIL, new String[]{toemailAddress});
                emailApp.putExtra(Intent.EXTRA_SUBJECT, msubject);
                emailApp.putExtra(Intent.EXTRA_TEXT, mmessage);
                emailApp.setType("message/rfc822");
                startActivity(Intent.createChooser(emailApp, "Send message via:"));

            }
        });
    }




}

