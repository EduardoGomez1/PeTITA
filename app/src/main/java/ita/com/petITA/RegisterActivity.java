package ita.com.petITA;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import ita.com.petITA.R;
public class RegisterActivity extends Activity {
    EditText correoR,passwordR,nombreR,telefonoR;
    Button btn_registro;
    FirebaseAuth AuthU;
    DatabaseReference databasereference;
    AwesomeValidation awesomeValidation;

    String correoRe="";
    String passwordRe="";
    String nombreRe="";
    String telefonoRe="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC.BASIC);
        awesomeValidation.addValidation(this,R.id.etxt_email_register, Patterns.EMAIL_ADDRESS,R.string.invalid_mail);
        awesomeValidation.addValidation(this,R.id.etxt_password_register,".{6,}",R.string.invalid_password);
        AuthU=FirebaseAuth.getInstance();
        databasereference= FirebaseDatabase.getInstance().getReference();
        correoR=(EditText)findViewById(R.id.etxt_email_register);
        passwordR=(EditText)findViewById(R.id.etxt_password_register);
        nombreR=(EditText)findViewById(R.id.etxt_name_register);
        telefonoR=(EditText)findViewById(R.id.etxt_phone_register);
        btn_registro=findViewById(R.id.btn_signup_register);


        btn_registro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                correoRe=correoR.getText().toString();
                passwordRe=passwordR.getText().toString();
                nombreRe=nombreR.getText().toString();
                telefonoRe=telefonoR.getText().toString();
                registrar();
            }


        });
    }
    private void registrar() {
        if (awesomeValidation.validate()){
            AuthU.createUserWithEmailAndPassword(correoRe,passwordRe).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Map<String,Object> map= new HashMap<>();
                        map.put("Nombre",nombreRe);
                        map.put("Correo",correoRe);
                        map.put("Password",passwordRe);
                        map.put("Telefono",telefonoRe);

                        String id = AuthU.getCurrentUser().getUid();
                        databasereference.child("Usuario").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    //Iniciar una nueva activity a partir de otra (Definir en que activity estas).
                                    startActivity( new Intent(RegisterActivity.this,LoginActivity.class));
                                    Toast.makeText(RegisterActivity.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    String errorCode=((FirebaseAuthException)task.getException()).getErrorCode();
                                    dameToastdeerror(errorCode);
                                }
                            }
                        });
                    } else {
                        Toast.makeText(RegisterActivity.this, "Ocurrio un error al registrarte", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }
    private void dameToastdeerror(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(RegisterActivity.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(RegisterActivity.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(RegisterActivity.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(RegisterActivity.this, "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
                correoR.setError("La dirección de correo electrónico está mal formateada.");
                correoR.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(RegisterActivity.this, "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                passwordR.setError("la contraseña es incorrecta ");
                passwordR.requestFocus();
                passwordR.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(RegisterActivity.this, "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(RegisterActivity.this,"Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(RegisterActivity.this, "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(RegisterActivity.this, "La dirección de correo electrónico ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                correoR.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                correoR.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(RegisterActivity.this, "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(RegisterActivity.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(RegisterActivity.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(RegisterActivity.this, "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(RegisterActivity.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(RegisterActivity.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(RegisterActivity.this, "La contraseña proporcionada no es válida..", Toast.LENGTH_LONG).show();
                passwordR.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                passwordR.requestFocus();
                break;

        }

    }


    public void Back(View view){
        Intent back =  new Intent(this, WelcomeActivity.class);
        startActivity(back);
    }
//
//    public void Login(View view){
//        Intent login =  new Intent(this, LoginActivity.class);
//        startActivity(login);
//    }
}