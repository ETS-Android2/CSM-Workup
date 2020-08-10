package br.com.GUI.treinamentos;

import java.util.ArrayList;

import br.com.Banco.Banco;
import br.com.Classes.Aluno;
import br.com.Classes.Treinamento;
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
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class ConfirmarInicioTreinamento extends Activity {
	
	//Atributos de Interface
	private Spinner selecionarTreinamento;
	private Button iniciarTreinamento;
	private TextView aviso;
	
	//Persist��ncias
	private SharedPreferences pref;
	private Banco b;
	
	//Atributos auxiliares
	private ArrayList<Treinamento> busca;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirmar_inicio_treinamento);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		selecionarTreinamento = (Spinner)findViewById(R.id.spnSelecionarTreinamentoRealizar);
		iniciarTreinamento = (Button)findViewById(R.id.btnIniciarTreinamentoAluno);
		iniciarTreinamento.setVisibility(View.GONE);
		
		aviso = (TextView) findViewById(R.id.lblAvisoSemTreinamentosLinkConfirmarInicioTreinamento);
		aviso.setVisibility(View.GONE);
		
		
		 pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		 b = new Banco(this,null,null,0);
		 
		 /*if(pref.getString("tipo", null).equals("personal")){
			busca = new Treinamento().buscarTreinamentos(b,pref.getString("usuario", null),"");
			 ArrayList<String> nomeTreinamentos = new ArrayList<String>();
				for (Treinamento t : busca){
					nomeTreinamentos.add(t.getNomeTreinamento());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,nomeTreinamentos);
				selecionarTreinamento.setAdapter(adapter);
				if(nomeTreinamentos.isEmpty()){
					Toast.makeText(this, "Você deve cadastrar um treinamento antes!", Toast.LENGTH_SHORT).show();
					aviso.setVisibility(View.VISIBLE);
					novoTreinamento.setVisibility(View.VISIBLE);
				}else{
					iniciarTreinamento.setVisibility(View.VISIBLE);
				}
		 }else */
		 
		 if(pref.getString("tipo", null).equals("aluno")){
			 
			 //declara variaveis
			 Aluno a = new Aluno().buscarAlunoEspecifico(b,pref.getString("usuario", null));
			 ArrayList<String> treino =  new ArrayList<String>();
			 
			 //busca nome do treinamento
			 if(a.getCodTreinamento() > 0){
				 String t = new Treinamento().buscarTreinamentoPorId(b, a.getCodTreinamento()).getNomeTreinamento();
				 treino.add(t);
				 iniciarTreinamento.setVisibility(View.VISIBLE);
			 }
			
			 //seta adapter
			 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,treino);
				selecionarTreinamento.setAdapter(adapter);
			if(iniciarTreinamento.getVisibility() == View.GONE){
				Toast.makeText(this, "Você não possui nenhum treinamento Preescrito", Toast.LENGTH_SHORT).show();
				aviso.setVisibility(View.VISIBLE);
				
			}
		 }
		
		 
		 iniciarTreinamento.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				iniciarTreinamento();
				
			}
		});
		 
		 if(selecionarTreinamento.getAdapter().isEmpty()){
			
			 aviso.setVisibility(View.VISIBLE);
			 
		 }

	}
	
	public void cadastrarNovoTreinamento(View v){
		Intent i = new Intent(this, GerenciarEdicaoDeExercicios.class);
		startActivity(i);
	}
	
	public void iniciarTreinamento(){
		finish();
		Intent i = new Intent(this,RealizarTreinamento.class);
		if(pref.getString("tipo", null).equals("aluno") ){
			Aluno a = new Aluno().buscarAlunoEspecifico(b,pref.getString("usuario", null));
			i.putExtra("codTreinamento", a.getCodTreinamento());
		}
		
		startActivity(i);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.confirmar_inicio_treinamento, menu);
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
