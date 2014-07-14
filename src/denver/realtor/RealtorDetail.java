/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package denver.realtor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import denver.util.ViewHolder;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author grant
 */
public class RealtorDetail extends Activity{
    private static String name;
    private static String office_name;
    private static String office_phone;
    private static String home_phone;
    private static String photoUri;
    
    TextView nameTextView;
    TextView officeNameTextView;
    TextView officePhoneTextView ;
    TextView homePhoneTextView ;
    ImageView photoView ;
    
    ViewHolder vholder;
    
    
    public RealtorDetail(){
        vholder = new ViewHolder();
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        
        try{
            //Grab references to ui components//
            nameTextView = (TextView) findViewById(R.id.name);
            officeNameTextView = (TextView) findViewById(R.id.office_name);
            officePhoneTextView = (TextView) findViewById(R.id.office_phone);
            homePhoneTextView = (TextView) findViewById(R.id.home_phone);
            photoView = (ImageView) findViewById(R.id.photo);
            vholder.photo = photoView;
            
            //Grab data to display from intent//
            Intent intent = getIntent();
            name = intent.getStringExtra("name");
            office_name = intent.getStringExtra("office_name");
            office_phone = intent.getStringExtra("office_phone");
            home_phone = intent.getStringExtra("home_phone");
            photoUri = intent.getStringExtra("photo_uri");
            vholder.photoUrl = photoUri;
            
            //Set intent data to ui components//
            nameTextView.setText(name);
            officeNameTextView.setText(office_name);
            officePhoneTextView.setText(office_phone);
            homePhoneTextView.setText(home_phone);
            
            new DownloadAsyncTask().execute(vholder);
        }catch(Exception e){
            Log.e("realtor", "RealtorDetail", e);
        }
    }
    
    private class DownloadAsyncTask extends AsyncTask<ViewHolder, Void, ViewHolder> {

        @Override
        protected ViewHolder doInBackground(ViewHolder... params) {
            //load image directly
            ViewHolder viewHolder = params[0];
            try {
                URL imageURL = new URL(viewHolder.photoUrl);
                viewHolder.photoBitmap = BitmapFactory.decodeStream(imageURL.openStream());
            } catch (IOException e) {
                Log.e("error", "Downloading Image Failed");
                viewHolder.photo = null;
            }
            return viewHolder;
        }

        @Override
        protected void onPostExecute(ViewHolder result) {
            if (result.photoBitmap == null) {
                result.photo.setImageResource(R.drawable.ic_launcher);
            } else {
                result.photo.setImageBitmap(result.photoBitmap);
            }
        }
        
    }
    
        
}
