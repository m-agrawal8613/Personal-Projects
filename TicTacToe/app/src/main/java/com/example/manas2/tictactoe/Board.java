package com.example.manas2.tictactoe;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;


public class Board extends ActionBarActivity {
    private int count = 0;
    private final int maxTurns = 9;
    private boolean gameOver = false;
    private String[][] board = new String[3][3];
    private HashMap<String, String> keyPlayer = new HashMap<String, String>();

    private TextView textView = null;
    String name1;
    String name2;

    private String checkWinner(int c, int r, String move) {
        if (c == 0 && r == 0 || c == 2 && r == 2) {
            if (board[0][0] == move && board[1][1] == move && board[2][2] == move)
                return move;
        }

        if (c == 0 && r == 2 || c == 2 && r == 0) {
            if (board[2][0] == move && board[1][1] == move && board[0][2] == move)
                return move;
        }

        if (c == 1 && r == 1) {
            if (board[2][0] == move && board[1][1] == move && board[0][2] == move)
                return move;

            if (board[0][0] == move && board[1][1] == move && board[2][2] == move)
                return move;
        }

        if (c == 0 || c == 1 || c == 2) {
            if (board[c][0] == move && board[c][1] == move && board[c][2] == move)
                return move;
            if (board[0][r] == move && board[1][r] == move && board[2][r] == move)
                return move;
        }

        return null;

    }

    private int getBoardRow(int id) {
        int k = id % 10;
        int r = 0;
        if (k == 4 || k == 5 || k == 6) {
            r = k - 4;
        }
        if (k == 7 || k == 8 || k == 9) {
            r = k - 7;
        }
        if (k == 0 || k == 1 || k == 2) {
            r = k;
        }
        return r;
    }

    private int getBoardColumn(int id) {
        int k = id % 10;
        int c = 0;
        if (k == 4 || k == 5 || k == 6) {
            c = 0;
        }
        if (k == 7 || k == 8 || k == 9) {
            c = 1;
        }
        if (k == 0 || k == 1 || k == 2) {
            c = 2;
        }
        return c;
    }

    private void modifyBoard(int c, int r, String move) {
        board[c][r] = move;
    }

    public void pickShape(View view) {
        if (gameOver) {
            return;
        }

        String move = "";
        Button b = (Button) view;
        if (!b.getText().toString().equals("")) {
            textView.setText("Please Choose a Different Square");
            return;
        }
        if (count % 2 == 0) {
            b.setTextSize(18);
            b.setText("X");
            move = "X";
            textView.setText(name2);
        }
        else {
            b.setTextSize(18);
            b.setText("O");
            move = "O";
            textView.setText(name1);
        }


        int id = b.getId();
        int r = getBoardRow(id);
        int c = getBoardColumn(id);

        modifyBoard(c, r, move);
        if (checkWinner(c, r, move) != null) {
            textView.setText(keyPlayer.get(move) + " just won!");
            gameOver = true;
            return;
        }

        count++;
        if (count == maxTurns) {
            textView.setText("It's a Cat's Game!");
            gameOver = true;
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        LinearLayout parent = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        parent.setLayoutParams(params);
        parent.setOrientation(LinearLayout.VERTICAL);

        TextView tv3 = new TextView(this);
        tv3.setTextSize(20);
        tv3.setText("");
        parent.addView(tv3);

        name1 = intent.getStringExtra("Player One");
        keyPlayer.put("X", name1);
        name1 = name1 + "'s Turn";
        name2 = intent.getStringExtra("Player Two");
        keyPlayer.put("O", name2);
        name2 = name2 + "'s Turn";
        textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(name1);
        textView.setGravity(Gravity.CENTER);
        parent.addView(textView);

        TextView tv2 = new TextView(this);
        tv2.setTextSize(20);
        tv2.setText("");
        parent.addView(tv2);

        GridLayout v = (GridLayout) getLayoutInflater().from(this).inflate(R.layout.activity_game, null);
        parent.addView(v);
        setContentView(parent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }
}
