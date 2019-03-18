package com.example.dhvani;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Pass;
    private Button Login;
    private TextView ureg;
   private FirebaseAuth firebaseAuth;
    private ProgressDialog pdilog;
    private TextView Fpswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name =(EditText)findViewById(R.id.n1);
        Pass =(EditText)findViewById(R.id.p1);
        Login =(Button) findViewById(R.id.b1);
        ureg=(TextView) findViewById(R.id.t1);
       firebaseAuth=FirebaseAuth.getInstance();
        pdilog=new ProgressDialog(this);
        Fpswd =(TextView)findViewById(R.id.fpswd);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null){
            finish();
            startActivity(new Intent(MainActivity.this,home.class));
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valid(Name.getText().toString(), Pass.getText().toString());
            }
        });

       Fpswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity .this, reg.class));
            }
        });
        ureg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity .this, reg.class));
            }
        });



    }
    private void  valid(String userName, String pswd){
        pdilog.setMessage("Welcome  Validating...");
        pdilog.show();

        firebaseAuth.signInWithEmailAndPassword(userName,pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    pdilog.dismiss();
                    Toast.makeText(MainActivity.this,"Login successfull",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,home.class));
                }else {
                    pdilog.dismiss();
                    Toast.makeText(MainActivity.this,"Login failed",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
