package br.com.dhsoftware.workerday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.io.Serializable;

import br.com.dhsoftware.workerday.model.User;

public class MainActivity extends AppCompatActivity implements Serializable, View.OnClickListener {

    ImageButton buttonAddRegistry;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();


        user = new User("Douglas", "douglas@hotmail.com");
    }


    public void setView(){
        buttonAddRegistry = findViewById(R.id.button_add_registry);
        buttonAddRegistry.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_add_registry:
                startActivityAddRegistry(user);
                break;
        }
    }


    public void startActivityAddRegistry(User user){
        Intent intent = new Intent(getApplicationContext(), AddRegistryActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
