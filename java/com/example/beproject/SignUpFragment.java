package com.example.beproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment {

    //XML Components
    EditText et_Set_Username , et_Set_Password , et_Confirm_Password;
    Button bt_Sign_Up;

    FirebaseAuth fAuth;

    //Android built-in class objects
    ViewGroup signUp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        signUp = (ViewGroup) inflater.inflate(R.layout.fragment_sign_up , container , false);

        //Connecting XML components
        connectXmlComponents();

        fAuth = FirebaseAuth.getInstance();

        //Onclick for SignUp Button
        bt_Sign_Up.setOnClickListener(view -> {

            String username = et_Set_Username.getText().toString().trim();
            String password = et_Set_Password.getText().toString().trim();
            String confpass = et_Confirm_Password.getText().toString().trim();

            if(username.isEmpty()) {
                et_Set_Username.setError("Username is required");
                et_Set_Username.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                et_Set_Password.setError("Password is required");
                et_Set_Password.requestFocus();
                return;
            }

            if (password.length()<6) {
                et_Set_Password.setError("Password less than 6 characters");
                et_Set_Password.requestFocus();
                return;
            }

            if (confpass.isEmpty()) {
                et_Confirm_Password.setError("Password is required");
                et_Confirm_Password.requestFocus();
                return;
            }
            if(!confpass.equals(password)){
                et_Confirm_Password.setError("Password doesn't match");
                et_Confirm_Password.requestFocus();
                return;
            }

            //FireBase creating new user
            fAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Registration Successful Please login now", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getActivity(), "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        });


        return signUp;
    }

    //Connecting XML components
    private void connectXmlComponents(){
        et_Set_Username = signUp.findViewById(R.id.etSetUsername);
        et_Set_Password = signUp.findViewById(R.id.etSetPassword);
        et_Confirm_Password = signUp.findViewById(R.id.etConfirmPassword);
        bt_Sign_Up = signUp.findViewById(R.id.btSignUp);
    }
}
