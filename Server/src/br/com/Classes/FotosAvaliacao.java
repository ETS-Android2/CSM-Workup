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
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import br.com.Banco.Banco;
import br.com.Utilitarios.ImageUtils;
import br.com.Utilitarios.MarshalDouble;
import br.com.Utilitarios.WebService;
import br.com.WorkUp.R;

public class FotosAvaliacao implements KvmSerializable{
	

	
	private int codAvaliacao;
	private String bicepsContraido;
	private String bicepsRelaxado;
	private String panturrilha;
	private String coxaProximal;
	private String coxaMedial;
	private String coxaDistal;
	private String antebracoOmbro;
	private String quadrilAbdomenPeito;
	
	
	
	//fotos
	private byte[] bicepsContraidoFoto ;
	private byte[] bicepsRelaxadoFoto;
	private byte[] panturrilhaFoto;
	private byte[] coxaProximalFoto;
	private byte[] coxaMedialFoto;
	private byte[] coxaDistalFoto;
	private byte[] antebracoOmbroFoto;
	private byte[] quadrilAbdomenPeitoFoto;
	
	
	//atributos Auxiliares
		private boolean retorno;
		private FotosAvaliacao retornoFotosAvaliacao;
		
	
	
	
	public FotosAvaliacao(){
		
	}
		
	public FotosAvaliacao(int codAvaliacao, String bicepsContraido,
			String bicepsRelaxado, String panturrilha, String coxaProximal,
			String coxaMedial, String coxaDistal, String antebracoOmbro,
			String quadrilAbdomenPeito) {
		super();
		this.codAvaliacao = codAvaliacao;
		this.bicepsContraido = bicepsContraido;
		this.bicepsRelaxado = bicepsRelaxado;
		this.panturrilha = panturrilha;
		this.coxaProximal = coxaProximal;
		this.coxaMedial = coxaMedial;
		this.coxaDistal = coxaDistal;
		this.antebracoOmbro = antebracoOmbro;
		this.quadrilAbdomenPeito = quadrilAbdomenPeito;
	}



	//CUD Local
	public boolean salvarAvaliacoes(Banco b){

		 String SQL = "INSERT INTO FotosAvaliacao ("
                + "codAvaliacao,"
                + "bicepsContraido,"
                + "bicepsRelaxado," 
                + "panturrilha,"
                + "coxaProximal,"
                + "coxaMedial,"
                + "coxaDistal,"
                + "antebracoOmbro,"
                + "quadrilAbdomenPeito) values "
                + "(?, ? , ? , ? , ? , ? , ? , ? , ? )";
         
      
        
        SQLiteStatement statement = b.getWritableDatabase().compileStatement(SQL);
        statement.bindLong(1, getCodAvaliacao());
        if(bicepsContraidoFoto == null){ 
        	 statement.bindNull(2);
        }else{
        	 statement.bindBlob(2, bicepsContraidoFoto);
        }
        if(bicepsRelaxadoFoto == null){
        	statement.bindNull(3);
        }else{
        	statement.bindBlob(3, bicepsRelaxadoFoto);
        }
        if(panturrilhaFoto == null){
        	 statement.bindNull(4);
        }else{
        	 statement.bindBlob(4, panturrilhaFoto);
        }
        if(coxaProximalFoto == null){
        	 statement.bindNull(5);
             
        }else{
        	 statement.bindBlob(5, coxaProximalFoto);
             
        }
        if(coxaMedialFoto == null){
        	statement.bindNull(6);
            
        }else{
        	statement.bindBlob(6, coxaMedialFoto);
            
        }
        if(coxaDistalFoto == null){
        	statement.bindNull(7);
        }else{
        	statement.bindBlob(7, coxaDistalFoto);
            
        }
        if(antebracoOmbroFoto == null){
        	statement.bindNull(8);
        }else{
        	statement.bindBlob(8, antebracoOmbroFoto);
        }
        
        if(quadrilAbdomenPeitoFoto == null){
        	statement.bindNull(9);

        }else{
        	statement.bindBlob(9, quadrilAbdomenPeitoFoto);

        }
        
    
        try{
             	
            statement.executeInsert();
            return true;
        }catch(Exception ex){
        	ex.printStackTrace();
           return false;
        }
		
	}
	
