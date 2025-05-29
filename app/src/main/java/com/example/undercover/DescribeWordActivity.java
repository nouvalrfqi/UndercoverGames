package com.example.undercover;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class DescribeWordActivity extends AppCompatActivity {

    private String[] playerNames;
    private String[] secretWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_describe_word);

        Log.d("Debug", "Current Activity: " + getClass().getSimpleName());

        // Ambil data dari Intent
        Intent receivedIntent = getIntent();
        playerNames = receivedIntent.getStringArrayExtra("playerNames");
        secretWords = receivedIntent.getStringArrayExtra("secretWords");

        // Tombol BACK
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        // Tombol OK
        ImageButton okbutton = findViewById(R.id.ok_button);
        okbutton.setOnClickListener(v -> {
            Log.d("Debug", "Navigating to DescriptionTimeActivity");

            Intent intentToNext = new Intent(DescribeWordActivity.this, DescriptionTimeActivity.class);

            // Cek null untuk menghindari crash
            if (playerNames != null) intentToNext.putExtra("playerNames", playerNames);
            if (secretWords != null) intentToNext.putExtra("secretWords", secretWords);

            intentToNext.putExtra("undercoverCount", 1);
            intentToNext.putExtra("isEliminated", new boolean[5]); // default semua false

            startActivity(intentToNext);
            finish();
        });
    }
}
