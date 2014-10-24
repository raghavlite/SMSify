package com.example.hacku4;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.ParseException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;


import com.example.hacku21.CardItemData;
import com.example.hacku21.JSONParser;
import com.example.hacku21.SAXXMLParser;
import com.example.hacku21.XMLParser;
import com.example.hacku21.XMLPullParser;
import com.example.hacku21.newsparser;


import com.jayway.jsonpath.JsonPath;


import android.R.integer;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class ManagerService extends Service {

	static int a=0;
	
	static String URL ="http://weather.yahooapis.com/forecastrss?w=2442047&u=c";
	static String URL1="news.yahoo.com/rss/";
	static String getter="http://";
	String msg,phoneno;
	//ArrayList<mesgwebrqster> runingasynctasks;
	int i,ser_no,xx;
	
	databasehandler db;
	public static ArrayList<Service1> ar;
	String msgd;
	ArrayList<String> astrrk;
	
	ArrayList<String> Name;
	ArrayList<String> Key;
	public static ArrayList<Integer> Used;
	SharedPreferences prefs ;
	
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		
		Toast.makeText(getApplicationContext(), "in on create", Toast.LENGTH_SHORT).show();
		
		 prefs = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
		
		 String arrayString=prefs.getString("Used", "false");
		 
//		 if(!arrayString.contentEquals("false"))
//		 { Gson gson = new Gson();
//		Used = gson.fromJson(arrayString, ArrayList.class);
//		 }
//		
		
		
	}

	
	

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
	
//		
//		
		if(intent.getBooleanExtra("switchon", false)==true)
		{
			
			i=1;
			
			Name=new ArrayList<String>();
			Key=new ArrayList<String>();
			Used=new ArrayList<Integer>();
			
			db=new databasehandler(getApplicationContext());
					
			ar=db.getallservices();
			
			
			MainActivity.adapter.clear(true);
			
			for(xx=0;xx<ar.size();xx++)
			{
				
				Name.add(ar.get(xx).getName());
				Key.add(ar.get(xx).getCode());
				
				Used.add(0);
				
				CardItemData data = new CardItemData(Name.get(xx),"Usage : "+ Used.get(xx), "Item " + i + " Line 3");
	           	MainActivity.adapter.addItem(data, false);
				
				
				
			}
			
//			for (int i = 0; i < 50; i++)
//				{
//					CardItemData data = new CardItemData("Item " + i + " Line 1", "Item " + i + " Line 2", "Item " + i + " Line 3");
//					adapter.addItem(data, false);
//				}
			
			
			Log.i("STATUS", "in on create");
			
			
			
			
			
			
			
			
			
			
			
			
			a=1;
			Log.d("debugging", "inside if true");
			
		}
		
		
		else
		{
			
			Log.d("STATUS", "inside else");
			
			
		msg=intent.getStringExtra("msg");

		phoneno=intent.getStringExtra("sender");
		
		ser_no=intent.getIntExtra("serv_no", -1);
	
		Log.i("SERV_NO", String.valueOf(ser_no));
		
//	
		
		
		if(ser_no>=0)
		{
   
		Service1 sv=ar.get(ser_no);
//		
		
		
		
		Used.set(ser_no, Used.get(ser_no)+1);
		MainActivity.adapter.Update_item(new CardItemData(Name.get(ser_no), "Used : "+Used.get(ser_no), ""), ser_no);
		CardItemData data = new CardItemData(Name.get(ser_no),"Usage : "+ Used.get(ser_no), "Item " + i + " Line 3");
		MainActivity.adapter.Update_item(data, ser_no);
		LocationsAsyncTask2 mn=new LocationsAsyncTask2();
		mn.execute(msg,phoneno,sv.getUrl(),sv.getValues(),sv.getWantedv(),sv.getType(),sv.getName());
		//mn.execute("GETNEWS ","9651424499","api.androidhive.info/pizza/?format=xml","","raghav is <query.count>  lat <query.results.menu.item[0].name> ");
		}
			
		else
		{
			
			
			
			String msgd=intent.getStringExtra("msg");
			
			if(msgd.startsWith("SENDMAIL"))
			{
				astrrk=new ArrayList<String>();
				
				extractfronarrows(msgd);
				
				 Mail m = new Mail(astrrk.get(1), astrrk.get(2)); 

				    String[] toArr = {astrrk.get(0)};
				    m.setTo(toArr);
				    m.setFrom(astrrk.get(1)); 
				    m.setSubject("SUB"); 
				   
				    
				    try
				    {m.setBody(msgd.substring(msgd.lastIndexOf('>')+1)); 
				    }
				    catch(IndexOutOfBoundsException ex)
				    {
				    	
				    	m.setBody("");
				    	
				    }
				    
				    
				    
				    try {
						m.send();
						Toast.makeText(getApplicationContext(), "sent", Toast.LENGTH_SHORT).show();
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						
						Toast.makeText(getApplicationContext(), "not sent", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}
				
			}
			
			
			
			
		}
		
		} 
		
//		
		return START_STICKY;
	}
	
	
	
	void extractfronarrows(String msge)
	{
		
		
		int r=0;
		
		
		r=msge.indexOf('<');
		for(;r!=-1;) 
		{
			
			astrrk.add(msge.substring(msge.indexOf('<')+1, msge.indexOf('>')));
			
			
			
			Log.v("TAGS",msge.substring(msge.indexOf('<')+1, msge.indexOf('>')) );
			
			
			if(msge.length()>msge.indexOf('>')+1)
			{
			msge=msge.substring(msge.indexOf('>')+1);
			
			
		r=msge.indexOf('<');
			}
			
			else
			{
				r=-1;
			}
		}
		

		
		
	}
	
	
	
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	
	i=0;
	a=0;
	
	
	
	//Gson gson = new Gson();
	// This can be any object. Does not have to be an arraylist.