	public boolean editar(Banco b){
		 String SQL = "Update FotosAvaliacao set "
	                + "bicepsContraido = ?, "
	                + "bicepsRelaxado = ?, " 
	                + "panturrilha = ?, "
	                + "coxaProximal = ?, "
	                + "coxaMedial = ?, "
	                + "coxaDistal = ?, "
	                + "antebracoOmbro = ?, "
	                + "quadrilAbdomenPeito = ? "
	                + "where codavaliacao = ?";
	         
	      
	        
	        SQLiteStatement statement = b.getWritableDatabase().compileStatement(SQL);
	        statement.bindBlob(1, bicepsContraidoFoto);
	        statement.bindBlob(2, bicepsRelaxadoFoto);
	        statement.bindBlob(3, panturrilhaFoto);
	        statement.bindBlob(4, coxaProximalFoto);
	        statement.bindBlob(5, coxaMedialFoto);
	        statement.bindBlob(6, coxaDistalFoto);
	        statement.bindBlob(7, antebracoOmbroFoto);
	        statement.bindBlob(8, quadrilAbdomenPeitoFoto);
	        statement.bindLong(9, codAvaliacao);
		      
	        

	        try{
	             	
	            statement.executeUpdateDelete();
	            return true;
	        }catch(Exception ex){
	           Log.e("Erro ao salvar personal", ex.toString());
	           return false;
	        }
		
	}
	
	
	//Buscas Local
	public FotosAvaliacao buscarFotosAvaliacaoPorId(Banco b, int codAvaliacao){
		
		FotosAvaliacao a = new FotosAvaliacao();
		
		String SQL = "SELECT * FROM FotosAvaliacao where codAvaliacao = " + codAvaliacao ;
		
		Cursor dataset = b.querySQL(SQL);
		
		int col_bicepsContraido = dataset.getColumnIndex("bicepsContraido");
		int col_bicepsRelaxado = dataset.getColumnIndex("bicepsRelaxado");
		int col_panturrilha = dataset.getColumnIndex("panturrilha");
		int col_coxaProximal = dataset.getColumnIndex("coxaProximal");
		int col_coxaMedial = dataset.getColumnIndex("coxaMedial");
		int col_coxaDistal = dataset.getColumnIndex("coxaDistal");
		int col_antebracoOmbro = dataset.getColumnIndex("antebracoOmbro");
		int col_quadrilAbdomenPeito = dataset.getColumnIndex("quadrilAbdomenPeito");
		
		//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
		dataset.moveToFirst();
			
			if(dataset.getCount()>0){
				bicepsContraidoFoto = dataset.getBlob(col_bicepsContraido);
				
				bicepsRelaxadoFoto = dataset.getBlob(col_bicepsRelaxado);
				
				panturrilhaFoto = dataset.getBlob(col_panturrilha);
				
				coxaProximalFoto = dataset.getBlob(col_coxaProximal);
				
				coxaMedialFoto = dataset.getBlob(col_coxaMedial);
				
				coxaDistalFoto = dataset.getBlob(col_coxaDistal);
				
				antebracoOmbroFoto = dataset.getBlob(col_antebracoOmbro);
				
				quadrilAbdomenPeitoFoto = dataset.getBlob(col_quadrilAbdomenPeito);
				
				a = new FotosAvaliacao();
				a.setCodAvaliacao(codAvaliacao);
				a.setBicepsContraidoBitmap(bicepsContraidoFoto);
				a.setBicepsRelaxadoBitmap(bicepsRelaxadoFoto);
				a.setPanturrilhaBitmap(panturrilhaFoto);
				a.setCoxaMedialBitmap(coxaMedialFoto);
				a.setCoxaDistalBitmap(coxaDistalFoto);
				a.setAntebracoOmbroBitmap(antebracoOmbroFoto);
				a.setQuadrilAbdomenPeitoBitmap(quadrilAbdomenPeitoFoto);
				
				dataset.moveToNext();
			
		
				return a;
			}
			
			return null;
			
	}
	
	
	
	
	//CUD Web Service
	public boolean salvarFotosAvaliacaoWeb(){
				clear();
				try{
					bicepsContraidoFoto = Base64.decode(bicepsContraido, Base64.DEFAULT);
					bicepsRelaxadoFoto = Base64.decode(bicepsRelaxado, Base64.DEFAULT);
					panturrilhaFoto = Base64.decode(panturrilha, Base64.DEFAULT);
					coxaProximalFoto = Base64.decode(coxaProximal, Base64.DEFAULT);
					coxaMedialFoto = Base64.decode(coxaMedial, Base64.DEFAULT);
					coxaDistalFoto = Base64.decode(coxaDistal, Base64.DEFAULT);
					antebracoOmbroFoto = Base64.decode(antebracoOmbro, Base64.DEFAULT);
					quadrilAbdomenPeitoFoto = Base64.decode(quadrilAbdomenPeito, Base64.DEFAULT);
				}catch(Exception e){
					e.printStackTrace();
				}
			
				Thread threadWs = new Thread(){
					
					@Override		
					public void run(){
						
						SoapObject request = new SoapObject(WebService.getNamespace(),"salvarFotosAvaliacao");
						
						//parametro 1
						PropertyInfo pCodAvaliacao= new PropertyInfo();
						pCodAvaliacao.setName("codAvaliacao");
						pCodAvaliacao.setValue(codAvaliacao);
						pCodAvaliacao.setType(Integer.class);
						
						request.addProperty(pCodAvaliacao);
						
						//parametro 2
						PropertyInfo pBicepsContraido= new PropertyInfo();
						pBicepsContraido.setName("bicepsContraido");
						pBicepsContraido.setValue(bicepsContraidoFoto);
						pBicepsContraido.setType(byte[].class);
						
						request.addProperty(pBicepsContraido);
						
						//parametro 3
						PropertyInfo pBicepsRelaxado= new PropertyInfo();
						pBicepsRelaxado.setName("bicepsRelaxado");
						pBicepsRelaxado.setValue(bicepsRelaxadoFoto);
						pBicepsRelaxado.setType(byte[].class);
						
						request.addProperty(pBicepsRelaxado);
						
						//parametro 4
						PropertyInfo pPanturrilha= new PropertyInfo();
						pPanturrilha.setName("panturrilha");
						pPanturrilha.setValue(panturrilhaFoto);
						pPanturrilha.setType(byte[].class);
						
						request.addProperty(pPanturrilha);
						
						//parametro 5
						PropertyInfo pCoxaProximal= new PropertyInfo();
						pCoxaProximal.setName("coxaProximal");
						pCoxaProximal.setValue(coxaProximalFoto);
						pCoxaProximal.setType(byte[].class);
						
						request.addProperty(pCoxaProximal);
						
						//parametro 6
						PropertyInfo pCoxaMedial= new PropertyInfo();
						pCoxaMedial.setName("coxaMedial");
						pCoxaMedial.setValue(coxaMedialFoto);
						pCoxaMedial.setType(byte[].class);
						
						request.addProperty(pCoxaMedial);
						
						//parametro 6
						PropertyInfo pCoxaDistal= new PropertyInfo();
						pCoxaDistal.setName("coxaDistal");
						pCoxaDistal.setValue(coxaDistalFoto);
						pCoxaDistal.setType(byte[].class);
						
						request.addProperty(pCoxaDistal);
						
						//parametro 7
						PropertyInfo pAntebracoOmbro= new PropertyInfo();
						pAntebracoOmbro.setName("antebracoOmbro");
						pAntebracoOmbro.setValue(antebracoOmbroFoto);
						pAntebracoOmbro.setType(byte[].class);
						
						request.addProperty(pAntebracoOmbro);
						
						//parametro 8
						PropertyInfo pQuadrilAbdomenPeito= new PropertyInfo();
						pQuadrilAbdomenPeito.setName("quadrilAbdomenPeito");
						pQuadrilAbdomenPeito.setValue(quadrilAbdomenPeitoFoto);
						pQuadrilAbdomenPeito.setType(byte[].class);
						
						request.addProperty(pQuadrilAbdomenPeito);
						
			
						SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
						envelope.setOutputSoapObject(request);
						envelope.addMapping(WebService.getNamespace(), "FotosAvaliacao", FotosAvaliacao.class);
						envelope.addMapping(WebService.getNamespace(), "bicepsContraido", byte[].class, new MarshalBase64());
						envelope.addMapping(WebService.getNamespace(), "bicepsRelaxado", byte[].class, new MarshalBase64());
						envelope.addMapping(WebService.getNamespace(), "panturrilha", byte[].class, new MarshalBase64());
						envelope.addMapping(WebService.getNamespace(), "coxaProximal", byte[].class, new MarshalBase64());
						envelope.addMapping(WebService.getNamespace(), "coxaMedial", byte[].class, new MarshalBase64());
						envelope.addMapping(WebService.getNamespace(), "coxaDistal", byte[].class, new MarshalBase64());
						envelope.addMapping(WebService.getNamespace(), "antebracoOmbro", byte[].class, new MarshalBase64());
						envelope.addMapping(WebService.getNamespace(), "quadrilAbdomenPeito", byte[].class, new MarshalBase64());
						
						
						
						HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
						
						
						try{
							ht.call(WebService.getSoapAction("salvarFotosAvaliacao"), envelope);
							
							 retorno = Boolean.parseBoolean(envelope.getResponse().toString());
							
						}catch(Exception e){
							//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
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
				return retorno;
			}
			
	public boolean editarFotosAvaliacaoWeb(){
				clear();
				final FotosAvaliacao a = new FotosAvaliacao(codAvaliacao,bicepsContraido,bicepsRelaxado,panturrilha
						,coxaProximal,coxaMedial,coxaDistal,antebracoOmbro,quadrilAbdomenPeito);
				
				Thread threadWs = new Thread(){
					
					@Override		
					public void run(){
						
						SoapObject request = new SoapObject(WebService.getNamespace(),"editarFotosAvaliacao");
						PropertyInfo p1 = new PropertyInfo();
						p1.setName("FotosAvaliacao");
						p1.setValue(a);
						p1.setType(new FotosAvaliacao().getClass());
						
						request.addProperty(p1);
						
			
						SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
						envelope.setOutputSoapObject(request);
						envelope.addMapping(WebService.getNamespace(), "FotosAvaliacao", FotosAvaliacao.class, new MarshalBase64());
						new MarshalBase64().register(envelope);
						envelope.encodingStyle = SoapEnvelope.ENC;
						
						HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
						
						
						try{
							ht.call(WebService.getSoapAction("editarFotosAvaliacao"), envelope);
							
							 retorno = Boolean.parseBoolean(envelope.getResponse().toString());
							
						}catch(Exception e){
							//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
							Log.e("Erro: editarFotosAvaliacao", e.toString());
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
	public FotosAvaliacao buscarFotosAvaliacaoPorIdWeb(int codAvaliacao){
				final FotosAvaliacao a = new FotosAvaliacao();
				a.setCodAvaliacao(codAvaliacao);
				
						
						Thread threadWs = new Thread(){
							
							@Override		
							public void run(){
								
								
								
								SoapObject request = new SoapObject(WebService.getNamespace(),"buscarFotosAvaliacaoPorId");
								PropertyInfo p1 = new PropertyInfo();
								p1.setName("FotosAvaliacao");
								p1.setValue(a);
								p1.setType(new FotosAvaliacao().getClass());
								
								request.addProperty(p1);
								
					
								SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
								envelope.setOutputSoapObject(request);
								envelope.addMapping(WebService.getNamespace(),"x", byte[].class, new MarshalBase64());
								new MarshalBase64().register(envelope);
								envelope.encodingStyle = SoapEnvelope.ENC;
								HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
								
								
								try{
									ht.call(WebService.getSoapAction("buscarFotosAvaliacaoPorId"), envelope);
									
									SoapObject res = new SoapObject();
									
									try{
										res = (SoapObject) envelope.getResponse();
									//Log.i("debug", "resposta do servidor --- > " + res.toString());
										
												
									}catch(Exception e){
										Log.i("Erro: BuscarFotosAvaliacao (UNICO) ", e.toString());
									}
							
									retornoFotosAvaliacao = getSoapFotosAvaliacao(res);
							       // Log.i("debug aluno", retornoAvaliacoes.toString());
								         
								}catch(Exception e){
									e.printStackTrace();
									Log.e("Erro: buscarFotosAvaliacao (VETOR) ", e.toString());
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
						return retornoFotosAvaliacao;
			}
		
	public void clear(){
			retorno = false;
			retornoFotosAvaliacao = new FotosAvaliacao();
		}
		
		
	public FotosAvaliacao getSoapFotosAvaliacao(SoapObject res){
		//Log.i("logueee", res.toString());
			FotosAvaliacao item = new FotosAvaliacao();
			if (res.hasProperty("codAvaliacao")) {
	            item.setCodAvaliacao(Integer.parseInt(res.getPropertyAsString("codAvaliacao")));
			}
			//byte[] foto = null;
			if (res.hasProperty("bicepsContraido")) {
	            item.setBicepsContraidoBitmap(Base64.decode(res.getProperty("bicepsContraido").toString(),Base64.DEFAULT));
			}
			
			//Bitmap bmp = BitmapFactory.decodeByteArray(item.getBicepsContraidoBitmap(), 0, item.getBicepsContraidoBitmap().length);
			//if(bmp == null){
			//	Log.i("é realmente", "está bem null mesmo");
			//else{
			//	Log.i("aki tá de boa", "nem ficou null");
			//}
			
			if (res.hasProperty("bicepsRelaxado")) {
				item.setBicepsRelaxadoBitmap(Base64.decode(res.getProperty("bicepsRelaxado").toString(),Base64.DEFAULT));
			}
			if (res.hasProperty("panturrilha")) {
				item.setPanturrilhaBitmap(Base64.decode(res.getProperty("panturrilha").toString(),Base64.DEFAULT));
			}
			if (res.hasProperty("coxaProximal")) {
				item.setCoxaProximalBitmap(Base64.decode(res.getProperty("coxaProximal").toString(),Base64.DEFAULT));
			}
			if (res.hasProperty("coxaMedial")) {
				item.setCoxaMedialBitmap(Base64.decode(res.getProperty("coxaMedial").toString(),Base64.DEFAULT));
			}
			if (res.hasProperty("coxaDistal")) {
				item.setCoxaDistalBitmap(Base64.decode(res.getProperty("coxaDistal").toString(),Base64.DEFAULT));
			}
			if (res.hasProperty("antebracoOmbro")) {
				item.setAntebracoOmbroBitmap(Base64.decode(res.getProperty("antebracoOmbro").toString(),Base64.DEFAULT));
			}
			if (res.hasProperty("quadrilAbdomenPeito")) {
				item.setQuadrilAbdomenPeitoBitmap(Base64.decode(res.getProperty("quadrilAbdomenPeito").toString(),Base64.DEFAULT));
			}
			return item;
			
		}
			
	
	public int getCodAvaliacao() {
		return codAvaliacao;
	}
	public void setCodAvaliacao(int codAvaliacao) {
		this.codAvaliacao = codAvaliacao;
	}

	public String getBicepsContraido() {
		return bicepsContraido;
	}

	public void setBicepsContraido(String bicepsContraido) {
		this.bicepsContraido = bicepsContraido;
	}

	public String getBicepsRelaxado() {
		return bicepsRelaxado;
	}
	
	public void setBicepsRelaxado(String bicepsRelaxado) {
		this.bicepsRelaxado = bicepsRelaxado;
	}
	
	public String getPanturrilha() {
		return panturrilha;
	}

	public void setPanturrilha(String panturrilha) {
		this.panturrilha = panturrilha;
	}
	
	public String getCoxaProximal() {
		return coxaProximal;
	}

	public void setCoxaProximal(String coxaProximal) {
		this.coxaProximal = coxaProximal;
	}

	public String getCoxaMedial() {
		return coxaMedial;
	}

	public void setCoxaMedial(String coxaMedial) {
		this.coxaMedial = coxaMedial;
	}

	public String getCoxaDistal() {
		return coxaDistal;
	}

	public void setCoxaDistal(String coxaDistal) {
		this.coxaDistal = coxaDistal;
	}

	public String getAntebracoOmbro() {
		return antebracoOmbro;
	}

	public void setAntebracoOmbro(String antebracoOmbro) {
		this.antebracoOmbro = antebracoOmbro;
	}

	public String getQuadrilAbdomenPeito() {
		return quadrilAbdomenPeito;
	}

	public void setQuadrilAbdomenPeito(String quadrilAbdomenPeito) {
		this.quadrilAbdomenPeito = quadrilAbdomenPeito;
	}
	
	
	
	
	
	
		public byte[] getBicepsContraidoBitmap() {
		return bicepsContraidoFoto;
	}

	public void setBicepsContraidoBitmap(byte[] bicepsContraidoFoto) {
		this.bicepsContraidoFoto = bicepsContraidoFoto;
	}

	public byte[] getBicepsRelaxadoBitmap() {
		return bicepsRelaxadoFoto;
	}

	public void setBicepsRelaxadoBitmap(byte[] bicepsRelaxadoFoto) {
		this.bicepsRelaxadoFoto = bicepsRelaxadoFoto;
	}

	public byte[] getPanturrilhaBitmap() {
		return panturrilhaFoto;
	}

	public void setPanturrilhaBitmap(byte[] panturrilhaFoto) {
		this.panturrilhaFoto = panturrilhaFoto;
	}

	public byte[] getCoxaProximalBitmap() {
		return coxaProximalFoto;
	}

	public void setCoxaProximalBitmap(byte[] coxaProximalFoto) {
		this.coxaProximalFoto = coxaProximalFoto;
	}

	public byte[] getCoxaMedialBitmap() {
		return coxaMedialFoto;
	}

	public void setCoxaMedialBitmap(byte[] coxaMedialFoto) {
		this.coxaMedialFoto = coxaMedialFoto;
	}

	public byte[] getCoxaDistalBitmap() {
		return coxaDistalFoto;
	}

	public void setCoxaDistalBitmap(byte[] coxaDistalFoto) {
		this.coxaDistalFoto = coxaDistalFoto;
	}

	public byte[] getAntebracoOmbroBitmap() {
		return antebracoOmbroFoto;
	}

	public void setAntebracoOmbroBitmap(byte[] antebracoOmbroFoto) {
		this.antebracoOmbroFoto = antebracoOmbroFoto;
	}

	public byte[] getQuadrilAbdomenPeitoBitmap() {
		return quadrilAbdomenPeitoFoto;
	}

	public void setQuadrilAbdomenPeitoBitmap(byte[] quadrilAbdomenPeitoFoto) {
		this.quadrilAbdomenPeitoFoto = quadrilAbdomenPeitoFoto;
	}
	
	

	//KVM 

		@Override
		public Object getProperty(int arg0) {
			switch(arg0){
	        case 0:
	            return codAvaliacao;
	        case 1:
	            return bicepsContraido;
	        case 2:
	         return bicepsRelaxado;
	        case 3:
	        	return panturrilha;
	        case 4:
	           	return coxaProximal;
	        case 5:
	        	return coxaMedial;
	        case 6:
	        	return coxaDistal;
	        case 7: 
	        	return antebracoOmbro;
	        case 8: 
	        	return quadrilAbdomenPeito;
	        default: 
	        	return null;
	        		
	        }
		}


		@Override
		public int getPropertyCount() {
			return 9;
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
	            arg2.name = "bicepsContraido";
	            break;
	        case 2:
	            arg2.type = PropertyInfo.STRING_CLASS;
	            arg2.name = "bicepsRelaxado";
	            break;
	        case 3:
	            arg2.type = PropertyInfo.STRING_CLASS;
	            arg2.name = "panturrilha";
	            break;
	        case 4:
	            arg2.type = PropertyInfo.STRING_CLASS;
	            arg2.name = "coxaProximal";
	            break;
	        case 5:
	            arg2.type = PropertyInfo.STRING_CLASS;
	            arg2.name = "coxaMedial";
	            break;
	        case 6:
	            arg2.type = PropertyInfo.STRING_CLASS;
	            arg2.name = "coxaDistal";
	            break;
	        case 7:
	            arg2.type = PropertyInfo.STRING_CLASS;
	            arg2.name = "antebracoOmbro";
	            break;
	        case 8:
	            arg2.type = PropertyInfo.STRING_CLASS;
	            arg2.name = "quadrilAbdomenPeito";
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
	        	setBicepsContraido(arg1.toString());
	            break;
	        case 2:
	            setBicepsRelaxado(arg1.toString());
	            break;
	        case 3:
	        	setPanturrilha(arg1.toString());
	            break;
	        case 4:
	        	setCoxaProximal(arg1.toString());
	            break;
	        case 5:
	        	setCoxaMedial(arg1.toString());
	            break;
	        case 6:
	        	setCoxaDistal(arg1.toString());
	            break;
	        case 7:
	        	setAntebracoOmbro(arg1.toString());
	            break;
	        case 8:
	        	setQuadrilAbdomenPeito(arg1.toString());
	            break;
	        default:
	            break;
	        }
		}
	
	
	
	
}
