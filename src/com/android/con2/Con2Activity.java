package com.android.con2;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Con2Activity extends ListActivity {

	DatabaseHandler db = new DatabaseHandler(this);
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);
		
		List<String> contacts = db.getAllContacts();
		db.close();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_list_item_1,
				contacts);
		
		setListAdapter(adapter);

		ListView lv = getListView();
		registerForContextMenu(lv);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String details = ((TextView) view).getText().toString();
				String temp[] = details.split("\n");

				// String cname=((TextView) view).getText().toString();
				Intent disp = new Intent(getApplicationContext(),
						com.android.con2.Display.class);
				disp.putExtra("name", temp[0]);
				disp.putExtra("no", temp[1]);
				startActivity(disp);
			}
		});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		List<String> contacts = db.getAllContacts();
		db.close();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_list_item_1,
				contacts);

		setListAdapter(adapter);

		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String details = ((TextView) view).getText().toString();
				String temp[] = details.split("\n");

				// String cname=((TextView) view).getText().toString();
				Intent disp = new Intent(getApplicationContext(),
						com.android.con2.Display.class);
				disp.putExtra("name", temp[0]);
				disp.putExtra("no", temp[1]);
				startActivity(disp);
			}
		});

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int p = info.position;
		List<String> temp = db.getAllContacts();
		db.close();
		String details = temp.get(p);
		String t[] = details.split("\n");

		Log.d("details", t[0] + " " + t[1]);
		switch (item.getItemId()) {
		case R.id.menu_delete:
			Contact c = db.getContact(t[0], t[1]);
			db.deleteContact(c);
			List<String> contacts = db.getAllContacts();
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getApplicationContext(),
					android.R.layout.simple_list_item_1, contacts);

			setListAdapter(adapter);
			db.close();
			return true;
		}
		return false;
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.menu_addcontact) {
			Intent add = new Intent(this, com.android.con2.ContactAdder.class);
			startActivity(add);
			return true;
		}

		return super.onOptionsItemSelected(item);

	}

}