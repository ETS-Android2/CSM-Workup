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

import br.com.Banco.Banco;
import br.com.Classes.Aluno;
import br.com.Classes.Personal;
import br.com.Utilitarios.ImageUtils;
import br.com.WorkUp.R;
import br.com.WorkUp.R.id;
import br.com.WorkUp.R.layout;
import br.com.WorkUp.R.menu;
import CustomListView.CustomAdapterPersonal;
import CustomListView.CustomAdapterSolicitacoesPendentes;
import CustomListView.RowItemPersonal;
import CustomListView.RowItemSolicitacaoPendente;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class SolicitacoesDeAmizade extends Activity {
	
	private ListView lstSolicitacoesAmizade;
	
	private SharedPreferences pref;
	private Banco b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_solicitacoes_de_amizade);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		b = new Banco(this, null, null, 0);
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);

		//Log.i("tipo", pref.getString("tipo", null));
		lstSolicitacoesAmizade = (ListView) findViewById(R.id.lstSolicitacoesDeAmizade);
		
		atualizarSolicitacoesPendentes();
		
		
		
	}
	
	public void atualizarSolicitacoesPendentes(){
		
		final ArrayList<RowItemSolicitacaoPendente> rowItemSolicitacaoPendente = new ArrayList<RowItemSolicitacaoPendente>();
		
		
		try{
			if(pref.getString("tipo",null).equals("personal")){
				final ArrayList<Aluno> listaAlunos  = new Aluno().buscarAlunoNaoConfirmadoPorPersonalWeb("", pref.getString("usuario", null));
				
				Log.i("is array null","" +  listaAlunos.isEmpty());
				
				for(Aluno a: listaAlunos){
					Log.i("lista fos alunos", a.toString());
				}
				
				for(Aluno a : listaAlunos){
					
					Bitmap bmp = new Aluno().buscarFotoAlunoWeb(a.getUsuario());
				
					RowItemSolicitacaoPendente item = new RowItemSolicitacaoPendente();
					item.setIcone(bmp);
					item.setNomePerfil(a.getNome());
					item.setUsuarioAluno(a.getUsuario());
					
					rowItemSolicitacaoPendente.add(item);
				}
				
				CustomAdapterSolicitacoesPendentes adapter = 
						new CustomAdapterSolicitacoesPendentes(SolicitacoesDeAmizade.this,rowItemSolicitacaoPendente);
				lstSolicitacoesAmizade.setAdapter(adapter);
			
				lstSolicitacoesAmizade.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
							long arg3) {
						
						//Log.i("usuario", (personais.get(arg2)).getUsuario());
						String usuario = listaAlunos.get(arg2).getUsuario();
					
						Intent intent = new Intent(SolicitacoesDeAmizade.this,AceitarRejeitarAmigo.class);
						intent.putExtra("usuario", usuario);
						intent.putExtra("tipo", "personal");
				    	startActivity(intent);
				    	finish();
				    	
					}

				});
			}else if(pref.getString("tipo",null).equals("aluno")){
				final ArrayList<Personal> listaPersonal = new ArrayList<Personal>(); 
				
						listaPersonal.add(new Aluno().buscarPersonalNaoConfirmadoPorAlunoWeb
								(pref.getString("usuario", null)));
				
				
				
				for(Personal a : listaPersonal){
					
					Bitmap bmp = new Personal().buscarFotoPersonalWeb(a.getUsuario());
				
					RowItemSolicitacaoPendente item = new RowItemSolicitacaoPendente();
					item.setIcone(bmp);
					item.setNomePerfil(a.getNome());
					item.setUsuarioAluno(a.getUsuario());
					
					rowItemSolicitacaoPendente.add(item);
				}
				
				CustomAdapterSolicitacoesPendentes adapter = 
						new CustomAdapterSolicitacoesPendentes(SolicitacoesDeAmizade.this,rowItemSolicitacaoPendente);
				lstSolicitacoesAmizade.setAdapter(adapter);
			
				lstSolicitacoesAmizade.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
							long arg3) {
						
						//Log.i("usuario", (personais.get(arg2)).getUsuario());
						String usuario = listaPersonal.get(arg2).getUsuario();
					
						Intent intent = new Intent(SolicitacoesDeAmizade.this,AceitarRejeitarAmigo.class);
						intent.putExtra("usuario", usuario);
						intent.putExtra("tipo", "aluno");
				    	startActivity(intent);
				    	finish();
				    	
					}

				});
			}
		}catch (Exception e){
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.solicitacoes_de_amizade, menu);
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
