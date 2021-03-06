package com.example.sebastian.tindertp.commonTools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.sebastian.tindertp.ChatListActivity;
import com.example.sebastian.tindertp.R;
import com.example.sebastian.tindertp.SelectLoginOrRegistryActivity;
import com.example.sebastian.tindertp.UrlActivity;

/**Contiene principalmente todas las variables en strings necesarias para una conexcion,
 * headers y sus keys. Reglas para registrar usuarios.
 * */
public class Common {

    public static final String PREF_FILE_NAME = "mypreferences";

    public static final String FAIL = "fail";

    //request methods
    public static final String GET = "GET";             /**< Vars para la conexion url */
    public static final String PUT = "PUT";             /**< Vars para la conexion url */
    public static final String POST = "POST";           /**< Vars para la conexion url */
    public static final String DELETE = "DELETE";           /**< Vars para la conexion url */

    //paths ej: url/login
    public static final String TEST = "/test";          /**< Vars para la conexion url */
    public static final String LOGIN = "/login";        /**< Vars para la conexion url */
    public static final String REGISTER = "/registro";  /**< Vars para la conexion url */
    public static final String MESSAGES = "/mensajes";  /**< Vars para la conexion url */
    public static final String CHAT = "/chat";          /**< Vars para la conexion url */
    public static final String PROFILE = "/perfil";          /**< Vars para la conexion url */
    public static final String INTERESTS = "/intereses";          /**< Vars para la conexion url */
    public static final String PICTURE = "/foto";          /**< Vars para la conexion url */
    public static final String MATCH = "/matcheo";          /**< Vars para la conexion url */
    public static final String INFO = "/info";          /**< Vars para la conexion url */
    public static final String GPS = "/gps";          /**< Vars para la conexion url */
    public static final String REMOVE = "/eliminar";          /**< Vars para la conexion url */
    public static final String EDIT = "/editar";          /**< Vars para la conexion url */

    public static final int BAD_TOKEN = 401;          /**< Vars para la conexion url */


    //files para screenSplash
    public static final String IA = "file:///android_asset/IA.gif";     /**< Vars para la ScreenSplash */
    public static final String DOTS = "file:///android_asset/dots.gif"; /**< Vars para la ScreenSplash */

    public static final String PROFILE_JSON = "profileJson";
    public static final String URL_KEY = "url";

    //Headers y Json Usuario
    public static final String USER_KEY = "Usuario";    /**< Vars para la conexion url */
    public static final String PASS_KEY = "Password";   /**< Vars para la conexion url */
    public static final String NAME_KEY = "name";   /**< Vars para la conexion url */
    public static final String ALIAS_KEY = "alias";         /**< Vars para la conexion url */
    public static final String SEX_KEY = "sex";           /**< Vars para la conexion url */
    public static final String MALE_KEY = "male";           /**< Vars para la conexion url */
    public static final String FEMALE_KEY = "female";           /**< Vars para la conexion url */
    public static final String EMAIL_KEY = "email";   /**< Vars para la conexion url */
    public static final String AGE_KEY = "age";   /**< Vars para la conexion url */
    public static final String INTERESTS_KEY = "interests";   /**< Vars para la conexion url */
    public static final String LONGITUDE_KEY = "longitude";   /**< Vars para la conexion url */
    public static final String LATITUDE_KEY = "latitude";   /**< Vars para la conexion url */
    public static final String LOCATION_KEY = "location";   /**< Vars para la conexion url */
    public static final String PHOTO_KEY = "photo_profile";
    public static final String CATEGORY_KEY = "category";
    public static final String VALUE_KEY = "value";
    public static final String USER = "user";
    public static final String COUNT = "count";
    public static final String MAP_VALUE = "map";
    public static final String MAP_KEY = "map_key";

    //intereses
    public static final String MUSIC = "music";
    public static final String MUSIC_BAND = "music/band";
    public static final String TRAVEL = "travel";
    public static final String FOOD = "food";
    public static final String SPORT = "sport";
    public static final String SEX = "sex";
    public static final String OUTDOORS = "outdoors";


    //Headers keys
    public static final String USER1 = "Usuario1";      /**< Vars para la conexion url */
    public static final String USER2 = "Usuario2";      /**< Vars para la conexion url */
    public static final String DESDE = "Desde";         /**< Vars para la conexion url */
    public static final String CANT = "Cantidad";       /**< Vars para la conexion url */
    public static final String MAX_MESSAGES= "10";      /**< Vars para la conexion url */
    public static final String TOKEN = "Token";         /**< Vars para la conexion url */
    public static final String RECEPTOR = "Receptor";   /**< Vars para la conexion url */
    public static final String USER_GET = "UsuarioPedido";
    public static final String TOKEN_GCM = "TokenGCM";   /**< Vars para la conexion url */

