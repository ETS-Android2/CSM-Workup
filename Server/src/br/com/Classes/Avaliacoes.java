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
package br.com.Classes;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;
import br.com.Banco.Banco;
import br.com.Utilitarios.MarshalDouble;
import br.com.Utilitarios.WebService;

public class Avaliacoes extends CalcularGorduraCorporal implements KvmSerializable{
	
	private int codAvaliacao;
	private String dataAvaliacao;
	private String horaAvaliacao;
	private String usuarioPersonal;
	private String usuarioAluno;
	private String ativo;
	
	private GorduraCorporal gorduraCorporal;
	private Perimetria perimetria;
	private QuestionarioQPAF questionarioQPAF;
	private SituacaoCoronaria situacaoCoronaria;
	private FotosAvaliacao fotosAvaliacao;
	
	
	
	//atributos auxiliares
	private static boolean retorno;
	private static int retornoCod;
	private static Avaliacoes retornoAvaliacoes;
	private static ArrayList<Avaliacoes> retornoListaAvaliacoes = new ArrayList<Avaliacoes>();
	
	public static int statusDoEnvio;
	
	
	
	//toString

	@Override
	public String toString() {
		return "Avaliacoes [codAvaliacao=" + codAvaliacao + ", dataAvaliacao="
				+ dataAvaliacao + ", horaAvaliacao=" + horaAvaliacao
				+ ", usuarioPersonal=" + usuarioPersonal + ", usuarioAluno="
				+ usuarioAluno + ", ativo=" + ativo + ", gorduraCorporal="
				+ gorduraCorporal + ", perimetria=" + perimetria
				+ ", questionarioQPAF=" + questionarioQPAF
				+ ", situacaoCoronaria=" + situacaoCoronaria
				+ ", fotosAvaliacao=" + fotosAvaliacao + "]";
	}
	
	
	//Construtores
	
	public Avaliacoes(int codAvaliacao, String dataAvaliacao, String horaAvaliacao,
			String usuarioPersonal, String usuarioAluno, String ativo,
			GorduraCorporal gorduraCorporal, Perimetria perimetria,
			QuestionarioQPAF questionarioQPAF,
			SituacaoCoronaria situacaoCoronaria, FotosAvaliacao fotosAvaliacao) {
		super();
		this.codAvaliacao = codAvaliacao;
		this.dataAvaliacao = dataAvaliacao;
		this.horaAvaliacao = horaAvaliacao;
		this.usuarioPersonal = usuarioPersonal;
		this.usuarioAluno = usuarioAluno;
		this.ativo = ativo;
		this.gorduraCorporal = gorduraCorporal;
		this.perimetria = perimetria;
		this.questionarioQPAF = questionarioQPAF;
		this.situacaoCoronaria = situacaoCoronaria;
		this.fotosAvaliacao = fotosAvaliacao;
	}




	public Avaliacoes(){
		
		gorduraCorporal = new GorduraCorporal();
		perimetria = new Perimetria();
		questionarioQPAF = new QuestionarioQPAF();
		situacaoCoronaria = new SituacaoCoronaria();
		fotosAvaliacao = new FotosAvaliacao();
		
	}

	//CUD Local
	public boolean salvarAvaliacoes(Banco b){
		try{
			
			String SQL = "INSERT INTO avaliacoes("
					+ "codAvaliacao,"
					+ "dataAvaliacao,"
					+ "horaAvaliacao,"
					+ "usuarioPersonal,"
					+ "usuarioAluno,"
					+ "ativo) VALUES("
					+ codAvaliacao + ",'"
					+ dataAvaliacao + "','"
					+ horaAvaliacao + "','"
					+ usuarioPersonal + "','"
					+ usuarioAluno + "',"
					+ "'ativado');";
					
			
			b.execSQL(SQL);
			
			
			
			if(gorduraCorporal.salvarGorduraCorporal(b)){
				Log.i("sucesso", "gordura salva com sucesso");
			}else{
				Log.i("Erro: enviarAvaliacoesLocal", "Houve um erro ao enviar a gordura corporal avaliacao ");
			}
			
			if(perimetria.salvarPerimetria(b)){
				Log.i("sucesso", "perimetria salva com sucesso");
			}else{
				Log.i("Erro: enviarAvaliacoes", "Houve um erro ao enviar a perimetria avaliacao ");
			}
			
			if(questionarioQPAF.salvarQuestinarioQPAF(b)){
				Log.i("sucesso", "QPAF salva com sucesso");
			}else{
				Log.i("Erro: enviarAvaliacoes", "Houve um erro ao enviar  o questionario avaliacao ");
			}
			
			if(situacaoCoronaria.salvarSituacaoCoronaria(b)){
				Log.i("sucesso", "Situação coronaria salva com sucesso");
			}else{
				Log.i("Erro: enviarAvaliacoes LOcal", "Houve um erro ao enviar a situacao  coronaria avaliacao ");
			}
			
			if(fotosAvaliacao.salvarAvaliacoes(b)){
				Log.i("sucesso", "Fotos salva com sucesso");
			}else{
				Log.i("Erro: salvarAvaliacoes", "Houve um erro ao enviar as fotos da avaliacao ");
			}
			
			
			return true;
		}catch(Exception ex){
			return false;
		}
		
	}
	
