package com.tehtava.tictactoe;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private boolean playerTurn = true;
    private MediaPlayer gamesong;
    private int roundCount;
    private Button playerColor, computerColor;

    private int playerPoints;
    private int computerPoints;

    private TextView textViewP1;
    private TextView textViewP2;
    private TextView gameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewP1 = findViewById(R.id.textP1);
        textViewP2 = findViewById(R.id.textP2);
        gameText = findViewById(R.id.gameTex);

        gameText.setText("This game have a twist. \n" +
                "Click a button to mark your move and \n" +
                "any button to mark a computer's move. \n" +
                "Computer's moves are random and \n" +
                "sometimes your's too! \n" +
                " \n"+
                "Enjoy!");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button musicBtn = (ToggleButton)findViewById(R.id.musicBtn);
        ((ToggleButton) musicBtn).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked){
             if (isChecked){
                 play();
             }else{
                 stop();
             }
            }
        });

        playerColor = (Button)findViewById(R.id.p1Btn);
        playerColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu pm = new PopupMenu(MainActivity.this, playerColor);
                pm.getMenuInflater().inflate(R.menu.popup_menu, pm.getMenu());
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.black:
                                playerColor.setTextColor(Color.BLACK);
                                Toast.makeText(MainActivity.this, "Black", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.blue:
                                playerColor.setTextColor(Color.BLUE);
                                Toast.makeText(MainActivity.this, "Blue", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.green:
                                playerColor.setTextColor(Color.GREEN);
                                Toast.makeText(MainActivity.this, "Green", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.red:
                                playerColor.setTextColor(Color.RED);
                                Toast.makeText(MainActivity.this, "Red", Toast.LENGTH_SHORT).show();
                                return true;
                        }
                        return true;
                    }
                });
                pm.show();
            }
        });

        computerColor = (Button)findViewById(R.id.p2Btn);
        computerColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu pm = new PopupMenu(MainActivity.this, computerColor);
                pm.getMenuInflater().inflate(R.menu.popup_menu, pm.getMenu());
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.black:
                                computerColor.setTextColor(Color.BLACK);
                                Toast.makeText(MainActivity.this, "Black", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.blue:
                                computerColor.setTextColor(Color.BLUE);
                                Toast.makeText(MainActivity.this, "Blue", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.green:
                                computerColor.setTextColor(Color.GREEN);
                                Toast.makeText(MainActivity.this, "Green", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.red:
                                computerColor.setTextColor(Color.RED);
                                Toast.makeText(MainActivity.this, "Red", Toast.LENGTH_SHORT).show();
                                return true;

                        }
                        return true;
                    }
                });
                pm.show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_reset_game) {
            resetGame();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(final View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (playerTurn) {
            xButton((Button) v);
        } else {
            oButton((Button) v);
        }

        roundCount++;

        if (checkForWin()) {
            if (playerTurn) {
                player1Won();
            } else {
                player2Won();
            }
        } else if (roundCount == 9) {
            draw();

        } else {
            playerTurn = !playerTurn;
        }
    }
    private void xButton(Button v) {
        Random randa = new Random();
        int z = randa.nextInt(2);
        if (z == 1){
            v.setTextColor(playerColor.getCurrentTextColor());
            v.setText("X");
        }else{
        xButtonRan((Button) v);


        }
    }
    private void xButtonRan(Button v){
        Random rando = new Random();
        int x = rando.nextInt(3);
        int y = rando.nextInt(3);

        if (buttons [x][y].getText().toString().equals("")){
            v = buttons[x][y];
            v.setTextColor(playerColor.getCurrentTextColor());
            v.setText("X");
        }else{
            xButtonRan((Button) v);
        }
    }
    private void oButton(Button v){
        Random rando = new Random();
        int x = rando.nextInt(3);
        int y = rando.nextInt(3);

        if (buttons [x][y].getText().toString().equals("")){
            v = buttons[x][y];
            v.setTextColor(computerColor.getCurrentTextColor());
            v.setText("O");
        }else{
            oButton((Button) v);
        }
    }
    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {

            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }
    private void player1Won() {
        playerPoints++;
        Toast.makeText(this, "Player won!", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }
    private void player2Won() {
        computerPoints++;
        Toast.makeText(this, "Computer won!", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }
    private void draw() {
        Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show();
        resetBoard();
    }
    private void updatePoints(){
        textViewP1.setText("Player: " + playerPoints);
        textViewP2.setText("Computer: " + computerPoints);

    }
    private void resetBoard(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        playerTurn = true;
    }
    private void resetGame(){
        playerPoints = 0;
        computerPoints = 0;
        updatePoints();
        resetBoard();
        Toast.makeText(this, "Game Reseted", Toast.LENGTH_SHORT).show();
    }
    public void play() {
        gamesong = MediaPlayer.create(this, R.raw.gametune);
        gamesong.start();
        gamesong.setLooping(true);
    }
    public void stop() {
        gamesong.release();
    }
}
