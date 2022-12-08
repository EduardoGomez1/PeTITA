package ita.com.petITA;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import ita.com.petITA.R;

public class ForgotActivity extends Activity {
    EditText correoF;
    Button btn_forgot;
    String email="";
    FirebaseAuth AuthF;
    ProgressDialog Barrap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        correoF=(EditText) findViewById(R.id.etxt_email_changePass);
        btn_forgot=(Button) findViewById(R.id.btn_next_changePass);
        AuthF=FirebaseAuth.getInstance();
        Barrap=new ProgressDialog(this);

        btn_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=correoF.getText().toString();
                Barrap.setMessage("Enviando correo");
                Barrap.setCanceledOnTouchOutside(false);
                Barrap.show();
                cambiarPasword();
                startActivity(new Intent(ForgotActivity.this,LoginActivity.class));
            }

        });

    }
    private void cambiarPasword() {
        AuthF.setLanguageCode("es");
        AuthF.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgotActivity.this, "El correo de restablecimiento fue enviado", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ForgotActivity.this, "No se pudo enviar el correo de restablecimiento", Toast.LENGTH_SHORT).show();
                }
                Barrap.dismiss();
            }
        });
    }

    public void Back(View view){
        Intent back =  new Intent(this, LoginActivity.class);
        startActivity(back);
    }

    public void Next(View view){
        Intent next =  new Intent(this, PasswordForgotActivity.class);
        startActivity(next);
    }
}