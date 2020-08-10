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
import br.com.Utilitarios.MarshalDouble;
import br.com.Utilitarios.WebService;

public class Perimetria implements KvmSerializable{

	private int codAvaliacao;
	private double bicepsContraidoDireito;
	private double coxaDistalEsquerda;
	private double antebraco;
	private double bicepsContraidoEsquerdo;
	private double cintura;
	private double coxaProximalEsquerda;
	private double coxaProximalDireita;
	private double panturrilhaEsquerda;
	private double peito;
	private double quadril;
	private double panturrilhaDireita;
	private double coxaDistalDireita;
	private double coxaMedialEsquerda;
	private double coxaMedialDireita;
	private double ombro;
	private double bicepsRelaxadoEsquerdo;
	private double abdomen;
	private double bicepsRelaxadoDireito;

	//atributos auxiliares
	private boolean retorno;
	private Perimetria perimetria;
	
	
		
	
	@Override
	public String toString() {
		return "Perimetria [codAvaliacao=" + codAvaliacao
				+ ", bicepsContraidoDireito=" + bicepsContraidoDireito
				+ ", coxaDistalEsquerda=" + coxaDistalEsquerda + ", antebraco="
				+ antebraco + ", bicepsContraidoEsquerdo="
				+ bicepsContraidoEsquerdo + ", cintura=" + cintura
				+ ", coxaProximalEsquerda=" + coxaProximalEsquerda
				+ ", coxaProximalDireita=" + coxaProximalDireita
				+ ", panturrilhaEsquerda=" + panturrilhaEsquerda + ", peito="
				+ peito + ", quadril=" + quadril + ", panturrilhaDireita="
				+ panturrilhaDireita + ", coxaDistalDireita="
				+ coxaDistalDireita + ", coxaMedialEsquerda="
				+ coxaMedialEsquerda + ", coxaMedialDireita="
				+ coxaMedialDireita + ", ombro=" + ombro
				+ ", bicepsRelaxadoEsquerdo=" + bicepsRelaxadoEsquerdo
				+ ", abdomen=" + abdomen + ", bicepsRelaxadoDireito="
				+ bicepsRelaxadoDireito + ", retorno=" + retorno
				+ ", perimetria=" + perimetria + "]";
	}


	public Perimetria(){
		
	}
	
	
	public Perimetria(int codAvaliacao, double bicepsContraidoDireito,
			double coxaDistalEsquerda, double antebraco,
			double bicepsContraidoEsquerdo, double cintura,
			double coxaProximalEsquerda, double coxaProximalDireita,
			double panturrilhaEsquerda, double peito, double quadril,
			double panturrilhaDireita, double coxaDistalDireita,
			double coxaMedialEsquerda, double coxaMedialDireita, double ombro,
			double bicepsRelaxadoEsquerdo, double abdomen,
			double bicepsRelaxadoDireito) {
		super();
		this.codAvaliacao = codAvaliacao;
		this.bicepsContraidoDireito = bicepsContraidoDireito;
		this.coxaDistalEsquerda = coxaDistalEsquerda;
		this.antebraco = antebraco;
		this.bicepsContraidoEsquerdo = bicepsContraidoEsquerdo;
		this.cintura = cintura;
		this.coxaProximalEsquerda = coxaProximalEsquerda;
		this.coxaProximalDireita = coxaProximalDireita;
		this.panturrilhaEsquerda = panturrilhaEsquerda;
		this.peito = peito;
		this.quadril = quadril;
		this.panturrilhaDireita = panturrilhaDireita;
		this.coxaDistalDireita = coxaDistalDireita;
		this.coxaMedialEsquerda = coxaMedialEsquerda;
		this.coxaMedialDireita = coxaMedialDireita;
		this.ombro = ombro;
		this.bicepsRelaxadoEsquerdo = bicepsRelaxadoEsquerdo;
		this.abdomen = abdomen;
		this.bicepsRelaxadoDireito = bicepsRelaxadoDireito;
	}




