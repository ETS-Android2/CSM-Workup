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

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import br.com.Banco.Banco;
import br.com.Classes.Aluno;
import br.com.Classes.Personal;
import br.com.GUI.treinamentos.AcompanhamentoTreinamentoFragment;
import br.com.GUI.treinamentos.PreescreverTreinamento;
import br.com.Utilitarios.ImageUtils;
import br.com.Utilitarios.WebService;
import br.com.WorkUp.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AdicionarAluno extends Activity {
	

	// Atributos de interface
	private ImageView foto;	
	private TextView usuario;
	private TextView nome;
	private TextView dataDeNascimento;
	private TextView sexo;
	private TextView email;
	private Button adicionarRemover;
	
	
	// Base de dados local
	private Banco b;
	
	//atributos auxiliares
	private String parametro;
	private Aluno c;
	private SharedPreferences pref;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adicionar_aluno);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		mapearComponentes();
		carregarPerfil();
	}
	
	public void mapearComponentes(){
		
		foto = (ImageView)findViewById(R.id.imgFotoAdicionarAluno);
		usuario = (TextView)findViewById(R.id.infoUsuarioAluno);
		nome = (TextView)findViewById(R.id.infoNomeAluno);
		dataDeNascimento = (TextView)findViewById(R.id.infoDataDeNascimentoAluno);
		sexo = (TextView)findViewById(R.id.infoSexoAluno);
		email = (TextView)findViewById(R.id.infoEmailAluno);
		adicionarRemover = (Button) findViewById(R.id.btnAdicionarRemover);
		Typeface font = Typeface.createFromAsset(getAssets(), "BebasNeue Bold.ttf");
		adicionarRemover.setTypeface(font);
		b = new Banco(this, null, null, 0);
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
	
	}
	
	
	public void carregarPerfil(){
		
		//Carregar do servidor Web
		
		parametro = getIntent().getExtras().getString("usuario");
		
		c = new Aluno().buscarAlunoEspecifico(b,parametro);
		
		if(c == null){
			c = new Aluno().buscarAlunoEspecificoWeb(parametro);
			byte[] foto = ImageUtils.bitmapToByteArray(c.buscarFotoAlunoWeb(parametro));
			c.salvar(b, foto);
		}
		
		if(c != null){
			Log.i("o que veio dentro do c", c.toString());
			if (c.getUsuarioPersonal() == "" || 
					c.getUsuarioPersonal().equals("NULL") || 
					c.getUsuarioPersonal().equals("") || 
					c.getUsuarioPersonal().equals("null") || 
					c.getUsuarioPersonal() == null){
				adicionarRemover.setBackground(getResources().getDrawable(R.drawable.shape_botao_azul));
				adicionarRemover.setText("Adicionar");
			}else { 
				adicionarRemover.setBackground(getResources().getDrawable(R.drawable.shape_botao_cinza));
				adicionarRemover.setText("Já adicionado!");
			}
			
			byte[] fotoAluno = Base64.decode(c.getFoto(), Base64.DEFAULT);
			foto.setImageBitmap(BitmapFactory.decodeByteArray(fotoAluno,0,fotoAluno.length));
			
			if(c.getFoto() == null){
				usuario.setText("");
			}
			
			usuario.setText(c.getUsuario());
			if(usuario.getText().equals("anyType{}")){
				usuario.setText("");
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
		}
								
	}

	
	
	public void adicionarRemoverAluno(View v){
		Log.i("aluno adicionar Aluno", c.toString());
		if (c.getUsuarioPersonal() == "" || 
				c.getUsuarioPersonal().equals("NULL") || 
				c.getUsuarioPersonal().equals("null") || 
				c.getUsuarioPersonal() == null ||
				c.getUsuarioPersonal().equals("") ){
			boolean op = new Aluno().adicionarAlunoWeb(pref.getString("usuario", null),c.getUsuario());
			
			if(op){
							
				if(c.adicionarAluno(b, pref.getString("usuario", null))){
					Toast.makeText(AdicionarAluno.this, "Adicionado com Sucesso", Toast.LENGTH_SHORT).show();
					finish();
				}else{
					Toast.makeText(AdicionarAluno.this, "Erro ao adicionar o personal", Toast.LENGTH_SHORT).show();
				}
			}

		}
		
	}
	
	
	
	
	//MENUS
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.adicionar_aluno, menu);
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
