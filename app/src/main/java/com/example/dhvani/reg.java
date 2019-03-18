package com.example.dhvani;

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

public class reg extends AppCompatActivity {
    private EditText uname,upswd,umail;
    private Button regbtn;
    private TextView ulogin;
    private FirebaseAuth fbase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        fbase= FirebaseAuth.getInstance();
        setupUIVews();

        regbtn.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                if(validate()) {
                   // upload
                   String u_mail=umail.getText().toString().trim();
                   String u_pswd=upswd.getText().toString().trim();

                   fbase.createUserWithEmailAndPassword(u_mail,u_pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Verifymail();

                                Toast. makeText(reg.this, "registeration done", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(reg.this, "registeration fail", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            private void Verifymail() {
                FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(reg.this,"Check Your Mail for verification", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                            }
                        }
                    });
                }
            }
        });
        ulogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(reg.this,MainActivity.class));
            }
        });
    }
    private void  setupUIVews(){
        uname = (EditText)findViewById(R.id.n2);
        upswd = (EditText)findViewById(R.id.p2);
        umail = (EditText)findViewById(R.id.e1);
        ulogin = (TextView) findViewById(R.id.t3);
        regbtn = (Button)findViewById(R.id.b2);


    }
    private Boolean validate(){
        Boolean result= false;

        String name =uname.getText().toString();
        String pswd =upswd.getText().toString();
        String email =umail.getText().toString();

        if (name.isEmpty()|| pswd.isEmpty() || email.isEmpty()){
            Toast.makeText(this,"Enter all the details",Toast.LENGTH_SHORT).show();

        }else {
            result = true;
        }
        return result;
    }

}


