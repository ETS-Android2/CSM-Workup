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

public class ExercicioRealizado implements KvmSerializable{
	
	//Atributos da classe
	private int codExercicioRealizado;
	private String nomeExercicio;
	private String inicioExercicio;
	private String fimExercicio;
	private int duracaoExercicio;
	private String usuarioPersonal;
	private String usuarioAluno;
	private String tipoExercicio;
	private int codExercicio;
	private String ativo;
	private int codTreinamentoRealizado;

	//Atributos Auxiliares
	private static int retornoLastId;
	private static boolean retorno;
	private static ExercicioRealizado retornoExercicioRealizado;
	private static ArrayList<ExercicioRealizado> retornoListaExercicioRealizado = new ArrayList<ExercicioRealizado>();
	
	
	//Construtores
	public ExercicioRealizado(int codExercicioRealizado, String nomeExercicio,
			String inicioExercicio, String fimExercicio,int duracaoExercicio,
			String usuarioPersonal, String usuarioAluno, String tipoExercicio,int codExercicio,
			String ativo,int codTreinamentoRealizado) {
		super();
		this.codExercicioRealizado = codExercicioRealizado;
		this.nomeExercicio = nomeExercicio;
		this.inicioExercicio = inicioExercicio;
		this.fimExercicio = fimExercicio;
		this.duracaoExercicio = duracaoExercicio;
		this.usuarioPersonal = usuarioPersonal;
		this.usuarioAluno = usuarioAluno;
		this.tipoExercicio = tipoExercicio;
		this.codExercicio = codExercicio;
		this.ativo = ativo;
		this.codTreinamentoRealizado = codTreinamentoRealizado;
		
	}

	public ExercicioRealizado(){
		
	}
	
	
	//toString
	@Override
	public String toString() {
		return "ExercicioRealizado [codExercicioRealizado="
				+ codExercicioRealizado + ", nomeExercicio=" + nomeExercicio
				+ ", inicioExercicio=" + inicioExercicio + ", fimExercicio="
				+ fimExercicio + "duracaoExercicio = " + duracaoExercicio 
				+ ", usuarioPersonal=" + usuarioPersonal
				+ ", usuarioAluno=" + usuarioAluno + ", tipoExercicio="
				+ tipoExercicio + ", codExercicio=" + codExercicio + ", ativo="
				+ ativo + ", codTreinamentoRealizado="
				+ codTreinamentoRealizado + "]";
	}

	
	//CUD Local
	public boolean salvarExercicioRealizado(Banco b){
		try{
		
			String SQL = "INSERT INTO exercicioRealizado ("
					+ "codExercicioRealizado,"
					+ "nomeExercicio,"
					+ "inicioExercicio,"
					+ "fimExercicio,"
					+ "duracaoExercicio,"
					+ "usuarioPersonal,"
					+ "usuarioAluno,"
					+ "tipoExercicio,"
					+ "codExercicio,"
					+ "ativo,"
					+ "codTreinamentoRealizado) VALUES (" 
					+ codExercicioRealizado + ",'"
					+ nomeExercicio + "','"
					+ inicioExercicio + "', '" 
					+ fimExercicio + "', " 
					+ duracaoExercicio + ",'" 
					+ usuarioPersonal + "','" 
					+ usuarioAluno + "','" 
					+ tipoExercicio + "'," 
					+ codExercicio + ",'"
					+ ativo + "'," 
					+ codTreinamentoRealizado +  ");";

			b.execSQL(SQL);
			
			//codExercicioRealizado = buscarUltimoExercicioRealizado(b);

			String sqlQuebra =  "";
			if(tipoExercicio.equals("aerobico")){
				sqlQuebra = "INSERT INTO realizaExercicioAerobico VALUES (" + codExercicio + "," + 
						codExercicioRealizado + ");";
				
			}else if (tipoExercicio.equals("anaerobico")){
				sqlQuebra = "INSERT INTO realizaExercicioAnaerobico VALUES (" + codExercicio + "," + 
						codExercicioRealizado + ");";
				
			}
			
			b.execSQL(sqlQuebra);
			return true;
		}catch(Exception e ){
			Log.i("Erro: SalvarExercicioRealizado", e.toString());
			return false;
		}
	}
	
	

	
		
	//Buscas local
	
