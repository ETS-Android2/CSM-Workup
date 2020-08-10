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
import android.database.DatabaseUtils;
import android.util.Log;
import br.com.Banco.Banco;
import br.com.Utilitarios.WebService;

public class TreinamentoRealizado implements KvmSerializable {
	
	//Atributos da classe
	private int codTreinamentoRealizado;
	private String dataTreino;
	private String horaInicio;
	private String horaFim;
	private String usuarioPersonal;
	private String usuarioAluno;
	private int codTreinamento;
	private String ativo;
	
	
	
	
	//Atributos auxiliares
	private static int retornoLastId;
	private static boolean retorno;
	private static TreinamentoRealizado retornoTreinamentoRealizado;
	private static ArrayList<TreinamentoRealizado> retornoListaTreinamentoRealizado = new ArrayList<TreinamentoRealizado>();
	private static ArrayList<ExercicioRealizado> retornoListaExerciciosRealizados = new ArrayList<ExercicioRealizado>();
	private static ArrayList<ExercicioRealizado> exercicios = new ArrayList<ExercicioRealizado>();
	
	
	// Construtores
	public TreinamentoRealizado(){
		
	}
	
	
	
	@Override
	public String toString() {
		return "TreinamentoRealizado [codTreinamentoRealizado="
				+ codTreinamentoRealizado + ", dataTreino=" + dataTreino
				+ ", horaInicio=" + horaInicio + ", horaFim=" + horaFim
				+ ", usuarioPersonal=" + usuarioPersonal + ", usuarioAluno="
				+ usuarioAluno + ", codTreinamento=" + codTreinamento
				+ ", ativo=" + ativo + "]";
	}



	public TreinamentoRealizado(int codTreinamentoRealizado, String dataTreino,
			String horaInicio, String horaFim, String usuarioPersonal,
			String usuarioAluno,int codTreinamento, String ativo) {
		super();
		this.codTreinamentoRealizado = codTreinamentoRealizado;
		this.dataTreino = dataTreino;
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
		this.usuarioPersonal = usuarioPersonal;
		this.usuarioAluno = usuarioAluno;
		this.ativo = ativo;
		this.codTreinamento = codTreinamento;
	}


	//CUD Local
	public boolean salvarTreinamentoRealizado(Banco b){
		String SQL = "INSERT INTO treinamentoRealizado ("
				+ "codTreinamentoRealizado,"
				+ "dataTreino,"
				+ "horaInicio,"
				+ "horaFim,"
				+ "usuarioPersonal,"
				+ "usuarioAluno,"
				+ "ativo ,"
				+ "codTreinamento) "
				+ "VALUES (" 
				+ codTreinamentoRealizado 
				+ ",'"+ dataTreino 
				+ "','"+ horaInicio 
				+ "','"+ horaFim 
				+  "','"+ usuarioPersonal 
				+ "','" + usuarioAluno 
				+ "','" + ativo 
				+ "'," + codTreinamento 
				+ ");";
		try{
			b.execSQL(SQL);
			return true;
		}catch(Exception e){
			Log.i("Erro: salvarTreinamentoRealizado (LOCAL) ",e.toString());
			return false;
		}
	}
	
	public boolean finalizarTreinamento(Banco b){
		String SQL = "UPDATE treinamentoRealizado set horaFim =  '"+ horaFim + 
				"' where codTreinamentoRealizado = " + codTreinamentoRealizado;
		try{
			b.execSQL(SQL);
			return true;
		}catch(Exception e){
			Log.i("Erro: finalizarTReinamento (LOCAL) ",e.toString());
			return false;
		}
	}
	
