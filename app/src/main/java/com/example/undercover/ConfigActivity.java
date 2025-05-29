package com.example.undercover;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class ConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        // Tombol START
        ImageButton startButton = findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Jumlah pemain dan undercover ditetapkan
                int totalPlayers = 5;
                int undercoverCount = 1;

                Intent intent = new Intent(ConfigActivity.this, InstructionActivity.class);
                intent.putExtra("totalPlayers", totalPlayers);
                intent.putExtra("undercoverCount", undercoverCount);
                startActivity(intent);
            }
        });

        // Tombol BACK
        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kembali ke MainActivity
            }
        });

        // Tombol SETTINGS (Placeholder)
        ImageButton settingsButton = findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logika untuk halaman settings di masa depan
            }
        });
    }
}