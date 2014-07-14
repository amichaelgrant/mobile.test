package denver.realtor;

import denver.util.RAdaptor;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import denver.util.RAdaptorRowItem;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends ListActivity {
    static final String REALTOR_URL = "http://www.ebby.com/realtor_list?format=ios";
    AsyncHttpClient client;
    RAdaptor adapter;
    List<RAdaptorRowItem> rowItems = new ArrayList<RAdaptorRowItem>();
    public MainActivity() {
         
         client = new AsyncHttpClient();
         client.get(REALTOR_URL, null, new JsonHttpResponseHandler() {              
            List<RAdaptorRowItem> rowItems = new ArrayList<RAdaptorRowItem>();
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("realtor", "onSuccess");
                try{
                    String photoUrl = "";
                    JSONObject realtor = null;
                    JSONArray realtors = response.getJSONArray("realtors");
                    for( int i = 0; i < realtors.length(); i++){
                        realtor = realtors.getJSONObject(i);
                        photoUrl = realtor.getString("photo");
                        RAdaptorRowItem rItem = new RAdaptorRowItem(
                                realtor.getString("name"),
                                realtor.getString("office"),
                                realtor.getString("phone_number"),
                                realtor.getString("photo")
                                );
                        rowItems.add( rItem );
                    }
                    RAdaptor adapter = new RAdaptor(MainActivity.this, rowItems);  
                    MainActivity.this.setListAdapter(adapter);
                    MainActivity.this.rowItems = rowItems;
                }catch(Exception e){
                    Log.e("realtor", "onSuccess Error json array parse error - " + e.getMessage());
                }
            }
         });
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        adapter = new RAdaptor(this, rowItems);
        setListAdapter(adapter);
        Toast.makeText(this, "Loading realtors list...", Toast.LENGTH_LONG).show();
    }
    
    @Override
    protected void onListItemClick (ListView l, View v, int position, long id) {
        RAdaptorRowItem item = rowItems.get(position);
        Toast.makeText(this, item.getName(), Toast.LENGTH_SHORT).show();
        //show details activity//
        try{

            Intent intent = new Intent(this, RealtorDetail.class); 
            intent.putExtra("name", item.getName());
            intent.putExtra("office_name", item.getOffice());
            intent.putExtra("office_phone", item.getPhone());
            intent.putExtra("home_phone", item.getPhone());
            intent.putExtra("photo_uri", item.getPhotoUrl() + "//width/200");

            startActivity(intent);
        }catch(Exception ex){
            Log.e("error", "Calling Intent", ex);
        }
    }  
         
    

    
}
