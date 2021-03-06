package ph.edu.apc.mytenant.activity;

import  android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ph.edu.apc.mytenant.R;

/**
 * Created by Aira Joyce on 12/1/2016.
 */
public class Signin_Activity extends AppCompatActivity implements View.OnClickListener{

    private EditText etSEmail, etSPassword;
    private Button bSignin;
    private TextView tvSignup;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), Content_Activity.class));
        }
        etSEmail=(EditText)findViewById(R.id.etSEmail);
        etSPassword=(EditText)findViewById(R.id.etSPassword);
        bSignin =(Button)findViewById(R.id.bSignin);
        tvSignup = (TextView)findViewById(R.id.tvSignup);

        progressDialog = new ProgressDialog(this);
        bSignin.setOnClickListener(this);
        tvSignup.setOnClickListener(this);

    }
    private void SigninUser(){
        String email = etSEmail.getText().toString().trim();
        String password  = etSPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Signing in your account...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), Content_Activity.class));
                        }else{
                            Toast.makeText(Signin_Activity.this, "Registration Error", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
    @Override
    public void onClick(View view) {
        if(view == bSignin){
            SigninUser();
        }
        if(view == tvSignup){
            finish();
            startActivity(new Intent(this, Signup_Activity.class));
        }
    }
}




