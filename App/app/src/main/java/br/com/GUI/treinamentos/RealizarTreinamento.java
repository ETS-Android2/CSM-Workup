package br.com.GUI.treinamentos;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.Banco.Banco;
import br.com.Classes.Aerobico;
import br.com.Classes.Aluno;
import br.com.Classes.Anaerobico;
import br.com.Classes.Treinamento;
import br.com.Classes.TreinamentoRealizado;
import br.com.WorkUp.R;
import br.com.CustomListView.CustomAdapterExercicio;
import br.com.CustomListView.RowItemExercicio;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

public class RealizarTreinamento extends Activity {
	
	//Atributos de interface
	private ListView lstRealizarExercicios;
	private Switch swcRealizarExercicioAerobicoAnaerobico;
	
	//persistencias
	private SharedPreferences pref;
	private Banco b;
	
	
	//Atributos auxiliares
	private TreinamentoRealizado treino;
	private int parametro;
	private ArrayList<Aerobico> buscaAerobico = new ArrayList<Aerobico>();
	private ArrayList<Anaerobico> buscaAnaerobico = new ArrayList<Anaerobico>();
	
	
	//Creates and Resumes
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_realizar_treinamento);
		parametro = getIntent().getExtras().getInt("codTreinamento");
		Log.i("parametro", parametro + "param");
		
		mapearComponentes();
	
		
		
		refreshTreinamentoExerciciosAerobico();	 //Atualiza listView com novo Treinamento
		
		
		//registra o inicio de um novo treino
		
		String registraPersonal = null;
		String registraAluno = null;
		
		
		if(pref.getString("tipo", null).equals("personal")){
			registraPersonal = pref.getString("usuario", null);
			registraAluno = null;
		}else if (pref.getString("tipo",null).equals("aluno")){
			registraAluno = pref.getString("usuario", null);
			registraPersonal = new Aluno().buscarAlunoEspecifico(b, registraAluno).getUsuarioPersonal();
		}
				
		Calendar c = Calendar.getInstance();
		
		String data = c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR);
		String hora = c.get(Calendar.HOUR_OF_DAY) + "h " + c.get(Calendar.MINUTE) + "min";
		
		treino = new TreinamentoRealizado(0,data,hora,null,registraPersonal,registraAluno,parametro,"ativado");
		
		try{
			int retornoCodTreinamento = treino.salvarTreinamentoRealizadoAlunoWeb(b);
			Log.i("Codigo do treinamento salvo na web", "Treinamento " + retornoCodTreinamento);
			if(retornoCodTreinamento > 0 ){
				treino.setCodTreinamentoRealizado(retornoCodTreinamento);
				treino.salvarTreinamentoRealizado(b);
				Log.i("IniciarTreinamento", "Sucesso!");
			}
			
		}catch(Exception e ){
			Log.i("erro ao inicializar o treinamento", e.toString());
		}
	}
	
	
	public void mapearComponentes(){
		lstRealizarExercicios = (ListView)findViewById(R.id.lstRealizarExercicios);
		lstRealizarExercicios.setDivider(null);
		Typeface font = Typeface.createFromAsset(getAssets(), "BebasNeue Bold.ttf");
		
		swcRealizarExercicioAerobicoAnaerobico = (Switch) findViewById(R.id.swcRealizarExercicioAerobicoAnaerobico);
		swcRealizarExercicioAerobicoAnaerobico.setTypeface(font);
		b = new Banco(this, null, null,0);
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		
		//listeners aerobico anaerobicos
		swcRealizarExercicioAerobicoAnaerobico.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(!arg1){
					refreshTreinamentoExerciciosAerobico();
					
				}else{
					refreshTreinamentoExerciciosAnaerobico();
					
				}
			}
		});
		
		
		
		swcRealizarExercicioAerobicoAnaerobico.setChecked(false);
		refreshTreinamentoExerciciosAerobico();
		
		
	}
	
	
	//Refresh
	public void refreshTreinamentoExerciciosAnaerobico(){
		
		buscaAnaerobico.clear();
		
		Treinamento t = new Treinamento().buscarTreinamentoPorId(b,parametro);
	
		buscaAnaerobico = t.buscarExerciciosAnaerobicoPorTreinamento(b,t.getCodTreinamento());
		
		ArrayList<RowItemExercicio> rowItensExercicios = new ArrayList<RowItemExercicio>();
		for (Anaerobico ex : buscaAnaerobico) {
			RowItemExercicio item = new RowItemExercicio(
					ex.getNomeExercicio(),
					"Descanso " + ex.getDescansoExercicio() + " minuto(s)",
					"Repetições " + ex.getRepeticoesExercicio() + " repetiçõe(s)",
					ex.getDescricaoExercicio());
					rowItensExercicios.add(item);
		}
		
		CustomAdapterExercicio adapter = 
				new CustomAdapterExercicio(this, rowItensExercicios);
		lstRealizarExercicios.setAdapter(adapter);
		
		lstRealizarExercicios.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
			       Intent i = new Intent(RealizarTreinamento.this, RealizarExercicio.class);
			       i.putExtra("exercicioAnaerobico", buscaAnaerobico.get(arg2).getCodExercicio());  
			       i.putExtra("codTreinamentoRealizado", treino.getCodTreinamentoRealizado());
			       i.putExtra("tipoExercicio", "anaerobico");
			      
			       startActivity(i);
			    }
		
			});

		
	}
	
	public void refreshTreinamentoExerciciosAerobico(){
			
			buscaAerobico.clear();
			
			Treinamento t = new Treinamento().buscarTreinamentoPorId(b,parametro);
			
			buscaAerobico = t.buscarExerciciosAerobicoPorTreinamento(b,t.getCodTreinamento());
			
			ArrayList<RowItemExercicio> rowItensExercicios = new ArrayList<RowItemExercicio>();
			for (Aerobico ex : buscaAerobico) {
				RowItemExercicio item = new RowItemExercicio(
						ex.getNomeExercicio(),
						"Duração: " + ex.getDuracaoExercicio() + " minuto(s)", 
						"Descanso: " + ex.getDescansoExercicio() + " minuto(s)",
						ex.getDescricaoExercicio());
						rowItensExercicios.add(item);
			}
			
			CustomAdapterExercicio adapter = 
					new CustomAdapterExercicio(this, rowItensExercicios);
			lstRealizarExercicios.setAdapter(adapter);
			lstRealizarExercicios.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					
				       Intent i = new Intent(RealizarTreinamento.this, RealizarExercicio.class);
				       i.putExtra("exercicioAerobico", buscaAerobico.get(arg2).getCodExercicio());  
				       i.putExtra("codTreinamento", treino.getCodTreinamentoRealizado());
				       i.putExtra("codTreinamentoRealizado", treino.getCodTreinamentoRealizado());
				       i.putExtra("tipoExercicio", "aerobico");
				       startActivity(i);
				    
				}
					
			});
	
			
		}

	@Override
	public void onBackPressed(){
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Atenção");
        alertDialog.setMessage("Você tem certeza que deseja finalizar este treinamento?");
        alertDialog.setIcon(R.drawable.critical);
        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog,int which) {
	        	finalizarTreinamento();
            }
        });
        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	dialog.cancel();
            }
        });
 
        alertDialog.show();
       
		
		
	}
	
	
	public void finalizarTreinamento(){
		try{
			Calendar c = Calendar.getInstance();
		
			String hora = c.get(Calendar.HOUR_OF_DAY) + "h " + c.get(Calendar.MINUTE) + "min";
			
			treino.setHoraFim(hora);
			if(treino.finalizarTreinamentoWeb()){
				Log.i("Treinamento salvo na Web", "salvo com sucesso!");
				
				if(treino.finalizarTreinamento(b)){
					Log.i("Treinamento salvo localmente!", "salvo localmente com sucesso!");
				}
			}
			Toast.makeText(this, "Finalizado com sucesso!", Toast.LENGTH_SHORT).show();
			finish();
		}catch(Exception ex){
			Log.i("erro ao salvar o treinamento", ex.toString());
		}
	}

	//Menus
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.realizar_treinamento_actions, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.actFinalizarRealizacaoDoTreinamento) {
			finalizarTreinamento();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
