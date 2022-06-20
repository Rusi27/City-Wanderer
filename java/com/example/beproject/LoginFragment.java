package com.example.beproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginFragment extends Fragment {

    //Constants
    private static int RC_SIGN_IN = 100;
    private float START_ALPHA = 0f , END_ALPHA = 1f , START_TRANSLATION = 150f , END_TRANSLATION = 0f;
    private long DURATION = 700;

    //XML Components
    private EditText et_Username , et_Password;
    private Button bt_Login;
    private TextView tv_OR;
    private FloatingActionButton fbt_Google_Sign_In;

    //Android built-in class objects
    ViewGroup login;

    FirebaseAuth fAuth;
    GoogleSignInClient mGoogleSignInClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        login = (ViewGroup) inflater.inflate(R.layout.fragment_login , container , false);


        //Connecting XML components
        connectXmlComponents();

        //Animating components
        componentAnimation();

        createRequest();
        fAuth = FirebaseAuth.getInstance();

        onClickForLogin();

        fbt_Google_Sign_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        return login;
    }
    

    //Connecting XML components
    private void connectXmlComponents(){
        et_Username = login.findViewById(R.id.etUsername);
        et_Password = login.findViewById(R.id.etPassword);
        bt_Login = login.findViewById(R.id.btLogin);
        tv_OR = login.findViewById(R.id.tv);
        fbt_Google_Sign_In = login.findViewById(R.id.fbtGoogleSignIn);
    }

    //Animating components
    private void componentAnimation(){
        //Translation
        et_Username.setTranslationY(-START_TRANSLATION);
        et_Password.setTranslationY(-START_TRANSLATION);
        bt_Login.setTranslationY(-START_TRANSLATION);
        tv_OR.setTranslationY(START_TRANSLATION);
        fbt_Google_Sign_In.setTranslationY(START_TRANSLATION);

        //Opacity
        et_Username.setAlpha(START_ALPHA);
        et_Password.setAlpha(START_ALPHA);
        bt_Login.setAlpha(START_ALPHA);
        tv_OR.setAlpha(START_ALPHA);
        fbt_Google_Sign_In.setAlpha(START_ALPHA);

        //Animate
        et_Username.animate().translationY(END_TRANSLATION).alpha(END_ALPHA).setDuration(DURATION).setStartDelay(300).start();
        et_Password.animate().translationY(END_TRANSLATION).alpha(END_ALPHA).setDuration(DURATION).setStartDelay(300).start();
        bt_Login.animate().translationY(END_TRANSLATION).alpha(END_ALPHA).setDuration(DURATION).setStartDelay(300).start();
        tv_OR.animate().translationY(END_TRANSLATION).alpha(END_ALPHA).setDuration(DURATION).setStartDelay(1000).start();
        fbt_Google_Sign_In.animate().translationY(END_TRANSLATION).alpha(END_ALPHA).setDuration(DURATION).setStartDelay(1000).start();
    }

    private void onClickForLogin() {

        bt_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strUsername = et_Username.getText().toString().trim();
                String strPassword = et_Password.getText().toString().trim();

                if (strUsername.isEmpty()) {
                    et_Username.setError("Username empty!");
                    et_Username.requestFocus();
                    return;
                }

                if (strPassword.isEmpty()) {
                    et_Password.setError("Password empty");
                    et_Password.requestFocus();
                    return;
                }


                //Authenticate User From Firebase
                fAuth.signInWithEmailAndPassword(strUsername, strPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), QuestionnaireActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), "Login Failed!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                });
            }

        });
    }

    private void createRequest(){
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString((R.string.default_web_client_id)))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(getActivity(),""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = fAuth.getCurrentUser();

                            Intent intent = new Intent(getActivity(), QuestionnaireActivity.class);
                            startActivity(intent);

                            Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            Toast.makeText(getActivity(), "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}