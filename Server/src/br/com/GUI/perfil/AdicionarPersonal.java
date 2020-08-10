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

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import br.com.Banco.Banco;
import br.com.Classes.Aluno;
import br.com.Classes.Personal;
import br.com.Utilitarios.ImageUtils;
import br.com.Utilitarios.WebService;
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
import android.util.Base64;
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

public class AdicionarPersonal extends Activity {
	
	
	//Atributos de interface
	private ImageView foto;	
	private Button acompanhamento;
	private TextView usuario;
	private TextView nome;
	private TextView dataDeNascimento;
	private TextView sexo;
	private TextView email;
	private Button adicionarRemover;
	
	//base de dados local;
	private Banco b;
	private SharedPreferences pref;
	
	//atributos Auxiliares
	private Personal p = new Personal();
	private Aluno c = new Aluno();
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adicionar_personal);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		mapearComponentes();
		
		carregarPerfil();
		
			
	}
	
	public void mapearComponentes(){
		
		foto = (ImageView)findViewById(R.id.imgFotoAdicionarPersonal);
		usuario = (TextView)findViewById(R.id.infoUsuarioPersonal);
		nome = (TextView)findViewById(R.id.infoNomePersonal);
		dataDeNascimento = (TextView)findViewById(R.id.infoDataDeNascimentoPersonal);
		sexo = (TextView)findViewById(R.id.infoSexoPersonal);
		email = (TextView)findViewById(R.id.infoEmailPersonal);
		adicionarRemover = (Button) findViewById(R.id.btnAdicionarRemoverPersonal);
		Typeface font = Typeface.createFromAsset(getAssets(), "BebasNeue Bold.ttf");
		adicionarRemover.setTypeface(font);
		b = new Banco(this, null, null, 0);
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
	}
	
	public void carregarPerfil(){
		//TODO Certificar que a busca de personais e alunos est�� salvando os personais e alunos no banco local
		
		final String parametro = getIntent().getExtras().getString("usuario");
		
		p = new Personal().buscarPersonal(b,parametro);	
		
		
		if(p == null){
			p = new Personal().buscarPersonalWeb(parametro);
			byte[] foto = ImageUtils.bitmapToByteArray(p.buscarFotoPersonalWeb(parametro));
			p.salvar(b, foto);
		}
		
		if(p!=null){		
			if(pref.getString("usuario", null).equals(p.getUsuario())){
				adicionarRemover.setBackground(getResources().getDrawable(R.drawable.shape_botao_cinza));
				adicionarRemover.setText("Já adicionado");
			}else{
				adicionarRemover.setBackground(getResources().getDrawable(R.drawable.shape_botao_azul));
				adicionarRemover.setText("Adicionar");
			}
			byte[] fotoPersonal = Base64.decode(p.getFoto(), Base64.DEFAULT);
			foto.setImageBitmap(BitmapFactory.decodeByteArray(fotoPersonal,0,fotoPersonal.length));
			usuario.setText(p.getUsuario());
			if(usuario.getText().equals("anyType{}")){
				usuario.setText("");
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
	
	public void adicionarRemoverPersonal(View v){
		
		c = new Aluno().buscarAlunoEspecifico(b,pref.getString("usuario", null));

		
		if (c.getUsuarioPersonal().equals("")||
				c.getUsuarioPersonal() == "" || 
				c.getUsuarioPersonal().equals("NULL") || 
				c.getUsuarioPersonal().equals("null") || 
				c.getUsuarioPersonal() == null){
			boolean op = c.adicionarPersonalWeb(p.getUsuario(), c.getUsuario());
			c.setUsuarioPersonal(p.getUsuario());
			
			//verifica se o personal j�� existe
			Personal aux = new Personal().buscarPersonal(b, p.getUsuario());
			
			if(op && c.adicionarAluno(b, p.getUsuario())){
					Toast.makeText(AdicionarPersonal.this, "Adicionado com Sucesso", Toast.LENGTH_SHORT).show();
					finish();
				}else{
						Toast.makeText(AdicionarPersonal.this, "adicionado com sucesso", Toast.LENGTH_SHORT).show();
						finish();
					}
		}else{
			finish();
		}
	}

	
	//MENUS
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.adicionar_personal, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if(id ==  android.R.id.home){
			finish();
			//NavUtils.navigateUpFromSameTask(this);
	        return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
