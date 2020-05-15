package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    PersonDataSource personDataSource;
    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        personDataSource = new PersonDataSource(this);
        if (!personDataSource.isOpen()) personDataSource.open();
        person=new Person();
        if(personDataSource.getPersonCount()==0)
        {
            Intent intent = new Intent(getApplicationContext(), Register.class);
            startActivity(intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        showToast("redirecting to register page");
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
        return true;
    }

    private void showToast (String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!personDataSource.isOpen()) personDataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void login(View view) {

        if(personDataSource.checkPerson(username.getText().toString(),password.getText().toString())) {
            person.setUsername(username.getText().toString());
            person.setPassword(password.getText().toString());
            Intent intent = new Intent(this, CityList.class);
            startActivity(intent);
        }
        else
            showToast("Login failed!!!!");
    }
}
