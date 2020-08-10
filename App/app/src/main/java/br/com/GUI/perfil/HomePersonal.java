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

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import com.facebook.android.Facebook;

import br.com.Banco.Banco;
import br.com.Classes.Aluno;
import br.com.Classes.Treinamento;
import br.com.GUI.aulas.MarcarAula;
import br.com.GUI.avaliacoes.AvaliarGorduraCorporal;
import br.com.GUI.treinamentos.GerenciarEdicaoDeExercicios;
import br.com.GUI.treinamentos.MeusTreinamentos;
import br.com.Utilitarios.WebService;
import br.com.Utilitarios.WorkUpService;
import br.com.WorkUp.R;
import br.com.adapters.TabsPagerAdapterEditarExercicios;
import br.com.adapters.TabsPagerAdapterHomePersonal;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.TabActivity;
import android.app.ActionBar.Tab;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.TypedArray;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.TabSpec;


public class HomePersonal extends FragmentActivity implements ActionBar.TabListener{

	private ViewPager viewPager;
	private TabsPagerAdapterHomePersonal mAdapter;
	private ActionBar actionBar;
	
	//Persistencias locais
	private SharedPreferences pref;
	private Editor editor;
	private Banco b;
	

@Override
	protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home_personal);
    getWindow().setSoftInputMode(
        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
   
    
    
   
    pref = getApplicationContext().getSharedPreferences("MyPref", 0);
	editor = pref.edit();
	b = new Banco(this,null,null,0);
	
	    // Initilization
	Log.i("usuario", pref.getString( "usuario", null));
	Log.i("tipo", pref.getString("tipo", null));
	    viewPager = (ViewPager) findViewById(R.id.pagerHomePersonal);
	    viewPager.setOffscreenPageLimit(5);
	    actionBar = getActionBar();
	    mAdapter = new TabsPagerAdapterHomePersonal(getSupportFragmentManager());
	
	    viewPager.setAdapter(mAdapter);
	    actionBar.setHomeButtonEnabled(false);
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);       
	
	    // Adding Tabs
	    actionBar.addTab(actionBar.newTab()
        		.setIcon(R.drawable.icon_alunos_tab)
                .setTabListener(this));
	   
	    actionBar.addTab(actionBar.newTab()
        		.setIcon(R.drawable.icon_agenda_tab)
                .setTabListener(this));
	    
	    actionBar.addTab(actionBar.newTab()
        		.setIcon(R.drawable.icon_treinamentos_tab)
                .setTabListener(this));
	
	    actionBar.addTab(actionBar.newTab()
        		.setIcon(R.drawable.icon_perfil_tab)
                .setTabListener(this));
	    /**
	     * on swiping the viewpager make respective tab selected
	     * */
	    viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
	
	        @Override
	        public void onPageSelected(int position) {
	            // on changing the page
	            // make respected tab selected
	        	actionBar.setSelectedNavigationItem(position);
	        	
	           	

	        }
	        @Override
	        public void onPageScrolled(int arg0, float arg1, int arg2) {
	        }
	
	        @Override
	        public void onPageScrollStateChanged(int arg0) {
	        }
	    });
	    
	  //Starta O servi��o
	    Intent i= new Intent(this, WorkUpService.class);
	    startService(i); 
	  
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_personal, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	        // Take appropriate action for each action item click
	        switch (item.getItemId()) {
	        
	        case R.id.actSolicitacoesDeAmizadePersonal:
	        	ArrayList<Aluno> solicitacoes = new Aluno().buscarAlunoNaoConfirmadoPorPersonalWeb("", pref.getString("usuario", null));
	        	if(!solicitacoes.isEmpty()){
	        		Intent i = new Intent(this, SolicitacoesDeAmizade.class);
		        	startActivity(i);
	        	}else{
	        		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
					alertDialog.setTitle("Ops...");
					alertDialog.setMessage(R.string.label_voce_nao_possui_solicitacoes_de_amizade);
					alertDialog.setIcon(R.drawable.profile);
					alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				         public void onClick(DialogInterface dialog,int which) {
				        	 dialog.cancel();
				         }
					});
					// Showing Alert Message
				        alertDialog.show();
	        	}
	        	
	        	return true;
	        case R.id.actLogoutPersonal:
	        	 finish();
				 Intent login = new Intent(this, Login.class);
				 login.putExtra("logout", true);
				 editor.clear();
				 editor.commit();
			     startActivity(login);
			     return true;
	        case R.id.actAdicionarAlunos:
	        	Intent adicionarAlunos = new Intent(this, BuscarUsuario.class);
	        	startActivity(adicionarAlunos);
	            return true;
	        case R.id.actAlterarDadosPessoaisPersonal:
	        	if(pref.getBoolean("isFacebookUser", false)){
	        		AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomePersonal.this);
	    			alertDialog.setTitle("Erro");
	    			alertDialog.setMessage("Seu cadastro está vinculado ao Facebook, sendo assim, não é possivel alteração de informações pessoais");
	    			alertDialog.setIcon(R.drawable.profile);
	    			alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {	
	    				public void onClick(DialogInterface dialog,int which) {
	    		        	dialog.cancel();
	    		         }
	    			});
	    			// Showing Alert Message
	    		        alertDialog.show();
	        	}else{
	        		Intent alterarDados = new Intent(this, AlterarDadosPessoais.class);
		        	startActivity(alterarDados);
	        	}
	        	
	            return true;
	        case R.id.actPerfil:
	        	Intent perfilIntent = new Intent(this, PerfilPersonal.class);
	        	startActivity(perfilIntent);
	        	return true;
	       
			 case R.id.actNovaAvaliacao:
		     	Intent avaliacoesIntent = new Intent(this, AvaliarGorduraCorporal.class);
		     	startActivity(avaliacoesIntent);
		     	return true;
			 case R.id.actNovoTreinamento:
				 
				 AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
					alertDialog.setTitle("Ops...");
					alertDialog.setMessage("Digite um nome para o novo treinamento");
					alertDialog.setIcon(R.drawable.critical);
					
					// Set an EditText view to get user input 
					final EditText input = new EditText(this);
					alertDialog.setView(input);


					alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				         public void onClick(DialogInterface dialog,int which) {
				        	 
				        	 
				        	 Treinamento t = new Treinamento(0,input.getText().toString(),null,null,
										pref.getString("usuario", null),null);
								
								
								//Log.i("interface: treinamento", t.toString());
								try{
									int resultado = t.salvarTreinamentoWeb(b);
									if(resultado > 0){
										Log.i("interface: salvei web", "salvei web");
										t.setCodTreinamento(resultado);
										if(t.salvarTreinamento(b, pref.getString("usuario", null))){
											Log.i("interface: salvei local", "salvei local");
											Toast.makeText(HomePersonal.this,"Salvo com sucesso!", Toast.LENGTH_SHORT).show();
										}
									}
								}catch(Exception ex){
									ex.printStackTrace();
									Toast.makeText(HomePersonal.this,"Erro ao salvar!", Toast.LENGTH_SHORT).show();
								}
				         }
					});
					alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
				         public void onClick(DialogInterface dialog,int which) {
				        	 
				        	 dialog.dismiss();
				        	
				         }
					});
					// Showing Alert Message
				    alertDialog.show();
				 
				 	
			 
		        }
	        
			return false;
	   }
	
}