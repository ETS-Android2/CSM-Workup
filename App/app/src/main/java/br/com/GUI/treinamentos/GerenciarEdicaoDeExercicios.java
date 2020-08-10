package br.com.GUI.treinamentos;

import br.com.WorkUp.R;
import br.com.adapters.TabsPagerAdapterEditarExercicios;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.WindowManager;


public class GerenciarEdicaoDeExercicios extends FragmentActivity implements ActionBar.TabListener{

	private ViewPager viewPager;
	private TabsPagerAdapterEditarExercicios mAdapter;
	private ActionBar actionBar;
	
	
	// Tab titles
	private String[] tabs = { "Meus Exercicios", "Novo Exercicio", };
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_gerenciar_edicao_de_exercicios);
    getWindow().setSoftInputMode(
    	      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
    
    	
    	
	    // Initilization
	    viewPager = (ViewPager) findViewById(R.id.pagerGerenciarTreinamentos);
	    actionBar = getActionBar();
	    mAdapter = new TabsPagerAdapterEditarExercicios(getSupportFragmentManager());
	
	    viewPager.setAdapter(mAdapter);
	    actionBar.setHomeButtonEnabled(false);
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);       
	
	    // Adding Tabs
	    actionBar.addTab(actionBar.newTab()
        		.setIcon(android.R.drawable.ic_menu_sort_alphabetically)
                .setTabListener(this));
	
	    actionBar.addTab(actionBar.newTab()
        		.setIcon(android.R.drawable.ic_menu_add)
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
}