	public boolean atualizarTreinamento(Banco b){
		
		String SQL = "UPDATE treinamentoRealizado set "
				+ "dataTreino = '" + dataTreino + "',"
				+ "horaInicio = '"+ horaInicio + "',"
				+ "horaFim = '"+ horaFim + "',"
				+ "usuarioPersonal = '"+ usuarioPersonal + "',"
				+ "usuarioAluno = '" + usuarioAluno + "',"
				+ "ativo = '"+ ativo + "',"
				+ "codTreinamento = " + codTreinamento + " "
				+ "where codTreinamentoRealizado = " + codTreinamentoRealizado + ";"; 
		try{
			b.execSQL(SQL);
			return true;
		}catch(Exception e){
			Log.i("Erro: atualizarTreinamento (LOCAL)",e.toString());
			return false;
		}
	}
	
	
	
	
	//Buscas local
	public ArrayList<TreinamentoRealizado> buscarTreinoPorPersonal(Banco b, String usuarioPersonal){
			
		String SQL = "SELECT * FROM TreinamentoRealizado where (usuarioPersonal = '" + usuarioPersonal + "') and ativo = 'ativado'";
		
		
		ArrayList<TreinamentoRealizado> treinos = new ArrayList<TreinamentoRealizado>();
		
		Cursor dataset = null;
		dataset = b.querySQL(SQL);
		
		dataset.moveToFirst();
		
		//Log.i("select Exercicios", SQL);
		
		int col_codTreino = dataset.getColumnIndex("codTreinamentoRealizado");
		int col_dataTreino = dataset.getColumnIndex("dataTreino");
		int col_horaInicio = dataset.getColumnIndex("horaInicio");
		int col_horaFim = dataset.getColumnIndex("horaFim");
		int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
		int col_usuarioAluno = dataset.getColumnIndex("usuarioAluno");
		int col_ativo = dataset.getColumnIndex("ativo");
		int col_codTreinamento = dataset.getColumnIndex("codTreinamento");
		
	
		
		int numRows = dataset.getCount();
		
//		Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
		dataset.moveToFirst();
		
		for(int c=0; c<numRows; c++) {
			int codTreino = dataset.getInt(col_codTreino);
			String dataTreino = dataset.getString(col_dataTreino);
			String horaInicio = dataset.getString(col_horaInicio);
			String horaFim = dataset.getString(col_horaFim);
			String usuarioP = dataset.getString(col_usuarioPersonal);
			String usuarioA = dataset.getString(col_usuarioAluno);
			String ativo = dataset.getString(col_ativo);
			int codTreinamento = dataset.getInt(col_codTreinamento);
				
			
			
			TreinamentoRealizado a = new TreinamentoRealizado(codTreino,dataTreino,horaInicio,horaFim,usuarioP,usuarioA,codTreinamento,ativo);
			
			dataset.moveToNext();
			treinos.add(a);
		}
		return treinos;
	}

	public ArrayList<TreinamentoRealizado> buscarTreinoPorAluno (Banco b, String usuarioAluno){
		
	
	String SQL = "SELECT * FROM TreinamentoRealizado where (usuarioAluno = '" + usuarioAluno + "') and ativo = 'ativado'";
	
	ArrayList<TreinamentoRealizado> treinos = new ArrayList<TreinamentoRealizado>();
	
	Cursor dataset = null;
	dataset = b.querySQL(SQL);
	
	dataset.moveToFirst();
	
//	Log.i("select treinos", SQL);
	
	int col_codTreino = dataset.getColumnIndex("codTreinamentoRealizado");
	int col_dataTreino = dataset.getColumnIndex("dataTreino");
	int col_horaInicio = dataset.getColumnIndex("horaInicio");
	int col_horaFim = dataset.getColumnIndex("horaFim");
	int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
	int col_usuarioAluno = dataset.getColumnIndex("usuarioAluno");
	int col_ativo = dataset.getColumnIndex("ativo");
	int col_codTreinamento = dataset.getColumnIndex("codTreinamento");


	
	int numRows = dataset.getCount();
	
	//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
	dataset.moveToFirst();
	
	for(int c=0; c<numRows; c++) {
		int codTreino = dataset.getInt(col_codTreino);
		String dataTreino = dataset.getString(col_dataTreino);
		String horaInicio = dataset.getString(col_horaInicio);
		String horaFim = dataset.getString(col_horaFim);
		String usuarioP = dataset.getString(col_usuarioPersonal);
		String usuarioA = dataset.getString(col_usuarioAluno);
		String ativo = dataset.getString(col_ativo);
		int codTreinamento = dataset.getInt(col_codTreinamento);
			
		
		
		TreinamentoRealizado a = new TreinamentoRealizado(
				codTreino,dataTreino,horaInicio,horaFim,usuarioP,
				usuarioA,codTreinamento,ativo);
		
		dataset.moveToNext();
		treinos.add(a);
	}
	return treinos;
		
	}
	
