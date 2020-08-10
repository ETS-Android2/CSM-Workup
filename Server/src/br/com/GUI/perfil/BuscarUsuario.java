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
import java.util.List;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import br.com.Banco.Banco;
import br.com.Classes.Aluno;
import br.com.Classes.FotosAvaliacao;
import br.com.Classes.Personal;
import br.com.Utilitarios.ImageUtils;
import br.com.Utilitarios.WebService;
import br.com.WorkUp.R;
import br.com.WorkUp.R.id;
import br.com.WorkUp.R.layout;
import br.com.WorkUp.R.menu;
import CustomListView.CustomAdapterAluno;
import CustomListView.CustomAdapterPersonal;
import CustomListView.RowItemAluno;
import CustomListView.RowItemPersonal;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;
import android.os.Build;

public class BuscarUsuario extends Activity {
	
	//Atributos de interface
	private ListView listViewBusca;
	private ArrayList<Aluno> alunos = new ArrayList<Aluno>();
	private ArrayList<Personal> personais =  new ArrayList<Personal>();
	
	//banco de dados local
	private Banco b;
	private SharedPreferences pref;
		
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar_usuario);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		mapearComponentes();
		
	}
	
	public void mapearComponentes(){
		listViewBusca = (ListView)findViewById(R.id.lstUsuariosResultado);
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		b = new Banco(this,null,null,0);
		
		if(pref.getString("tipo", null).equals("personal")){
    		pesquisarAlunosLocal("");
    	}else if(pref.getString("tipo", null).equals("aluno")){
    		pesquisarPersonaisLocal("");
    	}
	}
	
	public void pesquisarPersonaisLocal(String filtro){
		alunos.clear();
		personais.clear();
		CustomAdapterPersonal adpt = new CustomAdapterPersonal(this, new ArrayList<RowItemPersonal>());
		listViewBusca.setAdapter(adpt);
		
		
		//pesquisa Personal no webService
		
		personais = new Personal().buscarPersonais(b,filtro);
					    
		atualizarInterfacePersonal(filtro);
		
		
	}
	
	public void pesquisarAlunosLocal(String filtro){
		alunos.clear();
		personais.clear();
		CustomAdapterPersonal adpt = new CustomAdapterPersonal(this, new ArrayList<RowItemPersonal>());
		listViewBusca.setAdapter(adpt);
		
		
		//pesquisa Personal no webService
		
		alunos = new Aluno().buscarAlunos(b,filtro);
					    
		atualizarInterfaceAluno(filtro);
	}
	
	public void pesquisarPersonal(final String filtro){
		
		alunos.clear();
		personais.clear();
		CustomAdapterPersonal adpt = new CustomAdapterPersonal(this, new ArrayList<RowItemPersonal>());
		listViewBusca.setAdapter(adpt);
		
		Log.i("pesquisaPersonal", "Eu entrei na pesquisa de personal");
		//pesquisa Personal no webService
		
		personais = new Personal().buscarPersonaisWeb(filtro);
					    
		Log.i("tá vazio pesquisar personal", ""+ personais.isEmpty());
		atualizarInterfacePersonal(filtro);
	}

	public void pesquisarAluno(final String filtro){
		
		alunos.clear();
		personais.clear();
		CustomAdapterAluno adpt = new CustomAdapterAluno(this, new ArrayList<RowItemAluno>());
		listViewBusca.setAdapter(adpt);
		
		Log.i("perquisarALuno", "eu entrei na pesquisa de aluno");
		//pesquisa alunos no webService
		
		alunos = new Aluno().buscarAlunosWeb(filtro);
		
		atualizarInterfaceAluno(filtro);
		
		
	}

	public void atualizarInterfacePersonal(String filtro){
		
		
		
		final ArrayList<RowItemPersonal> rowItemPersonais = new ArrayList<RowItemPersonal>();
		
		//personal = new Personal().buscarPersonais(b, filtro);
									
		for(Personal p : personais){
			Bitmap bmp = new Personal().buscarFotoPersonalWeb(p.getUsuario());
		
			RowItemPersonal item = new RowItemPersonal();
			item.setImagemPerfil(bmp);
			item.setNomePerfil(p.getNome());
			item.setUsuarioPersonal(p.getUsuario());
			
			rowItemPersonais.add(item);
			Log.i("p", p.toString());
			
		}
		personais = new Personal().buscarPersonaisWeb(filtro);
		
		CustomAdapterPersonal adapter = 
				new CustomAdapterPersonal(BuscarUsuario.this,rowItemPersonais);
		listViewBusca.setAdapter(adapter);
	
		listViewBusca.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
			
				//Log.i("usuario", (personais.get(arg2)).getUsuario());
				String parametro = rowItemPersonais.get(arg2).getUsuarioPersonal();
				
				Personal aux = new Personal().buscarPersonal(b, parametro);
				if(aux == null){
					if(personais.get(arg2).salvar(b,ImageUtils.bitmapToByteArray(rowItemPersonais.get(arg2).getImagemPerfil()))){
						Log.i("sucesso", "personal gravado no banco local com sucesso!");
					}else{
						Log.i("erro", "Erro ao gravar o personal no banco local");
					}
				}else{
					Log.i("personal na busc", aux.toString());
					byte[] fotoPersonal = ImageUtils.bitmapToByteArray(rowItemPersonais.get(arg2).getImagemPerfil());
					if(personais.get(arg2).atualizar(b,fotoPersonal)){
						Log.i("sucesso", "personal gravado no banco local com sucesso!");
					}else{
						Log.i("erro", "Erro ao gravar o personal no banco local");
					}
				}
				
		
				Intent intent = new Intent(BuscarUsuario.this,AdicionarPersonal.class);
				intent.putExtra("usuario", parametro);
		    	startActivity(intent);
		    	finish();
		    	
			}

		});
	}
	
	public void atualizarInterfaceAluno(String filtro){
		final ArrayList<RowItemAluno> rowItemAlunos= new ArrayList<RowItemAluno>();

		for(Aluno a : alunos){
			
			
			Bitmap bmp = new Aluno().buscarFotoAlunoWeb(a.getUsuario());
			
			RowItemAluno item = new RowItemAluno();
			item.setImagemPerfil(bmp);
			item.setNomePerfil(a.getNome());
			item.setUsuarioAluno(a.getUsuario());
			
			rowItemAlunos.add(item);
		}
		
		CustomAdapterAluno adapter = 
				new CustomAdapterAluno(BuscarUsuario.this,rowItemAlunos);
		listViewBusca.setAdapter(adapter);
		
		listViewBusca.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			
				
				if(alunos.get(arg2).getUsuarioPersonal() == "" || 
						alunos.get(arg2).getUsuarioPersonal() == null || 
						alunos.get(arg2).getUsuarioPersonal().equals(pref.getString("usuario", null))||
						alunos.get(arg2).getUsuarioPersonal().equals("")){
					
					alunos.get(arg2).setConfirmacaoPersonal(1);
					
					Aluno aux = new Aluno().buscarAlunoEspecifico(b, alunos.get(arg2).getUsuario());
					if(aux == null){
						if(alunos.get(arg2).salvar(b,ImageUtils.bitmapToByteArray(rowItemAlunos.get(arg2).getImagemPerfil()))){
							Log.i("sucesso", "Aluno gravado no banco local com sucesso!");
						}else{
							Log.i("erro", "Erro ao gravar o aluno no banco local");
						}
					}else{
						Log.i("aluno na busc", aux.toString());
						byte[] fotoAluno = ImageUtils.bitmapToByteArray(rowItemAlunos.get(arg2).getImagemPerfil());
						if(alunos.get(arg2).atualizar(b,fotoAluno)){
							Log.i("sucesso", "Aluno atualizado no banco local com sucesso!");
						}else{
							Log.i("erro", "Erro ao atualizar o aluno no banco local");
						}
					}
						
					
					
					Intent intent = new Intent(BuscarUsuario.this,AdicionarAluno.class);
					intent.putExtra("usuario", alunos.get(arg2).getUsuario());
			    	startActivity(intent);
			    	finish();
			    	
				}else{
					Toast.makeText(BuscarUsuario.this, "Desculpe. Este aluno já possui um Personal Trainer", Toast.LENGTH_SHORT).show();
				}
				
			}

			});	
	}
	
	//MENUS
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.buscar_usuario, menu);
		
		final SearchView searchView = new SearchView(this.getActionBar().getThemedContext());
	    searchView.setQueryHint("Search");
	    
	    menu.add(Menu.NONE,Menu.NONE,1,"@string")
        .setIcon(android.R.drawable.ic_menu_search)
        .setActionView(searchView)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

    searchView.setOnQueryTextListener(new OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String newText) {
           /* if (newText.length() > 0) {
            	if(pref.getString("tipo", null).equals("personal")){
            		pesquisarAluno(newText.toString());
            	}else if(pref.getString("tipo", null).equals("aluno")){
            		pesquisarPersonal(newText.toString());
            	}
     
            } else {
            	if(pref.getString("tipo", null).equals("personal")){
            		pesquisarAluno("");
            	}else if(pref.getString("tipo", null).equals("aluno")){
            		pesquisarPersonal("");
            	}
            	
           }*/
            return false;
        }

		@Override
		public boolean onQueryTextSubmit(String arg0) {
			if(pref.getString("tipo", null).equals("personal")){
        		pesquisarAluno(arg0);
        	}else if(pref.getString("tipo", null).equals("aluno")){
        		pesquisarPersonal(arg0);
        	}
        	
			return false;
		}
    });
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
