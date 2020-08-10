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

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
/*
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
*/
import br.com.Banco.Banco;
import br.com.Classes.Aluno;
import br.com.Classes.Aula;
import br.com.Classes.Personal;
import br.com.Classes.Treinamento;
import br.com.WorkUp.R;

public class MarcarAula extends Activity {
	
	//Atributos de interface
	private NumberPicker nmbSelecionarDataAulaDia;
	private NumberPicker nmbSelecionarDataAulaMes;
	private NumberPicker nmbSelecionarDataAulaAno;
	private NumberPicker nmbSelecionarHorarioAulaHora;
	private NumberPicker nmbSelecionarHorarioAulaMinuto;
	private NumberPicker nmbSelecionarDuracaoAula;
	private Button btnVerificarDisponibilidade;
	private Button btnAgendarAula;
	
	//constantes
	static final int DATE_DIALOG_ID = 999;
	static final int TIME_DIALOG_ID = 888;
	
	//dados
	
	private ArrayList<Treinamento> buscaTreinamentos = new ArrayList<Treinamento>();
	private ArrayList<String> buscaAlunos;
	private Aluno dadosPessoais;
	private boolean isVerified = false;
	private Calendar c;
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	
	//Persistencias
	private Banco b;
	private SharedPreferences pref;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_marcar_aula);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		mapearComponentes();
		
	}
	
	public void mapearComponentes(){
		
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		b = new Banco(this,null,null,0);
		
		c = Calendar.getInstance();
		
		nmbSelecionarDataAulaDia = (NumberPicker) findViewById(R.id.nmbSelecionarDataAulaDia);
		nmbSelecionarDataAulaMes = (NumberPicker) findViewById(R.id.nmbSelecionarDataAulaMes);
		nmbSelecionarDataAulaAno= (NumberPicker) findViewById(R.id.nmbSelecionarDataAulaAno);
		nmbSelecionarHorarioAulaHora = (NumberPicker) findViewById(R.id.nmbSelecionarHorarioAulaHora);
		nmbSelecionarHorarioAulaMinuto= (NumberPicker) findViewById(R.id.nmbSelecionarHorarioAulaMinuto);
		nmbSelecionarDuracaoAula= (NumberPicker) findViewById(R.id.nmbSelecionarDuracaoAula);
		btnVerificarDisponibilidade =  (Button) findViewById(R.id.btnVerificarDisponibilidade);
		btnVerificarDisponibilidade.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				verificarDisponibilidade();
				
			}
		});
		
		if(pref.getString("tipo", null).equals("personal")){
			btnVerificarDisponibilidade.setVisibility(View.GONE);
		}
		
		btnAgendarAula = (Button) findViewById(R.id.btnAgendarAula);
		btnAgendarAula.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				agendarAula();
				
			}
		});
		
		//Estabelece maximos e mínimos
		nmbSelecionarDataAulaDia.setMaxValue(31);
		nmbSelecionarDataAulaDia.setMinValue(1);
		
		nmbSelecionarDataAulaMes.setMaxValue(12);
		nmbSelecionarDataAulaMes.setMinValue(1);
		
		nmbSelecionarDataAulaAno.setMaxValue(c.get(Calendar.YEAR));
		nmbSelecionarDataAulaAno.setMinValue(1900);
		
		nmbSelecionarHorarioAulaHora.setMaxValue(23);
		nmbSelecionarHorarioAulaHora.setMinValue(0);
		
		nmbSelecionarHorarioAulaMinuto.setMaxValue(59);
		nmbSelecionarHorarioAulaMinuto.setMinValue(0);
		
		nmbSelecionarDuracaoAula.setMaxValue(400);
		nmbSelecionarDuracaoAula.setMinValue(1);
		
		
		//estabelece valores padrão
		nmbSelecionarDataAulaDia.setValue(c.get(Calendar.DAY_OF_MONTH));
		nmbSelecionarDataAulaMes.setValue(c.get(Calendar.MONTH));
		nmbSelecionarDataAulaAno.setValue(c.get(Calendar.YEAR));
		
		nmbSelecionarHorarioAulaHora.setValue(c.get(Calendar.HOUR_OF_DAY));
		nmbSelecionarHorarioAulaMinuto.setValue(c.get(Calendar.MINUTE));
		
		
		nmbSelecionarDuracaoAula.setValue(60);
		
		
		
		btnVerificarDisponibilidade.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				verificarDisponibilidade();
			}
		});
		
		
		if(pref.getString("tipo" , null).equals("aluno")){
			btnAgendarAula.setEnabled(false);
		}
		btnAgendarAula.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				agendarAula();
			}
		});
		
	}
	
	
	public void agendarAula(){
			
		if(pref.getString("tipo", null).equals("aluno")){
		
			Aluno a = new Aluno().buscarAlunoEspecifico(b, pref.getString("usuario",null));
				
			Aula al = new Aula(
					0,
					nmbSelecionarDataAulaDia.getValue(),
					nmbSelecionarDataAulaMes.getValue(),
					nmbSelecionarDataAulaAno.getValue(),
					nmbSelecionarHorarioAulaHora.getValue(),
					nmbSelecionarHorarioAulaMinuto.getValue(),
					nmbSelecionarDuracaoAula.getValue(),
					0,
					1,
					"ativado",
					pref.getString("usuario", null),
					a.getUsuarioPersonal()
					);
			
			int resultado = 0;
			resultado = al.agendarAulaWeb();
			
			if(resultado > 0 ){
				al.setCodAula(resultado);
					if(al.agendarAula(b)){
						Toast.makeText(this, R.string.mensagens_agendado_com_sucesso, Toast.LENGTH_SHORT).show();
						finish();
					}else{
						Toast.makeText(this, R.string.mensagens_falha_ao_agendar_aula , Toast.LENGTH_SHORT).show();
					}
			}else{
				Toast.makeText(this, R.string.mensagens_falha_ao_agendar_aula , Toast.LENGTH_SHORT).show();
			}
			
			
		}else if(pref.getString("tipo",	null).equals("personal")){
			
			String avaliado = getIntent().getExtras().getString("usuario");
			
			Aula al = new Aula(
					0,
					nmbSelecionarDataAulaDia.getValue(),
					nmbSelecionarDataAulaMes.getValue(),
					nmbSelecionarDataAulaAno.getValue(),
					nmbSelecionarHorarioAulaHora.getValue(),
					nmbSelecionarHorarioAulaMinuto.getValue(),
					nmbSelecionarDuracaoAula.getValue(),
					1,
					0,
					"ativado",
					avaliado,
					pref.getString("usuario", null)
					);
			
			int resultado = 0;
			resultado = al.agendarAulaWeb();
			
			if(resultado > 0 ){
				
				al.setCodAula(resultado);
				
					if(al.agendarAula(b)){
						Toast.makeText(this, R.string.mensagens_agendado_com_sucesso, Toast.LENGTH_SHORT).show();
						finish();
					}else{
						Toast.makeText(this, R.string.mensagens_falha_ao_agendar_aula , Toast.LENGTH_SHORT).show();
					}
			}else{
				Toast.makeText(this, R.string.mensagens_falha_ao_agendar_aula , Toast.LENGTH_SHORT).show();
			}
			
		}
	}
	
	public void verificarDisponibilidade(){
		boolean verificarAno = true;
		boolean verificarMes = true;
		boolean verificarDia = true;
		boolean verificarHora = true;
		boolean verificarMinutos = true;
		
		if(nmbSelecionarDataAulaAno.getValue() < c.get(Calendar.YEAR)){
			verificarAno = false;
			Log.i("entrei no ano", "valor " + verificarAno );
		}
		if(nmbSelecionarDataAulaAno.getValue() == c.get(Calendar.YEAR) 
				&& nmbSelecionarDataAulaMes.getValue() <= c.get(Calendar.MONTH)){
			verificarMes = false;
			Log.i("entrei no mes", "valor " + verificarMes );
		}
		if(nmbSelecionarDataAulaAno.getValue() == c.get(Calendar.YEAR) 
				&& nmbSelecionarDataAulaMes.getValue() == c.get(Calendar.MONTH)
				&& nmbSelecionarDataAulaDia.getValue() <= c.get(Calendar.DAY_OF_MONTH)){
			verificarDia = false;
			Log.i("entrei no dia", "valor " + verificarDia );
		}
		
		if(nmbSelecionarDataAulaAno.getValue() == c.get(Calendar.YEAR) 
				&& nmbSelecionarDataAulaMes.getValue() == c.get(Calendar.MONTH)
				&& nmbSelecionarDataAulaDia.getValue() == c.get(Calendar.DAY_OF_MONTH)
				&& nmbSelecionarHorarioAulaHora.getValue() < c.get(Calendar.HOUR_OF_DAY)){
			verificarHora = false;
			Log.i("entrei no hora", "valor " + verificarHora );
		}
		
		if(nmbSelecionarDataAulaAno.getValue() == c.get(Calendar.YEAR) 
				&& nmbSelecionarDataAulaMes.getValue() == c.get(Calendar.MONTH)
				&& nmbSelecionarDataAulaDia.getValue() == c.get(Calendar.DAY_OF_MONTH)
				&& nmbSelecionarHorarioAulaHora.getValue() == c.get(Calendar.HOUR_OF_DAY)
				&& nmbSelecionarHorarioAulaMinuto.getValue() < c.get(Calendar.MINUTE)){
			verificarMinutos= false;
			Log.i("entrei no minuto", "valor " + verificarMinutos );
		}
		
		
		if(verificarAno && verificarMes && verificarDia && verificarHora  && verificarMinutos){
			
			if(pref.getString("tipo", null).equals("aluno")){
				Aluno a = new Aluno().buscarAlunoEspecifico(b, pref.getString("usuario",null));
				
				Aula al = new Aula(
						0,
						nmbSelecionarDataAulaDia.getValue(),
						nmbSelecionarDataAulaMes.getValue(),
						nmbSelecionarDataAulaAno.getValue(),
						nmbSelecionarHorarioAulaHora.getValue(),
						nmbSelecionarHorarioAulaMinuto.getValue(),
						nmbSelecionarDuracaoAula.getValue(),
						0,
						1,
						"ativo",
						pref.getString("usuario", null),
						a.getUsuarioPersonal()
						);
				
				boolean resultado = false;
				resultado = al.verificarDisponibilidadeWeb();

				if(resultado){
					Toast.makeText(this, R.string.mensagens_horario_disponivel, Toast.LENGTH_SHORT).show();
					btnVerificarDisponibilidade.setBackground(getResources().getDrawable(R.drawable.shape_botao_azul));
					btnAgendarAula.setEnabled(true);
				}else{
					Toast.makeText(this, R.string.mensagens_horario_indisponivel , Toast.LENGTH_SHORT).show();
					btnVerificarDisponibilidade.setBackground(getResources().getDrawable(R.drawable.shape_botao_vermelho));
					btnAgendarAula.setEnabled(false);		
				}
			}
				
		}else{
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
			alertDialog.setTitle(R.string.label_atencao);
			alertDialog.setMessage(R.string.mensagem_data_anterior_ao_dia_atual);
			alertDialog.setIcon(R.drawable.profile);
			alertDialog.setPositiveButton(R.string.label_ok, new DialogInterface.OnClickListener() {
		         public void onClick(DialogInterface dialog,int which) {
		        	 dialog.cancel();
		         }
			});
			// Showing Alert Message
		        alertDialog.show();
		}
			
			
			
		
	}
	
	//MENUS
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.marcar_aula, menu);
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
