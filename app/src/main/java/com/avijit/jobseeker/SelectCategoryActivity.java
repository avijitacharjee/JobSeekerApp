package com.avijit.jobseeker;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avijit.jobseeker.adapters.CategoryListAdapter;
import com.avijit.jobseeker.retroclient.com.avijit.jobseeker.retroclient.ApiClient;
import com.avijit.jobseeker.retroclient.com.avijit.jobseeker.retroclient.ApiInterface;
import com.avijit.jobseeker.retroclient.com.avijit.jobseeker.retroclient.models.Category;
import com.avijit.jobseeker.retroclient.com.avijit.jobseeker.retroclient.models.User;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class SelectCategoryActivity extends AppCompatActivity {

    int[] images = {1,2,3};
    String[] texts ={};
    CategoryListAdapter adapter;
    ListView listView;
    List<String> s = new ArrayList<>();
    TextView hiddenTextView;
    static int[] categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);

        listView = findViewById(R.id.category_list_view);
        texts = getCategoryNames();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),SubCategoryActivity.class);
                intent.putExtra("position",categoryId[position]);
                startActivity(intent);
            }
        });

    }
    public String[] getCategoryNames()
    {
        String[] categoryNames;
        List<String> categoryNamesList = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://androstar.tk/jobseeker/api-v2.php/";
        JSONObject body = new JSONObject();
        try
        {
            body.put("access_key","6808");
            body.put("get_categories","1");
        } catch (JSONException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      /*  textView.setText("Worked"+response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            textView.setText(jsonObject.get("data").toString());
                        } catch (JSONException e) {
                            textView.setText(e.toString());
                        }*/
                      //hiddenTextView.setText(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            int length = data.length();
                            List<Integer> ids= new ArrayList<>();

                            for(int i=0;i<length;i++)
                            {
                                JSONObject category = data.getJSONObject(i);
                                s.add(category.getString("category_name"));
                                ids.add(Integer.parseInt(category.getString("id")));
                            }
                            String[] texts = new String[s.size()];
                            int[] categoryIds = new int[ids.size()];
                            for(int i=0;i<s.size();i++)
                            {
                                texts[i]=s.get(i);
                                categoryIds[i]=ids.get(i);
                            }
                            SelectCategoryActivity.categoryId=categoryIds;

                            adapter = new CategoryListAdapter(getApplicationContext(),texts,images);
                            listView.setAdapter(adapter);
                           // hiddenTextView.setText(s.toString());
                            Toast.makeText(SelectCategoryActivity.this, ""+data, Toast.LENGTH_SHORT).show();
                        }catch (JSONException e)
                        {
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                /*textView.setText("Failed"+error.toString());*/
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){

            /* passing request body */
            protected Map<String,String> getParams()
            {
                Map<String,String> params = new HashMap<String, String>() ;
                params.put("access_key","6808");
                params.put("get_categories","1");

                return params;
            }
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("Content-Type", "application/x-www-form-urlencoded");

                return headers;
            }
        };

// Add the request to the RequestQueue.
        //getApplicationContext().addToRequestQueue(jsonObjectRequest, "headerRequest");
        queue.add(stringRequest);

       return new String[]{"A","b","C"};
    }

   /* public void retrofit()
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        JSONObject body = new JSONObject();
        try
        {
            body.put("access_key","6808");
            body.put("get_categories","1");
        } catch (JSONException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }


        Call<List<Object>> call = apiInterface.getCategories(body);

        call.enqueue(new Callback<List<Object>>() {

            @Override
            public void onResponse(Call<List<Object>> call, retrofit2.Response<List<Object>> response) {
                textView.setText("Success"+response.toString());
            }

            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {

                Toast.makeText(getApplicationContext(),"Failed"+t.toString(),Toast.LENGTH_LONG).show();
                System.out.print(call.toString());
                textView.setText("Failed"+t.toString());
            }
        });*/
    }

