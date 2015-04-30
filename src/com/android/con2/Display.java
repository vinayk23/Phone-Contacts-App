package com.android.con2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class Display extends Activity {

	DatabaseHandler db=new DatabaseHandler(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display);
		
		String val= getIntent().getExtras().getString("name");  
		String val2= getIntent().getExtras().getString("no");  
		 TextView namefield= (TextView) findViewById(R.id.contactNameTextView);
		 TextView mobilefield= (TextView) findViewById(R.id.contactPhoneTextView);
		 TextView emailfield= (TextView) findViewById(R.id.contactEmailTextView);
		 namefield.setText(val);
		 
		 Contact c=db.getContact(val,val2);
		 db.close();
		 String mobile=c.getPhoneNumber();
		 
		 String email=c.getEmail();
		 
		mobilefield.setText(mobile);
		emailfield.setText(email);
		
		
	}
	
	
	public boolean onCreateOptionsMenu(Menu menu) 
	{ 
	    MenuInflater menuInflater = getMenuInflater(); 
	    menuInflater.inflate(R.layout.delete, menu); 
	    return true; 
	} 

	public boolean onOptionsItemSelected(MenuItem item) 
	{ 

	    if(item.getItemId()==R.id.menu_deletecontact)
	    {
	    	String val= getIntent().getExtras().getString("name");
	    	String val2= getIntent().getExtras().getString("no");
	    	Contact c=db.getContact(val,val2);
	    	db.deleteContact(c);
	    	db.close();
	    	Toast.makeText(getApplicationContext(),"Contact deleted",
	    	          Toast.LENGTH_SHORT).show();
	    	finish();
			return true;
	    }
	    
	    return super.onOptionsItemSelected(item); 

	} 
}

