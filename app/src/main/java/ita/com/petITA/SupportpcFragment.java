package ita.com.petITA;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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


public class SupportpcFragment extends Fragment {

    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    private String CurrentUserId;

    private TextView txt_revision, txt_price_revision, diagnostico, price_diagnostico, limpieza, precio_limpieza,actualizacionHardware, precio_ActualizaconHardware, actualizacionSOS, precio_actualizacionSOS;
    private ImageView img_revisionpc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_supportpc, container, false);

        //obtener datos de la bd
        mDatabase = FirebaseDatabase.getInstance().getReference();

        txt_revision = (TextView) root.findViewById(R.id.txt_revision);
        txt_price_revision = (TextView) root.findViewById(R.id.txt_price_revision);

        diagnostico = (TextView) root.findViewById(R.id.txt_diagnostico);
        price_diagnostico = (TextView) root.findViewById(R.id.txt_price_diagnostico);

        limpieza = (TextView) root.findViewById(R.id.txt_LimpiezaPC);
        precio_limpieza = (TextView) root.findViewById(R.id.txt_price_limpiezapc);

        actualizacionHardware = (TextView) root.findViewById(R.id.txt_ActualizaiconHard);
        precio_ActualizaconHardware = (TextView) root.findViewById(R.id.txt_price_actualizacionHard);

        actualizacionSOS = (TextView) root.findViewById(R.id.txt_ActualizacionSOS);
        precio_actualizacionSOS = (TextView) root.findViewById(R.id.txt_price_ActualizacionSOS);


        mDatabase.child("Servicio1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String revisones = snapshot.child("nombre").getValue().toString();
                    String precios_revisiones = snapshot.child("precio").getValue().toString();
                    txt_revision.setText(revisones);
                    txt_price_revision.setText("MXN $"+precios_revisiones);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabase.child("Servicio2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String diagnosticos = snapshot.child("nombre").getValue().toString();
                    String precios_diagnostico = snapshot.child("precio").getValue().toString();
                    diagnostico.setText(diagnosticos);
                    price_diagnostico.setText("MXN $"+precios_diagnostico);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabase.child("Servicio3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String limpiezas = snapshot.child("nombre").getValue().toString();
                    String precios_limpiezas = snapshot.child("precio").getValue().toString();
                    limpieza.setText(limpiezas);
                    precio_limpieza.setText("MXN $"+precios_limpiezas);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabase.child("Servicio4").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String hardware = snapshot.child("nombre").getValue().toString();
                    String precios_hardware = snapshot.child("precio").getValue().toString();
                    actualizacionHardware.setText(hardware);
                    precio_ActualizaconHardware.setText("MXN $"+precios_hardware);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabase.child("Servicio5").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String sos = snapshot.child("nombre").getValue().toString();
                    String precios_sos = snapshot.child("precio").getValue().toString();
                    actualizacionSOS.setText(sos);
                    precio_actualizacionSOS.setText("MXN $"+precios_sos);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //Pasar al metodo de pago
        img_revisionpc = root.findViewById(R.id.img_revisionpc);
        img_revisionpc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.paymentFragment);
            }
        });

        return root;
    }
}