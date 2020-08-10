package br.com.GUI.treinamentos;

import java.util.ArrayList;

import br.com.Banco.Banco;
import br.com.Classes.Aerobico;
import br.com.Classes.Anaerobico;
import br.com.Classes.Exercicio;
import br.com.Classes.Treinamento;
import br.com.WorkUp.R;
import br.com.WorkUp.R.id;
import br.com.WorkUp.R.layout;
import br.com.WorkUp.R.menu;
import CustomListView.CustomAdapterAgendamentos;
import CustomListView.CustomAdapterExercicio;
import CustomListView.CustomAdapterTreinamentosRealizado;
import CustomListView.RowItemAgendamentos;
import CustomListView.RowItemExercicio;
import CustomListView.RowItemTreinamentoRealizado;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ToggleButton;

public class EditarTreinamentoExerciciosAnaerobicos extends Fragment {
	
	//Atributos de interface
	private ListView lstExerciciosAnaerobicoTreinamento;
	private Button addExercicioAnaerobico;
	
	//Persistencias
	private SharedPreferences pref;
	private SharedPreferences tr;
	private Banco b;
	
	//atributos auxiliares
	private Treinamento treinamento;
	private ArrayList<Aerobico> buscaAerobico;
	private ArrayList<Anaerobico> buscaAnaerobico;
	

	/*@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_treinamento);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		mapearComponentes();
		
		//sincronizarExercicios();
		
		}
	*/
	
