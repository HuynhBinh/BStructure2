package com.hnb.bstructure.daocontroller;

import android.content.Context;

import com.hnb.bstructure.application.MyApplication;
import com.hnb.bstructure.callbackinterface.iCallBack;
import com.hnb.bstructure.thread.BackgroundThreadExecutor;

import java.util.List;

import greendao.GProduct;
import greendao.GProductDao;

/**
 * Created by HuynhBinh on 9/9/15.
 */
public class ProductDAO extends iCallBack
{


    //region INIT
    public ProductDAO(Context context)
    {
        // init background thread and ui thread
        this.backgroundThreadExecutor = BackgroundThreadExecutor.getInstance();
        this.context = context;
    }

    private GProductDao getDao()
    {
        return ((MyApplication) context.getApplicationContext()).getDaoSession().getGProductDao();
    }
    //endregion


    //region GET PRODUCT LIST
    public void getProductList(final ProductListCallback callback)
    {
        backgroundThreadExecutor.runOnBackground(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    List<GProduct> gProductList = getDao().loadAll();
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
    public void getProduct(final long productID, final ProductDetailCallback callback)
    {
        backgroundThreadExecutor.runOnBackground(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {

                    GProduct gProduct = getDao().load(productID);
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


    public static void insertProduct(Context ctx, GProduct  product)
    {
        ((MyApplication) ctx.getApplicationContext()).getDaoSession().getGProductDao().insert(product);
    }
}
