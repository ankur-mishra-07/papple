package com.ifocus.papple.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ifocus.papple.manager.NetWorkHandler;
import com.ifocus.papple.R;
import com.ifocus.papple.Utils.AndroidUtils;
import com.ifocus.papple.Utils.RetroService;
import com.ifocus.papple.database.DBUserDetailsHelper;
import com.ifocus.papple.fragments.LoadingDialogFragment;
import com.ifocus.papple.helpers.Constant;
import com.ifocus.papple.helpers.PreferenceHelper;
import com.ifocus.papple.interfaces.IRetroTaskCompleteListener;
import com.ifocus.papple.model.UserAuth;
import com.ifocus.papple.model.UserDetails;
import com.ifocus.papple.views.textviewplus.TextViewPlus;

import java.util.HashMap;
import java.util.Map;

import retrofit.Retrofit;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener, IRetroTaskCompleteListener {

    private EditText loginIdEt;
    private EditText passwordEt;
    private DBUserDetailsHelper detailsHelper;
    private LoadingDialogFragment loadingDialogFragment;
    private ProgressDialog progressDialog;
    private CardView loginButton, signupButton;
    private String userID;
    private Activity activity;
    public static final int USER_SIGNUP_REQUEST = 101;
    public static final String INTENT_USER_ID = "userid";
    /**
     * Interface for result
     */
    private IRetroTaskCompleteListener taskCompleted;
    /**
     * PostParams
     */
    private Map<String, String> postParams = new HashMap<>();

    // Retrofit object for initializing the base url
    private Retrofit retrofit;
    private TextViewPlus forgotPassword;
    private PreferenceHelper saveCred;
    private RelativeLayout root;
    private String loginId, password;
    /**
     * Timeout for Splash
     */
    private int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;
        saveCred = new PreferenceHelper(activity);
        initViews();
        initListeners();

    }

    private void initListeners() {
        signupButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        //RetroFit Creation
        retrofit = RetroService.returnRestadapter(this);
        taskCompleted = this;
    }

    private void initViews() {
        loginIdEt = (EditText) findViewById(R.id.loginId);
        passwordEt = (EditText) findViewById(R.id.password);
        detailsHelper = new DBUserDetailsHelper(this, null, null, 1);
        loginButton = (CardView) findViewById(R.id.loginButton);
        signupButton = (CardView) findViewById(R.id.signupButton);
        root = (RelativeLayout) findViewById(R.id.root);
        forgotPassword = (TextViewPlus) findViewById(R.id.forgotPassword);
        checkLoginStatus();
    }

    private void checkLoginStatus() {
        if (saveCred.getLogintrue()) {
            progressDialog = ProgressDialog.show(
                    LoginActivity.this,
                    null,
                    getString(R.string.login_msg),
                    true,
                    false
            );
            loginIdEt.setVisibility(View.GONE);
            passwordEt.setVisibility(View.GONE);
            loginButton.setVisibility(View.GONE);
            signupButton.setVisibility(View.GONE);
            forgotPassword.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {
                /*
                 * Showing splash screen with a timer. This will be useful when you
                 * want to show case your app logo / company
                 */
                @Override
                public void run() {
                    if (saveCred.getLoginRole().equalsIgnoreCase("user")) {
                        navigateUserHomeScreen();
                    } else {
                        navigateContractorHomeScreen();
                    }
                }
            }, SPLASH_TIME_OUT);
        } else {
            loginIdEt.setVisibility(View.VISIBLE);
            passwordEt.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.VISIBLE);
            signupButton.setVisibility(View.VISIBLE);
            forgotPassword.setVisibility(View.VISIBLE);
        }
    }


    public void loginUser() { //TODO: Implement WS
        loginId = String.valueOf(loginIdEt.getText());
        password = String.valueOf(passwordEt.getText());

        Drawable drawable = getResources().getDrawable(R.drawable.ic_error_yellow_18dp);
        drawable.setBounds(0, 0, 25, 25);
        if (loginId.isEmpty() || password.isEmpty()) {
//            successLoading();
            Toast.makeText(this, "Bad credentials!", Toast.LENGTH_LONG).show();
            loginIdEt.requestFocus();
            loginIdEt.setError("Please check your credentials and try again",
                    drawable);
            return;
        }


        if (loginId.length() > 3 && password.length() > 3) {
            callLogin();
        } else {
            progressDialog.dismiss();
            Toast.makeText(this, "Bad credentials!", Toast.LENGTH_LONG).show();
            loginIdEt.requestFocus();
            loginIdEt.setError("Please check your credentials and try again",
                    drawable);
        }
    }

    private void callLogin() {
        /**
         * calling Login server for updating user data
         */

        if (AndroidUtils.isNetworkAvailable(LoginActivity.this)) {

            saveCred.putLogintrue(true);
            //Making Retro Fit call
            setProgressBarIndeterminateVisibility(true);
            // call Network Handler
            postParams.clear();
            postParams.put(getString(R.string.email_), loginId);
            postParams.put(getString(R.string.idToken), password);
            new NetWorkHandler(activity, Constant.AGENT_LOGIN, taskCompleted, retrofit, postParams, false);

        } else {
            AndroidUtils.showSnackBar(getString(R.string.networkError), root);
        }
    }

    //TODO : Remove after WS call made
    private void insertStaticPosts() {
        try {
            for (int i = 0; i < 10; i++) {
                detailsHelper.insertPosts("Barny Ted",
                        getString(R.string.userStaticInfo), "0", "0", "", "", "General Awareness" + i, "Social", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateUserHomeScreen() {
        if (progressDialog != null)
            progressDialog.dismiss();
        Intent intent = new Intent(this, HomeActivity.class); //TODO: Change intent. Fetch from DB
        startActivity(intent);
        finish();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signupButton:
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.loginButton:
                loginUser();
                break;
        }
    }

    @Override
    public void onResponseLanded(int serviceCode, String Response) {
        setProgressBarIndeterminateVisibility(false);
        if (!Response.isEmpty()) {
            saveCred.setLoginRole(Response.split(",")[0]);
            saveCred.setDisplayName(loginId);
            if (Response.split(",")[1] != null) {
                saveCred.putUserId(Response.split(",")[1]);
            }
            if (Response.split(",")[0].equalsIgnoreCase(getString(R.string.user))) {
                successLoadingUser();
            } else if (Response.split(",")[0].equalsIgnoreCase(getString(R.string.contractor))) {
                successLoadingContractor();
            } else {
                AndroidUtils.showSnackBar(getString(R.string.error_user_type), root);
            }
        }
    }

    private void successLoadingUser() {
        saveCred.putLogintrue(true);
        insertStaticPosts();
        navigateUserHomeScreen();
    }

    private void successLoadingContractor() {
        saveCred.putLogintrue(true);
        insertStaticPosts();
        navigateContractorHomeScreen();
    }

    private void navigateContractorHomeScreen() {
        if (progressDialog != null)
            progressDialog.dismiss();
        Intent intent = new Intent(this, ContractorHomeActivity.class); //TODO: Change intent. Fetch from DB
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailureLanded(int serviceCode, String Failure) {
        setProgressBarIndeterminateVisibility(false);
    }
}

