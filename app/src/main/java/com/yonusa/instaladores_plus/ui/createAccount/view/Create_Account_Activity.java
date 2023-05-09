package com.yonusa.instaladores_plus.ui.createAccount.view;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.yonusa.instaladores_plus.R;

public class Create_Account_Activity extends AppCompatActivity implements Privacy_policy_fragment.OnFragmentInteractionListener,
                                                                Create_account_form.OnFragmentInteractionListener,
                                                                Activation_count.OnFragmentInteractionListener{

    private static final String TAG = Create_Account_Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_activity);

        setTitle("Create account Yonusa");

        getSupportActionBar().hide();
        Log.i(TAG, "<========== We are here ");

        showCreateAccountform();
    }

    public void showCreateAccountform(){
        Fragment frgmnt = new Create_account_form();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container,frgmnt);
        transaction.commit();
    }

    public void onFragmentInteraction(Uri uri){ }

    @Override
    public void onBackPressed() {

        //si no queda ningún fragment sale de este activity
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            // super.onBackPressed();
            finish();

        } else { //si no manda al fragment anterior.
            getSupportFragmentManager().popBackStack();
        }
    }

}
