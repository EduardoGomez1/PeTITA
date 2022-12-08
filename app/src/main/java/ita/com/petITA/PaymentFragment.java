package ita.com.petITA;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ita.com.petITA.R;


public class PaymentFragment extends Fragment {

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase2;
    private FirebaseAuth auth;
    private String CurrentUserId;

    private TextView txt_nameService, txt_priceService, txt_infoCliente;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private Button button_Aprobado;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_payment, container, false);
        //obtener informacion bd
        auth = FirebaseAuth.getInstance();
        CurrentUserId = auth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Usuario");

        txt_infoCliente = (TextView) root.findViewById(R.id.txt_infoCliente);

        mDatabase.child(CurrentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String nombres = snapshot.child("Nombre").getValue().toString();
                    String telefonos = snapshot.child("Telefono").getValue().toString();
                    String correos = snapshot.child("Correo").getValue().toString();
                    String direcciones = snapshot.child("direccion").getValue().toString();

                    txt_infoCliente.setText("Nombre:" + nombres + "\nNumero de Telefono: "+telefonos+"\nCorreo: "+correos+"\nDireccion: "+direcciones);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabase2 = FirebaseDatabase.getInstance().getReference();

        txt_nameService = (TextView) root.findViewById(R.id.txt_nameService);
        txt_priceService = (TextView) root.findViewById(R.id.txt_priceService);

        mDatabase2.child("Servicio1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String revisones = snapshot.child("nombre").getValue().toString();
                    String precios_revisiones = snapshot.child("precio").getValue().toString();
                    txt_nameService.setText(revisones);
                    txt_priceService.setText("MXN $"+precios_revisiones);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //boton aprobado
        button_Aprobado = root.findViewById(R.id.btn_cash);
        button_Aprobado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.aprobadoFragment);
                Toast.makeText(getActivity(),"Aprobado!",Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}