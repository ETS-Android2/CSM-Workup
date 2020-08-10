package br.com.GUI.treinamentos;

import java.util.ArrayList;

import br.com.Classes.Aluno;
import br.com.Classes.Treinamento;
import br.com.GUI.aulas.MarcarAula;
import br.com.GUI.avaliacoes.AvaliarGorduraCorporal;
import br.com.GUI.perfil.AlterarDadosPessoais;
import br.com.GUI.perfil.BuscarUsuario;
import br.com.GUI.perfil.HomePersonal;
import br.com.GUI.perfil.Login;
import br.com.GUI.perfil.PerfilPersonal;
import br.com.GUI.perfil.SolicitacoesDeAmizade;
import br.com.WorkUp.R;
import br.com.adapters.TabsPagerAdapterEdicaoDeTreinamentos;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;


public class GerenciarEdicaoDeTreinamentos extends FragmentActivity implements ActionBar.TabListener{

	private ViewPager viewPager;
	private TabsPagerAdapterEdicaoDeTreinamentos mAdapter;
	private ActionBar actionBar;
	
	//shared pref
		private SharedPreferences treinamento;
		private Editor editor;
		
	// Tab titles
	private String[] tabs = { "Aerobicos", "Anaerobicos"};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_gerenciar_edicao_de_treinamentos);
    getWindow().setSoftInputMode(
    	      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
    
    treinamento = getApplicationContext().getSharedPreferences("treinamento", 0);
	editor = treinamento.edit();
	int codTreinamento = getIntent().getExtras().getInt("codTreinamento");
	editor.putInt("codTreinamento", codTreinamento);
	editor.commit();
    
	    // Initilization
	    viewPager = (ViewPager) findViewById(R.id.pagerEdicaoDeTreinamento);
	    actionBar = getActionBar();
	    mAdapter = new TabsPagerAdapterEdicaoDeTreinamentos(getSupportFragmentManager());
	
	    viewPager.setAdapter(mAdapter);
	    actionBar.setHomeButtonEnabled(false);
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);       
	
	    // Adding Tabs
	    actionBar.addTab(actionBar.newTab()
	    		.setText(tabs[0])
                .setTabListener(this));
	
	    actionBar.addTab(actionBar.newTab()
	    		.setText(tabs[1])
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
		getMenuInflater().inflate(R.menu.gerenciar_treinamentos_actions, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        
        case R.id.actGerenciarExercicios:
        	Intent i = new Intent(this,GerenciarEdicaoDeExercicios.class);
        	startActivity(i);
        return true;
        }
        return false;
	}
}

