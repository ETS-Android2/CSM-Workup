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
import android.util.Base64;
import android.util.Log;
import br.com.Banco.Banco;
import br.com.Utilitarios.MarshalDouble;
import br.com.Utilitarios.WebService;

public class GorduraCorporal implements KvmSerializable {

	
	private int codAvaliacao;
	private String sexo;
	private double peso;
	private double altura;
	private int idade;
	private double dobraAbdominal;
	private double dobraCoxa;
	private double dobraPeito;
	private double dobraSuprailiaca;
	private double dobraSubscapular;
	private double dobraTriceps;
	private double dobraLinhaAxilarMedia;
	private double dobraPanturrilha;
	private double resultadoAvaliacao;
	
	//atributos auxiliares
	
	private boolean retorno;
	public GorduraCorporal gorduraCorporal;
	
	
	@Override
	public String toString() {
		return "GorduraCorporal [codAvaliacao=" + codAvaliacao + ", sexo="
				+ sexo + ", peso=" + peso + ", altura=" + altura + ", idade="
				+ idade + ", dobraAbdominal=" + dobraAbdominal + ", dobraCoxa="
				+ dobraCoxa + ", dobraPeito=" + dobraPeito
				+ ", dobraSuprailiaca=" + dobraSuprailiaca
				+ ", dobraSubscapular=" + dobraSubscapular + ", dobraTriceps="
				+ dobraTriceps + ", dobraLinhaAxilarMedia="
				+ dobraLinhaAxilarMedia + ", dobraPanturrilha="
				+ dobraPanturrilha + ", resultadoAvaliacao="
				+ resultadoAvaliacao + ", retorno=" + retorno
				+ ", gorduraCorporal=" + gorduraCorporal + "]";
	}

	//construtores
	public GorduraCorporal(){
			
	}
	
	public GorduraCorporal(int codAvaliacao, String sexo, double peso,
			double altura,int idade, double dobraAbdominal, double dobraCoxa,
			double dobraPeito, double dobraSuprailiaca,
			double dobraSubscapular, double dobraTriceps,
			double dobraLinhaAxilarMedia,double dobraPanturrilha, double resultadoAvaliacao) {
		super();
		this.codAvaliacao = codAvaliacao;
		this.sexo = sexo;
		this.peso = peso;
		this.altura = altura;
		this.idade = idade;
		this.dobraAbdominal = dobraAbdominal;
		this.dobraCoxa = dobraCoxa;
		this.dobraPeito = dobraPeito;
		this.dobraSuprailiaca = dobraSuprailiaca;
		this.dobraSubscapular = dobraSubscapular;
		this.dobraTriceps = dobraTriceps;
		this.dobraLinhaAxilarMedia = dobraLinhaAxilarMedia;
		this.dobraPanturrilha = dobraPanturrilha;
		this.resultadoAvaliacao = resultadoAvaliacao;
	}
	