	//CUD Local
	public boolean salvarPerimetria(Banco b){
		try{
			
			String SQL = "INSERT INTO Perimetria ("
					+ "codAvaliacao,"
					+ "bicepsContraidoDireito,"
					+ "coxaDistalEsquerda,"
					+ "antebraco,"
					+ "bicepsContraidoEsquerdo,"
					+ "coxaProximalEsquerda,"
					+ "coxaProximalDireita,"
					+ "panturrilhaEsquerda,"
					+ "peito,"
					+ "quadril,"
					+ "panturrilhaDireita,"
					+ "coxaDistalDireita,"
					+ "coxaMedialEsquerda,"
					+ "coxaMedialDireita,"
					+ "ombro,"
					+ "bicepsRelaxadoEsquerdo,"
					+ "abdomen,"
					+ "bicepsRelaxadoDireito) VALUES("
					+ codAvaliacao + ","
					+ bicepsContraidoDireito + ","
					+ coxaDistalEsquerda + ","
					+ antebraco + ","
					+ bicepsContraidoEsquerdo + ","
					+ coxaProximalEsquerda + ","
					+ coxaProximalDireita + ","
					+ panturrilhaEsquerda + ","
					+ peito + ","
					+ quadril + ","
					+ panturrilhaDireita + ","
					+ coxaDistalDireita + "," 
					+ coxaMedialEsquerda + "," 
					+ coxaMedialDireita + ","
					+ ombro + ","
					+ bicepsRelaxadoEsquerdo + ","  
					+ abdomen + ","
					+ bicepsRelaxadoDireito + ");";
					
			
			b.execSQL(SQL);
			
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		
	}
	
	public boolean editar(Banco b){
		String SQL = "UPDATE Perimetria set "
				+ " bicepscontraidoDireito = " + bicepsContraidoDireito
				+ ", coxaDistalEsquerda = " + coxaDistalEsquerda
				+ ", antebraco = " + antebraco
				+ ", bicepsContraidoEsquerdo = " + bicepsContraidoEsquerdo 
				+ ", coxaProximalEsquerda = " + coxaProximalEsquerda 
				+ ", coxaProximalDireita = " + coxaProximalDireita
				+ ", panturrilhaEsquerda = " + panturrilhaEsquerda
				+ ", peito = " + peito 
				+ ", quadril = " + quadril 
				+ ", panturrilhaDireita = " + panturrilhaDireita
				+ ", coxaDistalDireita = " + coxaDistalDireita 
				+ ", coxaMedialEsquerda = " + coxaMedialEsquerda
				+ ", coxaMedialDireita = " + coxaMedialDireita
				+ ", ombro = " + ombro
				+ ", bicepsRelaxadoEsquerdo = " + bicepsRelaxadoEsquerdo
				+ ", abdomen = " + abdomen 
				+ ", bicepsRelaxadoDireito = " + bicepsRelaxadoDireito 
				+ " where codAvaliacao = " + codAvaliacao;
				
		try{
			b.execSQL(SQL);
			return true;
		}catch(Exception e){
			Log.i("Erro: ExcluirAvaliacoes",e.toString());
			return false;
		}
		
	}
	
	
	
public Perimetria buscarPerimetriaPorId(Banco b, int codAvaliacao){
		
		
		String SQL = "SELECT * FROM Perimetria where codAvaliacao = " + codAvaliacao ;
		
		
		Cursor dataset = b.querySQL(SQL);

		
		
		int col_bicepsContraidoDireito = dataset.getColumnIndex("bicepsContraidoDireito");
		int col_coxaDistalEsquerda = dataset.getColumnIndex("coxaDistalEsquesrda");
		int col_antebraco = dataset.getColumnIndex("antebraco");
		int col_bicepsContraidoEsquerdo = dataset.getColumnIndex("bicepsContraidoEsquerdo");
		int col_coxaProximalEsquerda = dataset.getColumnIndex("coxaProximalEsquerda");
		int col_coxaProximalDireita = dataset.getColumnIndex("coxaProximalDireita");
		int col_panturrilhaEsquerda = dataset.getColumnIndex("panturrilhaEsquerda");
		int col_peito = dataset.getColumnIndex("peito");
		int col_quadril = dataset.getColumnIndex("quadril");
		int col_panturrilhaDireita = dataset.getColumnIndex("panturrilhaDireita");
		int col_coxaDistalDireita = dataset.getColumnIndex("coxaDistalDireita");
		int col_coxaMedialEsquerda = dataset.getColumnIndex("coxaMedialEsquerda");
		int col_coxaMedialDireita = dataset.getColumnIndex("coxaMedialDireita");
		int col_ombro = dataset.getColumnIndex("ombro");
		int col_bicepsRelaxadoEsquerdo = dataset.getColumnIndex("bicepsRelaxadoEsquerdo");
		int col_abdomen = dataset.getColumnIndex("abdomen");
		int col_bicepsRelaxadoDireito = dataset.getColumnIndex("bocepsRelaxadoDireito");
		
			
		//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
		dataset.moveToFirst();
		
		if(dataset.getCount() > 0){
			Perimetria a = new Perimetria();
			a.setCodAvaliacao(codAvaliacao);
			a.setBicepsContraidoDireito(col_bicepsContraidoDireito);
			a.setCoxaDistalEsquerda(col_coxaDistalEsquerda);
			a.setAntebraco(col_antebraco);
			a.setBicepsContraidoEsquerdo(col_bicepsContraidoEsquerdo);
			a.setCoxaProximalEsquerda(col_coxaProximalEsquerda);
			a.setCoxaProximalDireita(col_coxaProximalDireita);
			a.setPanturrilhaEsquerda(col_panturrilhaEsquerda);
			a.setPeito(col_peito);
			a.setQuadril(col_quadril);
			a.setPanturrilhaDireita(col_panturrilhaDireita);
			a.setCoxaDistalDireita(col_coxaDistalDireita);
			a.setCoxaMedialEsquerda(col_coxaMedialEsquerda);
			a.setCoxaMedialDireita(col_coxaMedialDireita);
			a.setOmbro(col_ombro);
			a.setBicepsRelaxadoEsquerdo(col_bicepsRelaxadoEsquerdo);
			a.setAbdomen(col_abdomen);
			a.setBicepsRelaxadoDireito(col_bicepsRelaxadoDireito);
	
			
			return a;
		}
			
		return null;
	}
	
	
	

	//CUD WEB
		
	public boolean salvarPerimetriaWeb(){
		clear();
		final Perimetria a = new Perimetria(codAvaliacao,bicepsContraidoDireito,coxaDistalEsquerda,
				antebraco,bicepsContraidoEsquerdo,cintura,coxaProximalEsquerda,coxaProximalDireita,panturrilhaEsquerda,peito
				,quadril,panturrilhaDireita,coxaDistalDireita,coxaMedialEsquerda,coxaMedialDireita,
				ombro,bicepsRelaxadoEsquerdo,abdomen,bicepsRelaxadoDireito);
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"salvarPerimetria");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Perimetria");
				p1.setValue(a);
				p1.setType(new Perimetria().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "Perimetria",new Avaliacoes().getClass());
				MarshalDouble marshalDouble = new MarshalDouble();
				marshalDouble.register(envelope);
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("salvarPerimetria"), envelope);
					
