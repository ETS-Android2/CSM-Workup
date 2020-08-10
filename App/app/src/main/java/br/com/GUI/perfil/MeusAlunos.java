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
package br.com.GUI.perfil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.Banco.Banco;
import br.com.Classes.Aluno;
import br.com.Classes.Aula;
import br.com.GUI.aulas.MarcarAula;
import br.com.GUI.avaliacoes.NovaAvaliacao;
import br.com.GUI.treinamentos.GerenciarAcompanhamento;
import br.com.GUI.treinamentos.PreescreverTreinamento;
import br.com.WorkUp.R;
import br.com.CustomListView.CustomAdapterExpandedAluno;
import br.com.CustomListView.RowItemAluno;
import br.com.CustomListView.RowItemExpandedAluno;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

public class MeusAlunos extends Fragment {

	//Atributos do expanded List View
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<RowItemAluno> listDataHeader;
	HashMap<RowItemAluno, List<RowItemExpandedAluno>> listDataChild = new HashMap<RowItemAluno, List<RowItemExpandedAluno>>();
	
	//Atributos de interface
	private TextView lblMeusAlunos;
	private ArrayList<Aluno> alunos = new ArrayList<Aluno>();
	
	
	//Persistencias locais
	private Banco b;
	private SharedPreferences pref;
	
	
	@Override
	public void onResume(){
		super.onResume();
		atualizarMeusAlunos("");
		
	}
	
	@Override
	public void onPause(){
		super.onPause();
		atualizarMeusAlunos("");
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.activity_meus_alunos, container, false);
	        
