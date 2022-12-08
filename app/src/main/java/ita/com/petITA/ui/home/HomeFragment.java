package ita.com.petITA.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import ita.com.petITA.R;
import ita.com.petITA.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private Button button_About;
    private Button button_pc;
    private Button button_lap;
    private Button button_soft;
    private Button button_net;

    public View onCreateView(
                @NonNull LayoutInflater inflater,
                ViewGroup container,
                Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        button_About = root.findViewById(R.id.btn_about_home);
        button_pc = root.findViewById(R.id.btn_pc_home);
        button_lap = root.findViewById(R.id.btn_lap);
        button_net = root.findViewById(R.id.btn_net);
        button_soft = root.findViewById(R.id.btn_soft);



        button_About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.aboutFragment);
            }
        });

        button_pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.suportPcInfoFragment);
            }
        });

        button_lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.supporLapInfoFragment);
            }
        });

        button_soft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.instalationSoftInfoFragment);
            }
        });

        button_net.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.instalationNetInfoFragment);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}