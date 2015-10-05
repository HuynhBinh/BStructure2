package com.hnb.bstructure.daocontroller;

import android.content.Context;

import com.hnb.bstructure.application.MyApplication;
import com.hnb.bstructure.thread.BackgroundThreadExecutor;

import java.util.List;

import greendao.GProduct;
import greendao.GProductDao;

/**
 * Created by HuynhBinh on 9/9/15.
 */
public class ProductDAO
{
    //region VARIABLE
    // background thread to handle data in a separate thread from UI
    // will not lock the UI thread for improve performance
    BackgroundThreadExecutor backgroundThreadExecutor;

    Context context;
    //endregion


    //region INIT
    public ProductDAO(Context context)
    {
        // init background thread and ui thread
        this.backgroundThreadExecutor = BackgroundThreadExecutor.getInstance();
        this.context = context;
    }

    private GProductDao getDao(Context c)
    {
        return ((MyApplication) c.getApplicationContext()).getDaoSession().getGProductDao();
    }
    //endregion


    //region INTERFACE CALLBACK
    public interface dbProductListCallback
    {
        void onLoaded(List<GProduct> gProductList);

        void onError(String error);

    }

    public interface dbProductDetailCallback
    {
        void onLoaded(GProduct gProduct);

        void onError(String error);
    }
    //endregion


    //region GET PRODUCT LIST
    public void execute_GetProductList(final dbProductListCallback callback)
    {
        backgroundThreadExecutor.runOnBackground(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    List<GProduct> gProductList;
                    gProductList = getDao(context).loadAll();
                    callback.onLoaded(gProductList);
                }
                catch (final Exception ex)
                {
                    callback.onError(ex.toString());
                }
            }
        });

    }
    //endregion


    //region GET PRODUCT DETAIL
    public void execute_GetProduct(final long productID, final dbProductDetailCallback callback)
    {
        backgroundThreadExecutor.runOnBackground(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {

                    GProduct gProduct = getDao(context).load(productID);
                    if (gProduct != null)
                    {
                        callback.onLoaded(gProduct);
                    }
                    else
                    {
                        callback.onError("NULL");
                    }

                }
                catch (Exception ex)
                {
                    callback.onError(ex.getMessage());
                }

            }
        });
    }

    //endregion


    //region Test
    public static void insertProduct(Context context, GProduct gProduct)
    {
        ProductDAO productDAO = new ProductDAO(context);
        productDAO.getDao(context).insertOrReplace(gProduct);
    }
    //endregion


}
