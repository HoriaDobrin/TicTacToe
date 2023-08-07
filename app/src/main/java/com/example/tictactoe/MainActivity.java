package com.example.tictactoe;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneWins, playerTwoWins, playerOneLoses, playerTwoLoses, playerStatus, playerOneName, playerTwoName;
    private final Button[] buttons = new Button[9];
    private Button resetGame;
    private int playerOneWinsCount, playerTwoWinsCount, playerOneLosesCount, playerTwoLosesCount, roundCount, playerOneId, playerTwoId;
    boolean activePlayer;

    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int [][] winningPositions = {
            {0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}
    };
    SqlHelper db = new SqlHelper(MainActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        String Name1 = extras.getString("Player One User");
        String Name2 = extras.getString("Player Two User");

        int Wins1 = extras.getInt("Player One Wins");
        int Loses1 = extras.getInt("Player One Loses");

        int Wins2 = extras.getInt("Player Two Wins");
        int Loses2 = extras.getInt("Player Two Loses");


        playerOneWins = (TextView) findViewById(R.id.playerOneWins);
        playerTwoWins = (TextView) findViewById(R.id.playerTwoWins);
        playerOneLoses = (TextView) findViewById(R.id.playerOneLoses);
        playerTwoLoses = (TextView) findViewById(R.id.playerTwoLoses);
        playerStatus = (TextView) findViewById(R.id.playerStatus);
        playerOneName = (TextView) findViewById(R.id.playerOne);
        playerTwoName = (TextView) findViewById(R.id.playerTwo);
        resetGame = (Button) findViewById(R.id.resetGame);


        playerOneName.setText(Name1);
        playerTwoName.setText(Name2);
        playerOneWinsCount = Wins1;
        playerTwoWinsCount = Wins2;
        playerOneLosesCount = Loses1;
        playerTwoLosesCount = Loses2;
        updatePlayerScore();

        for(int i = 0; i < buttons.length; i++){
            String buttonID = "button" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        roundCount = 0;
        activePlayer = true;
    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }

        String buttonID = view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1));


        if (activePlayer) {
            gameState[gameStatePointer] = 0;
            ((Button) view).setText("X");
            ((Button) view).setTextColor(Color.parseColor("#72A090"));
        } else {
            gameState[gameStatePointer] = 1;
            ((Button) view).setText("O");
            ((Button) view).setTextColor(Color.parseColor("#D48192"));
        }

        roundCount++;

        if (checkWinner()) {
            if (activePlayer) {
                playerOneWinsCount++;
                playerTwoLosesCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player One won", Toast.LENGTH_SHORT).show();
                playAgain();
            } else {
                playerTwoWinsCount++;
                playerOneLosesCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two won", Toast.LENGTH_SHORT).show();
                playAgain();
            }

            db.updateOne(new UserModel(0, playerOneName.getText().toString(), playerOneWinsCount, playerOneLosesCount));
            db.updateOne(new UserModel(0, playerTwoName.getText().toString(), playerTwoWinsCount, playerTwoLosesCount));
            Toast.makeText(this, "Database updated", Toast.LENGTH_SHORT).show();
        } else if (roundCount == 9) {
            playAgain();
            Toast.makeText(this, "It's a draw! ", Toast.LENGTH_SHORT).show();
        } else {
            activePlayer = !activePlayer;
        }


        if (playerOneWinsCount > playerTwoWinsCount) {
            playerStatus.setText(playerOneName.getText().toString()+" is winning!");

        } else if (playerOneWinsCount < playerTwoWinsCount)
        {
            playerStatus.setText(playerTwoName.getText().toString()+" is winning!");
        }else
        {
            playerStatus.setText("");
        }

        resetGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                playAgain();
                playerOneWinsCount = 0;
                playerTwoWinsCount = 0;
                playerOneLosesCount = 0;
                playerTwoLosesCount = 0;
                playerStatus.setText("");
                updatePlayerScore();
                db.updateOne(new UserModel(0, playerOneName.getText().toString(), playerOneWinsCount, playerOneLosesCount));
                db.updateOne(new UserModel(0, playerTwoName.getText().toString(), playerTwoWinsCount, playerTwoLosesCount));
            }

        });

    }

    public boolean checkWinner(){
        boolean winnerResult = false;

        for(int [] winningPosition: winningPositions){
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]]
                    && gameState[winningPosition[0]] != 2) {
                winnerResult = true;

                break;
            }
        }

        return winnerResult;
    }

    public void updatePlayerScore(){
        playerOneWins.setText(Integer.toString(playerOneWinsCount));
        playerTwoWins.setText(Integer.toString(playerTwoWinsCount));
        playerOneLoses.setText(Integer.toString(playerOneLosesCount));
        playerTwoLoses.setText(Integer.toString(playerTwoLosesCount));
    }

    public void playAgain(){
        roundCount = 0;
        activePlayer = true;

        for(int i = 0; i < buttons.length; i++){
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }

}