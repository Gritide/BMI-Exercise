package com.example.bmmo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

public class Quiz extends AppCompatActivity {


    public Quiz(){}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);
    }

    public String Questions[] = {
            "question1",
            "question2",
            "question3",
            "question4",
            "question5",
            "question6",
            "question7",

    };
    private String Choices[][] = {
            {"choice1", "choice2", "choice3", "choice4"},
            {"choice1", "choice2", "choice3", "choice4"},
            {"choice1", "choice2", "choice3", "choice4"},
            {"choice1", "choice2", "choice3", "choice4"},
            {"choice1", "choice2", "choice3", "choice4"},
            {"choice1", "choice2", "choice3", "choice4"},
            {"choice1", "choice2", "choice3", "choice4"},

    };
    private String correctAnswers[] = {"choice1", "choice2", "choice3", "choice4", "choice1", "choice2", "choice3"};

    public String getQuestion(int quest) {
        String question = Questions[quest];
        return question;
    }
    public String getAnswer(int quest) {
        String choice = Choices[quest][0];
        return choice;
    }
}
