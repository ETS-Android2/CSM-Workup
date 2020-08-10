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

import android.util.Log;
import br.com.Utilitarios.WebService;



public class Notificacoes implements KvmSerializable{
    private int codNotificacao;
    private String origemNotificacao;
    private String destinoNotificacao;
    private String tipoNotificacao;
    private String descricaoNotificacao;
    private int visualizada;
    
    //atributos auxiliares
    private boolean retorno;
    private int retornoNumeroDeNotificacoes;
    

    //Construtor
    public Notificacoes(int codNotificacao, String origemNotificacao, String destinoNotificacao, String tipoNotificacao, String descricaoNotificacao, int visualizada) {
        this.codNotificacao = codNotificacao;
        this.origemNotificacao = origemNotificacao;
        this.destinoNotificacao = destinoNotificacao;
        this.tipoNotificacao = tipoNotificacao;
        this.descricaoNotificacao = descricaoNotificacao;
        this.visualizada = visualizada;
    }
    
    public Notificacoes(){
        
    }

    //ToString
    @Override
    public String toString() {
        return "Notificacoes{" + "codNotificacao=" + codNotificacao + ", origemNotificacao=" + origemNotificacao + ", destinoNotificacao=" + destinoNotificacao + ", tipoNotificacao=" + tipoNotificacao + ", descricaoNotificacao=" + descricaoNotificacao + ", visualizada=" + visualizada + '}';
    }
    
    
    //CUD
    public boolean visualizarNotificacao(int codNotificacao){
    	clear();
		final Notificacoes a = new Notificacoes();
		a.setCodNotificacao(codNotificacao);
		
		
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"visualizarNotificacao");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Notificacao");
				p1.setValue(a);
				p1.setType(new Notificacoes().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				//envelope.addMapping(WebService.getNamespace(), "Personal",new Personal().getClass());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("visualizarNotificacao"), envelope);
					
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: AtualizarPersonalWeb", e.toString());
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
    
    public int buscarNotificacoesPendentesPersonal(String usuarioPersonal){
    	clear();
		final Personal a = new Personal();
		a.setUsuario(usuarioPersonal);
	
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"tenhoNotificacoesPersonal");
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
						ht.call(WebService.getSoapAction("tenhoNotificacoesPersonal"), envelope);
						
						SoapPrimitive res = (SoapPrimitive) envelope.getResponse();
						
						retornoNumeroDeNotificacoes = Integer.parseInt(res.toString());
					 
					         
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("Erro: receber notificacoes do Personal ", e.toString());
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
			return retornoNumeroDeNotificacoes;
    }
    
    public int buscarNotificacoesPendentesAluno(String usuarioAluno){
    	clear();
		final Aluno a = new Aluno();
		a.setUsuario(usuarioAluno);
	
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"tenhoNotificacoesAluno");
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
						ht.call(WebService.getSoapAction("tenhoNotificacoesAluno"), envelope);
						
						SoapPrimitive res = (SoapPrimitive) envelope.getResponse();
						
