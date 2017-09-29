package com.example.sasa10.mljac.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sasa10.mljac.klasa.Obrok;
import com.example.sasa10.mljac.R;

import java.util.ArrayList;
import java.util.List;




public class AdapterObrok extends BaseAdapter {
    private Context context;
    private TextView id, naziv, recept;
    private List<Obrok> obroci =new ArrayList<>();

   // Database db;


    public AdapterObrok(Context context, List<Obrok> obroci) {
        this.context=context;
        this.obroci = obroci;

    }

    public AdapterObrok(){

    }


    @Override
    public int getCount() {
        return obroci.size();
    }

    @Override
    public Object getItem(int position) {
        return obroci.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.adapter_obroci,null);

        naziv =(TextView)v.findViewById(R.id.textViewNaziv);

        recept =(TextView)v.findViewById(R.id.textViewRecept);

        ImageView imageView=(ImageView)v.findViewById(R.id.imageView2);
        naziv.setText(String.valueOf(obroci.get(position).getNaziv()));
        recept.setText(String.valueOf(obroci.get(position).getRecept()));

        return v;
    }


}
