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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.database.Cursor;
import android.util.Base64;
import android.util.Log;
import br.com.Banco.Banco;
import br.com.Utilitarios.WebService;

public class Atualizacoes implements KvmSerializable {
	private int codAtualizacao;
	private String localAtualizacao;
	private String destinoAtualizacao;
	private String tipoAtualizacao;
	private String extra;
	private int codAtualizacaoServidor;
	private int visualizada;
	
	
	private boolean retorno;
	private int retornoNumeroDeAtualizacoes;
	
	
	
	public Atualizacoes(){
		
	}
	
	public Atualizacoes(int codAtualizacao, String localAtualizacao,
			String destinoAtualizacao, String tipoAtualizacao,String extra,int codAtualizacaoServidor, int visualizada) {
		super();
		this.codAtualizacao = codAtualizacao;
		this.localAtualizacao = localAtualizacao;
		this.destinoAtualizacao = destinoAtualizacao;
		this.tipoAtualizacao = tipoAtualizacao;
		this.extra = extra;
		this.codAtualizacaoServidor = codAtualizacaoServidor;
		this.visualizada = visualizada;
	}
	
    
  
    
    
  
	@Override
	public String toString() {
		return "Atualizacoes [codAtualizacao=" + codAtualizacao
				+ ", localAtualizacao=" + localAtualizacao
				+ ", destinoAtualizacao=" + destinoAtualizacao
				+ ", tipoAtualizacao=" + tipoAtualizacao + ", extra=" + extra
				+ ", codAtualizacaoServidor=" + codAtualizacaoServidor
				+ ", visualizada=" + visualizada + ", retorno=" + retorno
				+ ", retornoNumeroDeAtualizacoes="
				+ retornoNumeroDeAtualizacoes + "]";
	}
	
	

	//CUD Local
    public boolean visualizarAtualizacoes(Banco b,int codAtualizacao){
    	String SQL = "update atualizacoes set visualizada = 1 where codAtualizacao = " + codAtualizacao;
    	try{
    		b.execSQL(SQL);
    		return true;
    	}catch(Exception ex){
    		Log.i("Erro: visualizar Atualização", ex.toString());
    		return false;
    	}
    }
    
    public boolean salvarAtualizacao(Banco b){
    	String SQL = "INSERT INTO atualizacoes ("
    			+ "localAtualizacao,"
    			+ "destinoAtualizacao,"
    			+ "tipoAtualizacao,"
    			+ "extra,"
    			+ "codAtualizacaoServidor,"
    			+ "visualizada) VALUES ('"
    			+ localAtualizacao + "','"
    			+ destinoAtualizacao + "','"
    			+ tipoAtualizacao + "','"
    			+ extra + "',"
    			+ codAtualizacaoServidor + ","
    			+ visualizada + ");";
    	
    	try{
    		b.execSQL(SQL);
    		return true;
    	}catch(Exception ex){
    		ex.printStackTrace();
    		return false;
    	}
    			
    }
    
    
    //buscas Local
    public int buscarAtualizacoesPendentesPersonal(Banco b,String usuarioPersonal){
    	String SQL = "Select * from atualizacoes where destinoAtualizacao = '" + usuarioPersonal + "' "
                + " and visualizada = 0";
    	
		Cursor dataset = b.querySQL(SQL);
	
		return dataset.getCount();
    }
    
    public int buscarAtualizacoesPendentesAluno(Banco b,String usuarioAluno){
    	String SQL = "Select * from atualizacoes where destinoAtualizacao = '" + usuarioAluno + "' "
                 + " and visualizada = 0";
  
		Cursor dataset = b.querySQL(SQL);
	
		return dataset.getCount();
    }
    
