package br.com.GUI.treinamentos;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import br.com.Banco.Banco;
import br.com.Classes.Aerobico;
import br.com.Classes.Anaerobico;
import br.com.Classes.Aula;
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
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.os.Build;

public class EditarExercicio extends Activity {
	
	//Atributos de interface
	private RadioGroup tipoDeExercicio;
	private RadioButton aerobico;
	private RadioButton anaerobico;
	private EditText nomeExercicio;
	private NumberPicker duracaoExercicio;
	private NumberPicker descansoExercicio;
	private EditText descricaoExercicio;
	private NumberPicker repeticoesExercicio;
	private Button btnAdicionarExercicio;
	
	private LinearLayout lytDuracao;
	private LinearLayout lytDescanso;
	private LinearLayout lytRepeticoes;
	
	
	
	//Atributos auxiliares
	private String tipo;
	private int idExercicio;

	//Persistencias de dados
	private Banco b;
	private SharedPreferences pref;

	
	//Create and resumes
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_exercicio);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		mapearComponentes();
		carregarExercicio();
	}
	
	public void mapearComponentes(){
		tipoDeExercicio = (RadioGroup)findViewById(R.id.rdgEditarTipoExercicio);
		aerobico = (RadioButton)findViewById(R.id.rdBtEditarAerobico);
		anaerobico = (RadioButton)findViewById(R.id.rdBtEditarAnaerobico);
		nomeExercicio = (EditText)findViewById(R.id.edtEditarNomeExercicio);
		duracaoExercicio = (NumberPicker)findViewById(R.id.nmbEditarDuracaoExercicio);
		descansoExercicio = (NumberPicker)findViewById(R.id.nmbEditarDescansoExercicio);
		descricaoExercicio = (EditText)findViewById(R.id.edtEditarDescricaoExercicio);
		repeticoesExercicio = (NumberPicker)findViewById(R.id.nmbEditarRepeticoesExercicio);
		btnAdicionarExercicio = (Button)findViewById(R.id.btnEditarExercicio);
		Typeface font = Typeface.createFromAsset(getAssets(), "BebasNeue Bold.ttf");
		btnAdicionarExercicio.setTypeface(font);
		
		b = new Banco(this,null,null,0);
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		
		lytDescanso = (LinearLayout) findViewById(R.id.lytEditarControlarDescansoExercicio);
		lytDuracao = (LinearLayout) findViewById(R.id.lytEditarControlarDuracaoExercicio);
		lytRepeticoes = (LinearLayout) findViewById(R.id.lytEditarControlarRepeticoes);
		
		duracaoExercicio.setMaxValue(200);
		duracaoExercicio.setMinValue(0);
		
		descansoExercicio.setMaxValue(150);
		descansoExercicio.setMinValue(0);
		
		repeticoesExercicio.setMaxValue(200);
		repeticoesExercicio.setMinValue(0);
		 
		
		
		lytDuracao.setVisibility(View.VISIBLE);
		lytRepeticoes.setVisibility(View.VISIBLE);
		
		
		
		aerobico.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(aerobico.isChecked()){
					lytDuracao.setVisibility(View.VISIBLE);
					lytRepeticoes.setVisibility(View.GONE);
				}
				else if(anaerobico.isChecked()){
					lytDuracao.setVisibility(View.GONE);
					lytRepeticoes.setVisibility(View.VISIBLE);
				}
			}
			
		});
	}
	
	
	//Atualizar
	public void carregarExercicio(){
		Intent i = getIntent();
		Bundle parametros = i.getExtras();
		tipo = parametros.getString("tipo");
		idExercicio = parametros.getInt("codExercicio");
		
		
		Log.i("tipo ", tipo + idExercicio);
		Aerobico ea = null;
		Anaerobico eanb = null;
		if (tipo.equals("aerobico")){
			//ea = new Aerobico().buscarExerciciosPorCodigo(b, idExercicio);
			
			//inicia busca no servidor 
			ea = new Aerobico().buscarExerciciosPorIdWeb(idExercicio);
			
			try{
			aerobico.setSelected(true);
			nomeExercicio.setText(ea.getNomeExercicio());
			duracaoExercicio.setValue(Integer.parseInt(ea.getDuracaoExercicio()));
			descansoExercicio.setValue(Integer.parseInt(ea.getDescansoExercicio()));
			descricaoExercicio.setText(ea.getDescricaoExercicio());
			lytRepeticoes.setVisibility(View.GONE);
			lytDuracao.setVisibility(View.VISIBLE);
			
			}catch(Exception e){
					e.printStackTrace();
			}
							
			
		}else if (tipo.equals("anaerobico")){
			//eanb = new Anaerobico().buscarExerciciosPorCodigo(b, idExercicio);
			eanb = new Anaerobico().buscarExercicioAnaerobicoPorIdWeb(idExercicio);
				
			try{
			anaerobico.setSelected(true);
			nomeExercicio.setText(eanb.getNomeExercicio());
			descansoExercicio.setValue(Integer.parseInt(eanb.getDescansoExercicio()));
			descricaoExercicio.setText(eanb.getDescricaoExercicio());
			repeticoesExercicio.setValue(Integer.parseInt(eanb.getRepeticoesExercicio()));
			lytDuracao.setVisibility(View.GONE);	
			lytRepeticoes.setVisibility(View.VISIBLE);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
		
	public void atualizarExercicio(View v){
		Aerobico ea = null;
		Anaerobico eanb= null;
		
		if (tipo.equals("aerobico")){
			ea = new Aerobico(idExercicio,nomeExercicio.getText().toString(),descricaoExercicio.getText().toString(),
					String.valueOf(duracaoExercicio.getValue()),String.valueOf(descansoExercicio.getValue()),pref.getString("usuario", "public"));
		}else if (tipo.equals("anaerobico")){
			eanb = new Anaerobico(idExercicio,nomeExercicio.getText().toString(),descricaoExercicio.getText().toString(),
					String.valueOf(descansoExercicio.getValue()),String.valueOf(repeticoesExercicio.getValue()),
					pref.getString("usuario", "public"));
		}
		if (tipo.equals("aerobico") ){
			
			//Inicia atualização no web service
			try{
				if(ea.atualizarExercicioAerobicoWeb()){
					if(ea.atualizarExercicio(b)){
						Toast.makeText(this, "Sucesso!", Toast.LENGTH_SHORT).show();
						finish();
					}
				}
			}catch(Exception ex){
				Toast.makeText(this, "falha na atualização", Toast.LENGTH_SHORT).show();
			}
		}else if (tipo.equals("anaerobico")){
			try{
				if(eanb.atualizarExercicioAnaerobicoWeb()){
					if(eanb.atualizarExercicio(b)){
						Toast.makeText(this, "Sucesso!", Toast.LENGTH_SHORT).show();
						finish();
					}
				}
			}catch(Exception ex){
				Toast.makeText(this, "falha na atualização", Toast.LENGTH_SHORT).show();
			}
		}
		
		
	}
	
	
	//MENUS
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editar_exercicio, menu);
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
