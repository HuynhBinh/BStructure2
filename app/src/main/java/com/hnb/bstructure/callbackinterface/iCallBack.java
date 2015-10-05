package com.hnb.bstructure.callbackinterface;

import java.util.List;

import greendao.GProduct;

/**
 * Created by HuynhBinh on 10/5/15.
 */
public class iCallBack
{

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
    
}
