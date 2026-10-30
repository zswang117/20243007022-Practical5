package com.example.practicalfive;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Municipality extends AppCompatActivity {

    private TextView nameText;
    private TextView infoText;
    private ImageView imageView;
    private Button visitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipality);

        nameText = findViewById(R.id.textName);
        infoText = findViewById(R.id.textInfo);
        imageView = findViewById(R.id.imageMunicipality);
        visitButton = findViewById(R.id.buttonVisitSite);

        // Re-validate the name coming from MainActivity so this Activity is safe
        // even if the Intent is launched from somewhere else.
        String raw = getIntent().getStringExtra(MainActivity.EXTRA_MUNICIPALITY);
        final String municipality = TextProcessor.processMunicipality(raw);
        if (municipality == null) {
            Toast.makeText(this, R.string.error_invalid, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Pick the matching resources for this municipality.
        final int summaryIndex;
        final int colorRes;
        final int imageRes;
        final String websiteUrl;

        switch (municipality) {
            case "Beijing":
                summaryIndex = 0;
                colorRes = R.color.municipality_beijing;
                imageRes = R.drawable.beijing;
                websiteUrl = "https://english.beijing.gov.cn/";
                break;
            case "Shanghai":
                summaryIndex = 1;
                colorRes = R.color.municipality_shanghai;
                imageRes = R.drawable.shanghai;
                websiteUrl = "https://english.shanghai.gov.cn/";
                break;
            case "Chongqing":
                summaryIndex = 2;
                colorRes = R.color.municipality_chongqing;
                imageRes = R.drawable.chongqing;
                websiteUrl = "https://english.cq.gov.cn/";
                break;
            case "Tianjin":
            default:
                summaryIndex = 3;
                colorRes = R.color.municipality_tianjin;
                imageRes = R.drawable.tianjin;
                websiteUrl = "https://en.tj.gov.cn/";
                break;
        }

        // Populate text content.
        nameText.setText(municipality);
        String[] summaries = getResources().getStringArray(R.array.municipalities_array);
        infoText.setText(summaries[summaryIndex]);

        // Apply the municipality colour scheme.
        int accent = ContextCompat.getColor(this, colorRes);
        int lightText = ContextCompat.getColor(this, R.color.color_text_light);

        nameText.setBackgroundColor(accent);
        nameText.setTextColor(lightText);
        infoText.setTextColor(accent);
        imageView.setImageResource(imageRes);

        // Material Button colours itself via background tint.
        visitButton.setBackgroundTintList(ColorStateList.valueOf(accent));
        visitButton.setTextColor(lightText);

        // Match the status bar to the coloured header.
        getWindow().setStatusBarColor(accent);

        // Implicit Intent: open the official website in a browser.
        visitButton.setOnClickListener(v -> openWebsite(websiteUrl));
    }

    private void openWebsite(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        try {
            startActivity(browserIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, R.string.error_no_browser, Toast.LENGTH_SHORT).show();
        }
    }
}
