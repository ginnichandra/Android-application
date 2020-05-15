package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    EditText username;
    EditText password;
    Button save;
    Boolean editPage = false;
    PersonDataSource personDataSource;
    Person person;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        save = findViewById(R.id.save);
        personDataSource = new PersonDataSource(this);
        if (!personDataSource.isOpen()) personDataSource.open();

        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 0);
        Log.e("POS", ""+ id);
        if (id > 0 ) {
            editPage = true;
            invalidateOptionsMenu();
            person = personDataSource.getPerson(id);
            username.setText(person.getUsername());
            password.setText(person.getPassword());
        } else {
            person = new Person();
        }
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



    public void save(View view) {
        person.setUsername(username.getText().toString());
        person.setPassword(password.getText().toString());

        if (!editPage) {
            person.setId(personDataSource.getPersonCount() + 1);
            personDataSource.addPerson(person);
        } else {
            personDataSource.updatePerson(person);
        }
        finish();
    }
}
