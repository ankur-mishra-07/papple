package com.ifocus.papple.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.ifocus.papple.R;
import com.ifocus.papple.Utils.AndroidUtils;
import com.ifocus.papple.Utils.RetroService;
import com.ifocus.papple.adapter.SignupSpinnerAdapter;
import com.ifocus.papple.database.DBUserDetailsHelper;
import com.ifocus.papple.helpers.Constant;
import com.ifocus.papple.helpers.StringHelpers;
import com.ifocus.papple.helpers.ViewHelpers;
import com.ifocus.papple.interfaces.IRetroTaskCompleteListener;
import com.ifocus.papple.manager.Signupmanager;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Retrofit;


public class SignUpActivity extends AppCompatActivity implements IRetroTaskCompleteListener {
    public static final int USERNAME_MIN_LENGTH = 6;
    public static final int USERNAME_MAX_LENGTH = 20;
    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int PASSWORD_MAX_LENGTH = 22;
    private final String USER_TYPE_CORP = "corporator";
    private final String USER_TYPE_GEN = "general";

    private EditText usernameEt;
    private EditText passwordEt;
    private EditText emailEt;
    private EditText fNameEt;
    private EditText lNameEt;
    private EditText phoneNoEt;
    private EditText proofNoEt;
    private EditText birthDateEt;
    private EditText birthMonthEt;
    private EditText birthYearEt;
    private CoordinatorLayout root;

    private Spinner bloodGroupSp;
    private Spinner martialStatusSp;
    private Spinner wardNameSp;
    private Spinner proofTypeSp;
    private Spinner userTypeSp;


    private int selectedYear;
    private int selectedMonth;
    private int selectedDate;

    private ProgressDialog progressDialog;
    private Activity activity;

