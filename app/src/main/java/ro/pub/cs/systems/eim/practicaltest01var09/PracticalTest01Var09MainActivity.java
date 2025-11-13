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
    String saved_sum;

    private MeanBroadcastReceiver meanBroadcastReceiver = new MeanBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();

    public class MeanBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle data = intent.getExtras();
            Log.d("RANDOM_TAG", data.getString("broadcast"));
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        saved_sum = savedInstanceState.getString("sum");
        Toast.makeText(this, "Returned with OK.\nThe sum is: " + savedInstanceState.getString("sum"), Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("sum", saved_sum);
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
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
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
                        saved_sum = (extras.getString("sum"));
                        Toast.makeText(this, "Returned with OK.\nThe sum is: " + extras.getString("sum"), Toast.LENGTH_LONG).show();
                        if (Integer.parseInt(saved_sum) > 10) {
                            Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                            intent.putExtra("broadcast", saved_sum);
                            startService(intent);
                        }
                    }
                }
        );
        compute_button.setOnClickListener(switchActivityButtonListener);
        intentFilter.addAction("my_intent");
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(meanBroadcastReceiver, intentFilter);
    }

}