	public ArrayList<ExercicioRealizado> buscarExerciciosRealizadoPorTreinamento(Banco b, int codTreino){
		
		
		ArrayList<ExercicioRealizado> busca = new ArrayList<ExercicioRealizado>();
		
		String SQL = "SELECT *"
				+ " FROM exercicioRealizado"
				+ " WHERE exercicioRealizado.codTreinamentoRealizado = " + codTreino ;
				
	
		Log.i("SQL", SQL);
		Cursor dataset = b.querySQL(SQL);
		
		int col_codExercicioRealizado = dataset.getColumnIndex("codExercicioRealizado");
		int col_nomeExercicio = dataset.getColumnIndex("nomeExercicio");
		int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
		int col_usuarioAluno = dataset.getColumnIndex("usuarioAluno");
		int col_inicioExercicio = dataset.getColumnIndex("inicioExercicio");
		int col_fimExercicio = dataset.getColumnIndex("fimExercicio");
		int col_duracaoExercicio = dataset.getColumnIndex("duracaoExercicio");
		int col_tipoExercicio = dataset.getColumnIndex("tipoExercicio");
		int col_codExercicio = dataset.getColumnIndex("codExercicio");
		int col_ativo = dataset.getColumnIndex("ativo");
		int col_codTreinamentoRealizado = dataset.getColumnIndex("codTreinamentoRealizado");
		
	
	
		
		int numRows = dataset.getCount();
		
		
		dataset.moveToFirst();
		
		for(int c=0; c<numRows; c++) {
			int codExercicioRealizado = dataset.getInt(col_codExercicioRealizado);
			String nomeExercicio = dataset.getString(col_nomeExercicio);
			String usuarioPersonal = dataset.getString(col_usuarioPersonal);
			String usuarioAluno = dataset.getString(col_usuarioAluno);
			String inicioExercicio = dataset.getString(col_inicioExercicio);
			String fimExercicio = dataset.getString(col_fimExercicio);
			int duracaoExercicio = dataset.getInt(col_duracaoExercicio);
			String tipoExercicio = dataset.getString(col_tipoExercicio);
			int codExercicio = dataset.getInt(col_codExercicio);
			String ativo = dataset.getString(col_ativo);
			int codTreinamento = dataset.getInt(col_codTreinamentoRealizado);
			
			
			ExercicioRealizado a = new ExercicioRealizado(
					codExercicioRealizado,nomeExercicio,inicioExercicio,fimExercicio,duracaoExercicio,
					usuarioPersonal, usuarioAluno,tipoExercicio,codExercicio,ativo,codTreinamento);
			
			dataset.moveToNext();
			busca.add(a);
		}
		
		return busca;
		
	}
	
	public int buscarUltimoTreinamentoRealizado(Banco b){
		
		
		String SQL = "SELECT last_insert_rowid() FROM TreinamentoRealizado";
	
		
		Cursor dataset = b.querySQL(SQL);
		
		int numRows = dataset.getCount();
		
	//	Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
		dataset.moveToFirst();
		
		if(numRows > 0 ){
			
			int codTreinamento = dataset.getInt(0);
			return codTreinamento;
		}
		return -1;
	}
	
	
	//CUD Web Service
	
	public int salvarTreinamentoRealizadoAlunoWeb(Banco b){
		clear();
		final TreinamentoRealizado t = new TreinamentoRealizado(codTreinamentoRealizado,
				dataTreino,horaInicio,horaFim,usuarioPersonal,
				usuarioAluno,codTreinamento,ativo);
		
		exercicios = buscarExerciciosRealizadoPorTreinamento(b,codTreinamentoRealizado);
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"salvarTreinamentoRealizadoAluno");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("TreinamentoRealizado");
				p1.setValue(t);
				p1.setType(new TreinamentoRealizado().getClass());
				
