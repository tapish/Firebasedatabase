package io.androidtutorials.firebasedatabase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity{

    private FirebaseAuth auth;
    private Button bChangeEmail, bChangePassword, bRemove, bSignout;
    private ProgressDialog mProgressDialog;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setIndeterminate(true);

        bChangeEmail = (Button) findViewById(R.id.bchange_email);
        bChangePassword = (Button) findViewById(R.id.bchange_password);
        bRemove = (Button) findViewById(R.id.bremove_user);
        bSignout = (Button) findViewById(R.id.bsign_out);


        bChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChangeEmailActivity.class);
                startActivity(intent);
            }
        });

        bChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        bSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        bRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.show();
                if (user != null) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Your profile is deleted now", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this, SignupActivity.class));
                                        finish();
                                        mProgressDialog.hide();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Unable to delete your account!", Toast.LENGTH_SHORT).show();
                                        mProgressDialog.hide();

                                    }
                                }
                            });
                }
            }
        });
    }
}