	public boolean editar(Banco b){
		String SQL = "UPDATE Avaliacoes set "
				+ " dataAvaliacao = '" + dataAvaliacao
				+ "' horaAvaliacao = '" + horaAvaliacao
				+ "', usuarioPersonal = '" + usuarioPersonal
				+ "', usuarioAluno = '" + usuarioAluno 
				+ "' where codAvaliacao = " + codAvaliacao;
				
		try{
			b.execSQL(SQL);
			return true;
		}catch(Exception e){
			Log.i("Erro: ExcluirAvaliacoes",e.toString());
			return false;
		}
		
	}
	
	
	// BUSCAS Local METADADOS
	
	public ArrayList<Avaliacoes> buscarAvaliacoesPorAluno(Banco b,String filtro){
		
		
		 
		String SQL = "SELECT * FROM Avaliacoes where usuarioAluno = '"+ filtro + "' and ativo = 'ativado'";
		ArrayList<Avaliacoes> list = new ArrayList<Avaliacoes>();
		
		Cursor dataset = b.querySQL(SQL);
		
		
		
		int col_codAvaliacao = dataset.getColumnIndex("codAvaliacao");
		int col_horaAvaliacao= dataset.getColumnIndex("horaAvaliacao");
		int col_dataAvaliacao = dataset.getColumnIndex("dataAvaliacao");
		int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
		int col_usuarioAluno = dataset.getColumnIndex("usuarioAluno");
		int col_ativo = dataset.getColumnIndex("ativo");
		
		
		int numRows = dataset.getCount();
		
		//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
		dataset.moveToFirst();
		
		for(int c=0; c<numRows; c++) {
			//Log.i("Contagem do for", "For c = " + c);
			
			int codAvaliacao = dataset.getInt(col_codAvaliacao);
			String horaAvaliacao = dataset.getString(col_horaAvaliacao);
			String dataAvaliacao = dataset.getString(col_dataAvaliacao);
			String usuarioPersonal = dataset.getString(col_usuarioPersonal);
			String usuarioAluno = dataset.getString(col_usuarioAluno);
			String ativo = dataset.getString(col_ativo);
			
			
			Avaliacoes a = new Avaliacoes(codAvaliacao,dataAvaliacao,horaAvaliacao,
					usuarioPersonal,usuarioAluno,ativo,null,null,null,null,null);
			
			dataset.moveToNext();
			list.add(a);
		}
		return list;
	}
	
	public ArrayList<Avaliacoes> buscarAvaliacoesPorPersonal(Banco b,String filtro){
		
		
		String SQL = "SELECT * FROM Avaliacoes where usuarioPersonal = '" + filtro + "' and usuarioAluno <> 'null' and ativo = 'ativado'";
		ArrayList<Avaliacoes> list = new ArrayList<Avaliacoes>();
		
		Cursor dataset = b.querySQL(SQL);
		
		
		

		int col_codAvaliacao = dataset.getColumnIndex("codAvaliacao");
		int col_horaAvaliacoes = dataset.getColumnIndex("horaAvaliacoes");
		int col_dataAvaliacoes = dataset.getColumnIndex("dataAvaliacoes");
		int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
		int col_usuarioAluno = dataset.getColumnIndex("usuarioAluno");
		int col_ativo= dataset.getColumnIndex("ativo");
		
		
		
		int numRows = dataset.getCount();
		
	//	Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
		dataset.moveToFirst();
		
		for(int c=0; c<numRows; c++) {
		//	Log.i("Contagem do for", "For c = " + c);
			int codAvaliacao = dataset.getInt(col_codAvaliacao);
			String horaAvaliacao = dataset.getString(col_horaAvaliacoes);
			String dataAvaliacao = dataset.getString(col_dataAvaliacoes);
			String usuarioPersonal = dataset.getString(col_usuarioPersonal);
			String usuarioAluno = dataset.getString(col_usuarioAluno);
			String ativo = dataset.getString(col_ativo);
			
			
			Avaliacoes a = new Avaliacoes(codAvaliacao, dataAvaliacao,horaAvaliacao,
					usuarioPersonal,usuarioAluno,ativo,null,null,null,null,null);
			
			dataset.moveToNext();
			list.add(a);
		}
		return list;
	}
	
	public Avaliacoes buscarAvaliacoesPorId(Banco b,int codAvaliacaoBusca){
		
	
		String SQL = "SELECT * FROM Avaliacoes where codAvaliacao = " + codAvaliacaoBusca + " and ativo = 'ativado'";
		ArrayList<Avaliacoes> list = new ArrayList<Avaliacoes>();
		
		Cursor dataset = b.querySQL(SQL);
		
		

		int col_codAvaliacao = dataset.getColumnIndex("codAvaliacao");
		int col_horaAvaliacao = dataset.getColumnIndex("horaAvaliacao");
		int col_dataAvaliacao = dataset.getColumnIndex("dataAvaliacao");
		int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
		int col_usuarioAluno = dataset.getColumnIndex("usuarioAluno");
		int col_ativo = dataset.getColumnIndex("ativo");
		
		
		int numRows = dataset.getCount();
		
		//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
		dataset.moveToFirst();
		
		if(numRows > 0){
			int codAvaliacao = dataset.getInt(col_codAvaliacao);
			String horaAvaliacao = dataset.getString(col_horaAvaliacao);
			String dataAvaliacao = dataset.getString(col_dataAvaliacao);
			String usuarioPersonal = dataset.getString(col_usuarioPersonal);
			String usuarioAluno = dataset.getString(col_usuarioAluno);
			String ativo = dataset.getString(col_ativo);
			
			Avaliacoes a = new Avaliacoes(codAvaliacao, dataAvaliacao, horaAvaliacao,
					usuarioPersonal, usuarioAluno, ativo,null,null,null,null,null);
			
			return a;
			
		}
		
		return null;
	}


