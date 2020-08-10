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
import br.com.Banco.Banco;
import br.com.Utilitarios.WebService;

public class Treinamento implements KvmSerializable{
	
	//atributos da classe
	private int codTreinamento;
	private String nomeTreinamento;
	private ArrayList<Aerobico> exerciciosAerobicos;
	private ArrayList<Anaerobico> exerciciosAnaerobicos;
	private String usuarioPersonal;
	private String usuarioAluno;
	
	//Atributos Auxiliares
	private static boolean retorno;
	private static int retornoLastId;
	private static Treinamento retornoTreinamento;
	private static ArrayList<Treinamento> retornoListaTreinamento = new ArrayList<Treinamento>();
	private static ArrayList<Aerobico> retornoListaAerobico = new ArrayList<Aerobico>();
	private static ArrayList<Anaerobico> retornoListaAnaerobico = new ArrayList<Anaerobico>();
	
	
	
	//Construtores
	public Treinamento (int codTreinamento, String nomeTreinamento, 
			ArrayList<Aerobico> exerciciosAerobicos,ArrayList<Anaerobico> exerciciosAnaerobicos, 
			String usuarioPersonal, String usuarioAluno){
		this.codTreinamento = codTreinamento;
		this.nomeTreinamento = nomeTreinamento;
		this.exerciciosAerobicos = exerciciosAerobicos;
		this.exerciciosAnaerobicos = exerciciosAnaerobicos;
		this.usuarioPersonal = usuarioPersonal;
		this.usuarioAluno = usuarioAluno;
	}
	
	public Treinamento (){
		
	}
	
	
	//toString
	
	@Override
	public String toString() {
		return "Treinamento [codTreinamento=" + codTreinamento
				+ ", nomeTreinamento=" + nomeTreinamento + ", exercicios="
				+ exerciciosAerobicos+ ", usuarioPersonal=" + usuarioPersonal
				+ ", usuarioAluno=" + usuarioAluno + "]";
	}
	
