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

import java.util.ArrayList;

import br.com.Banco.Banco;
import br.com.Classes.Avaliacoes;
import br.com.Classes.CalcularGorduraCorporal;
import br.com.Utilitarios.ImageUtils;
import br.com.WorkUp.R;
import br.com.WorkUp.R.id;
import br.com.WorkUp.R.layout;
import br.com.WorkUp.R.menu;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Build;

public class VisualizarAvaliacao extends Activity {
	

	//Atributos de interface
	
	private ListView lstSituacaoCoronaria;
	private ListView lstQuestionarioQPAF;
	private ListView lstGorduraCorporal;
	private ListView lstPerimetria;
	
	private ImageView imgVisualizarBicepsContraido;
	private ImageView imgVisualizarBicepsRelaxado;
	private ImageView imgVisualizarPanturrilha;
	private ImageView imgVisualizarCoxaProximal;
	private ImageView imgVisualizarCoxaMedial;
	private ImageView imgVisualizarCoxaDistal;
	private ImageView imgVisualizarAntebracoOmbro;
	private ImageView imgVisualizarQuadrilAbdomenPeito;
	
	private Button btnOk;
	
	
	//Persistencias
	private SharedPreferences pref;
	private Banco b;
	
	

	//Create and resumes
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visualizar_avaliacao);
		getWindow().setSoftInputMode(
			        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
		getActionBar().setDisplayHomeAsUpEnabled(true);
		mapearComponentes();
		carregarAvaliacao();
	}
	
	public void mapearComponentes(){
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		b = new Banco(this,null,null,0);
		
		lstSituacaoCoronaria = (ListView) findViewById(R.id.lstSituacaoCoronaria);
		lstSituacaoCoronaria.setOnTouchListener(new OnTouchListener() {
		    @Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				  arg0.getParent().requestDisallowInterceptTouchEvent(true);
				    return false;
			}
		});
		
		
		lstQuestionarioQPAF = (ListView) findViewById(R.id.lstQuestionarioQPAF);
		
		lstQuestionarioQPAF.setOnTouchListener(new OnTouchListener() {
		    @Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				  arg0.getParent().requestDisallowInterceptTouchEvent(true);
				    return false;
			}
		});
		
		lstGorduraCorporal = (ListView) findViewById(R.id.lstGorduraCorporal);
		lstGorduraCorporal.setOnTouchListener(new OnTouchListener() {
		    @Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				  arg0.getParent().requestDisallowInterceptTouchEvent(true);
				    return false;
			}
		});
		
		lstPerimetria = (ListView) findViewById(R.id.lstPerimetria);
		lstPerimetria.setOnTouchListener(new OnTouchListener() {
		    @Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				  arg0.getParent().requestDisallowInterceptTouchEvent(true);
				    return false;
			}
		});
		
		
		imgVisualizarBicepsContraido = (ImageView) findViewById(R.id.imgVisualizarBicepsContraido);
		imgVisualizarBicepsRelaxado = (ImageView) findViewById(R.id.imgVisualizarBicepsRelaxado);
		imgVisualizarPanturrilha = (ImageView) findViewById(R.id.imgVisualizarPanturrilha);
		imgVisualizarCoxaProximal = (ImageView) findViewById(R.id.imgVisualizarCoxaProximal);
		imgVisualizarCoxaMedial = (ImageView) findViewById(R.id.imgVisualizarCoxaMedial);
		imgVisualizarCoxaDistal = (ImageView) findViewById(R.id.imgVisualizarCoxaDistal);
		imgVisualizarAntebracoOmbro = (ImageView) findViewById(R.id.imgVisualizarAntebracoOmbro);
		imgVisualizarQuadrilAbdomenPeito = (ImageView) findViewById(R.id.imgVisualizarQuadrilAbdomenPeito);
		
		btnOk = (Button) findViewById(R.id.btnOk);
		btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
	
	

	public void carregarAvaliacao(){
		int parametro = getIntent().getExtras().getInt("codAvaliacao");
		
		Avaliacoes a  = new Avaliacoes().buscarAvaliacoesCompletasPorId(b,parametro);
		
		Log.i("avaliacao mandando ", "id = " + parametro);
		ArrayList<String> situacaoCoronaria = new ArrayList<String>();
		try{
			situacaoCoronaria.add(getString(R.string.label_objetivo_do_treinamento) + a.getSituacaoCoronaria().getObjetivoDoTreinamento());
			situacaoCoronaria.add(getString(R.string.label_pressao_sistolica_maxima) + a.getSituacaoCoronaria().getPressaoSistolicaMaxima());
			situacaoCoronaria.add(getString(R.string.label_pressao_diastolica_maxima) + a.getSituacaoCoronaria().getPressaoDiastolicaMaxima());
			situacaoCoronaria.add(getString(R.string.label_pressao_sistolica_de_repouso) + a.getSituacaoCoronaria().getPressaoSistolicaDeRepouso());
			situacaoCoronaria.add(getString(R.string.label_diastolica_de_repouso) + a.getSituacaoCoronaria().getPressaoSistolicaDeRepouso());
		}catch(Exception e){
			e.printStackTrace();
		}
		ArrayAdapter<String> adaptSituacaoCoronaria = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,situacaoCoronaria);
		lstSituacaoCoronaria.setAdapter(adaptSituacaoCoronaria);
		
		
		
		ArrayList<String> questionarioQPAF = new ArrayList<String>();

		
		
		
		
				
		try{
			questionarioQPAF.add(getResources().getString(R.string.label_questao_um) + " \n\n" + a.getQuestionarioQPAF().getQuestao1());
			questionarioQPAF.add(getResources().getString(R.string.label_questao_dois) + " \n\n" + a.getQuestionarioQPAF().getQuestao2());
			questionarioQPAF.add(getResources().getString(R.string.label_questao_tres) + " \n\n" + a.getQuestionarioQPAF().getQuestao3());
			questionarioQPAF.add(getResources().getString(R.string.label_questao_quatro) + " \n\n" + a.getQuestionarioQPAF().getQuestao4());
			questionarioQPAF.add(getResources().getString(R.string.label_questao_cinco) + " \n\n" + a.getQuestionarioQPAF().getQuestao5());
			questionarioQPAF.add(getResources().getString(R.string.label_questao_seis) + " \n\n" + a.getQuestionarioQPAF().getQuestao6());
			questionarioQPAF.add(getResources().getString(R.string.label_questao_sete) + " \n\n" + a.getQuestionarioQPAF().getQuestao7());
		
		}catch(Exception e ){
			e.printStackTrace();
		}
		ArrayAdapter<String> adaptQuestionarioQPAF = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,questionarioQPAF);
		lstQuestionarioQPAF.setAdapter(adaptQuestionarioQPAF);
		
		
		
		
		
		ArrayList<String> gorduraCorporal = new ArrayList<String>();
		
		try{
			gorduraCorporal.add(getString(R.string.label_peso)+ a.getGorduraCorporal().getPeso());
			gorduraCorporal.add(getString(R.string.label_altura) + a.getGorduraCorporal().getAltura());
			gorduraCorporal.add(getString(R.string.label_dobra_abdominal) + a.getGorduraCorporal().getDobraAbdominal());
			gorduraCorporal.add(getString(R.string.label_dobra_coxa) + a.getGorduraCorporal().getDobraCoxa());
			gorduraCorporal.add(getString(R.string.label_dobra_peito) + a.getGorduraCorporal().getDobraPeito());
			gorduraCorporal.add(getString(R.string.label_dobra_suprailiaca) + a.getGorduraCorporal().getDobraSuprailiaca());
			gorduraCorporal.add(getString(R.string.label_dobra_subescapular) + a.getGorduraCorporal().getDobraSubscapular());
			gorduraCorporal.add(getString(R.string.label_dobra_triceps) + a.getGorduraCorporal().getDobraTriceps());
			gorduraCorporal.add(getString(R.string.label_dobra_linha_axilar_media) + a.getGorduraCorporal().getPeso());
			gorduraCorporal.add(getString(R.string.label_dobra_panturrilha) + a.getGorduraCorporal().getDobraPanturrilha());
			gorduraCorporal.add(getString(R.string.label_resultado_avaliacao) + a.getGorduraCorporal().getResultadoAvaliacao());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		ArrayAdapter<String> adaptGorduraCorporal = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,gorduraCorporal);
		lstGorduraCorporal.setAdapter(adaptGorduraCorporal);
		
		
		
		
		ArrayList<String> perimetria = new ArrayList<String>();
		
		try{
			perimetria.add(getString(R.string.label_bicepsContraidoDireito) + a.getPerimetria().getBicepsContraidoDireito());
			perimetria.add(getString(R.string.label_coxaDistalEsquerda) + a.getPerimetria().getCoxaDistalEsquerda());
			perimetria.add(getString(R.string.label_antebraco) + a.getPerimetria().getAntebraco());
			perimetria.add(getString(R.string.label_bicepsContraidoEsquerdo) + a.getPerimetria().getBicepsContraidoEsquerdo());
			perimetria.add(getString(R.string.label_coxaProximalEsquerda) + a.getPerimetria().getCoxaProximalEsquerda());
			perimetria.add(getString(R.string.label_coxaProximalDireita) + a.getPerimetria().getCoxaProximalDireita());
			perimetria.add(getString(R.string.label_panturrilhaEsquerda) + a.getPerimetria().getPanturrilhaEsquerda());
			perimetria.add(getString(R.string.label_peito) + a.getPerimetria().getPeito());
			perimetria.add(getString(R.string.label_quadril) + a.getPerimetria().getQuadril());
			perimetria.add(getString(R.string.label_panturrilhaDireita) + a.getPerimetria().getPanturrilhaDireita());
			perimetria.add(getString(R.string.label_coxaDistalEsquerda) + a.getPerimetria().getCoxaDistalDireita());
			perimetria.add(getString(R.string.label_coxaMedialEsquerda) + a.getPerimetria().getCoxaMedialEsquerda());
			perimetria.add(getString(R.string.label_ombro) + a.getPerimetria().getOmbro());
			perimetria.add(getString(R.string.label_bicepsRelaxadoEsquerdo) + a.getPerimetria().getBicepsRelaxadoEsquerdo());
			perimetria.add(getString(R.string.label_abdomen) + a.getPerimetria().getAbdomen());
			perimetria.add(getString(R.string.label_bicepsRelaxadoDireito) + a.getPerimetria().getBicepsRelaxadoDireito());
		
		ArrayAdapter<String> adaptPerimetria = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,perimetria);
		lstPerimetria.setAdapter(adaptPerimetria);
		
		
		imgVisualizarBicepsContraido.setImageDrawable
				(ImageUtils.byteToDrawable(a.getFotosAvaliacao().getBicepsContraidoBitmap()));
		
		imgVisualizarBicepsRelaxado.setImageDrawable(
				ImageUtils.byteToDrawable(a.getFotosAvaliacao().getBicepsRelaxadoBitmap()));
		
		imgVisualizarPanturrilha.setImageDrawable(
				ImageUtils.byteToDrawable(a.getFotosAvaliacao().getPanturrilhaBitmap()));
		
		imgVisualizarCoxaDistal.setImageDrawable(
				ImageUtils.byteToDrawable(a.getFotosAvaliacao().getCoxaDistalBitmap()));
		
		imgVisualizarCoxaMedial.setImageDrawable(
				ImageUtils.byteToDrawable(a.getFotosAvaliacao().getCoxaMedialBitmap()));
		
		imgVisualizarCoxaProximal.setImageDrawable(
				ImageUtils.byteToDrawable(a.getFotosAvaliacao().getCoxaProximalBitmap()));
		
		imgVisualizarAntebracoOmbro.setImageDrawable(
				ImageUtils.byteToDrawable(a.getFotosAvaliacao().getAntebracoOmbroBitmap()));
				
		imgVisualizarQuadrilAbdomenPeito.setImageDrawable(
				ImageUtils.byteToDrawable(a.getFotosAvaliacao().getQuadrilAbdomenPeitoBitmap()));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	
	
	//MENUS
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.visualizar_avaliacao, menu);
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
