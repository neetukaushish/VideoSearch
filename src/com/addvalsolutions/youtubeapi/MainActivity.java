package com.addvalsolutions.youtubeapi;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import addvalsolutions.videosearch.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText edit;
	Button btn;
	ImageView img;
	String player_default="";
	ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		edit=(EditText)findViewById(R.id.edit_search);
		btn=(Button)findViewById(R.id.btn_search);
		lv=(ListView)findViewById(R.id.list);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String[] val;
				String input="";
				String Keyword=edit.getText().toString();
				if(Keyword.contains(" ")){
					val=Keyword.split(" ");
					for(int j=0;j<val.length;j++){
						input=input+val[j]+"+";
					}
				}
				else{
					input+=Keyword;
				}
				new RequestTask().execute("https://gdata.youtube.com/feeds/api/videos?q="+input+"&alt=jsonc&v=2&key=AIzaSyBAhKQ4VY3PYYn1p4qjX0N7n7Cl3NPbzww");
			}
		});
		  lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
					// TODO Auto-generated method stub
					//Video chosen=(Video)arg0.getItemAtPosition(position);
					//int i=Integer.parseInt(chosen.toString());
					//lv.SelectedItems[i].SubItems[1].Text;
					Map<String, String> map=(Map<String,String>)lv.getItemAtPosition(position);
					String val=map.get("player");
					Bundle b=new Bundle();
					b.putString("video", val);
					Intent intent=new Intent(getApplicationContext(),SecondActivity.class);
					intent.putExtras(b);
					startActivity(intent);
					//startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(val)));
				}
			}); 
	}

	private class RequestTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... uri) {
			// TODO Auto-generated method stub
			HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            //JSONObject jsonObject;
            String responseString = null;
			try{
				response = httpclient.execute(new HttpGet(uri[0]));
				ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
			}
			catch(Exception e){
				//Toast.makeText(, "Please Enter a valid title", Toast.LENGTH_SHORT).show();
			}
			
			
			return responseString;
		}
		 @Override
	        protected void onPostExecute(String response)
	        {
	            super.onPostExecute(response);

	            if (response != null)
	            {
	                try
	                {
	                    
	                    JSONObject jsonObject = new JSONObject(response);
	                    ArrayList<HashMap<String, String>> items=new ArrayList<HashMap<String,String>>();
	                   JSONArray jsonarray=jsonObject.getJSONObject("data").getJSONArray("items");
	                   for(int i=0;i<jsonarray.length();i++){	
	           			String title=jsonarray.getJSONObject(i).getString("title");
	           			JSONObject thumb=jsonarray.getJSONObject(i).getJSONObject("thumbnail");
	           			String sqDefault=thumb.getString("sqDefault");
	           			JSONObject player=jsonarray.getJSONObject(i).getJSONObject("player");
	           			player_default=player.getString("default");
	           			//creating hashmap
	           		    HashMap<String, String> map=new HashMap<String, String>();
	           		    
	           		    //adding each child node to HashMap key and value
	           		    map.put("title", title);
	           		    map.put("sqDefault", sqDefault);
	                    map.put("player", player_default); 
	                    //adding to arraylist
	                       items.add(map);    
	           		}
	                    // update the UI
	                   
	                   if(items.isEmpty()){
							Toast.makeText(getApplicationContext(), "No Result Found", Toast.LENGTH_LONG).show();
						}
						else{
							ListAdapter adapter = new SimpleAdapter(getApplicationContext(), items,
									R.layout.list_items,
									new String[] { "sqDefault","title" }, new int[] {
											R.id.img , R.id.tv_search});

							lv=(ListView)findViewById(R.id.list);
							lv.setAdapter(adapter);
						}
	                }
	                catch (JSONException e)
	                {
	                    Log.d("Test", "Failed to parse the JSON response!");
	                }
	            }
	        }
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
