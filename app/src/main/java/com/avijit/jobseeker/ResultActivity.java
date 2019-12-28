package com.avijit.jobseeker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    TextView resultTextView;
    Button jobsButton;
    float percentage=0;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        resultTextView = findViewById(R.id.result_text_view);
        int totalQuestions = getIntent().getExtras().getInt("totalQuestions");
        int correctlyAnswered = getIntent().getExtras().getInt("correctlyAnswered");
        resultTextView.append(correctlyAnswered+" out of "+totalQuestions);
        percentage = (float)correctlyAnswered*100/totalQuestions;
        jobsButton = findViewById(R.id.jobsButton);
        if(percentage<60)
        {
            jobsButton.setText("You failed. Try Again");
            jobsButton.setTextColor(Color.RED);
            intent = new Intent(getApplicationContext(),SelectCategoryActivity.class);
        }
        else
        {
            intent = new Intent(getApplicationContext(),JobsActivity.class);
        }

        jobsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}
