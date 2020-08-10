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

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import br.com.Banco.Banco;
import br.com.Classes.Aluno;
import br.com.Classes.Avaliacoes;
import br.com.Classes.CalcularGorduraCorporal;
import br.com.Classes.Personal;
import br.com.Utilitarios.DateTimeUtilitario;
import br.com.Utilitarios.ImageUtils;
import br.com.WorkUp.R;
import br.com.WorkUp.R.id;
import br.com.WorkUp.R.layout;
import android.app.Activity;
import android.app.ActionBar;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.os.Build;

public class ConcluirAvaliacao extends Fragment {
	
	//atributos de interface
	private Button salvarAvaliacao;
	private ProgressBar pgrssSalvarAvaliacao;
	
	//persistencias
	private Banco b;
	private SharedPreferences pref;
	private SharedPreferences avaliacoes;
	
	//atributos da classe
	private Avaliacoes a = new Avaliacoes();
	boolean salvaComSucesso = false;
	
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	setHasOptionsMenu(true);
	
 
        View rootView = inflater.inflate(R.layout.activity_concluir_avaliacao, container, false);
        
        return rootView;
	}	
	
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		mapearComponentes();
		
	}
	
	
	public void mapearComponentes(){
		salvarAvaliacao = (Button) getActivity().findViewById(R.id.btnSalvarAvaliacao);
		salvarAvaliacao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				salvarAvaliacao();
			}
		});
		pgrssSalvarAvaliacao = (ProgressBar) getActivity().findViewById(R.id.pgrssSalvarAvaliacao);
		pgrssSalvarAvaliacao.setMax(100);
		
		b = new Banco(getActivity(),null,null,0);
		pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
		
		avaliacoes = getActivity().getApplicationContext().getSharedPreferences("novaAvaliacao", 0);
		
	}

	
	public void salvarAvaliacao(){
		Personal avaliador = new Personal().buscarPersonal(b, pref.getString("usuario", null));
		Aluno avaliado = new Aluno().buscarAlunoEspecifico(b, avaliacoes.getString("alunoAvaliacao", null));
		
		
		// calcula gordura corporal
		
		//medidas
		
		double peso = (double) avaliacoes.getFloat("peso", 0);
		double altura = (double) avaliacoes.getFloat("altura",0);
		int idade = avaliacoes.getInt("idade", 0);
		double dobraAbdominal = avaliacoes.getFloat("dobraAbdominal", 0);
		double dobraCoxa = (double) avaliacoes.getFloat("dobraCoxa", 0);
		double dobraPeito = (double) avaliacoes.getFloat("dobraPeito", 0);
		double dobraSuprailiaca = (double) avaliacoes.getFloat("dobraSuprailiaca", 0);
		double dobraSubscapular = (double) avaliacoes.getFloat("dobraSubscapular", 0);
		double dobraTriceps = (double) avaliacoes.getFloat("dobraTriceps", 0);
		double dobraLinhaAxilarMedia = (double) avaliacoes.getFloat("dobraLinhaAxilarMedia", 0);
		double dobraPanturrilha = (double) avaliacoes.getFloat("dobraPanturrilha", 0);
		
		CalcularGorduraCorporal CGC = new CalcularGorduraCorporal(
				avaliado.getSexo(),
				peso,
				altura,
				idade,
				dobraAbdominal,
				dobraCoxa,
				dobraPeito,
				dobraSuprailiaca,
				dobraSubscapular,
				dobraTriceps,
				dobraLinhaAxilarMedia,
				dobraPanturrilha,
				0);
		
		double gc = 0;
		
		String metodoEscolhido = avaliacoes.getString("metodoDeCalculo", null);
		
		if(metodoEscolhido.equals("Durnin")){
			gc = CGC.durnin();
		}else if(metodoEscolhido.equals("Jackson 4")){
			gc = CGC.jackson4();
		}else if(metodoEscolhido.equals("Jackson 6")){
			gc = CGC.jackson6();
		}else if(metodoEscolhido.equals("Jackson 7 Atletas")){
			gc = CGC.jacksonPollock7atletas();
		}else if(metodoEscolhido.equals("Jackson 4 Atletas")){
			gc = CGC.jackson4Atletas();
		}else if(metodoEscolhido.equals("Slaughter")){
			gc = CGC.slaughter();
		}else if(metodoEscolhido.equals("Guedes")){
			gc = CGC.guedes3();
		}
		
		
		Calendar cal = Calendar.getInstance();
    	String data = cal.get(Calendar.YEAR) +"/" 
    	+ cal.get(Calendar.MONTH) + "/" 
    	+ cal.get(Calendar.DAY_OF_MONTH);
    	
    	String hora = + cal.get(Calendar.HOUR_OF_DAY) + ":"
    	+ cal.get(Calendar.MINUTE) + ":"
    	+ cal.get(Calendar.SECOND);

	
		Log.i("avaliado", ""+ avaliacoes.getString("alunoAvaliacao", null));
		
		a.setDataAvaliacao(data);
		a.setHoraAvaliacao(hora);
		a.setUsuarioPersonal(pref.getString("usuario",null));
		a.setUsuarioAluno(avaliacoes.getString("alunoAvaliacao", null));
		a.setAtivo("ativado");
		
		a.getGorduraCorporal().setSexo(avaliado.getSexo());
		a.getGorduraCorporal().setPeso(avaliacoes.getFloat("peso", 0));
		a.getGorduraCorporal().setAltura(avaliacoes.getFloat("altura", 0));
		a.getGorduraCorporal().setIdade(avaliacoes.getInt("idade", 0));
		a.getGorduraCorporal().setDobraAbdominal(avaliacoes.getFloat("dobraAbdominal", 0));
		a.getGorduraCorporal().setDobraCoxa(avaliacoes.getFloat("dobraCoxa", 0));
		a.getGorduraCorporal().setDobraPeito(avaliacoes.getFloat("dobraPeito", 0));
		a.getGorduraCorporal().setDobraSuprailiaca(avaliacoes.getFloat("dobraSuprailiaca", 0));
		a.getGorduraCorporal().setDobraSubscapular(avaliacoes.getFloat("dobraSubscapular", 0));
		a.getGorduraCorporal().setDobraTriceps(avaliacoes.getFloat("dobraTriceps", 0));
		a.getGorduraCorporal().setDobraLinhaAxilarMedia(avaliacoes.getFloat("dobraLinhaAxilarMedia", 0));
		a.getGorduraCorporal().setDobraPanturrilha(avaliacoes.getFloat("dobraPanturrilha", 0));
		a.getGorduraCorporal().setResultadoAvaliacao(gc); 
		
		
		a.getPerimetria().setBicepsContraidoDireito(avaliacoes.getInt("bicepsContraidoDireito", 0));
		a.getPerimetria().setCoxaDistalEsquerda(avaliacoes.getInt("coxaDistalEsquerda", 0));
		a.getPerimetria().setAntebraco(avaliacoes.getInt("antebraco", 0));
		a.getPerimetria().setBicepsContraidoEsquerdo(avaliacoes.getInt("bicepsContraidoEsquerdo", 0));
		a.getPerimetria().setCintura(avaliacoes.getInt("cintura", 0));
		a.getPerimetria().setCoxaProximalEsquerda(avaliacoes.getInt("coxaProximalEsquerda", 0));
		a.getPerimetria().setCoxaProximalDireita(avaliacoes.getInt("coxaProximalDireita", 0));
		a.getPerimetria().setPanturrilhaEsquerda(avaliacoes.getInt("panturrilhaEsquerda", 0));
		a.getPerimetria().setPeito(avaliacoes.getInt("peito", 0));
		a.getPerimetria().setQuadril(avaliacoes.getInt("quadril", 0));
		a.getPerimetria().setPanturrilhaDireita(avaliacoes.getInt("panturrilhaDireita", 0));
		a.getPerimetria().setCoxaDistalDireita(avaliacoes.getInt("coxaDistalDireita", 0));
		a.getPerimetria().setCoxaMedialEsquerda(avaliacoes.getInt("coxaMedialEsquerda", 0));
		a.getPerimetria().setCoxaMedialDireita(avaliacoes.getInt("coxaMedialDireita", 0));
		a.getPerimetria().setOmbro(avaliacoes.getInt("ombro", 0));
		a.getPerimetria().setBicepsRelaxadoEsquerdo(avaliacoes.getInt("bicepsRelaxadoEsquerdo", 0));
		a.getPerimetria().setAbdomen(avaliacoes.getInt("abdomen", 0));
		a.getPerimetria().setBicepsRelaxadoDireito(avaliacoes.getInt("bicepsRelaxadoDireito", 0));
		
		
		a.getQuestionarioQPAF().setQuestao1(avaliacoes.getString("questao1", "Sem resposta"));
		a.getQuestionarioQPAF().setQuestao2(avaliacoes.getString("questao2", "Sem resposta"));
		a.getQuestionarioQPAF().setQuestao3(avaliacoes.getString("questao3", "Sem resposta"));
		a.getQuestionarioQPAF().setQuestao4(avaliacoes.getString("questao4", "Sem resposta"));
		a.getQuestionarioQPAF().setQuestao5(avaliacoes.getString("questao5", "Sem resposta"));
		a.getQuestionarioQPAF().setQuestao6(avaliacoes.getString("questao6", "Sem resposta"));
		a.getQuestionarioQPAF().setQuestao7(avaliacoes.getString("questao7", "Sem resposta"));
		
		
		a.getSituacaoCoronaria().setObjetivoDoTreinamento(avaliacoes.getString("objetivoDoTreinamento", "Sem resposta"));
		a.getSituacaoCoronaria().setPressaoSistolicaMaxima(String.valueOf(avaliacoes.getInt("pressaoSistolicaMaxima", 0)));
		a.getSituacaoCoronaria().setPressaoDiastolicaMaxima(String.valueOf(avaliacoes.getInt("pressaoDiastolicaMaxima", 0)));
		a.getSituacaoCoronaria().setPressaoSistolicaDeRepouso(String.valueOf(avaliacoes.getInt("pressaoSistolicaDeRepouso", 0)));
		a.getSituacaoCoronaria().setPressaoDiastolicaDeRepouso(String.valueOf(avaliacoes.getInt("pressaoDiastolicaDeRepouso", 0)));
		
		
		try{
			a.getFotosAvaliacao().setBicepsContraido(avaliacoes.getString("bicepsContraidoFoto",null));
			a.getFotosAvaliacao().setBicepsRelaxado(avaliacoes.getString("bicepsRelaxadoFoto",null));
			a.getFotosAvaliacao().setPanturrilha(avaliacoes.getString("panturrilhaFoto",null));
			a.getFotosAvaliacao().setCoxaProximal(avaliacoes.getString("coxaProximalFoto",null));
			a.getFotosAvaliacao().setCoxaMedial(avaliacoes.getString("coxaMedialFoto",null));
			a.getFotosAvaliacao().setCoxaDistal(avaliacoes.getString("coxaDistalFoto",null));
			a.getFotosAvaliacao().setAntebracoOmbro(avaliacoes.getString("antebracoOmbroFoto",null));
			a.getFotosAvaliacao().setQuadrilAbdomenPeito(avaliacoes.getString("quadrilAbdomenPeitoFoto",null));
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		int retornocod = a.salvarAvaliacoesWeb();
		
		pgrssSalvarAvaliacao.setProgress(50);
		
		a.setCodAvaliacao(retornocod);
		
		a.salvarAvaliacoes(b);
		
		pgrssSalvarAvaliacao.setProgress(100);
		
		getActivity().finish();
		
		
		
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	

}
