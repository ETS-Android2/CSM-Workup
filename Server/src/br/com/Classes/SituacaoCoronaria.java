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

public class SituacaoCoronaria implements KvmSerializable{

	private int codAvaliacao;
	private String objetivoDoTreinamento;
	private String pressaoSistolicaMaxima;
	private String pressaoDiastolicaMaxima;
	private String pressaoSistolicaDeRepouso;
	private String pressaoDiastolicaDeRepouso;
	
	//Atributos auxiliares
	private boolean retorno;
	private SituacaoCoronaria situacaoCoronaria;
	
	
	//toString
	@Override
	public String toString() {
		return "SituacaoCoronaria [codAvaliacao=" + codAvaliacao
				+ ", objetivoDoTreinamento=" + objetivoDoTreinamento
				+ ", pressaoSistolicaMaxima=" + pressaoSistolicaMaxima
				+ ", pressaoDiastolicaMaxima=" + pressaoDiastolicaMaxima
				+ ", pressaoSistolicaDeRepouso=" + pressaoSistolicaDeRepouso
				+ ", pressaoDiastolicaDeRepouso=" + pressaoDiastolicaDeRepouso
				+ ", retorno=" + retorno + ", situacaoCoronaria="
				+ situacaoCoronaria + "]";
	}

	public SituacaoCoronaria(){
		
	}
	
