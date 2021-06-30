package com.example.apivalorant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apivalorant.database.BancoController;
import com.example.apivalorant.views.CustomView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Provider;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, SensorEventListener, LocationListener {
    private JSONObject character;
    private EditText search;
    private TextView name;
    private TextView habilidad1;
    private TextView habilidad2;
    private TextView granad;
    private TextView ultim;
    private TextView descrs;
    private ImageView valoras;

    private Button button;
    private TextView localiz;
    private LocationManager locationManager;

    private SensorManager sensorManager;
    private Sensor sensor;

    private String imgURL;
    private String _id;
    private String displayname;
    private String habilidade1;
    private String habilidade2;
    private String granada;
    private String ultimate;
    private String descr;

    private String resultado;

    private CustomView mCustomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCustomView = (CustomView)  findViewById(R.id.customView);

        findViewById(R.id.btnBomb).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCustomView.swapColor();
            }
        });

        search = findViewById(R.id.txtSearch); //Relaciona a caixa de texto do codigo com a do layout.

        name = findViewById(R.id.txtName);
        habilidad1 = findViewById(R.id.txtHabilidade1);
        habilidad2 = findViewById(R.id.txtHabilidade2);
        granad = findViewById(R.id.txtGranada);
        ultim = findViewById(R.id.txtUltimate);
        descrs = findViewById(R.id.txtDescr);
        valoras = findViewById(R.id.imgValoras);
        button = findViewById(R.id.btnLocal);
        localiz = findViewById(R.id.txtLocal);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        if(getSupportLoaderManager().getLoader(0)!=null){ //Verifica se tem uma operação em segundo plano, caso não tenha, cria uma.
            getSupportLoaderManager().initLoader(0,null,this);
        }
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, MainActivity.this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void searchCharacter(View view){
        String queryString = search.getText().toString(); // seleciona o que o ususario escreveu na caixa de texto.
        InputMethodManager inputMethodManager = (InputMethodManager) //ao clicar na lupa o teclado se fecha.
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) //verifica se o dispositivo esta conectado na internet, caso esteja conectado, o app acessa a internet.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) { // caso a quantidade de caracteres que esta na caixa de busca seja diferente de 0 ele é armazenado.
            Bundle queryBundle = new Bundle(); // armazena em um pacote o que foi digitado.
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
        } else {
            if(queryString.length()==0){
                search.setError("Virei advinho agr?");
                return;
            } else{
                Toast.makeText(this,"Erro em alguma coisa'<'",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) { // seleciona o pacote criado anteriormente.
        String queryString = "";

        if(args!=null){
            queryString= args.getString("queryString");
        }
        return new Loadcharacter(this,queryString); //Cria o processo em segundo plano e passa o conteudo digitado.
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            character = new JSONObject(data);
            int result = character.getInt("status");
            if(result == 200){
                JSONObject info = character.getJSONObject("data");

                BancoController crud = new BancoController(getBaseContext());

                _id = info.getString("uuid");

                displayname = info.getString("displayName");
                String newdisplayname = getResources().getString(R.string.name) + " " + displayname;
                name.setText(newdisplayname);

                JSONArray habilidades = info.getJSONArray("abilities");
                JSONObject primaria = habilidades.getJSONObject(0);
                JSONObject secundaria = habilidades.getJSONObject(1);
                JSONObject gran = habilidades.getJSONObject(2);
                JSONObject ult = habilidades.getJSONObject(3);

                habilidade1 = primaria.getString("displayName");
                String newhabilidade1 = getResources().getString(R.string.habilidade1) + " " + habilidade1;
                habilidad1.setText(newhabilidade1);

                habilidade2 = secundaria.getString("displayName");
                String newhabilidade2 = getResources().getString(R.string.habilidade2) + " " + habilidade2;
                habilidad2.setText(newhabilidade2);

                granada = gran.getString("displayName");
                String newgranada = getResources().getString(R.string.granada) + " " + granada;
                granad.setText(newgranada);

                ultimate = ult.getString("displayName");
                String newultimate = getResources().getString(R.string.ultimate) + " " + ultimate;
                ultim.setText(newultimate);

                descr = info.getString("description");
                String newdescr = getResources().getString(R.string.descr) + " " + descr;
                descrs.setText(newdescr);

                imgURL = info.getString("displayIcon");
                Picasso.get().load(imgURL).into(valoras);

                resultado = crud.InserirDados(_id, imgURL, displayname, habilidade1, habilidade2, granada, ultimate, descr);
            }else{
                Toast.makeText(this, "Pesquisa algo antes animal =[", Toast.LENGTH_LONG).show();
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    boolean flashbang = false;
    /*Foram criados o sensor e o gerenciador do sensor no OnCreate, quando o sensor for funcionar o OnResume é disparado e a luminosidade é captada
    * quando o sensor não precisa funcionar, o OnPause é disparado e a luminosidade para de ser captada, quando o valor captado pelo sensor muda
    * ele armazena esse valor e realiza a verificação da quantidade de luz, caso a luz seja maior que 30 e caso a booleana "flashbang" seja falsa
    * a mensagem é exibida ao usuario.
    * A booleana "flashbang" serve para que a mensagem não seja disparada a todo momento*/
    @Override
    public void onSensorChanged(SensorEvent event) {
        float luz = event.values[0];
        if(!flashbang && luz > 50){
            Toast.makeText(this, "Foi bangado é?", Toast.LENGTH_LONG).show();
            flashbang = true;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this, "Atrás de você." + "\n" + location.getLatitude() + "," + location.getLongitude(), Toast.LENGTH_SHORT).show();

        try{
            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);

            localiz.setText(address);

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    public void ClicaHist(View view){
        Intent intent = new Intent(this, TelaDados.class);
        startActivity(intent);
    }
}