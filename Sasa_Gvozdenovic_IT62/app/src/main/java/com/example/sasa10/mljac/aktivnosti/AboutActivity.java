package com.example.sasa10.mljac.aktivnosti;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.example.sasa10.mljac.R;

public class AboutActivity extends AppCompatActivity {
    LottieAnimationView animationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "sasagvozdenovic@hotmail.com"});
                //email.putExtra(Intent.EXTRA_SUBJECT, editTextNaziv.getText().toString());
                //email.putExtra(Intent.EXTRA_TEXT, editTextRecept.getText().toString());

                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));

            }
        });


        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        animationView.setAnimation("mljac.json");
        animationView.loop(true);
        animationView.playAnimation();




    }

}
