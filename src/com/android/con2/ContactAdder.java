package com.android.con2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;


public class ContactAdder extends Activity {

	private EditText nameText,mobileText,emailText;
	private Button submitButton;
	DatabaseHandler db= new DatabaseHandler(this);
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_adder);
		
		nameText = (EditText) findViewById(R.id.contactNameEditText);
		mobileText  = (EditText) findViewById(R.id.contactPhoneEditText);
		emailText 	= (EditText) findViewById(R.id.contactEmailEditText);
		submitButton = (Button) findViewById(R.id.contactSaveButton);
		nameText.setSingleLine(true);
		mobileText.setSingleLine(true);
		emailText.setSingleLine(true);
		ImageButton add=(ImageButton)findViewById(R.id.ImageButton01);
		
		
		final TableRow tr=(TableRow)findViewById(R.id.table_row);
		
		
		add.setOnClickListener(new OnClickListener() {
			int i=4;
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final TableLayout layout=(TableLayout)findViewById(R.id.table_layout);
				
				  
				/* final TableRow tr = new TableRow(ContactAdder.this);
				 LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);   
				 tr.setLayoutParams(lp);
				 EditText number=new EditText(ContactAdder.this);
				 ImageButton remove=new ImageButton(ContactAdder.this);
				 number.setInputType(InputType.TYPE_CLASS_PHONE);
				 InputFilter maxLengthFilter = new InputFilter.LengthFilter(10);
				 number.setFilters(new InputFilter[]{ maxLengthFilter });
				
				 remove.setImageResource(R.drawable.remove);
				 number.setWidth(400);
				 tr.addView(number);
				 tr.addView(remove);
				 layout.addView(tr,i);
				 i++;
				 
				 remove.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						tr.removeAllViews();
					
						
					}
				});
				 */
				
				
				//LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
				final View t = getLayoutInflater().inflate(R.layout.table, null);
				ImageButton remove=(ImageButton)t.findViewById(R.id.ImageButton02);
				remove.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
								layout.removeView(t);
								i--;
						
					}
				});
				
				layout.addView(t,i);
				i++;
				
				
				
				
			}
		});
		
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create(); 

		// Setting Dialog Title 
		alertDialog.setTitle("Alert Dialog"); 

		

		

		// Setting OK Button 
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() { 
		    public void onClick(DialogInterface dialog, int which) { 
		    } 
		}); 
		submitButton.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Log.d("aa",nameText.getText().toString());
				
				Pattern p=Pattern.compile("[a-zA-Z]*[0-9]*@[a-zA-Z]+\\.[a-zA-Z]*"); 
				Matcher m=p.matcher(emailText.getText().toString());
				boolean b=m.matches();

				Pattern p2=Pattern.compile("[0-9]*"); 
				Matcher m2=p2.matcher(mobileText.getText().toString());
				boolean b2=m2.matches();
				boolean e=nameText.getText().toString().equals("")||mobileText.getText().toString().equals("")||emailText.getText().toString().equals

("");
				boolean l=mobileText.getText().toString().length()==10;
				if(!e&&b==true&&b2==true&&l==true)
				{
					
					Contact c=new Contact(nameText.getText().toString().trim(),mobileText.getText().toString().trim(),emailText.getText().toString

().trim());
					db.addContact(c);
					db.close();
					Toast.makeText(getApplicationContext(),"Contacts added",
			    	          Toast.LENGTH_SHORT).show();
					
					finish();
					
				}
				else
				{
					if(nameText.getText().toString().equals("")||mobileText.getText().toString().equals("")||emailText.getText().toString().equals

(""))
					{
						 
						alertDialog.setMessage("Empty Field"); 
					        // Showing Alert Message 
					        alertDialog.show();

					}
					if(b==false&&!e)
					{
						alertDialog.setMessage("Invalid Email"); 
				        // Showing Alert Message 
				        alertDialog.show();
				        emailText.requestFocus();
					}
					if((b2==false||l==false)&&!e)
					{
						alertDialog.setMessage("Invalid phone number"); 
				        // Showing Alert Message 
				        alertDialog.show();
				        mobileText.requestFocus();
					}
					
				}
				
				
				}
		});
		
		
	}

}