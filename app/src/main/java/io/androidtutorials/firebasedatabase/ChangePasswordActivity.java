package io.androidtutorials.firebasedatabase;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText inputPassword;
    private Button bPasswordChange;
    private FirebaseAuth auth;
    private ProgressDialog mProgressDialog;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        auth = FirebaseAuth.getInstance();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setIndeterminate(true);

        inputPassword = (EditText) findViewById(R.id.tpassword_new);
        bPasswordChange = (Button) findViewById(R.id.bchange_password);

        bPasswordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }
                mProgressDialog.show();
                if (user != null && !inputPassword.getText().toString().trim().equals("")) {
                    if (inputPassword.getText().toString().trim().length() < 5) {
                        inputPassword.setError("Invalid Password, enter minimum 6 characters");
                        inputPassword.setVisibility(View.GONE);
                    } else {
                        user.updatePassword(inputPassword.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ChangePasswordActivity.this, "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                                            auth.signOut();
                                            mProgressDialog.hide();
                                        } else {
                                            Toast.makeText(ChangePasswordActivity.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                                            mProgressDialog.hide();
                                        }
                                    }
                                });
                    }
                } else if (inputPassword.getText().toString().trim().equals("")) {
                    inputPassword.setError("Enter password");
                    mProgressDialog.hide();

                }
            }
        });
    }

}