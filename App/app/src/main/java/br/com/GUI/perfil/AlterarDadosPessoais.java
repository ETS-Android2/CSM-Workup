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
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import br.com.Banco.Banco;
import br.com.Classes.Aluno;
import br.com.Classes.Aula;
import br.com.Classes.Personal;
import br.com.Utilitarios.WebService;
import br.com.WorkUp.R;
import br.com.WorkUp.R.id;
import br.com.WorkUp.R.layout;
import br.com.WorkUp.R.menu;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class AlterarDadosPessoais extends Activity {
	
	//Atributos de Interface
	private EditText senhaAntiga;
	private EditText novaSenha;
	private EditText confirmarSenha;
	private Button atualizarSenha;
	
	//Persistencias
	private Banco b;
	private SharedPreferences pref;
	
	//atributos auxiliares
	Personal p;
	Aluno a;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alterar_dados_pessoais);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		senhaAntiga = (EditText) findViewById(R.id.edtSenhaAntiga);
		novaSenha = (EditText) findViewById(R.id.edtNovaSenha);
		confirmarSenha = (EditText) findViewById(R.id.edtConfirmarSenha);
		atualizarSenha = (Button) findViewById(R.id.btnAtualizarSenha);
		
		Typeface font = Typeface.createFromAsset(getAssets(), "BebasNeue Bold.ttf");
		atualizarSenha.setTypeface(font);
		
		b = new Banco(this,null,null,0);
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		

		
	}
	
	public void atualizarDadosPessoais(View v){
		if (pref.getString("tipo", null).equals("personal")){
			
			//Inicia altera����o no servidor
			
			p = new Personal().buscarPersonal(b, pref.getString("usuario", null));
			if(p.getSenha().equals(senhaAntiga.getText().toString()) && 
					novaSenha.getText().toString().equals(confirmarSenha.getText().toString())){
					
				p.setSenha(novaSenha.getText().toString());
					
				boolean flag = p.atualizarPersonalWeb();
				if(p.atualizarDadosPessoais(b)&& flag){
					Toast.makeText(AlterarDadosPessoais.this, "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
					finish();
				}else{
					Toast.makeText(AlterarDadosPessoais.this, "Erro ao atualizar a senha", Toast.LENGTH_SHORT).show();
				}
			}
			
		}else if(pref.getString("tipo", null).equals("aluno")){
			//inicia Alteração no Servidor
			
			a = new Aluno().buscarAlunoEspecifico(b, pref.getString("usuario", null));
			if(a.getSenha().equals(senhaAntiga.getText().toString()) && 
					novaSenha.getText().toString().equals(confirmarSenha.getText().toString())){
				
					a.setSenha(novaSenha.getText().toString());
					
					boolean flag = a.atualizarAlunoWeb();
					if(a.atualizarDadosPessoais(b)&& flag){
						Toast.makeText(AlterarDadosPessoais.this, "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
						finish();
					}else{
						Toast.makeText(AlterarDadosPessoais.this, "Erro ao atualizar a senha", Toast.LENGTH_SHORT).show();
					}
			}
		
		}
	}
	
	
	//Cria����o de MENUS
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alterar_dados_pessoais, menu);
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