	// BUSCAS Local COMPLETAS
	
		public ArrayList<Avaliacoes> buscarAvaliacoesCompletasPorAluno(Banco b,String filtro){
			
			
			 
			String SQL = "SELECT * FROM Avaliacoes where usuarioAluno = '"+ filtro + "' and ativo = 'ativado'";
			ArrayList<Avaliacoes> list = new ArrayList<Avaliacoes>();
			
			Cursor dataset = b.querySQL(SQL);
			
			Avaliacoes x = new Avaliacoes();
			
			int col_codAvaliacao = dataset.getColumnIndex("codAvaliacao");
			int col_horaAvaliacao= dataset.getColumnIndex("horaAvaliacao");
			int col_dataAvaliacao = dataset.getColumnIndex("dataAvaliacao");
			int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
			int col_usuarioAluno = dataset.getColumnIndex("usuarioAluno");
			int col_ativo = dataset.getColumnIndex("ativo");
			
			
			int numRows = dataset.getCount();
			
			//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
			dataset.moveToFirst();
			
			for(int c=0; c<numRows; c++) {
				//Log.i("Contagem do for", "For c = " + c);
				
				int codAvaliacao = dataset.getInt(col_codAvaliacao);
				String horaAvaliacao = dataset.getString(col_horaAvaliacao);
				String dataAvaliacao = dataset.getString(col_dataAvaliacao);
				String usuarioPersonal = dataset.getString(col_usuarioPersonal);
				String usuarioAluno = dataset.getString(col_usuarioAluno);
				String ativo = dataset.getString(col_ativo);
				
				
				x = new Avaliacoes(codAvaliacao, dataAvaliacao, horaAvaliacao,
							usuarioPersonal, usuarioAluno, ativo,new GorduraCorporal(),
							new Perimetria(),new QuestionarioQPAF(),new SituacaoCoronaria(),
							new FotosAvaliacao());
				
				dataset.moveToNext();
				list.add(x);
			}
			
			for(Avaliacoes a :list){

				a.setGorduraCorporal(a.getGorduraCorporal().buscarGorduraCorporalPorId(b,a.getCodAvaliacao()));
				a.setPerimetria(a.getPerimetria().buscarPerimetriaPorId(b,a.getCodAvaliacao()));
				a.setQuestionarioQPAF(a.getQuestionarioQPAF().buscarQuestionarioQPAFPorId(b,a.getCodAvaliacao()));
				a.setSituacaoCoronaria(a.getSituacaoCoronaria().buscarSituacaoCoronariaPorId(b,a.getCodAvaliacao()));
				a.setFotosAvaliacao(a.getFotosAvaliacao().buscarFotosAvaliacaoPorId(b,a.getCodAvaliacao()));
			
			}
			return list;
		}
		
		public ArrayList<Avaliacoes> buscarAvaliacoesCompletasPorPersonal(Banco b,String filtro){
			
			
			String SQL = "SELECT * FROM Avaliacoes where usuarioPersonal = '" + filtro + "' and usuarioAluno <> 'null' and ativo = 'ativado'";
			ArrayList<Avaliacoes> list = new ArrayList<Avaliacoes>();
			
			Cursor dataset = b.querySQL(SQL);
			
			Avaliacoes x = new Avaliacoes();
			

			int col_codAvaliacao = dataset.getColumnIndex("codAvaliacao");
			int col_horaAvaliacoes = dataset.getColumnIndex("horaAvaliacoes");
			int col_dataAvaliacoes = dataset.getColumnIndex("dataAvaliacoes");
			int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
			int col_usuarioAluno = dataset.getColumnIndex("usuarioAluno");
			int col_ativo= dataset.getColumnIndex("ativo");
			
			
			
			int numRows = dataset.getCount();
			
		//	Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
			dataset.moveToFirst();
			
			for(int c=0; c<numRows; c++) {
			//	Log.i("Contagem do for", "For c = " + c);
				int codAvaliacao = dataset.getInt(col_codAvaliacao);
				String horaAvaliacao = dataset.getString(col_horaAvaliacoes);
				String dataAvaliacao = dataset.getString(col_dataAvaliacoes);
				String usuarioPersonal = dataset.getString(col_usuarioPersonal);
				String usuarioAluno = dataset.getString(col_usuarioAluno);
				String ativo = dataset.getString(col_ativo);
				
				
				x = new Avaliacoes(codAvaliacao, dataAvaliacao, horaAvaliacao,
							usuarioPersonal, usuarioAluno, ativo,new GorduraCorporal(),
							new Perimetria(),new QuestionarioQPAF(),new SituacaoCoronaria(),
							new FotosAvaliacao());
				dataset.moveToNext();
				list.add(x);
			}
			
			for(Avaliacoes a: list){

				a.setGorduraCorporal(a.getGorduraCorporal().buscarGorduraCorporalPorId(b,a.getCodAvaliacao()));
				a.setPerimetria(a.getPerimetria().buscarPerimetriaPorId(b,a.getCodAvaliacao()));
				a.setQuestionarioQPAF(a.getQuestionarioQPAF().buscarQuestionarioQPAFPorId(b,a.getCodAvaliacao()));
				a.setSituacaoCoronaria(a.getSituacaoCoronaria().buscarSituacaoCoronariaPorId(b,a.getCodAvaliacao()));
				a.setFotosAvaliacao(a.getFotosAvaliacao().buscarFotosAvaliacaoPorId(b,a.getCodAvaliacao()));
			
			}
			
			return list;
		}
		
