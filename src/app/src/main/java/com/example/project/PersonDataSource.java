package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PersonDataSource {

	// Database fields
	private SQLiteDatabase database;
	private DBHelper dbHelper;

	public PersonDataSource(Context context) {
		dbHelper = new DBHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
		Log.i("PersonDataSource", "open");
	}
	
	public boolean isOpen()
	{
		if (database == null) return false;
		return database.isOpen();
	} 

	public void close() {
		dbHelper.close();
	}

	void addPerson(Person person) {

		database.insert("login", null, setPersonData(person));
		database.close();
	}

	// Getting single Person
	Person getPerson(int id) {
		Cursor cursor = database.query("login", new String[] { "id", "username", "password"}, "id= ?",
				new String[] { String.valueOf(id)}, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Person person = new Person();
		person.setId((int) cursor.getLong(0));
		person.setUsername(cursor.getString(1));
		person.setPassword(cursor.getString(2));
		cursor.close();
		return person;
	}
	boolean checkPerson(String username,String password)
	{
		Cursor cursor = database.query("login", new String[] { "id", "username", "password"}, "username= ? AND password = ?",
				new String[] { username,password}, null, null, null);
		int cursorCount=cursor.getCount();
		cursor.close();
		if(cursorCount>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	boolean checkUsername(String username)
	{
		Cursor cursor = database.query("login", new String[] { "id", "username", "password"}, "username= ?",
				new String[] { username}, null, null, null);
		int cursorCount=cursor.getCount();
		cursor.close();
		if(cursorCount>0)
			return true;
		else
			return false;
	}

	// Updating

	public int updatePerson(Person person) {
		// updating row
		return database.update("login", setPersonData(person)," id = ?",
				new String[] { String.valueOf(person.getId()) });
	}

	// Deleting single Person
	public void deletePerson(Person person) {
		database.delete("login"," id = ?",
				new String[] { String.valueOf(person.getId()) });
		database.close();
	}

	// Getting Person Count
	public List<Person> getAllPersons() {
		List<Person> persons = new ArrayList<Person>();
		String countQuery = "SELECT  * FROM " + "user";
		Cursor cursor = database.rawQuery(countQuery, null);
		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				Person person = new Person();
				person.setId((int) cursor.getLong(0));
				person.setUsername(cursor.getString(1));
				person.setPassword(cursor.getString(2));
				Log.e("user", person.toString());
				persons.add(person);
				cursor.moveToNext();
			}
		}
		cursor.close();

		// return count
		return persons;
	}

	// Getting Person Count
	public int getPersonCount() {
		String countQuery = "SELECT  * FROM " + "login";
		Cursor cursor = database.rawQuery(countQuery, null);
		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}

	private ContentValues setPersonData(Person person) {
		ContentValues values = new ContentValues();
		values.put("id", person.getId());
		values.put("username",person.getUsername());
		values.put("password", person.getPassword());
		return values;
	}
}