				request.addProperty(p1);
				
			
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("salvarTreinamentoRealizadoAluno"), envelope);
					
					 retornoLastId = Integer.parseInt(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: salvarTreinamentoRealizadoAluno", e.toString());
				}		
			}
		};
		
		threadWs.start();
		try {
			threadWs.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		boolean flag = false;
		for(ExercicioRealizado ex : exercicios){
			
			ex.setCodTreinamentoRealizado(retornoLastId);
			int aux = ex.salvarExercicioRealizadoAlunoWeb();
			if(aux > 0 ){
				ex.setCodExercicioRealizado(aux);
				ex.salvarExercicioRealizado(b);
			}else{
				flag = true;
			}
			
		}
		
		if(flag){
			Log.i("Erro: ExerciciosRealizados", "Ao menos um exercicio realizado não foi sincronizado com o servidor... [consulte Treinamento Realizado - salvar treinamentoRealizadoWeb] ");
		}
		return retornoLastId;
	}
	
	public boolean finalizarTreinamentoWeb(){
		clear();
		final TreinamentoRealizado t = new TreinamentoRealizado(codTreinamentoRealizado,
				dataTreino,horaInicio,horaFim,usuarioPersonal,
				usuarioAluno,codTreinamento,ativo);
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"finalizarTreinamento");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("TreinamentoRealizado");
				p1.setValue(t);
				p1.setType(new TreinamentoRealizado().getClass());
				
				request.addProperty(p1);
				
			
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("finalizarTreinamento"), envelope);
					
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: finalizarTreinamento", e.toString());
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
	
	public int salvarTreinamentoRealizadoPersonalWeb(Banco b){
		clear();
		final TreinamentoRealizado t = new TreinamentoRealizado(getCodTreinamentoRealizado(),
				getDataTreino(),getHoraInicio(),getHoraFim(),getUsuarioPersonal(),
				getUsuarioAluno(),getCodTreinamento(),getAtivo());
		
		exercicios = buscarExerciciosRealizadoPorTreinamento(b,codTreinamentoRealizado);
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"salvarTreinamentoRealizadoPersonal");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("TreinamentoRealizado");
				p1.setValue(t);
				p1.setType(new TreinamentoRealizado().getClass());
				
				request.addProperty(p1);
				
			
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("salvarTreinamentoRealizadoPersonal"), envelope);
					
					 retornoLastId = Integer.parseInt(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: salvarTreinamentoRealizadoPersonal", e.toString());
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
		

		boolean flag = false;
		for(ExercicioRealizado ex : exercicios){
			
			ex.setCodTreinamentoRealizado(retornoLastId);
			int aux = ex.salvarExercicioRealizadoPersonalWeb();
			if(aux > 0 ){
				ex.setCodExercicioRealizado(aux);
				ex.salvarExercicioRealizado(b);
			}else{
				flag = true;
			}
			
		}
		
		if(flag){
			Log.i("Erro: ExerciciosRealizados", "Ao menos um exercicio realizado não foi sincronizado com o servidor... [consulte Treinamento Realizado - salvar treinamentoRealizadoWeb] ");
		}
		
		return retornoLastId;
	}
	
	public boolean atualizarTreinamentoRealizadoWeb(int codTreinamentoRealizado){
		clear();
		final TreinamentoRealizado t = new TreinamentoRealizado(codTreinamentoRealizado,
				getDataTreino(),getHoraInicio(),getHoraFim(),getUsuarioPersonal(),
				getUsuarioAluno(),codTreinamento,getAtivo());
		
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"atualizarTreinamentoRealizado");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("TreinamentoRealizado");
				p1.setValue(t);
				p1.setType(new TreinamentoRealizado().getClass());
				
				request.addProperty(p1);
				
			
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("atualizarTreinamentoRealizado"), envelope);
					
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: AtualizarTreinamentoRealizado", e.toString());
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
	
	public TreinamentoRealizado buscarTreinamentoRealizadoPorIdWeb(int codTreinamentoRealizado){
		clear();
		final TreinamentoRealizado a = new TreinamentoRealizado();
		a.setCodTreinamentoRealizado(codTreinamentoRealizado);
		
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"buscarTreinamentoRealizadoPorId");
					PropertyInfo p1 = new PropertyInfo();
					p1.setName("TreinamentoRealizado");
					p1.setValue(a);
					p1.setType(new TreinamentoRealizado().getClass());
					
					request.addProperty(p1);
					
		
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					
					HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
					
					
					try{
						ht.call(WebService.getSoapAction("buscarTreinamentoRealizadoPorId"), envelope);
						
						SoapObject res = new SoapObject();
						
						try{
							res = (SoapObject) envelope.getResponse();
						//	Log.i("debug", "cheguei aki --- > " + res.toString());
							
									
						}catch(Exception e){
							Log.i("Erro: buscarTreinamentoPorId (UNICO) ", e.toString());
						}
						
							
							 	TreinamentoRealizado item = getSoapTreinamentoRealizado(res);
						       // Log.i("alunos", item.toString());
						        retornoTreinamentoRealizado =  item;
				
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("Erro: buscarTreinoPorPersonal", e.toString());
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
			return retornoTreinamentoRealizado;
	}
	
	
	public ArrayList<TreinamentoRealizado> buscarTreinamentoPorPersonalWeb(String usuarioPersonal){
		clear();
		final ArrayList<TreinamentoRealizado> retornoListaTreinamentoRealizado = new ArrayList<TreinamentoRealizado>();
		final Personal a = new Personal();
		a.setUsuario(usuarioPersonal);
		
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"buscarTreinamentoPorPersonal");
					PropertyInfo p1 = new PropertyInfo();
					p1.setName("Personal");
					p1.setValue(a);
					p1.setType(new Personal().getClass());
					
					request.addProperty(p1);
					
		
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					
					HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
					
					
					try{
						ht.call(WebService.getSoapAction("buscarTreinamentoPorPersonal"), envelope);
						
						SoapObject res = new SoapObject();
						Vector<SoapObject> response = new Vector<SoapObject>();
						
						try{
							res = (SoapObject) envelope.getResponse();
						//	Log.i("debug", "cheguei aki --- > " + res.toString());
							
									
						}catch(Exception e){
							Log.i("Erro: buscarTreinamentoPorPersonal (UNICO) ", e.toString());
						}
						
						try{
							
							response = (Vector<SoapObject>) envelope.getResponse();
							//Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
							
						}catch(Exception e){
							Log.i("Erro: buscarTreinoPorPersonal (VETOR) ", e.toString());
						}
						
						
						if(response.isEmpty()){
							
							 	TreinamentoRealizado item = getSoapTreinamentoRealizado(res);
						       // Log.i("alunos", item.toString());
						        retornoListaTreinamentoRealizado.add(item);
						         
						}else{
							
							for(SoapObject soapPersonal: response){  
						         TreinamentoRealizado item = getSoapTreinamentoRealizado(soapPersonal);
						        // Log.i("alunos", item.toString());
						         retornoListaTreinamentoRealizado.add(item);			       
					           }
							
						}
					         
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("Erro: buscarTreinamentoPorPersonal", e.toString());
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
			return retornoListaTreinamentoRealizado;
	}
	
	public ArrayList<TreinamentoRealizado> buscarTreinamentoPorAlunoWeb (String usuarioAluno){
		clear();
		final Aluno a = new Aluno();
		a.setUsuario(usuarioAluno);
		
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"buscarTreinamentoPorAluno");
					PropertyInfo p1 = new PropertyInfo();
					p1.setValue(a);
					p1.setType(new Aluno().getClass());
					
					request.addProperty(p1);
					
		
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					
					HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
					
					
					try{
						ht.call(WebService.getSoapAction("buscarTreinamentoPorAluno"), envelope);
						
						SoapObject res = new SoapObject();
						Vector<SoapObject> response = new Vector<SoapObject>();
						
						try{
							res = (SoapObject) envelope.getResponse();
						//	Log.i("debug", "cheguei aki --- > " + res.toString());
							
									
						}catch(Exception e){
							Log.i("Erro: buscarTreinamentoPorAluno (UNICO) ", e.toString());
						}
						
						try{
							
							response = (Vector<SoapObject>) envelope.getResponse();
							//Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
							
						}catch(Exception e){
							Log.i("Erro: buscarTreinamentoPorAluno (VETOR) ", e.toString());
						}

						if(response.isEmpty()){
							
							 	TreinamentoRealizado item = getSoapTreinamentoRealizado(res);
						     //   Log.i("alunos", item.toString());
						        retornoListaTreinamentoRealizado.add(item);
						         
						}else{
							
							for(SoapObject soapPersonal: response){  
						         TreinamentoRealizado item = getSoapTreinamentoRealizado(soapPersonal);
						     //    Log.i("alunos", item.toString());
						         retornoListaTreinamentoRealizado.add(item);			       
					           }
							
						}
					         
					}catch(Exception e){
						e.printStackTrace();
						Log.e("Erro: buscarTreinamentoPorAluno", e.toString());
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
			return retornoListaTreinamentoRealizado;
	}
	
	public ArrayList<ExercicioRealizado> buscarExerciciosRealizadoPorTreinamentoWeb(int codTreino){
		clear();
		final ArrayList<ExercicioRealizado> retornoListaExerciciosRealizados = new ArrayList<ExercicioRealizado>();
		final Treinamento a = new Treinamento();
		a.setCodTreinamento(codTreino);
		
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"buscarExerciciosRealizadosPorTreinamento");
					PropertyInfo p1 = new PropertyInfo();
					p1.setName("Treinamento");
					p1.setValue(a);
					p1.setType(new Treinamento().getClass());
					
					request.addProperty(p1);
					
		
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					
					HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
					
					
					try{
						ht.call(WebService.getSoapAction("buscarExerciciosRealizadosPorTreinamento"), envelope);
						
						SoapObject res = new SoapObject();
						Vector<SoapObject> response = new Vector<SoapObject>();
						
						try{
							res = (SoapObject) envelope.getResponse();
							//Log.i("debug", "cheguei aki --- > " + res.toString());
							
									
						}catch(Exception e){
							Log.i("Erro: buscarExerciciosRealizadosPorTreinamento (UNIcO) ", e.toString());
						}
						
						try{
							
							response = (Vector<SoapObject>) envelope.getResponse();
							//Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
							
						}catch(Exception e){
							Log.i("Erro: buscarExerciciosRealizadosPorTreinamento (VETOR) ", e.toString());
						}

						if(response.isEmpty()){
							
							 	ExercicioRealizado item = getSoapExercicioRealizado(res);
						      // Log.i("alunos", item.toString());
						        retornoListaExerciciosRealizados.add(item);
						         
						}else{
							
							for(SoapObject soapPersonal: response){  
						         ExercicioRealizado item = getSoapExercicioRealizado(soapPersonal);
						        // Log.i("alunos", item.toString());
						         retornoListaExerciciosRealizados.add(item);			       
					           }
							
						}
					         
					}catch(Exception e){
						e.printStackTrace();
						Log.e("Erro: buscarExerciciosRealizadosPorTreinamento", e.toString());
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
			return retornoListaExerciciosRealizados;
	}
	
	public int buscarUltimoTreinamentoRealizadoPersonalWeb(final String usuarioPersonal){
		clear();
		final Personal a = new Personal();
		a.setUsuario(usuarioPersonal);
		
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"buscarUltimoTreinamentoRealizadoPersonal");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Personal");
				p1.setValue(a);
				p1.setType(new Personal().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("buscarUltimoTreinamentoRealizadoPersonal"), envelope);
					
					retornoLastId = Integer.parseInt(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: buscarUltimoTreinamentoRealizadoPersonal", e.toString());
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
		return retornoLastId;
	}
	
	public int buscarUltimoTreinamentoRealizadoAlunoWeb(final String usuarioAluno){
		clear();
		final Aluno a = new Aluno();
		a.setUsuario(usuarioAluno);
		
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"buscarUltimoTreinamentoRealizadoAluno");
				
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Aluno");
				p1.setValue(a);
				p1.setType(new Aluno().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("buscarUltimoTreinamentoRealizadoAluno"), envelope);
					
					retornoLastId = Integer.parseInt(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: buscarUltimoTreinamentoRealizadoAluno", e.toString());
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
		return retornoLastId;
	}
	

	//clean
	public void clear(){
		retorno = false;
		retornoLastId = 0;
		retornoListaExerciciosRealizados.clear();
		retornoListaTreinamentoRealizado.clear();
		retornoTreinamentoRealizado = new TreinamentoRealizado();
	}
	
	//Gets e sets
	
	
 	public TreinamentoRealizado getSoapTreinamentoRealizado(SoapObject res){
		TreinamentoRealizado item = new TreinamentoRealizado();
		 
		 if (res.hasProperty("codTreinamentoRealizado")) {
        	 item.setCodTreinamentoRealizado(Integer.parseInt(res.getPropertyAsString("codTreinamentoRealizado")));
         }
		 if (res.hasProperty("dataTreino")) {
        	 item.setDataTreino(res.getPropertyAsString("dataTreino"));
         }
		 if (res.hasProperty("horaInicio")) {
        	 item.setHoraInicio(res.getPropertyAsString("horaInicio"));
         }
		 if (res.hasProperty("horaFim")) {
        	 item.setHoraFim(res.getPropertyAsString("horaFim"));
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
		 if (res.hasProperty("codTreinamento")) {
        	 item.setCodTreinamento(Integer.parseInt(res.getPropertyAsString("codTreinamento")));
         }
		 return item;
	}
	public ExercicioRealizado getSoapExercicioRealizado(SoapObject res){
		ExercicioRealizado item = new ExercicioRealizado();
		 if (res.hasProperty("codExercicioRealizado")) {
        	 item.setCodExercicioRealizado(Integer.parseInt(res.getPropertyAsString("codExercicioRealizado")));
         }
		 if (res.hasProperty("nomeExercicio")) {
        	 item.setNomeExercicio(res.getPropertyAsString("nomeExercicio"));
         }
		 if (res.hasProperty("inicioExercicio")) {
        	 item.setInicioExercicio(res.getPropertyAsString("inicioExercicio"));
         }
		 if (res.hasProperty("fimExercicio")) {
        	 item.setFimExercicio(res.getPropertyAsString("fimExercicio"));
         }
		 if (res.hasProperty("duracaoExercicio")) {
        	 item.setDuracaoExercicio(Integer.parseInt(res.getPropertyAsString("duracaoExercicio")));
         }
		 if (res.hasProperty("usuarioPersonal")) {
        	 item.setUsuarioPersonal(res.getPropertyAsString("usuarioPersonal"));
         }
		 if (res.hasProperty("usuarioAluno")) {
        	 item.setUsuarioAluno(res.getPropertyAsString("usuarioAluno"));
         }
		 if (res.hasProperty("tipoExercicio")) {
        	 item.setTipoExercicio(res.getPropertyAsString("tipoExercicio"));
         }
		 if (res.hasProperty("codExercicio")) {
        	 item.setCodExercicio(Integer.parseInt(res.getPropertyAsString("codExercicio")));
         }
		 if (res.hasProperty("ativo")) {
        	 item.setAtivo(res.getPropertyAsString("ativo"));
         }
		 if (res.hasProperty("codTreinamentoRealizado")) {
        	 item.setCodTreinamentoRealizado(Integer.parseInt(res.getPropertyAsString("codTreinamentoRealizado")));
         }
		 return item;
	}
	public int getCodTreinamentoRealizado() {
		return codTreinamentoRealizado;
	}
	public void setCodTreinamentoRealizado(int codTreinamentoRealizado) {
		this.codTreinamentoRealizado = codTreinamentoRealizado;
	}
	public String getDataTreino() {
		return dataTreino;
	}
	public void setDataTreino(String dataTreino) {
		this.dataTreino = dataTreino;
	}
	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	public String getHoraFim() {
		return horaFim;
	}
	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
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
	public int getCodTreinamento() {
		return codTreinamento;
	}
	public void setCodTreinamento(int codTreinamento) {
		this.codTreinamento = codTreinamento;
	}
	
	

	
	//KVM serialization
	@Override
	public Object getProperty(int arg0) {
		switch(arg0){
        case 0:
            return codTreinamentoRealizado;
        case 1:
            return dataTreino;
        case 2:
         return horaInicio;
        case 3:
        	return horaFim;
        case 4:
           	return usuarioPersonal;
        case 5:
        	return usuarioAluno;
        case 6:
        	return ativo;
        case 7: 
        	return codTreinamento;
        default: 
        		return null;
        		
        }
	}

	@Override
	public int getPropertyCount() {
		
		return 8;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		switch(arg0)
        {
        case 0:
            arg2.type = PropertyInfo.INTEGER_CLASS;
            arg2.name = "codTreinamentoRealizado";
            break;
        case 1:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "dataTreino";
            break;
        case 2:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "horaInicio";
            break;
        case 3:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "horaFim";
            break;
        case 4:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "usuarioPersonal";
            break;
        case 5:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "usuarioAluno";
            break;
        case 6:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "ativo";
            break;
        case 7:
            arg2.type = PropertyInfo.INTEGER_CLASS;
            arg2.name = "codTreinamento";
            break;
        default:break;
        }
		
		
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		switch(arg0){
        case 0:
            codTreinamentoRealizado = Integer.parseInt(arg1.toString());
            break;
        case 1:
        	dataTreino = arg1.toString();
            break;
        case 2:
        	horaInicio = arg1.toString();
            break;
        case 3:
        	horaFim = arg1.toString();
            break;
        case 4:
            usuarioPersonal = arg1.toString();
            break;
        case 5:
            usuarioAluno = arg1.toString();
            break;
        case 6:
            ativo = arg1.toString();
            break;
        case 7:
        	codTreinamento = Integer.parseInt(arg1.toString());
            break;
        default:
            break;
        }
		
	}

	
	
}