    private DBUserDetailsHelper userDetailsHelper;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userDetailsHelper = new DBUserDetailsHelper(this, null, null, 1);
        //RetroFit Creation
        retrofit = RetroService.returnRestadapter(this);
        taskCompleted = this;
        activity = this;
        initViews();
        initListeners();
    }

    private void initListeners() {

        Button backtoLoginBtn = (Button) findViewById(R.id.backtoLoginButton);
        backtoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout birthdateLayout = (LinearLayout) findViewById(R.id.signupBirthdateLayout);

        final DatePickerDialog.OnDateSetListener datePickListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                selectedDate = dayOfMonth;
                selectedMonth = monthOfYear;
                selectedYear = year;

                birthDateEt.setText(String.valueOf(dayOfMonth));
                birthYearEt.setText(String.valueOf(year));
                birthMonthEt.setText(String.valueOf(monthOfYear + 1));
            }
        };
        View.OnClickListener dateListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this,
                        datePickListener, (birthYearEt.getText().toString().equals("")) ? 1991 : Integer.parseInt(birthYearEt.getText().toString()),
                        (birthMonthEt.getText().toString().equals("")) ? 0 : Integer.parseInt(birthMonthEt.getText().toString()) - 1,
                        (birthDateEt.getText().toString().equals("")) ? 1 : Integer.parseInt(birthDateEt.getText().toString()));
                datePickerDialog.show();
            }
        };
        birthdateLayout.setOnClickListener(dateListener);
        birthDateEt.setOnClickListener(dateListener);
        birthYearEt.setOnClickListener(dateListener);
        birthMonthEt.setOnClickListener(dateListener);

        Button registerBtn = (Button) findViewById(R.id.registerButton);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(SignUpActivity.this, ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMessage("Registering your details. Please wait...");
                progressDialog.setIndeterminate(true);
                progressDialog.setProgress(0);
                progressDialog.show();
                progressDialog.setCancelable(false);
                registerUser(v);
                progressDialog.dismiss();

            }
        });

        userTypeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getSelectedItem().toString().toLowerCase().equals(USER_TYPE_CORP)) {
                    proofTypeSp.setAdapter(new SignupSpinnerAdapter(SignUpActivity.this, R.layout.signup_list_item,
                            getResources().getStringArray(R.array.corporatorProofTypes)));
                } else {
                    proofTypeSp.setAdapter(new SignupSpinnerAdapter(SignUpActivity.this, R.layout.signup_list_item,
                            getResources().getStringArray(R.array.proofTypes)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initViews() {
        root = (CoordinatorLayout) findViewById(R.id.root);
        usernameEt = (EditText) findViewById(R.id.signupUsername);
        passwordEt = (EditText) findViewById(R.id.signupPassword);
        emailEt = (EditText) findViewById(R.id.signupEmail);
        fNameEt = (EditText) findViewById(R.id.signupFname);
        lNameEt = (EditText) findViewById(R.id.signupLname);
        phoneNoEt = (EditText) findViewById(R.id.signupPhoneNumber);
        proofNoEt = (EditText) findViewById(R.id.signupProofDetail);
        birthDateEt = (EditText) findViewById(R.id.signupBirthDate);
        birthMonthEt = (EditText) findViewById(R.id.signupBirthMonth);
        birthYearEt = (EditText) findViewById(R.id.signupBirthYear);

        bloodGroupSp = (Spinner) findViewById(R.id.signupBgroup);
        bloodGroupSp.setAdapter(new SignupSpinnerAdapter(this, R.layout.signup_list_item,
                getResources().getStringArray(R.array.bloodGroups)));

        martialStatusSp = (Spinner) findViewById(R.id.signupMstatus);
        martialStatusSp.setAdapter(new SignupSpinnerAdapter(this, R.layout.signup_list_item,
                getResources().getStringArray(R.array.martialStatus)));

        wardNameSp = (Spinner) findViewById(R.id.signupWardname);
        wardNameSp.setAdapter(new SignupSpinnerAdapter(this, R.layout.signup_list_item,
                getResources().getStringArray(R.array.wardNames)));

        proofTypeSp = (Spinner) findViewById(R.id.signupProoftype);

        userTypeSp = (Spinner) findViewById(R.id.signupUserType);
        userTypeSp.setAdapter(new SignupSpinnerAdapter(this, R.layout.signup_list_item,
                getResources().getStringArray(R.array.userTypes)));
    }

    public void registerUser(View view) {//TODO: Implement WS

        List<EditText> emptyEditTexts =
                ViewHelpers.validatePresenceEditText(true, getString(R.string.error_required),
                        usernameEt, passwordEt, emailEt, phoneNoEt, proofNoEt, birthDateEt, birthMonthEt, birthYearEt);
        if (!emptyEditTexts.isEmpty()) {
            Toast.makeText(this, R.string.error_correction, Toast.LENGTH_LONG).show();
            return;
        }

        String email = String.valueOf(emailEt.getText()).trim();
        String username = String.valueOf(usernameEt.getText()).trim();
        String password = String.valueOf(passwordEt.getText());
        String fName = String.valueOf(fNameEt.getText()).trim();
        String lName = String.valueOf(lNameEt.getText()).trim();
        String phoneNumber = String.valueOf(phoneNoEt.getText()).trim();
        String proofNumber = String.valueOf(proofNoEt.getText()).trim();
        Calendar dateOfBirth = Calendar.getInstance();
        dateOfBirth.set(selectedYear, selectedMonth, selectedDate);
        String dob = dateOfBirth.toString();
        String wardname = wardNameSp.getSelectedItem().toString();
        String martial_status = martialStatusSp.getSelectedItem().toString();
        String blood_group = bloodGroupSp.getSelectedItem().toString();
        String proof_type = proofTypeSp.getSelectedItem().toString();

        if (!StringHelpers.isValidEmail(email)) {
            emailEt.setError(getString(R.string.error_email_invalid));
            return;
        } else if (username.isEmpty()) {
            usernameEt.setError(getString(R.string.error_username_exists));
            return;
        } else if (password.length() < PASSWORD_MIN_LENGTH || password.length() > PASSWORD_MAX_LENGTH) {
            passwordEt.setError("Password must contain atleast " + PASSWORD_MIN_LENGTH + " and max " + PASSWORD_MAX_LENGTH);
            return;
        } else if (phoneNumber.length() != 10) {
            phoneNoEt.setError("Invalid phone number");
            return;
        } else if (dob.isEmpty()) {
            birthDateEt.setError("Date is mandatory");
            return;
        } else if (wardname.isEmpty()) {
            wardNameSp.setFocusable(true);
            return;
        } else if (martial_status.isEmpty()) {
            martialStatusSp.setFocusable(true);
            return;
        } else if (blood_group.isEmpty()) {
            bloodGroupSp.setFocusable(true);
            return;
        } else if (proof_type.isEmpty()) {
            proofTypeSp.setFocusable(true);
            return;
        }

        /**
         * calling Login server for updating user data
         */

        if (AndroidUtils.isNetworkAvailable(SignUpActivity.this)) {
            //Making Retro Fit call
            setProgressBarIndeterminateVisibility(true);
            // call Network Handler
            postParams.clear();
            postParams.put(getString(R.string.email_), email);
            postParams.put(getString(R.string.username), username);
            postParams.put(getString(R.string.password), password);
            postParams.put(getString(R.string.user_type), "user");
            postParams.put(getString(R.string.fname), fName);
            postParams.put(getString(R.string.lname), lName);
            postParams.put(getString(R.string.ward_name), "2");
            postParams.put(getString(R.string.prompt_martialstatus), martial_status);
            postParams.put(getString(R.string.prompt_bloodgroup), blood_group);
            postParams.put(getString(R.string.prompt_prooftype), "1");
            postParams.put(getString(R.string.phone1), phoneNumber);
            postParams.put(getString(R.string.proofnumber), proofNumber);
            postParams.put(getString(R.string.ward_no1), wardNameSp.getSelectedItem().toString());
            new Signupmanager(activity, Constant.AGENT_REGISTRATION, taskCompleted, retrofit, postParams, false);

        } else {
            AndroidUtils.showSnackBar(getString(R.string.networkError), root);
        }
    }

    @Override
    public void onResponseLanded(int serviceCode, String Response) {
        setProgressBarIndeterminateVisibility(false);
        Toast.makeText(this, Response, Toast.LENGTH_LONG).show();
        /*Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        setResult(LoginActivity.USER_SIGNUP_REQUEST, intent);
        finish();*/
    }

    @Override
    public void onFailureLanded(int serviceCode, String Failure) {
        setProgressBarIndeterminateVisibility(false);

    }
}