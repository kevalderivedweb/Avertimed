package com.purchase.avertimed;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.purchase.avertimed.API.SearchProductRequest;
import com.purchase.avertimed.API.ServerUtils;
import com.purchase.avertimed.API.UserSession;
import com.purchase.avertimed.Adapter.NewProductAdapter;
import com.purchase.avertimed.Model.CategoryModel;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private ArrayList<CategoryModel> categoryModels_NewProduct = new ArrayList<>();
    private RecyclerView new_product;
    private NewProductAdapter New_Product_mAdapter;
    private RequestQueue requestQueue;
    private UserSession userSession;
    private EditText search_activity;
    private List<FavDatabaseModel> DataArrayList;
    private DbHelper_MultipleData dbHelper;
    private int SIZE;
    private LinearLayout no_data2;
    private LinearLayout no_data;
    private TextView T1, T2, T3, T4;
    private String mCategoryName;
    private String mPriceLow="";
    private String mPriceHigh="";
    private String SortByPrice="low_to_high";
    private Spinner category;

    private LinearLayoutManager linearLayout;
    private int IntPage = 1;
    private int last_size = 0;
    private Spinner sub_category;
    private String SUBCATEGORYNAME="";


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        dbHelper = new DbHelper_MultipleData(SearchActivity.this);
        requestQueue = Volley.newRequestQueue(SearchActivity.this);//Creating the RequestQueue
        userSession = new UserSession(getApplicationContext());

        no_data2 = findViewById(R.id.no_data2);
        new_product = findViewById(R.id.new_product);
        search_activity = findViewById(R.id.search_activity);
        new_product.setHasFixedSize(true);
        linearLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        new_product.setLayoutManager(linearLayout);
        New_Product_mAdapter = new NewProductAdapter(SearchActivity.this,categoryModels_NewProduct, new NewProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {

                Intent intent = new Intent(SearchActivity.this, GeneralPracticeActivity.class);
                intent.putExtra("ProductId", categoryModels_NewProduct.get(item).getCat_id());
                startActivity(intent);
            }
        });
        new_product.setAdapter(New_Product_mAdapter);
        new_product.setNestedScrollingEnabled(false);


        dbHelper = new DbHelper_MultipleData(SearchActivity.this);
        DataArrayList = new ArrayList<>();


        no_data = findViewById(R.id.no_data);
        T1 = findViewById(R.id.t1);
        T2 = findViewById(R.id.t2);
        T3 = findViewById(R.id.t3);
        T4 = findViewById(R.id.t4);
        search_data();
        GetProduct("",SUBCATEGORYNAME,mPriceLow,mPriceHigh,SortByPrice,IntPage);
        Log.e("Details", "???"+"----"+SUBCATEGORYNAME+"----"+mPriceLow+"----"+mPriceHigh);

        search_activity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_DONE) {
                    //do here your stuff f

                    dbHelper = new DbHelper_MultipleData(SearchActivity.this);
                    DataArrayList = new ArrayList<>();
                    DataArrayList = dbHelper.getSearch();


                    Log.e("Details", textView.getText().toString()+"----"+SUBCATEGORYNAME+"----"+mPriceLow+"----"+mPriceHigh);
                    GetProduct(textView.getText().toString(),SUBCATEGORYNAME,mPriceLow,mPriceHigh,SortByPrice,IntPage);
                    if (DataArrayList.size() >= 4) {
                        SIZE = 4;
                    } else {
                        SIZE = DataArrayList.size();
                    }

                    for (int j = 0; j < SIZE; j++) {
                        if (DataArrayList.get(j).getFavName().equals(textView.getText().toString())) {
                            // Toast.makeText(SearchActivity.this,"You have alredy added this product into cart.",Toast.LENGTH_SHORT).show();
                            return true;
                        }


                    }
                    dbHelper.insert_Search(textView.getText().toString());

                    DataArrayList = new ArrayList<>();
                    DataArrayList = dbHelper.getSearch();
                    if (DataArrayList.size() == 4) {
                        T1.setText(DataArrayList.get(0).getFavName());
                        T2.setText(DataArrayList.get(1).getFavName());
                        T3.setText(DataArrayList.get(2).getFavName());
                        T4.setText(DataArrayList.get(3).getFavName());
                        no_data.setVisibility(View.GONE);
                    } else if (DataArrayList.size() == 3) {
                        T1.setText(DataArrayList.get(0).getFavName());
                        T2.setText(DataArrayList.get(1).getFavName());
                        T3.setText(DataArrayList.get(2).getFavName());
                        T4.setVisibility(View.INVISIBLE);
                        no_data.setVisibility(View.GONE);
                    } else if (DataArrayList.size() == 2) {
                        T1.setText(DataArrayList.get(0).getFavName());
                        T2.setText(DataArrayList.get(1).getFavName());
                        T3.setVisibility(View.INVISIBLE);
                        T4.setVisibility(View.INVISIBLE);
                        no_data.setVisibility(View.GONE);
                    } else if (DataArrayList.size() == 1) {
                        T1.setText(DataArrayList.get(0).getFavName());
                        T2.setVisibility(View.INVISIBLE);
                        T3.setVisibility(View.INVISIBLE);
                        T4.setVisibility(View.INVISIBLE);
                        no_data.setVisibility(View.GONE);
                    } else if (DataArrayList.isEmpty()) {
                        T1.setVisibility(View.GONE);
                        T2.setVisibility(View.GONE);
                        T3.setVisibility(View.GONE);
                        T4.setVisibility(View.GONE);
                        no_data.setVisibility(View.VISIBLE);
                    } else {

                        T1.setText(DataArrayList.get(0).getFavName());
                        T2.setText(DataArrayList.get(1).getFavName());
                        T3.setText(DataArrayList.get(2).getFavName());
                        T4.setText(DataArrayList.get(3).getFavName());
                        no_data.setVisibility(View.GONE);
                    }

                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup();
            }
        });

        new_product.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayout) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                IntPage = page;
                if (page!=last_size){
                    int FinalOAgeSIze = page+1;
                    GetProduct(search_activity.getText().toString(),SUBCATEGORYNAME,mPriceLow,mPriceHigh,SortByPrice,FinalOAgeSIze);

                }
            }
        });

    }

    public void GetProduct(String search,String cat_id,String low,String high,String SortByPrice,int page) {

        categoryModels_NewProduct.clear();
        final KProgressHUD progressDialog = KProgressHUD.create(SearchActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        SearchProductRequest loginRequest = new SearchProductRequest(search,cat_id,low,high,SortByPrice, page,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response + " null");
                progressDialog.dismiss();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    // Toast.makeText(SearchActivity.this,jsonObject.getString("ResponseMsg"),Toast.LENGTH_SHORT).show();


                    JSONObject object1 = jsonObject.getJSONObject("data");
                    last_size = object1.getInt("last_page");

                    JSONArray jsonArray = object1.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        CategoryModel categoryModel = new CategoryModel();
                        categoryModel.setCat_id(object.getInt("ProductID"));
                        categoryModel.setCat_name_en(object.getString(userSession.getProductTitle()));
                        categoryModel.setCat_name_image(object.getString("ProductImage"));
                        categoryModel.setDescription(object.getString(userSession.getDescription()));
                   //     categoryModel.setDescription(object.getString("DescriptionFr"));
                        categoryModel.setTxt_price(object.getString("Price"));
                        categoryModels_NewProduct.add(categoryModel);
                    }

                    New_Product_mAdapter.notifyDataSetChanged();

                    if (categoryModels_NewProduct.isEmpty()) {
                        Toast.makeText(SearchActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                        no_data2.setVisibility(View.VISIBLE);
                        new_product.setVisibility(View.GONE);
                    } else {
                        no_data2.setVisibility(View.GONE);
                        new_product.setVisibility(View.VISIBLE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                error.printStackTrace();
                if (error instanceof ServerError)
                    Toast.makeText(SearchActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText(SearchActivity.this, "Connection Timed Out", Toast.LENGTH_SHORT).show();
                else if (error instanceof NetworkError)
                    Toast.makeText(SearchActivity.this, "Bad Network Connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + userSession.getAPIToken());
                return params;
            }
        };
        loginRequest.setTag("TAG");
        loginRequest.setShouldCache(false);

        requestQueue.add(loginRequest);

    }


    public void GetCategory(){
        final KProgressHUD progressDialog = KProgressHUD.create(SearchActivity.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        progressDialog.show();
        AndroidNetworking.get(ServerUtils.BASE_URL+"get-categories")
                .addHeaders("Accept","application/json")
                .addHeaders("Authorization","Bearer "+ userSession.getAPIToken())
                .setTag("Feed")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response : ", response);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getInt("ResponseCode")==200) {

                                try {

                                    JSONArray jsonObject1 = jsonObject.getJSONArray("data");

                                    final String[] City = new String[jsonObject1.length()];
                                    final String[] CityId = new String[jsonObject1.length()];

                                    for (int i = 0; i < jsonObject1.length(); i++) {
                                        JSONObject object = jsonObject1.getJSONObject(i);
                                        City[i] =  object.getString(userSession.getCategoryname());
                                        CityId[i] =  object.getString("CategoryID");
                                    }

                                    ArrayAdapter<String> adapter_age = new ArrayAdapter<String>(SearchActivity.this,
                                            android.R.layout.simple_spinner_item, City);
                                    category.setAdapter(adapter_age);
                                    category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view,
                                                                   int position, long id) {
                                            Log.e("currency",""+position);
                                            GetSubCategory(CityId[position]);
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {
                                            // TODO Auto-generated method stub
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }else {

                                Toast.makeText(SearchActivity.this,jsonObject.getString("ResponseMsg"),Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                        Toast.makeText(SearchActivity.this,"Unauthenticated",Toast.LENGTH_SHORT).show();}
                });

    }

    public void GetSubCategory(String CategoryID){
        final KProgressHUD progressDialog = KProgressHUD.create(SearchActivity.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        progressDialog.show();
        AndroidNetworking.get(ServerUtils.BASE_URL+"get-sub-categories?CategoryID="+CategoryID)
                .addHeaders("Accept","application/json")
                .addHeaders("Authorization","Bearer "+ userSession.getAPIToken())
                .setTag("Feed")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response : ", response);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getInt("ResponseCode")==200) {

                                try {

                                    JSONArray jsonObject1 = jsonObject.getJSONArray("data");

                                    final String[] City = new String[jsonObject1.length()];
                                    final String[] CityId = new String[jsonObject1.length()];

                                    for (int i = 0; i < jsonObject1.length(); i++) {
                                        JSONObject object = jsonObject1.getJSONObject(i);
                                        City[i] =  object.getString(userSession.getSubcategoryname());
                                        CityId[i] =  object.getString("SubCategoryID");
                                    }

                                    ArrayAdapter<String> adapter_age = new ArrayAdapter<String>(SearchActivity.this,
                                            android.R.layout.simple_spinner_item, City);
                                    sub_category.setAdapter(adapter_age);
                                    sub_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view,
                                                                   int position, long id) {
                                            Log.e("currency",""+position);

                                            SUBCATEGORYNAME = CityId[position];

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {
                                            // TODO Auto-generated method stub
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }else {

                                Toast.makeText(SearchActivity.this,jsonObject.getString("ResponseMsg"),Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                        Toast.makeText(SearchActivity.this,"Unauthenticated",Toast.LENGTH_SHORT).show();}
                });

    }



    public void search_data() {
        DataArrayList = dbHelper.getSearch();
        if (DataArrayList.size() == 4) {
            T1.setText(DataArrayList.get(0).getFavName());
            T2.setText(DataArrayList.get(1).getFavName());
            T3.setText(DataArrayList.get(2).getFavName());
            T4.setText(DataArrayList.get(3).getFavName());
            no_data.setVisibility(View.GONE);
        } else if (DataArrayList.size() == 3) {
            T1.setText(DataArrayList.get(0).getFavName());
            T2.setText(DataArrayList.get(1).getFavName());
            T3.setText(DataArrayList.get(2).getFavName());
            T4.setVisibility(View.INVISIBLE);
            no_data.setVisibility(View.GONE);
        } else if (DataArrayList.size() == 2) {
            T1.setText(DataArrayList.get(0).getFavName());
            T2.setText(DataArrayList.get(1).getFavName());
            T3.setVisibility(View.INVISIBLE);
            T4.setVisibility(View.INVISIBLE);
            no_data.setVisibility(View.GONE);
        } else if (DataArrayList.size() == 1) {
            T1.setText(DataArrayList.get(0).getFavName());
            T2.setVisibility(View.INVISIBLE);
            T3.setVisibility(View.INVISIBLE);
            T4.setVisibility(View.INVISIBLE);
            no_data.setVisibility(View.GONE);
        } else if (DataArrayList.isEmpty()) {
            T1.setVisibility(View.GONE);
            T2.setVisibility(View.GONE);
            T3.setVisibility(View.GONE);
            T4.setVisibility(View.GONE);
            no_data.setVisibility(View.VISIBLE);
        } else {

            T1.setText(DataArrayList.get(0).getFavName());
            T2.setText(DataArrayList.get(1).getFavName());
            T3.setText(DataArrayList.get(2).getFavName());
            T4.setText(DataArrayList.get(3).getFavName());
            no_data.setVisibility(View.GONE);
        }


    }

    private void showPopup() {
        // custom dialog
        final Dialog dialog = new Dialog(SearchActivity.this);
        dialog.setContentView(R.layout.add_filtter);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        TextView m_cancel = dialog.findViewById(R.id.m_cancel);
        final TextView txt_short = dialog.findViewById(R.id.txt_short);
        final EditText high = dialog.findViewById(R.id.high);
        final EditText low = dialog.findViewById(R.id.low);
        category = (Spinner) dialog.findViewById(R.id.category);
        sub_category = (Spinner) dialog.findViewById(R.id.sub_category);
        SwitchCompat shortby = (SwitchCompat) dialog.findViewById(R.id.shortby);


        shortby.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    SortByPrice = "high_to_low";
                    txt_short.setText("High - Low");
                }else {
                    SortByPrice = "low_to_high";
                    txt_short.setText("Low - High");
                }
            }
        });
        high.setText(mPriceHigh);
        low.setText(mPriceLow);
        dialog.findViewById(R.id.m_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPriceHigh = high.getText().toString();
                mPriceLow = low.getText().toString();
                dialog.dismiss();
                Log.e("Details", search_activity.getText().toString()+"----"+SUBCATEGORYNAME+"----"+mPriceLow+"----"+mPriceHigh);

                GetProduct(search_activity.getText().toString(),SUBCATEGORYNAME,mPriceLow,mPriceHigh,SortByPrice,IntPage);

            }
        });

        GetCategory();
        m_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }


    public void setLocale(String lang) {

        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserSession userSession = new UserSession(this);
        setLocale(userSession.getLanguageCode());
    }

}