package com.example.undercover;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EliminationTimeActivity extends AppCompatActivity {
    private String[] playerNames = new String[5];
    private String[] secretWords = new String[5];
    private boolean[] isEliminated = new boolean[5];
    private int undercoverCount;

    // Deklarasi UI elemen global
    private TextView[] nameLabels = new TextView[5];  // Untuk nama pemain
    private ImageButton[] cards = new ImageButton[5];
    private TextView[] eliminateLabels = new TextView[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elimination_time);

        // Ambil data dari Intent
        Intent intent = getIntent();
        playerNames = intent.getStringArrayExtra("playerNames");
        secretWords = intent.getStringArrayExtra("secretWords");
        isEliminated = intent.getBooleanArrayExtra("isEliminated");
        undercoverCount = intent.getIntExtra("undercoverCount", 1);

        nameLabels[0] = findViewById(R.id.name1);
        nameLabels[1] = findViewById(R.id.name2);
        nameLabels[2] = findViewById(R.id.name3);
        nameLabels[3] = findViewById(R.id.name4);
        nameLabels[4] = findViewById(R.id.name5);

        // Inisialisasi elemen UI
        cards[0] = findViewById(R.id.card1);
        cards[1] = findViewById(R.id.card2);
        cards[2] = findViewById(R.id.card3);
        cards[3] = findViewById(R.id.card4);
        cards[4] = findViewById(R.id.card5);

        eliminateLabels[0] = findViewById(R.id.eliminate_label_card1);
        eliminateLabels[1] = findViewById(R.id.eliminate_label_card2);
        eliminateLabels[2] = findViewById(R.id.eliminate_label_card3);
        eliminateLabels[3] = findViewById(R.id.eliminate_label_card4);
        eliminateLabels[4] = findViewById(R.id.eliminate_label_card5);

        initCards();

        // Tombol Back
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            Intent backIntent = new Intent(EliminationTimeActivity.this, DescriptionTimeActivity.class);
            backIntent.putExtra("playerNames", playerNames);
            backIntent.putExtra("secretWords", secretWords);
            backIntent.putExtra("isEliminated", isEliminated);
            backIntent.putExtra("undercoverCount", undercoverCount);
            startActivity(backIntent);
            finish();
        });
    }

    private void initCards() {
        for (int i = 0; i < 5; i++) {
            if (playerNames[i] != null && !isEliminated[i]) {
                int finalI = i;
                cards[i].setContentDescription(playerNames[i]);
                cards[i].setVisibility(View.VISIBLE);
                eliminateLabels[i].setVisibility(View.VISIBLE);
                nameLabels[i].setVisibility(View.VISIBLE);
                nameLabels[i].setText(playerNames[i]); // <== Set nama pemain
                cards[i].setOnClickListener(v -> showEliminationDialog(finalI));
            } else {
                cards[i].setVisibility(View.GONE);
                eliminateLabels[i].setVisibility(View.GONE);
                nameLabels[i].setVisibility(View.GONE); // Sembunyikan juga nama
            }
        }
    }


    private void showEliminationDialog(final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_eliminate, null);
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        TextView messageText = view.findViewById(R.id.eliminate_message);
        messageText.setText("Eliminate " + playerNames[index] + "?");

        view.findViewById(R.id.yes_button).setOnClickListener(v -> {
            isEliminated[index] = true;

            // Hilangkan kartu dan label "ELIMINATE"
            cards[index].setVisibility(View.GONE);
            eliminateLabels[index].setVisibility(View.GONE);

            cards[index].setVisibility(View.GONE);
            eliminateLabels[index].setVisibility(View.GONE);
            nameLabels[index].setVisibility(View.GONE); // Sembunyikan nama pemain juga

            showEliminationResult(index);
            dialog.dismiss();
        });

        view.findViewById(R.id.cancel_button).setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void showEliminationResult(int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_result, null);
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        TextView resultText = view.findViewById(R.id.result_text);
        String status = secretWords[index].equals("Iron Man") ? "undercover" : "civilian";
        resultText.setText(status.equals("undercover") ? "An undercover has been eliminated!" : "A civilian has been eliminated!");
        TextView nameText = view.findViewById(R.id.result_name);
        nameText.setText(playerNames[index]);

        view.findViewById(R.id.ok_button).setOnClickListener(v -> {
            if (status.equals("undercover")) {
                showWinDialog("Civilians win!");
            } else {
                undercoverCount--;
                if (checkWinCondition()) {
                    showWinDialog("Undercovers win!");
                } else {
                    Intent intent = new Intent(EliminationTimeActivity.this, DescriptionTimeActivity.class);
                    intent.putExtra("playerNames", playerNames);
                    intent.putExtra("secretWords", secretWords);
                    intent.putExtra("isEliminated", isEliminated);
                    intent.putExtra("undercoverCount", undercoverCount);
                    startActivity(intent);
                    finish();
                }
            }
            dialog.dismiss();
        });

        dialog.show();
    }

    private boolean checkWinCondition() {
        int remainingPlayers = 0;
        int remainingUndercovers = 0;
        for (int i = 0; i < 5; i++) {
            if (!isEliminated[i]) {
                remainingPlayers++;
                if (secretWords[i].equals("Iron Man")) remainingUndercovers++;
            }
        }
        return remainingPlayers == 2 && remainingUndercovers == 1;
    }

    private void showWinDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", (dialog, id) -> {
                    Intent intent = new Intent(EliminationTimeActivity.this, SummaryActivity.class);
                    intent.putExtra("playerNames", playerNames);
                    intent.putExtra("secretWords", secretWords);
                    intent.putExtra("winner", message.contains("Civilians") ? "Civilians" : "Undercovers");
                    startActivity(intent);
                    finish();
                });
        builder.show();
    }

}