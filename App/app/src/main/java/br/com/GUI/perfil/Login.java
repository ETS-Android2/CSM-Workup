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
//
package br.com.GUI.perfil;


import br.com.Banco.*;
import br.com.Classes.Aluno;
import br.com.Classes.Atualizacoes;
import br.com.Classes.Personal;
import br.com.GUI.perfil.HomePersonal;
import br.com.GUI.perfil.HomeAluno;
import br.com.Utilitarios.ImageUtils;
import br.com.WorkUp.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	
	
	//Atributos da interface
	private TextView appName;
	private EditText login;
	private EditText senha;
	private Button cadastrar;
	private Button entrar;
	private Button cancelar;
	
	//Atributos auxiliares
	private SharedPreferences pref;
	private Editor editor;
	
	//Base de dados local
	private Banco b;
	

    
 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getActionBar().hide();
		
		mapearComponentes();
	
	}

	//Mapeamento de componentes
	
	
	public void mapearComponentes(){
		
		Typeface font2 = Typeface.createFromAsset(this.getAssets(), "BebasNeue Regular.ttf");
		appName = (TextView)this.findViewById(R.id.lblTituloAplicativo);
		

		login = (EditText)this.findViewById(R.id.usuario);
		senha = (EditText)this.findViewById(R.id.senha);
		
		
		cadastrar = (Button)this.findViewById(R.id.btnCadastrese);
		cadastrar.setTypeface(font2);
		entrar = (Button)this.findViewById(R.id.btnEntrar);
		entrar.setTypeface(font2);
		cancelar = (Button)this.findViewById(R.id.btnCancelar);
		cancelar.setTypeface(font2);
			
			
		
		b = new Banco (this,null,null,0);
		pref = this.getApplicationContext().getSharedPreferences("MyPref", 0);
		editor = pref.edit();
		
	
	}
	
	public void loginPrepare(View v){
		entrar.setText("Carregando...");
		entrar.setBackground(getResources().getDrawable(R.drawable.shape_botao_cinza));
		logar();
	}
	
	
	public void logar(){
		
		Personal p = new Personal();
		Personal rP = null;
		Aluno a = new Aluno();
		Aluno rA = null;
		
		
		//Tenta logar o Personal
		rP = p.buscarPersonal(b, login.getText().toString());
			
			//Log.i("personal if ", rP.toString());
			//Logar Personal
				try{
					final Personal item = new Personal().buscarPersonalWeb(login.getText().toString());
					
					
					if (item!=null && item.getSenha().equals(
							senha.getText().toString()) && !(senha.getText().toString().equals("isFacebookUser"))){
						
						if(rP != null){
							Log.i("INFORMAÇÃO", "O personal já existe na base de dados local");
							
						}else{
							byte[] fotoPersonal  = ImageUtils.bitmapToByteArray(item.buscarFotoPersonalWeb(item.getUsuario()));
							if(item.salvar(b,fotoPersonal)){
								Log.i("INFORMAÇÃO", "Salvo com sucesso na base local");
								new Atualizacoes(0,"local",item.getUsuario(),"atualizarAlunos","",0,0).salvarAtualizacao(b);
								new Atualizacoes(0,"local",item.getUsuario(),"atualizarTodasAvaliacoes","",0,0).salvarAtualizacao(b);
								new Atualizacoes(0,"local",item.getUsuario(),"atualizarAulasPersonal","",0,0).salvarAtualizacao(b);
								new Atualizacoes(0,"local",item.getUsuario(),"atualizarTodosTreinamentos","",0,0).salvarAtualizacao(b);
								new Atualizacoes(0,"local",item.getUsuario(),"atualizarTodosAcompanhamentos","",0,0).salvarAtualizacao(b);
								
							} 
						}
						
						editor.putString("telefone", item.getTelefone());
						editor.putString("usuario", item.getUsuario());
						editor.putString("senha", item.getSenha());
						editor.putString("tipo", "personal");
						editor.putString("sexo", item.getSexo());
						editor.putBoolean("isFacebookUser", false);
						editor.commit();
						//Toast.makeText(Login.this, "Bem-vindo " + item.getNome() , Toast.LENGTH_LONG).show();
						this.finish();
						Intent intent = new Intent(this,HomePersonal.class);
						//Log.i("eu cheguei", "eu cheguei na intent do personal");
						startActivity(intent);
						//Toast.makeText(Login.this, "Salvo no servidor", Toast.LENGTH_SHORT).show();
						return;
					}
				}catch(Exception ex){
					/*
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
					alertDialog.setTitle("Ops...");
					alertDialog.setMessage("Você pode ter errado seu Login ou sua senha... \nPor favor, Verifique e tente novamente.");
					alertDialog.setIcon(R.drawable.profile);
					alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				         public void onClick(DialogInterface dialog,int which) {
				        	 dialog.cancel();
				         }
					});
					// Showing Alert Message
				        alertDialog.show();
				      
					
					//Toast.makeText(this, "Personal não encontrado", Toast.LENGTH_SHORT).show();
					Log.i("Erro: Login", "Personal Não encontrado");*/
				}
		
				
				
				
				
				
				
				
				
				
			//Tenta Logar o Aluno	
				
			rA = a.buscarAlunoEspecifico(b, login.getText().toString());
	 
				try{
					final Aluno item = new Aluno().buscarAlunoEspecificoWeb(login.getText().toString());
					Log.i("Aluno recebido na busca.", item.toString());
					if (item!=null && item.getSenha().equals(senha.getText().toString()) && !(senha.getText().toString().equals("isFacebookUser"))){
						if(rA != null){
							Log.i("INFORMAÇÃO", "O personal já existe na base de dados local");
						}else{
							byte[] fotoAluno  = ImageUtils.bitmapToByteArray(item.buscarFotoAlunoWeb(item.getUsuario()));
							if(item.salvar(b,fotoAluno)){
								Log.i("INFORMAÇÃO", "Salvo com sucesso na base local");
								new Atualizacoes(0,"local",item.getUsuario(),"atualizarPersonal","",0,0).salvarAtualizacao(b);
								new Atualizacoes(0,"local",item.getUsuario(),"atualizarTodasAvaliacoes","",0,0).salvarAtualizacao(b);
								new Atualizacoes(0,"local",item.getUsuario(),"atualizarAulasAluno","",0,0).salvarAtualizacao(b);
								new Atualizacoes(0,"local",item.getUsuario(),"atualizarTodosTreinamentos","",0,0).salvarAtualizacao(b);
								new Atualizacoes(0,"local",item.getUsuario(),"atualizarTreinamentoPreescrito","",0,0).salvarAtualizacao(b);
								new Atualizacoes(0,"local",item.getUsuario(),"atualizarTodosAcompanhamentos","",0,0).salvarAtualizacao(b);
								
							} 
						}
						
					
							//Log.i("na hora de logar", "passei por aki");
							editor.putString("telefone", item.getTelefone());
							editor.putString("usuario", item.getUsuario());
							editor.putString("senha", item.getSenha());
							editor.putString("tipo", "aluno");
							editor.putString("sexo", item.getSexo());
							editor.putBoolean("isFacebookUser", false);
							editor.commit();
							
							///Log.i("shared pref", pref.getString("usuario", null));
							Toast.makeText(this, "Bem-vindo " + item.getNome() , Toast.LENGTH_LONG).show();
							this.finish();
							Intent intent = new Intent(this,HomeAluno.class);
							//Log.i("eu cheguei na intent do aluno", "eu cheguei na intent do aluno");
							startActivity(intent);
							//Toast.makeText(Login.this, "Salvo no servidor", Toast.LENGTH_SHORT).show();
							
								return;		
					}
				}catch(Exception ex){
					entrar.setText("Entrar");
					entrar.setBackground(getResources().getDrawable(R.drawable.shape_botao_azul));
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
					alertDialog.setTitle("Ops...");
					alertDialog.setMessage("Você pode ter errado seu Login ou sua senha... \nPor favor, Verifique e tente novamente.");
					alertDialog.setIcon(R.drawable.profile);
					alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				         public void onClick(DialogInterface dialog,int which) {
				        	 dialog.cancel();
				         }
					});
					// Showing Alert Message
				        alertDialog.show();
					//Toast.makeText(this, "Aluno não encontrado", Toast.LENGTH_SHORT).show();
					Log.i("Erro: Login", ex.toString());
				}
				
	}
	
	public void cadastrar(View v){
		Intent intent = new Intent(this,Cadastrar.class);
    	startActivity(intent);
    	this.finish();
	}

	public void cancelar(View v){
		this.finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
