package com.example.sasa10.mljac.aktivnosti;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.sasa10.mljac.klasa.Obrok;
import com.example.sasa10.mljac.R;
import com.example.sasa10.mljac.adapter.AdapterObrok;
import com.example.sasa10.mljac.baza.Database;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    private Database db;
    private ArrayList<Obrok>obroci=new ArrayList<>();
    private AdapterObrok adapterObrok=new AdapterObrok();
    private GridView listViewLista;
    LottieAnimationView animationView;
    TextView textViewNaslov;
    TextView textViewPodnaslov;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        com.github.clans.fab.FloatingActionButton btn=(com.github.clans.fab.FloatingActionButton)findViewById(R.id.menu_item);
        //povezivanje dugmeta sa grafickim prikazom xml declar
        com.github.clans.fab.FloatingActionButton btn2=(com.github.clans.fab.FloatingActionButton)findViewById(R.id.menu_item1);
        com.github.clans.fab.FloatingActionButton btnYT=(com.github.clans.fab.FloatingActionButton)findViewById(R.id.youtube);

        textViewNaslov =(TextView)findViewById(R.id.textViewNaslovPocetak);
        textViewPodnaslov=(TextView)findViewById(R.id.textViewPodnaslov);
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);


        db = Database.getInstance(this);//instanciranje baze podataka,koriscenjem singlton obrasca

        obroci= (ArrayList<Obrok>) db.readObrok(); //lista svih obroka iz baze

        if(!obroci.isEmpty()){

            textViewNaslov.setVisibility(View.INVISIBLE);
            textViewPodnaslov.setVisibility(View.INVISIBLE);
            animationView.setVisibility(View.INVISIBLE);


        }else{

            textViewNaslov.setVisibility(View.VISIBLE);
            textViewPodnaslov.setVisibility(View.VISIBLE);
            animationView.setAnimation("mljac.json"); //animaicja
            animationView.loop(true);
            animationView.playAnimation();

        }


        //add recipe button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ActivitiDodajObrok.class);
                startActivityForResult(i,1);
            }
        });
        //about button
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),AboutActivity.class);
                startActivity(i);
            }
        });

        btnYT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),YoutubeActivity.class);
                startActivity(i);
            }
        });




        listViewLista = (GridView) findViewById(R.id.listViewObroci);

         adapterObrok = new AdapterObrok(getApplicationContext(),obroci);

        listViewLista.setAdapter(adapterObrok);


        listViewLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i =new Intent(getApplicationContext(),ActivitiDodajObrok.class);

                i.putExtra("position",position); //putExtra - salje podatke na aktiviti dodaj obrok
                i.putExtra("obrok",obroci.get(position));
                startActivityForResult(i,1); // 1 - odnosi se na konkretan intent


            }
        });


        listViewLista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                    db.deleteRow(obroci.get(position).getId());
                                    obroci.remove(position);
                                    adapterObrok.notifyDataSetChanged();


                                if(obroci.isEmpty()){

                                    textViewNaslov.setVisibility(View.VISIBLE);
                                    textViewPodnaslov.setVisibility(View.VISIBLE);
                                    animationView.setVisibility(View.VISIBLE);


                                }


                                Toast.makeText(MainActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {

            if(data.getSerializableExtra("obrok")!=null){

                Obrok o= (Obrok) data.getSerializableExtra("obrok");
                int position=data.getIntExtra("position",-1);

                db.deleteRow(obroci.get(position).getId());
                db.createObrok(o);

            }

            obroci = (ArrayList<Obrok>) db.readObrok();
            adapterObrok = new AdapterObrok(getApplicationContext(),obroci);
            listViewLista.setAdapter(adapterObrok);
            textViewNaslov.setVisibility(View.INVISIBLE);
            textViewPodnaslov.setVisibility(View.INVISIBLE);
            animationView.setVisibility(View.INVISIBLE);

        }

    }
}
