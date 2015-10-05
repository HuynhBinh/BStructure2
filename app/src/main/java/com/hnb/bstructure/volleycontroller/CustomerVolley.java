package com.hnb.bstructure.volleycontroller;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hnb.bstructure.callbackinterface.iCallBack;
import com.hnb.bstructure.thread.BackgroundThreadExecutor;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import greendao.Customer;
import greendao.GProduct;

/**
 * Created by HuynhBinh on 10/5/15.
 */
public class CustomerVolley extends iCallBack
{

    public CustomerVolley(Context context)
    {
        this.context = context;
        this.backgroundThreadExecutor = BackgroundThreadExecutor.getInstance();
    }

    //region Get product list
    public static final String TAG_EXECUTE_GET_CUSTOMER_LIST = "TAG_EXECUTE_GET_CUSTOMER_LIST";

    public void execute_GetCustomerList(final CustomerListCallback callback)
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
                        List<Customer> list = Arrays.asList(gson.fromJson(s, Customer[].class));
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

        stringRequest.setTag(TAG_EXECUTE_GET_CUSTOMER_LIST);

        VolleyFactory.getRequestQueue(context).add(stringRequest);
    }

    public void cancel_GetCustomerList()
    {
        VolleyFactory.getRequestQueue(context).cancelAll(TAG_EXECUTE_GET_CUSTOMER_LIST);
    }
    //endregion


    //region Get product
    public static final String TAG_EXCUTE_GET_Customer = "TAG_EXCUTE_GET_PRODUCT";

    public void execute_GetCustomer(final long id, final CustomerDetailCallback callback)
    {
        String url = "http://rest.elkstein.org/2008/02/real-rest-examples.html";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                Gson gson = new Gson();
                Customer customer = gson.fromJson(response.toString(), Customer.class);
                callback.onLoaded(customer);

            }
        }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error)
            {

                callback.onError(error.toString());
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

        jsonObjectRequest.setTag(TAG_EXCUTE_GET_Customer);
        VolleyFactory.getRequestQueue(context).add(jsonObjectRequest);
    }

    public void cancel_GetCustomer()
    {
        VolleyFactory.getRequestQueue(context).cancelAll(TAG_EXCUTE_GET_Customer);
    }
    //endregion


}
