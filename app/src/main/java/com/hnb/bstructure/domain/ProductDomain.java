package com.hnb.bstructure.domain;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hnb.bstructure.daocontroller.ProductDAO;
import com.hnb.bstructure.volleycontroller.ProductVolley;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import greendao.GProduct;

/**
 * Created by USER on 9/11/2015.
 */
public class ProductDomain
{


    public interface dProductListCallback
    {
        void onLoaded(List<GProduct> gProductList);

        void onError(String error);
    }

    public void getProductList(Context context, String WHERE, final dProductListCallback callback)
    {
        if (WHERE.equals(DomainFactory.DATABASE))
        {
            ProductDAO productDAO = new ProductDAO(context);
            productDAO.execute_GetProductList(new ProductDAO.dbProductListCallback()
            {
                @Override
                public void onLoaded(List<GProduct> gProductList)
                {
                    callback.onLoaded(gProductList);
                }

                @Override
                public void onError(String error)
                {
                    callback.onError(error);
                }
            });

        }
        else
        {
            ProductVolley productVolley = new ProductVolley(context);
            productVolley.execute_GetProductList(new ProductVolley.vlProductListCallback()
            {
                @Override
                public void onLoaded(String result)
                {

                    List<GProduct> list = new ArrayList<GProduct>();
                    GProduct gProduct = new GProduct();
                    gProduct.setId(1l);
                    //gProduct.setName("Iphone 6s");
                    //gProduct.setDescription("ip 6s plus desc");
                    gProduct.setPrice(50.00);
                    list.add(gProduct);

                    gProduct = new GProduct();
                    gProduct.setId(2l);
                    //gProduct.setName("Nexus 7");
                    //gProduct.setDescription("A nexus from google");
                    gProduct.setPrice(45.99);
                    list.add(gProduct);


                    Type collectionType = new TypeToken<Collection<GProduct>>(){}.getType();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    //gsonBuilder.setDateFormat("M/d/yy hh:mm a");

                    Gson gson = gsonBuilder.create();
                    String resultJson = gson.toJson(list, collectionType);


                    List<GProduct> listProducts = new ArrayList<GProduct>();
                    listProducts = Arrays.asList(gson.fromJson(resultJson, GProduct[].class));


                    //Collection<Person> ints2 = gson.fromJson(result, collectionType);


                    //Type collectionType = new TypeToken<Collection<GProduct>>(){}.getType();
                    //Collection<GProduct> ints2 = gson.fromJson(result, collectionType);



                    callback.onLoaded(listProducts);

                }

                @Override
                public void onError(String error)
                {
                    callback.onError(error);

                }
            });

        }

    }

}
