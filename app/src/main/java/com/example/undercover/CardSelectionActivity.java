package com.example.undercover;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CardSelectionActivity extends AppCompatActivity {
    private int currentPlayer = 1;
    private boolean[] cardSelected = new boolean[5]; // Array untuk melacak kartu yang sudah dipilih
    private String[] playerNames = new String[5]; // Array untuk menyimpan nama pemain
    private String[] secretWords = new String[5]; // Array untuk menyimpan kata rahasia
    private int undercoverIndex = (int) (Math.random() * 5); // Acak posisi Undercover

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_selection);

        // Ambil data dari Intent
        Intent intent = getIntent();
        int totalPlayers = intent.getIntExtra("totalPlayers", 5);
        int undercoverCount = intent.getIntExtra("undercoverCount", 1);

        // Inisialisasi kartu
        initCards();

        // Tombol BACK
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kembali ke InstructionActivity
            }
        });

        // Tombol SETTINGS (Placeholder)
        ImageButton settingsButton = findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logika untuk pengaturan di masa depan
            }
        });
    }

    private void initCards() {
        ImageButton card1 = findViewById(R.id.card1);
        ImageButton card2 = findViewById(R.id.card2);
        ImageButton card3 = findViewById(R.id.card3);
        ImageButton card4 = findViewById(R.id.card4);
        ImageButton card5 = findViewById(R.id.card5);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cardSelected[0]) showNameDialog(0);
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cardSelected[1]) showNameDialog(1);
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cardSelected[2]) showNameDialog(2);
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cardSelected[3]) showNameDialog(3);
            }
        });
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cardSelected[4]) showNameDialog(4);
            }
        });
    }

    private void showNameDialog(final int cardIndex) {
        cardSelected[cardIndex] = true;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_name, null);
        final EditText nameInput = view.findViewById(R.id.name_input);
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        view.findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                if (!name.isEmpty()) {
                    playerNames[cardIndex] = name;
                    showSecretWordDialog(cardIndex);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    private void showSecretWordDialog(final int cardIndex) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_secret_word, null);
        TextView secretWordText = view.findViewById(R.id.secret_word_text);
        builder.setView(view);

        String secretWord = (cardIndex == undercoverIndex) ? "Iron Man" : "Spider-Man";
        secretWords[cardIndex] = secretWord;
        secretWordText.setText(secretWord);

        final AlertDialog dialog = builder.create();
        view.findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (currentPlayer < 5) {
                    currentPlayer++;
                    updatePlayerHeader();
                    updateCards();
                } else {
                    // Beralih ke DescribeWordActivity untuk setiap pemain
                    Intent intent = new Intent(CardSelectionActivity.this, DescribeWordActivity.class);
                    intent.putExtra("playerNames", playerNames);
                    intent.putExtra("secretWords", secretWords);
                    startActivity(intent);
                    finish();
                }
            }
        });
        dialog.show();
    }

    private void updatePlayerHeader() {
        TextView playerHeader = findViewById(R.id.player_header);
        playerHeader.setText("PLAYER " + currentPlayer + "\nPlease pick a card");
    }

    private void updateCards() {
        ImageButton card1 = findViewById(R.id.card1);
        ImageButton card2 = findViewById(R.id.card2);
        ImageButton card3 = findViewById(R.id.card3);
        ImageButton card4 = findViewById(R.id.card4);
        ImageButton card5 = findViewById(R.id.card5);

        card1.setVisibility(cardSelected[0] ? View.GONE : View.VISIBLE);
        card2.setVisibility(cardSelected[1] ? View.GONE : View.VISIBLE);
        card3.setVisibility(cardSelected[2] ? View.GONE : View.VISIBLE);
        card4.setVisibility(cardSelected[3] ? View.GONE : View.VISIBLE);
        card5.setVisibility(cardSelected[4] ? View.GONE : View.VISIBLE);
    }
}