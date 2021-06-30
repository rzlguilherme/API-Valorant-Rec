package com.example.apivalorant;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class Loadcharacter extends AsyncTaskLoader<String> {
    private String mQueryString;
    Loadcharacter(Context context,String queryString){
        super(context);
        mQueryString = queryString; // seleciona a mQueryString e insere o conteudo digitado pelo usuario.
    }
    @Override
    protected void onStartLoading(){
        super.onStartLoading();
        forceLoad();
    }
    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.searchCharacterInfo(mQueryString);
    }
}
