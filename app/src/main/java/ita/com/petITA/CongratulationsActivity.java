package ita.com.petITA;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ita.com.petITA.R;

public class CongratulationsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations);
    }
    public void Next(View view){
        Intent next =  new Intent(this, LoginActivity.class);
        startActivity(next);
    }
}