package com.avijit.jobseeker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avijit.jobseeker.adapters.CategoryListAdapter;
import com.avijit.jobseeker.adapters.QuizListAdapater;
import com.avijit.jobseeker.adapters.SubCategoryListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {
    private TextView questionTextView;
    private ListView listView;
    private QuizListAdapater adapter;
    private String[] options={"lkjasdflkjsdf","lkadsjfkjf","hilakdf","How are you"};
    private int subCategoryId ;
    private static JSONObject jsonObject= new JSONObject();
    private String question="";
    private String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionTextView= findViewById(R.id.question_text_view);
        questionTextView.setText("lakjsdflkjasdflkjasdfkdkfkjfjfjfjf");

        listView = findViewById(R.id.option_list_view);


        subCategoryId = getIntent().getExtras().getInt("subCategoryId");
        setOptionTexts();//It also initialises the jsonObject
        try {
            JSONArray data = jsonObject.getJSONArray("data");
            int length= data.length();
            for(int i=0;i<length;i++)
            {
                JSONObject questionsJsonObj = data.getJSONObject(i);
                question=questionsJsonObj.getString("question");
                questionTextView.setText(question);
                options[0]=questionsJsonObj.getString("optiona");
                options[1]=questionsJsonObj.getString("optionb");
                options[2]=questionsJsonObj.getString("optionc");
                options[3]=questionsJsonObj.getString("optiond");
                answer = questionsJsonObj.getString("answer");
                break;
            }

        } catch (JSONException e) {
            System.out.println(e.toString());
        }


    }
    public void setOptionTexts()
    {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://androstar.tk/jobseeker/api-v2.php/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<String> s = new ArrayList<>();
                            List<Integer> ids= new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response);
                            QuizActivity.jsonObject = jsonObject;

                            JSONArray data = jsonObject.getJSONArray("data");
                            int length= data.length();
                            for(int i=0;i<length;i++)
                            {
                                JSONObject questionsJsonObj = data.getJSONObject(i);
                                question=questionsJsonObj.getString("question");
                                questionTextView.setText(question);
                                options[0]=questionsJsonObj.getString("optiona");
                                options[1]=questionsJsonObj.getString("optionb");
                                options[2]=questionsJsonObj.getString("optionc");
                                options[3]=questionsJsonObj.getString("optiond");
                                answer = questionsJsonObj.getString("answer");

                            }
                            adapter = new QuizListAdapater(getApplicationContext(),options);
                            listView.setAdapter(adapter);
                            /*JSONArray data = jsonObject.getJSONArray("data");
                            int length = data.length();
                            for(int i=0;i<length;i++)
                            {
                                JSONObject category = data.getJSONObject(i);
                                s.add(category.getString("subcategory_name"));
                                ids.add(Integer.parseInt(category.getString("id")));
                            }
                            int[] idArr = new int[ids.size()];
                            String[] texts = new String[s.size()];
                            for(int i=0;i<s.size();i++)
                            {
                                texts[i]=s.get(i);
                                idArr[i]=ids.get(i);
                            }
                            SubCategoryActivity.subCategoryIds=idArr;
                            adapter = new SubCategoryListAdapter(getApplicationContext(),texts,images);
                            listView.setAdapter(adapter);*/

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
                params.put("get_questions_by_subcategory","1");
                params.put("subcategory",subCategoryId+"");

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
