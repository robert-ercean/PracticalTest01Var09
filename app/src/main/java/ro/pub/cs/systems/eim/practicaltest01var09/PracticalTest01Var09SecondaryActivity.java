package ro.pub.cs.systems.eim.practicaltest01var09;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PracticalTest01Var09SecondaryActivity extends AppCompatActivity {
    private class SecondaryButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent_to_main = new Intent();
            if (view.getId() == R.id.return_button) {
                intent_to_main.putExtra("sum", String.valueOf(total_sum));
                setResult(RESULT_OK, intent_to_main);
                finish();
            }
        }
    }
    private Button return_button;
    int total_sum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_practical_test01_var09_secondary);


        Intent intentFromMain = getIntent();
        Bundle data = intentFromMain.getExtras();

        return_button = findViewById(R.id.return_button);


        int sum = 0;
        String terms = data.getString("sum");
        String[] parts = terms.split(" \\+ ");
        for (String p : parts) {
            int n = Integer.parseInt(p);
            sum += n;
        }
        total_sum = sum;

        return_button.setOnClickListener(new SecondaryButtonListener());
    }
}