package local.martic20.img;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class verifyEmailActivity extends BaseActivity {

    private static final String TAG = "verifyEmail";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mNameField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);
        mNameField = (EditText) findViewById(R.id.field_name);

        ImageView logo = (ImageView) findViewById(R.id.logoImage);
        int orientation=this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            logo.setVisibility(View.GONE);
        } else  {
            logo.setVisibility(View.VISIBLE);
        }


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void createAccount(View view) {
        if (!validateForm()) {
            return;
        }
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        final String name = mNameField.getText().toString();
        showProgressDialog();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (task.isSuccessful()) {
                            user.sendEmailVerification();
                            DatabaseReference firebase = FirebaseDatabase.getInstance().getReference("users/" +user.getUid()+"/name");
                            firebase.child("name").setValue(name);
                            Toast.makeText(verifyEmailActivity.this, "We have send a verification email",
                                    Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                            finish();
                        } else {
                            if (user == null) {
                                Toast.makeText(verifyEmailActivity.this, "Registering failed. Try out",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(verifyEmailActivity.this, "You are already registered",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        hideProgressDialog();
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;
        String name = mNameField.getText().toString();
        if (TextUtils.isEmpty(name)) {
            mNameField.setError("Required.");
            valid = false;
        } else {
            mNameField.setError(null);
        }
        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }
        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }
        return valid;
    }


}
