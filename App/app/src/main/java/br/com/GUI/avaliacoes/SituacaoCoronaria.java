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

import java.io.ObjectOutputStream.PutField;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class SituacaoCoronaria extends Fragment {
	
	private ImageView imgAumentarPressaoSistolicaMaxima;
	private ImageView imgDiminuirPressaoSistolicaMaxima;
	private ImageView imgAumentarPressaoDiastolicaMaxima;
	private ImageView imgDiminuirPressaoDiastolicaMaxima;
	private ImageView imgAumentarPressaoSistolicaRepouso;
	private ImageView imgDiminuirPressaoSistolicaDeRepouso;
	private ImageView imgAumentarPressaoDiastolicaDeRepouso;
	private ImageView imgDiminuirPressaoDiastolicaDeRepouso;
	
	private EditText txtPressaoSistolicaMaxima;
	private EditText txtPressaoDiastolicaMaxima;
	private EditText txtPressaoSistolicaDeRepouso;
	private EditText txtPressaoDiastolicaDeRepouso;
	
	private RadioGroup rdObjetivoDoTreinamento;
	private RadioButton rdEmagrecimento;
	private RadioButton	rdHipertrofiaMuscular;
	private RadioButton rdAumentoDaQualidadeDeVida;
	
	private SharedPreferences avaliacoes;
	private Editor editor;

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	setHasOptionsMenu(true);
	
 
        View rootView = inflater.inflate(R.layout.activity_situacao_coronaria, container, false);
        
        return rootView;
	}	
	
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		mapearComponentes();
		
	}
	
	public void mapearComponentes(){
		imgAumentarPressaoSistolicaMaxima = (ImageView) getActivity().findViewById(R.id.imgAumentarPressaoSistolicaMaxima);
		imgAumentarPressaoSistolicaMaxima.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				try{
					int pressao = Integer.parseInt(txtPressaoSistolicaMaxima.getText().toString());
					txtPressaoSistolicaMaxima.setText(String.valueOf(pressao + 1));
				}catch(Exception e){
					txtPressaoSistolicaMaxima.setText(String.valueOf(0));
				}
				
			}
		});
		
		imgDiminuirPressaoSistolicaMaxima = (ImageView) getActivity().findViewById(R.id.imgDiminuirPressaoSistolicaMaxima);
		imgDiminuirPressaoSistolicaMaxima.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				try{
					if(Integer.parseInt(txtPressaoSistolicaMaxima.getText().toString()) > 0 ){
						
						int pressao = Integer.parseInt(txtPressaoSistolicaMaxima.getText().toString());
						txtPressaoSistolicaMaxima.setText(String.valueOf(pressao - 1));
					}
				}catch(Exception e){
					txtPressaoSistolicaMaxima.setText(String.valueOf(0));
				}
				
			}
		});
		
		imgAumentarPressaoDiastolicaMaxima = (ImageView) getActivity().findViewById(R.id.imgAumentarPressaoDiastolicaMaxima);
		imgAumentarPressaoDiastolicaMaxima.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				try{
					int pressao = Integer.parseInt(txtPressaoDiastolicaMaxima.getText().toString());
					txtPressaoDiastolicaMaxima.setText(String.valueOf(pressao + 1));
				}catch(Exception e){
					txtPressaoDiastolicaMaxima.setText(String.valueOf(0));
				}
				
			}
		});
		imgDiminuirPressaoDiastolicaMaxima = (ImageView) getActivity().findViewById(R.id.imgDiminuirPressaoDiastolicaMaxima);
		imgDiminuirPressaoDiastolicaMaxima.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				try{
					if(Integer.parseInt(txtPressaoDiastolicaMaxima.getText().toString()) > 0){
						int pressao = Integer.parseInt(txtPressaoDiastolicaMaxima.getText().toString());
						txtPressaoDiastolicaMaxima.setText(String.valueOf(pressao - 1));
					}
				}catch(Exception e){
					txtPressaoDiastolicaMaxima.setText(String.valueOf(0));
				}
			}
		});
		imgAumentarPressaoSistolicaRepouso = (ImageView) getActivity().findViewById(R.id.imgAumentarPressaoSistolicaRepouso);
		imgAumentarPressaoSistolicaRepouso.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				try{
					int pressao = Integer.parseInt(txtPressaoSistolicaDeRepouso.getText().toString());
					txtPressaoSistolicaDeRepouso.setText(String.valueOf(pressao + 1));
				}catch(Exception e){
					txtPressaoSistolicaDeRepouso.setText(String.valueOf(0));
				}
				
			}
		});
		imgDiminuirPressaoSistolicaDeRepouso = (ImageView) getActivity().findViewById(R.id.imgDiminurPressaoSistolicaDeRepouso);
		imgDiminuirPressaoSistolicaDeRepouso.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				try{
					if(Integer.parseInt(txtPressaoSistolicaDeRepouso.getText().toString()) > 0 ){
						int pressao = Integer.parseInt(txtPressaoSistolicaDeRepouso.getText().toString());
						txtPressaoSistolicaDeRepouso.setText(String.valueOf(pressao - 1));
					}
				}catch(Exception e){
					txtPressaoSistolicaDeRepouso.setText(String.valueOf(0));
				}
				
				
			}
		});
		imgAumentarPressaoDiastolicaDeRepouso =(ImageView) getActivity().findViewById(R.id.imgAumentarPressaoDiastolicaDeRepouso);
		imgAumentarPressaoDiastolicaDeRepouso.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				try{
					int pressao = Integer.parseInt(txtPressaoDiastolicaDeRepouso.getText().toString());
					txtPressaoDiastolicaDeRepouso.setText(String.valueOf(pressao + 1));
				}catch(Exception e ){
					txtPressaoDiastolicaDeRepouso.setText(String.valueOf(0));
				}
				
			}
		});
		imgDiminuirPressaoDiastolicaDeRepouso = (ImageView) getActivity().findViewById(R.id.imgDiminuirPressaoDiastolicaDeRepouso);
		imgDiminuirPressaoDiastolicaDeRepouso.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				try{
					if(Integer.parseInt(txtPressaoDiastolicaDeRepouso.getText().toString()) > 0 ){
						int pressao = Integer.parseInt(txtPressaoDiastolicaDeRepouso.getText().toString());
						txtPressaoDiastolicaDeRepouso.setText(String.valueOf(pressao - 1));
					}
				}catch(Exception e){
					txtPressaoDiastolicaDeRepouso.setText(String.valueOf(0));
				}
				
				
			}
		});
		
		txtPressaoSistolicaMaxima = (EditText) getActivity().findViewById(R.id.txtPressaoSistolicaMaxima);
		txtPressaoDiastolicaMaxima =(EditText) getActivity().findViewById(R.id.txtPressaoDiastolicaMaxima);
		txtPressaoSistolicaDeRepouso =(EditText) getActivity().findViewById(R.id.txtPressaoSistolicaDeRepouso);
		txtPressaoDiastolicaDeRepouso =(EditText) getActivity().findViewById(R.id.txtPressaoDiastolicaDeRepouso);
		
		rdObjetivoDoTreinamento = (RadioGroup) getActivity().findViewById(R.id.rdgObjetivoTreinamento);
		rdEmagrecimento = (RadioButton) getActivity().findViewById(R.id.rdEmagrecimento);
		rdHipertrofiaMuscular = (RadioButton) getActivity().findViewById(R.id.rdHipertrofiaMuscular);
		rdAumentoDaQualidadeDeVida = (RadioButton) getActivity().findViewById(R.id.rdAumentoDaQualidadeDeVida);
		
		avaliacoes = getActivity().getApplicationContext().getSharedPreferences("novaAvaliacao", 0);
		editor = avaliacoes.edit();
		
		
		adicionarPersistencia();
	}
	
	public void adicionarPersistencia(){
		
		//Objetivo do Treinamento
		rdObjetivoDoTreinamento.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId){
				case R.id.rdEmagrecimento :
					editor.putString("objetivoDoTreinamento", getString(R.string.label_emagrecimento));
					break;
				case R.id.rdHipertrofiaMuscular:
					editor.putString("objetivoDoTreinamento", getString(R.string.label_hipertrofia_muscular));
					break;
				case R.id.rdAumentoDaQualidadeDeVida:
					editor.putString("objetivoDoTreinamento", getString(R.string.label_aumento_da_qualidade_de_vida));
					break;
				}
				editor.commit();
			}
		});
		
		
		txtPressaoSistolicaMaxima.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				int valor = 0;
				try{
					 valor = Integer.parseInt(s.toString());
				}catch(Exception e){
					
				}
				editor.putInt("pressaoSistolicaMaxima", valor);
				editor.commit();
			}
		});
		
		txtPressaoDiastolicaMaxima.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				int valor = 0;
				try{
					 valor = Integer.parseInt(s.toString());
				}catch(Exception e){
					
				}
				editor.putInt("pressaoDiastolicaMaxima", valor);
				editor.commit();
			}
		});
		
		txtPressaoSistolicaDeRepouso.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				int valor = 0;
				try{
					 valor = Integer.parseInt(s.toString());
				}catch(Exception e){
					
				}
				editor.putInt("pressaoSistolicaDeRepouso", valor);
				editor.commit();
			}
		});
		
		txtPressaoDiastolicaDeRepouso.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				int valor = 0;
				try{
					 valor = Integer.parseInt(s.toString());
				}catch(Exception e){
					
				}
				editor.putInt("pressaoDiastolicaDeRepouso", valor);
				editor.commit();
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
