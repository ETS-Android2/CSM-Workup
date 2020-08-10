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

import br.com.Classes.Aluno;
import br.com.Classes.Personal;
import br.com.GUI.aulas.MarcarAula;
import br.com.GUI.avaliacoes.tutoriais.InformacoesGerais;
import br.com.GUI.perfil.AlterarDadosPessoais;
import br.com.GUI.perfil.BuscarUsuario;
import br.com.GUI.perfil.HomeAluno;
import br.com.GUI.perfil.Login;
import br.com.GUI.perfil.PerfilPersonal;
import br.com.GUI.perfil.SolicitacoesDeAmizade;
import br.com.WorkUp.R;
import br.com.WorkUp.R.id;
import br.com.WorkUp.R.layout;
import br.com.adapters.TabsPagerAdapterAvaliacoes;
import br.com.adapters.TabsPagerAdapterEditarExercicios;
import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.os.Build;

public class NovaAvaliacao extends FragmentActivity implements ActionBar.TabListener{

	private ViewPager viewPager;
	private TabsPagerAdapterAvaliacoes mAdapter;
	private ActionBar actionBar;
	
	// persistencia 
	private SharedPreferences avaliacoes;
	private Editor editor;
	
	
	
	// Tab titles
	private String[] tabs = { "Passo 1", "Passo 2", "Passo 3", "Passo 4","Concluir" };
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_nova_avaliacao);
    getWindow().setSoftInputMode(
    	      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    
    getActionBar().setDisplayHomeAsUpEnabled(true);
		
	    // Initilization
	    viewPager = (ViewPager) findViewById(R.id.pagerNovaAvaliacao);
	    actionBar = getActionBar();
	    mAdapter = new TabsPagerAdapterAvaliacoes(getSupportFragmentManager());
	
	    viewPager.setAdapter(mAdapter);
	    actionBar.setHomeButtonEnabled(false);
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);   
	    
	    //Comitta o aluno avaliado
	    String parametro = getIntent().getExtras().getString("alunoAvaliacao");
	    avaliacoes = getApplicationContext().getSharedPreferences("novaAvaliacao", 0);
		editor = avaliacoes.edit();
		
		//Limpa o shared pref
		editor.clear();
		editor.commit();
		
		editor.putString("alunoAvaliacao", parametro);		
	    editor.commit();
	    // ----------------------------------------------------
	    
	    // Adding Tabs
	    actionBar.addTab(actionBar.newTab()
	    		.setText(tabs[0])
        		//.setIcon(android.R.drawable.ic_menu_sort_alphabetically)
                .setTabListener(this));
	
	    actionBar.addTab(actionBar.newTab()
	    		.setText(tabs[1])
        		//.setIcon(android.R.drawable.ic_menu_add)
                .setTabListener(this));
	    
	    actionBar.addTab(actionBar.newTab()
	    		.setText(tabs[2])
        		//.setIcon(android.R.drawable.ic_menu_share)
                .setTabListener(this));
	    
	    actionBar.addTab(actionBar.newTab()
	    		.setText(tabs[3])
        		//.setIcon(android.R.drawable.ic_menu_share)
                .setTabListener(this));
	    
	    actionBar.addTab(actionBar.newTab()
	    		.setText(tabs[4])
        		//.setIcon(android.R.drawable.ic_menu_share)
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
	public void onDestroy(){
		super.onDestroy();
		editor.clear();
		editor.commit();
		
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
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nova_avaliacao_actions, menu);
		return true;
	}
	
	@Override
	public void onBackPressed(){
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Atenção");
        alertDialog.setMessage("Você tem certeza que deseja sair desta avaliação?\n Todos os dados que não foram salvos serão perdidos!");
        alertDialog.setIcon(R.drawable.critical);
        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog,int which) {
        		editor.clear();
        		editor.commit();
        		finish();
            }
        });
        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	dialog.cancel();
            }
        });
 
        alertDialog.show();
       
		
		
	}
	
	
	 public boolean onOptionsItemSelected(MenuItem item) {
	        // Take appropriate action for each action item click
	        switch (item.getItemId()) {
	        
	        case R.id.actInformacoesGerais:
	        	Intent i = new Intent(NovaAvaliacao.this,InformacoesGerais.class);
	        	startActivity(i);
	        	return true;
		    
	        case android.R.id.home:
	        	super.onDestroy();
	        return true;
	        } 
			return false;
	   }
	

	

}
