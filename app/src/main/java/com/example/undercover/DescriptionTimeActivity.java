package com.example.undercover;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class DescriptionTimeActivity extends AppCompatActivity {
    private String[] playerNames = new String[5];
    private String[] secretWords = new String[5];
    private boolean[] isEliminated = new boolean[5];
    private int undercoverCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_time);

        // Ambil data dari Intent
        Intent intent = getIntent();
        playerNames = intent.getStringArrayExtra("playerNames");
        secretWords = intent.getStringArrayExtra("secretWords");
        undercoverCount = intent.getIntExtra("undercoverCount", 1);

        // Tambahan untuk menghindari null pointer
        boolean[] receivedEliminated = intent.getBooleanArrayExtra("isEliminated");
        if (receivedEliminated != null && receivedEliminated.length == 5) {
            isEliminated = receivedEliminated;
        } else {
            isEliminated = new boolean[5]; // default semua false
        }

        // Inisialisasi kartu
        initCards();

        // Tombol Back (ImageButton)
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Tombol Go To Vote (ImageButton)
        ImageButton goToVoteButton = findViewById(R.id.gotovote);
        goToVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DescriptionTimeActivity.this, EliminationTimeActivity.class);
                intent.putExtra("playerNames", playerNames);
                intent.putExtra("secretWords", secretWords);
                intent.putExtra("isEliminated", isEliminated);
                intent.putExtra("undercoverCount", undercoverCount);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initCards() {
        ImageButton card1 = findViewById(R.id.card1);
        ImageButton card2 = findViewById(R.id.card2);
        ImageButton card3 = findViewById(R.id.card3);
        ImageButton card4 = findViewById(R.id.card4);
        ImageButton card5 = findViewById(R.id.card5);

        TextView name1 = findViewById(R.id.name1);
        TextView name2 = findViewById(R.id.name2);
        TextView name3 = findViewById(R.id.name3);
        TextView name4 = findViewById(R.id.name4);
        TextView name5 = findViewById(R.id.name5);

        if (playerNames[0] != null) name1.setText(playerNames[0]);
        if (playerNames[1] != null) name2.setText(playerNames[1]);
        if (playerNames[2] != null) name3.setText(playerNames[2]);
        if (playerNames[3] != null) name4.setText(playerNames[3]);
        if (playerNames[4] != null) name5.setText(playerNames[4]);

        // Sembunyikan kartu dan nama jika pemain sudah dieliminasi
        card1.setVisibility(isEliminated[0] ? View.INVISIBLE : View.VISIBLE);
        name1.setVisibility(isEliminated[0] ? View.INVISIBLE : View.VISIBLE);

        card2.setVisibility(isEliminated[1] ? View.INVISIBLE : View.VISIBLE);
        name2.setVisibility(isEliminated[1] ? View.INVISIBLE : View.VISIBLE);

        card3.setVisibility(isEliminated[2] ? View.INVISIBLE : View.VISIBLE);
        name3.setVisibility(isEliminated[2] ? View.INVISIBLE : View.VISIBLE);

        card4.setVisibility(isEliminated[3] ? View.INVISIBLE : View.VISIBLE);
        name4.setVisibility(isEliminated[3] ? View.INVISIBLE : View.VISIBLE);

        card5.setVisibility(isEliminated[4] ? View.INVISIBLE : View.VISIBLE);
        name5.setVisibility(isEliminated[4] ? View.INVISIBLE : View.VISIBLE);
    }

}