    public ArrayList<Atualizacoes> getAtualizacoesPendentesPersonal(Banco b,String usuarioPersonal){
    	String SQL = "Select * from atualizacoes where destinoAtualizacao = '" + usuarioPersonal + "' "
                + " and visualizada = 0";
 ArrayList<Atualizacoes> atualizacoes = new ArrayList<Atualizacoes>();
		
		Cursor dataset = b.querySQL(SQL);
		
		
		
		int col_codAtualizacao = dataset.getColumnIndex("codAtualizacao");
		int col_localAtualizacao = dataset.getColumnIndex("localAtualizacao");
		int col_destinoAtualizacao = dataset.getColumnIndex("destinoAtualizacao");
		int col_tipoAtualizacao = dataset.getColumnIndex("tipoAtualizacao");
		int col_extra = dataset.getColumnIndex("extra");
		int col_codAtualizacaoServidor = dataset.getColumnIndex("codAtualizacaoServidor");
		int col_visualizada = dataset.getColumnIndex("visualizada");
		
		int numRows = dataset.getCount();
		
		//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
		dataset.moveToFirst();
		
		for(int c=0; c<numRows; c++) {
			int codAtualizacao = dataset.getInt(col_codAtualizacao);
			String localAtualizacao = dataset.getString(col_localAtualizacao);
			String destinoAtualizacao = dataset.getString(col_destinoAtualizacao);
			String tipoAtualizacao = dataset.getString(col_tipoAtualizacao);
			String extra = dataset.getString(col_extra);
			int codAtualizacaoServidor = dataset.getInt(col_codAtualizacaoServidor);
			int visualizada = dataset.getInt(col_visualizada);
			
			
			Atualizacoes a = new Atualizacoes(codAtualizacao,localAtualizacao,
					destinoAtualizacao,tipoAtualizacao,extra,codAtualizacaoServidor,visualizada);
			
			dataset.moveToNext();
			atualizacoes.add(a);
		}
		return atualizacoes;
    }
    
    public ArrayList<Atualizacoes> getAtualizacoesPendentesAluno(Banco b,String usuarioAluno){
    	 String SQL = "Select * from atualizacoes where destinoAtualizacao = '" + usuarioAluno + "' "
                 + " and visualizada = 0";
  
    	
    	ArrayList<Atualizacoes> atualizacoes = new ArrayList<Atualizacoes>();
		
		Cursor dataset = b.querySQL(SQL);
		
		
		
		int col_codAtualizacao = dataset.getColumnIndex("codAtualizacao");
		int col_localAtualizacao = dataset.getColumnIndex("localAtualizacao");
		int col_destinoAtualizacao = dataset.getColumnIndex("destinoAtualizacao");
		int col_tipoAtualizacao = dataset.getColumnIndex("tipoAtualizacao");
		int col_extra = dataset.getColumnIndex("extra");
		int col_codAtualizacaoServidor = dataset.getColumnIndex("codAtualizacaoServidor");
		int col_visualizada = dataset.getColumnIndex("visualizada");
		
		int numRows = dataset.getCount();
		
		//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
		dataset.moveToFirst();
		
		for(int c=0; c<numRows; c++) {
			int codAtualizacao = dataset.getInt(col_codAtualizacao);
			String localAtualizacao = dataset.getString(col_localAtualizacao);
			String destinoAtualizacao = dataset.getString(col_destinoAtualizacao);
			String tipoAtualizacao = dataset.getString(col_tipoAtualizacao);
			String extra = dataset.getString(col_extra);
			int codAtualizacaoServidor = dataset.getInt(col_codAtualizacaoServidor);
			int visualizada = dataset.getInt(col_visualizada);
			
			
			Atualizacoes a = new Atualizacoes(codAtualizacao,localAtualizacao,
					destinoAtualizacao,tipoAtualizacao,extra,codAtualizacaoServidor,visualizada);
			
			dataset.moveToNext();
			atualizacoes.add(a);
		}
		return atualizacoes;
    }
    
