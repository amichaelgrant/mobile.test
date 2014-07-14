/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package denver.util;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import denver.realtor.R;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 *
 * @author grant
 */
public class RAdaptor extends ArrayAdapter<RAdaptorRowItem> {
    private final Context context;
    private final List<RAdaptorRowItem> items;

    public RAdaptor( Context context,  List<RAdaptorRowItem> items) {
        super(context, R.layout.rlist, items);
        this.context = context;
        this.items = items;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vholder = null;
        RAdaptorRowItem rowItem = getItem(position);
         
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.rlist, parent, false);
            vholder = new ViewHolder();
            vholder.name = (TextView) convertView.findViewById(R.id.name);
            vholder.office = (TextView) convertView.findViewById(R.id.office);
            vholder.phone = (TextView) convertView.findViewById(R.id.phone);
            vholder.photo = (ImageView) convertView.findViewById(R.id.photo);
            convertView.setTag(vholder);
        } else
            vholder = (ViewHolder) convertView.getTag();
                 
        vholder.name.setText(rowItem.getName());
        vholder.office.setText(rowItem.getOffice());
        vholder.phone.setText(rowItem.getPhone());
        vholder.photoUrl = rowItem.getPhotoUrl();
   
        new DownloadAsyncTask().execute(vholder);
         
        return convertView;
    }
        
    public class DownloadAsyncTask extends AsyncTask<ViewHolder, Void, ViewHolder> {

        @Override
        protected ViewHolder doInBackground(ViewHolder... params) {
            //load image directly
            ViewHolder viewHolder = params[0];
            try {
                URL imageURL = new URL(viewHolder.photoUrl);
                viewHolder.photoBitmap = BitmapFactory.decodeStream(imageURL.openStream());
            } catch (IOException e) {
                Log.e("realtor", "Downloading Image Failed");
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
