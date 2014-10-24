package com.example.hacku4;

import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



import br.com.dina.ui.model.BasicItem;
import br.com.dina.ui.model.ViewItem;
import br.com.dina.ui.widget.UITableView;
import br.com.dina.ui.widget.UITableView.ClickListener;
import br.com.dina.ui.widget.UITableView.ClickListener1;

import com.actionbarsherlock.app.SherlockActivity;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class jsontree extends SherlockActivity{

	
	public static final String ACTION_CLOSE = "com.example.hacku4.ACTION_CLOSE";
	public static final String ACTION_CLOSE1 = "com.example.hacku4.ACTION_MSG";
	private UITableView mTableView;
	JSONObject json;
	
	ArrayList<Integer> ai;
	JSONArray arr;
	String path;
	ArrayList<Activity> lst;
	private FirstReceiver firstReceiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uitableview_activity);
		Intent intent = getIntent();
		
		
		path=intent.getStringExtra("jsonPath");
		String json1=intent.getStringExtra("json");
		try {
			json=new JSONObject(json1);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// lst=(ArrayList<Activity>) intent.getSerializableExtra("act_array");
		
		//lst.add(this);
		 ai= new ArrayList<Integer>();
		
		 
		 //Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
		 
		getSupportActionBar().setTitle(path);
		
		
		
		
//		
		mTableView =(UITableView) findViewById(R.id.tableView);
	    CustomClickListener listener = new CustomClickListener();
        mTableView.setClickListener(listener);
        aa ll=new aa();
        mTableView.setClickListener1(ll);
		
		
		JSONObject json=new JSONObject();
		try {
			json=new JSONObject(json1);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		arr=json.names();
		
		
		
	     
	     populateList(arr);
	     mTableView.commit();
		
		
	     
	
	     
	     
		
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	     IntentFilter filter = new IntentFilter(ACTION_CLOSE);
	        firstReceiver = new FirstReceiver();
	        registerReceiver(firstReceiver, filter);
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		unregisterReceiver(firstReceiver);
	
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
    		  Intent myIntent = new Intent(jsontree.ACTION_CLOSE);
    	        sendBroadcast(myIntent);
    		
    	}
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	return super.onOptionsItemSelected(item);
    	
    	
    	
    	
    	
    }
	
	
    public class aa implements ClickListener1{

    	@Override
    	public void onClick1(int index) {
    		// TODO Auto-generated method stub
    		
    		Toast.makeText(jsontree.this, "item long clicked: " + index, Toast.LENGTH_SHORT).show();
    		
    		Intent myIntent = new Intent(jsontree.ACTION_CLOSE1);
    		try {
				//myIntent.putExtra("path", "<$"+path+arr.getString(index)+">");
				
				bufferer.className="<$"+path+arr.getString(index)+">";
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        sendBroadcast(myIntent);
    		
    		
    		
    		
    	}
    	  
    	  
      }
        
        
        
        private class CustomClickListener implements ClickListener {

    		@Override
    		public void onClick(int index) {
    			//Toast.makeText(jsontree.this, "item clicked: " + index, Toast.LENGTH_SHORT).show();
    		  
    			
    			 try {
					switch (ai.get(index)) {
					case 1:
						//Toast.makeText(getApplicationContext(), "first", Toast.LENGTH_SHORT).show();
						Intent in=new Intent(getApplicationContext(),jsontree.class);
						
						
						//Toast.makeText(getApplicationContext(),arr.getString(index), Toast.LENGTH_SHORT);
						in.putExtra("jsonPath", path+arr.getString(index)+".");
					    in.putExtra("json",""+json.getJSONObject(arr.getString(index)));
					   // in.putExtra("act_array", lst);
					    
					    
					    startActivity(in);
						break;
					case 2:
						
						//Toast.makeText(getApplicationContext(), "second", Toast.LENGTH_SHORT).show();
					
						Intent in1=new Intent(getApplicationContext(),jsontree.class);
						
						
						//Toast.makeText(getApplicationContext(), arr.getString(index), Toast.LENGTH_SHORT).show();
						in1.putExtra("jsonPath", path+arr.getString(index)+"[0].");
						in1.putExtra("json",""+json.getJSONArray(""+arr.getString(index)).get(0));
						//in1.putExtra("act_array", lst);
						
						startActivity(in1);
						break;
					case 3:
						//Toast.makeText(getApplicationContext(), "Third", Toast.LENGTH_SHORT).show();
					//	json.getJSONObject(""+arr.getString(index));
						break;
					case 4:
						
						//Toast.makeText(getApplicationContext(), "fourth", Toast.LENGTH_SHORT).show();
						//json.getJSONObject(""+arr.getInt(index));
						break;
						
						
						
					default:
						break;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		
    		     
    		
    		}
        	
        }

    	
    	protected void populateList(JSONArray arr) {
    		

    		
    		//Toast.makeText(getApplicationContext(), ""+arr, Toast.LENGTH_SHORT).show();
    		
    		for(int i=0;i<arr.length();i++)
    		{
    			
    			try {
					additemto_table(arr.getString(i),json);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
    			
    			
    		}
    		
    		

    	
//    		
//    		LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    		RelativeLayout view = (RelativeLayout) mInflater.inflate(R.layout.custom_view, null);
//    		ViewItem viewItem = new ViewItem(view);
//    		getUITableView().addViewItem(viewItem);
//    		
//    		RelativeLayout v = (RelativeLayout) mInflater.inflate(R.layout.custom_view2, null);
//    		ViewItem v2 = new ViewItem(v);
//    		v2.setClickable(false);
//    		getUITableView().addViewItem(v2);
    		
    		
    	}


		private void additemto_table(String object,JSONObject json) {
			// decides the type of parameter and gives it a corresponding object
			boolean b=false;
			
			if(!b)
			{
			try {
				json.getJSONObject(object);
			    b=true;
			    ai.add(1);
				mTableView.addBasicItem(R.drawable.user_image, object, "JsonObject");
			    
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
		  if(!b)
		  {
		    
			  
				try {
					json.getJSONArray(object);
				    b=true;
				    ai.add(2);
				    mTableView.addBasicItem(R.drawable.user_image, object, "JsonArray");
				   
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  
		  }
			
			
		  if(!b)
		  {
		    
			  
				try {
					json.getString(object);
				    b=true;
				    ai.add(3);
					LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					RelativeLayout view = (RelativeLayout) mInflater.inflate(R.layout.custom_view, null);
					
					
			        TextView tv=(TextView)view.findViewById(R.id.title);
					 
			        
			        tv.setText(object);
			   
					                      
					ViewItem viewItem = new ViewItem(view);
					viewItem.setClickable(true);
					mTableView.addViewItem(viewItem);		
				    
				    
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  
		  }	
			
		  if(!b)
		  {
		    
			  
				try {
					json.getInt(object);
				    b=true;
				    ai.add(4);
				    
					LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					RelativeLayout view = (RelativeLayout) mInflater.inflate(R.layout.custom_view, null);
					
					
			        TextView tv=(TextView)view.findViewById(R.id.title);
					 
			        
			        tv.setText(object);
			   
					                      
					ViewItem viewItem = new ViewItem(view);
					viewItem.setClickable(true);
					mTableView.addViewItem(viewItem);		
				    
				    
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  
		  }
			
			
		
		
		  
		  
		  
		  
			
			
		}
    
    
    
    
//		LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		RelativeLayout view = (RelativeLayout) mInflater.inflate(R.layout.custom_view, null);
//		
//		
//        TextView tv=(TextView)view.findViewById(R.id.title);
//		 
//        
//        tv.setText(object);
//   
//		                      
//		ViewItem viewItem = new ViewItem(view);
//		mTableView.addViewItem(viewItem);		
		
		
		class FirstReceiver extends BroadcastReceiver {
	        @Override
	        public void onReceive(Context context, Intent intent) {
	            Log.e("FirstReceiver", "FirstReceiver");
	            if (intent.getAction().equals(ACTION_CLOSE)) {
	                jsontree.this.finish();
	            }
	        }
	    }		
	
	
	
}
