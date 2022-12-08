package ita.com.petITA;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
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

public class RatingFragment extends Fragment {
    private RatingBar ratingBar;
    private Button btn_submit;

    private DatabaseReference mDatabase;
    private TextView nombretecnico;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_rating, container, false);
        //obtner datos bd
        mDatabase = FirebaseDatabase.getInstance().getReference();

        nombretecnico = (TextView) root.findViewById(R.id.txt_nombreTec);
        mDatabase.child("Tecnico").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nombres = snapshot.child("nombre").getValue().toString();
                    nombretecnico.setText(nombres);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //calificar
        ratingBar = root.findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(getActivity(),"Usted calificado con: "+v,Toast.LENGTH_SHORT).show();
            }
        });

        btn_submit = root.findViewById(R.id.btn_saveRating);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_home);
                Toast.makeText(getActivity(),"Calificado con Exito: ",Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
}