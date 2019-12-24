package com.avijit.jobseeker;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avijit.jobseeker.Models.User;
import com.avijit.jobseeker.adapters.CategoryListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SignUpActivity extends AppCompatActivity {
    EditText emailEditText,userNameEditText,passwordEditText,mobileNumberEditText;
    Button signUpButton,loginIntentButton;
    Handler mainHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailEditText = findViewById(R.id.email_edit_text);
        userNameEditText = findViewById(R.id.user_name_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        mobileNumberEditText = findViewById(R.id.mobile_number_edit_text);

        signUpButton = findViewById(R.id.signup_button);
        loginIntentButton = findViewById(R.id.login_intent_button);

        loginIntentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User();
                user.setEmail(emailEditText.getText().toString());
                user.setName(userNameEditText.getText().toString());
                user.setMobile(mobileNumberEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());
                register(user);

            }
        });
    }
    private void register(final User user)
    {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://androstar.tk/jobseeker/api-v2.php/";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            if(message.equals("Successfully logged in"))
                            {
                                Toast.makeText(getApplicationContext(),"Couldn't Sign Up. \nEmail Already Exists",Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(getApplicationContext(),SelectCategoryActivity.class));
                            }
                            else
                            {
                                Toast.makeText(SignUpActivity.this,"Your are registered successfully..",Toast.LENGTH_SHORT).show();
                                mainHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                        startActivity(intent);
                                    }
                                },500);
                            }
                        }catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(), "Json Exception", Toast.LENGTH_SHORT).show();
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
                Map<String,String> params = new HashMap<String,String>() ;
                params.put("access_key","6808");
                params.put("user_signup","1");
                params.put("name",user.getName());
                params.put("email",user.getEmail());
                params.put("profile","");
                params.put("mobile",user.getMobile());
                params.put("type","email");
                params.put("fcm_id","abcde");
                params.put("ip_address","127.0.0.1");
                params.put("status","1");
                params.put("password",user.getPassword());

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
