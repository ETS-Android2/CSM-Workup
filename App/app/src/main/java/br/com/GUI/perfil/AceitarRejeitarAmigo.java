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

import br.com.Banco.Banco;
import br.com.Classes.Aluno;
import br.com.Classes.Personal;
import br.com.Utilitarios.ImageUtils;
import br.com.WorkUp.R;
import br.com.WorkUp.R.id;
import br.com.WorkUp.R.layout;
import br.com.WorkUp.R.menu;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class AceitarRejeitarAmigo extends Activity {
	
	private ImageView foto;	
	private TextView usuario;
	private TextView nome;
	private TextView dataDeNascimento;
	private TextView sexo;
	private TextView email;
	private Button adicionar;
	private Button remover;
	
	//base de dados local;
	private Banco b;
	private SharedPreferences pref;
	
	//atributos Auxiliares
	private Personal p = new Personal();
	private Aluno c = new Aluno();
	private String usr = null;
	private String tipo = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aceitar_rejeitar_amigo);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		usr = getIntent().getExtras().getString("usuario");
		tipo = getIntent().getExtras().getString("tipo");
		
		
		mapearComponentes();
		
		carregarPerfil();
	}
	
	public void carregarPerfil(){
		
		
		if(tipo.equals("personal")){
			
			c = new Aluno().buscarAlunoEspecificoWeb(usr);	
			
			if(c!=null){	
				
				Bitmap fotoAluno =  c.buscarFotoAlunoWeb(c.getUsuario());
				
				foto.setImageBitmap(fotoAluno);
				
				c.setUsuarioPersonal(pref.getString("usuario", null));
				
				Aluno aux = c.buscarAlunoEspecifico(b, c.getUsuario());
				if(aux == null){
					c.salvar(b, ImageUtils.bitmapToByteArray(fotoAluno));
				}else{
					c.atualizar(b, ImageUtils.bitmapToByteArray(fotoAluno));
				}
				
				
				this.usuario.setText(c.getUsuario());
				if(this.usuario.getText().equals("anyType{}")){
					this.usuario.setText("");
				}
				nome.setText(c.getNome());
				if(nome.getText().equals("anyType{}")){
					nome.setText("");
				}
				dataDeNascimento.setText(c.getDataDeNascimento());
				if(dataDeNascimento.getText().equals("anyType{}")){
					dataDeNascimento.setText("");
				}
				sexo.setText(c.getSexo());
				if(sexo.getText().equals("anyType{}")){
					sexo.setText("");
				}
				email.setText(c.getEmail());
				if(email.getText().equals("anyType{}")){
					email.setText("");
				}								
			}else{
				Toast.makeText(this, "Falha ao carregar o perfil! ", Toast.LENGTH_SHORT).show();
			}
			
		}else if (tipo.equals("aluno")){
			c = new Aluno().buscarAlunoEspecificoWeb(pref.getString("usuario", null));
			p = new Personal().buscarPersonalWeb(usr);	
			
			if(p!=null){		
				
				Bitmap fotoPersonal = p.buscarFotoPersonalWeb(usr);
				foto.setImageBitmap(fotoPersonal);
				
				Personal auxP = p.buscarPersonal(b, p.getUsuario());
				
				if(auxP == null){
					p.salvar(b, ImageUtils.bitmapToByteArray(fotoPersonal));
				}else{
					p.atualizar(b, ImageUtils.bitmapToByteArray(fotoPersonal));
					
				}
				c.setUsuarioPersonal(p.getUsuario());
				c.atualizar(b, c.getFoto().getBytes());
				
				this.usuario.setText(p.getUsuario());
				if(this.usuario.getText().equals("anyType{}")){
					this.usuario.setText("");
				}
				nome.setText(p.getNome());
				if(nome.getText().equals("anyType{}")){
					nome.setText("");
				}
				dataDeNascimento.setText(p.getDataDeNascimento());
				if(dataDeNascimento.getText().equals("anyType{}")){
					dataDeNascimento.setText("");
				}
				sexo.setText(p.getSexo());
				if(sexo.getText().equals("anyType{}")){
					sexo.setText("");
				}
				email.setText(p.getEmail());
				if(email.getText().equals("anyType{}")){
					email.setText("");
				}								
			}else{
				Toast.makeText(this, "Falha ao carregar o perfil! ", Toast.LENGTH_SHORT).show();
			}
			
		}
		
		
			
	}
	
	public void aceitarConvite(View v){
		if (tipo.equals("personal")){
			
			Log.i("c e user", "c " + c + "usr " + usr);
			if(c.aceitarConvite(b,tipo) && c.aceitarConviteWeb(usr,tipo)){
				Toast.makeText(this, "Adicionado com sucesso!", Toast.LENGTH_SHORT).show();
				finish();
			}else{
				Toast.makeText(this, "Erro ao adicionar!", Toast.LENGTH_SHORT).show();
				finish();
			}
		}else if (tipo.equals("aluno")){
			if(c.aceitarConvite(b,tipo) && c.aceitarConviteWeb(pref.getString("usuario", null),tipo)){
				Toast.makeText(this, "Adicionado com sucesso!", Toast.LENGTH_SHORT).show();
				finish();
			}else{
				Toast.makeText(this, "Erro ao adicionar!", Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}
	
	public void rejeitarConvite(View v){
		if (tipo.equals("personal")){
			if(c.rejeitarConvite(b) && c.rejeitarConviteWeb(c.getUsuario(),tipo)){
				Toast.makeText(this, "Adicionado com sucesso!", Toast.LENGTH_SHORT).show();
				finish();
			}else{
				Toast.makeText(this, "Erro ao adicionar!", Toast.LENGTH_SHORT).show();
				finish();
			}
		}else if (tipo.equals("aluno")){
			if(c.rejeitarConvite(b) && c.rejeitarConviteWeb(pref.getString("usuario", null),tipo)){
				Toast.makeText(this, "Adicionado com sucesso!", Toast.LENGTH_SHORT).show();
				finish();
			}else{
				Toast.makeText(this, "Erro ao adicionar!", Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}
	
	
	public void mapearComponentes(){
		
		foto = (ImageView)findViewById(R.id.imgFoto_aceitarRejeitarAmigo);
		usuario = (TextView)findViewById(R.id.infoUsuario_aceitarRejeitarAmigo);
		nome = (TextView)findViewById(R.id.infoNome_aceitarRejeitarAmigo);
		dataDeNascimento = (TextView)findViewById(R.id.infoDataDeNascimento_aceitarRejeitarAmigo);
		sexo = (TextView)findViewById(R.id.infoSexo_aceitarRejeitarAmigo);
		email = (TextView)findViewById(R.id.infoEmail_aceitarRejeitarAmigo);
		adicionar = (Button) findViewById(R.id.btnAceitarConvite);
		remover = (Button) findViewById(R.id.btnRejeitarConvite);
		Typeface font = Typeface.createFromAsset(getAssets(), "BebasNeue Bold.ttf");
		adicionar.setTypeface(font);
		remover.setTypeface(font);
		b = new Banco(this, null, null, 0);
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.aceitar_rejeitar_amigo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id ==  android.R.id.home){
			finish();
			//NavUtils.navigateUpFromSameTask(this);
	        return true;
		}
		return super.onOptionsItemSelected(item);
	}

	

}
