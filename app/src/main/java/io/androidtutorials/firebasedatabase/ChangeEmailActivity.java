package io.androidtutorials.firebasedatabase;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeEmailActivity extends AppCompatActivity {

    private EditText inputEmail;
    private Button btnchangeEmail;
    private FirebaseAuth auth;
    private ProgressDialog mProgressDialog;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        auth = FirebaseAuth.getInstance();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setIndeterminate(true);

        inputEmail = (EditText) findViewById(R.id.temail_new);
        btnchangeEmail = (Button) findViewById(R.id.breset_email);


        btnchangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mProgressDialog.show();
                if (user != null && !inputEmail.getText().toString().trim().equals("")) {
                    user.updateEmail(inputEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ChangeEmailActivity.this, "Email address is updated. Please sign in with new email id!", Toast.LENGTH_LONG).show();
                                        auth.signOut();
                                        mProgressDialog.hide();

                                    } else {
                                        Toast.makeText(ChangeEmailActivity.this, "Failed to update email!", Toast.LENGTH_LONG).show();
                                        mProgressDialog.hide();
                                    }
                                }
                            });
                } else if (inputEmail.getText().toString().trim().equals("")) {
                    inputEmail.setError("Enter valid email");
                    mProgressDialog.hide();

                }
            }
        });

    }

}