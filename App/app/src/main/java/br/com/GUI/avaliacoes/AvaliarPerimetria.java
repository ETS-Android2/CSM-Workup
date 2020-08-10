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
package br.com.GUI.avaliacoes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import br.com.Utilitarios.ImageUtils;
import br.com.WorkUp.R;
import br.com.WorkUp.R.id;
import br.com.WorkUp.R.layout;
import br.com.WorkUp.R.menu;
import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.Toast;
import android.os.Build;
import android.provider.MediaStore;

public class AvaliarPerimetria extends Fragment {
	
	//medidas gerais
	
		private NumberPicker nmbQuadril;
		private NumberPicker nmbAbdomen;
		private NumberPicker nmbPeito;
		private NumberPicker nmbOmbro;
		private NumberPicker nmbAntebraco;
		private NumberPicker nmbBicepsRelaxadoEsquerdo;
		private NumberPicker nmbBicepsRelaxadoDireito;
		private NumberPicker nmbBicepsContraidoEsquerdo;
		private NumberPicker nmbBicepsContraidoDireito;
		private NumberPicker nmbCoxaProximalDireita;
		private NumberPicker nmbCoxaProximalEsquerda;
		private NumberPicker nmbCoxaDistalEsquerda;
		private NumberPicker nmbCoxaDistalDireita;
		private NumberPicker nmbCoxaMedialEsquerda;
		private NumberPicker nmbCoxaMedialDireita;
		private NumberPicker nmbPanturrilhaDireita;
		private NumberPicker nmbPanturrilhaEsquerda;
		
		private ImageView bicepsContraido;
		private ImageView bicepsRelaxado;
		private ImageView panturrilha;
		private ImageView coxaProximal;
		private ImageView coxaMedial;
		private ImageView coxaDistal;
		private ImageView antebraçoOmbro;
		private ImageView quadrilAbdomenPeito;
		
		private SharedPreferences avaliacoes;
		private Editor editor;
		
		//atributosAxiliares
		private File photo;
		private Bitmap bmp;
		private Uri selectedImageUri;
		private String FILE_NAME;
		
		private String whoIsCalling;

	/*@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_avaliar_perimetria);

		
	}*/
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	setHasOptionsMenu(true);
	
 
        View rootView = inflater.inflate(R.layout.activity_avaliar_perimetria, container, false);
        
