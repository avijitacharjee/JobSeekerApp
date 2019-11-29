package com.avijit.jobseeker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avijit.jobseeker.adapters.CategoryListAdapter;
import com.avijit.jobseeker.adapters.SubCategoryListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubCategoryActivity extends AppCompatActivity {
    ListView listView;
    SubCategoryListAdapter adapter;
    int categoryId;
    int[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        listView = findViewById(R.id.sub_category_list_view);
        categoryId = getIntent().getExtras().getInt("position");
        setListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Callback method to be invoked when an item in this AdapterView has
             * been clicked.
             * <p>
             * Implementers can call getItemAtPosition(position) if they need
             * to access the data associated with the selected item.
             *
             * @param parent   The AdapterView where the click happened.
             * @param view     The view within the AdapterView that was clicked (this
             *                 will be a view provided by the adapter)
             * @param position The position of the view in the adapter.
             * @param id       The row id of the item that was clicked.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }
    public void setListView ()
    {

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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<String> s = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            int length = data.length();
                            for(int i=0;i<length;i++)
                            {
                                JSONObject category = data.getJSONObject(i);
                                s.add(category.getString("subcategory_name"));
                            }
                            String[] texts = new String[s.size()];
                            for(int i=0;i<s.size();i++)
                            {
                                texts[i]=s.get(i);
                            }

                            adapter = new SubCategoryListAdapter(getApplicationContext(),texts,images);
                            listView.setAdapter(adapter);

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
                params.put("get_subcategory_by_maincategory","1");
                params.put("main_id",categoryId+"");

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
    }
}
