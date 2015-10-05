package com.hnb.bstructure.daocontroller;

import android.content.Context;

import com.hnb.bstructure.application.MyApplication;
import com.hnb.bstructure.callbackinterface.iCallBack;
import com.hnb.bstructure.thread.BackgroundThreadExecutor;

import java.util.List;

import greendao.Customer;
import greendao.CustomerDao;
import greendao.GProductDao;

/**
 * Created by HuynhBinh on 10/5/15.
 */
public class CustomerDAO extends iCallBack
{

    public CustomerDAO(Context context)
    {
        this.context = context;
        this.backgroundThreadExecutor = BackgroundThreadExecutor.getInstance();


    }

    private CustomerDao getDao()
    {
        return ((MyApplication) context.getApplicationContext()).getDaoSession().getCustomerDao();
    }

    public void getCustomerList(final CustomerListCallback callback)
    {
        this.backgroundThreadExecutor.runOnBackground(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    List<Customer> list = getDao().loadAll();
                    callback.onLoaded(list);
                }
                catch (Exception ex)
                {
                    callback.onError(ex.getMessage());

                }

            }
        });
    }


    public void getCustomer(final long id, final CustomerDetailCallback callback)
    {
        this.backgroundThreadExecutor.runOnBackground(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Customer customer = getDao().load(id);
                    if(customer != null)
                    {
                        callback.onLoaded(customer);
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


}