					 retorno = Boolean.parseBoolean(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: Salvar Perimetria", e.toString());
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
	
	public boolean editarPerimetriaWeb(){
		clear();
		final Perimetria a = new Perimetria(codAvaliacao,bicepsContraidoDireito,coxaDistalEsquerda,
				antebraco,bicepsContraidoEsquerdo,cintura,coxaProximalEsquerda,coxaProximalDireita,panturrilhaEsquerda,peito
				,quadril,panturrilhaDireita,coxaDistalDireita,coxaMedialEsquerda,coxaMedialDireita,
				ombro,bicepsRelaxadoEsquerdo,abdomen,bicepsRelaxadoDireito);
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"editarPerimetria");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Perimetria");
				p1.setValue(a);
				p1.setType(new Perimetria().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "Perimetria",new Avaliacoes().getClass());
				MarshalDouble marshaldDouble = new MarshalDouble();
				marshaldDouble.register(envelope);
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("editarPerimetria"), envelope);
					
					 retorno = Boolean.parseBoolean(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: EditarPerimetriaweb", e.toString());
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
	

	
	public Perimetria buscarPerimetriaPorIdWeb(int codAvaliacao){
			final Perimetria a = new Perimetria();
			a.setCodAvaliacao(codAvaliacao);
			
					
					Thread threadWs = new Thread(){
						
						@Override		
						public void run(){
							
							
							
							SoapObject request = new SoapObject(WebService.getNamespace(),"buscarPerimetriaPorId");
							PropertyInfo p1 = new PropertyInfo();
							p1.setName("Perimetria");
							p1.setValue(a);
							p1.setType(new Perimetria().getClass());
							
							request.addProperty(p1);
							
				
							SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
							envelope.setOutputSoapObject(request);
							envelope.addMapping(WebService.getNamespace(), "Perimetria",new Perimetria().getClass());
							MarshalDouble marshaldDouble = new MarshalDouble();
							marshaldDouble.register(envelope);
							HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
							
							
							try{
								ht.call(WebService.getSoapAction("buscarPerimetriaPorId"), envelope);
								
								SoapObject res = new SoapObject();
								
								try{
									res = (SoapObject) envelope.getResponse();
									//Log.i("debug", "cheguei aki --- > " + res.toString());
									
											
								}catch(Exception e){
									Log.i("Erro: buscarPerimetriaPorId (UNICO) ", e.toString());
								}
						
								perimetria = getSoapPerimetria(res);
						       // Log.i("debug aluno", retornoAvaliacoes.toString());
							         
							}catch(Exception e){
								//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
								Log.e("Erro: buscarPerimetriaPorId (VETOR) ", e.toString());
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
					return perimetria;
		}
		
		
	
	
	public Perimetria getSoapPerimetria (SoapObject res) { 
		Perimetria item = new Perimetria();
		if (res.hasProperty("codAvaliacao")) {
            item.setCodAvaliacao(Integer.parseInt(res.getPropertyAsString("codAvaliacao")));
		 }
		if (res.hasProperty("bicepsContraidoDireito")) {
            item.setBicepsContraidoDireito(Double.parseDouble(res.getPropertyAsString("bicepsContraidoDireito")));
		 }
		if (res.hasProperty("coxaDistalEsquerda")) {
	            item.setCoxaDistalEsquerda(Double.parseDouble(res.getPropertyAsString("coxaDistalEsquerda")));
		}
		if (res.hasProperty("antebraco")) {
            item.setAntebraco(Double.parseDouble(res.getPropertyAsString("antebraco")));
		 }
		if (res.hasProperty("bicepsContraidoEsquerdo")) {
            item.setBicepsContraidoEsquerdo(Double.parseDouble(res.getPropertyAsString("bicepsContraidoEsquerdo")));
		 }
		if (res.hasProperty("cintura")) {
            item.setCintura(Double.parseDouble(res.getPropertyAsString("cintura")));
		 }
		if (res.hasProperty("coxaProximalEsquerda")) {
            item.setCoxaProximalEsquerda(Double.parseDouble(res.getPropertyAsString("coxaProximalEsquerda")));
		 }
		if (res.hasProperty("coxaProximalDireita")) {
            item.setCoxaProximalDireita(Double.parseDouble(res.getPropertyAsString("coxaProximalDireita")));
		 }
		if (res.hasProperty("panturrilhaEsquerda")) {
            item.setPanturrilhaEsquerda(Double.parseDouble(res.getPropertyAsString("panturrilhaEsquerda")));
		 }
		if (res.hasProperty("peito")) {
            item.setPeito(Double.parseDouble(res.getPropertyAsString("peito")));
		 }
		if (res.hasProperty("quadril")) {
            item.setQuadril(Double.parseDouble(res.getPropertyAsString("quadril")));
		 }
		if (res.hasProperty("panturrilhaDireita")) {
            item.setPanturrilhaDireita(Double.parseDouble(res.getPropertyAsString("panturrilhaDireita")));
        }
		if (res.hasProperty("coxaDistalDireita")) {
            item.setCoxaDistalDireita(Double.parseDouble(res.getPropertyAsString("coxaDistalDireita")));
		 }
		if (res.hasProperty("coxaMedialEsquerda")) {
            item.setCoxaMedialEsquerda(Double.parseDouble(res.getPropertyAsString("coxaMedialEsquerda")));
		 }
		if (res.hasProperty("coxaMedialDireita")) {
            item.setCoxaMedialDireita(Double.parseDouble(res.getPropertyAsString("coxaMedialDireita")));
		 }
		if (res.hasProperty("ombro")) {
            item.setOmbro(Double.parseDouble(res.getPropertyAsString("ombro")));
		 }
		if (res.hasProperty("bicepsRelaxadoEsquerdo")) {
            item.setBicepsRelaxadoEsquerdo(Double.parseDouble(res.getPropertyAsString("bicepsRelaxadoEsquerdo")));
		 }
		if (res.hasProperty("abdomen")) {
            item.setAbdomen(Double.parseDouble(res.getPropertyAsString("abdomen")));
		 }
		if (res.hasProperty("setBicepsRelaxadoDireito")) {
            item.setBicepsRelaxadoDireito(Double.parseDouble(res.getPropertyAsString("bicepsRelaxadoDireito")));
		}
		return item;
	}
	
	public void clear(){
		retorno = false;
		perimetria = new Perimetria();
		
	}
		
		//GETs e Sets
	
	public int getCodAvaliacao(){
		return codAvaliacao;
	}
	public void setCodAvaliacao(int codAvaliacao){
		this.codAvaliacao = codAvaliacao;
	}
	
	public double getBicepsContraidoDireito() {
		return bicepsContraidoDireito;
	}
	public void setBicepsContraidoDireito(double bicepsContraidoDireito) {
		this.bicepsContraidoDireito = bicepsContraidoDireito;
	}
	public double getCoxaDistalEsquerda() {
		return coxaDistalEsquerda;
	}
	public void setCoxaDistalEsquerda(double coxaDistalEsquerda) {
		this.coxaDistalEsquerda = coxaDistalEsquerda;
	}
	public double getAntebraco() {
		return antebraco;
	}
	public void setAntebraco(double antebraco) {
		this.antebraco = antebraco;
	}
	public double getBicepsContraidoEsquerdo() {
		return bicepsContraidoEsquerdo;
	}
	public void setBicepsContraidoEsquerdo(double bicepsContraidoEsquerdo) {
		this.bicepsContraidoEsquerdo = bicepsContraidoEsquerdo;
	}
	public double getCintura() {
		return cintura;
	}
	public void setCintura(double cintura) {
		this.cintura = cintura;
	}
	public double getCoxaProximalEsquerda() {
		return coxaProximalEsquerda;
	}
	public void setCoxaProximalEsquerda(double coxaProximalEsquerda) {
		this.coxaProximalEsquerda = coxaProximalEsquerda;
	}
	public double getCoxaProximalDireita() {
		return coxaProximalDireita;
	}
	public void setCoxaProximalDireita(double coxaProximalDireita) {
		this.coxaProximalDireita = coxaProximalDireita;
	}
	public double getPanturrilhaEsquerda() {
		return panturrilhaEsquerda;
	}
	public void setPanturrilhaEsquerda(double panturrilhaEsquerda) {
		this.panturrilhaEsquerda = panturrilhaEsquerda;
	}
	public double getPeito() {
		return peito;
	}
	public void setPeito(double peito) {
		this.peito = peito;
	}
	public double getQuadril() {
		return quadril;
	}
	public void setQuadril(double quadril) {
		this.quadril = quadril;
	}
	public double getPanturrilhaDireita() {
		return panturrilhaDireita;
	}
	public void setPanturrilhaDireita(double panturrilhaDireita) {
		this.panturrilhaDireita = panturrilhaDireita;
	}
	public double getCoxaDistalDireita() {
		return coxaDistalDireita;
	}
	public void setCoxaDistalDireita(double coxaDistalDireita) {
		this.coxaDistalDireita = coxaDistalDireita;
	}
	public double getCoxaMedialEsquerda() {
		return coxaMedialEsquerda;
	}
	public void setCoxaMedialEsquerda(double coxaMedialEsquerda) {
		this.coxaMedialEsquerda = coxaMedialEsquerda;
	}
	public double getCoxaMedialDireita() {
		return coxaMedialDireita;
	}
	public void setCoxaMedialDireita(double coxaMedialDireita) {
		this.coxaMedialDireita = coxaMedialDireita;
	}
	public double getOmbro() {
		return ombro;
	}
	public void setOmbro(double ombro) {
		this.ombro = ombro;
	}
	public double getBicepsRelaxadoEsquerdo() {
		return bicepsRelaxadoEsquerdo;
	}
	public void setBicepsRelaxadoEsquerdo(double bicepsRelaxadoEsquerdo) {
		this.bicepsRelaxadoEsquerdo = bicepsRelaxadoEsquerdo;
	}
	public double getAbdomen() {
		return abdomen;
	}
	public void setAbdomen(double abdomen) {
		this.abdomen = abdomen;
	}
	public double getBicepsRelaxadoDireito() {
		return bicepsRelaxadoDireito;
	}
	public void setBicepsRelaxadoDireito(double bicepsRelaxadoDireito) {
		this.bicepsRelaxadoDireito = bicepsRelaxadoDireito;
	}
	
	
	//KVM
	

	@Override
	public Object getProperty(int arg0) {
		switch(arg0){
        case 0:
            return codAvaliacao;
        case 1:
            return bicepsContraidoDireito;
        case 2:
         return coxaDistalEsquerda;
        case 3:
        	return antebraco;
        case 4:
           	return bicepsContraidoEsquerdo;
        case 5:
        	return cintura;
        case 6:
        	return coxaProximalEsquerda;
        case 7:
        	return coxaProximalDireita;
        case 8:
        	return panturrilhaEsquerda;
        case 9:
        	return peito;
        case 10:
        	return quadril;
        case 11:
        	return panturrilhaDireita;
        case 12:
        	return coxaDistalDireita;
        case 13:
        	return coxaMedialEsquerda;
        case 14:
        	return coxaMedialDireita;
        case 15:
        	return ombro;
        case 16:
        	return bicepsRelaxadoEsquerdo;
        case 17:
        	return abdomen;
        case 18:
        	return bicepsRelaxadoDireito;
        default: 
        	return null;
        		
        }
	}


	@Override
	public int getPropertyCount() {
		return 19;
	}



	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		switch(arg0){
        case 0:
            arg2.type = PropertyInfo.INTEGER_CLASS;
            arg2.name = "codAvaliacao";
            break;
        case 1:
        	 arg2.type = Double.class;
        	 arg2.name = "bicepsContraidoDireito";
            break;
        case 2:
        	 arg2.type = Double.class;
        	 arg2.name = "coxaDistalEsquerda";
            break;
        case 3:
        	 arg2.type = Double.class;
        	 arg2.name = "antebraco";
            break;
        case 4:
        	 arg2.type = Double.class;
        	 arg2.name = "bicepsContraidoEsquerdo";
            break;
        case 5:
        	 arg2.type = Double.class;
            arg2.name = "cintura";
            break;
        case 6:
        	 arg2.type = Double.class;
            arg2.name = "coxaProximalEsquerda";
            break;
        case 7:
        	 arg2.type = Double.class;
            arg2.name = "coxaProximalDireita";
            break;
        case 8:
            arg2.type = Double.class;
            arg2.name = "panturrilhaEsquerda";
            break;
        case 9:
            arg2.type = Double.class;
            arg2.name = "peito";
            break;
        case 10:
            arg2.type = Double.class;
            arg2.name = "quadril";
            break;
        case 11:
            arg2.type = Double.class;
            arg2.name = "panturrilhaDireita";
            break;
        case 12:
            arg2.type = Double.class;
            arg2.name = "coxaDistalDireita";
            break;
        case 13:
            arg2.type = Double.class;
            arg2.name = "coxaMedialEsquerda";
            break;
        case 14:
            arg2.type = Double.class;
            arg2.name = "coxaMedialDireita";
            break;
        case 15:
            arg2.type = Double.class;
            arg2.name = "ombro";
            break;
        case 16:
            arg2.type = Double.class;
            arg2.name = "bicepsRelaxadoEsquerdo";
            break;
        case 17:
            arg2.type = Double.class;
            arg2.name = "abdomen";
            break;
        case 18:
            arg2.type = Double.class;
            arg2.name = "bicepsRelaxadoDireito";
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
        	setBicepsContraidoDireito(Double.parseDouble(arg1.toString()));
            break;
        case 2:
        	setCoxaDistalEsquerda(Double.parseDouble(arg1.toString()));
            break;
        case 3:
        	setAntebraco(Double.parseDouble(arg1.toString()));
            break;
        case 4:
        	setBicepsContraidoEsquerdo(Double.parseDouble(arg1.toString()));
            break;
        case 5:
        	setCintura(Double.parseDouble(arg1.toString()));
            break;
        case 6:
        	setCoxaProximalEsquerda(Double.parseDouble(arg1.toString()));
            break;
        case 7:
        	setPanturrilhaEsquerda(Double.parseDouble(arg1.toString()));
            break;
        case 8:
        	setPeito(Double.parseDouble(arg1.toString()));
            break;
        case 9:
        	setQuadril(Double.parseDouble(arg1.toString()));
            break;
        case 10:
        	setPanturrilhaDireita(Double.parseDouble(arg1.toString()));
            break;
        case 11:
        	setCoxaDistalDireita(Double.parseDouble(arg1.toString()));
            break;
        case 12:
        	setCoxaMedialEsquerda(Double.parseDouble(arg1.toString()));
            break;
        case 13:
        	setCoxaMedialDireita(Double.parseDouble(arg1.toString()));
            break;
        case 14:
        	setOmbro(Double.parseDouble(arg1.toString()));
            break;
        case 15:
        	setBicepsRelaxadoEsquerdo(Double.parseDouble(arg1.toString()));
            break;
        case 16:
        	setAbdomen(Double.parseDouble(arg1.toString()));
            break;
        case 17:
        	setBicepsRelaxadoDireito(Double.parseDouble(arg1.toString()));
            break;
        default:
            break;
        }
	}
	
	
	
}
