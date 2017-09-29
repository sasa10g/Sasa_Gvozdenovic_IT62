package com.example.sasa10.mljac.aktivnosti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.sasa10.mljac.klasa.Obrok;
import com.example.sasa10.mljac.R;
import com.example.sasa10.mljac.baza.Database;

public class ActivitiDodajObrok extends AppCompatActivity {


    Database db;
    private int position= -1;
    private EditText editTextNaziv;
    private EditText editTextRecept;
    private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activiti_dodaj_obrok);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db=Database.getInstance(this);
        editTextNaziv=(EditText)findViewById(R.id.nazivEdit);
        editTextRecept=(EditText)findViewById(R.id.receptEdit);
        animationView = (LottieAnimationView) findViewById(R.id.animation_view1);



        com.github.clans.fab.FloatingActionButton btnDone=(com.github.clans.fab.FloatingActionButton)findViewById(R.id.add_done);
        com.github.clans.fab.FloatingActionButton btnEmail=(com.github.clans.fab.FloatingActionButton)findViewById(R.id.add_email);
        com.github.clans.fab.FloatingActionButton btnSms=(com.github.clans.fab.FloatingActionButton)findViewById(R.id.add_sms);


        position= getIntent().getIntExtra("position",-1);//kupimo vrednosti iz intenta

        if(position!=-1){//kupimo vrednosti iz intenta


            Obrok o = (Obrok) getIntent().getSerializableExtra("obrok");
            editTextNaziv.setText(o.getNaziv());
            editTextRecept.setText(o.getRecept());
        }


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editTextNaziv.getText().toString().isEmpty() || editTextRecept.getText().toString().isEmpty()){

                    Toast.makeText(ActivitiDodajObrok.this, "You have empty fields!", Toast.LENGTH_SHORT).show();

                }else if (position==-1){

                  Obrok o=new Obrok( editTextNaziv.getText().toString(),editTextRecept.getText().toString());

                    db.createObrok(o);

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    setResult(1, i);

                    finish();



                }else if(position!=-1){

                    Obrok o=new Obrok( editTextNaziv.getText().toString(),editTextRecept.getText().toString());
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("position",position);
                    i.putExtra("obrok",o);
                    setResult(1, i);

                    finish();



                }

            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(editTextNaziv.getText().toString().isEmpty() || editTextRecept.getText().toString().isEmpty()){

                    Toast.makeText(ActivitiDodajObrok.this, "You have empty fields!", Toast.LENGTH_SHORT).show();

                }else {
                    Intent email = new Intent(Intent.ACTION_SEND);

                    email.putExtra(Intent.EXTRA_SUBJECT, editTextNaziv.getText().toString());
                    email.putExtra(Intent.EXTRA_TEXT, editTextRecept.getText().toString()+"\n\n\n by Mljac");

                    //need this to prompts email client only
                    email.setType("message/rfc822");

                    startActivity(Intent.createChooser(email, "Choose an Email client :"));
                }
            }
        });





        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editTextNaziv.getText().toString().isEmpty() || editTextRecept.getText().toString().isEmpty()){

                    Toast.makeText(ActivitiDodajObrok.this, "You have empty fields!", Toast.LENGTH_SHORT).show();

                }else {

                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.putExtra("sms_body", "Naziv rezepta: " + editTextNaziv.getText() + "\n"+ "\n"+ "Sadrzaj: " + editTextRecept.getText() + "\n\n\n by Mljac");
                    sendIntent.setType("vnd.android-dir/mms-sms");
                    startActivity(sendIntent);
                }
            }
        });
    }

}
