package tuting.example.tutor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Objects;

public class Animation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView textView = (TextView) findViewById(R.id.textView1);
        textView.animate().translationX(1000).setDuration(1000).setStartDelay(2500);

        Thread thread = new Thread() {
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(Animation.this, welcome.class);
                    startActivity(intent);
                    finish();
                }
            }


        };
       thread.start();
    }
}