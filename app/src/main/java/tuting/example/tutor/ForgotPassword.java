package tuting.example.tutor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    EditText editText;
    FirebaseAuth Auth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        editText = (EditText) findViewById(R.id.editTextTextEmailAddress);
        Auth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

    }

    public void onClickResetPassword(View v){
        resetPassword();

    }

    private void resetPassword() {

        String email = editText.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editText.setError("Enter the valid email");
            editText.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        Auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this,"Please check your email to reset" +
                            " your password",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(ForgotPassword.this," Reset email is failed to send ",
                            Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

    }
}