package com.hnb.bstructure.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.hnb.bstructure.daocontroller.ProductDAO;

import greendao.DaoMaster;
import greendao.DaoSession;
import greendao.GProduct;

/**
 * Created by USER on 9/10/2015.
 */
public class MyApplication extends Application
{


    private static MyApplication mInstance;

    public DaoSession daoSession;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;

        setupDatabase();
        createFakeData();


    }

    public static synchronized MyApplication getInstance()
    {
        return mInstance;
    }


    private void setupDatabase()
    {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "bstructure", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession()
    {
        if (daoSession != null)
        {
            return daoSession;
        }
        else
        {
            setupDatabase();
            return daoSession;
        }
    }

    private void createFakeData()
    {
        GProduct gProduct = new GProduct();
        gProduct.setId(1l);
        gProduct.setName("asd");
        gProduct.setDescription("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        gProduct.setPrice(23.23);
        ProductDAO.insertProduct(this, gProduct);

        gProduct = new GProduct();
        gProduct.setId(2l);
        gProduct.setName("qwe");
        gProduct.setDescription("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
        gProduct.setPrice(23.23);
        ProductDAO.insertProduct(this, gProduct);


        gProduct = new GProduct();
        gProduct.setId(3l);
        gProduct.setName("zxc");
        gProduct.setDescription("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
        gProduct.setPrice(23.23);
        ProductDAO.insertProduct(this, gProduct);

    }


}