  //CUD
    public boolean visualizarAtualizacoesWeb(int codAtualizacao){
    	clear();
		final Atualizacoes a = new Atualizacoes();
		a.setCodAtualizacao(codAtualizacao);
		
		
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"visualizarAtualizacoes");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Atualizacoes");
				p1.setValue(a);
				p1.setType(new Atualizacoes().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				//envelope.addMapping(WebService.getNamespace(), "Personal",new Personal().getClass());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("visualizarAtualizacoes"), envelope);
					
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: visualizarAtualizacoesWeb", e.toString());
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
    
    //Buscas
    
    public int buscarAtualizacoesPendentesPersonalWeb(String usuarioPersonal){
    	clear();
		final Personal a = new Personal();
		a.setUsuario(usuarioPersonal);
	
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"tenhoAtualizacoesPersonal");
					PropertyInfo p1 = new PropertyInfo();
					p1.setName("Personal");
					p1.setValue(a);
					p1.setType(new Personal().getClass());
					
					request.addProperty(p1);
					
		
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					envelope.addMapping(WebService.getNamespace(), "Personal",new Personal().getClass());
					
					HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
					
					
					try{
						ht.call(WebService.getSoapAction("tenhoAtualizacoesPersonal"), envelope);
						
						SoapPrimitive res = (SoapPrimitive) envelope.getResponse();
						
						retornoNumeroDeAtualizacoes = Integer.parseInt(res.toString());
					 
					         
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("Erro: receber atualizacoes Personal ", e.toString());
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
			return retornoNumeroDeAtualizacoes;
    }
    
    public int buscarAtualizacoesPendentesAlunoWeb(String usuarioAluno){
    	clear();
		final Aluno a = new Aluno();
		a.setUsuario(usuarioAluno);
	
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"tenhoAtualizacoesAluno");
					PropertyInfo p1 = new PropertyInfo();
					p1.setName("Aluno");
					p1.setValue(a);
					p1.setType(new Aluno().getClass());
					
					request.addProperty(p1);
					
		
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					envelope.addMapping(WebService.getNamespace(), "Aluno",new Aluno().getClass());
					
					HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
					
					
					try{
						ht.call(WebService.getSoapAction("tenhoAtualizacoesAluno"), envelope);
						
						SoapPrimitive res = (SoapPrimitive) envelope.getResponse();
						
						retornoNumeroDeAtualizacoes = Integer.parseInt(res.toString());
					 
					         
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("erro: receber atualizacoes dos alunos", e.toString());
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
			return retornoNumeroDeAtualizacoes;
    }
    
    public ArrayList<Atualizacoes> getAtualizacoesPendentesPersonalWeb(String usuarioPersonal) throws SQLException{
    	clear();
		final Personal a = new Personal();
		final ArrayList<Atualizacoes> retornoListaAtualizacoes = new ArrayList<Atualizacoes>();
		a.setUsuario(usuarioPersonal);
		
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"getAtualizacoesPendentesPersonal");
					PropertyInfo p1 = new PropertyInfo();
					p1.setName("Personal");
					p1.setValue(a);
					p1.setType(new Personal().getClass());
					
					request.addProperty(p1);
					
		
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					envelope.addMapping(WebService.getNamespace(), "Personal",new Personal().getClass());
					
					HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
					
					
					try{
						ht.call(WebService.getSoapAction("getAtualizacoesPendentesPersonal"), envelope);
						
						SoapObject res = new SoapObject();
						Vector<SoapObject> response = new Vector<SoapObject>();
						
						try{
							res = (SoapObject) envelope.getResponse();
							//Log.i("debug", "cheguei aki --- > " + res.toString());
							
									
						}catch(Exception e){
							Log.i("Erro: getatualizacoesPersonal (Unico)", e.toString());
						}
						
						try{
							
							response = (Vector<SoapObject>) envelope.getResponse();
							//Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
							
						}catch(Exception e){
							e.printStackTrace();
							Log.i("Erro: getAtualizacoesPersonal (vetor)", e.toString());
						}

						if(response.isEmpty()){
							
							 	Atualizacoes item = getSoapAtualizacoes(res);
						      //  Log.i("alunos", item.toString());
						        retornoListaAtualizacoes.add(item);
						        Log.i("atualizacao", item.toString());
						         
						}else{
							
							for(SoapObject soapAtualizacao: response){  
						         Atualizacoes item = getSoapAtualizacoes(soapAtualizacao);
						         Log.i("alunos", item.toString());
						         retornoListaAtualizacoes.add(item);			       
					           }
							
						}
					         
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
						Log.e("erro: receber notificacoes Personal", e.toString());
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
			return retornoListaAtualizacoes;
    }
    
    public ArrayList<Atualizacoes> getAtualizacoesPendentesAlunoWeb(String usuarioAluno) throws SQLException{
    	clear();
		final Aluno a = new Aluno();
		final ArrayList<Atualizacoes> retornoListaAtualizacoes = new ArrayList<Atualizacoes>();
		a.setUsuario(usuarioAluno);
		
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"getAtualizacoesPendentesAluno");
					PropertyInfo p1 = new PropertyInfo();
					p1.setName("Aluno");
					p1.setValue(a);
					p1.setType(new Aluno().getClass());
					
					request.addProperty(p1);
					
		
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					envelope.addMapping(WebService.getNamespace(), "Aluno",new Aluno().getClass());
					
					HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
					ht.debug =true;
					
					try{
						ht.call(WebService.getSoapAction("getAtualizacoesPendentesAluno"), envelope);
						
						Log.i("envelope","o envelope esta "+ envelope.toString());
						SoapObject res = new SoapObject();
						Vector<SoapObject> response = new Vector<SoapObject>();
						
						try{
							res = (SoapObject) envelope.getResponse();
							//Log.i("debug", "cheguei aki --- > " + res.toString());
							
									
						}catch(Exception e){
							Log.i("Erro: Get atualizacoes (Unico)", e.toString());
						}
						
						try{
							
							response = (Vector<SoapObject>) envelope.getResponse();
							//Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
							
						}catch(Exception e){
							Log.i("Erro: buscar Notificacoes (vetor)", e.toString());
						}

						if(response.isEmpty()){
							
							 	Atualizacoes item = getSoapAtualizacoes(res);
						      //  Log.i("alunos", item.toString());
						        retornoListaAtualizacoes.add(item);
						         
						}else{
							
							for(SoapObject soapAtualizacoes: response){  
						         Atualizacoes item = getSoapAtualizacoes(soapAtualizacoes);
						      //   Log.i("alunos", item.toString());
						         retornoListaAtualizacoes.add(item);			       
					           }
							
						}
					         
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
						Log.e("erro: get Atualizacoes Pendentes dos alunos", e.toString());
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
			return retornoListaAtualizacoes;
    }
    
    
    
    public void clear(){
    	retorno = false;
    }

    //get Soap
	public Atualizacoes getSoapAtualizacoes(SoapObject res){
		Atualizacoes item = new Atualizacoes();
		if (res.hasProperty("codAtualizacao")) {
	        item.setCodAtualizacao(Integer.parseInt(res.getPropertyAsString("codAtualizacao")));
		}
	     if (res.hasProperty("localAtualizacao")) {
	            item.setLocalAtualizacao(res.getPropertyAsString("localAtualizacao"));
	     }
	     if (res.hasProperty("destinoAtualizacao")) {
	            item.setDestinoAtualizacao(res.getPropertyAsString("destinoAtualizacao"));
	     }
	     if (res.hasProperty("tipoAtualizacao")) {
	            item.setTipoAtualizacao(res.getPropertyAsString("tipoAtualizacao"));
	     }
	     if (res.hasProperty("extra")) {
	            item.setExtra(res.getPropertyAsString("extra"));
	     }
	     if (res.hasProperty("codAtualizacao")) {
		        item.setCodAtualizacaoServidor(Integer.parseInt(res.getPropertyAsString("codAtualizacao")));
	     }
	     
	     if (res.hasProperty("visualizada")) {
	            item.setVisualizada(Integer.parseInt(res.getPropertyAsString("visualizada")));
	     }
	     return item;
	}
	
	
	//KVM
	@Override
	public Object getProperty(int arg0) {
			  switch(arg0){
			        case 0:
			            return getCodAtualizacao();
			        case 1:
			            return getLocalAtualizacao();
			        case 2:
			         return getDestinoAtualizacao();
			        case 3:
			        	return getTipoAtualizacao();
			        case 4:
			        	return getExtra();
			        case 5:
			           	return getVisualizada();
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
            arg2.name = "codAtualizacao";
            break;
        case 1:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "localAtualizacao";
            break;
        case 2:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "destinoAtualizacao";
            break;
        case 3:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "tipoAtualizacao";
            break;
        case 4:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "extra";
            break;
        case 5:
            arg2.type = PropertyInfo.INTEGER_CLASS;
            arg2.name = "visualizada";
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
            setCodAtualizacao(Integer.parseInt(arg1.toString()));
            break;
        case 1:
        	setLocalAtualizacao(arg1.toString());
            break;
        case 2:
            setDestinoAtualizacao(arg1.toString());
            break;
        case 3:
            setTipoAtualizacao(arg1.toString());
            break;
        case 4:
            setExtra(arg1.toString());
            break;
        case 5:
            setVisualizada(Integer.parseInt(arg1.toString()));
            break;
        default:
            break;
        }

		
	}
	
	public int getCodAtualizacao() {
		return codAtualizacao;
	}
	public void setCodAtualizacao(int codAtualizacao) {
		this.codAtualizacao = codAtualizacao;
	}
	public String getLocalAtualizacao() {
		return localAtualizacao;
	}
	public void setLocalAtualizacao(String localAtualizacao) {
		this.localAtualizacao = localAtualizacao;
	}
	public String getDestinoAtualizacao() {
		return destinoAtualizacao;
	}
	public void setDestinoAtualizacao(String destinoAtualizacao) {
		this.destinoAtualizacao = destinoAtualizacao;
	}
	public String getTipoAtualizacao() {
		return tipoAtualizacao;
	}
	public void setTipoAtualizacao(String tipoAtualizacao) {
		this.tipoAtualizacao = tipoAtualizacao;
	}
	public String getExtra(){
		return extra;
	}
	public void setExtra(String extra){
		this.extra = extra;
	}
	public int getVisualizada() {
		return visualizada;
	}
	public void setVisualizada(int visualizada) {
		this.visualizada = visualizada;
	}
	public int getCodAtualizacaoServidor() {
		return codAtualizacaoServidor;
	}
	public void setCodAtualizacaoServidor(int codAtualizacaoServidor) {
		this.codAtualizacaoServidor = codAtualizacaoServidor;
	}
	
	
	
	
	
}
