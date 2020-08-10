package br.com.GUI.treinamentos;

import java.util.ArrayList;

import br.com.Banco.Banco;
import br.com.Classes.Treinamento;
import br.com.WorkUp.R;
import br.com.CustomListView.CustomAdapterTreinamento;
import br.com.CustomListView.RowItemTreinamento;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;

public class MeusTreinamentos extends Fragment {
	
	//Atributos de interface
	private ListView lstTreinamentos;
	private LinearLayout lytAvisoSemTreinamentos;
	private SharedPreferences pref;
	
	
	//Atributos auxiliares
	private ArrayList <Treinamento> busca = new ArrayList<Treinamento>();
	private ArrayList <Treinamento> sincronizar = new ArrayList<Treinamento>();
	
	//Persistencias locais
	private Banco b;
	
	

	//Create and Resumes
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.activity_meus_treinamentos, container, false);
	        
	        return rootView;
	 }	
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		  super.onActivityCreated(savedInstanceState);
		  setHasOptionsMenu(true);
		b = new Banco(getActivity(), null, null,0);
		pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
		/*iniciarTreinamento = (Button)getActivity().findViewById(R.id.btnIniciarTreinamento);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "BebasNeue Bold.ttf");
		iniciarTreinamento.setTypeface(font);
		
		
		

		
		
		//listener on click
		
		iniciarTreinamento.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getActivity(),ConfirmarInicioTreinamento.class);
				startActivity(i);
				
			}
		});*/
		
		
		
		
		
		//listener list View
		lstTreinamentos = (ListView)getActivity().findViewById(R.id.lstMeusTreinamentos);
		lstTreinamentos.setDivider(null);
		
		lytAvisoSemTreinamentos = (LinearLayout) getActivity().findViewById(R.id.lytAvisoSemTreinamento);
		lytAvisoSemTreinamentos.setVisibility(View.GONE);
		lstTreinamentos.setOnItemClickListener(new OnItemClickListener() {
			
		

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			int codTreino = busca.get(arg2).getCodTreinamento();
			Intent i = new Intent(getActivity(),GerenciarEdicaoDeTreinamentos.class);
			i.putExtra("codTreinamento", codTreino);
			startActivity(i);
		}

		});
		
		/*edtFiltro = (EditText)getActivity().findViewById(R.id.edtFiltroTreinamento);
		btnPesquisar = (ImageButton)getActivity().findViewById(R.id.btnPesquisar);
		
		btnPesquisar.setOnClickListener(new View.OnClickListener() {

		    public void onClick(View v) {
		       refresh();
		    }
		});*/
		
		
		  
		
	 
	    }	
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
	    super.setUserVisibleHint(isVisibleToUser);
	    if(lstTreinamentos != null){
		    onResume();
	    }
	}
	
	@Override
	public void onResume(){
		super.onResume();
		refresh("");
		atualizaInterface();
		
	}
	
	
	//Atualizar
	public void refresh(String filtro){
		
		//Busca local 
		busca = new Treinamento().buscarTreinamentos(b,pref.getString("usuario", null),filtro);
		
		atualizaInterface();
		/*
		
		//busca webService
		sincronizar = new Treinamento().buscarTreinamentosWeb(pref.getString("usuario", null), filtro);
		
		
		
		//inicia sincroniza����o
		for(Treinamento x: sincronizar){
			boolean flag = false;
			if(!busca.isEmpty()){
				for(Treinamento y: busca){
					if(x.getCodTreinamento() == y.getCodTreinamento()){
						flag = true;
					}
				}	
			}
			if(flag == false ){
				ArrayList<Aerobico> exeAerobicos = new ArrayList<Aerobico>();
				ArrayList<Anaerobico> exeAnaerobicos = new ArrayList<Anaerobico>();
				try{
					exeAerobicos = new Treinamento().buscarExerciciosAerobicoPorTreinamentoWeb(x.getCodTreinamento());
					exeAnaerobicos = new Treinamento().buscarExerciciosAnaerobicoPorTreinamentoWeb(x.getCodTreinamento());
				}catch(Exception e){
					
				}
			
				ArrayList<Exercicio> exercicios = new ArrayList<Exercicio>();
				
				exercicios.addAll(exeAerobicos);
				exercicios.addAll(exeAnaerobicos);
				
				x.setExercicios(exercicios);
				
				for(Aerobico y : exeAerobicos){
					y.salvarExercicio(b, pref.getString("usuario", null));
				}
				for(Anaerobico y: exeAnaerobicos){
					y.salvarExercicio(b, pref.getString("usuario", null));
				}
						
		//atualizaInterface();
		
				if(x.salvarTreinamento(b, pref.getString("usuario", null))){
					Log.i("salvar no banco local", "Treinamento salvo no banco Local");
				}else{
					Log.i("salvar no banco local", "Erro ao salvar o treinamento");
				}
			}
		}
		
		busca.clear();
		busca.addAll(sincronizar);
		
		atualizaInterface();
		*/
	}
	
	public void atualizaInterface(){
		
		ArrayList<RowItemTreinamento> treinamentos = new ArrayList<RowItemTreinamento>();
		if(!busca.isEmpty()){
			for(Treinamento t : busca){
				int numeroDeExercicios = t.buscarNumeroDeExerciciosPorTreinamento(b, t.getCodTreinamento());
				RowItemTreinamento rowItem = new RowItemTreinamento(
						t.getNomeTreinamento(),
						numeroDeExercicios + " exercicios");
				treinamentos.add(rowItem);
			}
		}
		CustomAdapterTreinamento adapter = new CustomAdapterTreinamento(getActivity(), treinamentos);
		lstTreinamentos.setAdapter(adapter);
		
		if(lstTreinamentos.getAdapter().isEmpty()){
			lytAvisoSemTreinamentos.setVisibility(View.VISIBLE);
		}
		registerForContextMenu(lstTreinamentos);
	}
	
	
	//MENUS
	@Override
	public void onCreateOptionsMenu(
	    Menu menu, MenuInflater inflater) {
	   inflater.inflate(R.menu.meus_treinamentos_actions, menu);
	   
	   final SearchView searchView = new SearchView(getActivity().getActionBar().getThemedContext());
	    searchView.setQueryHint("Search");
	    
	    menu.add(Menu.NONE,Menu.NONE,1,"@string")
     .setIcon(android.R.drawable.ic_menu_search)
     .setActionView(searchView)
     .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

 searchView.setOnQueryTextListener(new OnQueryTextListener() {
     @Override
     public boolean onQueryTextChange(String newText) {
         if (newText.length() > 0) {
             // Search
       	  refresh(newText.toString());
       	  
         } else {
             // Do something when there's no input
       	  refresh(newText.toString());
         }
         return false;
     }

		@Override
		public boolean onQueryTextSubmit(String arg0) {
			// TODO Auto-generated method stub
			refresh(arg0);
			return false;
		}
 	});

	}
		
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getActivity().getMenuInflater().inflate(R.menu.meus_treinamentos_context_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		
		switch(item.getItemId()) {
		
		case R.id.mnuExcluirTreinamento:
			//Toast.makeText(getActivity(), "entrei", Toast.LENGTH_SHORT).show();
			if(new Treinamento().removerTreinamento(b, busca.get(info.position).getCodTreinamento())){
				Toast.makeText(getActivity(), "Excluido com sucesso!", Toast.LENGTH_SHORT).show();
				refresh("");
				return true;
			}else{
				Toast.makeText(getActivity(), "Erro ao excluir!", Toast.LENGTH_SHORT).show();
				return true;
			}
		}
		return true;
	}
	
}
