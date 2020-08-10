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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import br.com.Banco.Banco;
import br.com.Classes.Aluno;
import br.com.Classes.Avaliacoes;
import br.com.Classes.CalcularGorduraCorporal;
import br.com.Classes.GorduraCorporal;
import br.com.Classes.Personal;
import br.com.GUI.avaliacoes.tutoriais.MedidaAbdominal;
import br.com.GUI.avaliacoes.tutoriais.MedidaAxilarMedia;
import br.com.GUI.avaliacoes.tutoriais.MedidaCoxa;
import br.com.GUI.avaliacoes.tutoriais.MedidaPanturrilha;
import br.com.GUI.avaliacoes.tutoriais.MedidaPeitoral;
import br.com.GUI.avaliacoes.tutoriais.MedidaSubscapular;
import br.com.GUI.avaliacoes.tutoriais.MedidaSuprailiaca;
import br.com.GUI.avaliacoes.tutoriais.MedidaTricipital;
import br.com.Utilitarios.WebService;
import br.com.WorkUp.R;
import br.com.WorkUp.R.layout;
import br.com.WorkUp.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

public class AvaliarGorduraCorporal extends Fragment {
	
	//atributos de interface 
	
	private Spinner spnMetodoDeCalculo;
	
	private EditText edtDobraPeito;
	private EditText edtDobraAbdomen;
	private EditText edtDobraCoxa;
	private EditText edtDobraLinhaAxilarMedia;
	private EditText edtDobraSuprailiaca;
	private EditText edtDobraTriceps;
	private EditText edtDobraSubscapular;
	private EditText edtDobraPanturrilha;
	
	private SeekBar skDobraAbdomen;
	private SeekBar skDobraPeito;
	private SeekBar skDobraCoxa;
	private SeekBar skDobraLinhaAxilarMedia;
	private SeekBar skDobraSuprailiaca;
	private SeekBar skDobraTriceps;
	private SeekBar skDobraSubscapular;
	private SeekBar skDobraPanturrilha;
	
	private ImageView imgTutorialTomarDobraAbdominal;
	private ImageView imgTutorialTomarDobraCoxa;
	private ImageView imgTutorialTomarDobraSubscapular;
	private ImageView imgTutorialTomarDobraPeito;
	private ImageView imgTutorialTomarDobraLinhaAxilarMedia;
	private ImageView imgTutorialTomarDobraTriceps;
	private ImageView imgTutorialTomarDobraSuprailiaca;
	private ImageView imgTutorialTomarDobraPanturrilha;
	
	private RelativeLayout tomadaDobraAbdominal;
	private RelativeLayout tomadaDobraCoxa;
	private RelativeLayout tomadaDobraSubscapular;
	private RelativeLayout tomadaDobraPeito;
	private RelativeLayout tomadaDobraLinhaAxilarMedia;
	private RelativeLayout tomadaDobraTriceps;
	private RelativeLayout tomadaDobraSuprailiaca;
	private RelativeLayout tomadaDobraPanturrilha;
	
	
	private NumberPicker nmbPeso;
	private NumberPicker nmbAltura;
	private NumberPicker nmbIdade;
	
	// atributos auxiliares 
	
	private ArrayList<EditText> views = new ArrayList<EditText>();
	
	//base de dados local 
	
	private SharedPreferences pref;
	private Banco b;
	
	
	private SharedPreferences avaliacoes;
	private Editor editor;
	
	

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	setHasOptionsMenu(true);
	
 
        View rootView = inflater.inflate(R.layout.activity_avaliar_gordura_corporal, container, false);
        