	public SituacaoCoronaria(int codAvaliacao, String objetivoDoTreinamento,
			String pressaoSistolicaMaxima, String pressaoDiastolicaMaxima,
			String pressaoSistolicaDeRepouso, String pressaoDiastolicaDeRepouso) {
		super();
		this.codAvaliacao = codAvaliacao;
		this.objetivoDoTreinamento = objetivoDoTreinamento;
		this.pressaoSistolicaMaxima = pressaoSistolicaMaxima;
		this.pressaoDiastolicaMaxima = pressaoDiastolicaMaxima;
		this.pressaoSistolicaDeRepouso = pressaoSistolicaDeRepouso;
		this.pressaoDiastolicaDeRepouso = pressaoDiastolicaDeRepouso;
	}
	
	
	public boolean salvarSituacaoCoronaria(Banco b){
		try{
			
			String SQL = "INSERT INTO SituacaoCoronaria ("
					+ "codAvaliacao,"
					+ "objetivoDoTreinamento,"
					+ "pressaoSistolicaMaxima,"
					+ "pressaoDiastolicaMaxima,"
					+ "pressaoSistolicaDeRepouso,"
					+ "PressaoDiastolicaDeRepouso) VALUES("
					+ codAvaliacao + ",'"
					+ objetivoDoTreinamento + "','"
					+ pressaoSistolicaMaxima + "','"
					+ pressaoDiastolicaMaxima + "','"
					+ pressaoSistolicaDeRepouso + "','"
					+ pressaoDiastolicaDeRepouso + "');";
					
			
			b.execSQL(SQL);
			
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		
	}
	
	
	public boolean editar(Banco b){
		String SQL = "UPDATE SituacaoCoronaria set "
				+ " objetivoDoTreinamento = '" + objetivoDoTreinamento 
				+ "', pressaoSistolicaMaxima = '" + pressaoSistolicaMaxima
				+ "', pressaoDiastolicaMaxima = '" + pressaoDiastolicaMaxima
				+ "', pressaoSistolicaDeRepouso = '" + pressaoSistolicaDeRepouso 
				+ "', pressaoDiastolicaDeRepouso = '" + pressaoDiastolicaDeRepouso 
				+ "' where codAvaliacao = " + codAvaliacao;
				
		try{
			b.execSQL(SQL);
			return true;
		}catch(Exception e){
			Log.i("Erro: EditarSituacaoCoronaria",e.toString());
			return false;
		}
		
	}
	
	
	
public SituacaoCoronaria buscarSituacaoCoronariaPorId(Banco b, int codAvaliacao){
		
		
		String SQL = "SELECT * FROM situacaocoronaria where codAvaliacao = " + codAvaliacao ;
		
		
		Cursor dataset = b.querySQL(SQL);

		
		
		int col_objetivoDoTreinamento = dataset.getColumnIndex("objetivoDoTreinamento");
		int col_pressaoSistolicaMaxima = dataset.getColumnIndex("pressaoSistolicaMaxima");
		int col_pressaoDiastolicaMaxima = dataset.getColumnIndex("pressaoDiastolicaMaxima");
		int col_pressaoSistolicaDeRepouso = dataset.getColumnIndex("pressaoSistolicaDeRepouso");
		int col_pressaoDiastolicaDeRepouso = dataset.getColumnIndex("pressaoDiastolicaDeRepouso");
		
		
		
		
		//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
		dataset.moveToFirst();
		
		if(dataset.getCount() > 0){
			SituacaoCoronaria a = new SituacaoCoronaria();
			a.setCodAvaliacao(codAvaliacao);
			a.setObjetivoDoTreinamento(dataset.getString(col_objetivoDoTreinamento));
			a.setPressaoSistolicaMaxima(dataset.getString(col_pressaoSistolicaMaxima));
			a.setPressaoDiastolicaMaxima(dataset.getString(col_pressaoDiastolicaMaxima));
			a.setPressaoSistolicaDeRepouso(dataset.getString(col_pressaoSistolicaDeRepouso));
			a.setPressaoDiastolicaDeRepouso(dataset.getString(col_pressaoDiastolicaDeRepouso));
			return a;
		}
			
		return null;
	}
	
	

	
	//CUD WEB
	
	//CUD Web Service
		public boolean salvarSituacaoCoronariaWeb(){
				clear();
				final SituacaoCoronaria a = new SituacaoCoronaria(codAvaliacao,objetivoDoTreinamento,
						pressaoSistolicaMaxima,pressaoDiastolicaMaxima,pressaoSistolicaDeRepouso,
						pressaoDiastolicaDeRepouso);
				
				Thread threadWs = new Thread(){
					
					@Override		
					public void run(){
						
						SoapObject request = new SoapObject(WebService.getNamespace(),"salvarSituacaoCoronaria");
						PropertyInfo p1 = new PropertyInfo();
						p1.setName("SituacaoCoronaria");
						p1.setValue(a);
						p1.setType(new SituacaoCoronaria().getClass());
						
						request.addProperty(p1);
						
			
						SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
						envelope.setOutputSoapObject(request);
						envelope.addMapping(WebService.getNamespace(), "SituacaoCoronaria",new SituacaoCoronaria().getClass());
						
						HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
						
						
						try{
							ht.call(WebService.getSoapAction("SituacaoCoronaria"), envelope);
							
							 retorno = Boolean.parseBoolean(envelope.getResponse().toString());
							
						}catch(Exception e){
							//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
							Log.e("Erro: salvarSituacaoCoronaria", e.toString());
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
			
		public boolean editarSituacaoCoronariaWeb(){
				clear();
				final SituacaoCoronaria a = new SituacaoCoronaria(codAvaliacao,objetivoDoTreinamento,
						pressaoSistolicaMaxima,pressaoDiastolicaMaxima,pressaoSistolicaDeRepouso,
						pressaoDiastolicaDeRepouso);
				
				Thread threadWs = new Thread(){
					
					@Override		
					public void run(){
						
						SoapObject request = new SoapObject(WebService.getNamespace(),"editarSituacaoCoronaria");
						PropertyInfo p1 = new PropertyInfo();
						p1.setName("SituacaoCoronaria");
						p1.setValue(a);
						p1.setType(new SituacaoCoronaria().getClass());
						
						request.addProperty(p1);
						
			
						SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
						envelope.setOutputSoapObject(request);
						envelope.addMapping(WebService.getNamespace(), "SituacaoCoronaria",new SituacaoCoronaria().getClass());
						
						HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
						
						
						try{
							ht.call(WebService.getSoapAction("editarSituacaoCoronaria"), envelope);
							
							 retorno = Boolean.parseBoolean(envelope.getResponse().toString());
							
						}catch(Exception e){
							//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
							Log.e("Erro: editarSituacaoCoronaria", e.toString());
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
			
		
		
		//buscar WEB
		public SituacaoCoronaria buscarSituacaoCoronariaPorIdWeb(int codAvaliacao){
				final SituacaoCoronaria a = new SituacaoCoronaria();
				a.setCodAvaliacao(codAvaliacao);
				
						
						Thread threadWs = new Thread(){
							
							@Override		
							public void run(){
								
								
								
								SoapObject request = new SoapObject(WebService.getNamespace(),"buscarSituacaoCoronariaPorId");
								PropertyInfo p1 = new PropertyInfo();
								p1.setName("SituacaoCoronaria");
								p1.setValue(a);
								p1.setType(new SituacaoCoronaria().getClass());
								
								request.addProperty(p1);
								
					
								SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
								envelope.setOutputSoapObject(request);
								envelope.addMapping(WebService.getNamespace(), "SituacaoCoronaria",new SituacaoCoronaria().getClass());
								//MarshalDouble marshalDouble = new MarshalDouble();
								//marshalDouble.register(envelope);
								
								HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
								
								
								try{
									ht.call(WebService.getSoapAction("buscarSituacaoCoronariaPorId"), envelope);
									
									SoapObject res = new SoapObject();
									
									try{
										res = (SoapObject) envelope.getResponse();
										//Log.i("debug", "cheguei aki --- > " + res.toString());
										
												
									}catch(Exception e){
										Log.i("Erro: buscarSituacaoCoronariaPorId (UNICO) ", e.toString());
									}
							
									situacaoCoronaria = getSoapSituacaoCoronaria(res);
							       // Log.i("debug aluno", retornoAvaliacoes.toString());
								         
								}catch(Exception e){
									//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
									Log.e("Erro: BuscarSituacaoCoronaria (VETOR) ", e.toString());
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
						return situacaoCoronaria;
			}
		
		public void clear(){
			retorno = false;
			situacaoCoronaria = new SituacaoCoronaria();
		}
	
		
	public SituacaoCoronaria getSoapSituacaoCoronaria(SoapObject res){
			SituacaoCoronaria item = new SituacaoCoronaria();
			if (res.hasProperty("codAvaliacao")) {
	            item.setCodAvaliacao(Integer.parseInt(res.getPropertyAsString("codAvaliacao")));
			}
			if (res.hasProperty("objetivoDoTreinamento")) {
	            item.setObjetivoDoTreinamento(res.getPropertyAsString("objetivoDoTreinamento"));
			}
			if (res.hasProperty("pressaoSistolicaMaxima")) {
	            item.setPressaoSistolicaMaxima(res.getPropertyAsString("pressaoSistolicaMaxima"));
			}
			if (res.hasProperty("pressaoDiastolicaMaxima")) {
	            item.setPressaoDiastolicaMaxima(res.getPropertyAsString("pressaoDiastolicaMaxima"));
			}
			if (res.hasProperty("pressaoSistolicaDeRepouso")) {
	            item.setPressaoSistolicaDeRepouso(res.getPropertyAsString("pressaoSistolicaDeRepouso"));
			}
			if (res.hasProperty("pressaoDiastolicaDeRepouso")) {
	            item.setPressaoDiastolicaDeRepouso(res.getPropertyAsString("pressaoDiastolicaDeRepouso"));
			}
			return item;
			
		}
			
		
	
	
	public int getCodAvaliacao() {
		return codAvaliacao;
	}

	public void setCodAvaliacao(int codAvaliacao) {
		this.codAvaliacao = codAvaliacao;
	}

	public String getObjetivoDoTreinamento() {
		return objetivoDoTreinamento;
	}

	public void setObjetivoDoTreinamento(String objetivoDoTreinamento) {
		this.objetivoDoTreinamento = objetivoDoTreinamento;
	}

	public String getPressaoSistolicaMaxima() {
		return pressaoSistolicaMaxima;
	}

	public void setPressaoSistolicaMaxima(String pressaoSistolicaMaxima) {
		this.pressaoSistolicaMaxima = pressaoSistolicaMaxima;
	}

	public String getPressaoDiastolicaMaxima() {
		return pressaoDiastolicaMaxima;
	}

	public void setPressaoDiastolicaMaxima(String pressaoDiastolicaMaxima) {
		this.pressaoDiastolicaMaxima = pressaoDiastolicaMaxima;
	}

	public String getPressaoSistolicaDeRepouso() {
		return pressaoSistolicaDeRepouso;
	}

	public void setPressaoSistolicaDeRepouso(String pressaoSistolicaDeRepouso) {
		this.pressaoSistolicaDeRepouso = pressaoSistolicaDeRepouso;
	}

	public String getPressaoDiastolicaDeRepouso() {
		return pressaoDiastolicaDeRepouso;
	}

	public void setPressaoDiastolicaDeRepouso(String pressaoDiastolicaDeRepouso) {
		this.pressaoDiastolicaDeRepouso = pressaoDiastolicaDeRepouso;
	}
	
	
	//KVM 
	

	@Override
	public Object getProperty(int arg0) {
		switch(arg0){
        case 0:
            return codAvaliacao;
        case 1:
            return objetivoDoTreinamento;
        case 2:
         return pressaoSistolicaMaxima;
        case 3:
        	return pressaoDiastolicaMaxima;
        case 4:
           	return pressaoSistolicaDeRepouso;
        case 5:
        	return pressaoDiastolicaDeRepouso;
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
		switch(arg0){
        case 0:
            arg2.type = PropertyInfo.INTEGER_CLASS;
            arg2.name = "codAvaliacao";
            break;
        case 1:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "objetivoDoTreinamento";
            break;
        case 2:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "pressaoSistolicaMaxima";
            break;
        case 3:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "pressaoDiastolicaMaxima";
            break;
        case 4:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "pressaoSistolicaDeRepouso";
            break;
        case 5:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "pressaoDiastolicaDeRepouso";
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
        	setObjetivoDoTreinamento(arg1.toString());
            break;
        case 2:
            setPressaoSistolicaMaxima(arg1.toString());
            break;
        case 3:
            setPressaoDiastolicaMaxima(arg1.toString());
            break;
        case 4:
            setPressaoSistolicaDeRepouso(arg1.toString());
            break;
        case 5:
            setPressaoDiastolicaDeRepouso(arg1.toString());
            break;
        default:
            break;
        }
	}
}
