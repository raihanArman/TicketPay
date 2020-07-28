package id.co.myproject.ticketpay.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import id.co.myproject.ticketpay.MainActivity;
import id.co.myproject.ticketpay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    EditText edt_username, edt_password;
    Button btn_login;
    TextView txt_forgot, txt_dont_have_account;
    FrameLayout parent_frame;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    ProgressBar progressBar;
    FirebaseAuth auth;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        edt_username = view.findViewById(R.id.edt_username);
        edt_password = view.findViewById(R.id.edt_password);
        btn_login = view.findViewById(R.id.btn_login);
        txt_forgot = view.findViewById(R.id.txt_forgot_password);
        txt_dont_have_account = view.findViewById(R.id.txt_dont_have_account);
        parent_frame = getActivity().findViewById(R.id.frame);
        progressBar = view.findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txt_dont_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new RegisterFragment());
            }
        });
        edt_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edt_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailAndPassword();
            }
        });
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(parent_frame.getId(), fragment);
        transaction.commit();
    }

    private void checkInput(){
        if (!TextUtils.isEmpty(edt_username.getText())){
            if (!TextUtils.isEmpty(edt_password.getText())){
                btn_login.setEnabled(true);
            }else {
                btn_login.setEnabled(false);
            }
        }else {
            btn_login.setEnabled(false);
        }
    }

    private void checkEmailAndPassword(){
        final FirebaseUser user = auth.getCurrentUser();
        if (edt_username.getText().toString().matches(emailPattern)){
            if (edt_password.length() >= 8){
                progressBar.setVisibility(View.VISIBLE);
                btn_login.setEnabled(true);

                auth.signInWithEmailAndPassword(edt_username.getText().toString(), edt_password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (user.isEmailVerified()){
                                    mainIntent();
                                }else {
                                    progressBar.setVisibility(View.GONE);
                                    btn_login.setEnabled(true);
                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }else {
                Toast.makeText(getActivity(), "Password kurang boss", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getActivity(), "Username atau Password salah boss", Toast.LENGTH_SHORT).show();
        }
    }

    private void mainIntent() {
        Intent main = new Intent(getActivity(), MainActivity.class);
        startActivity(main);
        getActivity().finish();
    }
}