	public int buscarUltimoExercicioRealizado(Banco b){
			
			/*
			 * CREATE TABLE Treinamento (
				 * codTreinamento INTEGER PRIMARY KEY AUTOINCREMENT,
				 * nomeTreinamento TEXT,
				 * telefonePersonal INTEGER */
			
			String SQL = "SELECT last_insert_rowid() FROM ExercicioRealizado";
		
			
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
	
	
	//CUD Web Service
	
	public int salvarExercicioRealizadoPersonalWeb(){
		clear();
		final ExercicioRealizado e = new ExercicioRealizado(
				codExercicioRealizado,nomeExercicio,inicioExercicio,fimExercicio,duracaoExercicio,
				usuarioPersonal,usuarioAluno,tipoExercicio,codExercicio,
				ativo,codTreinamentoRealizado);
		
		
		//Log.i("exercicio dentro da classe", e.toString());
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"salvarExercicioRealizadoPersonal");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("ExercicioRealizado");
				p1.setValue(e);
				p1.setType(new ExercicioRealizado().getClass());
				
				request.addProperty(p1);
				
					
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				//ht.debug = true;
				
				try{
					ht.call(WebService.getSoapAction("salvarExercicioRealizadoPersonal"), envelope);
				//	Log.i("envelope", ht.requestDump);
					
					 retornoLastId = Integer.parseInt(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: salvarExercicioRealizado", e.toString());
				}		
			}
		};
		
		threadWs.start();
		try {
			threadWs.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return retornoLastId;
	}
	
	
	public int salvarExercicioRealizadoAlunoWeb(){
		clear();
		final ExercicioRealizado e = new ExercicioRealizado(
				codExercicioRealizado,nomeExercicio,inicioExercicio,fimExercicio,duracaoExercicio,
				usuarioPersonal,usuarioAluno,tipoExercicio,codExercicio,
				ativo,codTreinamentoRealizado);
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"salvarExercicioRealizadoAluno");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("ExercicioRealizado");
				p1.setValue(e);
				p1.setType(new ExercicioRealizado().getClass());
				