						retornoNumeroDeNotificacoes = Integer.parseInt(res.toString());
					 
					         
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("erro: receber notificacoes dos alunos", e.toString());
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
			return retornoNumeroDeNotificacoes;
    }
    
    public ArrayList<Notificacoes> getNotificacoesPendentesPersonal(String usuarioPersonal) throws SQLException{
    	clear();
		final Personal a = new Personal();
		final ArrayList<Notificacoes> retornoListaNotificacao = new ArrayList<Notificacoes>();
		a.setUsuario(usuarioPersonal);
		
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"getNotificacoesPendentesPersonal");
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
						ht.call(WebService.getSoapAction("getNotificacoesPendentesPersonal"), envelope);
						
						SoapObject res = new SoapObject();
						Vector<SoapObject> response = new Vector<SoapObject>();
						
						try{
							res = (SoapObject) envelope.getResponse();
							//Log.i("debug", "cheguei aki --- > " + res.toString());
							
									
						}catch(Exception e){
							Log.i("Erro: buscarNotificacoesWeb (Unico)", e.toString());
						}
						
						try{
							
							response = (Vector<SoapObject>) envelope.getResponse();
							//Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
							
						}catch(Exception e){
							Log.i("Erro: buscarNotificacoesWeb (vetor)", e.toString());
						}

						if(response.isEmpty()){
							
							 	Notificacoes item = getSoapNotificacao(res);
						      //  Log.i("alunos", item.toString());
						        retornoListaNotificacao.add(item);
						         
						}else{
							
							for(SoapObject soapNotificacao: response){  
						         Notificacoes item = getSoapNotificacao(soapNotificacao);
						      //   Log.i("alunos", item.toString());
						         retornoListaNotificacao.add(item);			       
					           }
							
						}
					         
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
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
			return retornoListaNotificacao;
    }
    
    public ArrayList<Notificacoes> getNotificacoesPendentesAluno(String usuarioAluno) throws SQLException{
    	clear();
		final Aluno a = new Aluno();
		final ArrayList<Notificacoes> retornoListaNotificacao = new ArrayList<Notificacoes>();
		a.setUsuario(usuarioAluno);
		
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"getNotificacoesPendentesAluno");
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
						ht.call(WebService.getSoapAction("getNotificacoesPendentesAluno"), envelope);
						Log.i("envelope",envelope.getResponse().toString());
						SoapObject res = new SoapObject();
						Vector<SoapObject> response = new Vector<SoapObject>();
						
						try{
							res = (SoapObject) envelope.getResponse();
							//Log.i("debug", "cheguei aki --- > " + res.toString());
							
									
						}catch(Exception e){
							Log.i("Erro: buscar Notificacoes (Unico)", e.toString());
						}
						
						try{
							
							response = (Vector<SoapObject>) envelope.getResponse();
							//Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
							
						}catch(Exception e){
							Log.i("Erro: buscar Notificacoes (vetor)", e.toString());
						}

						if(response.isEmpty()){
							
							 	Notificacoes item = getSoapNotificacao(res);
						      //  Log.i("alunos", item.toString());
						        retornoListaNotificacao.add(item);
						         
						}else{
							
							for(SoapObject soapNotificacao: response){  
						         Notificacoes item = getSoapNotificacao(soapNotificacao);
						      //   Log.i("alunos", item.toString());
						         retornoListaNotificacao.add(item);			       
					           }
							
						}
					         
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
						Log.e("erro: get Notificacoes Pendentes dos alunos", e.toString());
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
			return retornoListaNotificacao;
    }
    
    
    
    public void clear(){
    	retorno = false;
    }

    
    public Notificacoes getSoapNotificacao(SoapObject res){
		Notificacoes item = new Notificacoes();
		if (res.hasProperty("codNotificacao")) {
            item.setCodNotificacao(Integer.parseInt(res.getPropertyAsString("codNotificacao")));
		}
	     if (res.hasProperty("origemNotificacao")) {
	            item.setOrigemNotificacao(res.getPropertyAsString("origemNotificacao"));
	     }
	     if (res.hasProperty("destinoNotificacao")) {
	            item.setDestinoNotificacao(res.getPropertyAsString("destinoNotificacao"));
	     }
	     if (res.hasProperty("tipoNotificacao")) {
	            item.setTipoNotificacao(res.getPropertyAsString("tipoNotificacao"));
	     }
	     if (res.hasProperty("descricaoNotificacao")) {
	            item.setDescricaoNotificacao(res.getPropertyAsString("descricaoNotificacao"));
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
			            return getCodNotificacao();
			        case 1:
			            return getOrigemNotificacao();
			        case 2:
			         return getDescricaoNotificacao();
			        case 3:
			        	return getTipoNotificacao();
			        case 4:
			           	return getDescricaoNotificacao();
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
            arg2.name = "codNotificacao";
            break;
        case 1:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "origemNotificacao";
            break;
        case 2:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "destinoNotificacao";
            break;
        case 3:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "tipoNotificacao";
            break;
        case 4:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "descricaoNotificacao";
            break;
        case 5:
            arg2.type = PropertyInfo.INTEGER_CLASS;
            arg2.name = "visualizada";
            break;
        default:break;
        }
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		switch(arg0){
        case 0:
            setCodNotificacao(Integer.parseInt(arg1.toString()));
            break;
        case 1:
        	setOrigemNotificacao(arg1.toString());
            break;
        case 2:
            setDestinoNotificacao(arg1.toString());
            break;
        case 3:
            setTipoNotificacao(arg1.toString());
            break;
        case 4:
            setDescricaoNotificacao(arg1.toString());
            break;
        case 5:
            setVisualizada(Integer.parseInt(arg1.toString()));
            break;
        default:
            break;
        }

		
	}
	

    //Gets and Sets
    
    
    public int getCodNotificacao() {
        return codNotificacao;
    }

    public void setCodNotificacao(int codNotificacao) {
        this.codNotificacao = codNotificacao;
    }

    public String getOrigemNotificacao() {
        return origemNotificacao;
    }

    public void setOrigemNotificacao(String origemNotificacao) {
        this.origemNotificacao = origemNotificacao;
    }

    public String getDestinoNotificacao() {
        return destinoNotificacao;
    }

    public void setDestinoNotificacao(String destinoNotificacao) {
        this.destinoNotificacao = destinoNotificacao;
    }

    public String getTipoNotificacao() {
        return tipoNotificacao;
    }

    public void setTipoNotificacao(String tipoNotificacao) {
        this.tipoNotificacao = tipoNotificacao;
    }

    public String getDescricaoNotificacao() {
        return descricaoNotificacao;
    }

    public void setDescricaoNotificacao(String descricaoNotificacao) {
        this.descricaoNotificacao = descricaoNotificacao;
    }

    public int getVisualizada() {
        return visualizada;
    }

    public void setVisualizada(int visualizada) {
        this.visualizada = visualizada;
    }
    
}
