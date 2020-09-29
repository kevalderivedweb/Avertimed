package com.example.avertimed;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.avertimed.API.CategoryRequest;
import com.example.avertimed.API.HomeRequest;
import com.example.avertimed.Adapter.CategoryAdapter;
import com.example.avertimed.Model.CategoryModel;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllTopTrends extends AppCompatActivity {


    private RequestQueue requestQueue;
    private RecyclerView category_view;
    private SugestAdapter2 mAdapter;
    private ArrayList<CategoryModel> categoryModels = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allcategories);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));

        requestQueue = Volley.newRequestQueue(AllTopTrends.this);//Creating the RequestQueue

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        category_view = findViewById(R.id.category_view);
        mAdapter = new SugestAdapter2(categoryModels, new SugestAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {

            }
        });
        category_view.setHasFixedSize(true);
        category_view.setLayoutManager(new GridLayoutManager(this, 2));
        category_view.setAdapter(mAdapter);
        category_view.setNestedScrollingEnabled(false);

        TextView name = findViewById(R.id.name);
        name.setText("Top Trends");

        GetCategory();

    }

    public void GetCategory() {

        final KProgressHUD progressDialog = KProgressHUD.create(AllTopTrends.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        HomeRequest loginRequest = new HomeRequest(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response + " null");
                progressDialog.dismiss();

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    // Toast.makeText(ProductActivity.this,jsonObject.getString("ResponseMsg"),Toast.LENGTH_SHORT).show();
                    if (jsonObject.getInt("ResponseCode")==200) {

                        JSONObject object = jsonObject.getJSONObject("data");

                        JSONArray header_products = object.getJSONArray("header-products");
                        JSONArray categories = object.getJSONArray("categories");
                        JSONArray top_trends = object.getJSONArray("top-trends");
                        JSONArray new_products = object.getJSONArray("new-products");


                        for(int i =0 ; i<top_trends.length();i++){
                            JSONObject object_top_trends = top_trends.getJSONObject(i);
                            CategoryModel categoryModel = new CategoryModel();
                            categoryModel.setCat_id(object_top_trends.getInt("ProductID"));
                            categoryModel.setCat_name_en(object_top_trends.getString("ProductTitleEn"));
                            categoryModel.setCat_name_image(object_top_trends.getString("ProductImage"));
                            categoryModel.setDescription(object_top_trends.getString("DescriptionEn"));
                            categoryModel.setTxt_price(object_top_trends.getString("Price"));
                            categoryModels.add(categoryModel);
                        }

                        mAdapter.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(AllTopTrends.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                if (error instanceof ServerError)
                    Toast.makeText(AllTopTrends.this, "Server Error", Toast.LENGTH_SHORT).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText(AllTopTrends.this, "Connection Timed Out", Toast.LENGTH_SHORT).show();
                else if (error instanceof NetworkError)
                    Toast.makeText(AllTopTrends.this, "Bad Network Connection", Toast.LENGTH_SHORT).show();
            }
        });
        loginRequest.setTag("TAG");
        requestQueue.add(loginRequest);

    }

}