				request.addProperty(p1);
				
					
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("salvarExercicioRealizadoAluno"), envelope);
					
					 retornoLastId = Integer.parseInt(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: salvarExercicioRealizado", e.toString());
				}		
			}
		};
		
		threadWs.start();
		try {
			threadWs.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return retornoLastId;
	}
	
	
	//Buscas WebService
	
	public int buscarUltimoExercicioRealizadoPersonalWeb(final String usuarioPersonal){
		clear();
		
		final Personal p = new Personal();
		p.setUsuario(usuarioPersonal);
		
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"buscarUltimoExercicioRealizadoPersonal");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Personal");
				p1.setValue(p);
				p1.setType(new Personal().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("buscarUltimoExercicioRealizadoPersonal"), envelope);
					
					retornoLastId = Integer.parseInt(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: buscarUltimoExercicioRealizadoPersonal", e.toString());
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
	
	public int buscarUltimoExercicioRealizadoAlunoWeb(final String usuarioAluno){
		clear();
		
		final Aluno a = new Aluno();
		a.setUsuario(usuarioAluno);
		
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"buscarUltimoExercicioRealizadoAluno");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Aluno");
				p1.setValue(a);
				p1.setType(new Aluno().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("buscarUltimoExercicioRealizadoAluno"), envelope);
					
					retornoLastId = Integer.parseInt(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: buscarUltimoExercicioRealizadoAluno", e.toString());
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
	
	
	public void clear(){
		retorno = false;
		retornoExercicioRealizado = new ExercicioRealizado();
		retornoLastId = 0;
		retornoListaExercicioRealizado.clear();
	}
	
	//Gets e Sets
	
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
        	 item.setFimExercicio(res.getPropertyAsString("duracaoExercicio"));
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
	public int getCodExercicioRealizado() {
		return codExercicioRealizado;
	}
	public void setCodExercicioRealizado(int codExercicioRealizado) {
		this.codExercicioRealizado = codExercicioRealizado;
	}
	public String getNomeExercicio() {
		return nomeExercicio;
	}
	public void setNomeExercicio(String nomeExercicio) {
		this.nomeExercicio = nomeExercicio;
	}
	public String getInicioExercicio() {
		return inicioExercicio;
	}
	public void setInicioExercicio(String inicioExercicio) {
		this.inicioExercicio = inicioExercicio;
	}
	public String getFimExercicio() {
		return fimExercicio;
	}
	public void setFimExercicio(String fimExercicio) {
		this.fimExercicio = fimExercicio;
	}
	public int getDuracaoExercicio(){
		return duracaoExercicio;
	}
	public void setDuracaoExercicio(int duracaoExercicio){
		this.duracaoExercicio = duracaoExercicio;
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
	public String getTipoExercicio(){
		return tipoExercicio;
	}
	public void setTipoExercicio(String tipoExercicio){
		this.tipoExercicio = tipoExercicio;
	}
	public int getCodExercicio(){
		return codExercicio;
	}
	public void setCodExercicio(int codExercicio){
		this.codExercicio = codExercicio;
	}
	public String getAtivo() {
		return ativo;
	}
	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}
	public int getCodTreinamentoRealizado() {
		return codTreinamentoRealizado;
	}
	public void setCodTreinamentoRealizado(int codTreinamentoRealizado) {
	this.codTreinamentoRealizado = codTreinamentoRealizado;
	}


	
	//KVM Serialization
	
	
@Override
	public Object getProperty(int arg0) {
	switch(arg0){
    case 0:
        return codExercicioRealizado;
    case 1:
        return nomeExercicio;
    case 2:
     return inicioExercicio;
    case 3:
    	return fimExercicio;
    case 4:
    	return duracaoExercicio;
    case 5:
       	return usuarioPersonal;
    case 6:
    	return usuarioAluno;
    case 7:
    	return tipoExercicio;
    case 8:
    	return codExercicio;
    case 9:
    	return ativo;
    case 10:
    	return codTreinamentoRealizado;
    default: 
    		return null;
    		
    }
}

@Override
	public int getPropertyCount() {
	
	return 11;
}


@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
	switch(arg0)
    {
    case 0:
        arg2.type = PropertyInfo.INTEGER_CLASS;
        arg2.name = "codExercicioRealizado";
        break;
    case 1:
        arg2.type = PropertyInfo.STRING_CLASS;
        arg2.name = "nomeExercicio";
        break;
    case 2:
        arg2.type = PropertyInfo.STRING_CLASS;
        arg2.name = "inicioExercicio";
        break;
    case 3:
        arg2.type = PropertyInfo.STRING_CLASS;
        arg2.name = "fimExercicio";
        break;
    case 4:
        arg2.type = PropertyInfo.STRING_CLASS;
        arg2.name = "duracaoExercicio";
        break;
    case 5:
        arg2.type = PropertyInfo.STRING_CLASS;
        arg2.name = "usuarioPersonal";
        break;
    case 6:
        arg2.type = PropertyInfo.STRING_CLASS;
        arg2.name = "usuarioAluno";
        break;
    case 7:
        arg2.type = PropertyInfo.STRING_CLASS;
        arg2.name = "tipoExercicio";
        break;
    case 8:
        arg2.type = PropertyInfo.INTEGER_CLASS;
        arg2.name = "codExercicio";
        break;
    case 9:
        arg2.type = PropertyInfo.STRING_CLASS;
        arg2.name = "ativo";
        break;
    case 10:
        arg2.type = PropertyInfo.INTEGER_CLASS;
        arg2.name = "codTreinamentoRealizado";
        break;
    default:break;
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
       codExercicioRealizado = Integer.parseInt(arg1.toString());
        break;
    case 1:
    	nomeExercicio = arg1.toString();
        break;
    case 2:
        inicioExercicio = arg1.toString();
        break;
    case 3:
        fimExercicio = arg1.toString();
        break;
    case 4:
        duracaoExercicio = Integer.parseInt(arg1.toString());
        break;
    case 5:
       usuarioPersonal = arg1.toString();
        break;
    case 6:
        usuarioAluno = arg1.toString();
        break;
    case 7:
        tipoExercicio = arg1.toString();
        break;
    case 8:
    	codExercicio = Integer.parseInt(arg1.toString());
        break;
    case 9:
    	ativo = arg1.toString();
        break;
    case 10:
    	codTreinamentoRealizado = Integer.parseInt(arg1.toString());
        break;
    default:
        break;
    }
	
	}


}
