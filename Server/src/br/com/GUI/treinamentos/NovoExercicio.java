package br.com.GUI.treinamentos;

import br.com.Banco.Banco;
import br.com.Classes.Aerobico;
import br.com.Classes.Anaerobico;
import br.com.Classes.Exercicio;
import br.com.WorkUp.R;
import br.com.WorkUp.R.layout;
import br.com.WorkUp.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Toast;

public class NovoExercicio extends Fragment {
	
	//Atributos de interface
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
	
	
	//Persistências  locais
	private Banco b;
	private SharedPreferences pref;


	//Creates and Resumes
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		
	 
	        View rootView = inflater.inflate(R.layout.activity_novo_exercicio, container, false);
	        
	        return rootView;
	 }	
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		aerobico = (RadioButton)getActivity().findViewById(R.id.rdBtAddAerobico);
		anaerobico = (RadioButton)getActivity().findViewById(R.id.rdBtAddAnaerobico);
		nomeExercicio = (EditText)getActivity().findViewById(R.id.edtCadastrarNomeExercicio);
		duracaoExercicio = (NumberPicker)getActivity().findViewById(R.id.nmbCadastrarDuracaoExercicio);
		descansoExercicio = (NumberPicker)getActivity().findViewById(R.id.nmbCadastrarDescansoExercicio);
		descricaoExercicio = (EditText)getActivity().findViewById(R.id.edtCadastrarDescricaoExercicio);
		repeticoesExercicio = (NumberPicker)getActivity().findViewById(R.id.nmbCadastrarRepeticoesExercicio);
		btnAdicionarExercicio = (Button)getActivity().findViewById(R.id.btnAdicionarExercicio);
		
		lytDescanso = (LinearLayout) getActivity().findViewById(R.id.lytControlarDescansoExercicio);
		lytDuracao = (LinearLayout) getActivity().findViewById(R.id.lytControlarDuracaoExercicio);
		lytRepeticoes = (LinearLayout) getActivity().findViewById(R.id.lytControlarRepeticoes);
		
		duracaoExercicio.setMaxValue(200);
		duracaoExercicio.setMinValue(0);
		
		descansoExercicio.setMaxValue(150);
		descansoExercicio.setMinValue(0);
		
		repeticoesExercicio.setMaxValue(200);
		repeticoesExercicio.setMinValue(0);
		
		
		
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "BebasNeue Bold.ttf");
		btnAdicionarExercicio.setTypeface(font);
		
		btnAdicionarExercicio.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				salvarExercicio();
			}
		});
		
		b = new Banco(getActivity(),null,null,0);
		
		 pref =  NovoExercicio.this.getActivity().getSharedPreferences("MyPref", 0);

		 
		
		
		 lytDuracao.setVisibility(View.VISIBLE);
			lytRepeticoes.setVisibility(View.GONE);
		
		
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
	
	
	//CUD Local 
	public void salvarExercicio(){
		
		Aerobico a = null;
		Anaerobico ab = null;
		if(aerobico.isChecked()){
			a = new Aerobico(0	, nomeExercicio.getText().toString(), 
					descricaoExercicio.getText().toString(), String.valueOf(duracaoExercicio.getValue()),
					String.valueOf(descansoExercicio.getValue()), pref.getString("usuario", null));
		}else{
			ab = new Anaerobico(0, nomeExercicio.getText().toString(),
					descricaoExercicio.getText().toString(), String.valueOf(descansoExercicio.getValue()),
					String.valueOf(repeticoesExercicio.getValue()), pref.getString("usuario", null));
		}
		
		
		if (aerobico.isChecked()){
			try{
				int aux = a.salvarExercicioAerobicoWeb();
				if(aux > 0 ){
					a.setCodExercicio(aux);
					if(a.salvarExercicio(b,pref.getString("usuario", null))){
						Toast.makeText(getActivity(), "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
					}
					
				}
			}catch(Exception ex){
				Toast.makeText(getActivity(), "Erro ao salvar - Erro: " + ex.toString(), Toast.LENGTH_SHORT).show();
			}
			
		}else if (anaerobico.isChecked()){
			
			try{
				int aux = ab.salvarExercicioAnaerobicoWeb();
				if(aux > 0){
					ab.setCodExercicio(aux);
					if(ab.salvarExercicio(b,pref.getString("usuario", null))){
						Toast.makeText(getActivity(), "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
						
					}
				}
			}catch(Exception ex){
				Toast.makeText(getActivity(), "Erro ao salvar - Erro: " + ex.toString(), Toast.LENGTH_SHORT).show();
			}
		}
		aerobico.setChecked(true);
		anaerobico.setChecked(false);
		nomeExercicio.setText("");
		duracaoExercicio.setValue(0);
		descansoExercicio.setValue(0);
		descricaoExercicio.setText("");
		repeticoesExercicio.setValue(0);
	}

	
	//Menus
	@Override
	public void onCreateOptionsMenu(
	    Menu menu, MenuInflater inflater) {
	   inflater.inflate(R.menu.novo_exercicio, menu);
	}

}
