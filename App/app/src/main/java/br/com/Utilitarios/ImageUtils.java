//    This file is part of WorkUp.
//
//    WorkUp is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    WorkUp is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with WorkUp.  If not, see <http://www.gnu.org/licenses/>.
//
package br.com.Utilitarios;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;

public class ImageUtils {
	public static byte[] drawableToByteArray(Drawable d) {

	    if (d != null) {
	        Bitmap imageBitmap = ((BitmapDrawable) d).getBitmap();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
	        byte[] byteData = baos.toByteArray();

	        return byteData;
	    } else
	        return null;

	}
	
	public static String bitmapToString(Bitmap bitmap){
	     ByteArrayOutputStream baos=new  ByteArrayOutputStream();
	     bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
	     byte [] b=baos.toByteArray();
	     String temp=Base64.encodeToString(b, Base64.DEFAULT);
	     return temp;
	}
	
	public static Bitmap stringToBitMap(String encodedString){
		   try {
		      byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
		      Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
		      return bitmap;
		   } catch(Exception e) {
		      e.getMessage();
		      return null;
		   }
		}
	
	public static byte[] bitmapToByteArray(Bitmap bmp){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		return byteArray;
	}


	public static Drawable byteToDrawable(byte[] data) {

	    if (data == null)
	        return null;
	    else
	        return new BitmapDrawable(BitmapFactory.decodeByteArray(data, 0, data.length));
	}
	
	
	public static Bitmap compactImages(Bitmap bitmap) {
	    try {
	        Bitmap encogida = Bitmap.createScaledBitmap(bitmap,144, 144, true);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        encogida.compress(Bitmap.CompressFormat.PNG,80, baos);
	        return encogida;
	    } catch (Exception ex) {
	    	 Log.e("ERROR compactar imagens ",ex.toString()); // ex.getMessage());
		     return null;
	    }
	}
	

}
