package ita.com.petITA;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import ita.com.petITA.R;

public class LoginActivity extends Activity {

    EditText CorreoU, PasswordU;
    TextView ForgotU;
    Button ButtonL;
    FirebaseAuth AuthU;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Instanciando las variables.
        CorreoU = (EditText) findViewById(R.id.etxt_email_login);
        PasswordU = (EditText) findViewById(R.id.etxt_password_login);
        ButtonL =(Button) findViewById(R.id.btn_userLogin);
        ForgotU=(TextView)findViewById(R.id.txt_forgot_login);

        AuthU = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.etxt_email_login,Patterns.EMAIL_ADDRESS,R.string.invalid_mail);
        awesomeValidation.addValidation(this,R.id.etxt_password_login,".{6,}",R.string.invalid_password);
        //Evento al clickear el boton de login.
        ButtonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Llamar función para iniciar sesion
                IniciarSesion();
            }
        });
        ForgotU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(LoginActivity.this,ForgotActivity.class));
            }
        });


    }



    //Funcion para iniciar sesion.
    public void IniciarSesion(){
        if(awesomeValidation.validate()){


            //Definicion de variables para comparar en firebase autoriazacion.
            String CorreoF = CorreoU.getText().toString();
            String PasswordF = PasswordU.getText().toString();
            //Comparar las variables de autenticacion en firebase.
            AuthU.signInWithEmailAndPassword(CorreoF,PasswordF).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    //Validacion si la tarea fue realizada (Comparar el usuario y la contraseña de firebase)
                    if (task.isSuccessful()){

                        //Iniciar una nueva activity a partir de otra (Definir en que activity estas).
                        startActivity( new Intent(LoginActivity.this,MenuActivity.class));
                        //Mensaje para indicar al usuario que su sesion fue correcta.
                        Toast.makeText(LoginActivity.this, "Inicio de sision exitoso :)", Toast.LENGTH_SHORT).show();
                        //No permite regresar a la activity anterior.
                        finish();
                    }else{
                        String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                        dameToastdeerror(errorCode);
                    }
                }
            });
        }
    }

    private void dameToastdeerror(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(LoginActivity.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(LoginActivity.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(LoginActivity.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(LoginActivity.this, "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
                CorreoU.setError("La dirección de correo electrónico está mal formateada.");
                PasswordU.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(LoginActivity.this, "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                PasswordU.setError("la contraseña es incorrecta ");
                PasswordU.requestFocus();
                PasswordU.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(LoginActivity.this, "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(LoginActivity.this,"Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(LoginActivity.this, "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(LoginActivity.this, "La dirección de correo electrónico ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                CorreoU.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                CorreoU.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(LoginActivity.this, "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(LoginActivity.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(LoginActivity.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(LoginActivity.this, "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(LoginActivity.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(LoginActivity.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(LoginActivity.this, "La contraseña proporcionada no es válida..", Toast.LENGTH_LONG).show();
                PasswordU.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                PasswordU.requestFocus();
                break;

        }

    }
//    public void Login(View view){
//        Intent login =  new Intent(this, MenuActivity.class);
//        startActivity(login);
//    }
    public void Back(View view){
        Intent back =  new Intent(this, WelcomeActivity.class);
        startActivity(back);
    }
//
//    public void Forgot(View view){
//        Intent forgot =  new Intent(this, ForgotActivity.class);
//        startActivity(forgot);
//    }
}