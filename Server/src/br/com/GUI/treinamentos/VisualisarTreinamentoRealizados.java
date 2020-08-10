package br.com.GUI.treinamentos;

import java.util.ArrayList;

import br.com.Banco.Banco;
import br.com.Classes.Aerobico;
import br.com.Classes.ExercicioRealizado;
import br.com.Classes.Treinamento;
import br.com.Classes.TreinamentoRealizado;
import br.com.WorkUp.R;
import br.com.WorkUp.R.id;
import br.com.WorkUp.R.layout;
import br.com.WorkUp.R.menu;
import CustomListView.CustomAdapterExercicioRealizado;
import CustomListView.CustomAdapterTreinamentosRealizado;
import CustomListView.RowItemExercicioRealizado;
import CustomListView.RowItemTreinamentoRealizado;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

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
