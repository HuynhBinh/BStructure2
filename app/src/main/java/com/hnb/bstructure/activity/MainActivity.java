package com.hnb.bstructure.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hnb.bstructure.R;
import com.hnb.bstructure.daocontroller.ProductDAO;
import com.hnb.bstructure.domain.DomainFactory;
import com.hnb.bstructure.domain.ProductDomain;
import com.hnb.bstructure.volleycontroller.ProductVolley;

import java.util.List;

import greendao.GProduct;


public class MainActivity extends ActionBarActivity
{

    Button btnLoadAll;
    Button btnLoadOne;
    TextView txtData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView()
    {
        btnLoadAll = (Button) findViewById(R.id.btnLoadAll);
        txtData = (TextView) findViewById(R.id.txtData);
        btnLoadOne = (Button) findViewById(R.id.btnLoadOne);

        btnLoadOne.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                ProductDomain productDomain = new ProductDomain();
                productDomain.getProductList(MainActivity.this, DomainFactory.VOLLEY, new ProductDomain.dProductListCallback()
                {
                    @Override
                    public void onLoaded(final List<GProduct> gProductList)
                    {
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                Toast.makeText(MainActivity.this, gProductList.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onError(final String error)
                    {
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        });


        btnLoadAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                ProductDAO productDAO = new ProductDAO(MainActivity.this);
                productDAO.execute_GetProductList(new ProductDAO.dbProductListCallback()
                {
                    @Override
                    public void onLoaded(List<GProduct> gProductList)
                    {
                        String result = "";
                        for (GProduct gProduct : gProductList)
                        {
                            result += gProduct.toString();
                        }

                        final String re = result;

                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                Toast.makeText(MainActivity.this, re, Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                    @Override
                    public void onError(String error)
                    {
                        Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
                    }
                });


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