//	String jsonrr = gson.toJson(Used);
	
//	Editor editor = prefs.edit();
//    editor.putString("Used", jsonrr);
//    editor.commit();
	
	
	Log.d("status", "ondestroyed");
	
	
	}

	
	
	public void sendsms(String phoneNo,String sms) {
		
		
		
		
		
		
		Toast.makeText(getApplicationContext(), "Phone : "+phoneNo+",Sms  : "+sms, Toast.LENGTH_SHORT).show();
		
//		SmsManager smsManager = SmsManager.getDefault();
//		smsManager.sendTextMessage(phoneNo, null, sms, null, null);
		
	}
	
	
	public void logd(String ra) {
		
		Log.d("log msg", ra);
		
	}
	
	
	
	
	
		
	

	
	private class LocationsAsyncTask2 extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;
		String ph_no,msg,url,val,wanv,type,name23,urly;
		ArrayList<String> astr;
		ArrayList<String> values23;
		int no_of_spaces;
		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			
			Log.d("new asynctask", "pre exe 1");
			
//			pDialog= new ProgressDialog(SAXActivity.this);
//			pDialog.setMessage("Please Wait...");
//			pDialog.setCancelable(false);
//			
//			pDialog.show();
			
			
			values23=new ArrayList<String>();
            astr=new ArrayList<String>();
            
            urly="";
            
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			int i7;
			msg=params[0];
			ph_no=params[1];
			url=params[2];
			val=params[3];
			wanv=params[4];
			type=params[5];
			name23=params[6];
			
			ArrayList<String> values1;
			String url_cop;
			
			
			
			extractvalues(val);
			extractbraced(msg);
//			if(values23.size()!=astr.size())
//			{
//				
//				
//				return "Your message dosent match the specified syntax";
//			}
			
			if(no_of_spaces!=astr.size())
			{
				
				return "Your message dosent match the specified syntax";
				
			}
		
			
			
			
			
			if(!(type.contentEquals("yahoo")))
			{
				Log.d("inside main", "main"+type);
				
				
				
				
			
//			if(values23.size()>0)
//			{
//			
//				url="?"+url+values23.get(0)+"="+astr.get(0);
//				
//			for(i7=1;i7<values23.size();i7++)
//			{
//				
//				url=url+"&"+values23.get(i7)+"="+astr.get(i7);
//				
//				
//			}
//			
//			}
				
				
				if(no_of_spaces>0)
				{
					url_cop=url;
					
					url="";
					
					
					for(i7=0;i7<no_of_spaces&&url_cop.indexOf("")>-1;i7++)
					{
					
					url=url+url_cop.substring(0, url_cop.indexOf(" "));
					url=url+astr.get(i7);
					
					
					try {
						url_cop=url_cop.substring(url_cop.indexOf(" ")+1);
						
					} catch (IndexOutOfBoundsException e) {
						// TODO: handle exception
						
						break;
					}
					
					
					}
					
					
					
					
					
				}
				
				
			
			Log.i("raghav", url);
			
			
			if(type.contentEquals("Xml"))
			{
			url="http://query.yahooapis.com/v1/public/yql?q="+"select*from%20xml%20where%20url='"+url+"'&format=json";
			}
			
			else if(type.contentEquals("Rss"))
			{
				url="http://query.yahooapis.com/v1/public/yql?q="+"select*from%20feed%20where%20url='"+url+"'&format=json";
			}
			else if(type.contentEquals("Json"))
			{
				url="http://query.yahooapis.com/v1/public/yql?q="+"select*from%20json%20where%20url='"+url+"'&format=json";
			
				Log.i("IN json", url);
				
			}
			
			}
			
			else
			{
				
				if(values23.size()>0)
				{
					urly=(values23.get(0).substring(0, values23.get(0).indexOf('<'))).trim();
					
					if(values23.get(0).contains("string"))
					{
						urly=urly+"='"+astr.get(0)+"'%20";
					}
					else
					{
						urly=urly+"="+astr.get(0)+"%20";
					}
					
					
					
					for(i7=1;i7<values23.size();i7++)
					{
						
						urly=urly+"and%20"+(values23.get(i7).substring(0,values23.get(i7).indexOf('<')));
						
					    
						
						if(values23.get(i7).contains("string"))
						{
							urly=urly+"='"+astr.get(i7)+"'%20";
						}
						else
						{
							urly=urly+"="+astr.get(i7)+"%20";
						}
						
						
						
					}
					
					
					
					
					
					
					
				}
				
				
				
				
				
				url="http://query.yahooapis.com/v1/public/yql?q="+"select*from%20"+name23.trim()+"%20where%20"+urly+"&format=json";
				
				
				
			}
			
			
			Log.v("URL", url);
			
			Log.i("INFO", "Strted json reading");
			
			JSONParser jParser = new JSONParser();
