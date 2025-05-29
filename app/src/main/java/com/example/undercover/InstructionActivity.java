package com.example.undercover;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class InstructionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        // Ambil data dari Intent (jumlah pemain dan undercover)
        Intent intent = getIntent();
        int totalPlayers = intent.getIntExtra("totalPlayers", 5); // Default 5 jika tidak ada data
        int undercoverCount = intent.getIntExtra("undercoverCount", 1); // Default 1 jika tidak ada data

        // Tombol BACK
        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kembali ke ConfigActivity
            }
        });

        // Tombol OK
        ImageButton okButton = findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke CardSelectionActivity dengan membawa data jumlah pemain dan undercover
                Intent cardSelectionIntent = new Intent(InstructionActivity.this, CardSelectionActivity.class);
                cardSelectionIntent.putExtra("totalPlayers", totalPlayers);
                cardSelectionIntent.putExtra("undercoverCount", undercoverCount);
                startActivity(cardSelectionIntent);
            }
        });
    }
}