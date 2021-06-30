package com.example.apivalorant.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BancoController {

    private SQLiteDatabase BancoInfo;
    private BancoDados Dados;

    public BancoController(Context context){Dados = new BancoDados(context);}

    public String InserirDados(String _id, String Foto, String Nome, String Primaria, String Secundaria, String Granada, String Ultimate, String Descricao){
        ContentValues Valoras;
        long result;

        BancoInfo = Dados.getWritableDatabase();
        Valoras = new ContentValues();
        Valoras.put(BancoDados.ID, _id);
        Valoras.put(BancoDados.PIC_AGENTE, Foto);
        Valoras.put(BancoDados.NOME, Nome);
        Valoras.put(BancoDados.PRIMARIA, Primaria);
        Valoras.put(BancoDados.SECUNDARIA, Secundaria);
        Valoras.put(BancoDados.GRANADA, Granada);
        Valoras.put(BancoDados.ULTIMATE, Ultimate);
        Valoras.put(BancoDados.DESCRICAO, Descricao);

        result = BancoInfo.insert(BancoDados.TABELA_AGENT,null, Valoras);

        if(result == -1){
            return"Erro no registro de agente.";
        }else{
            return"Agente registrado.";
        }
    }
    public Cursor CarregaDados(){
        Cursor cursor;
        String [] RegistroAgente = {Dados.ID, Dados.PIC_AGENTE, Dados.NOME, Dados.PRIMARIA, Dados.SECUNDARIA, Dados.GRANADA, Dados.ULTIMATE, Dados.DESCRICAO};
        BancoInfo = Dados.getReadableDatabase();
        cursor = BancoInfo.query(Dados.TABELA_AGENT, RegistroAgente, null, null, null, null, null, null);
        return cursor;
    }

}
