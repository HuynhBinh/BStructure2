package com.hnb.bstructure.volleycontroller;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hnb.bstructure.callbackinterface.iCallBack;
import com.hnb.bstructure.thread.BackgroundThreadExecutor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import greendao.GProduct;

/**
 * Created by USER on 9/11/2015.
 */
public class ProductVolley extends iCallBack
{

    public ProductVolley(Context context)
    {
        // init background thread and ui thread
        this.backgroundThreadExecutor = BackgroundThreadExecutor.getInstance();
        this.context = context;

    }


    //region Get product list
    public static final String TAG_executeGetProductList = "TAG_executeGetProductList";

    public void execute_GetProductList(final ProductListCallback callback)
    {
        String url = "http://rest.elkstein.org/2008/02/real-rest-examples.html";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(final String s)
            {
                backgroundThreadExecutor.runOnBackground(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        Gson gson = new Gson();
                        List<GProduct> list = Arrays.asList(gson.fromJson(s, GProduct[].class));
                        callback.onLoaded(list);
                    }
                });


            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(final VolleyError volleyError)
            {
                backgroundThreadExecutor.runOnBackground(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        callback.onError(volleyError.toString());
                    }
                });

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("latitude", "10.7915789");
                params.put("longitude", "106.7022916");
                params.put("page", "1");

                return params;
            }
        };

        stringRequest.setTag(TAG_executeGetProductList);

        VolleyFactory.getRequestQueue(context).add(stringRequest);
    }

    public void cancel_GetProductList()
    {
        VolleyFactory.getRequestQueue(context).cancelAll(TAG_executeGetProductList);
    }
    //endregion


}
