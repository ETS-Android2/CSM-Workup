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
package br.com.GUI.aulas;

import java.io.Serializable;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import br.com.Banco.Banco;
import br.com.Classes.Aluno;
import br.com.Classes.Aula;
import br.com.Classes.Treinamento;
import br.com.Utilitarios.WebService;
import br.com.WorkUp.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ConfirmarAula extends Activity {
	
	//Atributos de Interface
	
	private ListView dadosAula;
	private Button confirmar;
	private Button cancelar;
	
	//Persistencias
	private SharedPreferences pref;
	private Aula aula;
	private Banco b;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirmar_aula);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		mapearComponentes();
		
		
		int aux = getIntent().getExtras().getInt("aula");
		aula = new Aula().buscarAulasPorId(b, aux);
		
		String dataAula = "Data da Aula:\n" + aula.getDiaAula() + "/" + aula.getMesAula() + "/" + aula.getAnoAula();
		String horaAula = "Hora da Aula:\n" + aula.getHoraAula() + ":" + aula.getMinutoAula();
		String duracaoAula = "Duração:\n" + aula.getDuracaoAula() + " minutos";
		
		ArrayList<String> lista = new ArrayList<String>();
		lista.add(dataAula);
		lista.add(horaAula);
		lista.add(duracaoAula);
		
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,lista);
		dadosAula.setAdapter(adapt);
		
		
	}
	
	public void mapearComponentes(){
		
		dadosAula = (ListView) findViewById(R.id.lstConfirmacaoAula);
		Typeface font = Typeface.createFromAsset(getAssets(), "BebasNeue Bold.ttf");
		confirmar = (Button) findViewById(R.id.btnConfirmarAula);
		confirmar.setTypeface(font);
		cancelar = (Button) findViewById(R.id.btnCancelarAula);
		cancelar.setTypeface(font);
		
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		b = new Banco(this,null,null,0);
	
	}
	
	public void confirmarAula(View v){
		confirmaCancelaAula(1);
		
	}
	
	public void cancelarAula(View v){
		
		confirmaCancelaAula(0);
		
	}
	
	public void confirmaCancelaAula(int confirmaAula){
		if (pref.getString("tipo", null).equals("personal")){
				
				//Log.i("confirma cancela", "ca = " + confirmaAula);
				boolean flag = aula.confirmarCancelarAulaPersonalWeb(confirmaAula);
				if (flag){
					if(aula.confirmarCancelarAulaPersonal(b, confirmaAula)){
						Toast.makeText(ConfirmarAula.this, "Aula Confirmada!", Toast.LENGTH_SHORT).show();
						finish();
					}
				}else{
					Toast.makeText(ConfirmarAula.this, "Erro ao cancelar!", Toast.LENGTH_SHORT).show();
				}
	

	
		}else if (pref.getString("tipo",null).equals("aluno")){
			
				boolean flag = aula.confirmarCancelarAulaAlunoWeb(confirmaAula);
				
				if (flag){
					if(aula.confirmarCancelarAulaAluno(b, confirmaAula)){
						Toast.makeText(ConfirmarAula.this, "Aula Confirmada!", Toast.LENGTH_SHORT).show();
						finish();
					}
				}else{
					Toast.makeText(ConfirmarAula.this, "Erro ao cancelar!", Toast.LENGTH_SHORT).show();
					
				}
		}
	}

	
	//MENUS
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.confirmar_aula, menu);
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
