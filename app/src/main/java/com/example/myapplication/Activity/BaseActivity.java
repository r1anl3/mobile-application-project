package com.example.myapplication.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Fragment.MapFragment;
import com.example.myapplication.Manager.LanguageManager;
import com.example.myapplication.R;
import com.example.myapplication.Users;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.util.Objects;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "Base Activity";
    public boolean isAuthorizedByGoogle = false;
    FirebaseAuth auth;
    FirebaseDatabase database;
    GoogleSignInClient mGoogleSignInClient;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(BaseActivity.this);
        progressDialog.setTitle("Creating account");
        progressDialog.setMessage("We are creating your account");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
    }

    public void onLanguageChange() {
        // Change language
        LanguageManager manager = new LanguageManager(BaseActivity.this);
        String currLang = manager.checkResource();

        if (Objects.equals(currLang, "en")) {
            // if language is English, change to Vietnamese
            manager.updateResource("vi");
        }
        else if (Objects.equals(currLang, "vi")){
            // if language is Vietnamese, change to English
            manager.updateResource("en");
        }
        recreate(); // recreate the build
    }

    public void setLangIcon() {
        // Synchronize with app current language
        LanguageManager manager = new LanguageManager(this);
        ImageButton button = findViewById(R.id.btn_changeLanguage);
        String devLang = manager.checkResource();
        manager.updateIcon(devLang, button);
        Log.d(TAG, "setLangIcon: " + devLang);
    }

    public void openMainActivity() {
        // Open main activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void openRegisterActivity() {
        // Open register activity
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivities(new Intent[]{intent});
    }

    public void openLogInActivity() {
        // Open login activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivities(new Intent[]{intent});
    }

    public void openDashboardActivity() {
        // Open dashboard activity
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivities(new Intent[]{intent});
    }

    public void openChangePasswordActivity() {
        // Open Change password activity
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        startActivities(new Intent[]{intent});
    }

    int RC_SIGN_IN = 40;
    public void signInWithGoogle() {
        // Authenticate by google
        /*
            Do something here to be authorized by Google
            Update isAuthorizedByGoogle
         */
//        isAuthorizedByGoogle = true;
//
//        if (isAuthorizedByGoogle) {
//            openDashboardActivity();
//            finish();
//        }
//        else {
//            // Pop up message show that "Can not sign in"
//            Toast mToast = Toast.makeText(this, R.string.signup_warning, Toast.LENGTH_SHORT); // Warning user
//            mToast.show();
//        }
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuth(account.getIdToken());
            } catch (ApiException e) {
                throw new RuntimeException(e);

            }
        }
    }

    private void firebaseAuth(String idToken){

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);

        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            FirebaseUser user = auth.getCurrentUser();

                            Users users = new Users();
                            users.setUserId(user.getUid());
                            users.setName(user.getDisplayName());
                            users.setProfile(user.getPhotoUrl().toString());

                            database.getReference().child("Users").child(user.getUid()).setValue(users);

                            Intent intent = new Intent(BaseActivity.this,DashboardActivity.class);
                            startActivity(intent);

                        }
                        else {

                            Toast.makeText(BaseActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    boolean isEmail(EditText text) {
        // Check email format
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public long getTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // Get system timestamp in milliseconds
        return timestamp.getTime();
    }
}

