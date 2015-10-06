package com.hnb.bstructure.domain;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hnb.bstructure.callbackinterface.iCallBack;
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
public class ProductDomain extends DomainBase
{

    public ProductDomain(Context context)
    {
        this.context = context;

    }

    public interface dProductListCallback
    {
        void onLoaded(List<GProduct> gProductList);

        void onError(String error);
    }

    public void getProductsThatPriceLessThan50(final dProductListCallback callback)
    {
        final List<GProduct> listResults = new ArrayList<>();
        ProductVolley productVolley = new ProductVolley(context);
        productVolley.execute_GetProductList(new iCallBack.ProductListCallback()
        {
            @Override
            public void onLoaded(List<GProduct> productList)
            {

                for (GProduct product : productList)
                {
                    if (product.getPrice() < 50.00)
                    {
                        listResults.add(product);
                    }
                }

                ProductDAO productDAO = new ProductDAO(context);
                productDAO.getProductList(new iCallBack.ProductListCallback()
                {
                    @Override
                    public void onLoaded(List<GProduct> productList)
                    {
                        for (GProduct product : productList)
                        {
                            if (product.getPrice() < 50.00)
                            {
                                if (!listResults.contains(product))
                                {
                                    listResults.add(product);
                                }
                            }
                        }

                        callback.onLoaded(listResults);

                    }

                    @Override
                    public void onError(String error)
                    {

                        callback.onError(error);
                    }
                });
            }

            @Override
            public void onError(String error)
            {
                callback.onError(error);

            }
        });
    }

    public void getProductList(String WHERE, final dProductListCallback callback)
    {
        if (WHERE.equals(DomainFactory.DATABASE))
        {
            ProductDAO productDAO = new ProductDAO(context);
            productDAO.getProductList(new iCallBack.ProductListCallback()
            {
                @Override
                public void onLoaded(List<GProduct> gProductList)
                {
                    //handle data here before callback to UI

                    //handle data here before callback to UI
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
            productVolley.execute_GetProductList(new iCallBack.ProductListCallback()
            {
                @Override
                public void onLoaded(List<GProduct> gProductList)
                {

                    //handle data here before callback to UI
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


                    Type collectionType = new TypeToken<Collection<GProduct>>()
                    {
                    }.getType();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    //gsonBuilder.setDateFormat("M/d/yy hh:mm a");

                    Gson gson = gsonBuilder.create();
                    String resultJson = gson.toJson(list, collectionType);


                    List<GProduct> listProducts = new ArrayList<GProduct>();
                    listProducts = Arrays.asList(gson.fromJson(resultJson, GProduct[].class));


                    //Collection<Person> ints2 = gson.fromJson(result, collectionType);


                    //Type collectionType = new TypeToken<Collection<GProduct>>(){}.getType();
                    //Collection<GProduct> ints2 = gson.fromJson(result, collectionType);


                    //handle data here before callback to UI
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