	        return rootView;
	 }	
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		mapearComponentes();
		
		atualizarMeusAlunos("");
		
	
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
	    super.setUserVisibleHint(isVisibleToUser);
	    if(expListView != null){
	    	   onResume();
	    }
	 
	}
	
	public void mapearComponentes(){
		//meusAlunos = (ListView) getActivity().findViewById(R.id.meusAlunos);
				expListView = (ExpandableListView) getActivity().findViewById(R.id.expMeusAlunos);

				//meusAlunos.setDivider(null);
				lblMeusAlunos = (TextView) 	getActivity().findViewById(R.id.lblMeusAlunos);
				setHasOptionsMenu(true);
				b = new Banco (getActivity(),null,null,0);
				pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
				
	}
	
	
	
		
	public void atualizarMeusAlunos(String filtro){
		
		
		alunos = new Aluno().buscarAlunosPorPersonal(b, filtro, pref.getString("usuario",null));
		
		//Log.i("home personal alunos", alunos.toString());
		atualizarInterface();
		
	}
	
	public void atualizarInterface(){
		listDataHeader = new ArrayList<RowItemAluno>();
		
		if(alunos.isEmpty()){
			Log.i("alunos","esta vazio");
		}else{
			Log.i("aluno","nao esta vazio");
		}
		
		List<RowItemExpandedAluno> opcoes = prepararOpcoes();
		
		for(Aluno a : alunos){
			
			byte[] foto = new Aluno().buscarFotoAluno(b,a.getUsuario());
			Bitmap bmp = BitmapFactory.decodeByteArray(foto, 0, foto.length);
			
			RowItemAluno item = new RowItemAluno();
			item.setImagemPerfil(bmp);
			item.setNomePerfil(a.getNome());
			item.setUsuarioAluno(a.getUsuario());
			
			listDataHeader.add(item);
			
	
			
		
		}
		
		
		for(RowItemAluno row : listDataHeader){
			listDataChild.put(row, opcoes);
		}
		/*CustomAdapterAluno adapter = 
				new CustomAdapterAluno(getActivity(),alunosPerfil);
		meusAlunos.setAdapter(adapter);
		
		meusAlunos.setOnItemClickListener(new OnItemClickListener() {
			
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			String usuario = alunos.get(arg2).getUsuario();			
			Intent intent = new Intent(getActivity(),AdicionarAluno.class);
			intent.putExtra("usuario", usuario);
	    	startActivity(intent);
				
			}
		});*/
		
		
		

		listAdapter = new CustomAdapterExpandedAluno(getActivity(), listDataHeader,
				listDataChild);

		expListView.setAdapter(listAdapter);
	
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					final int groupPosition, int childPosition, long id) {
				 Intent i;
				 switch(childPosition){
				 	case 0:
				 		i = new Intent(getActivity(),GerenciarAcompanhamento.class);
				 		i.putExtra("alunoAcompanhamento", listDataHeader.get(groupPosition).getUsuarioAluno());
				 		startActivity(i);
				 		break;
				 	case 1:
				 		i = new Intent(getActivity(),NovaAvaliacao.class);
				 		i.putExtra("alunoAvaliacao", listDataHeader.get(groupPosition).getUsuarioAluno());
				 		startActivity(i);
				 		break;
				 	case 2:
				 		i = new Intent(getActivity(),PreescreverTreinamento.class);
				 		i.putExtra("usuario", listDataHeader.get(groupPosition).getUsuarioAluno());
				 		startActivity(i);
				 		break;
				 	case 3:
				 		i = new Intent(getActivity(),MarcarAula.class);
				 		i.putExtra("usuario", listDataHeader.get(groupPosition).getUsuarioAluno());
				 		startActivity(i);
				 		break;
				 	case 4:
				 		

						AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
				        alertDialog.setTitle("Atenção");
				        alertDialog.setMessage("Você tem certeza que deseja remover este aluno?");
				        alertDialog.setIcon(R.drawable.critical);
				        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog,int which) {
					        	Aluno a = new Aluno().buscarAlunoEspecifico(b, listDataHeader.get(groupPosition).getUsuarioAluno());
						 		if(a.removerAlunoWeb(a.getUsuario()) && a.removerAluno(b)){
						 				Toast.makeText(getActivity(), "Removido com Sucesso", Toast.LENGTH_SHORT).show();
						 			// Exclui todas as aulas marcadas com o personal
					     				ArrayList<Aula> aulasParaExcluir = new ArrayList<Aula>();
					     				
					     				aulasParaExcluir = new Aula().buscarAulasPorPersonalWeb(a.getUsuarioPersonal(), "");
					     				for(Aula c : aulasParaExcluir){
					     					if(c.excluirAulaWeb() && c.excluirAula(b)){
					     						Log.i("INFORMAÇÃO", "Aulas excluidas com sucesso!");
					     					}
					     				}
						 		}else {
						     			Toast.makeText(getActivity(), "Falha ao Remover o aluno", Toast.LENGTH_SHORT).show();
						     	}
				            }
				        });
				        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int which) {
				            	dialog.cancel();
				            }
				        });
				 
				        alertDialog.show();
				       
				 		
				 		
				 		break;	
				 }
					
				atualizarInterface();
				
				return false;
			}
			
		});
	}
	
	public List<RowItemExpandedAluno> prepararOpcoes(){
		List<RowItemExpandedAluno> opcoes = new ArrayList<RowItemExpandedAluno>();
		opcoes.add(new RowItemExpandedAluno("Acompanhamento", R.drawable.acompanhamento));
		opcoes.add(new RowItemExpandedAluno("Avaliações", R.drawable.prancheta));
		opcoes.add(new RowItemExpandedAluno("Preescrever Treinamento", R.drawable.personal));
		opcoes.add(new RowItemExpandedAluno("Agendar Aula", R.drawable.horarios));
		opcoes.add(new RowItemExpandedAluno("Excluir", R.drawable.excluir));
		return opcoes;
	}
	
	
	
	//MENUS
	@Override
	public void onCreateOptionsMenu(
		      Menu menu, MenuInflater inflater) {
		   inflater.inflate(R.menu.adicionar_aluno_actions, menu);
		   
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
	            	atualizarMeusAlunos(newText.toString());

	            } else {
	                // Do something when there's no input
	            	atualizarMeusAlunos(newText.toString());
	            }
	            return false;
	        }

			@Override
			public boolean onQueryTextSubmit(String arg0) {
				// TODO Auto-generated method stub
				atualizarMeusAlunos(arg0.toString());
				return false;
			}
	    });
	}

}
