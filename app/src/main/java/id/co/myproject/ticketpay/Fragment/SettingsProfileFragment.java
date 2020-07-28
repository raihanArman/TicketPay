package id.co.myproject.ticketpay.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import id.co.myproject.ticketpay.MainActivity;
import id.co.myproject.ticketpay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsProfileFragment extends Fragment {

    ImageView iv_back;
    TextView tv_edit_profile, tv_sign_out;
    FirebaseAuth auth;
    FrameLayout frameLayout;
    public SettingsProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings_profile, container, false);
        frameLayout = getActivity().findViewById(R.id.frame);
        iv_back = view.findViewById(R.id.back);
        tv_edit_profile = view.findViewById(R.id.tv_edit_profile);
        tv_sign_out = view.findViewById(R.id.tv_sign_out);
        auth = FirebaseAuth.getInstance();
        tv_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(frameLayout.getId(), profileFragment);
                transaction.commit();
            }
        });

        return view;
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Sign Out");
        builder.setMessage("Apakah anda yakin ingin keluar ?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                auth.signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

}
