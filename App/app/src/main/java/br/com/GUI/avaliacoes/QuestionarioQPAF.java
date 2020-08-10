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

import br.com.WorkUp.R;
import br.com.WorkUp.R.id;
import br.com.WorkUp.R.layout;
import br.com.WorkUp.R.menu;
import android.app.Activity;
import android.app.ActionBar;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.os.Build;

public class QuestionarioQPAF extends Fragment {
	
	private RadioGroup rdgQuestao1;
	private RadioGroup rdgQuestao2;
	private RadioGroup rdgQuestao3;
	private RadioGroup rdgQuestao4;
	private RadioGroup rdgQuestao5;
	private RadioGroup rdgQuestao6;
	private RadioGroup rdgQuestao7;
	
	
	private SharedPreferences avaliacoes;
	private Editor editor;
	
	
	
	/*@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questionario_qpaf);

	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.questionario_qpa, menu);
		return true;
	}*/
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	setHasOptionsMenu(true);
	
 
        View rootView = inflater.inflate(R.layout.activity_questionario_qpaf, container, false);
        
        return rootView;
	}	
	
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		mapearComponentes();
		
	}
	
	public void mapearComponentes(){
		rdgQuestao1 = (RadioGroup) getActivity().findViewById(R.id.rdgQuestao1);
		rdgQuestao2 = (RadioGroup) getActivity().findViewById(R.id.rdgQuestao2);
		rdgQuestao3 = (RadioGroup) getActivity().findViewById(R.id.rdgQuestao3);
		rdgQuestao4 = (RadioGroup) getActivity().findViewById(R.id.rdgQuestao4);
		rdgQuestao5 = (RadioGroup) getActivity().findViewById(R.id.rdgQuestao5);
		rdgQuestao6 = (RadioGroup) getActivity().findViewById(R.id.rdgQuestao6);
		rdgQuestao7 = (RadioGroup) getActivity().findViewById(R.id.rdgQuestao7);
		
		avaliacoes = getActivity().getApplicationContext().getSharedPreferences("novaAvaliacao", 0);
		editor = avaliacoes.edit();
		
		
		adicionarPersistencia();
	}
	
	public void adicionarPersistencia(){
		rdgQuestao1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				switch(arg1){
				case R.id.rdQuestao1Sim:
					editor.putString("questao1", getString(R.string.label_sim));
					editor.commit();
					break;
				case R.id.rdQuestao1Nao:
					editor.putString("questao1", getString(R.string.label_nao));
					editor.commit();
					break;
				}
			}
		});
		
		rdgQuestao2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId){
				case R.id.rdQuestao2Sim:
					editor.putString("questao2", getString(R.string.label_sim));
					editor.commit();
					break;
				case R.id.rdQuestao2Nao:
					editor.putString("questao2",getString(R.string.label_nao));
					editor.commit();
					break;
				}
			}
		});
		
		rdgQuestao3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId){
				case R.id.rdQuestao3Sim:
					editor.putString("questao3", getString(R.string.label_sim));
					editor.commit();
					break;
				case R.id.rdQuestao3Nao:
					editor.putString("questao3",getString(R.string.label_nao));
					editor.commit();
					break;
				}
			}
		});
	
		rdgQuestao4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId){
				case R.id.rdQuestao4Sim:
					editor.putString("questao4", getString(R.string.label_sim));
					editor.commit();
					break;
				case R.id.rdQuestao4Nao:
					editor.putString("questao4", getString(R.string.label_nao));
					editor.commit();
					break;
				}
			}
		});
	
		rdgQuestao5.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId){
				case R.id.rdQuestao5Sim:
					editor.putString("questao5", getString(R.string.label_sim));
					editor.commit();
					break;
				case R.id.rdQuestao5Nao:
					editor.putString("questao5", getString(R.string.label_nao));
					editor.commit();
					break;
				}
			}
		});

		rdgQuestao6.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId){
				case R.id.rdQuestao6Sim:
					editor.putString("questao6", getString(R.string.label_sim));
					editor.commit();
					break;
				case R.id.rdQuestao6Nao:
					editor.putString("questao6", getString(R.string.label_nao));
					editor.commit();
					break;
				}
				
			}
		});
	
		rdgQuestao7.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId){
				case R.id.rdQuestao7Sim:
					editor.putString("questao7", getString(R.string.label_sim));
					editor.commit();
					break;
				case R.id.rdQuestao7Nao:
					editor.putString("questao7", getString(R.string.label_nao));
					editor.commit();
					break;
				}
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	

}
