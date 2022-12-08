package ita.com.petITA;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import ita.com.petITA.R;


public class AprobadoFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Button btn_aprobado;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_aprobado, container, false);

        btn_aprobado = root.findViewById(R.id.btn_aprobado);
        btn_aprobado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
                Navigation.findNavController(view).navigate(R.id.homeServiceFragment);
            }
        });

        return root;
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =  getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}