		public Avaliacoes buscarAvaliacoesCompletasPorId(Banco b,int codAvaliacaoBusca){

			String SQL = "SELECT * FROM Avaliacoes where codAvaliacao = " + codAvaliacaoBusca + " and ativo = 'ativado'";
			
			Avaliacoes a = new Avaliacoes();
			
			Cursor dataset = b.querySQL(SQL);
			
			

			int col_codAvaliacao = dataset.getColumnIndex("codAvaliacao");
			int col_horaAvaliacao = dataset.getColumnIndex("horaAvaliacao");
			int col_dataAvaliacao = dataset.getColumnIndex("dataAvaliacao");
			int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
			int col_usuarioAluno = dataset.getColumnIndex("usuarioAluno");
			int col_ativo = dataset.getColumnIndex("ativo");
			
			
			int numRows = dataset.getCount();
			
			//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
			dataset.moveToFirst();
			
			if(numRows > 0){
				int codAvaliacao = dataset.getInt(col_codAvaliacao);
				String horaAvaliacao = dataset.getString(col_horaAvaliacao);
				String dataAvaliacao = dataset.getString(col_dataAvaliacao);
				String usuarioPersonal = dataset.getString(col_usuarioPersonal);
				String usuarioAluno = dataset.getString(col_usuarioAluno);
				String ativo = dataset.getString(col_ativo);
				
				a = new Avaliacoes(codAvaliacao, dataAvaliacao, horaAvaliacao,
						usuarioPersonal, usuarioAluno, ativo,new GorduraCorporal(),
						new Perimetria(),new QuestionarioQPAF(),new SituacaoCoronaria(),
						new FotosAvaliacao());
			
				
			}
			
			a.setGorduraCorporal(a.getGorduraCorporal().buscarGorduraCorporalPorId(b,a.getCodAvaliacao()));
			a.setPerimetria(a.getPerimetria().buscarPerimetriaPorId(b,a.getCodAvaliacao()));
			a.setQuestionarioQPAF(a.getQuestionarioQPAF().buscarQuestionarioQPAFPorId(b,a.getCodAvaliacao()));
			a.setSituacaoCoronaria(a.getSituacaoCoronaria().buscarSituacaoCoronariaPorId(b,a.getCodAvaliacao()));
			a.setFotosAvaliacao(a.getFotosAvaliacao().buscarFotosAvaliacaoPorId(b,a.getCodAvaliacao()));
			Log.i("idavaliacao", "id = " + a.getCodAvaliacao());
			Log.i("dentro de avaliacoes", a.toString());
			
			return a;
		}

	
	
