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
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class PreescreverTreinamento extends Activity {
	
	//Atributos de interface
	private Spinner selecionarTreinamento;
	private Button preescreverTreinamento;
	private TextView semTreinamentos;
	
	//Persistencias locais
	private SharedPreferences pref;
	
	
	//Atributos auxiliares
	private Banco b;
	private Aluno c;
	private ArrayList<Treinamento> busca = new ArrayList<Treinamento>();
	
	
	//Creates and resumes
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preescrever_treinamento);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		b = new Banco(this,null,null,0);
		selecionarTreinamento = (Spinner) findViewById(R.id.spnSelecionarTreinamentoPreescrever);
		
		Typeface font = Typeface.createFromAsset(getAssets(), "BebasNeue Bold.ttf");
		
		preescreverTreinamento = (Button) findViewById(R.id.btnPreescreverTreinamento);
		preescreverTreinamento.setTypeface(font);
		
		
		semTreinamentos = (TextView) findViewById(R.id.lblAvisoSemTreinamentos);
		semTreinamentos.setVisibility(View.GONE);
		
		//pega parametros
		String parametro = getIntent().getExtras().getString("usuario");
		c = new Aluno().buscarAlunoEspecifico(b, parametro);
		

		atualizaTreinamentos();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		atualizaTreinamentos();
		
	}
	
	
	//ATualizar
	public void atualizaTreinamentos(){
		clear();
		//busca = new Treinamento().buscarTreinamentos(b,pref.getString("usuario", null),"");
		
		busca = new Treinamento().buscarTreinamentos(b,pref.getString("usuario", null), "");
		
		ArrayList<String> nomeTreinamentos = new ArrayList<String>();
			for (Treinamento t : busca){
				nomeTreinamentos.add(t.getNomeTreinamento());
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					this, android.R.layout.simple_list_item_1,nomeTreinamentos);
			selecionarTreinamento.setAdapter(adapter);
			
			if(nomeTreinamentos.isEmpty()){
				Toast.makeText(this, "Você deve cadastrar um treinamento antes!", Toast.LENGTH_SHORT).show();
				semTreinamentos.setVisibility(View.VISIBLE);
				preescreverTreinamento.setVisibility(View.GONE);
			}else{
				semTreinamentos.setVisibility(View.GONE);
				preescreverTreinamento.setVisibility(View.VISIBLE);
			}
	}
	
	public void cadastrarNovoTreinamento(View v){
		Intent i = new Intent(this, GerenciarEdicaoDeExercicios.class);
		startActivity(i);
	}
	
	public void preescreverTreinamento(View v){
		
		int treinamento = busca.get(selecionarTreinamento.getSelectedItemPosition()).getCodTreinamento();
		if(c.preescreverTreinamentoWeb(treinamento)){
			if (c.preescreverTreinamento(b, treinamento)){
				Toast.makeText(this, "Preescrito com sucesso!", Toast.LENGTH_SHORT).show();
				finish();
			}
		}else{
			Toast.makeText(this, "Erro ao preescrever", Toast.LENGTH_SHORT).show();
		}
	}

	public void clear(){
		busca.clear();
	}
	
	//MENUS
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preescrever_treinamento, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		if(id ==  android.R.id.home){
			finish();
			//NavUtils.navigateUpFromSameTask(this);
	        return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
