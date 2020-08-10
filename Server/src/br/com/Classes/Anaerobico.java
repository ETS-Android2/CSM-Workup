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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import CustomListView.CustomAdapterAluno;
import CustomListView.CustomAdapterPersonal;
import CustomListView.RowItemAluno;
import CustomListView.RowItemPersonal;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import br.com.Banco.Banco;
import br.com.GUI.perfil.AdicionarAluno;
import br.com.GUI.perfil.AdicionarPersonal;
import br.com.GUI.perfil.BuscarUsuario;
import br.com.Utilitarios.WebService;
import br.com.WorkUp.R;

public class Anaerobico extends Exercicio implements KvmSerializable {
	

	private String repeticoesExercicio;
	
	//atributos auxiliares
	private static int retornoLastId;
	private static boolean retorno;
	private static Anaerobico retornoAnaerobico;
	private static ArrayList<Anaerobico> retornoListaAnaerobico = new ArrayList<Anaerobico>();
	
	//construtores
	
	public Anaerobico(){
		super();
	}
	
	public Anaerobico(int codExercicio, String nomeExercicio, String descricaoExercicio,
			String descansoExercicio, String repeticoesExercicio,String usuarioPersonal){
		super(codExercicio,nomeExercicio,descricaoExercicio,descansoExercicio, usuarioPersonal);
		this.repeticoesExercicio = repeticoesExercicio;
		
	}

		
	//BUSCAS Local
	public Anaerobico buscarExerciciosPorId(Banco b, int codExercicios){
		
		String SQL = "SELECT * FROM Anaerobico where codExercicioAnaerobico = " + codExercicios + " and ativo = 'ativado'" ;
		
		Cursor dataset = b.querySQL(SQL);
		
		
		
		int col_codExercicioAnaerobico = dataset.getColumnIndex("codExercicioAnaerobico");
		int col_nomeExercicio = dataset.getColumnIndex("nomeExercicio");
		int col_descansoExercicio = dataset.getColumnIndex("descansoExercicio");
		int col_repeticoesExercicio = dataset.getColumnIndex("repeticoesExercicio");
		int col_descricaoExercicio = dataset.getColumnIndex("descricaoExercicio");
		int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
	
	
		
		int numRows = dataset.getCount();
		
		//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
		dataset.moveToFirst();
		
		if(numRows > 0 ) {
			int codExercicioAnaerobico = dataset.getInt(col_codExercicioAnaerobico);
			String nomeExercicio = dataset.getString(col_nomeExercicio);
			String descansoExercicio = dataset.getString(col_descansoExercicio);
			String repeticoesExercicio = dataset.getString(col_repeticoesExercicio);
			String descricaoExercicio = dataset.getString(col_descricaoExercicio);
			String usuarioP = dataset.getString(col_usuarioPersonal);
				
			
			
			Anaerobico a = new Anaerobico(codExercicioAnaerobico,nomeExercicio,descricaoExercicio,
					descansoExercicio,repeticoesExercicio,usuarioP);
			
			return a;
		}
		return null;
	}
	
	public ArrayList<Anaerobico> buscarExerciciosPorPersonal(Banco b, String usuarioPersonal){
		
		/*codExercicioAnaerobico INTEGER PRIMARY KEY AUTOINCREMENT,
		 * nomeExercicio TEXT, 
		 * descansoExercicio TEXT ,
		 * repeticoesExercicio INTEGER,
		 * descricaoExercicio TEXT, 
		 * usuarioPersonal TEXT
		 */
		String SQL = "SELECT * FROM Anaerobico where (usuarioPersonal = '" + usuarioPersonal + "' or usuarioPersonal = 'default') and ativo = 'ativado'";
		ArrayList<Anaerobico> anaerobicos = new ArrayList<Anaerobico>();
		
		
		Cursor dataset = null;
		dataset = b.querySQL(SQL);
		
		dataset.moveToFirst();
		
	//	Log.i("select Exercicios", SQL);
		//DatabaseUtils.dumpCursor(dataset);
		
		int col_codExercicioAnaerobico = dataset.getColumnIndex("codExercicioAnaerobico");
		int col_nomeExercicio = dataset.getColumnIndex("nomeExercicio");
		int col_descansoExercicio = dataset.getColumnIndex("descansoExercicio");
		int col_repeticoesExercicio = dataset.getColumnIndex("repeticoesExercicio");
		int col_descricaoExercicio = dataset.getColumnIndex("descricaoExercicio");
		int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
	
	
		
		int numRows = dataset.getCount();
		
		//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
		
		
		for(int c=0; c<numRows; c++) {
			int codExercicioAnaerobico = dataset.getInt(col_codExercicioAnaerobico);
			String nomeExercicio = dataset.getString(col_nomeExercicio);
			String descansoExercicio = dataset.getString(col_descansoExercicio);
			String repeticoesExercicio = dataset.getString(col_repeticoesExercicio);
			String descricaoExercicio = dataset.getString(col_descricaoExercicio);
			String usuarioP = dataset.getString(col_usuarioPersonal);
				
			
			
			Anaerobico a = new Anaerobico(codExercicioAnaerobico,nomeExercicio,descricaoExercicio,
					descansoExercicio,repeticoesExercicio,usuarioP);
			
			dataset.moveToNext();
			anaerobicos.add(a);
		}
		return anaerobicos;
	}
	
	
	//CUD local
	public boolean salvarExercicio(Banco b, String usuario){
		/*codExercicio INTEGER PRIMARY KEY AUTOINCREMENT,
		 * nomeExercicio TEXT,
		 * descansoExercicio TEXT ,
		 * repeticoesExercicio INTEGER,
		 * descricaoExercicio TEXT,
		 * codTreinamento INTEGER*/
		
		String SQL = "INSERT INTO Anaerobico (codExercicioAnaerobico, nomeExercicio,descansoExercicio,repeticoesExercicio," +
				"descricaoExercicio,usuarioPersonal,ativo) VALUES ("+ super.getCodExercicio() + ",'"  + super.getNomeExercicio() + "','" + super.getDescansoExercicio()
				+ "','" + repeticoesExercicio + "','" + super.getDescricaoExercicio() + "','" + 
				usuario + "', 'ativado');";
				
		try{
			b.execSQL(SQL);
			return true;
		}catch(Exception ex){
			return false;
		}
	}
	
