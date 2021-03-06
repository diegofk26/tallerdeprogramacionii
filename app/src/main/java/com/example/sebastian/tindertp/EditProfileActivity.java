package com.example.sebastian.tindertp;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.example.sebastian.tindertp.ExpandedListAdapters.ExpandableListAdapter;
import com.example.sebastian.tindertp.ExpandedListAdapters.MyScrollListener;
import com.example.sebastian.tindertp.Interfaces.CategoryUpdater;
import com.example.sebastian.tindertp.Interfaces.ConectivityManagerInterface;
import com.example.sebastian.tindertp.application.TinderTP;
import com.example.sebastian.tindertp.commonTools.Common;
import com.example.sebastian.tindertp.commonTools.ConnectionStruct;
import com.example.sebastian.tindertp.commonTools.HeaderBuilder;
import com.example.sebastian.tindertp.commonTools.JsonArrayBuilder;
import com.example.sebastian.tindertp.commonTools.MultiHashMap;
import com.example.sebastian.tindertp.commonTools.ProfileInfo;
import com.example.sebastian.tindertp.internetTools.InfoDownloaderClient;
import com.example.sebastian.tindertp.internetTools.InterestsInfoDownloader;
import com.example.sebastian.tindertp.internetTools.RequestResponseClient;
import com.example.sebastian.tindertp.services.ReceiverOnGetInterests;
import com.example.sebastian.tindertp.services.ReceiverOnProfileEdit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class EditProfileActivity extends AppCompatActivity implements CategoryUpdater, ConectivityManagerInterface {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> categories;
    List<String> listDataHeader;
    MultiHashMap listDataChild;

    private Map<Integer,String> mapperID = new HashMap<>();
    private Map<String,String> categoryMapper = new HashMap<>();

    private static final String EDIT_TAG = "EditProfileActivity";

    private boolean finishUpdate;
    private ProfileInfo profileInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ReceiverOnGetInterests getInterests = new ReceiverOnGetInterests(this, true);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(getInterests,
                new IntentFilter(Common.INTERESTS));

        expListView = (ExpandableListView) findViewById(R.id.expandableListView2);
        buildCategoryMapper();

        listDataHeader = new ArrayList<>();
        listDataChild = new MultiHashMap();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        expListView.setOnScrollListener(new MyScrollListener(this));

        ReceiverOnProfileEdit onProfileInfo = new ReceiverOnProfileEdit(this,listAdapter,mapperID);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(onProfileInfo,
                new IntentFilter(Common.MY_PROFILE));

        Map<String, String> values = new HashMap<>();

        String url = ((TinderTP) this.getApplication()).getUrl();
        ConnectionStruct conn = new ConnectionStruct(Common.INTERESTS, Common.GET, url);
        InterestsInfoDownloader interests = new InterestsInfoDownloader(this, values, conn,
                findViewById(R.id.relative));
        interests.runInBackground();

        finishUpdate = false;

    }
    /**Traaduccion de music/band a Bandas: */
    private void buildCategoryMapper() {
        Log.i(EDIT_TAG,"Construyo traduccion de interest a español");
        categoryMapper.put(Common.FOOD,"Comida:");
        categoryMapper.put(Common.OUTDOORS,"Actividades:");
        categoryMapper.put(Common.TRAVEL,"Viajes:");
        categoryMapper.put(Common.SEX,"Sexo:");
        categoryMapper.put(Common.MUSIC,"Música:");
        categoryMapper.put(Common.MUSIC_BAND,"Bandas:");
        categoryMapper.put(Common.SPORT, "Deportes:");
    }

    /**Envia la edision del perfil al app server*/
    public void goToModify(View v) {

        EditText alias = (EditText)findViewById(R.id.editText6);
        EditText name = (EditText)findViewById(R.id.editText5);
        String nameText = name.getText().toString();
        String aliasText = alias.getText().toString();

        if (finishUpdate && !nameText.isEmpty() && !aliasText.isEmpty()) {
            finishUpdate = false;

            try {
                JSONArray jsonInterests = JsonArrayBuilder.buildInterests(categories, listAdapter);
                JSONObject jsonO = profileInfo.updateProfile(aliasText,nameText,jsonInterests);

                //agrega todo_ a un user
                JSONObject jsonFinal = new JSONObject();
                jsonFinal.put("user",jsonO);

                String url = ((TinderTP) this.getApplication()).getUrl();
                String userEmail = ((TinderTP) this.getApplication()).getUser();
                String token = ((TinderTP) this.getApplication()).getToken();

                Map<String, String> values = HeaderBuilder.forNewUser(userEmail, token);
                ConnectionStruct conn = new ConnectionStruct(Common.EDIT, Common.PUT, url);

                final Context context = this;
                RequestResponseClient sendEditProfile = new RequestResponseClient(this,conn,values) {
                    @Override
                    protected void getJson() throws IOException {
                        jsonString = readIt();
                    }

                    @Override
                    protected void onPostExec() {
                        if (!badResponse && isConnected) {
                            Log.i(EDIT_TAG,"Perfil modificado");
                            showText("Perfil modificado.");
                            finish();
                        } else if (responseCode == Common.BAD_TOKEN) {
                            Log.d(EDIT_TAG, "Token vencido");
                            SharedPreferences preferences = getSharedPreferences(Common.PREF_FILE_NAME, Context.MODE_PRIVATE);
                            String user = preferences.getString(Common.USER_KEY, "");
                            String pass = preferences.getString(Common.PASS_KEY, "");
                            String url = ((TinderTP) getApplication()).getUrl();
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                            String tokenGCM = sharedPreferences.getString(Common.TOKEN_GCM, "");

                            Map<String,String> values = HeaderBuilder.forLogin(user, pass, tokenGCM);
                            ConnectionStruct conn = new ConnectionStruct(Common.LOGIN,Common.GET,url);
                            InfoDownloaderClient info = new InfoDownloaderClient(context,values,conn, findViewById(R.id.edit_relative),false);
                            info.runInBackground();
                        }else {

                            finishUpdate = true;
                            Log.w(EDIT_TAG,"El perfil no se pudo modificar");
                            showText("El perfil no se pudo modificar. Intente nuevamente.");
                        }
                    }

                    @Override
                    protected void showText(String message) {
                        Common.showSnackbar(findViewById(R.id.edit_relative),message);
                    }
                };

                sendEditProfile.addBody(jsonFinal.toString());
                sendEditProfile.runInBackground();

            }catch(JSONException e){
                Log.e(EDIT_TAG,"Error en el json modificado");
            }
        }else {
            Common.showSnackbar(findViewById(R.id.relative), "Descargando categorias. Por favor espere...");
        }
    }

    /**Ingresa los intereses que se pidieron al app server*/
    @Override
    public void update(MultiHashMap categoryValues) {

        Log.i(EDIT_TAG,"Agrega intereses al expandable list");

        EditText edit = null;
        listDataHeader.clear();
        mapperID.clear();
        listDataChild.clear();

        categories = categoryValues.getKeysList();

        for(int i = 0; i < categories.size(); i++) {

            String category = categories.get(i);
            String categoryMod;
            if (categoryMapper.containsKey(category)) {
                categoryMod = categoryMapper.get(category);
            }else {
                categoryMod = category.substring(0, 1).toUpperCase() + category.substring(1) + ": ";
            }

            int categoryID = Integer.parseInt(""+(i+1)+""+0 );
            mapperID.put(categoryID,categoryMod);

            listDataHeader.add(categoryMod);
            listDataChild.put(categoryMod,edit);
        }

        listAdapter.setSuggestions(categories, categoryValues);
        listAdapter.addHeaders(listDataHeader);
    }

    public void saveProfile(ProfileInfo profileInfo) {
        this.profileInfo = profileInfo;
        finishUpdate = true;
    }

    /**Agrega mas valores a un interes*/
    public void more(View view) {
        int ID = view.getId();

        int digitSize = String.valueOf(listDataHeader.size()).length();

        int categoryID = Integer.parseInt(("" + ID).substring(0, digitSize));
        int childIDprev = Integer.parseInt(("" + ID).substring(digitSize));
        int childIDnext = childIDprev + 1;
        int nextViewID = Integer.parseInt(""+categoryID+""+childIDnext);

        mapperID.put(nextViewID, mapperID.get(ID));
        EditText newEdit = null;
        listDataChild.put(mapperID.get(nextViewID), newEdit);
        listAdapter.addHeaders(listDataHeader);
    }

    @Override
    public ConnectivityManager getConectivityManager() {
        return (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}