        return rootView;
	}	
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		mapearComponentes();
		
		carregarInformacoes();
	}
	
	
	public void carregarInformacoes(){
		//Metodos de calculo
		Log.i("usuario", "aluno avaliacao " + avaliacoes.getString("alunoAvaliacao",null));
		
		
		
		final Aluno avaliado = new Aluno().buscarAlunoEspecifico(b,avaliacoes.getString("alunoAvaliacao",null));
			
		Log.i("tostring", avaliado.toString());
		
			ArrayAdapter<String> adpMetodos = 
					new ArrayAdapter<String>(getActivity(),
							android.R.layout.simple_list_item_1,
							CalcularGorduraCorporal.metodosDeCalculo());
			spnMetodoDeCalculo.setAdapter(adpMetodos);
			
			spnMetodoDeCalculo.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					editor.putString("metodoDeCalculo", spnMetodoDeCalculo.getItemAtPosition(arg2).toString());
					editor.commit();
					
					
					
					
					if(spnMetodoDeCalculo.getItemAtPosition(arg2).toString().equals(getString(R.string.label_jackson_4))){
						Toast.makeText(getActivity(), CalcularGorduraCorporal.getIndicacaoJackson4(), Toast.LENGTH_LONG).show();
						//esconde os campos
						if(avaliado.getSexo().equals(getString(R.string.label_masculino))){
							tomadaDobraAbdominal.setVisibility(View.VISIBLE);
							tomadaDobraCoxa.setVisibility(View.VISIBLE);
							tomadaDobraSubscapular.setVisibility(View.GONE);
							tomadaDobraPeito.setVisibility(View.GONE);
							tomadaDobraLinhaAxilarMedia.setVisibility(View.GONE);
							tomadaDobraTriceps.setVisibility(View.VISIBLE);
							tomadaDobraSuprailiaca.setVisibility(View.VISIBLE);
							tomadaDobraPanturrilha.setVisibility(View.GONE);
						}else if(avaliado.getSexo().equals(getString(R.string.label_feminino))){
							tomadaDobraAbdominal.setVisibility(View.GONE);
							tomadaDobraCoxa.setVisibility(View.VISIBLE);
							tomadaDobraSubscapular.setVisibility(View.VISIBLE);
							tomadaDobraPeito.setVisibility(View.GONE);
							tomadaDobraLinhaAxilarMedia.setVisibility(View.GONE);
							tomadaDobraTriceps.setVisibility(View.GONE);
							tomadaDobraSuprailiaca.setVisibility(View.VISIBLE);
							tomadaDobraPanturrilha.setVisibility(View.GONE);
						}
												
						
						
						
					}else if(spnMetodoDeCalculo.getItemAtPosition(arg2).toString().equals(getString(R.string.label_jackson_6))){
						Toast.makeText(getActivity(), CalcularGorduraCorporal.getIndicacaoJackson6(), Toast.LENGTH_LONG).show();
						//esconde os campos
							tomadaDobraAbdominal.setVisibility(View.VISIBLE);
							tomadaDobraCoxa.setVisibility(View.VISIBLE);
							tomadaDobraSubscapular.setVisibility(View.VISIBLE);
							tomadaDobraPeito.setVisibility(View.VISIBLE);
							tomadaDobraLinhaAxilarMedia.setVisibility(View.GONE);
							tomadaDobraTriceps.setVisibility(View.VISIBLE);
							tomadaDobraSuprailiaca.setVisibility(View.VISIBLE);
							tomadaDobraPanturrilha.setVisibility(View.GONE);
						
						
						
						
					}else if(spnMetodoDeCalculo.getItemAtPosition(arg2).toString().equals(getString(R.string.label_jackson_7_atletas))){
						Toast.makeText(getActivity(), CalcularGorduraCorporal.getIndicacaoJackson4Atletas(), Toast.LENGTH_LONG).show();
						//esconde os campos
						
						if(avaliado.getSexo().equals(getString(R.string.label_masculino))){
							tomadaDobraAbdominal.setVisibility(View.VISIBLE);
							tomadaDobraCoxa.setVisibility(View.VISIBLE);
							tomadaDobraSubscapular.setVisibility(View.VISIBLE);
							tomadaDobraPeito.setVisibility(View.VISIBLE);
							tomadaDobraLinhaAxilarMedia.setVisibility(View.VISIBLE);
							tomadaDobraTriceps.setVisibility(View.VISIBLE);
							tomadaDobraSuprailiaca.setVisibility(View.VISIBLE);
							tomadaDobraPanturrilha.setVisibility(View.GONE);
						}else if(avaliado.getSexo().equals(getString(R.string.label_feminino))){
							Toast.makeText(getActivity(),  R.string.mensagens_metodo_exclusivo_para_homens, Toast.LENGTH_LONG).show();
						}
						
						
					}else if(spnMetodoDeCalculo.getItemAtPosition(arg2).toString().equals(getString(R.string.label_jackson_4_atletas))){
						Toast.makeText(getActivity(), CalcularGorduraCorporal.getIndicacaoJackson4Atletas(), Toast.LENGTH_LONG).show();
						if(avaliado.getSexo().equals(getString(R.string.label_masculino))){
							Toast.makeText(getActivity(),  R.string.mensagens_metodo_exclusivo_para_mulheres, Toast.LENGTH_LONG).show();
						}else if(avaliado.getSexo().equals(getString(R.string.label_feminino))){
							tomadaDobraAbdominal.setVisibility(View.VISIBLE);
							tomadaDobraCoxa.setVisibility(View.VISIBLE);
							tomadaDobraSubscapular.setVisibility(View.GONE);
							tomadaDobraPeito.setVisibility(View.GONE);
							tomadaDobraLinhaAxilarMedia.setVisibility(View.GONE);
							tomadaDobraTriceps.setVisibility(View.VISIBLE);
							tomadaDobraSuprailiaca.setVisibility(View.VISIBLE);
							tomadaDobraPanturrilha.setVisibility(View.GONE);
						}
						
						
					}else if(spnMetodoDeCalculo.getItemAtPosition(arg2).toString().equals(getString(R.string.mensagens_slaughter))){
						Toast.makeText(getActivity(), CalcularGorduraCorporal.getIndicacaoSlaughter(), Toast.LENGTH_LONG).show();
					
							tomadaDobraAbdominal.setVisibility(View.GONE);
							tomadaDobraCoxa.setVisibility(View.GONE);
							tomadaDobraSubscapular.setVisibility(View.GONE);
							tomadaDobraPeito.setVisibility(View.GONE);
							tomadaDobraLinhaAxilarMedia.setVisibility(View.GONE);
							tomadaDobraTriceps.setVisibility(View.VISIBLE);
							tomadaDobraSuprailiaca.setVisibility(View.GONE);
							tomadaDobraPanturrilha.setVisibility(View.VISIBLE);
					
						
					}else if(spnMetodoDeCalculo.getItemAtPosition(arg2).toString().equals(getString(R.string.label_guedes))){
						Toast.makeText(getActivity(), CalcularGorduraCorporal.getIndicacaoGuedes3(), Toast.LENGTH_LONG).show();
						if(avaliado.getSexo().equals(getString(R.string.label_masculino))){
							tomadaDobraAbdominal.setVisibility(View.VISIBLE);
							tomadaDobraCoxa.setVisibility(View.GONE);
							tomadaDobraSubscapular.setVisibility(View.GONE);
							tomadaDobraPeito.setVisibility(View.GONE);
							tomadaDobraLinhaAxilarMedia.setVisibility(View.GONE);
							tomadaDobraTriceps.setVisibility(View.VISIBLE);
							tomadaDobraSuprailiaca.setVisibility(View.VISIBLE);
							tomadaDobraPanturrilha.setVisibility(View.GONE);
						}else if(avaliado.getSexo().equals(getString(R.string.label_feminino))){
							tomadaDobraAbdominal.setVisibility(View.GONE);
							tomadaDobraCoxa.setVisibility(View.VISIBLE);
							tomadaDobraSubscapular.setVisibility(View.VISIBLE);
							tomadaDobraPeito.setVisibility(View.GONE);
							tomadaDobraLinhaAxilarMedia.setVisibility(View.GONE);
							tomadaDobraTriceps.setVisibility(View.GONE);
							tomadaDobraSuprailiaca.setVisibility(View.VISIBLE);
							tomadaDobraPanturrilha.setVisibility(View.GONE);
						}
					}
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					tomadaDobraAbdominal.setVisibility(View.GONE);
					tomadaDobraCoxa.setVisibility(View.GONE);
					tomadaDobraSubscapular.setVisibility(View.GONE);
					tomadaDobraPeito.setVisibility(View.GONE);
					tomadaDobraLinhaAxilarMedia.setVisibility(View.GONE);
					tomadaDobraTriceps.setVisibility(View.GONE);
					tomadaDobraSuprailiaca.setVisibility(View.GONE);
					tomadaDobraPanturrilha.setVisibility(View.GONE);
					editor.putString("metodoDeCalculo", "Guedes");
					editor.commit();
					
				}
			});
			
	}
	
	public void mapearComponentes(){
		
		b = new Banco(getActivity(),null,null,0);
		
		tomadaDobraAbdominal = (RelativeLayout) getActivity().findViewById(R.id.lytTomadaDobraAbdominal);
		tomadaDobraCoxa= (RelativeLayout) getActivity().findViewById(R.id.lytTomadaDobraCoxa);
		tomadaDobraSubscapular= (RelativeLayout) getActivity().findViewById(R.id.lytTomadaDobraSubscapular);
		tomadaDobraPeito= (RelativeLayout) getActivity().findViewById(R.id.lytTomadaDobraPeito);
		tomadaDobraLinhaAxilarMedia= (RelativeLayout) getActivity().findViewById(R.id.lytTomadaDobraLinhaAxilarMedia);
		tomadaDobraTriceps= (RelativeLayout) getActivity().findViewById(R.id.lytTomadaDobraTriceps);
		tomadaDobraSuprailiaca= (RelativeLayout) getActivity().findViewById(R.id.lytTomadaDobraSuprailiaca);
		tomadaDobraPanturrilha = (RelativeLayout) getActivity().findViewById(R.id.lytPanturrilha);
		
		
		
		tomadaDobraAbdominal.setVisibility(View.GONE);
		tomadaDobraCoxa.setVisibility(View.GONE);
		tomadaDobraSubscapular.setVisibility(View.GONE);
		tomadaDobraPeito.setVisibility(View.GONE);
		tomadaDobraLinhaAxilarMedia.setVisibility(View.GONE);
		tomadaDobraTriceps.setVisibility(View.GONE);
		tomadaDobraSuprailiaca.setVisibility(View.GONE);
		tomadaDobraPanturrilha.setVisibility(View.GONE);
		
		imgTutorialTomarDobraAbdominal = (ImageView) getActivity().findViewById(R.id.imgTutorialTomarDobraAbdominal);
		imgTutorialTomarDobraAbdominal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getActivity(),MedidaAbdominal.class);
				startActivity(i);
			}
		});
		imgTutorialTomarDobraCoxa = (ImageView) getActivity().findViewById(R.id.imgTutorialTomarDobraCoxa);
		imgTutorialTomarDobraCoxa.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getActivity(),MedidaCoxa.class);
				startActivity(i);
			}
		});
		imgTutorialTomarDobraSubscapular =(ImageView) getActivity().findViewById(R.id.imgTutorialTomarDobraSubscapular);
		imgTutorialTomarDobraSubscapular.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getActivity(),MedidaSubscapular.class);
				startActivity(i);
			}
		});
		imgTutorialTomarDobraPeito = (ImageView) getActivity().findViewById(R.id.imgTutorialTomarDobraPeito);
		imgTutorialTomarDobraPeito.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getActivity(),MedidaPeitoral.class);
				startActivity(i);
			}
		});
		
		imgTutorialTomarDobraLinhaAxilarMedia = (ImageView) getActivity().findViewById(R.id.imgTutorialTomarDobraLinhaAxilarMedia);
		imgTutorialTomarDobraLinhaAxilarMedia.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getActivity(),MedidaAxilarMedia.class);
				startActivity(i);
			}
		});
		imgTutorialTomarDobraTriceps = (ImageView) getActivity().findViewById(R.id.imgTutorialTomarDobraTriceps);
		imgTutorialTomarDobraTriceps.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getActivity(),MedidaTricipital.class);
				startActivity(i);
			}
		});
		imgTutorialTomarDobraSuprailiaca = (ImageView) getActivity().findViewById(R.id.imgTutorialTomarDobraSuprailiaca);
		imgTutorialTomarDobraSuprailiaca.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getActivity(),MedidaSuprailiaca.class);
				startActivity(i);
			}
		});
		
		imgTutorialTomarDobraPanturrilha = (ImageView) getActivity().findViewById(R.id.imgTutorialTomarDobraPanturrilha);
		imgTutorialTomarDobraPanturrilha.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getActivity(),MedidaPanturrilha.class);
				startActivity(i);
			}
		});
		
		
		
		avaliacoes = getActivity().getApplicationContext().getSharedPreferences("novaAvaliacao", 0);
		editor = avaliacoes.edit();
		
		spnMetodoDeCalculo = (Spinner) getActivity().findViewById(R.id.spnMetodoDeCalculo);
		
		nmbAltura = (NumberPicker) getActivity().findViewById(R.id.nmbAltura);
		nmbPeso = (NumberPicker) getActivity().findViewById(R.id.nmbPeso);
		nmbIdade = (NumberPicker) getActivity().findViewById(R.id.nmbIdade);
		
		edtDobraAbdomen = (EditText)getActivity().findViewById(R.id.edtDobraAbdomen);
		edtDobraCoxa = (EditText)getActivity().findViewById(R.id.edtDobraCoxa);
		edtDobraSubscapular = (EditText)getActivity().findViewById(R.id.edtDobraSubscapular);
		edtDobraPeito = (EditText)getActivity().findViewById(R.id.edtDobraPeito);
		edtDobraLinhaAxilarMedia = (EditText)getActivity().findViewById(R.id.edtDobraLinhaAxilarMedia);
		edtDobraTriceps = (EditText)getActivity().findViewById(R.id.edtDobraTriceps);
		edtDobraSuprailiaca = (EditText)getActivity().findViewById(R.id.edtDobraSuprailiaca);
		edtDobraPanturrilha = (EditText)getActivity().findViewById(R.id.txtDobraPanturrilha);
		
		
		skDobraAbdomen = (SeekBar) getActivity().findViewById(R.id.skDobraAbdomen);
		skDobraCoxa = (SeekBar) getActivity().findViewById(R.id.skDobraCoxa);
		skDobraSubscapular = (SeekBar) getActivity().findViewById(R.id.skDobraSubscapular);
		skDobraPeito = (SeekBar) getActivity().findViewById(R.id.skDobraPeito);
		skDobraLinhaAxilarMedia = (SeekBar) getActivity().findViewById(R.id.skDobraLinhaAxilarMedia);
		skDobraTriceps = (SeekBar) getActivity().findViewById(R.id.skDobraTriceps);
		skDobraSuprailiaca = (SeekBar) getActivity().findViewById(R.id.skDobraSuprailiaca);
		skDobraPanturrilha = (SeekBar) getActivity().findViewById(R.id.skDobraPanturrilha);
		
		
		
		skDobraAbdomen.setMax(30);
		skDobraPeito.setMax(30);
		skDobraCoxa.setMax(30);
		skDobraLinhaAxilarMedia.setMax(30);
		skDobraSuprailiaca.setMax(30);
		skDobraTriceps.setMax(30);
		skDobraSubscapular.setMax(30);
		
		nmbAltura.setMinValue(0);
		nmbPeso.setMinValue(0);
		nmbAltura.setMaxValue(200);
		nmbPeso.setMaxValue(200);
		nmbIdade.setMinValue(0);
		nmbIdade.setMaxValue(150);
		
		nmbAltura.setWrapSelectorWheel(true);
		nmbPeso.setWrapSelectorWheel(true);
		nmbIdade.setWrapSelectorWheel(true);
		
		skDobraAbdomen.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				edtDobraAbdomen.setText(String.valueOf(arg1));
				
			}
		});
		
		nmbPeso.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				editor.putFloat("peso", newVal);
				editor.commit();
			}
		});
		
		nmbAltura.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				editor.putFloat("altura", newVal);
				editor.commit();
			}
		});
	
		nmbIdade.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				editor.putInt("idade", newVal);
				editor.commit();
			}
		});
		
		skDobraPeito.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				edtDobraPeito.setText(String.valueOf(arg1));
				
			}
		});
		
		
		skDobraPanturrilha.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				edtDobraPanturrilha.setText(String.valueOf(arg1));
				
			}
		});
		
		skDobraCoxa.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				edtDobraCoxa.setText(String.valueOf(arg1));
				
			}
		});
		
		skDobraLinhaAxilarMedia.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				edtDobraLinhaAxilarMedia.setText(String.valueOf(arg1));
				
			}
		});
		
		skDobraSuprailiaca.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				edtDobraSuprailiaca.setText(String.valueOf(arg1));
				
			}
		});
		
		skDobraTriceps.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				edtDobraTriceps.setText(String.valueOf(arg1));
				
			}
		});
		
		skDobraSubscapular.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				edtDobraSubscapular.setText(String.valueOf(arg1));
				
			}
		});
		
		
		//Adiciona Persistencia
		
		edtDobraPeito.addTextChangedListener(new TextWatcher() {
			
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
				try{
					editor.putFloat("dobraPeito", Float.parseFloat(s.toString()));
				}catch(Exception e){
					editor.putFloat("dobraPeito", 0);
				}finally{
				editor.commit();
				}
			}
		});
		
		edtDobraAbdomen.addTextChangedListener(new TextWatcher() {
			
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
				try{
					editor.putFloat("dobraAbdomen", Float.parseFloat(s.toString()));
				}catch(Exception e){
					editor.putFloat("dobraAbdomen", 0);
				}finally{
				editor.commit();
				}
				
			}
		});
		
		edtDobraCoxa.addTextChangedListener(new TextWatcher() {
			
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
				try{
					editor.putFloat("dobraCoxa", Float.parseFloat(s.toString()));
				}catch(Exception e){
					editor.putFloat("dobraCoxa", 0);
				}finally{
				editor.commit();
				}
				
			}
		});
		
		edtDobraLinhaAxilarMedia.addTextChangedListener(new TextWatcher() {
			
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
				try{
					editor.putFloat("dobraLinhaAxilarMedia", Float.parseFloat(s.toString()));
				}catch(Exception e){
					editor.putFloat("dobraLinhaAxilarMedia", 0);
				}finally{
				editor.commit();
				}
				
			}
		});
		
		edtDobraSuprailiaca.addTextChangedListener(new TextWatcher() {
			
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
				try{
					editor.putFloat("dobraSuprailiaca", Float.parseFloat(s.toString()));
				}catch(Exception e){
					editor.putFloat("dobraSuprailiaca", 0);
				}finally{
				editor.commit();
				}
				
			}
		});
		
		edtDobraTriceps.addTextChangedListener(new TextWatcher() {
			
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
				try{
					editor.putFloat("dobraTriceps", Float.parseFloat(s.toString()));
				}catch(Exception e){
					editor.putFloat("dobraTriceps", 0);
				}finally{
				editor.commit();
				}
				
			}
		});
		
		edtDobraSubscapular.addTextChangedListener(new TextWatcher() {
			
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
				try{
					editor.putFloat("dobraSubscapular", Float.parseFloat(s.toString()));
					
				}catch(Exception e){
					editor.putFloat("dobraSubscapular", 0);
				}finally{
					editor.commit();
				}
				
				
			}
		});
		
		
		
	}

}
