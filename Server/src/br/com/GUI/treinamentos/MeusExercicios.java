package br.com.GUI.treinamentos;

import java.util.ArrayList;

import br.com.Banco.Banco;
import br.com.Classes.Aerobico;
import br.com.Classes.Aluno;
import br.com.Classes.Anaerobico;
import br.com.Classes.Aula;
import br.com.Classes.Exercicio;
import br.com.Classes.Personal;
import br.com.Classes.Treinamento;
import br.com.WorkUp.R;
import br.com.WorkUp.R.layout;
import br.com.WorkUp.R.menu;
import CustomListView.CustomAdapterAgendamentos;
import CustomListView.CustomAdapterExercicio;
import CustomListView.CustomAdapterTreinamento;
import CustomListView.CustomAdapterTreinamentosRealizado;
import CustomListView.RowItemAgendamentos;
import CustomListView.RowItemExercicio;
import CustomListView.RowItemExercicioRealizado;
import CustomListView.RowItemTreinamento;
import CustomListView.RowItemTreinamentoRealizado;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MeusExercicios extends Fragment {
	
	//Atributos de interface
	private ListView lstExercicios;
	private Switch swcTipoDeExercicio;
	
	//Atributos Auxiliares
	//private ArrayList<Aerobico> listaAerobico = new ArrayList<Aerobico>();
	//private ArrayList<Anaerobico> listaAnaerobico = new ArrayList<Anaerobico>();
	private ArrayList<Exercicio> listaExercicios = new ArrayList<Exercicio>();
	private  ArrayList<Exercicio> exerciciosTreinamento = new ArrayList<Exercicio>();
	
	
	//Persisitencias Locais
	private SharedPreferences pref;
	private Banco b;
	
	//Create and resume
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		
	 
	        View rootView = inflater.inflate(R.layout.activity_meus_exercicios, container, false);
	        
	        return rootView;
	 }	
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		mapearComponentes();
	}
	
	public void mapearComponentes(){
		lstExercicios = (ListView)getActivity().findViewById(R.id.lstMeusExercicios);
		lstExercicios.setDivider(null);
		
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "BebasNeue Bold.ttf");
		
		swcTipoDeExercicio = (Switch) getActivity().findViewById(R.id.swcTipoDeExercicio);
		swcTipoDeExercicio.setTypeface(font);
		
		b = new Banco (getActivity(),null,null,0);
		pref = MeusExercicios.this.getActivity().getSharedPreferences("MyPref", 0);
		

		lstExercicios.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i = new Intent(getActivity(),EditarExercicio.class);
				i.putExtra("codExercicio", listaExercicios.get(arg2).getCodExercicio());
				if(!swcTipoDeExercicio.isChecked()){
					i.putExtra("tipo", "aerobico");
				}else{
					i.putExtra("tipo", "anaerobico");
				}
				startActivity(i);
				
				
			}
		});
		
		swcTipoDeExercicio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1 == true){
					refreshListaAnaerobico();
				}else{
					refreshListaAerobico();
				}
			}
		});
		
		swcTipoDeExercicio.setChecked(true);
	}
			
		
	
	
	//Atualizar
	public void refreshListaAerobico(){
		
		clear();
		
		ArrayList<Aerobico> listaAerobico = new Aerobico()
					.buscarExerciciosPorPersonal(b,pref.getString("usuario", null));
		
		atualizaInterfaceAerobico(listaAerobico);
		
		/*ArrayList<Aerobico> sincronizar = new Aerobico().buscarExerciciosPorPersonalWeb(pref.getString("usuario", null));
		
		//Inicia sincroniza����o
		for(Aerobico e : sincronizar){
			boolean flag = false;
			for(Aerobico x: listaAerobico){
				if(e.getCodExercicio() == x.getCodExercicio()){
					e.atualizarExercicio(b);
					flag = true;
				}
			}
			if (flag == false ){
				e.salvarExercicio(b, pref.getString("usuario", null));
			}
		}
		
		//atualiza a busca
		listaAerobico = new Aerobico()
		.buscarExerciciosPorPersonal(b,pref.getString("usuario", null));
		
		listaExercicios.addAll(listaAerobico);

		atualizaInterfaceAerobico(listaAerobico);*/
		
	}
	
	public void atualizaInterfaceAerobico(ArrayList<Aerobico> listaAerobico){
		
		ArrayList<RowItemExercicio> rowItensExercicios = new ArrayList<RowItemExercicio>();
				
		for (Aerobico arb : listaAerobico) {
			
			RowItemExercicio item = new RowItemExercicio(arb.getNomeExercicio(),
					"Duração: " + arb.getDuracaoExercicio() + " minuto(s)", "Descanso: " + arb.getDescansoExercicio() + " minuto(s)",arb.getDescricaoExercicio());
			rowItensExercicios.add(item);
		}
		
		
		CustomAdapterExercicio adapter = 
				new CustomAdapterExercicio(getActivity(), rowItensExercicios);
		lstExercicios.setAdapter(adapter);
		
		registerForContextMenu(lstExercicios);
	}
	
	public void refreshListaAnaerobico(){
		clear();
		
		
		ArrayList<Anaerobico> listaAnaerobico = new Anaerobico()
				.buscarExerciciosPorPersonal(b,pref.getString("usuario", null));
		
		atualizarInterfaceAnaerobico(listaAnaerobico);
		
		ArrayList<Anaerobico> sincronizar = new Anaerobico().buscarExerciciosAnaerobicoPorPersonalWeb(pref.getString("usuario", null));
		
		//Inicia sincroniza����o
		for(Anaerobico e : sincronizar){
			boolean flag = false;
			for(Anaerobico x: listaAnaerobico){
				if(e.getCodExercicio() == x.getCodExercicio()){
					e.atualizarExercicio(b);
					flag = true;
				}
			}
			if (flag == false ){
				e.salvarExercicio(b, pref.getString("usuario", null));
			}
		}
		
		listaAnaerobico = new Anaerobico()
		.buscarExerciciosPorPersonal(b,pref.getString("usuario", null));
		
		listaExercicios.addAll(listaAnaerobico);
		
		atualizarInterfaceAnaerobico(listaAnaerobico);

		
	}
	
	public void atualizarInterfaceAnaerobico(ArrayList<Anaerobico> listaAnaerobico ){
		ArrayList<RowItemExercicio> rowItensExercicios = new ArrayList<RowItemExercicio>();
				
		for (Anaerobico arb : listaAnaerobico) {
			
			RowItemExercicio item = new RowItemExercicio(arb.getNomeExercicio(),
					"Repetições: " + arb.getRepeticoesExercicio() + " Minuto(s)", "Descanso: " + arb.getDescansoExercicio() + " Minuto(s)",arb.getDescricaoExercicio());
			rowItensExercicios.add(item);
		}
		
		
		CustomAdapterExercicio adapter = 
				new CustomAdapterExercicio(getActivity(), rowItensExercicios);
		lstExercicios.setAdapter(adapter);
		
		registerForContextMenu(lstExercicios);
	}
	
	
	//Concluir o treinamento
	/*
	public void concluirTreinamento(){
		if(nomeTreinamento.getText().equals("")|| nomeTreinamento.getText() == null){
			Toast.makeText(getActivity(),"�� necess��rio atribuir um nome ao treinamento...", Toast.LENGTH_SHORT).show();
		}else{
			Treinamento t = new Treinamento(0,nomeTreinamento.getText().toString(),exerciciosTreinamento,
					pref.getString("usuario", null),null);
			
			
			Log.i("interface: treinamento", t.toString());
			try{
				if(t.salvarTreinamentoWeb(b)){
					Log.i("interface: salvei web", "salvei web");
					if(t.salvarTreinamento(b, pref.getString("usuario", null))){
						Log.i("interface: salvei local", "salvei local");
						Toast.makeText(getActivity(),"Salvo com sucesso!", Toast.LENGTH_SHORT).show();
						getActivity().finish();
					}
				}
			}catch(Exception ex){
				Toast.makeText(getActivity(),"Erro ao salvar!", Toast.LENGTH_SHORT).show();
			}
		}
		
		
	}
	*/
	
	//clean
	
	public void clear(){
		CustomAdapterTreinamentosRealizado vazio = 
				new CustomAdapterTreinamentosRealizado(getActivity(), new ArrayList<RowItemTreinamentoRealizado>());
		lstExercicios.setAdapter(vazio);
		listaExercicios.clear();
	}
	
	//MENUS
	/*
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		
		switch(item.getItemId()) {
		
		case R.id.mnuEditarExercicio:
			//String teste = listaExercicios.get(info.position).getUsuarioPersonal();
			if (listaExercicios.get(info.position) instanceof Aerobico){
				if(listaExercicios.get(info.position).getUsuarioPersonal().equals("default")){
					Toast.makeText(getActivity(), "Este exercicio n��o pode ser editado", Toast.LENGTH_SHORT).show();
				}else{
					Intent i = new Intent(getActivity(),EditarExercicio.class);
					i.putExtra("tipo", "aerobico");
					i.putExtra("codExercicio", listaExercicios.get(info.position).getCodExercicio());
					startActivity(i);
				}
				
				
			}else if(listaExercicios.get(info.position) instanceof Anaerobico){
				if(listaExercicios.get(info.position).getUsuarioPersonal().equals("default")){
					Toast.makeText(getActivity(), "Este exercicio n��o pode ser editado", Toast.LENGTH_SHORT).show();
				}else{
					Intent i = new Intent(getActivity(),EditarExercicio.class);
					i.putExtra("tipo", "anaerobico");
					i.putExtra("codExercicio", listaExercicios.get(info.position).getCodExercicio());
					startActivity(i);
				}
			}
		
		break;
		
		case R.id.mnuExcluirExercicio:
			
			if (listaExercicios.get(info.position) instanceof Aerobico){
				Aerobico a = (Aerobico)listaExercicios.get(info.position);
				if(a.getUsuarioPersonal().equals("default")){
					Toast.makeText(getActivity(), "N��o �� poss��vel excluir este exerc��cio", Toast.LENGTH_SHORT).show();
				}else{
					if (a.excluirExercicio(b)){
						Toast.makeText(getActivity(), "Excluido com sucesso", Toast.LENGTH_SHORT).show();
						//refreshLista();
					}else {
						Toast.makeText(getActivity(), "Erro ao Excluir", Toast.LENGTH_SHORT).show();
					}
				}
				
				
				
				
			}else if(listaExercicios.get(info.position) instanceof Anaerobico){
				Anaerobico a = (Anaerobico)listaExercicios.get(info.position);
				
				if(a.getUsuarioPersonal().equals("default")){
					Toast.makeText(getActivity(), "N��o �� poss��vel excluir este exerc��cio", Toast.LENGTH_SHORT).show();
				}else{
					if (a.excluirExercicio(b)){
						Toast.makeText(getActivity(), "Excluido com sucesso", Toast.LENGTH_SHORT).show();
						refreshListaAerobico();
					}else {
						Toast.makeText(getActivity(), "Erro ao Excluir", Toast.LENGTH_SHORT).show();
					}
				}
				
				
			}
			break;
			
		case R.id.mnuPublicarExercicio:
			if (listaExercicios.get(info.position) instanceof Aerobico){
				try{
					Aerobico a = (Aerobico)listaExercicios.get(info.position);
					a.salvarExercicio(b, "public");
					Toast.makeText(getActivity(), "Adicionado com Sucesso!",Toast.LENGTH_LONG).show();
				}catch(Exception e){
					Toast.makeText(getActivity(), "Erro: " + e.toString(),Toast.LENGTH_LONG).show();
				}
			}else if(listaExercicios.get(info.position) instanceof Anaerobico){
					try{
						Anaerobico a = (Anaerobico)listaExercicios.get(info.position);
						a.salvarExercicio(b, "public");
						Toast.makeText(getActivity(), "Adicionado com Sucesso!",Toast.LENGTH_LONG).show();
					}catch(Exception e){
						Toast.makeText(getActivity(), "Erro: " + e.toString() ,Toast.LENGTH_LONG).show();
					}
				}
			break;
		case R.id.mnuAdicionarExercicio:
			exerciciosTreinamento.add(listaExercicios.get(info.position));
			Toast.makeText(getActivity(), listaExercicios.get(info.position).getNomeExercicio() + " adicionado com sucesso!", Toast.LENGTH_SHORT).show();
			
			break;
			
				
			}

		return true;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getActivity().getMenuInflater().inflate(R.menu.meus_exercicios_context_menu, menu);
	}
	*/
	@Override
	public void onCreateOptionsMenu(
	    Menu menu, MenuInflater inflater) {
	   inflater.inflate(R.menu.meus_exercicios, menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		/*case R.id.actSalvarTreinamento:
			if (nomeTreinamento.getText().toString().isEmpty()){
				Toast.makeText(getActivity(), "Digite um nome para o treinamento!", Toast.LENGTH_SHORT).show();
			}else{
				try{
					concluirTreinamento();
					Toast.makeText(getActivity(), "Salvo com sucesso", Toast.LENGTH_LONG).show();
					getActivity().finish();
					
				}catch (Exception ex){
					Toast.makeText(getActivity(), "Erro ao salvar!", Toast.LENGTH_LONG).show();
				}
			}
			
			
			break;
		case R.id.actAtualizarExercicios:
			if (aerobicos.isChecked()){
				refreshListaAerobico();
			}else if(anaerobicos.isChecked()){
				refreshListaAnaerobico();
			}
			
			break;
		*/
		}
		return true;
	}
} 
