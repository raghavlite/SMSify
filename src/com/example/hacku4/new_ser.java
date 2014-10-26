package com.example.hacku4;



import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.ParseException;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.example.hacku21.JSONParser;
import com.example.hacku4.jsontree.FirstReceiver;



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class new_ser extends SherlockActivity {

	
	
	
	private ProgressDialog pDialog;
	
	//protected static final String srv = null;
	public int as=0;
	Service1 srv;
	FirstReceiver1 firstReceiver;
	int colour=Integer.parseInt("d6d6d6", 16);
	Context cntxt;
     EditText ser_name;
	 EditText ser_url, ser_code, ser_values,ser_wantedv,ser_type,ser_exurl;
	ImageView iv;
	ImageView iv2;
	ImageView iv3;
	ImageView iv4;
	TextView type_view;
	String[] names={"Json","Xml","Rss"};
	String Slected;
	databasehandler dbh;
	ArrayList<String> keys;
	public static final String ACTION_CLOSE = "com.example.hacku4.ACTION_MSG";
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);

    setContentView(R.layout.m1);
    as=0;
    
    getSupportActionBar().setTitle("New Service");
   
    
    cntxt=getApplicationContext();
    Slected="Json";
    
    dbh=new databasehandler(cntxt);
    keys=dbh.getall_keys();



    
 type_view=(TextView)findViewById(R.id.Type1);
 iv=(ImageView)findViewById(R.id.Dropper_type);
 iv2=(ImageView)findViewById(R.id.Dropper_image);
 iv3=(ImageView)findViewById(R.id.avail_check);
 iv4=(ImageView)findViewById(R.id.browse_json);
 
 
 Set_icon(0);
 iv.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		
		setup_dropdown();
	}

	
});
 
 

 
 
 
    ser_name=(EditText)findViewById(R.id.ser_name);
     ser_url = (EditText)findViewById(R.id.ser_url);
    ser_code=(EditText)findViewById(R.id.ser_key);
    //ser_values=(EditText)findViewById(R.id.ser_values);
    ser_wantedv=(EditText)findViewById(R.id.ser_wantedv);
    //ser_type=(EditText)findViewById(R.id.ser_type);
     ser_exurl=(EditText)findViewById(R.id.ser_exurl);
    
    
     
     IntentFilter filter = new IntentFilter(ACTION_CLOSE);
     firstReceiver = new FirstReceiver1();
     registerReceiver(firstReceiver, filter);
     
     
    iv3.setOnClickListener(new OnClickListener() {
   	
   	@Override
   	public void onClick(View v) {
   		// TODO Auto-generated method stub
   		
   		if(keys.contains(ser_code.getText().toString()))
   		{
   			Toast.makeText(cntxt, "Keyword already taken", Toast.LENGTH_SHORT).show();
   			
   		}
   		
   		else
   		{
   			Toast.makeText(cntxt, "Keyword available", Toast.LENGTH_SHORT).show();
   			
   		}
   		
   	}
   });
    
    iv4.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			
			/////check for intternet connection and intimate
			
			new localtaske().execute(ser_exurl.getText().toString());
			
			
		}
	});
    
    
    
    
    
    
    Intent inw=getIntent();
    if(inw.getBooleanExtra("update", false))
    {as=1;
    	
    	
    	srv=ManagerService.ar.get(inw.getIntExtra("pos", 0));
    	
    	
    	ser_name.setText(srv.getName());
    	ser_url.setText(srv.getUrl());
    	ser_code.setText(srv.getCode());
    	//ser_values.setText(srv.getValues());
    	ser_wantedv.setText(srv.getWantedv());
    	//ser_type.setText(srv.getType());
    	
    	
    	
    	
    	
    }
    else
    {
    	as=0;
    	
    }
   
    
    
    
    
  
    
    
}

class FirstReceiver1 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("FirstReceiver", "FirstReceiver");
        if (intent.getAction().equals(ACTION_CLOSE)) {
           
        	ser_wantedv.setText(ser_wantedv.getText().toString()+bufferer.className);
        	bufferer.className="";
        	
        }
    }
}	




private class localtaske extends AsyncTask<String, Void, Intent>
{
@Override
protected void onPreExecute() {
	// TODO Auto-generated method stub
	super.onPreExecute();
	
	
	
	pDialog = new ProgressDialog(new_ser.this);
	pDialog.setMessage("Please wait...");
	pDialog.setCancelable(false);
	pDialog.show();
	
	
	
}


