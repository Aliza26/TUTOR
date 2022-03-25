package tuting.example.tutor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_Up_Activity extends AppCompatActivity {

    EditText userName;
    EditText email;
    EditText password;
    Button signUp;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userName = (EditText) findViewById(R.id.UserName);
        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);
        signUp = (Button) findViewById(R.id.sign_upp);
        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);


    }
    public void signUpButtonClicked(View view){

        String UserName = userName.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();



        if(UserName.isEmpty()){
            userName.setError("Enter the user name");
            userName.requestFocus();
        }
        if(Email.isEmpty()){
            email.setError("Enter the email");
            email.requestFocus();
        }
        if(Password.isEmpty()){
            password.setError("Enter the password");
            password.requestFocus();
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful()) {

                            User user = new User(UserName, Email, Password);

                            FirebaseDatabase.getInstance().getReference("Users").child(
                                    FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Sign_Up_Activity.this,
                                                "User is successfully signed up", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(Sign_Up_Activity.this,
                                                "User failed to register successfully", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                    }

                                }
                            });

                        }

                        else{
                            Toast.makeText(Sign_Up_Activity.this,
                                    "User failed to register successfully",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }

}
