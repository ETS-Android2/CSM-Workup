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
package br.com.GUI.perfil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import br.com.Banco.Banco;
import br.com.Classes.Aluno;
import br.com.Classes.Aula;
import br.com.Classes.Personal;
import br.com.GUI.treinamentos.AcompanhamentoTreinamentoFragment;
import br.com.Utilitarios.ImageUtils;
import br.com.Utilitarios.WebService;
import br.com.WorkUp.R;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class PerfilPersonal extends Fragment {
	
	//Elementos da interface
	private ImageView img;	
	private EditText nome;
	private NumberPicker dataNascimentoDia;
	private NumberPicker dataNascimentoMes;
	private NumberPicker dataNascimentoAno;
	private EditText telefone;
	private EditText email;
	private Button atualizar;
	
	
	// Atributos auxiliares;
	private SharedPreferences pref;
	private Editor editor;
	
	//Atributos Auxiliares Foto
	
		private File photo;
		private Bitmap bmp;
		private Uri selectedImageUri;
		private String FILE_NAME;

	
	//Base da dados Local	
	private Banco b;
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
	    super.setUserVisibleHint(isVisibleToUser);
	    if(img != null){
	    	   onResume();
	    }
	 
	}
	
	//Creates and Resumes
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.activity_perfil_personal, container, false);
	        
	        return rootView;
	 }	

	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		  super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);

		mapearComponentes();
		
			refresh();
		 
	}	
	
	public void mapearComponentes(){
		img = (ImageView) getActivity().findViewById(R.id.imgFotoPerfilPersonal);
		
		img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				alterarFoto();
				
			}
		});
		
		pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
		editor = pref.edit();
		nome = (EditText) getActivity().findViewById(R.id.edtNome);
		dataNascimentoDia = (NumberPicker)getActivity().findViewById(R.id.nmbDataNascimentoDiaPersonal);
		dataNascimentoDia.setMaxValue(31);
		dataNascimentoDia.setMinValue(1);
		
		dataNascimentoMes = (NumberPicker)getActivity().findViewById(R.id.nmbDataNascimentoMesPersonal);
		dataNascimentoMes.setMaxValue(12);
		dataNascimentoMes.setMinValue(1);
				
		dataNascimentoAno = (NumberPicker)getActivity().findViewById(R.id.nmbDataNascimentoAnoPersonal);
		Calendar c = Calendar.getInstance();
		int ano = c.get(Calendar.YEAR);
		dataNascimentoAno.setMaxValue(ano);
		dataNascimentoAno.setMinValue(1900);
		dataNascimentoAno.setValue(ano - 18);
		
		
		telefone = (EditText)getActivity().findViewById(R.id.edtTelefone);
		email = (EditText)getActivity().findViewById(R.id.edtEmail);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "BebasNeue Bold.ttf");
		atualizar = (Button)getActivity().findViewById(R.id.btnAtualizarPerfilPersonal);
		atualizar.setTypeface(font);
		
		atualizar.setOnClickListener(new View.OnClickListener() {

		    public void onClick(View v) {
		        atualizarDadosPersonal();
		    }
		});
		b = new Banco (getActivity(),null,null,0);
		
	}
	
	
	@Override
	public void onResume(){
		super.onResume();
			refresh();
	}
	
	
	
	public void alterarFoto(){
		tirarFoto();
	}
	
	public void tirarFoto(){
		 
		 AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
	        alertDialog.setTitle("Selecione o método");
	        alertDialog.setMessage("Deseja usar qual aplicativo para importar sua foto?");
	        alertDialog.setIcon(R.drawable.profile);
	        alertDialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog,int which) {
	            	usarCamera();
	            }
	        });
	        alertDialog.setNegativeButton("Galeria", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            	usarGaleria();
	            	dialog.cancel();
	            }
	        });
	 
	        alertDialog.show();
	       
	    	
	    }
	 
	public void usarCamera(){
		/* Calendar cal = Calendar.getInstance();
	    	String nomeArquivo = cal.get(Calendar.YEAR) +"-" 
	    	+ cal.get(Calendar.MONTH) + "-" 
	    	+ cal.get(Calendar.DAY_OF_MONTH) + "-" 
	    	+ cal.get(Calendar.HOUR_OF_DAY) + "-"
	    	+ cal.get(Calendar.MINUTE);
	    	
	    	Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
	     	
	     	photo = new File(android.os.Environment.getExternalStorageDirectory(), nomeArquivo);
	     	
	     	i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
			selectedImageUri = Uri.fromFile(photo);
			startActivityForResult(i, 100);*/
		
		//use standard intent to capture an image
   	Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
   	//we will handle the returned data in onActivityResult
       startActivityForResult(captureIntent, 100);
		 
	 }
	 
	public void usarGaleria(){
		 Calendar c = Calendar.getInstance();

			FILE_NAME = c.get(Calendar.DAY_OF_MONTH) + "_" + c.get(Calendar.YEAR) + ".jpg";

			Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
				photo = new File(android.os.Environment.getExternalStorageDirectory(), FILE_NAME);
			} else {
				photo = new File(getActivity().getCacheDir(), FILE_NAME);
			}

			if (photo != null) {
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
				selectedImageUri = Uri.fromFile(photo);
				startActivityForResult(intent, 200);
			}
		 
		
	 }
	 
	 @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		 if (resultCode == Activity.RESULT_OK) {

	    	if(requestCode == 200 || requestCode == 202){
	    		
	    		if(requestCode == 200){
	    			
	    			selectedImageUri = data.getData();
	    			
	    			cortar(requestCode);
	    			
	    			/*selectedImageUri = Uri.fromFile(photo);
		    		
		    		ContentResolver cr = getContentResolver();
		    		
		    		Bitmap foto;
		    		try{
		    			foto = android.provider.MediaStore.Images.Media.getBitmap(cr,  selectedImageUri);
		    			bmp = ImageUtils.compactImages(foto);
		    			imagem.setImageBitmap(foto);
		    		
		    		}catch(FileNotFoundException e){
		    			Log.i("Exception", e.toString());
		    			e.printStackTrace();
		    		} catch (IOException e){
		    			
		    			
		    		}*/
	    		}else if(requestCode == 202){
	    			//get the returned data
	    			Bundle extras = data.getExtras();
	    			//get the cropped bitmap
	    			Bitmap foto = extras.getParcelable("data");
		    		bmp = ImageUtils.compactImages(foto);
		    		img.setImageBitmap(bmp);
		    		
		    		Log.i("tirei a foto ",  "e setei no canvas");
		    		Personal p = new Personal();
		    		p.setUsuario(pref.getString("usuario", null));
		    		if(p.editarFotoPersonalWeb(ImageUtils.bitmapToByteArray(bmp))){
		    			Log.i("salvei web","salvei web");
		    			if(p.editarFotoPersonal(b,ImageUtils.bitmapToByteArray(bmp))){
		    				Log.i("salvei local","salvei local");
		    				refresh();
		    				Toast.makeText(getActivity(), "Atualizada com sucesso!", Toast.LENGTH_SHORT).show();
		    			}
		    		}
		    		Log.i("setei", "no primeirro");
		    		
		    	
		    		
	    		}
	    		
	    			
	    		
	    		
	    		
	    		
	    	}else if( requestCode == 100 || requestCode == 102){
	    		
	    		if(requestCode == 100){
	    			selectedImageUri = data.getData();
	    			
	    			cortar(requestCode);
	    			
	    		}else if(requestCode == 102){
	    			//get the returned data
	    			Bundle extras = data.getExtras();
	    			//get the cropped bitmap
	    			Bitmap foto = extras.getParcelable("data");
	    			bmp = ImageUtils.compactImages(foto);
		    		
		    		Log.i("tirei a foto ",  "e setei no canvas");
		    		Personal p = new Personal();
		    		p.setUsuario(pref.getString("usuario", null));
		    		if(p.editarFotoPersonalWeb(ImageUtils.bitmapToByteArray(bmp))){
		    			Log.i("salvei web","salvei web");
			    		
		    			if(p.editarFotoPersonal(b, ImageUtils.bitmapToByteArray(bmp))){
		    				Log.i("salvei local","salvei local");
		    				img.setImageBitmap(bmp);
		    				Toast.makeText(getActivity(), "Atualizada com sucesso!", Toast.LENGTH_SHORT).show();
		    			}
		    		}
		    		Log.i("setei", "no primeirro");
			    	
		    		
		    		
		    		refresh();
		    		
		    		
	    		}
					/*try {
						
		    			Uri selectedImage = data.getData();
		    			
		    			String[] nomeCaminho = {MediaStore.Images.Media.DATA};
		    			
		    			Cursor cursor = getContentResolver().query(selectedImage, nomeCaminho, null, null, null);
		    			cursor.moveToFirst();
		    			
		    			int indexColuna = cursor.getColumnIndex(nomeCaminho[0]);
		    			String caminho = cursor.getString(indexColuna);
		    			
		    			bmp = ImageUtils.compactImages(BitmapFactory.decodeFile(caminho));
		    			imagem.setImageBitmap(BitmapFactory.decodeFile(caminho));
		    							
		    				
					} catch (Exception e) {
						Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
						Log.e("Camera", e.toString());
					}
					*/
	    		}
	  
		 }else{
				selectedImageUri = null;
				img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.profile));
		 }
	}
	 
	private void cortar(int requestCode){
	    	//take care of exceptions
	    	try {
	    		//call the standard crop action intent (the user device may not support it)
		    	Intent cropIntent = new Intent("com.android.camera.action.CROP"); 
		    	//indicate image type and Uri
		    	cropIntent.setDataAndType(selectedImageUri, "image/*");
		    	//set crop properties
		    	cropIntent.putExtra("crop", "true");
		    	//indicate aspect of desired crop
		    	cropIntent.putExtra("aspectX", 1);
		    	cropIntent.putExtra("aspectY", 1);
		    	//indicate output X and Y
		    	cropIntent.putExtra("outputX", 144);
		    	cropIntent.putExtra("outputY", 144);
		    	//retrieve data on return
		    	cropIntent.putExtra("return-data", true);
		    	//start the activity - we handle returning in onActivityResult
		        startActivityForResult(cropIntent, requestCode + 2 );  
	    	}
	    	//respond to users whose devices do not support the crop action
	    	catch(ActivityNotFoundException anfe){
	    		//display an error message
	    		String errorMessage = "Whoops - your device doesn't support the crop action!";
	    		Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
	    		toast.show();
	    	}
	    }
	 
	
	
	//Atualizar
	public void refresh(){
		bmp = null;
		Personal rP = new Personal();
		
		rP = new Personal().buscarPersonal(b,pref.getString("usuario", null));
		//Log.i("Aluno NO RPPPPP", rP.toString());
		try{
			byte[] foto = rP.buscarFotoPersonal(b, pref.getString("usuario", null));
			bmp = BitmapFactory.decodeByteArray(foto,0,foto.length);
			img.setImageBitmap(bmp);
		}catch(Exception ex){
			img.setImageDrawable(getResources().getDrawable(R.drawable.profile));
		}
		
		try{
			nome.setText(rP.getNome());
			if(nome.getText().toString().equals("anyType{}")){
				nome.setText("");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	
		
		try{
			if(!rP.getDataDeNascimento().equals("anyType{}") && rP.getDataDeNascimento() != null){
				String dia = rP.getDataDeNascimento().substring(0,rP.getDataDeNascimento().indexOf("/"));
				String mes = rP.getDataDeNascimento().substring(rP.getDataDeNascimento().indexOf("/") + 1 ,rP.getDataDeNascimento().lastIndexOf("/") );
				String ano = rP.getDataDeNascimento().substring(rP.getDataDeNascimento().lastIndexOf("/")+ 1);
				
				dataNascimentoDia.setValue(Integer.parseInt(dia));
				dataNascimentoMes.setValue(Integer.parseInt(mes));
				dataNascimentoAno.setValue(Integer.parseInt(ano));
			}	
		}catch(Exception e ){
			dataNascimentoDia.setValue(1);
			dataNascimentoMes.setValue(1);
			dataNascimentoAno.setValue(1900);
		}
		
		try{
			telefone.setText(rP.getTelefone());
			if(telefone.getText().toString().equals("anyType{}")){
				telefone.setText("");
			}
			email.setText(rP.getEmail());
			if(email.getText().toString().equals("anyType{}")){
				email.setText("");
			}
		}catch(Exception e ){
			e.printStackTrace();
		}
		
		
	}
	
	public void atualizarDadosPersonal(){
		
		if(nome.getText().toString() == null){
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
	        alertDialog.setTitle("Atenção");
	        alertDialog.setMessage("Por favor, digite o seu nome");
	        alertDialog.setIcon(R.drawable.critical);
	        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog,int which) {
		        	dialog.dismiss();
	            }
	        });
	        
	 
	        alertDialog.show();
		}else{
		
			byte[] byteFoto = ImageUtils.bitmapToByteArray(bmp);
			String fotoPersonal = Base64.encodeToString(byteFoto, 0);
			
			String dataDeNascimento = dataNascimentoDia.getValue() + "/" + dataNascimentoMes.getValue() + "/" + dataNascimentoAno.getValue();
			
			final Personal p = new Personal(
					telefone.getText().toString(),
					nome.getText().toString(),
					dataDeNascimento,
					email.getText().toString(),
					pref.getString("sexo", null),
					pref.getString("usuario", null),
					pref.getString("senha", null),
					fotoPersonal);
			
			boolean r = p.atualizarPersonalWeb();
			
			if(r && p.atualizar(b,byteFoto)){
				Toast.makeText(getActivity(), "Atualizado com Sucesso", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getActivity(), "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
				
			}
		}
	}
	
	
		
	//MENUS
	@Override
	public void onCreateOptionsMenu(
	    Menu menu, MenuInflater inflater) {
	   inflater.inflate(R.menu.perfil_personal, menu);   
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.actAcompanhamentoPersonal) {
			Intent i = new Intent(getActivity(),AcompanhamentoTreinamentoFragment.class);
			getActivity().startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
