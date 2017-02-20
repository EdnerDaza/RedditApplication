package com.ednerdaza.codigoriginal.redditapplication.mvc.models;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ednerdaza.codigoriginal.redditapplication.R;
import com.ednerdaza.codigoriginal.redditapplication.classes.utilities.Config;
import com.ednerdaza.codigoriginal.redditapplication.mvc.controllers.base.VolleyQueue;
import com.ednerdaza.codigoriginal.redditapplication.mvc.controllers.interfaces.ItemModelInterface;
import com.ednerdaza.codigoriginal.redditapplication.mvc.models.entities.ItemEntityResponse;
import com.google.gson.Gson;
import org.json.JSONObject;


/**
 * Created by administrador on 16/02/17.
 */
public class ItemModel {

    //region METODOS DEL MODELO

        public static void getItems(final Context context, final ItemModelInterface itemModelInterface){

        String url = Config.BASE_URL_JSON;

        final Gson gson = new Gson();
        Log.v(Config.LOG_TAG, " <ItemModel> URL --> " + url);

        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response==null){
                    Log.v(Config.LOG_TAG, "Response --> Cadena nula "+response);
                }else{
                    Log.v(Config.LOG_TAG, "Response --> Cadena buena "+response);
                }

                ItemEntityResponse res = gson.fromJson(response.toString(), ItemEntityResponse.class);
                Log.v(Config.LOG_TAG, "Res --> "+res);

                if (res != null){
                    itemModelInterface.completeSuccess(res);
                }else {
                    itemModelInterface.completeFail(context.getResources().getString(R.string.error_response_null));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v(Config.LOG_TAG, "ERROR --> "+error);
                itemModelInterface.completeFail(context.getResources().getString(R.string.error_listener));
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        VolleyQueue.addToRequestQueue(request, "Item");
    }

    //endregion

}
