package com.example.practicalfive;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    /** Intent extra key shared with {@link Municipality}. */
    public static final String EXTRA_MUNICIPALITY = "municipality";

    private EditText editTextMunicipality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMunicipality = findViewById(R.id.editTextMunicipality);

        // Fire when the user presses "Go" / "Done" on the soft keyboard.
        editTextMunicipality.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                processInput();
                return true;
            }
            return false;
        });
    }

    private void processInput() {
        String raw = editTextMunicipality.getText().toString();
        String result = TextProcessor.processMunicipality(raw);

        if (result == null) {
            // Distinguish "no input at all" from "unknown municipality".
            if (raw == null || raw.trim().isEmpty()) {
                editTextMunicipality.setError(getString(R.string.error_empty));
            } else {
                editTextMunicipality.setError(getString(R.string.error_invalid));
            }
            editTextMunicipality.requestFocus();
            return;
        }

        // Valid: hand the normalised name to the second Activity.
        Intent intent = new Intent(this, Municipality.class);
        intent.putExtra(EXTRA_MUNICIPALITY, result);
        startActivity(intent);
    }
}
