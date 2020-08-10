package br.com.GUI.treinamentos;

import java.util.ArrayList;

import br.com.Banco.Banco;
import br.com.Classes.ExercicioRealizado;
import br.com.Classes.TreinamentoRealizado;
import br.com.WorkUp.R;
import br.com.WorkUp.R.id;
import br.com.WorkUp.R.layout;
import br.com.WorkUp.R.menu;
import CustomListView.CustomAdapterTreinamentosRealizado;
import CustomListView.RowItemTreinamentoRealizado;
import android.app.Activity;
import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class AcompanhamentoTreinamento extends Activity {
	
	private ListView lstAcompanhamentoAlunoTreinamentosRealizados;
	private ArrayList<TreinamentoRealizado> buscaTreinos = new ArrayList<TreinamentoRealizado>();
	private String usuarioAluno;
	
	private SharedPreferences pref;
	private Banco b;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acompanhamento_aluno);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		b = new Banco(this, null, null, 0);
		pref = this.getApplicationContext().getSharedPreferences("MyPref", 0);
		lstAcompanhamentoAlunoTreinamentosRealizados = (ListView) this.findViewById(R.id.lstAcompanhamentoAlunoTreinamentosRealizados);
		lstAcompanhamentoAlunoTreinamentosRealizados.setDivider(null);
		
		usuarioAluno = getIntent().getExtras().getString("usuario");
		
		
		  atualizarAcompanhamento();
		}
	
	public void atualizarAcompanhamento(){
		
		buscaTreinos = new TreinamentoRealizado()
		.buscarTreinoPorAluno(b, usuarioAluno);
		
		atualizarInterface();
		
		
		ArrayList<TreinamentoRealizado> buscaLocal = new TreinamentoRealizado()
		.buscarTreinoPorAluno(b, usuarioAluno);
		
		buscaTreinos = new TreinamentoRealizado()
			.buscarTreinamentoPorAlunoWeb(usuarioAluno);

		
		//Manda os treinos para serem sincronizados
		
		if(!buscaTreinos.isEmpty()){
			for( TreinamentoRealizado x: buscaTreinos){
				boolean flag = false;
				for(TreinamentoRealizado y: buscaLocal){
					if(x.getCodTreinamentoRealizado() == y.getCodTreinamento()){
						flag = true;
					}
				}
				if(!flag){ 
					x.salvarTreinamentoRealizado(b);
					ArrayList<ExercicioRealizado> exercicios = x.buscarExerciciosRealizadoPorTreinamentoWeb(x.getCodTreinamentoRealizado());
					for(ExercicioRealizado exr: exercicios){
						try{
							exr.salvarExercicioRealizado(b);
						}catch( Exception e){
							Log.i("Erro: atualizar Acompanhamento", 
									"Erro ao sincronizar o exercicio realizado: "+ e.toString());
						}
					}
				}
			}	
		}
		
		atualizarInterface();
		
	}
	
	
	public void atualizarInterface(){
		ArrayList<RowItemTreinamentoRealizado> lista = new ArrayList<RowItemTreinamentoRealizado>();
	
		for(TreinamentoRealizado t: buscaTreinos){
			RowItemTreinamentoRealizado a = new RowItemTreinamentoRealizado(t.getDataTreino(), t.getHoraInicio(), t.getHoraFim(), "Completo!");
			lista.add(a);
		}
		
		CustomAdapterTreinamentosRealizado adapter = new CustomAdapterTreinamentosRealizado(this, lista);
		lstAcompanhamentoAlunoTreinamentosRealizados.setAdapter(adapter);
		
		lstAcompanhamentoAlunoTreinamentosRealizados.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i = new Intent(AcompanhamentoTreinamento.this, VisualisarTreinamentoRealizados.class);
				i.putExtra("treino", buscaTreinos.get(arg2).getCodTreinamentoRealizado());
				startActivity(i);
				
			}
		});
		
	}
	@Override
	public void onResume(){
		super.onResume();
		atualizarAcompanhamento();
	}
	
/*

	@Override
	public void onCreateOptionsMenu(
	    Menu menu, MenuInflater inflater) {
	   inflater.inflate(R.menu.acompanhamento_aluno, menu);
	   
	   
	}*/

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
