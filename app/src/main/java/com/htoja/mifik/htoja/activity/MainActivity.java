package com.htoja.mifik.htoja.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.htoja.mifik.htoja.R;
import com.htoja.mifik.htoja.control.TeamGameManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        if (!TeamGameManager.getInstance().hasStarted()) {
            findViewById(R.id.btContinue).setVisibility(View.GONE);
        }
    }

    public void startSingle(View view) {
        Intent i = new Intent(getApplicationContext(), ResultActivity.class);
        i.putExtra("SHOW_NEXT_TEAM", true);
        startActivity(i);
    }

    public void startTeams(View view) {
        Intent i = new Intent(getApplicationContext(), SetupActivity.class);
        startActivity(i);
    }
}
