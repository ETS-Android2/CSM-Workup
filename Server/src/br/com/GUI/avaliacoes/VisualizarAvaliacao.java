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
			situacaoCoronaria.add("Objetivo do Treinamento: " + a.getSituacaoCoronaria().getObjetivoDoTreinamento());
			situacaoCoronaria.add("Pressão sistólica Máxima: " + a.getSituacaoCoronaria().getPressaoSistolicaMaxima());
			situacaoCoronaria.add("Pressão diastólica Máxima: " + a.getSituacaoCoronaria().getPressaoDiastolicaMaxima());
			situacaoCoronaria.add("Pressão sistólica de Repouso: " + a.getSituacaoCoronaria().getPressaoSistolicaDeRepouso());
			situacaoCoronaria.add("Pressão diastólica de Repouso: " + a.getSituacaoCoronaria().getPressaoSistolicaDeRepouso());
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
			gorduraCorporal.add("Peso: " + a.getGorduraCorporal().getPeso());
			gorduraCorporal.add("Altura: " + a.getGorduraCorporal().getAltura());
			gorduraCorporal.add("Dobra Abdominal: " + a.getGorduraCorporal().getDobraAbdominal());
			gorduraCorporal.add("Dobra Coxa: " + a.getGorduraCorporal().getDobraCoxa());
			gorduraCorporal.add("Dobra Peito: " + a.getGorduraCorporal().getDobraPeito());
			gorduraCorporal.add("Dobra Suprailiaca: " + a.getGorduraCorporal().getDobraSuprailiaca());
			gorduraCorporal.add("Dobra Subscapular: " + a.getGorduraCorporal().getDobraSubscapular());
			gorduraCorporal.add("Dobra Triceps: " + a.getGorduraCorporal().getDobraTriceps());
			gorduraCorporal.add("Dobra Linha Axilar Média: " + a.getGorduraCorporal().getPeso());
			gorduraCorporal.add("Dobra Panturrilha: " + a.getGorduraCorporal().getDobraPanturrilha());
			gorduraCorporal.add("Resultado avaliação: " + a.getGorduraCorporal().getResultadoAvaliacao());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		ArrayAdapter<String> adaptGorduraCorporal = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,gorduraCorporal);
		lstGorduraCorporal.setAdapter(adaptGorduraCorporal);
		
		
		
		
		ArrayList<String> perimetria = new ArrayList<String>();
		
		try{
			perimetria.add("Biceps Contraido Direito: " + a.getPerimetria().getBicepsContraidoDireito());
			perimetria.add("Coxa Distal Esquerda: " + a.getPerimetria().getCoxaDistalEsquerda());
			perimetria.add("Antebraço : " + a.getPerimetria().getAntebraco());
			perimetria.add("Biceps Contraido Esquerdo: " + a.getPerimetria().getBicepsContraidoEsquerdo());
			perimetria.add("Coxa Proximal Esquerda : " + a.getPerimetria().getCoxaProximalEsquerda());
			perimetria.add("Coxa Proximal Direita : " + a.getPerimetria().getCoxaProximalDireita());
			perimetria.add("Panturrilha Esquerda : " + a.getPerimetria().getPanturrilhaEsquerda());
			perimetria.add("Peito : " + a.getPerimetria().getPeito());
			perimetria.add("Quadril : " + a.getPerimetria().getQuadril());
			perimetria.add("Panturrilha Direita : " + a.getPerimetria().getPanturrilhaDireita());
			perimetria.add("Coxa Distal Direita : " + a.getPerimetria().getCoxaDistalDireita());
			perimetria.add("Coxa Medial Esquerda : " + a.getPerimetria().getCoxaMedialEsquerda());
			perimetria.add("Ombro : " + a.getPerimetria().getOmbro());
			perimetria.add("Biceps Relaxado Esquerdo : " + a.getPerimetria().getBicepsRelaxadoEsquerdo());
			perimetria.add("Abdomen : " + a.getPerimetria().getAbdomen());
			perimetria.add("Biceps Relaxado Direito : " + a.getPerimetria().getBicepsRelaxadoDireito());
		
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
