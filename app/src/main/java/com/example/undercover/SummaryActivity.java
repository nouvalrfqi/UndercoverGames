package com.example.undercover;
import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {
    private String[] playerNames;
    private String[] secretWords;
    private String winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("SummaryActivity", "onCreate: SummaryActivity started");
        setContentView(R.layout.activity_summary);

        // Ambil data dari Intent
        Intent intent = getIntent();
        playerNames = intent.getStringArrayExtra("playerNames");
        secretWords = intent.getStringArrayExtra("secretWords");
        winner = intent.getStringExtra("winner");

        // Cek data agar tidak null
        if (playerNames == null || secretWords == null || winner == null) {
            // Data tidak lengkap, kembali ke MainActivity
            Intent backIntent = new Intent(SummaryActivity.this, MainActivity.class);
            startActivity(backIntent);
            finish();
            return;
        }

        // Set teks kemenangan
        TextView winnerText = findViewById(R.id.winner_text);
        winnerText.setText(winner.equals("Civilians") ? "The Civilians Win!" : "The Undercovers Win!");

        // Set kata rahasia
        String undercoverWord = "";
        String civilianWord = "";
        for (String word : secretWords) {
            if (word != null) {
                if (word.equals("Iron Man")) {
                    undercoverWord = word;
                } else {
                    civilianWord = word;
                }
            }
        }

        TextView undercoverWordText = findViewById(R.id.undercover_word);
        TextView civilianWordText = findViewById(R.id.civilian_word);
        undercoverWordText.setText(undercoverWord);
        civilianWordText.setText(civilianWord);

        // Set daftar pemain dan status
        setPlayerStatus(R.id.player1_name, R.id.player1_icon, 0);
        setPlayerStatus(R.id.player2_name, R.id.player2_icon, 1);
        setPlayerStatus(R.id.player3_name, R.id.player3_icon, 2);
        setPlayerStatus(R.id.player4_name, R.id.player4_icon, 3);
        setPlayerStatus(R.id.player5_name, R.id.player5_icon, 4);

        // Tombol OK
        ImageButton okButton = findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SummaryActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setPlayerStatus(int nameId, int iconId, int index) {
        TextView playerNameText = findViewById(nameId);
        ImageView playerIcon = findViewById(iconId);

        if (playerNames.length > index && secretWords.length > index &&
                playerNames[index] != null && secretWords[index] != null) {

            playerNameText.setText(playerNames[index]);

            if (secretWords[index].equals("Iron Man")) {
                playerIcon.setImageResource(R.drawable.skull); // undercover
            } else {
                playerIcon.setImageResource(R.drawable.pumpkin); // civilian
            }
        } else {
            playerNameText.setVisibility(View.GONE);
            playerIcon.setVisibility(View.GONE);
        }
    }
}
