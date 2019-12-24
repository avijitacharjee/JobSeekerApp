package com.avijit.jobseeker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avijit.jobseeker.Models.Question;
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

    AlertDialog alertDialog=null;
    private Handler mainHandler = new Handler();

    private TextView questionTextView;
    private ListView listView;
    private QuizListAdapater adapter;
    private String[] options={"lkjasdflkjsdf","lkadsjfkjf","hilakdf","How are you"};
    private int subCategoryId ;
    private static JSONObject jsonObject= new JSONObject();
    private String question="";
    private String answer;
    CountDownTimer countDownTimer;
    static int TIME_IN_MILLIS=1000;
    public static List<Question> questions ;
    private Button nextQuiz;
    private int questionIndex=1;
    private int correctlyAnswered=0;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        questionTextView= findViewById(R.id.question_text_view);
        nextQuiz = findViewById(R.id.quiz_next_button);
        questionTextView.setText("Loading");

        listView = findViewById(R.id.option_list_view);
        subCategoryId = getIntent().getExtras().getInt("subCategoryId");
        questions= new ArrayList<>();
        setQuestion();//It also initialises the jsonObject
        intent = new Intent(getApplicationContext(),ResultActivity.class);

        countDownTimer = new CountDownTimer(4000,500) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if(questionIndex<questions.size())
                {
                    Question question = questions.get(questionIndex++);
                    changeQuestion(question);
                    countDownTimer.start();
                }
            }
        };

        nextQuiz.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(questionIndex<questions.size())
                {
                    countDownTimer.start();
                    Question question = questions.get(questionIndex++);
                    changeQuestion(question);
                }
                else
                {
                    intent.putExtra("correctlyAnswered",correctlyAnswered);
                    intent.putExtra("totalQuestions",questions.size());
                    Toast.makeText(QuizActivity.this, "No more Questions", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                countDownTimer.cancel();
                countDownTimer.start();
                Toast.makeText(QuizActivity.this, position+"", Toast.LENGTH_SHORT).show();
                final Question question = questions.get(questionIndex-1);

                int answer=0;
                answer = question.getAnswerIndex(question);
                View selectedListItemView = listView.getChildAt(position);
                View answerListItemView = listView.getChildAt(answer);
                if(questionIndex<questions.size())
                {
                    if(answer==position)
                    {
                        Toast.makeText(QuizActivity.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                        selectedListItemView.setBackgroundColor(Color.GREEN);
                        selectedListItemView.findViewById(R.id.option_text).setBackgroundColor(Color.GREEN);
                        correctlyAnswered++;
                    }
                    else
                    {
                        selectedListItemView.setBackgroundColor(Color.RED);
                        selectedListItemView.findViewById(R.id.option_text).setBackgroundColor(Color.RED);
                        answerListItemView.setBackgroundColor(Color.GREEN);
                        answerListItemView.findViewById(R.id.option_text).setBackgroundColor(Color.GREEN);
                        Toast.makeText(QuizActivity.this,"Wrong Answer",Toast.LENGTH_SHORT).show();
                    }
                    mainHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(QuizActivity.this, "Handler", Toast.LENGTH_SHORT).show();
                            changeQuestion(question);
                        }
                    }, 800);

                    questionIndex++;
                }
                else
                {
                    if(answer==position)
                    {
                        Toast.makeText(QuizActivity.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(QuizActivity.this,"Wrong Answer",Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(QuizActivity.this,"No more questions",Toast.LENGTH_SHORT).show();
                    intent.putExtra("correctlyAnswered",correctlyAnswered);
                    intent.putExtra("totalQuestions",questions.size());
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    protected void onPause() {
        open();
        super.onPause();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        if (alertDialog!=null && alertDialog.isShowing()){
            alertDialog.dismiss();
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        if (alertDialog!=null && alertDialog.isShowing()){
            alertDialog.dismiss();
        }
        super.onStop();
    }

    public void setQuestion()
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
                            JSONObject questionsJsonObj=null;

                            for(int i=0;i<length;i++)
                            {
                                questionsJsonObj = data.getJSONObject(i);
                                questions.add(new Question(
                                        questionsJsonObj.getString("question"),
                                        questionsJsonObj.getString("optiona"),
                                        questionsJsonObj.getString("optionb"),
                                        questionsJsonObj.getString("optionc"),
                                        questionsJsonObj.getString("optiond"),
                                        questionsJsonObj.getString("answer")
                                ));
                            }
                            if(questions.size()>0)
                            {
                                changeQuestion(questions.get(0));
                                questionIndex++;
                            }

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
    public void changeQuestion(Question question)
    {
        questionTextView.setText(question.getQuestion());
        String[] options={question.getOption1(),question.getOption2(),question.getOption3(),question.getOption4()};
        adapter = new QuizListAdapater(getApplicationContext(),options);
        listView.setAdapter(adapter);

    }
    public void timer(final JSONObject questionJSONObject)
    {
        countDownTimer = new CountDownTimer(QuizActivity.TIME_IN_MILLIS,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {

            }
        }.start();
    }
    public void open(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Quiz stopped due to your inactivity");
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (alertDialog!=null && alertDialog.isShowing()){
                            alertDialog.dismiss();
                        }
                        finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
