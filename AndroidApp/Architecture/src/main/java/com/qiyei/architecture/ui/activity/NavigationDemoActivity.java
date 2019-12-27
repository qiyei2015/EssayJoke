package com.qiyei.architecture.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavAction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.View;

import com.qiyei.architecture.R;
import com.qiyei.sdk.log.LogManager;

public class NavigationDemoActivity extends AppCompatActivity{

    private static final String TAG = "NavigationDemoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_demo);

        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);

        findViewById(R.id.page1_rb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogManager.i(TAG,"btn1=" + navController.getCurrentDestination().getId());

                int id = navController.getCurrentDestination().getId();
                if (id == R.id.navigationPage2Fragment){
                    navController.navigate(R.id.action_navigationPage2Fragment_to_navigationPage1Fragment);
                } else if (id == R.id.navigationPage3Fragment){
                    navController.navigate(R.id.action_navigationPage3Fragment_to_navigationPage1Fragment);
                }
            }
        });

        findViewById(R.id.page2_rb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogManager.i(TAG,"btn2=" + navController.getCurrentDestination().getId());

                int id = navController.getCurrentDestination().getId();
                if (id == R.id.navigationPage1Fragment){
                    navController.navigate(R.id.action_navigationPage1Fragment_to_navigationPage2Fragment);
                } else if (id == R.id.navigationPage3Fragment){
                    navController.navigate(R.id.action_navigationPage3Fragment_to_navigationPage2Fragment);
                }
            }
        });

        findViewById(R.id.page3_rb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogManager.i(TAG,"btn3=" + navController.getCurrentDestination().getId());

                int id = navController.getCurrentDestination().getId();
                if (id == R.id.navigationPage1Fragment){
                    navController.navigate(R.id.action_navigationPage1Fragment_to_navigationPage3Fragment);
                } else if (id == R.id.navigationPage2Fragment){
                    navController.navigate(R.id.action_navigationPage2Fragment_to_navigationPage3Fragment);
                }
            }
        });
    }
}