	//CUD local
	public boolean salvarTreinamento(Banco b, String usuario){
		
		try{
			String SQL = "INSERT INTO Treinamento (codTreinamento, nomeTreinamento, usuarioPersonal" +
				") VALUES ("+ codTreinamento + ",'"  + nomeTreinamento + "','" + usuario + "');";
			b.execSQL(SQL);
			
			for (Aerobico e: exerciciosAerobicos){
				String s = "";
				//Log.i("entrei no for", e.getNomeExercicio());
				s = "INSERT INTO treinamentoTemExercicios(codAerobico,codTreinamento)" +
							"VALUES (" + e.getCodExercicio() + ", " + codTreinamento + ");";
							
				b.execSQL(s);
			}
			
			for (Anaerobico e: exerciciosAnaerobicos){
				String s = "";
				Log.i("entrei no for", e.getNomeExercicio());
				 s = "INSERT INTO treinamentoTemExercicios(codAnaerobico,codTreinamento)" +
							"VALUES (" + e.getCodExercicio() + ", " + codTreinamento + ");";
							
				b.execSQL(s);
			}
			
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	
	

	public boolean adicionarExercicioAoTreinamento(Banco b, int idExercicioAerobico, int idExercicioAnaerobico, int idTreinamento){
		/*
		 codExercicioAerobico INTEGER,
		 codExercicioAnaerobico INTEGER, 
		 codTreinamento INTEGER, 
		 FOREIGN KEY(codExercicioAerobico) references Aerobico(codExercicioAerobico), 
		 FOREIGN KEY(codExercicioAnaerobico) references Anaerobico(codExercicioAnaerobico),
		 FOREIGN KEY(codTreinamento) references Treinamento(codTreinamento))	;"
	
		CREATE TABLE Treinamento (
			codTreinamento INTEGER PRIMARY KEY AUTOINCREMENT,
			nomeTreinamento TEXT,
			usuarioPersonal TEXT);*/
		String s = null;
		try{
		if (idExercicioAnaerobico == -1){
			s = "INSERT INTO treinamentoTemExercicios(codAerobico,codTreinamento)" +
							"VALUES (" + idExercicioAerobico + ", " + idTreinamento + ");";
							
		}else if ( idExercicioAerobico == -1){
			s = "INSERT INTO treinamentoTemExercicios(codAnaerobico,codTreinamento)" +
					"VALUES (" + idExercicioAnaerobico + ", " + idTreinamento + ");";
		}
			b.execSQL(s);
		//	Log.i("inserir no treinamento", s);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean removerExercicioDoTreinamento(Banco b, int idExercicio, int idTreinamento){
		/*
		 codExercicioAerobico INTEGER,
		 codExercicioAnaerobico INTEGER, 
		 codTreinamento INTEGER, 
		 FOREIGN KEY(codExercicioAerobico) references Aerobico(codExercicioAerobico), 
		 FOREIGN KEY(codExercicioAnaerobico) references Anaerobico(codExercicioAnaerobico),
		 FOREIGN KEY(codTreinamento) references Treinamento(codTreinamento))	;"
	
		CREATE TABLE Treinamento (
			codTreinamento INTEGER PRIMARY KEY AUTOINCREMENT,
			nomeTreinamento TEXT,
			usuarioPersonal TEXT);*/
		try{
			String SQL = "DELETE FROM treinamentoTemExercicios where "
					+ "(codTreinamento = " + idTreinamento + " and codAerobico = " + idExercicio + ") "
							+ "or (codTreinamento = " + idTreinamento + " and codAnaerobico = " + idExercicio + ");";
			//Log.i("sql",SQL);
			Log.i("sql", SQL);
			b.execSQL(SQL);
			
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean removerTreinamento(Banco b, int idTreinamento){
		/*
		 codExercicioAerobico INTEGER,
		 codExercicioAnaerobico INTEGER, 
		 codTreinamento INTEGER, 
		 FOREIGN KEY(codExercicioAerobico) references Aerobico(codExercicioAerobico), 
		 FOREIGN KEY(codExercicioAnaerobico) references Anaerobico(codExercicioAnaerobico),
		 FOREIGN KEY(codTreinamento) references Treinamento(codTreinamento))	;"
	
		CREATE TABLE Treinamento (
			codTreinamento INTEGER PRIMARY KEY AUTOINCREMENT,
			nomeTreinamento TEXT,
			usuarioPersonal TEXT);*/
		try{
			String SQL = "DELETE FROM treinamentoTemExercicios where "
					+ "(codTreinamento = " + codTreinamento + ");";
			//Log.i("sql",SQL);
			b.execSQL(SQL);
			
			SQL = "UPDATE Treinamento set ativo = 'desativado' where codTreinamento = " + idTreinamento;
			b.execSQL(SQL);
			return true;
		}catch(Exception ex){
			return false;
		}
	}
	
	
	//BUSCAS local
	public int buscarUltimoTreinamento(Banco b){
		
		/*
		 * CREATE TABLE Treinamento (
			 * codTreinamento INTEGER PRIMARY KEY AUTOINCREMENT,
			 * nomeTreinamento TEXT,
			 * telefonePersonal INTEGER */
		
		String SQL = "SELECT last_insert_rowid() FROM Treinamento";
	
		
		Cursor dataset = b.querySQL(SQL);
		
		
		
		
		int numRows = dataset.getCount();
		
		//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
		dataset.moveToFirst();
		
		if(numRows > 0 ){
			
			int codTreinamento = dataset.getInt(0);
			return codTreinamento;
		}
		return -1;
	}
	
	public ArrayList<Aerobico> buscarExerciciosAerobicoPorTreinamento(Banco b, int codTreino){
	
		/*
		 * 
		 * CREATE TABLE Aerobico (
		 * 	   codExercicioAerobico INTEGER PRIMARY KEY AUTOINCREMENT,
			 * nomeExercicio TEXT,
			 * duracaoExercicio TEXT,
			 * descansoExercicio TEXT, 
			 * descricaoExercicio TEXT, 
			 * usuarioPersonal TEXT);";
	
			CREATE TABLE Anaerobico (
				codExercicioAnaerobico INTEGER PRIMARY KEY AUTOINCREMENT,
				nomeExercicio TEXT, 
				descansoExercicio TEXT ,
				repeticoesExercicio INTEGER,
				descricaoExercicio TEXT, 
				usuarioPersonal TEXT);";
				
			CREATE TABLE treinamentoTemExercicios (
				codExercicioAerobico INTEGER,
				codExercicioAnaerobico INTEGER,
				codTreinamento INTEGER, 
				FOREIGN KEY(codExercicioAerobico) references Aerobico(codExercicioAerobico), 
				FOREIGN KEY(codExercicioAnaerobico) references Anaerobico(codExercicioAnaerobico),
				FOREIGN KEY(codTreinamento) references Treinamento(codTreinamento))	;";
	
			CREATE TABLE Treinamento (
				codTreinamento INTEGER PRIMARY KEY AUTOINCREMENT,
				nomeTreinamento TEXT NOT NULL,
				usuarioPersonal TEXT);";
		 
			;*/
		
		ArrayList<Aerobico> busca = new ArrayList<Aerobico>();
		
		String SQL = "SELECT codExercicioAerobico,nomeExercicio,duracaoExercicio," +
				"descansoExercicio,descricaoExercicio,usuarioPersonal FROM Aerobico," +
				"treinamentoTemExercicios where treinamentoTemExercicios.codTreinamento = " + codTreino + 
				" and Aerobico.codExercicioAerobico = treinamentoTemExercicios.codAerobico";
	
		
		Cursor dataset = b.querySQL(SQL);
		
		int col_codExercicioAerobico = dataset.getColumnIndex("codExercicioAerobico");
		int col_nomeExercicio = dataset.getColumnIndex("nomeExercicio");
		int col_duracaoExercicio = dataset.getColumnIndex("duracaoExercicio");
		int col_descansoExercicio = dataset.getColumnIndex("descansoExercicio");
		int col_descricaoExercicio = dataset.getColumnIndex("descricaoExercicio");
	
	
		
		int numRows = dataset.getCount();
		
		
		dataset.moveToFirst();
		
		for(int c=0; c<numRows; c++) {
			int codExercicioAerobico = dataset.getInt(col_codExercicioAerobico);
			String duracaoExercicio = dataset.getString(col_duracaoExercicio);
			String nomeExercicio = dataset.getString(col_nomeExercicio);
			String descansoExercicio = dataset.getString(col_descansoExercicio);
			String descricaoExercicio = dataset.getString(col_descricaoExercicio);
			String usuarioP = usuarioPersonal;
				
			
			
			Aerobico a = new Aerobico(codExercicioAerobico,nomeExercicio,descricaoExercicio,
					duracaoExercicio,descansoExercicio,usuarioP);
			
			dataset.moveToNext();
			busca.add(a);
		}
		
		return busca;
		
	}
	
	public ArrayList<Anaerobico> buscarExerciciosAnaerobicoPorTreinamento(Banco b, int codTreino){
			/*CREATE TABLE Anaerobico (
			codExercicioAnaerobico INTEGER PRIMARY KEY AUTOINCREMENT,
			nomeExercicio TEXT, 
			descansoExercicio TEXT ,
			repeticoesExercicio INTEGER,
			descricaoExercicio TEXT, 
			usuarioPersonal TEXT);";
			
		CREATE TABLE treinamentoTemExercicios (
			codExercicioAerobico INTEGER,
			codExercicioAnaerobico INTEGER,
			codTreinamento INTEGER, 
			FOREIGN KEY(codExercicioAerobico) references Aerobico(codExercicioAerobico), 
			FOREIGN KEY(codExercicioAnaerobico) references Anaerobico(codExercicioAnaerobico),
			FOREIGN KEY(codTreinamento) references Treinamento(codTreinamento))	;";*/
		
		ArrayList<Anaerobico> busca = new ArrayList<Anaerobico>();
		
	
		String SQL2 = "SELECT codExercicioAnaerobico,nomeExercicio,descansoExercicio," +
			"repeticoesExercicio,descricaoExercicio,usuarioPersonal FROM Anaerobico," +
			"treinamentoTemExercicios where treinamentoTemExercicios.codTreinamento = " + codTreino + 
			" and Anaerobico.codExercicioAnaerobico = treinamentoTemExercicios.codAnaerobico";
	
		Cursor dataset2 = b.querySQL(SQL2);
		
		int col_codExercicioAnaerobico = dataset2.getColumnIndex("codExercicioAnaerobico");
		int col_nomeExercicio = dataset2.getColumnIndex("nomeExercicio");
		int col_descansoExercicio = dataset2.getColumnIndex("descansoExercicio");
		int col_repeticoesExercicio = dataset2.getColumnIndex("repeticoesExercicio");
		int col_descricaoExercicio = dataset2.getColumnIndex("descricaoExercicio");
		int col_usuarioPersonal = dataset2.getColumnIndex("usuarioPersonal");
		
	
	
	
		int numRows = dataset2.getCount();
		
		dataset2.moveToFirst();
		
		for(int c=0; c<numRows; c++) {
		int codExercicioAnaerobico = dataset2.getInt(col_codExercicioAnaerobico);
		String nomeExercicio = dataset2.getString(col_nomeExercicio);
		String descansoExercicio = dataset2.getString(col_descansoExercicio);
		String repeticoesExercicio = dataset2.getString(col_repeticoesExercicio);
		String descricaoExercicio = dataset2.getString(col_descricaoExercicio);
		String usuarioP = dataset2.getString(col_usuarioPersonal);
			
		
		
		Anaerobico a = new Anaerobico(codExercicioAnaerobico,nomeExercicio,descricaoExercicio,
				descansoExercicio,repeticoesExercicio,usuarioP);
		
		dataset2.moveToNext();
		busca.add(a);
		}
		return busca;
	}
	
	
	public int buscarNumeroDeExerciciosPorTreinamento(Banco b, int codTreino){
		
		/*
		 * 
		 * CREATE TABLE Aerobico (
		 * 	   codExercicioAerobico INTEGER PRIMARY KEY AUTOINCREMENT,
			 * nomeExercicio TEXT,
			 * duracaoExercicio TEXT,
			 * descansoExercicio TEXT, 
			 * descricaoExercicio TEXT, 
			 * usuarioPersonal TEXT);";
	
			CREATE TABLE Anaerobico (
				codExercicioAnaerobico INTEGER PRIMARY KEY AUTOINCREMENT,
				nomeExercicio TEXT, 
				descansoExercicio TEXT ,
				repeticoesExercicio INTEGER,
				descricaoExercicio TEXT, 
				usuarioPersonal TEXT);";
				
			CREATE TABLE treinamentoTemExercicios (
				codExercicioAerobico INTEGER,
				codExercicioAnaerobico INTEGER,
				codTreinamento INTEGER, 
				FOREIGN KEY(codExercicioAerobico) references Aerobico(codExercicioAerobico), 
				FOREIGN KEY(codExercicioAnaerobico) references Anaerobico(codExercicioAnaerobico),
				FOREIGN KEY(codTreinamento) references Treinamento(codTreinamento))	;";
	
			CREATE TABLE Treinamento (
				codTreinamento INTEGER PRIMARY KEY AUTOINCREMENT,
				nomeTreinamento TEXT NOT NULL,
				usuarioPersonal TEXT);";
		 
			;*/
		
		ArrayList<Aerobico> busca = new ArrayList<Aerobico>();
		
		String SQL = "SELECT codExercicioAerobico,nomeExercicio,duracaoExercicio," +
				"descansoExercicio,descricaoExercicio,usuarioPersonal FROM Aerobico," +
				"treinamentoTemExercicios where treinamentoTemExercicios.codTreinamento = " + codTreino + 
				" and Aerobico.codExercicioAerobico = treinamentoTemExercicios.codAerobico";
	
		
		Cursor dataset = b.querySQL(SQL);
		
		
		int numRowsExerciciosAerobicos = dataset.getCount();
		
		

		String SQL2 = "SELECT codExercicioAnaerobico,nomeExercicio,descansoExercicio," +
			"repeticoesExercicio,descricaoExercicio,usuarioPersonal FROM Anaerobico," +
			"treinamentoTemExercicios where treinamentoTemExercicios.codTreinamento = " + codTreino + 
			" and Anaerobico.codExercicioAnaerobico = treinamentoTemExercicios.codAnaerobico";
	
		Cursor dataset2 = b.querySQL(SQL2);
		
		
		int numRowsExerciciosAnaerobicos = dataset2.getCount();
		
		int retorno = numRowsExerciciosAerobicos + numRowsExerciciosAnaerobicos;
		
		return retorno;
		
	}
	
	
	public Treinamento buscarTreinamentoPorId(Banco b, int codTreino){
		
		
		
		String SQL = "SELECT * FROM Treinamento where codTreinamento = " + codTreino ;
		
		
		Cursor dataset = b.querySQL(SQL);
		
		
		
		int col_codTreinamento = dataset.getColumnIndex("codTreinamento");
		int col_nomeTreinamento = dataset.getColumnIndex("nomeTreinamento");
		int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
		
		
		int numRows = dataset.getCount();
		
		//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
		dataset.moveToFirst();
		
		if(numRows > 0 ){
			
			int codTreinamento = dataset.getInt(col_codTreinamento);
			String nmTreinamento = dataset.getString(col_nomeTreinamento);
			String usuarioPersonal = dataset.getString(col_usuarioPersonal);
			
			
			
			Treinamento t = new Treinamento(codTreinamento,nmTreinamento,null,null,
					usuarioPersonal,null);
			
			dataset.moveToNext();
			return t;
		}
		return null;
	}

	public ArrayList<Treinamento> buscarTreinamentos(Banco b,String usuarioPersonal,String filtro){
	
	/*
	 * CREATE TABLE Treinamento (
		 * codTreinamento INTEGER PRIMARY KEY AUTOINCREMENT,
		 * nomeTreinamento TEXT,
		 * telefonePersonal INTEGER */
	
	String SQL = "SELECT * FROM Treinamento where usuarioPersonal = '" + usuarioPersonal + "' " +
			"and nomeTreinamento like '%" + filtro + "%'";
	ArrayList<Treinamento> treinamentos = new ArrayList<Treinamento>();
	
	Cursor dataset = b.querySQL(SQL);
	
	
	
	int col_codTreinamento = dataset.getColumnIndex("codTreinamento");
	int col_nomeTreinamento = dataset.getColumnIndex("nomeTreinamento");
	int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
	
	
	int numRows = dataset.getCount();
	
	//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
	dataset.moveToFirst();
	
	for(int c=0; c<numRows; c++) {
		
		int codTreinamento = dataset.getInt(col_codTreinamento);
		String nomeTreinamento = dataset.getString(col_nomeTreinamento);
		String usuarioP = dataset.getString(col_usuarioPersonal);
		
		
		
		treinamentos.add(new Treinamento(codTreinamento,nomeTreinamento,null,null,
				usuarioP,null));
		
		dataset.moveToNext();
		
	}
	return treinamentos;
}

	
	//CUD Web service
	
	public int salvarTreinamentoWeb(Banco b){
		clear();
		final Treinamento t = new Treinamento(
				codTreinamento,
				nomeTreinamento,
				null,null,
				usuarioPersonal,
				usuarioAluno);
		Log.i("treinamento dentro da classe no metodo salvarTreinamento web", t.toString());
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
		
				SoapObject request = new SoapObject(WebService.getNamespace(),"salvarTreinamento");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Treinamento");
				p1.setValue(t);
				p1.setType(new Treinamento().getClass());
				
				request.addProperty(p1);
				
				PropertyInfo p2 = new PropertyInfo();
				p2.setName("usuarioPersonal");
				p2.setValue(t.getUsuarioPersonal());
				p2.setType(new String().getClass());
				
				request.addProperty(p2);
			
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("salvarTreinamento"), envelope);
					
					 retornoLastId = Integer.parseInt(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: Salvar Treinamento Web", e.toString());
				}		
			}
		};
		
		threadWs.start();
		try {
			threadWs.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return retornoLastId;
		
		//Log.i("retorno do treinamento salvo na web", "ultimo id: "+  retornoLastId);
		
		//Debug
		
		/*for(Exercicio a : exercicios){
			if (a instanceof Aerobico){
				Log.i("exercicios aerobico no array", ((Aerobico) (a)).toString());
			}else{
				Log.i("exercicios anaerobico no array", ((Anaerobico) (a)).toString());
			}
		}
		
		for(Exercicio ex: exercicios){
			if (ex instanceof Aerobico){
				if(!new Treinamento().adicionarExercicioAoTreinamentoWeb(ex.getCodExercicio(), -1, retornoLastId)){
					flag = false;
				}
			}else if(ex instanceof Anaerobico){
				if(!new Treinamento().adicionarExercicioAoTreinamentoWeb(-1, ex.getCodExercicio(), retornoLastId)){
					flag = false;
				}
			}
		}
		if(flag == false){
			Log.i("Erro: AdicionarExerciciosAoTreinamento (Classe Treinamento)", "Ao menos 1 exercicio n��o foi adicionado corretamente.");		
		}
		
		return flag;*/
	}
	
	public boolean adicionarExercicioAoTreinamentoWeb(final int idExercicioAerobico, final int idExercicioAnaerobico, final int idTreinamento){
		clear();
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
		
				SoapObject request = new SoapObject(WebService.getNamespace(),"adicionarExercicioAoTreinamento");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("idExercicioAerobico");
				p1.setValue(idExercicioAerobico);
				p1.setType(int.class);
				
				request.addProperty(p1);
				
				PropertyInfo p2 = new PropertyInfo();
				p2.setName("idExercicioAnaerobico");
				p2.setValue(idExercicioAnaerobico);
				p2.setType(int.class);
				
				request.addProperty(p2);
				
				PropertyInfo p3 = new PropertyInfo();
				p3.setName("idTreinamento");
				p3.setValue(idTreinamento);
				p3.setType(int.class);
				
				request.addProperty(p3);
				
				Log.i("informacoes da request de treinamento", "idExercicioAerobico = " + idExercicioAerobico 
						+ " id exercicioAnaerobico  =  " + idExercicioAnaerobico + " idTreinamento = + " + idTreinamento); 
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("adicionarExercicioAoTreinamento"), envelope);
					
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: AdicionarExercicioAoTreinamentoWeb", e.toString());
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

	public boolean removerExercicioDoTreinamentoWeb(final int idExercicio,final  int idTreinamento){
		clear();
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
		
				SoapObject request = new SoapObject(WebService.getNamespace(),"removerExercicioDoTreinamento");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("codExercicio");
				p1.setValue(idExercicio);
				p1.setType(Integer.class);
				
				request.addProperty(p1);
				
				PropertyInfo p2 = new PropertyInfo();
				p2.setName("codTreinamento");
				p2.setValue(idTreinamento);
				p2.setType(Integer.class);
				
				request.addProperty(p2);
				
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("removerExercicioDoTreinamento"), envelope);
					
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: removerExercicioDoTreinamento", e.toString());
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
	
	public boolean removerTreinamentoWeb(int idTreinamento){
		clear();
		final Treinamento t = new Treinamento(
				idTreinamento,
				nomeTreinamento,
				null,null,
				usuarioPersonal,
				usuarioAluno);
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
		
				SoapObject request = new SoapObject(WebService.getNamespace(),"removerTreinamento");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Treinamento");
				p1.setValue(t);
				p1.setType(new Treinamento().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "Treinamento",new Personal().getClass());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("removerTreinamento"), envelope);
					
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: removerTreinamento", e.toString());
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
	
	public ArrayList<Aerobico> buscarExerciciosAerobicoPorTreinamentoWeb(int codTreino){
		clear();
		final ArrayList<Aerobico> retornoListaAerobico = new ArrayList<Aerobico>();
		final Treinamento t = new Treinamento();
		t.setCodTreinamento(codTreino);
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"buscarExerciciosAerobicoPorTreinamento");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Treinamento");
				p1.setValue(t);
				p1.setType(new Treinamento().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "Treinamento",new Treinamento().getClass());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("buscarExerciciosAerobicoPorTreinamento"), envelope);
					
					SoapObject res = new SoapObject();
					Vector<SoapObject> response = new Vector<SoapObject>();
					
					try{
						res = (SoapObject) envelope.getResponse();
						//Log.i("debug", "cheguei aki --- > " + res.toString());
						
								
					}catch(Exception e){
						Log.i("Erro: buscarExerciciosAerobicoPorTreinamento (UNICO) ", e.toString());
					}
					
					try{
						
						response = (Vector<SoapObject>) envelope.getResponse();
						//Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
						
					}catch(Exception e){
						Log.i("Erro: buscarExercicioAerobicoPorTreinamento (Vetor) ", e.toString());
					}

					if(response.isEmpty()){
						
						 	Aerobico item = getSoapAerobico(res);
					    //    Log.i("alunos", item.toString());
					        retornoListaAerobico.add(item);
					         
					}else{
						
						for(SoapObject soapPersonal: response){  
					         Aerobico item = getSoapAerobico(soapPersonal);
					       //  Log.i("alunos", item.toString());
					         retornoListaAerobico.add(item);			       
				           }
						
					}
				         
				}catch(Exception e){
					e.printStackTrace();
					Log.e("Erro: buscarExercicioAerobicoPorTreinamentoWeb", e.toString());
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
		return retornoListaAerobico;
	}
	
	public ArrayList<Anaerobico> buscarExerciciosAnaerobicoPorTreinamentoWeb(int codTreino){
		clear();
		final ArrayList<Anaerobico> retornoListaAnaerobico = new ArrayList<Anaerobico>();
		final Treinamento t = new Treinamento();
		t.setCodTreinamento(codTreino);
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"buscarExerciciosAnaerobicoPorTreinamento");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Treinamento");
				p1.setValue(t);
				p1.setType(new Treinamento().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "Treinamento",new Treinamento().getClass());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("buscarExerciciosAnaerobicoPorTreinamento"), envelope);
					
