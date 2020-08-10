package br.com.GUI.treinamentos;

import java.util.ArrayList;

import br.com.Banco.Banco;
import br.com.Classes.ExercicioRealizado;
import br.com.Classes.TreinamentoRealizado;
import br.com.WorkUp.R;
import br.com.CustomListView.CustomAdapterExercicioRealizado;
import br.com.CustomListView.RowItemExercicioRealizado;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class VisualisarTreinamentoRealizados extends Activity {
	
	//Atributos de interface
	
	private ListView exerciciosRealizados;

	//Atributos auxiliares
	
	private ArrayList<ExercicioRealizado> busca = new ArrayList<ExercicioRealizado>();
	
	//Persistencias
	
	private Banco b;
	private SharedPreferences pref;
	
	//Create and Resume
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visualisar_treinamento_realizado);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		exerciciosRealizados = (ListView) findViewById(R.id.listViewExerciciosRealizados);
		exerciciosRealizados.setDivider(null);
		
		b = new Banco(this,null,null,0);
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
	
		refreshLista();

		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		refreshLista();
	}
	
	//refresh
	public void refreshLista(){
		int codTreino = getIntent().getExtras().getInt("treino");
		
			busca = new TreinamentoRealizado()
			.buscarExerciciosRealizadoPorTreinamento(b,codTreino);
			
			if(busca.isEmpty()){
				Log.i("ta ", "vazio");

			}else{
				Log.i("nem ta ", "vazio");
			}
			atualizarInterface(busca);
	}
	
	public void atualizarInterface(ArrayList<ExercicioRealizado> busca){
		ArrayList<RowItemExercicioRealizado> exercicios = 
				new ArrayList<RowItemExercicioRealizado>();
		
		for(ExercicioRealizado ex: busca){ 
				RowItemExercicioRealizado r = new RowItemExercicioRealizado(
						BitmapFactory.decodeResource(getResources(), R.drawable.haltere_logo),
						ex.getNomeExercicio(),
						"Inicio: " + ex.getInicioExercicio(),
						"Termino: " + ex.getFimExercicio(),
						"Duração: " + ex.getDuracaoExercicio() + "min(s)");
			exercicios.add(r);
		}
		
		CustomAdapterExercicioRealizado adapter = 
				new CustomAdapterExercicioRealizado(this, exercicios);	
		exerciciosRealizados.setAdapter(adapter);
	}
	
	//Menus
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater()
				.inflate(R.menu.visualisar_treinamento_realizado, menu);
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
