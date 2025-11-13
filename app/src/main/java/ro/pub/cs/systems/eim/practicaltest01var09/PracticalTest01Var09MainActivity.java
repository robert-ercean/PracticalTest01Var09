package ro.pub.cs.systems.eim.practicaltest01var09;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;

public class PracticalTest01Var09MainActivity extends AppCompatActivity {
    private TextView all_terms;
    private EditText next_term;
    private Button add_button;
    private Button compute_button;
    private ButtonListener buttonListener;
    int saved_sum = 0;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        saved_sum = savedInstanceState.getInt("sum");
        Toast.makeText(this, "Returned with OK.\nThe sum is: " + savedInstanceState.getString("sum"), Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("sum", saved_sum);
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.add) {
                if (next_term.getText().toString().isEmpty()) {
                    return;
                }
                int term = Integer.parseInt(next_term.getText().toString());
                next_term.getText().clear();

                String old_str = all_terms.getText().toString();
                if (old_str.isEmpty()) {
                    all_terms.setText(String.valueOf(term));
                } else {
                    all_terms.setText(old_str + " + " + term);
                }
            }
        }
    }
    private ActivityResultLauncher<Intent> secondActivityLauncher;
    private SwitchActivityButtonListener switchActivityButtonListener = new SwitchActivityButtonListener();
    private class SwitchActivityButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.compute) {
                Intent intent = new Intent(PracticalTest01Var09MainActivity.this, PracticalTest01Var09SecondaryActivity.class);
                String terms = all_terms.getText().toString();
                intent.putExtra("sum", terms);
                secondActivityLauncher.launch(intent);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_practical_test01_var09);

        buttonListener = new ButtonListener();

        add_button = findViewById(R.id.add);
        compute_button = findViewById(R.id.compute);
        all_terms = findViewById(R.id.all_terms);
        next_term = findViewById(R.id.next_term);

        add_button.setOnClickListener(buttonListener);

        secondActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bundle extras = data.getExtras();
                        saved_sum = Integer.valueOf(extras.getString("sum"));
                        Toast.makeText(this, "Returned with OK.\nThe sum is: " + extras.getString("sum"), Toast.LENGTH_LONG).show();
                    }
                }
        );
        compute_button.setOnClickListener(switchActivityButtonListener);
    }
}






