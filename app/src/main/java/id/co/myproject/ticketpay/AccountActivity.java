package id.co.myproject.ticketpay;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import id.co.myproject.ticketpay.Fragment.LoginFragment;
import id.co.myproject.ticketpay.Fragment.ProfileFragment;

public class AccountActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        frameLayout = findViewById(R.id.frame);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        mAuth = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null){

                }else {

                }
            }
        };
        if (auth.getCurrentUser() == null || !user.isEmailVerified()){
            setFragment(new LoginFragment());
        }else {
            setFragment(new ProfileFragment());
        }
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(frameLayout.getId(), fragment);
        transaction.commit();
    }
}