	//Create and Resumes
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
		            Bundle savedInstanceState) {
		 
		        View rootView = inflater.inflate(R.layout.activity_editar_treinamento_exercicios_anaerobicos, container, false);
		        
		        return rootView;
		 }	
		
		
		@Override
		public void onActivityCreated (Bundle savedInstanceState){
			super.onActivityCreated(savedInstanceState);
			mapearComponentes();
		}
		
		
	public void mapearComponentes(){
		lstExerciciosAnaerobicoTreinamento= (ListView) getActivity().findViewById(R.id.lstExerciciosAnaerobicoTreinamento);
		lstExerciciosAnaerobicoTreinamento.setDivider(null);
		
		addExercicioAnaerobico = (Button) getActivity().findViewById(R.id.addExercicioAnaerobico);
		addExercicioAnaerobico.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				adicionarExercicioAnaerobico();
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
	}*/
	
	@Override
	public void onResume(){
		super.onResume();
		refreshListaAnaerobico();
		
	}
	
	/*
	public void adicionarExercicioAerobico(View v){
		
		AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                getActivity());
        builderSingle.setIcon(R.drawable.treinamento);
        builderSingle.setTitle("Selecione o exercício aeróbico:");
        
        //preenche listView
        
        final ArrayList<Aerobico> novoExercicio = new ArrayList<Aerobico>();
        
		ArrayList<Aerobico> novo= new Aerobico().buscarExerciciosPorPersonal(b, pref.getString("usuario", null));
        
		novoExercicio.addAll(novo);
		
		ArrayList<RowItemExercicio> rowItensExercicios = new ArrayList<RowItemExercicio>();
		for (Aerobico ex : novoExercicio) {
			RowItemExercicio itemAerobico = 
					new RowItemExercicio(ex.getNomeExercicio(),
							ex.getDuracaoExercicio(), ex.getDescansoExercicio(),ex.getDescricaoExercicio());
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
                        		if(aerobicos.isChecked()){
                        			refreshListaAerobico();
                        		}else if(anaerobicos.isChecked()){
                        			aerobicos.setChecked(true);
                        			refreshListaAerobico();
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
	public void adicionarExercicioAnaerobico(){
		AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                getActivity());
        builderSingle.setIcon(R.drawable.treinamento);
        builderSingle.setTitle("Selecione o exercício anaeróbico");
        
        //preenche listView
        
        final ArrayList<Anaerobico> novoExercicio = new ArrayList<Anaerobico>();
        
		ArrayList<Anaerobico> novo= new Anaerobico().buscarExerciciosPorPersonal(b, pref.getString("usuario", null));
	
		novoExercicio.addAll(novo);
		
		ArrayList<RowItemExercicio> rowItensExercicios = new ArrayList<RowItemExercicio>();
		for (Anaerobico ex : novoExercicio) {
			RowItemExercicio itemAnaerobico = new RowItemExercicio(ex.getNomeExercicio()
					, "Duração: " + ex.getDescansoExercicio() + " minuto(s)",
					"Duração: " + ex.getRepeticoesExercicio() + " minuto(s)",
								ex.getDescricaoExercicio());
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
	                        		refreshListaAnaerobico();
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
	public void refreshListaAerobico(){
		Bundle parametro = getActivity().getIntent().getExtras();
		int p = parametro.getInt("codTreinamento");
		
		treinamento = new Treinamento().buscarTreinamentoPorId(b,p);
		
		buscaAerobico = treinamento.buscarExerciciosAerobicoPorTreinamento(b, treinamento.getCodTreinamento());
		ArrayList<RowItemTreinamentoRealizado> rowItensExercicios = new ArrayList<RowItemTreinamentoRealizado>();
		for (Aerobico ex : buscaAerobico) {
				RowItemTreinamentoRealizado item = new RowItemTreinamentoRealizado(ex.getNomeExercicio(),
						ex.getDuracaoExercicio(), ex.getDescansoExercicio(),ex.getDescricaoExercicio());
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
			            	//Executa remo����o Web
			            	if(new Treinamento().removerExercicioDoTreinamentoWeb(idExercicio, treinamento.getCodTreinamento())){
			            		//Executa remo����o local
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
		*/
	public void refreshListaAnaerobico(){
			int p = tr.getInt("codTreinamento", 0);
			
			treinamento = new Treinamento().buscarTreinamentoPorId(b, p);
			
			buscaAnaerobico = treinamento.buscarExerciciosAnaerobicoPorTreinamento(b, treinamento.getCodTreinamento());
			ArrayList<RowItemExercicio> rowItensExercicios = new ArrayList<RowItemExercicio>();
			for (Anaerobico ex : buscaAnaerobico) {
						RowItemExercicio item = new RowItemExercicio(ex.getNomeExercicio(), 
								"Descanso: "  + ex.getDescansoExercicio() + " minuto(s)",
								"Repeticões: " + ex.getRepeticoesExercicio() + " repeticão(ões)",
								"Descrição: " + ex.getDescricaoExercicio());
								rowItensExercicios.add(item);
					
					
			}
			
		
		CustomAdapterExercicio adapter = 
				new CustomAdapterExercicio(getActivity(), rowItensExercicios);
		lstExerciciosAnaerobicoTreinamento.setAdapter(adapter);
		
		lstExerciciosAnaerobicoTreinamento.setOnItemClickListener(new OnItemClickListener() {

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
			            	editarExercicio.putExtra("tipo", "anaerobico");
			            	editarExercicio.putExtra("codExercicio", buscaAnaerobico.get(arg2).getCodExercicio());
			            	startActivity(editarExercicio);
			            
			            }
			        });
			 
			        
			        alertDialog.setNegativeButton("Excluir do Treinamento", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int which) {
			            //pega o codExercicio
			            	int idExercicio = 0 ;
			            	idExercicio = buscaAnaerobico.get(arg2).getCodExercicio();
			            	
			            //executa a remoção
			            try{
			            	if(new Treinamento().removerExercicioDoTreinamentoWeb(idExercicio, treinamento.getCodTreinamento())){
			            		Log.i("treinamento e id", treinamento.toString() + idExercicio);
			            		if(new Treinamento().removerExercicioDoTreinamento(b, idExercicio , treinamento.getCodTreinamento())){
				            		Toast.makeText(getActivity(), "Excluido Com sucesso!", Toast.LENGTH_SHORT).show();	
				            		refreshListaAnaerobico();
				            		
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; getActivity() adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editar_treinamento_actions, menu);
		return true;	
		
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.actAdicionarExercicio:
			Intent i = new Intent(getActivity(),GerenciarTreinamentos.class);
			startActivity(i);
			return true;
		case android.R.id.home: 
			finish();
			return true;
	
		}
		return false;
		
	}*/

}