					SoapObject res = new SoapObject();
					Vector<SoapObject> response = new Vector<SoapObject>();
					
					try{
						res = (SoapObject) envelope.getResponse();
						//Log.i("debug", "cheguei aki --- > " + res.toString());
						
								
					}catch(Exception e){
						Log.i("Erro: buscarExerciciosAnaerobicoPorTreinamento", e.toString());
					}
					
					try{
						
						response = (Vector<SoapObject>) envelope.getResponse();
						//Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
						
					}catch(Exception e){
						Log.i("Erro: buscarExerciciosAnaerobicoPorTreinamento", e.toString());
					}

					if(response.isEmpty()){
						
						 	Anaerobico item = getSoapAnaerobico(res);
					      //  Log.i("alunos", item.toString());
					        retornoListaAnaerobico.add(item);
					         
					}else{
						
						for(SoapObject soapPersonal: response){  
					         Anaerobico item = getSoapAnaerobico(soapPersonal);
					        // Log.i("alunos", item.toString());
					         retornoListaAnaerobico.add(item);			       
				           }
						
					}
				         
				}catch(Exception e){
					e.printStackTrace();
					Log.e("Erro: buscarExerciciosAnaerobicoPorTreinamento", e.toString());
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
		return retornoListaAnaerobico;
	}
	
	public Treinamento buscarTreinamentoPorIdWeb(int codTreino){
		clear();

		final Treinamento t = new Treinamento();
		t.setCodTreinamento(codTreino);
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
			
				SoapObject request = new SoapObject(WebService.getNamespace(),"buscarTreinamentoPorId");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Treinamento");
				p1.setValue(t);
				p1.setType(new Treinamento().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				try{
					ht.call(WebService.getSoapAction("buscarTreinamentoPorId"), envelope);
					
					SoapObject res = new SoapObject();
					
					try{
						res = (SoapObject) envelope.getResponse();
						//Log.i("debug", "cheguei aki --- > " + res.toString());
						
								
					}catch(Exception e){
						Log.i("Erro: buscarTreinamentoPorId", e.toString());
					}
			
					retornoTreinamento = getSoapTreinamento(res);
			             
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: buscarTreinamentoPorId", e.toString());
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
		return retornoTreinamento;
	}
	
	public ArrayList<Treinamento> buscarTreinamentosWeb(String usuarioPersonal,String filtro){
		clear();
		final ArrayList<Treinamento> retornoListaTreinamento = new ArrayList<Treinamento>();
		final Treinamento t = new Treinamento();
		t.setUsuarioPersonal(usuarioPersonal);
		t.setUsuarioAluno(filtro);
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"buscarTreinamentos");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Treinamento");
				p1.setValue(t);
				p1.setType(new Treinamento().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("buscarTreinamentos"), envelope);
					
					SoapObject res = new SoapObject();
					Vector<SoapObject> response = new Vector<SoapObject>();
					
					try{
						res = (SoapObject) envelope.getResponse();
						//Log.i("debug", "cheguei aki --- > " + res.toString());
							
					}catch(Exception e){
						Log.i("Erro: buscarTreinamentos (UNICO) ", e.toString());
					}
					
					try{
						response = (Vector<SoapObject>) envelope.getResponse();
						//Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
						
					}catch(Exception e){
						Log.i("Erro: buscarTreinamentos (Vetor) ", e.toString());
					}

					if(response.isEmpty()){
						
						 	Treinamento item = getSoapTreinamento(res);
					        //Log.i("alunos", item.toString());
					        retornoListaTreinamento.add(item);
					         
					}else{
						
						for(SoapObject soapPersonal: response){  
					         Treinamento item = getSoapTreinamento(soapPersonal);
					        // Log.i("alunos", item.toString());
					         retornoListaTreinamento.add(item);			       
				           }
						
					}
				         
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: buscarTreinamentos", e.toString());
				}		
			}
		};
		
		threadWs.start();
		try {
			threadWs.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		

		return retornoListaTreinamento;
	}
	
	
	//clean
	public void clear(){
		retorno = false;
		retornoLastId = -1;
		retornoListaAerobico.clear();
		retornoListaAnaerobico.clear();
		retornoListaTreinamento.clear();
		retornoTreinamento = new Treinamento();
		
	}
	
	
	//Gets e sets
	
	public Aerobico getSoapAerobico(SoapObject res){
		Aerobico item = new Aerobico();
		 if (res.hasProperty("codExercicio")) {
       	 item.setCodExercicio(Integer.parseInt(res.getPropertyAsString("codExercicio")));
        }
        if (res.hasProperty("nomeExercicio")) {
       	 item.setNomeExercicio(res.getPropertyAsString("nomeExercicio"));
        }
        if (res.hasProperty("descansoExercicio")) {
       	 item.setDescansoExercicio(res.getPropertyAsString("descansoExercicio"));
        }
        if (res.hasProperty("duracaoExercicio")) {
       	 item.setDuracaoExercicio(res.getPropertyAsString("duracaoExercicio"));
        }
        if (res.hasProperty("descricaoExercicio")) {
       	 item.setDescricaoExercicio(res.getPropertyAsString("descricaoExercicio"));
        }
        if (res.hasProperty("usuarioPersonal")) {
       	 item.setUsuarioPersonal(res.getPropertyAsString("usuarioPersonal"));
        }
        
        return item;
	}
	public Anaerobico getSoapAnaerobico(SoapObject res){
		
		Anaerobico item = new Anaerobico();
		 if (res.hasProperty("codExercicio")) {
        	 item.setCodExercicio(Integer.parseInt(res.getPropertyAsString("codExercicio")));
         }
         if (res.hasProperty("nomeExercicio")) {
        	 item.setNomeExercicio(res.getPropertyAsString("nomeExercicio"));
         }
         if (res.hasProperty("descansoExercicio")) {
        	 item.setDescansoExercicio(res.getPropertyAsString("descansoExercicio"));
         }
         if (res.hasProperty("repeticoesExercicio")) {
        	 item.setRepeticoesExercicio(res.getPropertyAsString("repeticoesExercicio"));
         }
         if (res.hasProperty("descricaoExercicio")) {
        	 item.setDescricaoExercicio(res.getPropertyAsString("descricaoExercicio"));
         }
         if (res.hasProperty("usuarioPersonal")) {
        	 item.setUsuarioPersonal(res.getPropertyAsString("usuarioPersonal"));
         }
         
         return item;
	}
	public Treinamento getSoapTreinamento(SoapObject res){
		Treinamento item = new Treinamento();
		
		if (res.hasProperty("codTreinamento")) {
       	 item.setCodTreinamento(Integer.parseInt(res.getPropertyAsString("codTreinamento")));
        }
		if (res.hasProperty("nomeTreinamento")) {
       	 item.setNomeTreinamento(res.getPropertyAsString("nomeTreinamento"));
        }
		if (res.hasProperty("usuarioPersonal")) {
       	 item.setUsuarioPersonal(res.getPropertyAsString("usuarioPersonal"));
        }
		if (res.hasProperty("usuarioAluno")) {
       	 item.setUsuarioAluno(res.getPropertyAsString("usuarioAluno"));
        }
		
		return item;
	}
	
	
	public int getCodTreinamento() {
		return codTreinamento;
	}
	public void setCodTreinamento(int codTreinamento) {
		this.codTreinamento = codTreinamento;
	}
	public String getNomeTreinamento() {
		return nomeTreinamento;
	}
	public void setNomeTreinamento(String nomeTreinamento) {
		this.nomeTreinamento = nomeTreinamento;
	}
	public ArrayList<Aerobico> getExerciciosAerobicos() {
		return exerciciosAerobicos;
	}
	public void setExerciciosAerobicos(ArrayList<Aerobico> exercicios) {
		this.exerciciosAerobicos = new ArrayList<Aerobico>();
		for(Aerobico x : exercicios){
			this.exerciciosAerobicos.add(x);
		}
	}
	public ArrayList<Anaerobico> getExerciciosAnaerobicos() {
		return exerciciosAnaerobicos;
	}
	public void setExerciciosAnaerobicos(ArrayList<Anaerobico> exercicios) {
		this.exerciciosAnaerobicos = new ArrayList<Anaerobico>();
		for(Anaerobico x : exercicios){
			this.exerciciosAnaerobicos.add(x);
		}
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
	
	
	//KVM Serialization
@Override
	public Object getProperty(int arg0) {
		switch(arg0){
	    case 0:
	        return codTreinamento;
	    case 1:
	        return nomeTreinamento;
	    case 2:
	    	return usuarioPersonal;
	    case 3:
	       	return usuarioAluno;
	    default: 
	    	return null;
	    		
	    }
	}
	
	@Override
	public int getPropertyCount() {
		
		return 4;
	}
	
	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		switch(arg0)
	    {
	    case 0:
	        arg2.type = PropertyInfo.INTEGER_CLASS;
	        arg2.name = "codTreinamento";
	        break;
	    case 1:
	        arg2.type = PropertyInfo.STRING_CLASS;
	        arg2.name = "nomeTreinamento";
	        break;
	    case 2:
	        arg2.type = PropertyInfo.STRING_CLASS;
	        arg2.name = "usuarioPersonal";
	        break;
	    case 3:
	        arg2.type = PropertyInfo.STRING_CLASS;
	        arg2.name = "usuarioAluno";
	        break;
	    default:
	    	break;
	    }
		
		
	}

	@Override
	public String getInnerText() {
		return null;
	}

	@Override
	public void setInnerText(String s) {

	}

	@Override
	public void setProperty(int arg0, Object arg1) {
	switch(arg0){
    case 0:
       codTreinamento = Integer.parseInt(arg1.toString());
        break;
    case 1:
    	nomeTreinamento = arg1.toString();
        break;
    case 2:
        usuarioPersonal = arg1.toString();
        break;
    case 3:
       usuarioAluno = arg1.toString();
        break;
    default:
        break;
    }
	
	}
	
}