	public boolean atualizarExercicio(Banco b){
		/*codExercicio INTEGER PRIMARY KEY AUTOINCREMENT,
		 * nomeExercicio TEXT,
		 * descansoExercicio TEXT ,
		 * repeticoesExercicio INTEGER,
		 * descricaoExercicio TEXT,
		 * codTreinamento INTEGER*/
		
		String SQL = "UPDATE Anaerobico set nomeExercicio = '"  + super.getNomeExercicio() + "', descansoExercicio = '" 
		+ super.getDescansoExercicio() + "', repeticoesExercicio = '" + repeticoesExercicio + "', descricaoExercicio = '" 
				+ super.getDescricaoExercicio() + "' where codExercicioAnaerobico = " + super.getCodExercicio() + ";";
				
		try{
			//Log.i("sql Atualizar", SQL);
			b.execSQL(SQL);
			return true;
		}catch(Exception ex){
			return false;
		}
	}

	public boolean excluirExercicio(Banco b){
		/*codExercicio INTEGER PRIMARY KEY AUTOINCREMENT,
		 * nomeExercicio TEXT,
		 * descansoExercicio TEXT ,
		 * repeticoesExercicio INTEGER,
		 * descricaoExercicio TEXT,
		 * codTreinamento INTEGER*/
		
		String SQL = "UPDATE Anaerobico set ativo = 'desativado' where codExercicioAnaerobico = " + super.getCodExercicio() + ";";
				
		try{
		//	Log.i("sql Atualizar", SQL);
			b.execSQL(SQL);
			return true;
		}catch(Exception ex){
			return false;
		}
	}
	
	//CUD web service
	
