package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;


public class LogIn extends AppCompatActivity {

    Button lgnBtn;
    EditText userName1, userName2;
    SqlHelper db = new SqlHelper(LogIn.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        lgnBtn = findViewById(R.id.logIn);
        userName1 = findViewById(R.id.textUsernamePlayerOne);
        userName2 = findViewById(R.id.textUsernamePlayerTwo);
        lgnBtn.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View view) {

                UserModel user1, user2;
                if (User_Validation(userName1.getText().toString()) && User_Validation(userName2.getText().toString())) {

                    /*user1 = new UserModel(index, userName1.getText().toString(), 0, 0);
                    user2 = new UserModel(index, userName2.getText().toString(), 0, 0);

                    boolean success1 = false, success2 = false;
                    if (db.validateUser(user1) && db.validateUser(user2)) {
                        user1 = db.selectOne(user1);
                        user2 = db.selectOne(user2);

                    } else if (!db.validateUser(user1) && db.validateUser(user2)){
                        db.addOne(user1);
                        user2 = db.selectOne(user2);
                    }else if (!db.validateUser(user2) && db.validateUser(user1))
                    {
                        db.addOne(user2);
                        user1 = db.selectOne(user1);
                    }else
                    {
                        db.addOne(user1);
                        db.addOne(user2);
                    }*/
                    //maxindex = 0
                    //user1 = new UserModel(index, userName1.getText().toString(), 0, 0);
                    //user2 = new UserModel(index, userName2.getText().toString(), 0, 0);
                    user1 = new UserModel(0, userName1.getText().toString(), 0, 0);
                    user2 = new UserModel(0, userName2.getText().toString(), 0, 0);
                    boolean success1 = false, success2 = false;
                    if (db.validateUserByUser(userName1.getText().toString()) && db.validateUserByUser(userName2.getText().toString())) {
                        user1 = db.selectOne(user1);
                        user2 = db.selectOne(user2);

                    } else if (!db.validateUserByUser(userName1.getText().toString()) && db.validateUserByUser(userName2.getText().toString())){
                        db.addOne(user1);
                        user2 = db.selectOne(user2);

                    }else if (!db.validateUserByUser(userName2.getText().toString()) && db.validateUserByUser(userName1.getText().toString()))
                    {
                        db.addOne(user2);
                        user1 = db.selectOne(user1);
                    }else
                    {
                        db.addOne(user1);
                        db.addOne(user2);
                    }
                    Log_In(user1,user2);
                    Toast.makeText(getApplicationContext(),"Logged in successfully",Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(getApplicationContext(), "Please insert a username for both players", Toast.LENGTH_SHORT).show();
                }
            }

            ;
        });

    }


    private void Log_In(UserModel user1, UserModel user2) {
        Intent switchActivity = new Intent(this, MainActivity.class);
        switchActivity.putExtra("Player One User", user1.getUserName());
        switchActivity.putExtra("Player One Wins", user1.getWins());
        switchActivity.putExtra("Player One Loses", user1.getLoses());
        switchActivity.putExtra("Player Two User", user2.getUserName());
        switchActivity.putExtra("Player Two Wins", user2.getWins());
        switchActivity.putExtra("Player Two Loses", user2.getLoses());

        startActivity(switchActivity);
    }

    private boolean User_Validation(String sUsername) {
        if (sUsername.matches(""))
            return false;
        return true;
    }
}
