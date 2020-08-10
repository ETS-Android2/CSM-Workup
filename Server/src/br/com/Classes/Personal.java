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


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import br.com.Banco.*;
import br.com.Utilitarios.WebService;

public class Personal extends Usuario implements KvmSerializable{
	
	//Atributos auxiliares
	private static boolean retorno;
	private static byte[] fotoPersonal;
	private static Personal retornoPersonal;
	private static ArrayList<Personal> retornoListaPersonal = new ArrayList<Personal>();
	private static ArrayList<String> retornoListaNomes = new ArrayList<String>();
	
	
	
	//Construtores
	public Personal(String telefone, String nome, String dataDeNascimento,
			String email, String sexo, String usuario, String senha,String foto) {
		super(telefone, nome, dataDeNascimento, email, sexo, usuario, senha,foto);
	}

	public Personal(){
	
	}
	
	
	//CUD local
	public Boolean salvar(Banco b,byte[] fotoPersonal){
		/*String SQL = "INSERT INTO Personal (telefonePersonal,nomePersonal,dataDeNascimentoPersonal," +
				"emailPersonal,sexoPersonal,senhaPersonal,usuarioPersonal,fotoPersonal) " + 
				"VALUES('" + super.getTelefone() + "','"+ super.getNome() + "','" + super.getDataDeNascimento() +
				"','" + super.getEmail() + "','" + super.getSexo() + "','"  +
				super.getSenha() + "','" 	+ super.getUsuario() + "', '" + super.getFoto() + "')";
				*/
		
		  String SQL = "INSERT INTO Personal ("
                  + "telefonePersonal,"
                  + "nomePersonal,"
                  + "dataDeNascimentoPersonal," 
                  + "emailPersonal,"
                  + "sexoPersonal,"
                  + "senhaPersonal,"
                  + "usuarioPersonal,"
                  + "fotoPersonal,"
                  + "ativo) values "
                  + "(?, ?, ?, ? , ?, ? ,? ,? ,?)";
           
          
          SQLiteStatement statement = b.getWritableDatabase().compileStatement(SQL);
          statement.bindString(1, super.getTelefone());
          statement.bindString(2, super.getNome());
          statement.bindString(3, super.getDataDeNascimento());
          statement.bindString(4, super.getEmail());
          statement.bindString(5, super.getSexo());
          statement.bindString(6, super.getSenha());
          statement.bindString(7, super.getUsuario());
          statement.bindBlob(8, fotoPersonal);
          statement.bindString(9, "ativado");
          
         
          
          /*String SQL = "INSERT INTO Personal (telefonePersonal,nomePersonal,dataDeNascimentoPersonal," +
                          "emailPersonal,sexoPersonal,senhaPersonal,usuarioPersonal,fotoPersonal,ativo) " + 
                          "VALUES('" + super.getTelefone()+ "','"+ super.getNome() + "','" + super.getDataDeNascimento() +
                          "','" + super.getEmail() + "','" + super.getSexo() + "','"  +
                          super.getSenha() + "','" 	+ super.getUsuario() + "','"+ foto + "','ativado');";
        //  System.out.println(SQL);*/
          try{
              statement.executeInsert();
              return true;
          }catch(Exception ex){
              //System.out.println(ex.toString());
              ex.printStackTrace();
              return false;
          }
	}
	
