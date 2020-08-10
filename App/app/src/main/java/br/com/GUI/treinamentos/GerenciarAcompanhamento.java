package br.com.GUI.treinamentos;

import br.com.Banco.Banco;
import br.com.WorkUp.R;
import br.com.WorkUp.R.id;
import br.com.WorkUp.R.layout;
import br.com.WorkUp.R.menu;
import br.com.adapters.TabsPagerAdapterAcompanhamento;
import br.com.adapters.TabsPagerAdapterEditarExercicios;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.os.Build;

public class GerenciarAcompanhamento extends FragmentActivity implements ActionBar.TabListener {

	private ViewPager viewPager;
	private TabsPagerAdapterAcompanhamento mAdapter;
	private ActionBar actionBar;
	
	//persistencias auxiliares
	private SharedPreferences avaliacoes;
	private Editor editor;
	
	private Banco b;
	
	
	// Tab titles
	private String[] tabs = { "Treinamentos", "Avaliações"};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_gerenciar_acompanhamento);
    getWindow().setSoftInputMode(
    	      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    
    getActionBar().setDisplayHomeAsUpEnabled(true);
	
	    //Usa o shared pref
	    avaliacoes = this.getApplicationContext().getSharedPreferences("acompanhamento", 0);
		editor = avaliacoes.edit();
		b = new Banco (this,null,null,0);
		
		String parametro = getIntent().getExtras().getString("alunoAcompanhamento");
		editor.putString("alunoSelecionado", parametro);
		editor.commit();
	
	    // Initilization
	    viewPager = (ViewPager) findViewById(R.id.pagerGerenciarAcompanhamento);
	    actionBar = getActionBar();
	    mAdapter = new TabsPagerAdapterAcompanhamento(getSupportFragmentManager());
	
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
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id ==  android.R.id.home){
			finishActivity(0);
			//NavUtils.navigateUpFromSameTask(this);
	        return true;
		}
	        
		return super.onOptionsItemSelected(item);
	}

}
