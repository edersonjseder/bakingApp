package baking.training.udacity.com.bakingappproject.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ederson.js on 14/12/2016.
 */

public class ConnectionDetector {

    private Context _context;

    public ConnectionDetector(Context context){
        this._context = context;
    }

    /**
     * Checking for all possible internet providers
     *
     * @return boolean value
     */
    public boolean isConnectingToInternet(){

        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null){

            NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();

            if(activeNetwork != null){

                if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI){

                    return true;

                } else if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE){

                    return true;

                }

            } else {

                return false;

            }

        } else {

            return false;

        }

        return false;
    }
}