	public Boolean atualizar(Banco b, byte[] fotoPersonal){
		
		
		  String SQL = "UPDATE Personal set "
		          + "telefonePersonal = ?,"
		          + "nomePersonal = ?,"
		          + "dataDeNascimentoPersonal = ?," 
		          + "emailPersonal = ?,"
		          + "sexoPersonal = ?,"
		          + "senhaPersonal = ?,"
		          + "usuarioPersonal = ?,"
		          + "fotoPersonal = ?,"
		          + "ativo = ?";
		   
		  
		  SQLiteStatement statement = b.getWritableDatabase().compileStatement(SQL);
		  statement.bindString(1, super.getTelefone());
		  statement.bindString(2, super.getNome());
		  statement.bindString(3, super.getDataDeNascimento());
		  statement.bindString(4, super.getEmail());
		  statement.bindString(5, super.getSexo());
		  statement.bindString(6, super.getSenha());
		  statement.bindString(7, super.getUsuario());
		  statement.bindBlob(8, fotoPersonal);
		  statement.bindString(9, "ativado");
		  
		 
		  
		  /*String SQL = "INSERT INTO Personal (telefonePersonal,nomePersonal,dataDeNascimentoPersonal," +
		                  "emailPersonal,sexoPersonal,senhaPersonal,usuarioPersonal,fotoPersonal,ativo) " + 
		                  "VALUES('" + super.getTelefone()+ "','"+ super.getNome() + "','" + super.getDataDeNascimento() +
		                  "','" + super.getEmail() + "','" + super.getSexo() + "','"  +
		                  super.getSenha() + "','" 	+ super.getUsuario() + "','"+ foto + "','ativado');";
		//  System.out.println(SQL);*/
		  try{
		      statement.executeUpdateDelete();
		      return true;
		  }catch(Exception ex){
		      //System.out.println(ex.toString());
		      ex.printStackTrace();
		      return false;
		  }
	}
	
	public Boolean atualizarFotoPersonal(Banco b, byte[] fotoPersonal){
		
		
		  String SQL = "UPDATE Personal set "
		          + "fotoPersonal = ? "
		          + "where usuario = ?";
		   
		  
		  SQLiteStatement statement = b.getWritableDatabase().compileStatement(SQL);
		  statement.bindBlob(1, fotoPersonal);
		  statement.bindString(2, super.getUsuario());
		  
		  try{
		      statement.executeUpdateDelete();
		      return true;
		  }catch(Exception ex){
		      //System.out.println(ex.toString());
		      ex.printStackTrace();
		      return false;
		  }
	}
	
	
	public boolean atualizarDadosPessoais(Banco b){
		
		
		String SQL = "UPDATE Personal set "
				+ "senha = ? "
				+ " where usuarioPersonal= ?";
		
		SQLiteStatement statement = b.getWritableDatabase().compileStatement(SQL);
		  statement.bindString(1, getSenha());
		  statement.bindString(2, getUsuario());
		  
		  try{
		      statement.executeUpdateDelete();
		      return true;
		  }catch(Exception ex){
		      ex.printStackTrace();
		      return false;
		  }
	}
	
	public Boolean editarFotoPersonal(Banco b,byte[] fotoPersonal){
		
		  String SQL = "UPDATE Personal set "
				  + "fotoPersonal = ? "
                  + " where usuarioPersonal = ?";
                
          SQLiteStatement statement = b.getWritableDatabase().compileStatement(SQL);
          statement.bindString(1, super.getUsuario());
          statement.bindBlob(2, fotoPersonal);
          
         
          try{
              statement.executeUpdateDelete();
              return true;
          }catch(Exception ex){
              //System.out.println(ex.toString());
              ex.printStackTrace();
              return false;
          }
	}
	
	
	
	//BUSCAS local
	
	public ArrayList<Personal> buscarPersonais(Banco b, String filtro){
		
		
		String SQL = "SELECT * FROM Personal where usuarioPersonal like '%" + filtro + "%'";
		
		Cursor dataset = b.querySQL(SQL);
		
		ArrayList<Personal> personal = new ArrayList<Personal>();

		
		
		int col_telefonePersonal = dataset.getColumnIndex("telefonePersonal");
		int col_fotoPersonal = dataset.getColumnIndex("fotoPersonal");
		int col_nomePersonal = dataset.getColumnIndex("nomePersonal");
		int col_dataDeNascimentoPersonal = dataset.getColumnIndex("dataDeNascimentoPersonal");
		int col_emailPersonal = dataset.getColumnIndex("emailPersonal");
		int col_sexoPersonal = dataset.getColumnIndex("sexoPersonal");
		int col_senhaPersonal = dataset.getColumnIndex("senhaPersonal");
		int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
	
		int numRows = dataset.getCount();
		
		//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
		dataset.moveToFirst();
		
		for(int c=0; c<numRows; c++) {
			
			String telefonePersonal = dataset.getString(col_telefonePersonal);
			String nomePersonal = dataset.getString(col_nomePersonal);
			String dataDeNascimentoPersonal = dataset.getString(col_dataDeNascimentoPersonal);
			String emailPersonal = dataset.getString(col_emailPersonal);
			String sexoPersonal = dataset.getString(col_sexoPersonal);
			String senhaPersonal = dataset.getString(col_senhaPersonal);
			String usuarioPersonal = dataset.getString(col_usuarioPersonal);
			byte[] encode = dataset.getBlob(col_fotoPersonal);
			String fotoPersonal = Base64.encodeToString(encode, 0);
			
			
			Personal p = new Personal(telefonePersonal,nomePersonal,dataDeNascimentoPersonal,emailPersonal,
					sexoPersonal, usuarioPersonal, senhaPersonal,fotoPersonal);
			
			dataset.moveToNext();
			personal.add(p);
		}
		return personal;
		
	}

