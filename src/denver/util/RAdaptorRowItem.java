/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package denver.util;

import android.graphics.Bitmap;

/**
 *
 * @author grant
 */
public class RAdaptorRowItem {
        private String photoUrl;
        private Bitmap photo;
        private String name;
        private String office;
        private String phone;

        public RAdaptorRowItem(Bitmap photo, String name, String office, String phone) {
            this.photo   = photo;
            this.name    = name;
            this.office    = office;
            this.phone  = phone;
        }
        public RAdaptorRowItem( String name, String office, String phone, String photoUrl) {
            this.name    = name;
            this.office    = office;
            this.phone  = phone;
            this.photoUrl = photoUrl;
        }
        
        public Bitmap getPhoto() {
            return photo;
        }
        public void setPhoto(Bitmap photo) {
            this.photo = photo;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getOffice() {
            return office;
        }
        public void setOffice(String office) {
            this.office = office;
        }
        public String getPhone() {
            return phone;
        }
        public void setPhone(String photoUrl) {
            this.phone = photoUrl;
        }
        public String getPhotoUrl() {
            return photoUrl;
        }
        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }
        @Override
        public String toString() {
            return this.name + "\n" + office;
        }   
}