//			//
			String json = null;
			try {
				json = jParser.getJSONFromUrl(url);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			String resul9;
			
resul9=get_specificvaluesfromjson(json, wanv);			

			
//			Log.d("After parse",url);
//			Log.i("after parse", "get specific values from json");

			
	
			
			
			//Log.d("note parse", get_specificvaluesfromjson(null, wanv));
			
			//return resul9;
			
			
			
			return resul9;
		}
		
		
		
		
		
		private String get_specificvaluesfromjson(String json,String wanv) {
			// TODO Auto-generated method stub
		
			int red;
			String res98="",jreq;
			
			
			
			   red=wanv.indexOf("<");
			   
			   for(;red!=-1;)
			   {
				 
			   
			   res98=res98+wanv.substring(0, wanv.indexOf('<'));
			   
				   jreq=wanv.substring(wanv.indexOf('<')+1, wanv.indexOf('>'));

				   
				   Log.i("OBJECT DETEC", jreq);
				   
				 res98=res98+getvaluefromjson(jreq,json);
				   
				 
				 if(wanv.length()>wanv.indexOf('>')+1)
				 {
			    wanv=wanv.substring(wanv.indexOf('>')+1);	   
				 
			    red=wanv.indexOf('<')  ;
				 } 
				 
				 else
				 {
					 red=-1;
					 
				 }
				 
				 
			   }
			
			
			
			
			
			
			
			
			
			return res98;
		}

		
		
		
		
		
		
		
		
		
		private String getvaluefromjson(String jreq, String json){
			// TODO Auto-generated method stub
			
			
			
			String ab=String.valueOf(JsonPath.read(json, jreq));
			
			
			
			
			
			
			return ab;
		}

		private void extractvalues(String val2) throws NullPointerException{
			// TODO Auto-generated method stub
//			int r1;
//			
//			r1=val2.indexOf(';');
//			
//			for(;r1!=-1;)
//			{
//				val2=val2.trim();
//				
//				values23.add(val2.substring(0, val2.indexOf(';')));
//				Log.d("yo", val2.substring(0, val2.indexOf(';')));
//				
//				if(val2.length()>(val2.indexOf(';')+1))
//				{
//					val2=val2.substring(val2.indexOf(';')+1);
//					Log.d("true", "yes");
//				}
//				else
//				{
//					r1=-1;
//					
//				}
//				
//				
//				
//				
//			}
			
			
			no_of_spaces=Integer.parseInt(val2);
			
			Log.d("no of spaces", no_of_spaces+"");
			
			
		}

		private void extractbraced(String msg2) {
			// TODO Auto-generated method stub
			
			
			int r=0;
			
			
			r=msg2.indexOf('<');
			for(;r!=-1;)
			{
				
				astr.add(msg2.substring(msg2.indexOf('<')+1, msg2.indexOf('>')));
				
				
				if(msg2.length()>msg2.indexOf('>')+1)
				{
				msg2=msg2.substring(msg2.indexOf('>')+1);
				
				
			r=msg2.indexOf('<');
				}
				
				else
				{
					r=-1;
				}
			}
			
			
			
			
			
			
			
			
			
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			
			
			Log.d("new asynctask", "post exe 1");
			
			Log.i("final", result);
			
			
			
			sendsms(ph_no, result);

			
		
			
		}
		
		
		
		
		
	}
	

	
	
	
	

}