	public Personal buscarPersonal(Banco b, String filtro){
	
	
	String SQL = "SELECT * FROM Personal where usuarioPersonal = '" + filtro + "'";
	
	Cursor dataset = b.querySQL(SQL);
	
	
	
	int col_telefonePersonal = dataset.getColumnIndex("telefonePersonal");
	int col_fotoPersonal = dataset.getColumnIndex("fotoPersonal");
	int col_nomePersonal = dataset.getColumnIndex("nomePersonal");
	int col_dataDeNascimentoPersonal = dataset.getColumnIndex("dataDeNascimentoPersonal");
	int col_emailPersonal = dataset.getColumnIndex("emailPersonal");
	int col_sexoPersonal = dataset.getColumnIndex("sexoPersonal");
	int col_senhaPersonal = dataset.getColumnIndex("senhaPersonal");
	int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
	
	
	
	int numRows = dataset.getCount();


		dataset.moveToFirst();
	if(numRows == 0 ){
		return null;
	}else{
		Personal p;
		
		String telefonePersonal = dataset.getString(col_telefonePersonal);
		String nomePersonal = dataset.getString(col_nomePersonal);
		String dataDeNascimentoPersonal = dataset.getString(col_dataDeNascimentoPersonal);
		String emailPersonal = dataset.getString(col_emailPersonal);
		String sexoPersonal = dataset.getString(col_sexoPersonal);
		String senhaPersonal = dataset.getString(col_senhaPersonal);
		String usuarioPersonal = dataset.getString(col_usuarioPersonal);
		byte[] encode = dataset.getBlob(col_fotoPersonal);
		String fotoPersonal = Base64.encodeToString(encode, 0);
		
		
		p = new Personal(telefonePersonal,nomePersonal,dataDeNascimentoPersonal,emailPersonal,
				sexoPersonal, usuarioPersonal, senhaPersonal,fotoPersonal);
		
		return p;
	
	}
}

	public byte[] buscarFotoPersonal(Banco b, String filtro){

		String SQL = "SELECT * FROM Personal where usuarioPersonal = '" + filtro + "'";
		
		Cursor dataset = b.querySQL(SQL);
		
		int col_fotoPersonal = dataset.getColumnIndex("fotoPersonal");
		
		int numRows = dataset.getCount();
		dataset.moveToFirst();
		if(numRows == 0 ){
			return null;
		}else{
		
			byte[] encode = dataset.getBlob(col_fotoPersonal);
		
			return encode;
		
		}
	}

	
	//CUD Web service
	
	public boolean atualizarPersonalWeb(){
		clear();
		
		final Personal a = new Personal(getTelefone(),getNome(),getDataDeNascimento(),getEmail(),getSexo(),
				getUsuario(),getSenha(),getFoto());
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"atualizarPersonal");
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
					ht.call(WebService.getSoapAction("atualizarPersonal"), envelope);
					
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
	
	public boolean salvarPersonalWeb(final byte[] foto){
		clear();
		final Personal a = new Personal(super.getTelefone(),super.getNome(),super.getDataDeNascimento(),
				super.getEmail(),super.getSexo(),super.getUsuario(),super.getSenha(),getFoto());
		//Log.i("foto dum dentro do salvar", a.getFoto());
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"salvarPersonal");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Personal");
				p1.setValue(a);
				p1.setType(new Personal().getClass());
				
				request.addProperty(p1);
				
				PropertyInfo p2 = new PropertyInfo();
				p2.setName("imagem");
				p2.setValue(foto);
				p2.setType(byte[].class);
				
