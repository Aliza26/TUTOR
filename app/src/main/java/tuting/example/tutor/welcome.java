package tuting.example.tutor;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class welcome extends AppCompatActivity {


    public EditText editTextEmail;
    public EditText editTextPass;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(welcome.this, NavigationDrawer.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail2) ;
        editTextPass = (EditText) findViewById(R.id.EditTextPass2);
        mAuth = FirebaseAuth.getInstance();




        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("coding");


        myRef.addValueEventListener(new ValueEventListener() {
            private static final String TAG = "TUTOR";

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    public void onClickSignInButton(View view){
        String email = editTextEmail.getText().toString().trim();
        String pass = editTextPass.getText().toString().trim();


        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextPass.setError("Please set a valid userName");
            editTextEmail.requestFocus();
        }

        if(pass.length()< 6){
            editTextPass.setError("Please enter the valid credentials");
            editTextPass.requestFocus();
        }

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(welcome.this,"User is sucessfully signed in",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(welcome.this,NavigationDrawer.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(welcome.this,"User is failed to sign in",Toast.LENGTH_LONG).show();
                }
            }
        });



    }
    public void onClickSignUpButton(View view){

        Intent intent = new Intent(welcome.this,Sign_Up_Activity.class);
        startActivity(intent);

    }
    public void onClickForgotpassword(View view){

        Intent intent = new Intent(welcome.this,ForgotPassword.class);
        startActivity(intent);

    }
}