	@Override
	protected Intent doInBackground(String... params) {
		// TODO Auto-generated method stub
		JSONParser jParser = new JSONParser();
//		//
		String json = null;
		
		Intent in=new Intent(getApplicationContext(), jsontree.class);
		
		try {
			
			String url=params[0];
		
			
			
			
			
			
			
			if(Slected.contentEquals("Xml"))
			{
			url="http://query.yahooapis.com/v1/public/yql?q="+"select*from%20xml%20where%20url='"+url+"'&format=json";
			}
			
			else if(Slected.contentEquals("Rss"))
			{
				url="http://query.yahooapis.com/v1/public/yql?q="+"select*from%20feed%20where%20url='"+url+"'&format=json";
			}
			else if(Slected.contentEquals("Json"))
			{
				url="http://query.yahooapis.com/v1/public/yql?q="+"select*from%20json%20where%20url='"+url+"'&format=json";
			
				Log.i("IN json", url);
				
			}
			json = jParser.getJSONFromUrl(url);
		      //json=jParser.getJSONFromUrl("http://query.yahooapis.com/v1/public/yql?q="+"select*from%20xml%20where%20url='"+url+"'&format=json");
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			in.putExtra("parse", false);
			
		} catch (IOException e) {
			// TODO Auto-ge-nerated catch block
			in.putExtra("ex_url", false);
			
			e.printStackTrace();
		}
		
		
		
		in.putExtra("jsonPath", ".");
		in.putExtra("json", json);
		
		
		
		return in;
	}

	@Override
		protected void onPostExecute(Intent in) {
			// TODO Auto-generated method stub
			super.onPostExecute(in);
			
			if (pDialog.isShowing())
				pDialog.dismiss();
			
			if(in.getBooleanExtra("parse", true))
			{
				Toast.makeText(getApplicationContext(), "Select correct Type", Toast.LENGTH_SHORT).show();
			}
			if(in.getBooleanExtra("ex_url", true))
			{
				Toast.makeText(getApplicationContext(), "Enter proper Url", Toast.LENGTH_SHORT).show();
			}
			
			
			startActivity(in);
			
		}

	
	

}






@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();

	
unregisterReceiver(firstReceiver);

}
		
private void setup_dropdown() {
	// TODO Auto-generated method stub

	

	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	builder.setTitle("Select Source");
	builder.setItems(names, new DialogInterface.OnClickListener() {
	    public void onClick(DialogInterface dialog, int item) {
	       switch (item) {
		case 0:
			Slected="Json";
			break;
        case 1:
        	Slected="Xml";
			break;
        case 2:
        	Slected="Rss";
			break;
		default:
			break;
		}
	       
	       
	       Set_icon(item);
	    }
	});
	AlertDialog alert = builder.create();
	alert.show();
	
	
	
}


void Set_icon(int a)
{
	
switch (a) {
case 0:
	iv2.setImageResource(R.drawable.json);
	type_view.setText("Json");
	break;

	case 1:
		iv2.setImageResource(R.drawable.xml);
		type_view.setText("Xml");
		break;
	case 2:
		iv2.setImageResource(R.drawable.rss);
		type_view.setText("Rss");
		break;
default:
	break;
}


}

@Override
public boolean onCreateOptionsMenu(Menu menu) {
    //Used to put dark icons on light action bar
  
	  menu.add("Close")
      // .setIcon(isLight ? R.drawable.ic_refresh_inverse : R.drawable.ic_refresh)
       .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
   

    menu.add("Done")
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

  

    return true;
}


@Override
public boolean onOptionsItemSelected(MenuItem item) {
	// TODO Auto-generated method stub
	
	if(item.getTitle()=="Done")
	{
		
		
		Service1 sr;
		
		
		if(ser_url.getText().toString()=="")
				{
		Toast.makeText(getApplicationContext(), "Enter Proper Url", Toast.LENGTH_SHORT).show();
		
		return false;
				}
		
		
		
		
		sr=new Service1(ser_name.getText().toString(), ser_code.getText().toString(),
				ser_url.getText().toString(),
				StringUtils.countMatches(ser_wantedv.getText().toString()," ")+"",
				ser_wantedv.getText().toString(),Slected);
		
	Log.d("editt", StringUtils.countMatches(ser_wantedv.getText().toString()," ")+"");	
		
		
	if(as==0)
	{
		
		if(keys.contains(ser_code.getText().toString()))
		{
Toast.makeText(getApplicationContext(), "Key already exists", Toast.LENGTH_SHORT).show();

return false;
		}
		
		dbh.addservice(sr);
		ManagerService.Used.add(0);
	}
	else
	{
		
		
	    dbh.upgradeservice(srv.getId(), sr);	
	}
	
	
	
	
    Intent in2 =new Intent(getApplicationContext(), ManagerService.class);
	
    
   // stopService(in2);
    in2.putExtra("switchon", true);
	startService(in2);
	
	
	
	Intent imk=new Intent(getApplicationContext(), MainActivity.class);
	finish();
	//startActivity(imk);
	Toast.makeText(getApplicationContext(), "added to database", Toast.LENGTH_SHORT).show();
	









		
	}
	
	
	
	else if(item.getTitle()=="Close")
	{
		
	
	
	
	
	}
	
	
	
	
	
	
	
	
	
	return super.onOptionsItemSelected(item);
	
	
	
	
	
}






}
