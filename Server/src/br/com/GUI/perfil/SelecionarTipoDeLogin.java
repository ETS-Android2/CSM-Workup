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

 package br.com.GUI.perfil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.Facebook.DialogListener;

import br.com.Banco.Banco;
import br.com.Classes.Aluno;
import br.com.Classes.Atualizacoes;
import br.com.Classes.Personal;
import br.com.Utilitarios.ImageUtils;
import br.com.Utilitarios.WebService;
import br.com.WorkUp.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class SelecionarTipoDeLogin extends Activity{
	
	private Button loginConvencional;
	private Button loginComFacebook;
	private String whatTypeIs = null;
	
	//shared preferences
	private SharedPreferences pref;
	private Editor editor;
	
	Banco b;
	
	   // Instance of Facebook Class
    private SharedPreferences mPrefs;
    private Facebook facebook = new Facebook(WebService.getAppId());
    private AsyncFacebookRunner mAsyncRunner;
    String FILENAME = "AndroidSSO_data";
    
  
    
    //Atributos para cadastro no facebook
    
    private static String telefoneFb;
	private static String nomeFb;
	private static String sexoFb;
	private static String tipoDePerfilFb;
	private static String usuarioFb;
	private static String senhaFb;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selecionar_tipo_de_login);
		getActionBar().hide();
		
		mapearComponentes();
		
		loginAutomatico();
	}
	
	public void loginAutomatico(){
		if( pref.getString("telefone",null) != null 
				&& pref.getString("usuario", null) != null 
					&& pref.getString("senha", null) != null){
		 
		
			
			Personal p = new Personal();
			Personal rP;
			Aluno a = new Aluno();
			Aluno rA;
			
			rP = p.buscarPersonal(b,  pref.getString("usuario",null));
			rA = a.buscarAlunoEspecifico(b,  pref.getString("usuario",null));
			
			if(rP != null){
				if(rP.getSenha().equals(pref.getString("senha",null))){
					Toast.makeText(this, "Bem-vindo " + rP.getNome() , Toast.LENGTH_LONG).show();
					Intent intent = new Intent(this,HomePersonal.class);
					startActivity(intent);
					finish();
					new Atualizacoes(0,"local",rP.getUsuario(),"atualizarAlunos",null,0,0).salvarAtualizacao(b);
					new Atualizacoes(0,"local",rP.getUsuario(),"atualizarTodasAvaliacoes","",0,0).salvarAtualizacao(b);
					new Atualizacoes(0,"local",rP.getUsuario(),"atualizarAulasPersonal","",0,0).salvarAtualizacao(b);
					new Atualizacoes(0,"local",rP.getUsuario(),"atualizarTodosTreinamentos","",0,0).salvarAtualizacao(b);
					
				}
				
			}else if (rA != null){
				if(rA.getSenha().equals(pref.getString("senha", null))){
					Toast.makeText(this, "Bem-vindo " + rA.getNome() , Toast.LENGTH_LONG).show();
					Intent intent = new Intent(this,HomeAluno.class);
			    	startActivity(intent);
			    	finish();
			    	new Atualizacoes(0,"local",rA.getUsuario(),"atualizarPersonal","",0,0).salvarAtualizacao(b);
					new Atualizacoes(0,"local",rA.getUsuario(),"atualizarTodasAvaliacoes","",0,0).salvarAtualizacao(b);
					new Atualizacoes(0,"local",rA.getUsuario(),"atualizarAulasAluno","",0,0).salvarAtualizacao(b);
					new Atualizacoes(0,"local",rA.getUsuario(),"atualizarTodosTreinamentos","",0,0).salvarAtualizacao(b);
					new Atualizacoes(0,"local",rA.getUsuario(),"atualizarTreinamentoPreescrito","",0,0).salvarAtualizacao(b);
						
				}
			}
		 }
		
	}
	
	public void loginConvencional(View v){
		Intent i = new Intent(this,Login.class);
		i.putExtra("tipoLogin", "convencional");
		startActivity(i);
	}
	
	public void loginComFacebook(View v){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Selecione seu tipo de Perfil");
		alertDialog.setMessage("O WorkUp é util tanto para treinadores quanto para alunos, por favor, \nselecione abaixo qual o tipo de perfil que você deseja criar.");
		alertDialog.setIcon(R.drawable.profile);
		alertDialog.setPositiveButton("Treinador", new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog,int which) {
	        	 whatTypeIs = "personal";
	        	 loginComFacebook();
	         }
		});
		alertDialog.setNegativeButton("Aluno", new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog,int which) {
	        	 whatTypeIs = "aluno";
	        	 loginComFacebook();
	         }
		});
		// Showing Alert Message
	        alertDialog.show();
	      
	       
	}
	
	public void mapearComponentes(){
		loginConvencional = (Button)findViewById(R.id.btnLoginConvencional);
		loginComFacebook = (Button) findViewById(R.id.btnLoginComFacebook);
		pref = this.getApplicationContext().getSharedPreferences("MyPref", 0);
		editor = pref.edit();
		mPrefs = this.getPreferences(this.MODE_PRIVATE);
		mAsyncRunner = new AsyncFacebookRunner(facebook);
		b = new Banco(this,null,null,0);
	}

	@SuppressWarnings("deprecation")
	public void loginComFacebook(){
		clearFbData();
		//Log.i("clicou", "logarComFacebook");
		Log.i("issessionvalid"," "+ String.valueOf(facebook.isSessionValid()));
		
	
		if(facebook.isSessionValid()){
			getProfileInformation();
			
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
			alertDialog.setTitle("Carregando");
			alertDialog.setMessage("Carregando informações... Por favor, Espere alguns momentos...");
			alertDialog.setIcon(R.drawable.profile);
			alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {	
				public void onClick(DialogInterface dialog,int which) {
		        	dialog.cancel();
		         }
			});
			// Showing Alert Message
		        alertDialog.show();
		}else{
			 String access_token = mPrefs.getString("access_token", null);
			    long expires = mPrefs.getLong("access_expires", 0);
			 
			    if (access_token != null) {
			        facebook.setAccessToken(access_token);
			    }
			 
			    if (expires != 0) {
			        facebook.setAccessExpires(expires);
			    }
			 
			    if (!facebook.isSessionValid()) {
			        facebook.authorize(this,new String[] {  "publish_stream" },new DialogListener(){
			        	//"name","email","gender","username"
			                    @Override
			                    public void onCancel() {
			                        // Function to handle cancel event
			                    }
			 
			                    @Override
			                    public void onComplete(Bundle values) {
			                        // Function to handle complete event
			                        // Edit Preferences and update facebook acess_token
			                        SharedPreferences.Editor editor = mPrefs.edit();
			                        editor.putString("access_token",
			                                facebook.getAccessToken());
			                        editor.putLong("access_expires",
			                                facebook.getAccessExpires());
			                        editor.commit();
			                        //Log.i("entrei aki ", "entrei no on complete");
		                        
			                        getProfileInformation();
			                       
			                	}
			                
			 
			                    @Override
			                    public void onError(DialogError error) {
			                        // Function to handle error
			 
			                    }
			 
			                    @Override
			                    public void onFacebookError(FacebookError fberror) {
			                        // Function to handle Facebook errors
			 
			                    }
			 
			                });
			    }
		}
		
	   
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebook.authorizeCallback(requestCode, resultCode, data);
		
		Log.i("rodei o result", "rodei o result");
		
	}
	
	@SuppressWarnings("deprecation")
	public void getProfileInformation(){
			mAsyncRunner.request("me", new RequestListener() {
				@Override
				public void onComplete(String response, Object state) {
					Log.d("Profile", response);
					String json = response;
					try {
						// Facebook Profile JSON data
						JSONObject profile = new JSONObject(json);
						Log.i("JSON", profile.toString());
						
						
						//final String telefone = "0000";//profile.getString("phone");
						
						// getting name of the user
						final String nome = profile.getString("name");
					
						// getting email of the user
						final String email = profile.getString("email");
						
						final String gender = profile.getString("gender");
						
						final String usuario = profile.getString("email");
												
						
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								String tipo = null;
								if(whatTypeIs.equals("personal")){
									tipo = "personal";
								}else if (whatTypeIs.equals("aluno")){
									tipo = "aluno";
								}
								String sexo= null;
								if(gender.equals("male")){
									sexo = "Masculino";
								}else if(gender.equals("female")){
									sexo = "Feminino";
								}
								//telefoneFb = telefone;
								nomeFb = nome;
								sexoFb = sexo;
								tipoDePerfilFb = tipo;
								usuarioFb = usuario;
								senhaFb = "isFacebookUser";	
								Log.i("atributos recebidos do face", "telefone = " + telefoneFb + 
										" nome "  + nome + 
										" sexo " + sexo + 
										" tipo " + tipoDePerfilFb+ 
										" usuario " + usuarioFb + 
										" senha " + senhaFb);
									
		                		concluirLoginFacebook();
								
							}

						});

						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onIOException(IOException e, Object state) {
				}

				@Override
				public void onFileNotFoundException(FileNotFoundException e,
						Object state) {
				}

				@Override
				public void onMalformedURLException(MalformedURLException e,
						Object state) {
				}

				@Override
				public void onFacebookError(FacebookError e, Object state) {
				}
			});
			
	}

	public void concluirLoginFacebook(){
		Personal facebookUserPersonal = new Personal(telefoneFb,nomeFb,null,null,sexoFb,usuarioFb,senhaFb,null);
		Aluno facebookUserAluno = new Aluno(telefoneFb,nomeFb,null,null,sexoFb,usuarioFb,senhaFb,null,0,0,null,0); 
		
		//verifica se o usuario já foi cadastrado
		boolean alunoExiste = false;
		boolean personalExiste = false;
		
		if(new Personal().buscarPersonalWeb(usuarioFb) != null){
			personalExiste = true;
		}
		if(new Aluno().buscarAlunoEspecificoWeb(usuarioFb) != null){
			alunoExiste = true;
		}
		
		Log.i("Usuario do Facebook", "" + facebookUserPersonal.toString());
		Log.i("Usuario do Facebook", "" + facebookUserAluno.toString());
		
		if(facebookUserPersonal != null){
			if(whatTypeIs.equals("personal")){
				if(!personalExiste){
					final boolean res = facebookUserPersonal.salvarPersonalWeb(null);
					if(res){
						facebookUserPersonal.salvar(b,ImageUtils.drawableToByteArray(getResources().getDrawable(R.drawable.profile)));
					}
				}
				
				if(personalExiste){
					new Atualizacoes(0,"local",facebookUserAluno.getUsuario(),"atualizarAlunos","",0,0).salvarAtualizacao(b);
					new Atualizacoes(0,"local",facebookUserAluno.getUsuario(),"atualizarTodasAvaliacoes","",0,0).salvarAtualizacao(b);
					new Atualizacoes(0,"local",facebookUserAluno.getUsuario(),"atualizarAulasPersonal","",0,0).salvarAtualizacao(b);
					new Atualizacoes(0,"local",facebookUserAluno.getUsuario(),"atualizarTodosTreinamentos","",0,0).salvarAtualizacao(b);
					new Atualizacoes(0,"local",facebookUserAluno.getUsuario(),"atualizarTodosAcompanhamentos","",0,0).salvarAtualizacao(b);
					
				}
				
				
				editor.putString("telefone", facebookUserPersonal.getTelefone());
				editor.putString("usuario", facebookUserPersonal.getUsuario());
				editor.putString("senha", facebookUserPersonal.getSenha());
				editor.putString("tipo", "personal");
				editor.putString("sexo", facebookUserPersonal.getSexo());
				editor.putBoolean("isFacebookUser", true);
				editor.commit();
				//Log.i("access token", facebook.getAccessToken());
				Toast.makeText(this, "Bem-vindo " + facebookUserPersonal.getNome() , Toast.LENGTH_LONG).show();
				this.finish();
				Intent intent = new Intent(this,br.com.GUI.perfil.HomePersonal.class);
		    	startActivity(intent);
				//Toast.makeText(this, "Salvo no servidor", Toast.LENGTH_SHORT).show();
			}else if (whatTypeIs.equals("aluno")){
				
				if(!alunoExiste){
					final boolean res = facebookUserAluno.salvarAlunoWeb(null);
					if(res){
						facebookUserAluno.salvar(b,ImageUtils.drawableToByteArray(getResources().getDrawable(R.drawable.profile)));
					}
				}
				
				if(!alunoExiste){
					new Atualizacoes(0,"local",facebookUserAluno.getUsuario(),"atualizarPersonal","",0,0).salvarAtualizacao(b);
					new Atualizacoes(0,"local",facebookUserAluno.getUsuario(),"atualizarTodasAvaliacoes","",0,0).salvarAtualizacao(b);
					new Atualizacoes(0,"local",facebookUserAluno.getUsuario(),"atualizarAulasAluno","",0,0).salvarAtualizacao(b);
					new Atualizacoes(0,"local",facebookUserAluno.getUsuario(),"atualizarTodosTreinamentos","",0,0).salvarAtualizacao(b);
					new Atualizacoes(0,"local",facebookUserAluno.getUsuario(),"atualizarTodosAcompanhamentos","",0,0).salvarAtualizacao(b);
					
				}
					editor.putString("telefone", facebookUserAluno.getTelefone());
					editor.putString("usuario", facebookUserAluno.getUsuario());
					editor.putString("senha", facebookUserAluno.getSenha());
					editor.putString("tipo", "aluno");
					editor.putString("sexo", facebookUserAluno.getSexo());
					editor.putBoolean("isFacebookUser", true);
					editor.commit();
					Toast.makeText(this, "Bem-vindo " + facebookUserAluno.getNome() , Toast.LENGTH_LONG).show();
					this.finish();
					Intent intent = new Intent(this,br.com.GUI.perfil.HomeAluno.class);
			    	startActivity(intent);
					Toast.makeText(this, "Salvo no servidor", Toast.LENGTH_SHORT).show();
				
			}

		}
	}
	
	@SuppressWarnings("deprecation")
	public void logoutFromFacebook() {
	    mAsyncRunner.logout(this, new RequestListener() {
	        @Override
	        public void onComplete(String response, Object state) {
	            Log.d("Logout from Facebook", response);
	            if (Boolean.parseBoolean(response) == true) {
	                // User successfully Logged out
	            }
	        }
	 
	        @Override
	        public void onIOException(IOException e, Object state) {
	        }
	 
	        @Override
	        public void onFileNotFoundException(FileNotFoundException e,
	                Object state) {
	        }
	 
	        @Override
	        public void onMalformedURLException(MalformedURLException e,
	                Object state) {
	        }
	 
	        @Override
	        public void onFacebookError(FacebookError e, Object state) {
	        }
	    });
	}
	
	
	public void clearFbData(){
		telefoneFb = null;
		nomeFb = null;
		sexoFb = null;
		tipoDePerfilFb = null;
		usuarioFb = null;
		senhaFb = null;
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
