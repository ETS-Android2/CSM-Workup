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
package br.com.GUI.perfil;



import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import com.facebook.android.Facebook;

import br.com.Banco.Banco;
import br.com.Classes.Aluno;
import br.com.Classes.Personal;
import br.com.Classes.Treinamento;
import br.com.GUI.aulas.MarcarAula;
import br.com.GUI.avaliacoes.AvaliarGorduraCorporal;
import br.com.Utilitarios.WebService;
import br.com.Utilitarios.WorkUpService;
import br.com.WorkUp.R;
import br.com.adapters.TabsPagerAdapterEditarExercicios;
import br.com.adapters.TabsPagerAdapterHomeAluno;
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
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.TabSpec;


public class HomeAluno extends FragmentActivity implements ActionBar.TabListener{

	private ViewPager viewPager;
	private TabsPagerAdapterHomeAluno mAdapter;
	private ActionBar actionBar;
	
	private SharedPreferences pref;
	private Editor editor;
	private Banco b;
	
	
	

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home_aluno);
    getWindow().setSoftInputMode(
        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    
    //Starta O servi��o
    Intent i= new Intent(this, WorkUpService.class);
    startService(i); 
    
    pref = getApplicationContext().getSharedPreferences("MyPref", 0);
	editor = pref.edit();
	b = new Banco(this,null,null,0);
	
	    // Initilization
	
		Log.i("usuario", pref.getString( "usuario", null));
		Log.i("tipo", pref.getString("tipo", null));
	    viewPager = (ViewPager) findViewById(R.id.pagerHomeAluno);
	    viewPager.setOffscreenPageLimit(5);
	    actionBar = getActionBar();
	    mAdapter = new TabsPagerAdapterHomeAluno(getSupportFragmentManager());
	
	    viewPager.setAdapter(mAdapter);
	    actionBar.setHomeButtonEnabled(false);
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);       
	
	    // Adding Tabs
	    actionBar.addTab(actionBar.newTab()
        		.setIcon(R.drawable.icon_personal_trainer_tab)
                .setTabListener(this));
	
	    actionBar.addTab(actionBar.newTab()
        		.setIcon(R.drawable.icon_acompanhamento_tab)
                .setTabListener(this));
	    
	    actionBar.addTab(actionBar.newTab()
        		.setIcon(R.drawable.icon_agenda_tab)
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
	}
	
		

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
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
		getMenuInflater().inflate(R.menu.home_aluno, menu);
		return true;
	}
	

	
	 public boolean onOptionsItemSelected(MenuItem item) {
	        // Take appropriate action for each action item click
	        switch (item.getItemId()) {
	        
	        case R.id.actSolicitacoesDeAmizadeAluno:
	        	Personal solicitacoes = new Aluno().buscarPersonalNaoConfirmadoPorAlunoWeb(pref.getString("usuario", null));
	        	Log.i("usuario", solicitacoes.toString());
	        	if(solicitacoes.getUsuario() != null){
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
	        case R.id.actLogoutAluno:
	        	 finish();
				 Intent login = new Intent(this, Login.class);
				 login.putExtra("logout", true);
				 editor.clear();
				 editor.commit();
			     startActivity(login);
			     return true;
	        case R.id.actAdicionarPersonal:
	        	Intent adicionarAlunos = new Intent(this, BuscarUsuario.class);
	        	startActivity(adicionarAlunos);
	            return true;
	        case R.id.actAlterarDadosPessoaisAluno:
	        	if(pref.getBoolean("isFacebookUser", false)){
	        		AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeAluno.this);
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
	        case R.id.actMarcarAula:
	        	Aluno a = new Aluno().buscarAlunoEspecifico(b, pref.getString("usuario", null));
	        	if( a.getUsuarioPersonal() != null){
	        		Intent marcarAulaIntent = new Intent(this, MarcarAula.class);
		        	startActivity(marcarAulaIntent);
	        	}else{
	        		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
					alertDialog.setTitle("Ops...");
					alertDialog.setMessage("Antes de você agendar uma aula é necessário que você "
							+ " possua um treinador para lhe auxiliar na sua rotina de exercícios");
					alertDialog.setIcon(R.drawable.horarios);
					alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				         public void onClick(DialogInterface dialog,int which) {
				        	 dialog.cancel();
				         }
					});
					// Showing Alert Message
				        alertDialog.show();
				      
	        	}
	        	
	        	
	        	return true;
			 case R.id.actNovaAvaliacao:
		     	Intent avaliacoesIntent = new Intent(this, AvaliarGorduraCorporal.class);
		     	startActivity(avaliacoesIntent);
		     	return true;
			 
			 
		        }
	        
			return false;
	   }
	
	
}
