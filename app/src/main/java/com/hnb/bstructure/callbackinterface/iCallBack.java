package com.hnb.bstructure.callbackinterface;

import android.content.Context;

import com.android.internal.util.Predicate;
import com.hnb.bstructure.thread.BackgroundThreadExecutor;

import java.util.List;

import greendao.Customer;
import greendao.GProduct;

/**
 * Created by HuynhBinh on 10/5/15.
 */
public class iCallBack
{

    //region VARIABLE
    // background thread to handle data in a separate thread from UI
    // will not lock the UI thread for improve performance
    public BackgroundThreadExecutor backgroundThreadExecutor;
    //region VARIABLE
    public Context context;
    //endregion

   public interface cCallback<T>
   {
       void onSuccess(T t);

   }




    public interface ProductListCallback
    {
        void onLoaded(List<GProduct> productList);

        void onError(String error);

    }

    public interface ProductDetailCallback
    {
        void onLoaded(GProduct product);

        void onError(String error);
    }

    public interface CustomerListCallback
    {
        void onLoaded(List<Customer> customerList);

        void onError(String error);
    }


    public interface CustomerDetailCallback
    {
        void onLoaded(Customer customer);

        void onError(String error);
    }

}
