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
package br.com.GUI.aulas;

import java.util.ArrayList;
















import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import br.com.Banco.Banco;
import br.com.Classes.Aerobico;
import br.com.Classes.Aluno;
import br.com.Classes.Anaerobico;
import br.com.Classes.Aula;
import br.com.Classes.Personal;
import br.com.Classes.Treinamento;
import br.com.GUI.treinamentos.PreescreverTreinamento;
import br.com.Utilitarios.WebService;
import br.com.WorkUp.R;
import CustomListView.CustomAdapterAgendamentos;
import CustomListView.CustomAdapterPersonal;
import CustomListView.RowItemAgendamentos;
import CustomListView.RowItemPersonal;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

public class Agenda extends Fragment {
	
	//atributos de interface
	private ListView lstAulas;
	private Spinner spnFiltrarAulas;
	
	//Persistências
	private SharedPreferences pref;
	private Banco b;

	//atributos auxiliares
	private ArrayList<Aula> al = new ArrayList<Aula>();
	ArrayList<RowItemAgendamentos> listaAulas = new ArrayList<RowItemAgendamentos>();
	
	
	
	//Metodos
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		
	        View rootView = inflater.inflate(R.layout.activity_agenda, container, false);
	        
	        return rootView;
	 }	
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
	    super.setUserVisibleHint(isVisibleToUser);
	    if(lstAulas != null){
	    	  onResume();
	    }
	  
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		  super.onActivityCreated(savedInstanceState);
		  
		
		lstAulas = (ListView)getActivity().findViewById(R.id.lstAulasAgendadas);
		lstAulas.setDivider(null);
		
		spnFiltrarAulas = (Spinner) getActivity().findViewById(R.id.spnFiltrarAgenda);
		
		ArrayList<String> opcoesFiltro = new ArrayList<String>();
		opcoesFiltro.add("Hoje");
		opcoesFiltro.add("Neste Mês");
		opcoesFiltro.add("Todas");
		
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,opcoesFiltro);
		spnFiltrarAulas.setAdapter(adapt);
				
		spnFiltrarAulas.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				atualizarAgenda("");
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
	
		b = new Banco (getActivity(),null,null,0);
			
		pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);

		if(pref.getString("tipo", null).equals("aluno")){
			  setHasOptionsMenu(true);
		}
		
		atualizarAgenda("");
		
	
	 
	 
	 }	
	
	@Override
	public void onResume(){
		super.onResume();
		atualizarAgenda("");
	}
	
	public void atualizarAgenda(String filtro){
		clear();
		Calendar c = Calendar.getInstance();
		
		if (pref.getString("tipo", null).equals("personal")){
			//Busca local
			al = new Aula().buscarAulasPorPersonal(b, pref.getString("usuario", null), filtro);
			
			ArrayList<Aula> exibir = new ArrayList<Aula>();
			for(Aula a : al){
				if(spnFiltrarAulas.getSelectedItem().toString().equals("Hoje")){
					if(a.getDiaAula()== c.get(Calendar.DAY_OF_MONTH)
							&& a.getMesAula() == c.get(Calendar.MONTH)
							&& a.getAnoAula() == c.get(Calendar.YEAR)){
						exibir.add(a);
						
					}
				}else if(spnFiltrarAulas.getSelectedItem().toString().equals("Neste Mês")){
					if( a.getMesAula() == c.get(Calendar.MONTH)
							&& a.getAnoAula() == c.get(Calendar.YEAR)){
						exibir.add(a);
						
					}
				}else if(spnFiltrarAulas.getSelectedItem().toString().equals("Todas")){
					exibir.add(a);
				}
			}
			
			
			atualizarInterface(exibir);
			
		}else if ( pref.getString("tipo", null).equals("aluno")){
			
			
			al = new Aula().buscarAulasPorAluno(b,pref.getString("usuario",null),filtro);
			
			
			ArrayList<Aula> exibir = new ArrayList<Aula>();
			for(Aula a : al){
				if(spnFiltrarAulas.getSelectedItem().toString().equals("Hoje")){
					if(a.getDiaAula()== c.get(Calendar.DAY_OF_MONTH)
							&& a.getMesAula() == c.get(Calendar.MONTH)
							&& a.getAnoAula() == c.get(Calendar.YEAR)){
						exibir.add(a);
						
					}
				}else if(spnFiltrarAulas.getSelectedItem().toString().equals("Neste Mês")){
					if( a.getMesAula() == c.get(Calendar.MONTH)
							&& a.getAnoAula() == c.get(Calendar.YEAR)){
						exibir.add(a);
						
					}
				}else if(spnFiltrarAulas.getSelectedItem().toString().equals("Todas")){
					exibir.add(a);
				}
			}
			

			atualizarInterface(exibir);
			
			
		}
	
	}
	
	public void atualizarInterface(ArrayList<Aula> exibir){
		
		for (Aula l : exibir) {
			String dataAula = l.getDiaAula() + "/" + l.getMesAula() + "/" + l.getAnoAula();
			String anoAula = l.getHoraAula() + ":" + l.getMinutoAula();
			
			String confirmacao;
			if(l.getConfirmacaoAulaAluno() == 1 && l.getConfirmacaoAulaPersonal() == 1){
				confirmacao = "Confirmada!";
			}else if(l.getConfirmacaoAulaAluno() == 0 && l.getConfirmacaoAulaPersonal() == 0 ){
				confirmacao = "Cancelada";
			}else{
				confirmacao = "Aguardando Confirmação...";
			}
			
			RowItemAgendamentos item = new RowItemAgendamentos(
					BitmapFactory.decodeResource(getResources(), R.drawable.horarios),
					l.getUsuarioAluno(),
					dataAula, 
					anoAula,
					confirmacao);
			listaAulas.add(item);
		}

		CustomAdapterAgendamentos adapter = 
				new CustomAdapterAgendamentos(getActivity(), listaAulas);
		lstAulas.setAdapter(adapter);
		registerForContextMenu(lstAulas);
		

		
		lstAulas.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				
				Intent intent = new Intent(getActivity(),ConfirmarAula.class);
				intent.putExtra("aula", al.get(arg2).getCodAula());
		    	startActivity(intent);
		    	
			}
		});
	}

	
	//clean
	public void clear(){
		al.clear();
		listaAulas.clear();
		CustomAdapterAgendamentos c = new CustomAdapterAgendamentos(getActivity(), new ArrayList<RowItemAgendamentos>());
		lstAulas.setAdapter(c);
	}
	
	//Criação de Menus
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getActivity().getMenuInflater().inflate(R.menu.agenda_context_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		
		switch(item.getItemId()) {
		case R.id.mnuExcluirAula:
			Toast.makeText(getActivity(), "entreeei", Toast.LENGTH_SHORT).show();
		break;
		case R.id.mnuEditarAula:
			Toast.makeText(getActivity(), "entreeei editaar", Toast.LENGTH_SHORT).show();
		break;
			
		
		}
		return true;
	}
	
	@Override
	public void onCreateOptionsMenu(
	    Menu menu, MenuInflater inflater) {
	   inflater.inflate(R.menu.agenda_actions, menu);
	   
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
            	atualizarAgenda(newText.toString());

            } else {
                // Do something when there's no input
            	atualizarAgenda(newText.toString());
            }
            return false;
        }

		@Override
		public boolean onQueryTextSubmit(String arg0) {
			// TODO Auto-generated method stub
			atualizarAgenda(arg0.toString());
			return false;
		}
    });
	}

}
	