package com.example.apivalorant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import com.example.apivalorant.database.BancoController;

import java.util.ArrayList;

public class TelaDados extends AppCompatActivity {

    private ArrayList<Agente> Lista_Agente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_dados);

        Lista_Agente = new ArrayList<>();

        setAgenteInfo();
        setAdapter();
    }

    private void setAdapter() {
        ListAdapter listAdapter = new ListAdapter(this, R.layout.itensagente, Lista_Agente);

        ListView listView = findViewById(R.id.listAgente);
        listView.setAdapter(listAdapter);
    }

    private void setAgenteInfo() {
        BancoController crud = new BancoController(getBaseContext());
        Cursor cursor = crud.CarregaDados();

        while (cursor.moveToNext()){
            Lista_Agente.add(new Agente(cursor.getString(0),
                                        cursor.getString(1),
                                        cursor.getString(2),
                                        cursor.getString(3),
                                        cursor.getString(4),
                                        cursor.getString(5),
                                        cursor.getString(6),
                                        cursor.getString(7))); //adiciona elementos na lista.
        }
    }
}