	//CUD Web Service
	public int salvarAvaliacoesWeb(){
		
		clear();
		final Avaliacoes a = new Avaliacoes(getCodAvaliacao(),dataAvaliacao,
				horaAvaliacao,usuarioPersonal,usuarioAluno,ativo,null,null,null,null,null);
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"salvarAvaliacoes");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Avaliacoes");
				p1.setValue(a);
				p1.setType(new Avaliacoes().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "Avaliacoes",new Avaliacoes().getClass());
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("salvarAvaliacoes"), envelope);
					
					 retornoCod = Integer.parseInt(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.i("Erro: SalvarAvaliacoes", e.toString());
					e.printStackTrace();
				}		
			}
		};
		
		threadWs.start();
		try {
			threadWs.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gorduraCorporal.setCodAvaliacao(retornoCod);
		perimetria.setCodAvaliacao(retornoCod);
		questionarioQPAF.setCodAvaliacao(retornoCod);
		situacaoCoronaria.setCodAvaliacao(retornoCod);
		fotosAvaliacao.setCodAvaliacao(retornoCod);
		
		statusDoEnvio = 17;
		if(gorduraCorporal.salvarGorduraCorporalWeb()){
			statusDoEnvio += 17;
		}else{
			Log.i("Erro: enviarAvaliacoesWEb", "Houve um erro ao enviar a gordura corporal avaliacao web");
		}
		
		if(perimetria.salvarPerimetriaWeb()){
			statusDoEnvio += 17;
		}else{
			Log.i("Erro: enviarAvaliacoesWEb", "Houve um erro ao enviar a perimetria avaliacao web");
		}
		
		if(questionarioQPAF.salvarQuestinarioQPAFWeb()){
			statusDoEnvio += 17;
		}else{
			Log.i("Erro: enviarAvaliacoesWEb", "Houve um erro ao enviar  o questionario avaliacao web");
		}
		
		if(situacaoCoronaria.salvarSituacaoCoronariaWeb()){
			statusDoEnvio += 17;
		}else{
			Log.i("Erro: enviarAvaliacoesWEb", "Houve um erro ao enviar a situacao  coronaria avaliacao web");
		}
		
		if(fotosAvaliacao.salvarFotosAvaliacaoWeb()){
			statusDoEnvio = 100;
		}else{
			Log.i("Erro: enviarAvaliacoesWEb", "Houve um erro ao enviar as fotos da avaliacao web");
		}
		
		return retornoCod;
		
	}
	
	public boolean editarAvaliacoesWeb(){
		clear();
		final Avaliacoes a = new Avaliacoes(getCodAvaliacao(),getDataAvaliacao(),
				getHoraAvaliacao(),getUsuarioPersonal(),getUsuarioAluno(),getAtivo(),null,null,null,null,null);
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"editarAvaliacoes");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Avaliacoes");
				p1.setValue(a);
				p1.setType(new Avaliacoes().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "Avaliacoes",new Avaliacoes().getClass());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("editarAvaliacoes"), envelope);
					
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: EditarAvaliacoes", e.toString());
				}		
			}
		};
		
		threadWs.start();
		try {
			threadWs.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gorduraCorporal.setCodAvaliacao(a.getCodAvaliacao());
		perimetria.setCodAvaliacao(a.getCodAvaliacao());
		questionarioQPAF.setCodAvaliacao(a.getCodAvaliacao());
		situacaoCoronaria.setCodAvaliacao(a.getCodAvaliacao());
		fotosAvaliacao.setCodAvaliacao(a.getCodAvaliacao());
		
		statusDoEnvio = 17;
		if(gorduraCorporal.editarGorduraCorporalWeb()){
			statusDoEnvio += 17;
		}else{
			Log.i("Erro: enviarAvaliacoesWEb", "Houve um erro ao enviar a gordura corporal avaliacao web");
		}
		
		if(perimetria.editarPerimetriaWeb()){
			statusDoEnvio += 17;
		}else{
			Log.i("Erro: enviarAvaliacoesWEb", "Houve um erro ao enviar a perimetria avaliacao web");
		}
		
		if(questionarioQPAF.editarQuestionarioQPAFWeb()){
			statusDoEnvio += 17;
		}else{
			Log.i("Erro: enviarAvaliacoesWEb", "Houve um erro ao enviar  o questionario avaliacao web");
		}
		
		if(situacaoCoronaria.editarSituacaoCoronariaWeb()){
			statusDoEnvio += 17;
		}else{
			Log.i("Erro: enviarAvaliacoesWEb", "Houve um erro ao enviar a situacao  coronaria avaliacao web");
		}
		
		if(fotosAvaliacao.editarFotosAvaliacaoWeb()){
			statusDoEnvio = 100;
		}else{
			Log.i("Erro: enviarAvaliacoesWEb", "Houve um erro ao enviar as fotos da avaliacao web");
		}
		
		return retorno;
	}

	public boolean excluirAvaliacoesWeb(){
		clear();
		final Avaliacoes a = new Avaliacoes();
		a.setCodAvaliacao(getCodAvaliacao());
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"excluirAvaliacoes");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Avaliacoes");
				p1.setValue(a);
				p1.setType(new Avaliacoes().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "Avaliacoes",new Avaliacoes().getClass());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("excluirAvaliacoes"), envelope);
					
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: ExcluirAvaliacoes", e.toString());
				}		
			}
		};
		
		threadWs.start();
		try {
			threadWs.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;
	}
	
	
	//Buscas Web Service
	
	//buscas WEb METADADOS
	public Avaliacoes buscarAvaliacoesPorIdWeb(int codAvaliacao){
		clear();
		final Avaliacoes a = new Avaliacoes();
		a.setCodAvaliacao(codAvaliacao);
		
				
				Thread threadWs = new Thread(){
					
					@Override		
					public void run(){
						
						
						
						SoapObject request = new SoapObject(WebService.getNamespace(),"buscarAvaliacoesPorId");
						PropertyInfo p1 = new PropertyInfo();
						p1.setName("Avaliacoes");
						p1.setValue(a);
						p1.setType(new Avaliacoes().getClass());
						
						request.addProperty(p1);
						
			
						SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
						envelope.setOutputSoapObject(request);
						envelope.addMapping(WebService.getNamespace(), "Avaliacoes",new Avaliacoes().getClass());
						MarshalDouble marshalDouble = new MarshalDouble();
						marshalDouble.register(envelope);
						
						HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
						
						
						try{
							ht.call(WebService.getSoapAction("buscarAvaliacoesPorId"), envelope);
							
							SoapObject res = new SoapObject();
							
							try{
								res = (SoapObject) envelope.getResponse();
								//Log.i("debug", "cheguei aki --- > " + res.toString());
								
										
							}catch(Exception e){
								Log.i("Erro: buscarAvaliacoesPorId (UNICO) ", e.toString());
							}
					
							retornoAvaliacoes = getSoapAvaliacoes(res);
					       // Log.i("debug aluno", retornoAvaliacoes.toString());
						         
						}catch(Exception e){
							//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
							Log.e("Erro: buscarAvaliacoesPorId (UNICO) ", e.toString());
						}		
					}
				};
				
				threadWs.start();
				try {
					threadWs.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return retornoAvaliacoes;
	}
	
	public ArrayList<Avaliacoes> buscarAvaliacoesPorPersonalWeb(String usuarioPersonal,String filtro){
		clear();
		final Avaliacoes a= new Avaliacoes();
		a.setUsuarioPersonal(usuarioPersonal);
		a.setUsuarioAluno(filtro);
		
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"buscarAvaliacoesPorPersonal");
					PropertyInfo p1 = new PropertyInfo();
					p1.setName("Avaliacoes");
					p1.setValue(a);
					p1.setType(new Avaliacoes().getClass());
					
					request.addProperty(p1);
					
		
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					envelope.addMapping(WebService.getNamespace(), "Avaliacoes",new Personal().getClass());
					
					HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
					
					
					try{
						ht.call(WebService.getSoapAction("buscarAvaliacoesPorPersonal"), envelope);
						
						SoapObject res = new SoapObject();
						Vector<SoapObject> response = new Vector<SoapObject>();
						
						try{
							res = (SoapObject) envelope.getResponse();
							//Log.i("debug", "cheguei aki --- > " + res.toString());
							
									
						}catch(Exception e){
							Log.i("Erro: buscarAvaliacoesPorPersonalWeb (UNICO) ", e.toString());
						}
						
						try{
							
							response = (Vector<SoapObject>) envelope.getResponse();
							//Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
							
						}catch(Exception e){
							Log.i("Erro: buscarAvaliacoesPorPersonalWeb (VETOR) ", e.toString());
						}

						if(response.isEmpty()){
							
							 	Avaliacoes item = getSoapAvaliacoes(res);
						        Log.i("alunos", item.toString());
						        retornoListaAvaliacoes.add(item);
						         
						}else{
							
							for(SoapObject soapPersonal: response){  
						         Avaliacoes item = getSoapAvaliacoes(soapPersonal);
						         Log.i("alunos", item.toString());
						         retornoListaAvaliacoes.add(item);			       
					           }
							
						}
					         
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("Erro: buscar Avaliacoes Por personal", e.toString());
						e.printStackTrace();
					}		
				}
			};
			
			threadWs.start();
			try {
				threadWs.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return retornoListaAvaliacoes;
	}
	
	public ArrayList<Avaliacoes>buscarAvaliacoesPorAlunoWeb(String filtro){
		clear();
		final Avaliacoes a= new Avaliacoes();
		a.setUsuarioAluno(filtro);
		
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"buscarAvaliacoesPorAluno");
					PropertyInfo p1 = new PropertyInfo();
					p1.setName("Avaliacoes");
					p1.setValue(a);
					p1.setType(new Avaliacoes().getClass());
					
					request.addProperty(p1);
					
		
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					envelope.addMapping(WebService.getNamespace(), "Avaliacoes",new Personal().getClass());
					
					HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
					
					
					try{
						ht.call(WebService.getSoapAction("buscarAvaliacoesPorAluno"), envelope);
						
						SoapObject res = new SoapObject();
						Vector<SoapObject> response = new Vector<SoapObject>();
						
						try{
							res = (SoapObject) envelope.getResponse();
							//Log.i("debug", "cheguei aki --- > " + res.toString());
							
									
						}catch(Exception e){
							Log.i("Erro: buscarAvaliacoesPorAlunoWeb ( UNICO ) ", e.toString());
						}
						
						try{
							
							response = (Vector<SoapObject>) envelope.getResponse();
						//	Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
							
						}catch(Exception e){
							Log.i("Erro: buscarAvaliacoesPorAlunoWeb (VETOR) ", e.toString());
						}

						if(response.isEmpty()){
							
							 	Avaliacoes item = getSoapAvaliacoes(res);
						        Log.i("alunos", item.toString());
						        retornoListaAvaliacoes.add(item);
						         
						}else{
							
							for(SoapObject soapPersonal: response){  
						         Avaliacoes item = getSoapAvaliacoes(soapPersonal);
						         Log.i("alunos", item.toString());
						         retornoListaAvaliacoes.add(item);			       
					           }
							
						}
					         
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("Erro: buscarAvaliacoesPorAlunoWeb", e.toString());
					}		
				}
			};
			
			threadWs.start();
			try {
				threadWs.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return retornoListaAvaliacoes;
	}
	
	
	
	//buscas Web COMPLETAS
	public Avaliacoes buscarAvaliacoesCompletasPorIdWeb(int codAvaliacao){
		clear();
		final Avaliacoes a = new Avaliacoes();
		a.setCodAvaliacao(getCodAvaliacao());
		
				
				Thread threadWs = new Thread(){
					
					@Override		
					public void run(){
						
						
						
						SoapObject request = new SoapObject(WebService.getNamespace(),"buscarAvaliacoesPorId");
						PropertyInfo p1 = new PropertyInfo();
						p1.setName("Avaliacoes");
						p1.setValue(a);
						p1.setType(new Avaliacoes().getClass());
						
						request.addProperty(p1);
						
			
						SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
						envelope.setOutputSoapObject(request);
						envelope.addMapping(WebService.getNamespace(), "Avaliacoes",new Avaliacoes().getClass());
						MarshalDouble marshalDouble = new MarshalDouble();
						marshalDouble.register(envelope);
						
						HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
						
						
						try{
							ht.call(WebService.getSoapAction("buscarAvaliacoesPorId"), envelope);
							
							SoapObject res = new SoapObject();
							
							try{
								res = (SoapObject) envelope.getResponse();
								//Log.i("debug", "cheguei aki --- > " + res.toString());
								
										
							}catch(Exception e){
								Log.i("Erro: buscarAvaliacoesPorId (UNICO) ", e.toString());
							}
					
							retornoAvaliacoes = getSoapAvaliacoes(res);
					       // Log.i("debug aluno", retornoAvaliacoes.toString());
						         
						}catch(Exception e){
							//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
							Log.e("Erro: buscarAvaliacoesPorId (UNICO) ", e.toString());
						}		
					}
				};
				
				threadWs.start();
				try {
					threadWs.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
					retornoAvaliacoes.setGorduraCorporal(retornoAvaliacoes.getGorduraCorporal().buscarGorduraCorporalPorIdWeb(retornoAvaliacoes.getCodAvaliacao()));
					retornoAvaliacoes.setPerimetria(retornoAvaliacoes.getPerimetria().buscarPerimetriaPorIdWeb(retornoAvaliacoes.getCodAvaliacao()));
					retornoAvaliacoes.setQuestionarioQPAF(retornoAvaliacoes.getQuestionarioQPAF().buscarQuestionarioQPAFPorIdWeb(retornoAvaliacoes.getCodAvaliacao()));
					retornoAvaliacoes.setSituacaoCoronaria(retornoAvaliacoes.getSituacaoCoronaria().buscarSituacaoCoronariaPorIdWeb(retornoAvaliacoes.getCodAvaliacao()));
					retornoAvaliacoes.setFotosAvaliacao(retornoAvaliacoes.getFotosAvaliacao().buscarFotosAvaliacaoPorIdWeb(retornoAvaliacoes.getCodAvaliacao()));
			
					
				return retornoAvaliacoes;
	}
	
	public ArrayList<Avaliacoes> buscarAvaliacoesCompletasPorPersonalWeb(String usuarioPersonal,String filtro){
		clear();
		final Avaliacoes a= new Avaliacoes();
		a.setUsuarioPersonal(usuarioPersonal);
		a.setUsuarioAluno(filtro);
		
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"buscarAvaliacoesPorPersonal");
					PropertyInfo p1 = new PropertyInfo();
					p1.setName("Avaliacoes");
					p1.setValue(a);
					p1.setType(new Avaliacoes().getClass());
					
					request.addProperty(p1);
					
		
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					envelope.addMapping(WebService.getNamespace(), "Avaliacoes",new Personal().getClass());
					
					HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
					
					
					try{
						ht.call(WebService.getSoapAction("buscarAvaliacoesPorPersonal"), envelope);
						
						SoapObject res = new SoapObject();
						Vector<SoapObject> response = new Vector<SoapObject>();
						
						try{
							res = (SoapObject) envelope.getResponse();
							//Log.i("debug", "cheguei aki --- > " + res.toString());
							
									
						}catch(Exception e){
							Log.i("Erro: buscarAvaliacoesPorPersonalWeb (UNICO) ", e.toString());
						}
						
						try{
							
							response = (Vector<SoapObject>) envelope.getResponse();
							//Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
							
						}catch(Exception e){
							Log.i("Erro: buscarAvaliacoesPorPersonalWeb (VETOR) ", e.toString());
						}

						if(response.isEmpty()){
							
							 	Avaliacoes item = getSoapAvaliacoes(res);
						       // Log.i("alunos", item.toString());
						        retornoListaAvaliacoes.add(item);
						         
						}else{
							
							for(SoapObject soapPersonal: response){  
						         Avaliacoes item = getSoapAvaliacoes(soapPersonal);
						         //Log.i("alunos", item.toString());
						         retornoListaAvaliacoes.add(item);			       
					           }
							
						}
					         
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("Erro: buscar Avaliacoes Por personal", e.toString());
						e.printStackTrace();
					}		
				}
			};
			
			threadWs.start();
			try {
				threadWs.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			for(Avaliacoes x : retornoListaAvaliacoes){
				//Log.i("for each pesquisando", x.toString());
				x.setGorduraCorporal(x.getGorduraCorporal().buscarGorduraCorporalPorIdWeb(x.getCodAvaliacao()));
				x.setPerimetria(x.getPerimetria().buscarPerimetriaPorIdWeb(x.getCodAvaliacao()));
				x.setQuestionarioQPAF(x.getQuestionarioQPAF().buscarQuestionarioQPAFPorIdWeb(x.getCodAvaliacao()));
				x.setSituacaoCoronaria(x.getSituacaoCoronaria().buscarSituacaoCoronariaPorIdWeb(x.getCodAvaliacao()));
				x.setFotosAvaliacao(x.getFotosAvaliacao().buscarFotosAvaliacaoPorIdWeb(x.getCodAvaliacao()));
			}
			
			
			return retornoListaAvaliacoes;
	}
	
	public ArrayList<Avaliacoes>buscarAvaliacoesCompletasPorAlunoWeb(String filtro){
		clear();
		final Avaliacoes a= new Avaliacoes();
		a.setUsuarioAluno(filtro);
		
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"buscarAvaliacoesPorAluno");
					PropertyInfo p1 = new PropertyInfo();
					p1.setName("Avaliacoes");
					p1.setValue(a);
					p1.setType(new Avaliacoes().getClass());
					
					request.addProperty(p1);
					
		
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					envelope.addMapping(WebService.getNamespace(), "Avaliacoes",new Personal().getClass());
					
					HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
					
					
					try{
						ht.call(WebService.getSoapAction("buscarAvaliacoesPorAluno"), envelope);
						
						SoapObject res = new SoapObject();
						Vector<SoapObject> response = new Vector<SoapObject>();
						
						try{
							res = (SoapObject) envelope.getResponse();
							//Log.i("debug", "cheguei aki --- > " + res.toString());
							
									
						}catch(Exception e){
							Log.i("Erro: buscarAvaliacoesPorAlunoWeb ( UNICO ) ", e.toString());
						}
						
						try{
							
							response = (Vector<SoapObject>) envelope.getResponse();
						//	Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
							
						}catch(Exception e){
							Log.i("Erro: buscarAvaliacoesPorAlunoWeb (VETOR) ", e.toString());
						}

						if(response.isEmpty()){
							
							 	Avaliacoes item = getSoapAvaliacoes(res);
						        Log.i("alunos", item.toString());
						        retornoListaAvaliacoes.add(item);
						         
						}else{
							
							for(SoapObject soapPersonal: response){  
						         Avaliacoes item = getSoapAvaliacoes(soapPersonal);
						         Log.i("alunos", item.toString());
						         retornoListaAvaliacoes.add(item);			       
					           }
							
						}
					         
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("Erro: buscarAvaliacoesPorAlunoWeb", e.toString());
					}		
				}
			};
			
			threadWs.start();
			try {
				threadWs.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(Avaliacoes x : retornoListaAvaliacoes){
				x.setGorduraCorporal(x.getGorduraCorporal().buscarGorduraCorporalPorIdWeb(x.getCodAvaliacao()));
				x.setPerimetria(x.getPerimetria().buscarPerimetriaPorIdWeb(x.getCodAvaliacao()));
				x.setQuestionarioQPAF(x.getQuestionarioQPAF().buscarQuestionarioQPAFPorIdWeb(x.getCodAvaliacao()));
				x.setSituacaoCoronaria(x.getSituacaoCoronaria().buscarSituacaoCoronariaPorIdWeb(x.getCodAvaliacao()));
				x.setFotosAvaliacao(x.getFotosAvaliacao().buscarFotosAvaliacaoPorIdWeb(x.getCodAvaliacao()));
			}
			
			return retornoListaAvaliacoes;
	}
	
	
	//clear
	
	public void clear(){
		retorno = false;
		retornoAvaliacoes = new Avaliacoes();
		retornoCod = 0;
		statusDoEnvio = 0;
		retornoListaAvaliacoes.clear();
	}
	
	
	//Gets and Sets
	public Avaliacoes getSoapAvaliacoes(SoapObject res){
		 Avaliacoes item  = new Avaliacoes();
		 if (res.hasProperty("codAvaliacao")) {
            item.setCodAvaliacao(Integer.parseInt(res.getPropertyAsString("codAvaliacao")));
		 }
		 if (res.hasProperty("dataAvaliacao")) {
	            item.setDataAvaliacao(res.getPropertyAsString("dataAvaliacao"));
		 }
		 if (res.hasProperty("horaAvaliacao")) {
	            item.setHoraAvaliacao(res.getPropertyAsString("horaAvaliacao"));
		 }
		 if (res.hasProperty("usuarioPersonal")) {
	            item.setUsuarioPersonal(res.getPropertyAsString("usuarioPersonal"));
		 }
		 if (res.hasProperty("usuarioAluno")) {
	            item.setUsuarioAluno(res.getPropertyAsString("usuarioAluno"));
		 }
		 if (res.hasProperty("ativo")) {
	            item.setAtivo(res.getPropertyAsString("ativo"));
		 }
		
	     return item;
	}

	
	//implementação da KVM serializations

	@Override
	public Object getProperty(int arg0) {
		switch(arg0){
		case 0:
        	return codAvaliacao;
        case 1:
        	return dataAvaliacao;
        case 2:
        	return horaAvaliacao;
        case 3:
        	return usuarioPersonal;
        case 4:
        	return usuarioAluno;
        case 5:
        	return ativo;
        default:
        	return null;		
        }
	}

	@Override
	public int getPropertyCount() {
		return 6;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		switch(arg0)
        {
        case 0:
            arg2.type = PropertyInfo.INTEGER_CLASS;
            arg2.name = "codAvaliacao";
            break;
        case 1:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "dataAvaliacao";
            break;
        case 2:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "horaAvaliacao";
            break;
        case 3:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "usuarioPersonal";
            break;
        case 4:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "usuarioAluno";
            break;
        case 5:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "ativo";
            break;
        }
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		switch(arg0){
       
        case 0:
        	setCodAvaliacao(Integer.parseInt(arg1.toString()));
            break;
        case 1:
        	setDataAvaliacao(arg1.toString());
            break;
        case 2:
        	setHoraAvaliacao(arg1.toString());
            break;
        case 3:
        	setUsuarioPersonal(arg1.toString());
            break;
        case 4:
        	setUsuarioAluno(arg1.toString());
            break;
        case 5:
        	setAtivo(arg1.toString());
            break;
        default:
            break;
        }
	}
	
	public int getCodAvaliacao() {
		return codAvaliacao;
	}
	public void setCodAvaliacao(int codAvaliacao) {
		this.codAvaliacao = codAvaliacao;
	}
	public String getDataAvaliacao() {
		return dataAvaliacao;
	}
	public void setDataAvaliacao(String dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
	}
	
	public String getHoraAvaliacao() {
		return horaAvaliacao;
	}
	public void setHoraAvaliacao(String horaAvaliacao) {
		this.horaAvaliacao = horaAvaliacao;
	}
	public String getUsuarioPersonal() {
		return usuarioPersonal;
	}
	public void setUsuarioPersonal(String usuarioPersonal) {
		this.usuarioPersonal = usuarioPersonal;
	}
	public String getUsuarioAluno() {
		return usuarioAluno;
	}
	public void setUsuarioAluno(String usuarioAluno) {
		this.usuarioAluno = usuarioAluno;
	}
	public String getAtivo() {
		return ativo;
	}
	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}
	public GorduraCorporal getGorduraCorporal() {
		return gorduraCorporal;
	}
	public void setGorduraCorporal(GorduraCorporal gorduraCorporal) {
		this.gorduraCorporal = gorduraCorporal;
	}
	public Perimetria getPerimetria() {
		return perimetria;
	}
	public void setPerimetria(Perimetria perimetria) {
		this.perimetria = perimetria;
	}
	public QuestionarioQPAF getQuestionarioQPAF() {
		return questionarioQPAF;
	}
	public void setQuestionarioQPAF(QuestionarioQPAF questionarioQPAF) {
		this.questionarioQPAF = questionarioQPAF;
	}
	public SituacaoCoronaria getSituacaoCoronaria() {
		return situacaoCoronaria;
	}
	public void setSituacaoCoronaria(SituacaoCoronaria situacaoCoronaria) {
		this.situacaoCoronaria = situacaoCoronaria;
	}
	public FotosAvaliacao getFotosAvaliacao() {
		return fotosAvaliacao;
	}
	public void setFotosAvaliacao(FotosAvaliacao fotosAvaliacao) {
		this.fotosAvaliacao = fotosAvaliacao;
	}
	
	
	
}