	//CUD local
	public boolean salvarGorduraCorporal(Banco b){
		
		String SQL = "INSERT INTO gorduraCorporal ("
				+ "codAvaliacao,"
				+ "sexo,"
				+ "peso,"
				+ "altura,"
				+ "idade,"
				+ "dobraAbdominal, "
				+ "dobraCoxa,"
				+ "dobraPeito,"
				+ "dobraSuprailiaca,"
				+ "dobraSubscapular,"
				+ "dobraTriceps, "
				+ "dobraLinhaAxilarMedia,"
				+ "dobraPanturrilha,"
				+ "resultadoAvaliacao) VALUES ("
				+ codAvaliacao + ",'"
				+ sexo + "'," 
				+ peso + ","
				+ altura + ","
				+ idade + ","
				+ dobraAbdominal + ","
				+ dobraCoxa + ","
				+ dobraPeito + ","
				+ dobraSuprailiaca + ","
				+ dobraSubscapular + ","
				+ dobraTriceps + ", "
				+ dobraLinhaAxilarMedia + ","
				+ dobraPanturrilha + ","
				+ resultadoAvaliacao + ");"; 
				
				
		try{
			//Log.i("SQL de salvar avalia����o", SQL);
			b.execSQL(SQL);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		
	}
	
	public boolean editarGorduraCorporal(Banco b){
		
		String SQL = "UPDATE GorduraCorporal set"
				+ " sexo = '" + sexo + "', "
				+ " peso = " + peso + ","
				+ " altura = " + altura + ","
				+ " idade = " + idade + ","
				+ " dobraAbdominal = " + dobraAbdominal + ","
				+ " dobraCoxa = " + dobraCoxa + ","
				+ " dobraPeito = " + dobraPeito + ","
				+ " dobraSuprailiaca = " + dobraSuprailiaca + ","
				+ " dobraSubscapular = " + dobraSubscapular + ","
				+ " dobraTriceps = " + dobraTriceps + ","
				+ " dobraLinhaAxilarMedia = " + dobraLinhaAxilarMedia + ","
				+ " dobraPanturrilha = " + dobraPanturrilha + ","
				+ " resultadoAvaliacao = " + resultadoAvaliacao +
				" where codAvaliacao = " + codAvaliacao + ";";	
				
		try{
			//Log.i("SQL de salvar avalia����o", SQL);
			b.execSQL(SQL);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		
	}
	
	
public GorduraCorporal buscarGorduraCorporalPorId(Banco b, int codAvaliacao){
		
		
		String SQL = "SELECT * FROM GorduraCorporal where codAvaliacao = " + codAvaliacao ;
		
		
		Cursor dataset = b.querySQL(SQL);

		
		
		int col_sexo = dataset.getColumnIndex("sexo");
		int col_peso = dataset.getColumnIndex("peso");
		int col_altura = dataset.getColumnIndex("altura");
		int col_idade = dataset.getColumnIndex("idade");
		int col_dobraAbdominal = dataset.getColumnIndex("dobraAbdominal");
		int col_dobraCoxa = dataset.getColumnIndex("dobraCoxa");
		int col_dobraPeito = dataset.getColumnIndex("dobraPeito");
		int col_dobraSuprailiaca = dataset.getColumnIndex("dobraSuprailiaca");
		int col_dobraSubscapular = dataset.getColumnIndex("dobraSubscapular");
		int col_dobraTriceps = dataset.getColumnIndex("dobraTriceps");
		int col_dobraLinhaAxilarMedia = dataset.getColumnIndex("dobraLinhaAxilarMedia");
		int col_dobraPanturrilha = dataset.getColumnIndex("dobraPanturrilha");
		int col_resultadoAvaliacao = dataset.getColumnIndex("resultadoAvaliacao");
		
		int numRows = dataset.getCount();
		
		//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
		dataset.moveToFirst();
		
		if(dataset.getCount() > 0){
			GorduraCorporal a = new GorduraCorporal();
			a.setCodAvaliacao(codAvaliacao);
			a.setSexo(dataset.getString(col_sexo));
			a.setPeso(dataset.getDouble(col_peso));
			a.setAltura(dataset.getDouble(col_altura));
			a.setIdade(dataset.getInt(col_idade));
			a.setDobraAbdominal(dataset.getDouble(col_dobraAbdominal));
			a.setDobraCoxa(dataset.getDouble(col_dobraCoxa));
			a.setDobraPeito(dataset.getDouble(col_dobraPeito));
			a.setDobraSuprailiaca(dataset.getDouble(col_dobraSuprailiaca));
			a.setDobraSubscapular(dataset.getDouble(col_dobraSubscapular));			
			a.setDobraTriceps(dataset.getDouble(col_dobraTriceps));
			a.setDobraLinhaAxilarMedia(dataset.getDouble(col_dobraLinhaAxilarMedia));
			a.setDobraPanturrilha(dataset.getDouble(col_dobraPanturrilha));
			a.setResultadoAvaliacao(dataset.getDouble(col_resultadoAvaliacao));
			
			return a;
		}
			
		return null;
	}
	
	
	
	//CUD Web Service
	public boolean salvarGorduraCorporalWeb(){
		clear();
		final GorduraCorporal a = new GorduraCorporal(codAvaliacao,sexo,peso,altura,idade,dobraAbdominal,dobraCoxa,
				dobraPeito,dobraSuprailiaca,dobraSubscapular,dobraTriceps,dobraLinhaAxilarMedia,dobraPanturrilha,resultadoAvaliacao);
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"salvarGorduraCorporal");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("GorduraCorporal");
				p1.setValue(a);
				p1.setType(new GorduraCorporal().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "GorduraCorporal",new Avaliacoes().getClass());
				MarshalDouble marshaldDouble = new MarshalDouble();
				marshaldDouble.register(envelope);
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("GorduraCorporal"), envelope);
					
					 retorno = Boolean.parseBoolean(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: SalvarAvaliacoes", e.toString());
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
	
	public boolean editarGorduraCorporalWeb(){
		clear();
		final GorduraCorporal a = new GorduraCorporal(codAvaliacao,sexo,peso,altura,idade,dobraAbdominal,dobraCoxa,
				dobraPeito,dobraSuprailiaca,dobraSubscapular,dobraTriceps,dobraLinhaAxilarMedia,dobraPanturrilha,resultadoAvaliacao);
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"editarGorduraCorporal");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("GorduraCorporal");
				p1.setValue(a);
				p1.setType(new GorduraCorporal().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "GorduraCorporal",new Avaliacoes().getClass());
				MarshalDouble marshaldDouble = new MarshalDouble();
				marshaldDouble.register(envelope);
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("editarGorduraCorporal"), envelope);
					
					 retorno = Boolean.parseBoolean(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: EditarAvaliacoesWEB", e.toString());
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
	
	
	public GorduraCorporal buscarGorduraCorporalPorIdWeb(int codAvaliacao){
		final GorduraCorporal a = new GorduraCorporal();
		a.setCodAvaliacao(codAvaliacao);
		
				
				Thread threadWs = new Thread(){
					
					@Override		
					public void run(){
						
						
						
						SoapObject request = new SoapObject(WebService.getNamespace(),"buscarGorduraCorporalPorId");
						PropertyInfo p1 = new PropertyInfo();
						p1.setName("GorduraCorporal");
						p1.setValue(a);
						p1.setType(new GorduraCorporal().getClass());
						
						request.addProperty(p1);
						
			
						SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
						envelope.setOutputSoapObject(request);
						envelope.addMapping(WebService.getNamespace(), "GorduraCorporal",new Avaliacoes().getClass());
						MarshalDouble marshaldDouble = new MarshalDouble();
						marshaldDouble.register(envelope);
						HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
						
						
						try{
							ht.call(WebService.getSoapAction("buscarGorduraCorporalPorId"), envelope);
							
							SoapObject res = new SoapObject();
							
							try{
								res = (SoapObject) envelope.getResponse();
								//Log.i("debug", "cheguei aki --- > " + res.toString());
								
										
							}catch(Exception e){
								Log.i("Erro: buscarGorduraCorporal (UNICO) ", e.toString());
							}
					
							gorduraCorporal = getSoapGorduraCorporal(res);
					       // Log.i("debug aluno", retornoAvaliacoes.toString());
						         
						}catch(Exception e){
							//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
							Log.e("Erro: buscarGorduraCorporalporId ", e.toString());
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
				return gorduraCorporal;
	}
	
	public void clear(){
		retorno = false;
		gorduraCorporal = new GorduraCorporal();
	}
	
	public GorduraCorporal getSoapGorduraCorporal(SoapObject res){
		GorduraCorporal item = new GorduraCorporal();
		if (res.hasProperty("codAvaliacao")) {
            item.setCodAvaliacao(Integer.parseInt(res.getPropertyAsString("codAvaliacao")));
		 }
		if (res.hasProperty("sexo")) {
            item.setSexo(res.getPropertyAsString("sexo"));
		 }
		if (res.hasProperty("peso")) {
            item.setPeso(Double.parseDouble(res.getPropertyAsString("peso")));
		 }
		if (res.hasProperty("altura")) {
            item.setAltura(Double.parseDouble(res.getPropertyAsString("altura")));
		 }
		if (res.hasProperty("idade")) {
	            item.setIdade(Integer.parseInt(res.getPropertyAsString("idade")));
		}
		if (res.hasProperty("dobraAbdominal")) {
            item.setDobraAbdominal(Double.parseDouble(res.getPropertyAsString("dobraAbdominal")));
		 }
		if (res.hasProperty("dobraCoxa")) {
            item.setDobraCoxa(Double.parseDouble(res.getPropertyAsString("dobraCoxa")));
		 }
		if (res.hasProperty("dobraPeito")) {
            item.setDobraPeito(Double.parseDouble(res.getPropertyAsString("dobraPeito")));
		 }
		if (res.hasProperty("dobraSuprailiaca")) {
            item.setDobraSuprailiaca(Double.parseDouble(res.getPropertyAsString("dobraSuprailiaca")));
		 }
		if (res.hasProperty("dobraSubscapular")) {
            item.setDobraSubscapular(Double.parseDouble(res.getPropertyAsString("dobraSubscapular")));
		 }
		if (res.hasProperty("dobraTriceps")) {
            item.setDobraTriceps(Double.parseDouble(res.getPropertyAsString("dobraTriceps")));
		 }
		if (res.hasProperty("peso")) {
            item.setPeso(Double.parseDouble(res.getPropertyAsString("peso")));
		 }
		if (res.hasProperty("dobraLinhaAxilarMedia")) {
            item.setDobraLinhaAxilarMedia(Double.parseDouble(res.getPropertyAsString("dobraLinhaAxilarMedia")));
		 }
		if (res.hasProperty("dobraPanturrilha")) {
            item.setDobraPanturrilha(Double.parseDouble(res.getPropertyAsString("dobraPanturrilha")));
		 }
		if (res.hasProperty("resultadoAvaliacao")) {
            item.setResultadoAvaliacao(Double.parseDouble(res.getPropertyAsString("resultadoAvaliacao")));
		 }
		
		return item;
	}
	
	//GETs e Sets
	public int getCodAvaliacao() {
		return codAvaliacao;
	}
	public void setCodAvaliacao(int codAvaliacao) {
		this.codAvaliacao = codAvaliacao;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public double getAltura() {
		return altura;
	}
	public void setAltura(double altura) {
		this.altura = altura;
	}
	public double gerIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	public double getDobraAbdominal() {
		return dobraAbdominal;
	}
	public void setDobraAbdominal(double dobraAbdominal) {
		this.dobraAbdominal = dobraAbdominal;
	}
	public double getDobraCoxa() {
		return dobraCoxa;
	}
	public void setDobraCoxa(double dobraCoxa) {
		this.dobraCoxa = dobraCoxa;
	}
	public double getDobraPeito() {
		return dobraPeito;
	}
	public void setDobraPeito(double dobraPeito) {
		this.dobraPeito = dobraPeito;
	}
	public double getDobraSuprailiaca() {
		return dobraSuprailiaca;
	}
	public void setDobraSuprailiaca(double dobraSuprailiaca) {
		this.dobraSuprailiaca = dobraSuprailiaca;
	}
	public double getDobraSubscapular() {
		return dobraSubscapular;
	}
	public void setDobraSubscapular(double dobraSubscapular) {
		this.dobraSubscapular = dobraSubscapular;
	}
	public double getDobraTriceps() {
		return dobraTriceps;
	}
	public void setDobraTriceps(double dobraTriceps) {
		this.dobraTriceps = dobraTriceps;
	}
	public double getDobraLinhaAxilarMedia() {
		return dobraLinhaAxilarMedia;
	}
	public void setDobraLinhaAxilarMedia(double dobraLinhaAxilarMedia) {
		this.dobraLinhaAxilarMedia = dobraLinhaAxilarMedia;
	}
	public double getDobraPanturrilha() {
		return dobraPanturrilha;
	}
	public void setDobraPanturrilha(double dobraPanturrilha) {
		this.dobraPanturrilha = dobraPanturrilha;
	}
	public double getResultadoAvaliacao() {
		return resultadoAvaliacao;
	}
	public void setResultadoAvaliacao(double resultadoAvaliacao) {
		this.resultadoAvaliacao = resultadoAvaliacao;
	}
	
	
	//KVM 
	
	@Override
	public Object getProperty(int arg0) {
		switch(arg0){
        case 0:
            return codAvaliacao;
        case 1:
            return sexo;
        case 2:
         return peso;
        case 3:
        	return altura;
        case 4:
        	return idade;
        case 5:
           	return dobraAbdominal;
        case 6:
        	return dobraCoxa;
        case 7:
        	return dobraPeito;
        case 8: 
        	return dobraSuprailiaca;
        case 9: 
        	return dobraSubscapular;
        case 10: 
        	return dobraTriceps;
        case 11: 
        	return dobraLinhaAxilarMedia;
        case 12: 
        	return dobraPanturrilha;
        case 13: 
        	return resultadoAvaliacao;
        default: 
        	return null;
        		
        }
	}


	@Override
	public int getPropertyCount() {
		return 14;
	}



	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		switch(arg0){
        case 0:
            arg2.type = PropertyInfo.INTEGER_CLASS;
            arg2.name = "codAvaliacao";
            break;
        case 1:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "sexo";
            break;
        case 2:
            arg2.type = Double.class;
            arg2.name = "peso";
            break;
        case 3:
            arg2.type = Double.class;
            arg2.name = "altura";
            break;
        case 4:
            arg2.type = int.class;
            arg2.name = "idade";
            break;
        case 5:
            arg2.type = Double.class;
            arg2.name = "dobraAbdominal";
            break;
        case 6:
            arg2.type = Double.class;
            arg2.name = "dobraCoxa";
            break;
        case 7:
            arg2.type = Double.class;
            arg2.name = "dobraPeito";
            break;
        case 8:
            arg2.type = Double.class;
            arg2.name = "dobraSuprailiaca";
            break;
        case 9:
            arg2.type = Double.class;
            arg2.name = "dobraSubscapular";
            break;
        case 10:
            arg2.type = Double.class;
            arg2.name = "dobraTriceps";
            break;
        case 11:
            arg2.type = Double.class;
            arg2.name = "dobraLinhaAxilarMedia";
            break;
        case 12:
            arg2.type = Double.class;
            arg2.name = "dobraPanturrilha";
            break;
        case 13:
            arg2.type = Double.class;
            arg2.name = "resultadoAvaliacao";
            break;
            
        default:break;
        }
		
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		switch(arg0){
        case 0:
            setCodAvaliacao(Integer.parseInt(arg1.toString()));
            break;
        case 1:
        	setSexo(arg1.toString());
            break;
        case 2:
            setPeso(Double.parseDouble(arg1.toString()));
            break;
        case 3:
        	setAltura(Double.parseDouble(arg1.toString()));
            break;
        case 4:
        	setIdade(Integer.parseInt(arg1.toString()));
            break;
        case 5:
        	setDobraAbdominal(Double.parseDouble(arg1.toString()));
            break;
        case 6:
        	setDobraCoxa(Double.parseDouble(arg1.toString()));
            break;
        case 7:
        	setDobraPeito(Double.parseDouble(arg1.toString()));
            break;
        case 8:
        	setDobraSuprailiaca(Double.parseDouble(arg1.toString()));
            break;
        case 9:
        	setDobraSubscapular(Double.parseDouble(arg1.toString()));
            break;
        case 10:
        	setDobraTriceps(Double.parseDouble(arg1.toString()));
            break;
        case 11:
        	setDobraLinhaAxilarMedia(Double.parseDouble(arg1.toString()));
            break;
        case 12:
        	setDobraPanturrilha(Double.parseDouble(arg1.toString()));
            break;
        case 13:
        	setResultadoAvaliacao(Double.parseDouble(arg1.toString()));
            break;
        default:
            break;
        }
	}
	
}