	public boolean atualizarExercicioAnaerobicoWeb(){
		clear();
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"atualizarExercicioAnaerobico");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Anaerobico");
				p1.setValue(this);
				p1.setType(new Anaerobico().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "Anaerobico",new Anaerobico().getClass());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("atualizarExercicioAnaerobico"), envelope);
					
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: atualizarExercicioAnaerobico", e.toString());
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
	
	public int salvarExercicioAnaerobicoWeb(){
		clear();
		
		final Anaerobico a  = new Anaerobico(super.getCodExercicio(),super.getNomeExercicio(),
				super.getDescricaoExercicio(),super.getDescansoExercicio(),repeticoesExercicio,super.getUsuarioPersonal());
		
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"salvarExercicioAnaerobico");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Anaerobico");
				p1.setValue(a);
				p1.setType(new Anaerobico().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "Anaerobico",new Anaerobico().getClass());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("salvarExercicioAnaerobico"), envelope);
					
					 retornoLastId = Integer.parseInt(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: salvarExercicioAnaerobico", e.toString());
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
	
	public boolean excluirExercicioAnaerobicoWeb(){
		clear();
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"excluirExercicioAnaerobico");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Anaerobico");
				p1.setValue(this);
				p1.setType(new Anaerobico().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "Anaerobico",new Anaerobico().getClass());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("excluirExercicioAnaerobico"), envelope);
					
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: excluirExercicioAnaerobico", e.toString());
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
	
	//BUSCAS Web service
	
	public Anaerobico buscarExercicioAnaerobicoPorIdWeb(final int idExercicio){
		clear();
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				final Anaerobico a = new Anaerobico();
				a.setCodExercicio(idExercicio);
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"buscarExercicioAnaerobicoPorId");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Anaerobico");
				p1.setValue(a);
				p1.setType(new Anaerobico().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "Anaerobico",new Anaerobico().getClass());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("buscarExercicioAnaerobicoPorId"), envelope);
					
					SoapObject res = new SoapObject();
					
					try{
						res = (SoapObject) envelope.getResponse();
						Log.i("debug", "cheguei aki --- > " + res.toString());
						
								
					}catch(Exception e){
						Log.i("Erro: buscarExerciciosAnaerobicosPorId (UNICO) ", e.toString());
					}
			
					retornoAnaerobico = getSoapAnaerobico(res);
			      //  Log.i("debug aluno", retornoAnaerobico.toString());
				         
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: buscarExerciciosAnaerobicosPorIdWeb", e.toString());
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
		return retornoAnaerobico;
	}
	
	public ArrayList<Anaerobico> buscarExerciciosAnaerobicoPorPersonalWeb(final String usuarioPersonal){
		clear();
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				final Anaerobico a = new Anaerobico();
				a.setUsuarioPersonal(usuarioPersonal);
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"buscarExerciciosAnaerobicoPorPersonal");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Anaerobico");
				p1.setValue(a);
				p1.setType(new Anaerobico().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "Anaerobico",new Anaerobico().getClass());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				

				try{
					ht.call(WebService.getSoapAction("buscarExerciciosAnaerobicoPorPersonal"), envelope);
					
					SoapObject res = new SoapObject();
					Vector<SoapObject> response = new Vector<SoapObject>();
					
					try{
						res = (SoapObject) envelope.getResponse();
						//Log.i("debug", "cheguei aki --- > " + res.toString());
						
								
					}catch(Exception e){
						Log.i("Erro: buscarExerciciosAnaerobicoPorPersonal (UNICO)", e.toString());
					}
					
					try{
						
						response = (Vector<SoapObject>) envelope.getResponse();
						//Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
						
					}catch(Exception e){
						Log.i("Erro: buscarExerciciosAnaerobicoPorPersonal (VETOR) ", e.toString());
					}

					if(response.isEmpty()){
						
						 	Anaerobico item = getSoapAnaerobico(res);
					      //  Log.i("alunos", item.toString());
					        retornoListaAnaerobico.add(item);
					         
					}else{
						
						for(SoapObject soapAnaerobico: response){  
					         Anaerobico item = getSoapAnaerobico(soapAnaerobico);
					      //   Log.i("alunos", item.toString());
					         retornoListaAnaerobico.add(item);			       
				           }
						
					}
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: buscarExerciciosAnaerobicoPorPersonal", e.toString());
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
	
	//clean
	public void clear(){
		retorno = false;
		retornoAnaerobico = new Anaerobico();
		retornoLastId = 0;
		retornoListaAnaerobico.clear();
	}
	
	// gets e sets
	
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
	
	public String getRepeticoesExercicio() {
		return repeticoesExercicio;
	}
	
	public void setRepeticoesExercicio(String repeticoesExercicio) {
		this.repeticoesExercicio = repeticoesExercicio;
	}
	
	//toString
	@Override
	public String toString() {
		
		return String.valueOf(super.getCodExercicio());
		
	}
	
	//KVM
	@Override
	public Object getProperty(int arg0) {
		switch(arg0){
        case 0:
            return super.getCodExercicio();
        case 1:
        	return super.getNomeExercicio();
        case 2:
        	return super.getDescricaoExercicio();
        case 3:
        	return super.getUsuarioPersonal();
        case 4:
        	return super.getDescansoExercicio();
        case 5:
        	return repeticoesExercicio;   
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
            arg2.name = "codExercicio";
            break;
        case 1:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "nomeExercicio";
            break;
        case 2:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "descricaoExercicio";
            break;
        case 3:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "usuarioPersonal";
            break;
        case 4:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "descansoExercicio";
            break;
        case 5:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "repeticoesExercicio";
            break;
        
        default:break;
        }
		
		
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		switch(arg0){
        case 0:
            super.setCodExercicio(Integer.parseInt(arg1.toString()));
            break;
        case 1:
        	super.setDescricaoExercicio(arg1.toString());
            break;
        case 2:
            super.setNomeExercicio(arg1.toString());
            break;
        case 3:
            super.setUsuarioPersonal(arg1.toString());
            break;
        case 4:
            super.setDescansoExercicio(arg1.toString());
            break;
        case 5:
            setRepeticoesExercicio(arg1.toString());
            break;
        default:
            break;
        }
		
	}
}
