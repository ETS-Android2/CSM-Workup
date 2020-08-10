//    This file is part of WorkUp.
//
//    WorkUp is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    WorkUp is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with WorkUp.  If not, see <http://www.gnu.org/licenses/>.
//
package br.com.GUI.avaliacoes;

import java.util.ArrayList;
import java.util.Vector;

import org.kobjects.mime.Decoder;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import br.com.Banco.Banco;
import br.com.Classes.Aluno;
import br.com.Classes.Avaliacoes;
import br.com.Classes.Personal;
import br.com.Utilitarios.WebService;
import br.com.WorkUp.R;
import br.com.WorkUp.R.layout;
import br.com.WorkUp.R.menu;
import CustomListView.CustomAdapterAvaliacoes;
import CustomListView.CustomAdapterPersonal;
import CustomListView.RowItemAvaliacao;
import CustomListView.RowItemPersonal;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.ToggleButton;

public class AvaliacoesAluno extends Activity {
	
	//componentes visuais
	private ListView lstMinhasAvaliacoes;
	
	//persistencias
	private Banco b;
	private SharedPreferences pref;

	//Atributos auxiliares
	private ArrayList<Avaliacoes> buscaAvaliacoes = new ArrayList<Avaliacoes>();
	private String usuarioAluno;

	/*
	//Creates and resume
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {

	        View rootView = inflater.inflate(R.layout.activity_minhas_avaliacoes, container, false);
	        
	        return rootView;
	 }	
	
	
		
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		
		mapearComponentes();

	}*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_avaliacoes_aluno);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		mapearComponentes();
	
		//recebe parametros
		 
	}
	
	
	
	@Override 
	public void onResume(){
		super.onResume();
		

	}

	public void mapearComponentes(){
		b = new Banco (this,null,null,0);
		pref = this.getApplicationContext().getSharedPreferences("MyPref", 0);
		Typeface font = Typeface.createFromAsset(this.getAssets(), "BebasNeue Bold.ttf");
		lstMinhasAvaliacoes = (ListView)this.findViewById(R.id.lstMinhasAvaliacoes);
		lstMinhasAvaliacoes.setDivider(null);
		 usuarioAluno = getIntent().getExtras().getString("usuario");
		/*
		avaliacoesAluno.setChecked(false);
		
		if(pref.getString("tipo", null).equals("aluno")){
			avaliacoesPersonal.setVisibility(View.GONE);
			avaliacoesAluno.setVisibility(View.GONE);

			atualizarAvaliacoesPessoaisAlunos("");
			setHasOptionsMenu(false);
		}

		//listeners
		
		avaliacoesPersonal.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					avaliacoesAluno.setChecked(false);
					atualizarAvaliacoesPersonal("");
				}
				
			}
		});
		
		avaliacoesAluno.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					avaliacoesPersonal.setChecked(false);
					atualizarAvaliacoesAluno("");
				}
				
			}
		});
		
		avaliacoesPersonal.setChecked(true);
		atualizarAvaliacoesPersonal("");*/
	}
	
	
	/*
	//atualizar
	public void atualizarAvaliacoesPersonal(String filtro){
		
		buscaAvaliacoes = new Avaliacoes().buscarAvaliacoesPessoaisPersonal(b, pref.getString("usuario", null));
		
		atualizarInterface();
		
		//Inicia Sincroniza����o
		
		buscaAvaliacoes = new Avaliacoes().buscarAvaliacoesPessoaisPersonalWeb(pref.getString("usuario", null));
		
		ArrayList<Avaliacoes> aux = new ArrayList<Avaliacoes>();
		aux = new Avaliacoes().buscarAvaliacoesPessoaisPersonal(b, pref.getString("usuario", null));
		
	
		
		//Atualiza a Base local
		for(Avaliacao x : buscaAvaliacoes){
			boolean flag = false;
			for(Avaliacao y: aux){
				if (x.getCodAvaliacoes() == y.getCodAvaliacoes()){
					x.editarAvaliacao(b);
					flag = true;
					break;
				}
			}
			if(flag == false){
				x.salvarAvaliacao(b);
				// TODO Push a notification
			}
		}
		
		atualizarInterface();
		
		
		
	}
	
	public void atualizarAvaliacoesAluno(String filtro){
		
		buscaAvaliacoes = new Avaliacoes()
								.buscarAvaliacoesDeAlunos(b,pref.getString("usuario", null));
		
		atualizarInterface();
		
		buscaAvaliacoes = new Avaliacoes().buscarAvaliacoesPessoaisPersonalWeb(pref.getString("usuario", null));
		
		atualizarInterface();
		
		ArrayList<Avaliacoes> aux = new ArrayList<Avaliacoes>();
		aux = new Avaliacoes().buscarAvaliacoesPessoaisPersonal(b, pref.getString("usuario", null));
		
	
		
		//Atualiza a Base local
		for(Avaliacao x : buscaAvaliacoes){
			boolean flag = false;
			for(Avaliacao y: aux){
				if (x.getCodAvaliacoes() == y.getCodAvaliacoes()){
					x.editarAvaliacao(b);
					flag = true;
					break;
				}
			}
			if(flag == false){
				x.salvarAvaliacao(b);
				// TODO Push a notification
			}
		}
		
		atualizarInterface();
	}
	
	public void atualizarAvaliacoesPessoaisAlunos(final String filtro){
		
		buscaAvaliacoes = new Avaliacoes()
				.buscarAvaliacoesPessoaisAluno(b,pref.getString("usuario",null));
		
		atualizarInterface();
		
		buscaAvaliacoes = new Avaliacoes().buscarAvaliacoesPessoaisAlunoWeb(pref.getString("usuario", null));
		
		ArrayList<Avaliacoes> aux = new ArrayList<Avaliacoes>();
		aux = new Avaliacoes().buscarAvaliacoesPessoaisAluno(b, pref.getString("usuario", null));
		
	
		
		//Atualiza a Base local
		for(Avaliacao x : buscaAvaliacoes){
			boolean flag = false;
			for(Avaliacao y: aux){
				if (x.getCodAvaliacoes() == y.getCodAvaliacoes()){
					x.editarAvaliacao(b);
					flag = true;
					break;
				}
			}
			if(flag == false){
				x.salvarAvaliacao(b);
				// TODO Push a notification
			}
		}
		
		atualizarInterface();
		
	}
	
	public void atualizarInterface(){
		ArrayList<RowItemAvaliacao> resultado = new ArrayList<RowItemAvaliacao>();
		
		for(Avaliacao v : buscaAvaliacoes){
			RowItemAvaliacao item  = new RowItemAvaliacao(
					BitmapFactory.decodeResource(getResources(),
							R.drawable.prancheta),
							v.getUsuarioAluno(),
							v.getDataAvaliacoes(),
							v.getHoraAvaliacoes(),
							v.getResultado());
			resultado.add(item);
		}
		CustomAdapterAvaliacoes adapter = 
				new CustomAdapterAvaliacoes(this,resultado);
		lstMinhasAvaliacoes.setAdapter(adapter);
		
		registerForContextMenu(lstMinhasAvaliacoes);
		
		lstMinhasAvaliacoes.setOnItemClickListener(new OnItemClickListener() {
			
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			Intent intent = new Intent(AvaliacoesAluno.this,VisualizarAvaliacao.class);
			intent.putExtra("codAvaliacao", buscaAvaliacoes.get(arg2).getCodAvaliacoes());
	    	startActivity(intent);	
				
			}
		});
	}
	
	
	
	//MENUS
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
			super.onCreateContextMenu(menu, v, menuInfo);
			this.getMenuInflater().inflate(R.menu.minhas_avaliacoes_context_menu, menu);
		}
		
		@Override
	public boolean onContextItemSelected(MenuItem item) {
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			
			switch(item.getItemId()) {
			
			case R.id.mnuExcluirAvaliacao:
				try{
					buscaAvaliacoes.get(info.position).excluirAvaliacao(b);
					Toast.makeText(this,  "Sucesso!", Toast.LENGTH_SHORT).show();
				}catch(Exception ex){
					Log.i("Erro ao excluir a avaliacao", ex.toString());
				}
			
			break;
			
			}

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
		
	/*	
	@Override
	public void onCreateOptionsMenu(
	    Menu menu, MenuInflater inflater) {
	   inflater.inflate(R.menu.minhas_avaliacoes_actions, menu);
	   final SearchView searchView = new SearchView(this.getActionBar().getThemedContext());
	    searchView.setQueryHint(getString(R.string.label_buscaAvaliacoes));
	    
	    menu.add(Menu.NONE,Menu.NONE,1,"@string")
       .setIcon(android.R.drawable.ic_menu_search)
       .setActionView(searchView)
       .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

	   searchView.setOnQueryTextListener(new OnQueryTextListener() {
       @Override
       public boolean onQueryTextChange(String newText) {
           if (newText.length() > 0) {
        	   if(pref.getString("usuario", null).equals("aluno")){
       				atualizarAvaliacoesPessoaisAlunos(newText);
	       		}else if(avaliacoesAluno.isChecked()){
	       			atualizarAvaliacoesAluno(newText);
	       	
	       		}else if(avaliacoesPersonal.isChecked()){
	       			atualizarAvaliacoesPersonal(newText);
	       		}

           } else {
        	   if(pref.getString("usuario", null).equals("aluno")){
      				atualizarAvaliacoesPessoaisAlunos(newText);
	       		}else if(avaliacoesAluno.isChecked()){
	       			atualizarAvaliacoesAluno(newText);
	       	
	       		}else if(avaliacoesPersonal.isChecked()){
	       			atualizarAvaliacoesPersonal(newText);
	       		}
           }
           return false;
       }

		@Override
		public boolean onQueryTextSubmit(String arg0) {
			 if(pref.getString("usuario", null).equals("aluno")){
    				atualizarAvaliacoesPessoaisAlunos(arg0);
	       		}else if(avaliacoesAluno.isChecked()){
	       			atualizarAvaliacoesAluno(arg0);
	       	
	       		}else if(avaliacoesPersonal.isChecked()){
	       			atualizarAvaliacoesPersonal(arg0);
	       		}
			return false;
		}
   	});
	}*/
}