				request.addProperty(p2);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "imagem", byte[].class, new MarshalBase64());
				envelope.addMapping(WebService.getNamespace(), "Personal",new Personal().getClass());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				ht.debug=true;
				
				try{
					ht.call(WebService.getSoapAction("salvarPersonal"), envelope);
					//Log.i("envelope debug", ht.requestDump);
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					e.printStackTrace();
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: SalvarPersonalWeb", e.toString());
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
	
	public boolean editarFotoPersonalWeb(final byte[] foto){
		clear();
		final Personal a = new Personal(super.getTelefone(),super.getNome(),super.getDataDeNascimento(),
				super.getEmail(),super.getSexo(),super.getUsuario(),super.getSenha(),getFoto());
		//Log.i("foto dum dentro do salvar", a.getFoto());
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"editarFotoPersonal");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Personal");
				p1.setValue(a);
				p1.setType(new Personal().getClass());
				
				request.addProperty(p1);
				
				PropertyInfo p2 = new PropertyInfo();
				p2.setName("imagem");
				p2.setValue(foto);
				p2.setType(byte[].class);
				
				request.addProperty(p2);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "imagem", byte[].class, new MarshalBase64());
				envelope.addMapping(WebService.getNamespace(), "Personal",new Personal().getClass());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				ht.debug=true;
				
				try{
					ht.call(WebService.getSoapAction("editarFotoPersonal"), envelope);
					//Log.i("envelope debug", ht.requestDump);
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					e.printStackTrace();
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: editarFotoPersonal", e.toString());
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
	
	
	// BUSCAS WEB service
	
	public ArrayList<Personal> buscarPersonaisWeb(final String filtro){
		clear();
		final Personal a = new Personal(getTelefone(),getNome(),getDataDeNascimento(),getEmail(),getSexo(),
				filtro,getSenha(),getFoto());
		
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"buscarPersonais");
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
						ht.call(WebService.getSoapAction("buscarPersonais"), envelope);
						
						SoapObject res = new SoapObject();
						Vector<SoapObject> response = new Vector<SoapObject>();
						
						try{
							res = (SoapObject) envelope.getResponse();
							//Log.i("debug", "cheguei aki --- > " + res.toString());
							
									
						}catch(Exception e){
							Log.i("Erro: buscarPersonaisWeb (Unico)", e.toString());
						}
						
						try{
							
							response = (Vector<SoapObject>) envelope.getResponse();
							//Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
							
						}catch(Exception e){
							Log.i("Erro: buscarPersonaisWeb (vetor)", e.toString());
						}

						if(response.isEmpty()){
							
							 	Personal item = getSoapPersonal(res);
						      //  Log.i("alunos", item.toString());
						        retornoListaPersonal.add(item);
						         
						}else{
							
							for(SoapObject soapPersonal: response){  
						         Personal item = getSoapPersonal(soapPersonal);
						      //   Log.i("alunos", item.toString());
						         retornoListaPersonal.add(item);			       
					           }
							
						}
					         
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("erro de recebimento dos alunos", e.toString());
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
			return retornoListaPersonal;
	}
	
	public Personal buscarPersonalWeb(final String filtro){
		clear();
		final Personal a = new Personal(getTelefone(),getNome(),getDataDeNascimento(),getEmail(),getSexo(),
			filtro,getSenha(),getFoto());
		
	
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"buscarPersonal");
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
						ht.call(WebService.getSoapAction("buscarPersonal"), envelope);
						
						SoapObject res = new SoapObject();
						
						try{
							res = (SoapObject) envelope.getResponse();
						//	Log.i("debug", "cheguei aki --- > " + res.toString());
							
									
						}catch(Exception e){
							Log.i("Erro: buscarPersonal (UNICO) ", e.toString());
						}
						
						retornoPersonal = getSoapPersonal(res);
				   //     Log.i("Retorno do Personal", retornoPersonal.toString());
				        
					         
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("erro de recebimento dos alunos", e.toString());
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
			return retornoPersonal;
	}
	
	public Bitmap buscarFotoPersonalWeb(final String filtro){
		clear();
		
		final Personal a = new Personal(getTelefone(),getNome(),getDataDeNascimento(),getEmail(),getSexo(),
			filtro,getSenha(),getFoto());
		
	
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"buscarFotoPersonal");
					PropertyInfo p1 = new PropertyInfo();
					p1.setName("Personal");
					p1.setValue(a);
					p1.setType(new Personal().getClass());
					
					request.addProperty(p1);
					
		
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					//envelope.addMapping(WebService.getNamespace(), "Personal",new Personal().getClass());
					envelope.addMapping(WebService.getNamespace(),"return", byte[].class, new MarshalBase64());
					new MarshalBase64().register(envelope);
					envelope.encodingStyle = SoapEnvelope.ENC;
					
					HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
					//ht.debug = true;
					//Log.i("debug do envio", ht.requestDump);
					
					try{
						ht.call(WebService.getSoapAction("buscarFotoPersonal"), envelope);
						
						//Log.i("debug da resposta", "nao tem nada" + envelope.toString());
						
						SoapPrimitive res;
						
						try{
							res = (SoapPrimitive) envelope.getResponse();
							//Log.i("debug", "cheguei aki --- > " + res.toString());
							
							fotoPersonal = Base64.decode(res.toString(), Base64.DEFAULT);
							
						}catch(Exception e){
							Log.i("Erro: buscar foto PErsonal (UNICO) ", e.toString());
						}
						
						
				     //   Log.i("Retorno do Personal", retornoPersonal.toString());
				        
					         
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("erro de recebimento dos alunos", e.toString());
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
			Bitmap bmp = BitmapFactory.decodeByteArray(fotoPersonal, 0, fotoPersonal.length);
			return bmp;
	}
	
	
	//Clean
	public void clear(){
		fotoPersonal = null;
		retorno = false;
		retornoListaNomes.clear();
		retornoListaPersonal.clear();
		retornoPersonal = new Personal();
	}
	
	//gets and sets
	
	public Personal getSoapPersonal(SoapObject res){
		Personal item = new Personal();
		if (res.hasProperty("telefone")) {
            item.setTelefone(res.getPropertyAsString("telefone"));
		}
	     if (res.hasProperty("nome")) {
	            item.setNome(res.getPropertyAsString("nome"));
	     }
	     if (res.hasProperty("dataDeNascimento")) {
	            item.setDataDeNascimento(res.getPropertyAsString("dataDeNascimento"));
	     }
	     if (res.hasProperty("email")) {
	            item.setEmail(res.getPropertyAsString("email"));
	     }
	     if (res.hasProperty("sexo")) {
	            item.setSexo(res.getPropertyAsString("sexo"));
	     }
	     if (res.hasProperty("usuario")) {
	            item.setUsuario(res.getPropertyAsString("usuario"));
	     }
	     if (res.hasProperty("senha")) {
	            item.setSenha(res.getPropertyAsString("senha"));
	     }
	     if (res.hasProperty("foto")) {
	    	 item.setFoto(res.toString());
	     }
	     
	     
	     return item;
	}
	
	//KVM
	@Override
	public Object getProperty(int arg0) {
			  switch(arg0){
			        case 0:
			            return super.getTelefone();
			        case 1:
			            return super.getNome();
			        case 2:
			         return super.getDataDeNascimento();
			        case 3:
			        	return super.getEmail();
			        case 4:
			           	return super.getSexo();
			        case 5:
			        	return super.getUsuario();
			        case 6:
			        	return super.getSenha();
			        case 7:
			        	return super.getFoto();
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
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "telefone";
            break;
        case 1:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "nome";
            break;
        case 2:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "dataDeNascimento";
            break;
        case 3:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "email";
            break;
        case 4:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "sexo";
            break;
        case 5:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "usuario";
            break;
        case 6:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "senha";
            break;
        case 7:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "foto";
            break;
        default:break;
        }
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		switch(arg0){
        case 0:
            super.setTelefone(arg1.toString());
            break;
        case 1:
        	super.setNome(arg1.toString());
            break;
        case 2:
            super.setDataDeNascimento(arg1.toString());
            break;
        case 3:
            super.setEmail(arg1.toString());
            break;
        case 4:
            super.setSexo(arg1.toString());
            break;
        case 5:
            super.setUsuario(arg1.toString());
            break;
        case 6:
            super.setSenha(arg1.toString());
            break;
        case 7:
            super.setFoto(arg1.toString());
            break;
        default:
            break;
        }

		
	}
	
}
