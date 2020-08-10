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



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Calendar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import br.com.Banco.*;
import br.com.Classes.*;
import br.com.Utilitarios.ImageUtils;
import br.com.WorkUp.R;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class Cadastrar extends Activity {
	
	//Elementos da interface
	private EditText telefone;
	private EditText nome;
	private RadioGroup sexo;
	private Switch tipoDePerfil;
	private EditText usuario;
	private EditText senha;
	private LinearLayout bgCadastrar;
	
	//private Button selecionarFoto;
	private Button cadastrarPersonal;
	private ImageView imagem;
	
	//Atributos Auxiliares
	
	private File photo;
	private Bitmap bmp;
	private Uri selectedImageUri;
	private String FILE_NAME;

	//Base de dados local
	Banco b;
	private SharedPreferences pref;
	private Editor editor;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar);
		getActionBar().hide();
		mapearComponentes();
				
	}
	
	public void mapearComponentes(){
		b = new Banco(this, null, null, 0);
		imagem = (ImageView) findViewById(R.id.imgFotoCadastroPersonal);
		telefone = (EditText)findViewById(R.id.edtTelefonePersonal);
		nome = (EditText)findViewById(R.id.edtNomeCompleto);
		sexo = (RadioGroup)findViewById(R.id.RgSexo);
		tipoDePerfil = (Switch) findViewById(R.id.switchPersonalAluno);
		
		tipoDePerfil.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1){
					bgCadastrar.setBackground(getResources().getDrawable(R.drawable.shape_listview_gradient_bg));
				}else{
					bgCadastrar.setBackground(getResources().getDrawable(R.drawable.shape_bg));
					
				}
			}
		});
		
		
		usuario = (EditText)findViewById(R.id.edtUsuario);
		senha = (EditText)findViewById(R.id.edtSenha);
		//selecionarFoto = (Button) findViewById(R.id.btnSelecionarFotoPersonal);
		Typeface font = Typeface.createFromAsset(getAssets(), "BebasNeue Bold.ttf");
		//selecionarFoto.setTypeface(font);
		cadastrarPersonal = (Button) findViewById(R.id.btnConcluirCadastro);
		cadastrarPersonal.setTypeface(font);
		bgCadastrar = (LinearLayout) findViewById(R.id.bgCadastro);
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		editor = pref.edit();
	}
	
	
	public void tirarFoto(View v){
		 
		 AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
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
				photo = new File(getCacheDir(), FILE_NAME);
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
		    		imagem.setImageBitmap(foto);
		    		
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
		    		imagem.setImageBitmap(foto);
		    		
		    		
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
				imagem.setImageBitmap(null);
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
	    		Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
	    		toast.show();
	    	}
	    }
	 
	public void concluirCadastro(View v){
		if(telefone.getText().toString().equals("") ||
			nome.getText().toString().equals("") ||
			usuario.getText().toString().equals("") || 
			senha.getText().toString().equals("")){
			
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cadastrar.this);
			alertDialog.setTitle("Ops...");
			alertDialog.setMessage("Todos os campos devem ser preenchidos...\nPor favor, preencha-os e tente novamente");
			alertDialog.setIcon(R.drawable.profile);
			alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		         public void onClick(DialogInterface dialog,int which) {
		        	 dialog.cancel();
		         }
			});
			// Showing Alert Message
		        alertDialog.show();
		      
			
		}else{
			
			/*
		 	android:textOff="@string/botao_aluno"
            android:textOn="@string/botao_personal" 
            */
			if(tipoDePerfil.isChecked()){
				cadastrarNovoPersonal();
			}else{
				cadastrarNovoAluno();
			}
		}
	}
	 
	//Cadastrar
	public void cadastrarNovoPersonal(){
		if(senha.getText().toString().equals("isFacebookUser")){
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cadastrar.this);
			alertDialog.setTitle("Ops...");
			alertDialog.setMessage("Esta não é uma senha válida...\nPor favor tente outra senha...");
			alertDialog.setIcon(R.drawable.profile);
			alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		         public void onClick(DialogInterface dialog,int which) {
		        	 dialog.cancel();
		         }
			});
			// Showing Alert Message
		        alertDialog.show();
		}else{
			String s = null;
			byte[] byteFoto;
			String fotoPersonal = null;
			try{
			byteFoto = ImageUtils.bitmapToByteArray(bmp);
			fotoPersonal = Base64.encodeToString(byteFoto, Base64.DEFAULT);
			}catch(Exception e){
				byteFoto = ImageUtils.drawableToByteArray(getResources().getDrawable(R.drawable.profile));
			}
			//Log.i("Estou dentro do cadastrar", fotoPersonal);
			//Log.i("foto dump", fotoPersonal);
			//coleta se eh masculino ou feminino
			switch(sexo.getCheckedRadioButtonId()){
			case R.id.rdSexoMasculino:
				s = "Masculino";
			break;
			case R.id.rdSexoFeminino:
				s = "Feminino";
			break;			
			}
			//constroi objeto
			final Personal p = new Personal(
					telefone.getText().toString(),
					nome.getText().toString(),
					"",
					"",
					s,
					usuario.getText().toString(),
					senha.getText().toString(),
					fotoPersonal
					);
			
			// Cadastra Personal usando o servi��o Web 
			
			final boolean res = p.salvarPersonalWeb(byteFoto);
			if(res){
				p.salvar(b,byteFoto);
				editor.putString("telefone", p.getTelefone());
				editor.putString("usuario", p.getUsuario());
				editor.putString("senha", p.getSenha());
				editor.putString("tipo", "personal");
				editor.putString("sexo", s);
				editor.putBoolean("isFacebookUser", false);
				editor.commit();
				Toast.makeText(Cadastrar.this, "Bem-vindo " + p.getNome() , Toast.LENGTH_LONG).show();
				finish();
				Intent intent = new Intent(Cadastrar.this,HomePersonal.class);
		    	startActivity(intent);
				Toast.makeText(Cadastrar.this, "Salvo no servidor", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(Cadastrar.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
				
			}
		}
		
	}
	
	public void cadastrarNovoAluno(){
				
		String s = null;
		
		if(senha.getText().toString().equals("isFacebookUser")){
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cadastrar.this);
			alertDialog.setTitle("Ops...");
			alertDialog.setMessage("Esta não é uma senha válida...\nPor favor tente outra senha...");
			alertDialog.setIcon(R.drawable.profile);
			alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		         public void onClick(DialogInterface dialog,int which) {
		        	 dialog.cancel();
		         }
			});
			// Showing Alert Message
		        alertDialog.show();
		      
		}else{
			byte[] byteFoto;
			String fotoAluno= null;
			try{
			byteFoto = ImageUtils.bitmapToByteArray(bmp);
			fotoAluno = Base64.encodeToString(byteFoto, Base64.DEFAULT);
			}catch(Exception e){
				byteFoto = ImageUtils.drawableToByteArray(getResources().getDrawable(R.drawable.profile));
			}
			//Log.i("foto dump", fotoAluno);
			
			//coleta se eh masculino ou feminino
			switch(sexo.getCheckedRadioButtonId()){
			case R.id.rdSexoMasculino:
				s = "Masculino";
			break;
			case R.id.rdSexoFeminino:
				s = "Feminino";
			break;			
			}
			//constroi objeto
			final Aluno a = new Aluno(
					telefone.getText().toString(),
					nome.getText().toString(),
					"",
					"",
					s,
					usuario.getText().toString(),
					senha.getText().toString(),
					null,
					0,
					0,
					fotoAluno,
					0);
			
			// Cadastra Personal usando o servi��o Web 
			
			final boolean res = a.salvarAlunoWeb(byteFoto);
			if(res){
				a.salvar(b,byteFoto);
				editor.putString("telefone", a.getTelefone());
				editor.putString("usuario", a.getUsuario());
				editor.putString("senha", a.getSenha());
				editor.putString("tipo", "aluno");
				editor.putString("sexo", s);
				editor.putBoolean("isFacebookUser", false);
				editor.commit();
				Toast.makeText(Cadastrar.this, "Bem-vindo " + a.getNome() , Toast.LENGTH_LONG).show();
				finish();
				Intent intent = new Intent(Cadastrar.this,HomeAluno.class);
		    	startActivity(intent);
				Toast.makeText(Cadastrar.this, "Salvo no servidor", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(Cadastrar.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
				
			}
		}
		
	}
	
	//MENUS
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cadastrar_personal, menu);
		return true;
	}

}