    // intent.putExtra() keys
    public static final String IMG_KEY = "images";
    public static final String MSSG_KEY = "MSSG";
    public static final String USER_MSG_KEY = "USER_MSG";
    public static final String MATCH_KEY = "MATCH";

    //intentFilter keys
    public static final String MATCH_MSG_KEY = "MATCH_MSG";
    public static final String MATCH_MATCH_KEY = "MATCH_match";
    public static final String MSSG_READED_KEY = "mssg readed";
    public static final String RAND_USER_KEY = "RAND_USER";
    public static final String SPECIFIC_USER_KEY = "SPEC_USER_CHAT";
    public static final String SPECIFIC_USER_PROF = "SPEC_USER_PROF";
    public static final String CHAT_KEY = "CHAT";
    public static final String PROFILE_MSG_KEY = "PROFILE_MSG";
    public static final String PROFILE_MATCH_KEY = "PROFILE_MATCH";
    public static final String CHAT_LIST_MSG_KEY = "CHAT_LIST_MSG";
    public static final String CHAT_LIST_MATCH_KEY = "CHAT_LIST_MATCH";
    public static final String REGIST_COMPLETE = "registrationComplete";
    public static final String MY_PROFILE = "mi perfil";

    //respuestas de like dilike
    public static final String RESPONSE_KEY = "Resultado";
    public static final String CANDIDATE_KEY = "Candidato";
    public static final String LIKE_KEY = "like";
    public static final String DISLIKE_KEY = "dislike";

    //min max caracteres para contraseña y nombre de usuario ingresado en el registro
    public static final int MAX_CHARS = 30;
    public static final int MIN_CHARS = 5;

    //maxima cantidad de notificaciones q se muestran.
    public static final int MAX_MSSG_NOTIF = 6;

    private static boolean passAreEmpty( String password,StringBuilder response ) {
        if ( password.isEmpty()) {
            response.append("El campo contraseña está vacío.");
            return true;
        } else
            return false;
    }

    private static boolean passLong (String pass, StringBuilder response){
        if (pass.length() > Common.MAX_CHARS) {
            response.append("La contraseña supera los " + Common.MAX_CHARS + " caracteres.");
            return true;
        } else if(pass.length() < Common.MIN_CHARS) {
            response.append("La contraseña no supera los " + Common.MIN_CHARS + " caracteres.");
            return true;
        } else
            return false;
    }

    private static boolean isValidPass(String pass, String validPass, StringBuilder response) {
        if (!pass.equals(validPass)){
            response.append("La contraseña no es valida, ambas tiene que ser iguales.");
            return false;
        }
        return true;
    }

    public static boolean pass_OK(EditText password, StringBuilder response){
        String pass = password.getText().toString();
        return ( !passAreEmpty(pass, response) && !passLong(pass, response) );
    }

    public static boolean passAndValidPass_OK(EditText password, StringBuilder response, EditText valid_pass){
        String pass = password.getText().toString();
        String validPass = valid_pass.getText().toString();
        return ( !passAreEmpty(pass, response) && !passLong(pass, response) && isValidPass(pass,validPass,response) );
    }

    public static void clearLoginSaved(Context context){
        SharedPreferences preferences = context.getSharedPreferences(Common.PREF_FILE_NAME, Context.MODE_PRIVATE);
        preferences.edit().remove(Common.USER_KEY).apply();
        preferences.edit().remove(Common.PASS_KEY).apply();
        Log.i("Clear", "Borra datos de usuario");
    }

    public static boolean optionSelectedItem(MenuItem item, Context context) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            ActivityStarter.start(context, UrlActivity.class);
            return true;
        } else if (id == R.id.action_logout ) {
            Common.clearLoginSaved(context);
            ActivityStarter.startClear(context, SelectLoginOrRegistryActivity.class);
            ((Activity) context).finish();
            return true;
        }else if (id == R.id.badge) {
            ActivityStarter.start(context, ChatListActivity.class);
            return true;
        }

        return false;
    }

    public static void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public static final int MAX_MEGAS_PIC = 30;

    private static final long  MEGABYTE = 1024L * 1024L;

    public static long bytesToMeg(long bytes) {
        return bytes / MEGABYTE ;
    }

    public static void setBackgroundOnOrientation(int orientation, View view){
        RelativeLayout rLayout = (RelativeLayout) view;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rLayout.setBackgroundResource(R.drawable.background_land);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT){
            rLayout.setBackgroundResource(R.drawable.background);
        }
    }


}
