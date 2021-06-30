package com.example.apivalorant.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BancoDados extends SQLiteOpenHelper {
    public static final String BANCO = "BDInfo";
    public static final String TABELA_AGENT = "Agentes";
    public static final String PIC_AGENTE = "Foto";
    public static final String ID = "_id";
    public static final String NOME = "Nome";
    public static final String PRIMARIA = "Primaria";
    public static final String SECUNDARIA = "Secundaria";
    public static final String GRANADA = "Granada";
    public static final String ULTIMATE = "Ultimate";
    public static final String DESCRICAO = "Descricao";

    public static int VERSION = 1;

    public BancoDados(@Nullable Context context) {
        super(context, BANCO, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "Create Table Agentes" +
                        "(_id text primary key, Foto text, Nome text, Primaria text, Secundaria text, Granada text, Ultimate text, Descricao text)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
