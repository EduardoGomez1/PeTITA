package ita.com.petITA;

import android.content.Intent;
import android.net.Uri;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ita.com.petITA.R;


public class HomeServiceFragment extends Fragment {

    String url = "https://www.google.com.mx/maps/place/Salvador+Novo+347,+Pensadores+Mexicanos,+20179+Aguascalientes,+Ags./@21.9154192,-102.2643157,17z/data=!3m1!4b1!4m5!3m4!1s0x8429ee2c40f62731:0x5ab01cf16c17e360!8m2!3d21.9154253!4d-102.2621263";
    String urlchat = "https://api.whatsapp.com/send?phone=52";
    private Button btn_Maps;
    private Button btn_chat;
    private Button btn_qualify;
    TextView text;

    private DatabaseReference mDatabase;
    private TextView direccion,numbertecnico,nombreTecnico,correotecnico;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View root = inflater.inflate(R.layout.fragment_home_service, container, false);

       //obtener datos de la BD
        mDatabase = FirebaseDatabase.getInstance().getReference();

        nombreTecnico = (TextView) root.findViewById(R.id.txt_nombreTecnico);
        direccion = (TextView) root.findViewById(R.id.tex_direccion);
        numbertecnico = (TextView) root.findViewById(R.id.tex_numbertecnico);
        correotecnico = (TextView) root.findViewById(R.id.txt_correoTecnico);

        mDatabase.child("Tecnico").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nombres = snapshot.child("nombre").getValue().toString();
                    String telefonos = snapshot.child("Telefono").getValue().toString();
                    String direcciones = snapshot.child("Direccion").getValue().toString();
                    String correos = snapshot.child("Correo").getValue().toString();

                    nombreTecnico.setText(nombres);
                    direccion.setText(direcciones );
                    correotecnico.setText(correos);
                    numbertecnico.setText(telefonos);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       //botones
       btn_Maps = root.findViewById(R.id.btn_map);
       btn_Maps.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Uri uri = Uri.parse(url);
               Intent intent = new Intent(Intent.ACTION_VIEW, uri);
               startActivity(intent);
               Toast.makeText(getActivity(),"Ubicacion del Tecnico!",Toast.LENGTH_SHORT).show();
           }
       });


       btn_chat = root.findViewById(R.id.btn_chat);
       btn_chat.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               text = root.findViewById(R.id.tex_numbertecnico);
               String number = text.getText().toString();

               Uri uri = Uri.parse(urlchat+number);
               Intent intent = new Intent(Intent.ACTION_VIEW, uri);
               startActivity(intent);
               Toast.makeText(getActivity(),"Chat con el Tecnico!",Toast.LENGTH_SHORT).show();
           }
       });

       btn_qualify = root.findViewById(R.id.btn_qualify);
       btn_qualify.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Navigation.findNavController(view).navigate(R.id.ratingFragment);
           }
       });


        return root;
    }

}