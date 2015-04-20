package example.addressbook;

import example.addressboook.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayContact extends Activity {

	int from_Where_I_Am_Coming = 0;
	private DBHelper mydb;
	TextView name;
	TextView phone;
	TextView street;
	TextView email;
	TextView city;
	int id_To_Update=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_contact);
		
		name=(TextView)findViewById(R.id.textName);
		phone=(TextView)findViewById(R.id.textphone);
		street=(TextView)findViewById(R.id.textStreet);
		email=(TextView)findViewById(R.id.textEmail);
		city=(TextView)findViewById(R.id.textCity);
		
		mydb=new DBHelper(this);
		
		Bundle extras=getIntent().getExtras();
		if(extras!=null)
		{
			int value=extras.getInt("id");
			if(value>0)
			{
				//this is the view part not the add contact part
				Cursor rs=mydb.getData(value);
				id_To_Update=value;
				rs.moveToNext();
				String nam=rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
				String phn=rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
				String str=rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_STREET));
				String ema=rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMAIL));
				String cty=rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_CITY));
				
				if(!rs.isClosed())
				{
					rs.close();
				}
				
				Button b=(Button)findViewById(R.id.button1);
				b.setVisibility(View.INVISIBLE);
				
				name.setText((CharSequence)nam);
				name.setFocusable(false);
				name.setClickable(false);
				
				phone.setText((CharSequence)phn);
				phone.setFocusable(false);
				phone.setClickable(false);
				
				street.setText((CharSequence)str);
				street.setFocusable(false);
				street.setClickable(false);
				
				email.setText((CharSequence)ema);
				email.setFocusable(false);
				email.setClickable(false);
				
				city.setText((CharSequence)cty);
				city.setFocusable(false);
				city.setClickable(false);
				
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 // Inflate the menu; this adds items to the action bar if it is present.
	      Bundle extras = getIntent().getExtras(); 
	      if(extras !=null)
	      {
	         int Value = extras.getInt("id");
	         if(Value>0){
	            getMenuInflater().inflate(R.menu.display_contact, menu);
	         }
	         else{
	            getMenuInflater().inflate(R.menu.main, menu);
	         }
	      }
	      return true;
	   }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
		
		case R.id.Edit_Contact:
		Button b=(Button)findViewById(R.id.button1);
		 b.setVisibility(View.VISIBLE);
	      name.setEnabled(true);
	      name.setFocusableInTouchMode(true);
	      name.setClickable(true);

	      phone.setEnabled(true);
	      phone.setFocusableInTouchMode(true);
	      phone.setClickable(true);

	      email.setEnabled(true);
	      email.setFocusableInTouchMode(true);
	      email.setClickable(true);

	      street.setEnabled(true);
	      street.setFocusableInTouchMode(true);
	      street.setClickable(true);

	      city.setEnabled(true);
	      city.setFocusableInTouchMode(true);
	      city.setClickable(true);

	      return true; 
	      case R.id.Delete_Contact:

	      AlertDialog.Builder builder = new AlertDialog.Builder(this);
	      builder.setMessage(R.string.deleteContact).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog, int id) {
	            mydb.deleteContact(id_To_Update);
	            Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();  
	            Intent intent = new Intent(getApplicationContext(),example.addressbook.MainActivity.class);
	            startActivity(intent);
	         }
	      }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO Auto-generated method stub
				
			}
		});
	      
	      AlertDialog d=builder.create();
	      d.setTitle("Are you sure");
	      d.show();
	      
	      return true;
	      default:
	    	  return super.onOptionsItemSelected(item);
	      
		
		}
		
	}
	
	public void run(View view)
	   {	
	      Bundle extras = getIntent().getExtras();
	      if(extras !=null)
	      {
	         int Value = extras.getInt("id");
	         if(Value>0){
	            if(mydb.updateContact(id_To_Update,name.getText().toString(), phone.getText().toString(), email.getText().toString(), street.getText().toString(), city.getText().toString()))
	            {
	               Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();	
	               Intent intent = new Intent(getApplicationContext(),example.addressbook.MainActivity.class);
	               startActivity(intent);
	             }		
	            else{
	               Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();	
	            }
			 }
	         else{
	            if(mydb.insertContact(name.getText().toString(), phone.getText().toString(), email.getText().toString(), street.getText().toString(), city.getText().toString())){
	               Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
	               
	            }		
	            else{
	               Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();	
	            }
	            
	            Intent intent = new Intent(getApplicationContext(),example.addressbook.MainActivity.class);
		           startActivity(intent);
		           
	            }
	      }
	   }
	}
	
