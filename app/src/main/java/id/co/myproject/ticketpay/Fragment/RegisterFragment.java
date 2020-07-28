package id.co.myproject.ticketpay.Fragment;


import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import id.co.myproject.ticketpay.MainActivity;
import id.co.myproject.ticketpay.Model.User;
import id.co.myproject.ticketpay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    String userId;

    EditText edt_email, edt_password, edt_name, edt_confirm;
    Button btn_regis;
    TextView txt_have_accountl;
    ProgressBar progressBar;
    FrameLayout parentFrameLayout;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private String append;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        edt_email = view.findViewById(R.id.edt_email);
        edt_password = view.findViewById(R.id.edt_password);
        edt_name = view.findViewById(R.id.edt_fullname);
        edt_confirm = view.findViewById(R.id.edt_confirm_password);
        btn_regis = view.findViewById(R.id.btn_regis);
        txt_have_accountl = view.findViewById(R.id.txt_have_account);
        progressBar = view.findViewById(R.id.progressBar);
        parentFrameLayout = getActivity().findViewById(R.id.frame);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edt_email.addTextChangedListener(new TextWatcher() {
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
        edt_name.addTextChangedListener(new TextWatcher() {
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
        edt_confirm.addTextChangedListener(new TextWatcher() {
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

        txt_have_accountl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new LoginFragment());
            }
        });

        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewUser(edt_email.getText().toString(), edt_password.getText().toString(), edt_name.getText().toString());
            }
        });
    }

    private void addNewUser(final String email, final String password, final String username){
        Drawable customIconError = getResources().getDrawable(R.drawable.custom_error_icon);
        customIconError.setBounds(0,0,customIconError.getIntrinsicWidth(), customIconError.getIntrinsicHeight());
        if (edt_email.getText().toString().matches(emailPattern)){
            if (edt_password.getText().toString().equals(edt_confirm.getText().toString())){
                progressBar.setVisibility(View.VISIBLE);
                btn_regis.setEnabled(false);

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    sendVerificationEmail();
                                    userId = auth.getCurrentUser().getUid();
                                    User user = new User(userId, email, username);
                                    reference.child("User").child(userId).setValue(user);
                                    setFragment(new LoginFragment());
                                }else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    btn_regis.setEnabled(true);
                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }else {
                edt_confirm.setError("Password tidak cocok", customIconError);
            }
        }else {
            edt_email.setError("Email tidak cocok", customIconError);
        }
    }

    private void checkInput(){
        if (!TextUtils.isEmpty(edt_email.getText())){
            if (!TextUtils.isEmpty(edt_name.getText())){
                if (!TextUtils.isEmpty(edt_password.getText()) && edt_confirm.length() >= 8){
                    if (!TextUtils.isEmpty(edt_confirm.getText())){
                        btn_regis.setEnabled(true);
                    }else {
                        btn_regis.setEnabled(false);
                    }
                }else {
                    btn_regis.setEnabled(false);
                }
            }else {
                btn_regis.setEnabled(false);
            }
        }else {
            btn_regis.setEnabled(false);
        }
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(parentFrameLayout.getId(), fragment);
        transaction.commit();
    }

    private void mainIntent(){
        Intent main = new Intent(getActivity(), MainActivity.class);
        startActivity(main);
        getActivity().finish();
    }


    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getActivity(), "Berhasil Mengirim, segera cek email anda", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getActivity(), "Tidak Bisa Mengirim boss", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

}