        return rootView;
	}	
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		mapearComponentes();
		
	}
	
	public void mapearComponentes(){
		nmbQuadril = (NumberPicker) getActivity().findViewById(R.id.nmbQuadril);
		nmbAbdomen =  (NumberPicker) getActivity().findViewById(R.id.nmbAbdomen);
		nmbPeito = (NumberPicker) getActivity().findViewById(R.id.nmbPeito);
		nmbOmbro= (NumberPicker) getActivity().findViewById(R.id.nmbOmbro);
		nmbAntebraco= (NumberPicker) getActivity().findViewById(R.id.nmbAntebraco);
		nmbBicepsRelaxadoEsquerdo= (NumberPicker) getActivity().findViewById(R.id.nmbBicepsRelaxadoEsquerdo);
		nmbBicepsRelaxadoDireito= (NumberPicker) getActivity().findViewById(R.id.nmbBicepsRelaxadoDireito);
		nmbBicepsContraidoEsquerdo= (NumberPicker) getActivity().findViewById(R.id.nmbBicepsContraidoEsquerdo);
		nmbBicepsContraidoDireito= (NumberPicker) getActivity().findViewById(R.id.nbmBicepsContraidoDireito);
		nmbCoxaProximalDireita= (NumberPicker) getActivity().findViewById(R.id.nmbCoxaProximalDireita);
		nmbCoxaProximalEsquerda= (NumberPicker) getActivity().findViewById(R.id.nmbCoxaProximalEsquerda);
		nmbCoxaDistalEsquerda= (NumberPicker) getActivity().findViewById(R.id.nmbCoxaDistalEsquerda);
		nmbCoxaDistalDireita= (NumberPicker) getActivity().findViewById(R.id.nmbCoxaDistalDireita);
		nmbCoxaMedialEsquerda= (NumberPicker) getActivity().findViewById(R.id.nmbCoxaMedialEsquerda);
		nmbCoxaMedialDireita= (NumberPicker) getActivity().findViewById(R.id.nmbCoxaMedialDireita);
		nmbPanturrilhaDireita= (NumberPicker) getActivity().findViewById(R.id.nmbPanturrilhaDireita);
		nmbPanturrilhaEsquerda= (NumberPicker) getActivity().findViewById(R.id.nmbPanturrilhaEsquerda);
		
		bicepsContraido = (ImageView) getActivity().findViewById(R.id.imgBicepsContraido);
		bicepsRelaxado = (ImageView) getActivity().findViewById(R.id.imgBicepsRelaxado);
		panturrilha = (ImageView) getActivity().findViewById(R.id.imgPanturrilha);
		coxaProximal = (ImageView) getActivity().findViewById(R.id.imgVisualizarCoxaProximal);
		coxaMedial = (ImageView) getActivity().findViewById(R.id.imgCoxaMedial);
		coxaDistal = (ImageView) getActivity().findViewById(R.id.imgCoxaDistal);
		antebraçoOmbro = (ImageView) getActivity().findViewById(R.id.imgAntebracoOmbro);
		quadrilAbdomenPeito = (ImageView) getActivity().findViewById(R.id.imgQuadrilAbdomenPeito);
				
		
		avaliacoes = getActivity().getApplicationContext().getSharedPreferences("novaAvaliacao", 0);
		editor = avaliacoes.edit();
		
		
		nmbQuadril.setMaxValue(200);
		nmbAbdomen.setMaxValue(200);
		nmbPeito.setMaxValue(200);
		nmbOmbro.setMaxValue(200);
		nmbAntebraco.setMaxValue(100);
		nmbBicepsRelaxadoEsquerdo.setMaxValue(100);
		nmbBicepsRelaxadoDireito.setMaxValue(100);
		nmbBicepsContraidoEsquerdo.setMaxValue(100);
		nmbBicepsContraidoDireito.setMaxValue(100);
		nmbCoxaProximalDireita.setMaxValue(150);
		nmbCoxaProximalEsquerda.setMaxValue(150);
		nmbCoxaDistalEsquerda.setMaxValue(150);
		nmbCoxaDistalDireita.setMaxValue(150);
		nmbCoxaMedialEsquerda.setMaxValue(150);
		nmbCoxaMedialDireita.setMaxValue(150);
		nmbPanturrilhaDireita.setMaxValue(150);
		nmbPanturrilhaEsquerda.setMaxValue(150);
		
		nmbQuadril.setWrapSelectorWheel(true);
		nmbAbdomen.setWrapSelectorWheel(true);
		nmbPeito.setWrapSelectorWheel(true);
		nmbOmbro.setWrapSelectorWheel(true);
		nmbAntebraco.setWrapSelectorWheel(true);
		nmbBicepsRelaxadoEsquerdo.setWrapSelectorWheel(true);
		nmbBicepsRelaxadoDireito.setWrapSelectorWheel(true);
		nmbBicepsContraidoEsquerdo.setWrapSelectorWheel(true);
		nmbBicepsContraidoDireito.setWrapSelectorWheel(true);
		nmbCoxaProximalDireita.setWrapSelectorWheel(true);
		nmbCoxaProximalEsquerda.setWrapSelectorWheel(true);
		nmbCoxaDistalEsquerda.setWrapSelectorWheel(true);
		nmbCoxaDistalDireita.setWrapSelectorWheel(true);
		nmbCoxaMedialEsquerda.setWrapSelectorWheel(true);
		nmbCoxaMedialDireita.setWrapSelectorWheel(true);
		nmbPanturrilhaDireita.setWrapSelectorWheel(true);
		nmbPanturrilhaEsquerda.setWrapSelectorWheel(true);
		
		
		bicepsContraido.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				whoIsCalling = "bicepsContraido";
				tirarFoto();
			}
		});;
		bicepsRelaxado.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				whoIsCalling = ("bicepsRelaxado");
				tirarFoto();
			}
		});
		panturrilha.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				whoIsCalling = ("panturrilha");
				tirarFoto();
			}
		});
		coxaProximal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				whoIsCalling = "coxaProximal";
				tirarFoto();
			}
		});
		coxaMedial.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				whoIsCalling= "coxaMedial";
				tirarFoto();
			}
		});
		coxaDistal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				whoIsCalling = "coxaDistal";
				tirarFoto();
			}
		});
		antebraçoOmbro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				whoIsCalling = "antebracoOmbro";
				tirarFoto();
			}
		});
		quadrilAbdomenPeito.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				whoIsCalling = "quadrilAbdomenPeito";
				tirarFoto();
			}
		});
		
		
		adicionarPersistencia();
		
	}
	
	public void adicionarPersistencia(){

		nmbQuadril.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
				editor.putInt("quadril", arg2);
				editor.commit();
			}
		});
		nmbAbdomen.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				editor.putInt("abdomen", newVal);
				editor.commit();
				
			}
		});
		nmbPeito.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				editor.putInt("peito", newVal);
				editor.commit();
				
			}
		});
		
		nmbOmbro.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				editor.putInt("ombro", newVal);
				editor.commit();
			}
		});
		
		nmbAntebraco.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				editor.putInt("antebraco", newVal);
				editor.commit();
			}
		});
		
		nmbBicepsRelaxadoEsquerdo.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				editor.putInt("bicepsRelaxadoEsquerdo", newVal);
				editor.commit();
			}
		});
		
		nmbBicepsRelaxadoDireito.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				editor.putInt("bicepsRelaxadoDireito", newVal);
				editor.commit();
			}
		});
		nmbBicepsContraidoEsquerdo.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				editor.putInt("bicepsContraidoEsquerdo", newVal);
				editor.commit();
			}
		});
		
		nmbBicepsContraidoDireito.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				editor.putInt("bicepsContraidoDireito", newVal);
				editor.commit();
			}
		});
		nmbCoxaProximalDireita.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				editor.putInt("coxaProximalDireita", newVal);
				editor.commit();
			}
		});
		
		nmbCoxaProximalEsquerda.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				editor.putInt("coxaProximalEsquerda", newVal);
				editor.commit();
			}
		});
		
		nmbCoxaDistalEsquerda.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				editor.putInt("coxaDistalEsquerda", newVal);
				editor.commit();
			}
		});
		nmbCoxaDistalDireita.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				editor.putInt("coxaDistalDireita", newVal);
				editor.commit();
			}
		});
		nmbCoxaMedialEsquerda.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				editor.putInt("coxaMedialEsquerda", newVal);
				editor.commit();
			}
		});
		nmbCoxaMedialDireita.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				editor.putInt("coxaMedialDireita", newVal);
				editor.commit();
			}
		});
		nmbPanturrilhaDireita.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				editor.putInt("panturrilhaDireita", newVal);
				editor.commit();
			}
		});
		nmbPanturrilhaEsquerda.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				editor.putInt("panturrilhaEsquerda", newVal);
				editor.commit();
			}
		});
	}

	
	public void tirarFoto(){
		 
		 AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
	        alertDialog.setTitle(R.string.label_selecione_o_metodo);
	        alertDialog.setMessage(R.string.mensagens_qual_aplicativo_usar);
	        alertDialog.setIcon(R.drawable.profile);
	        alertDialog.setPositiveButton(R.string.label_camera, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog,int which) {
	            	usarCamera();
	            }
	        });
	        alertDialog.setNegativeButton(R.string.label_galeria, new DialogInterface.OnClickListener() {
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
		    			
		    		}else if(requestCode == 202){
		    			//get the returned data
		    			Bundle extras = data.getExtras();
		    			//get the cropped bitmap
		    			Bitmap foto = extras.getParcelable("data");
			    		bmp = ImageUtils.compactImages(foto);
			    		
		    			adicionarFotosPersistencia(bmp);
		    			
		    			adicionarFotosNaInterface();
			    		
		    		}
		    		
		    	}else if( requestCode == 100 || requestCode == 102){
		    		
		    		if(requestCode == 100){
		    			selectedImageUri = data.getData();
		    			
		    			cortar(requestCode);
		    			
		    		}else if(requestCode == 102){
		    			Bundle extras = data.getExtras();
		    			Bitmap foto = extras.getParcelable("data");
		    			bmp = ImageUtils.compactImages(foto);
		    			
		    			adicionarFotosPersistencia(bmp);
		    			
		    			adicionarFotosNaInterface();
			    		
			    		
		    		}
		    	}
		  
			 }else{
					selectedImageUri = null;
			 }
		 
		 /*
		 if(requestCode == 100){
	    		selectedImageUri = Uri.fromFile(photo);
	    		
	    		ContentResolver cr = getActivity().getContentResolver();
	    		
	    		Bitmap foto;
	    		try{
	    			foto = android.provider.MediaStore.Images.Media.getBitmap(cr,  selectedImageUri);
	    			
	    			bmp = ImageUtils.compactImages(foto);
	    			
	    			adicionarFotosPersistencia(bmp);
	    			
	    			adicionarFotosNaInterface();
	    			
	    		}catch(FileNotFoundException e){
	    			Log.i("Exception", e.toString());
	    			e.printStackTrace();
	    		} catch (IOException e){
	    			Log.i("Exception", e.toString());
	    			e.printStackTrace();
	    		}
	    		
	    		
	    	}else if( requestCode == 200){
	    		if (resultCode == Activity.RESULT_OK) {

					try {
						
		    			Uri selectedImage = data.getData();
		    			
		    			String[] nomeCaminho = {MediaStore.Images.Media.DATA};
		    			
		    			Cursor cursor = getActivity().getContentResolver().query(selectedImage, nomeCaminho, null, null, null);
		    			cursor.moveToFirst();
		    			
		    			int indexColuna = cursor.getColumnIndex(nomeCaminho[0]);
		    			String caminho = cursor.getString(indexColuna);
		    			
		    			bmp = ImageUtils.compactImages(BitmapFactory.decodeFile(caminho));
		    			
		    			adicionarFotosPersistencia(bmp);
		    			
		    			adicionarFotosNaInterface();
		    			
					} catch (Exception e) {
						Toast.makeText(getActivity(), "Failed to load", Toast.LENGTH_SHORT).show();
						Log.e("Camera", e.toString());
					}

				} else {
					selectedImageUri = null;
					//bmp.setImageBitmap(null);
				}
	    	}
	    	*/
	    }
	 
	 private void cortar(int requestCode){
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
		    	cropIntent.putExtra("outputX", 256);
		    	cropIntent.putExtra("outputY", 256);
		    	//retrieve data on return
		    	cropIntent.putExtra("return-data", true);
		    	//start the activity - we handle returning in onActivityResult
		        startActivityForResult(cropIntent, requestCode + 2 );  
	    	}
	    	//respond to users whose devices do not support the crop action
	    	catch(ActivityNotFoundException anfe){
	    		//display an error message
	    		String errorMessage = getString(R.string.mensagens_sem_suporte_para_esta_acao);
	    		Toast toast = Toast.makeText(getActivity(), getString(R.string.mensagens_sem_suporte_para_esta_acao), Toast.LENGTH_SHORT);
	    		toast.show();
	    	}
	    }
	 
	 public void adicionarFotosPersistencia(Bitmap foto){
		 Log.i("who is calling", whoIsCalling);
			if(whoIsCalling.equals("bicepsContraido")){
				String bicepsContraidoFotoString = ImageUtils.bitmapToString(foto);
				editor.putString("bicepsContraidoFoto", bicepsContraidoFotoString);
				
			}else if(whoIsCalling.equals("bicepsRelaxado")){
				String bicepsRelaxadoFotoString = ImageUtils.bitmapToString(foto);
				editor.putString("bicepsRelaxadoFoto", bicepsRelaxadoFotoString);
			
			
			}else if(whoIsCalling.equals("panturrilha")){
				String panturrilhaFotoString = ImageUtils.bitmapToString(foto);
				editor.putString("panturrilhaFoto", panturrilhaFotoString);
			
			
			}else if(whoIsCalling.equals("coxaProximal")){
				String coxaProximalFotoString = ImageUtils.bitmapToString(foto);
				editor.putString("coxaProximalFoto", coxaProximalFotoString);
			
			
			}else if(whoIsCalling.equals("coxaMedial")){
				coxaMedial.setImageBitmap(foto);
				String coxaMedialFotoString = ImageUtils.bitmapToString(foto);
				editor.putString("coxaMedialFoto", coxaMedialFotoString);
			
			
			}else if(whoIsCalling.equals("coxaDistal")){
				String coxaDistalFotoString = ImageUtils.bitmapToString(foto);
				editor.putString("coxaDistalFoto", coxaDistalFotoString);
			
			
			}else if(whoIsCalling.equals("antebracoOmbro")){
				String antebracoOmbroFotoString =ImageUtils.bitmapToString(foto);
				editor.putString("antebracoOmbroFoto", antebracoOmbroFotoString);
			
			
			}else if(whoIsCalling.equals("quadrilAbdomenPeito")){
				String quadrilAbdomenPeitoFotoString = ImageUtils.bitmapToString(foto);
				editor.putString("quadrilAbdomenPeitoFoto", quadrilAbdomenPeitoFotoString);
			}
			editor.commit();
			
		
	 }
	 
	 public void adicionarFotosNaInterface(){
		 if(whoIsCalling.equals("bicepsContraido")){
				bicepsContraido.setImageBitmap(bmp);
			}else if(whoIsCalling.equals("bicepsRelaxado")){
				bicepsRelaxado.setImageBitmap(bmp);
			}else if(whoIsCalling.equals("panturrilha")){
				panturrilha.setImageBitmap(bmp);
			}else if(whoIsCalling.equals("coxaProximal")){
				coxaProximal.setImageBitmap(bmp);
			}else if(whoIsCalling.equals("coxaMedial")){
				coxaMedial.setImageBitmap(bmp);
			}else if(whoIsCalling.equals("coxaDistal")){
				coxaDistal.setImageBitmap(bmp);
			}else if(whoIsCalling.equals("antebracoOmbro")){
				antebraçoOmbro.setImageBitmap(bmp);
			}else if(whoIsCalling.equals("quadrilAbdomenPeito")){
				quadrilAbdomenPeito.setImageBitmap(bmp);
			}	
	 }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	

}
