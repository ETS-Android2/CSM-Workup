package br.com.GUI.treinamentos;

import java.util.ArrayList;

import br.com.Banco.Banco;
import br.com.Classes.Aerobico;
import br.com.Classes.Anaerobico;
import br.com.Classes.Treinamento;
import br.com.WorkUp.R;
import br.com.CustomListView.CustomAdapterExercicio;
import br.com.CustomListView.RowItemExercicio;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class EditarTreinamentoExerciciosAerobicos extends Fragment {
	
	//Atributos de interface
	private ListView lstExerciciosAerobicoTreinamento;
	private Button addExercicioAerobico;
	
	//Persistencias
	private SharedPreferences pref;
	private SharedPreferences tr;
	private Banco b;
	
	
	
	//atributos auxiliares
	private Treinamento treinamento;
	private ArrayList<Aerobico> buscaAerobico;
	private ArrayList<Anaerobico> buscaAnaerobico;
	
/*
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_treinamento);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		mapearComponentes();
		
		//sincronizarExercicios();
		*/
	
	//Create and Resumes
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
		            Bundle savedInstanceState) {
		 
		        View rootView = inflater.inflate(R.layout.activity_editar_treinamento_exercicios_aerobicos, container, false);
		        
		        return rootView;
		 }	
		
		@Override
		public void onActivityCreated (Bundle savedInstanceState){
			super.onActivityCreated(savedInstanceState);
			mapearComponentes();
		}
		
		
	public void mapearComponentes(){
		lstExerciciosAerobicoTreinamento = (ListView) getActivity().findViewById(R.id.lstExercicioAerobicoTreinamento);
		lstExerciciosAerobicoTreinamento.setDivider(null);
		
		addExercicioAerobico = (Button) getActivity().findViewById(R.id.addExercicioAerobico);
		addExercicioAerobico.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				adicionarExercicioAerobico();
			}
		});
		
		b = new Banco(getActivity(), null, null,0);
		pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
		tr = getActivity().getApplicationContext().getSharedPreferences("treinamento", 0);
		
		
		//refreshListaAerobico();
			
	}
	/*
	public void sincronizarExercicios(){
		
		//aerobico
		ArrayList<Aerobico> listaAerobico = new Aerobico()
			.buscarExerciciosPorPersonal(b,pref.getString("usuario", null));
	
		ArrayList<Aerobico> sincronizar = new Aerobico().buscarExerciciosPorPersonalWeb(pref.getString("usuario", null));
			
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
			
		//anaerobico
			
		ArrayList<Anaerobico> listaAnaerobico = new Anaerobico()
			.buscarExerciciosPorPersonal(b,pref.getString("usuario", null));
	
		ArrayList<Anaerobico> sincronizarAnaerobico = new Anaerobico().buscarExerciciosAnaerobicoPorPersonalWeb(pref.getString("usuario", null));
	
		//Inicia sincroniza����o
		for(Anaerobico e : sincronizarAnaerobico){
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
	}
	*/
	@Override
	public void onResume(){
		super.onResume();
		refreshListaAerobico();
		
	}
	
	public void adicionarExercicioAerobico(){
		
		AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                getActivity());
        builderSingle.setIcon(R.drawable.treinamento);
        builderSingle.setTitle("Selecione o exercício aeróbico");
        
        //preenche listView
        
        final ArrayList<Aerobico> novoExercicio = new ArrayList<Aerobico>();
        
		ArrayList<Aerobico> novo= new Aerobico().buscarExerciciosPorPersonal(b, pref.getString("usuario", null));
        
		novoExercicio.addAll(novo);
		
		ArrayList<RowItemExercicio> rowItensExercicios = new ArrayList<RowItemExercicio>();
		for (Aerobico ex : novoExercicio) {
			RowItemExercicio itemAerobico = 
					new RowItemExercicio(ex.getNomeExercicio(),
							"Duração: " + ex.getDuracaoExercicio() + " minuto(s)",
							"Duração: " + ex.getDescansoExercicio() + " minuto(s)",
										ex.getDescricaoExercicio());
								rowItensExercicios.add(itemAerobico);
					
		}
		
        final CustomAdapterExercicio adapterExercicios= new CustomAdapterExercicio(
                getActivity(),rowItensExercicios);

        builderSingle.setNegativeButton("cancelar",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(adapterExercicios,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int codExercicioAdicionar = novoExercicio.get(which).getCodExercicio();
                        //Adiciona No Web Service
                        if(new Treinamento().adicionarExercicioAoTreinamentoWeb(codExercicioAdicionar, -1 , treinamento.getCodTreinamento())){	
                        	//Adiciona no banco Local
                        	if(new Treinamento().adicionarExercicioAoTreinamento(b, codExercicioAdicionar, -1 , treinamento.getCodTreinamento())){
                        		Toast.makeText(getActivity(), "Adicionado!", Toast.LENGTH_SHORT).show();
                        			refreshListaAerobico();
                        			dialog.dismiss();
                        	}
                        }else {
                    		Toast.makeText(getActivity(), "Falha ao adicionar!", Toast.LENGTH_SHORT).show();
                    		dialog.dismiss();
                        }
                    }
                });
        builderSingle.show();

	}
	/*	
	public void adicionarExercicioAnaerobico(View v){
		AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                getActivity());
        builderSingle.setIcon(R.drawable.treinamento);
        builderSingle.setTitle("Selecione o exerc��cio anaer��bico:");
        
        //preenche listView
        
        final ArrayList<Anaerobico> novoExercicio = new ArrayList<Anaerobico>();
        
		ArrayList<Anaerobico> novo= new Anaerobico().buscarExerciciosPorPersonal(b, pref.getString("usuario", null));
	
		novoExercicio.addAll(novo);
		
		ArrayList<RowItemExercicio> rowItensExercicios = new ArrayList<RowItemExercicio>();
		for (Anaerobico ex : novoExercicio) {
			RowItemExercicio itemAnaerobico = new RowItemExercicio(ex.getNomeExercicio()
					, ex.getDescansoExercicio(),ex.getRepeticoesExercicio(),ex.getDescricaoExercicio());
			rowItensExercicios.add(itemAnaerobico);
					
		}
		
        final CustomAdapterExercicio adapterExercicios= new CustomAdapterExercicio(
                getActivity(),rowItensExercicios);

        builderSingle.setNegativeButton("cancelar",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(adapterExercicios,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int codExercicioAdicionar = novoExercicio.get(which).getCodExercicio();
                        //Adiciona no Web service	
                        if(new Treinamento().adicionarExercicioAoTreinamentoWeb(-1, codExercicioAdicionar, treinamento.getCodTreinamento())){
	                        //Adiciona localmente	
                        	if(new Treinamento().adicionarExercicioAoTreinamento(b, -1, codExercicioAdicionar , treinamento.getCodTreinamento())){
	                        		Toast.makeText(getActivity(), "Adicionado!", Toast.LENGTH_SHORT).show();
	                        		if(aerobicos.isChecked()){
	                        			anaerobicos.setChecked(true);
	                        			refreshListaAnaerobico();
	                        		}else if(anaerobicos.isChecked()){
	                        			refreshListaAnaerobico();
	                        		}
	                        		dialog.dismiss();
	                        	}
                        	}else {
                        		Toast.makeText(getActivity(), "Falha ao adicionar!", Toast.LENGTH_SHORT).show();
                        		dialog.dismiss();
                        	}
                        }
                });
        builderSingle.show();
		
	}
	*/		
	public void refreshListaAerobico(){
		int p = tr.getInt("codTreinamento", 0);
		
		Log.i("codTreinamento", "cod = " + p );
		
		treinamento = new Treinamento().buscarTreinamentoPorId(b,p);
		
		Log.i("treinamento", treinamento.toString() );
		
		buscaAerobico = treinamento.buscarExerciciosAerobicoPorTreinamento(b, treinamento.getCodTreinamento());
		ArrayList<RowItemExercicio> rowItensExercicios = new ArrayList<RowItemExercicio>();
		for (Aerobico ex : buscaAerobico) {
				RowItemExercicio item = new RowItemExercicio(
						ex.getNomeExercicio(),
						"Duração: " + ex.getDuracaoExercicio() + "minuto(s)", 
						"Descanso: " + ex.getDescansoExercicio() + "minuto(s)",
						"Descrição: " + ex.getDescricaoExercicio());
						rowItensExercicios.add(item);
		}
		
		CustomAdapterExercicio adapter = 
				new CustomAdapterExercicio(getActivity(), rowItensExercicios);
		lstExerciciosAerobicoTreinamento.setAdapter(adapter);
		
		lstExerciciosAerobicoTreinamento.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
					long arg3) {
			
				
				 AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
				
			        alertDialog.setTitle("Escolha uma Ação");
		
			        alertDialog.setMessage("O que deseja fazer com este exercício?");
			 
			        alertDialog.setIcon(R.drawable.profile);
			 
			        alertDialog.setPositiveButton("Editar Exercicio", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog,int which) {
			            	Intent editarExercicio = new Intent(getActivity(), EditarExercicio.class);
			            		editarExercicio.putExtra("tipo", "aerobico");
			            		editarExercicio.putExtra("codExercicio", buscaAerobico.get(arg2).getCodExercicio());
			            	startActivity(editarExercicio);
			            
			            }
			        });
			 
			        
			        alertDialog.setNegativeButton("Excluir do Treinamento", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int which) {
			            //pega o codExercicio
			            	int idExercicio = 0 ;
			            	idExercicio = buscaAerobico.get(arg2).getCodExercicio();
			            
			            //executa a remoção
			            try{
			            	//Executa remoção Web
			            	if(new Treinamento().removerExercicioDoTreinamentoWeb(idExercicio, treinamento.getCodTreinamento())){
			            		//Executa remoção local
			            		if(new Treinamento().removerExercicioDoTreinamento(b, idExercicio , treinamento.getCodTreinamento())){
				            		Toast.makeText(getActivity(), "Excluido Com sucesso!", Toast.LENGTH_SHORT).show();	
				            			refreshListaAerobico();
				            	}else{
				            		Toast.makeText(getActivity(), "Erro ao excluir!", Toast.LENGTH_SHORT).show();		            		
				            	}
			            	}
			            	
			            }catch(Exception e){
			            	Toast.makeText(getActivity(), "Erro ao excluir!", Toast.LENGTH_SHORT).show();
			            }
			            
			            dialog.cancel();
			            }
			        });
			 
			        // Showing Alert Message
			        alertDialog.show();
			      
			    }
		
				
			});
		
				
		}
		/*
	public void refreshListaAnaerobico(){
			Bundle parametro = getActivity().getIntent().getExtras();
			int p = parametro.getInt("codTreinamento");
			
			treinamento = new Treinamento().buscarTreinamentoPorId(b, p
					);
			
			buscaAnaerobico = treinamento.buscarExerciciosAnaerobicoPorTreinamento(b, treinamento.getCodTreinamento());
			ArrayList<RowItemTreinamentoRealizado> rowItensExercicios = new ArrayList<RowItemTreinamentoRealizado>();
			for (Anaerobico ex : buscaAnaerobico) {
						RowItemTreinamentoRealizado item = new RowItemTreinamentoRealizado(ex.getNomeExercicio()
								, ex.getDescansoExercicio(),ex.getRepeticoesExercicio(),ex.getDescricaoExercicio());
						rowItensExercicios.add(item);
					
					
			}
			
		
		CustomAdapterTreinamentosRealizado adapter = 
				new CustomAdapterTreinamentosRealizado(getActivity(), rowItensExercicios);
		lstExerciciosTreinamento.setAdapter(adapter);
		
		lstExerciciosTreinamento.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
					long arg3) {
			
				
				 AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
				
			        alertDialog.setTitle("Escolha uma A����o");
		
			        alertDialog.setMessage("O que deseja fazer com este exerc��cio?");
			 
			        alertDialog.setIcon(R.drawable.profile);
			 
			        alertDialog.setPositiveButton("Editar Exercicio", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog,int which) {
			            	Intent editarExercicio = new Intent(getActivity(), EditarExercicio.class);
			            	if (aerobicos.isChecked()){
			            		editarExercicio.putExtra("tipo", "aerobico");
			            		editarExercicio.putExtra("codExercicio", buscaAerobico.get(arg2).getCodExercicio());
			            	}else if ( anaerobicos.isChecked()){
			            		editarExercicio.putExtra("tipo", "anaerobico");
			            		editarExercicio.putExtra("codExercicio", buscaAnaerobico.get(arg2).getCodExercicio());
			            	}
			            	startActivity(editarExercicio);
			            
			            }
			        });
			 
			        
			        alertDialog.setNegativeButton("Excluir do Treinamento", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int which) {
			            //pega o codExercicio
			            	int idExercicio = 0 ;
			            if(aerobicos.isChecked()){
			            	idExercicio = buscaAerobico.get(arg2).getCodExercicio();
			            }else if(anaerobicos.isChecked()){
			            	idExercicio = buscaAnaerobico.get(arg2).getCodExercicio();
			            }
			            
			            //executa a remo����o
			            try{
			            	if(new Treinamento().removerExercicioDoTreinamentoWeb(idExercicio, treinamento.getCodTreinamento())){
			            		if(new Treinamento().removerExercicioDoTreinamento(b, idExercicio , treinamento.getCodTreinamento())){
				            		Toast.makeText(getActivity(), "Excluido Com sucesso!", Toast.LENGTH_SHORT).show();	
				            		if(aerobicos.isChecked()){
				            			refreshListaAerobico();
				            		}else{
				            			refreshListaAnaerobico();
				            		}
				            	}else{
				            		Toast.makeText(getActivity(), "Erro ao excluir!", Toast.LENGTH_SHORT).show();		            		
				            	}
			            	}
			            	
			            }catch(Exception e){
			            	Toast.makeText(getActivity(), "Erro ao excluir!", Toast.LENGTH_SHORT).show();
			            }
			            
			            dialog.cancel();
			            }
			        });
			 
			        // Showing Alert Message
			        alertDialog.show();
			      
			    }
		
				
			});
		
	}
	
	//Cria����o de Menus
	@Override
	
	/*
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; getActivity() adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editar_treinamento_actions, menu);
		return true;	
		
	}*/
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.actAdicionarExercicio:
			Intent i = new Intent(getActivity(),GerenciarEdicaoDeExercicios.class);
			startActivity(i);
			return true;
		case android.R.id.home: 
			getActivity().finish();
			return true;
	
		}
		return false;
		
	}

}
