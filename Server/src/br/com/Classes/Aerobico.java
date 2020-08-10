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
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import CustomListView.CustomAdapterPersonal;
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
import br.com.GUI.perfil.AdicionarPersonal;
import br.com.GUI.perfil.BuscarUsuario;
import br.com.Utilitarios.WebService;
import br.com.WorkUp.R;

public class Aerobico extends Exercicio implements KvmSerializable {
	
	private String duracaoExercicio;

	
	//atributos auxiliares
		private static int retornoLastId;
		private static boolean retorno;
		private static Aerobico retornoAerobico;
		private static ArrayList<Aerobico> retornoListaAerobico = new ArrayList<Aerobico>();
		
	
	
	//construtores
	public Aerobico(){
		super();
	}
	
	public Aerobico(int codExercicio, String nomeExercicio, String descricaoExercicio,
			String duracaoExercicio, String descansoExercicio,String usuarioPersonal){
		super(codExercicio, nomeExercicio, descricaoExercicio,descansoExercicio,usuarioPersonal);
		this.duracaoExercicio = duracaoExercicio;
	}
	
	//toString
	
	@Override
	public String toString() {
		return super.toString()  + "Aerobico [duracaoExercicio=" + duracaoExercicio + "]";
	}
	
	
	
	//Buscar Localmente
	public ArrayList<Aerobico> buscarExerciciosPorPersonal(Banco b, String usuarioPersonal){
		
		/*codExercicioAerobico INTEGER PRIMARY KEY AUTOINCREMENT,
		 * nomeExercicio TEXT,
		 * duracaoExercicio TEXT,
		 * descansoExercicio TEXT, 
		 * descricaoExercicio TEXT, 
		 * usuarioPersonal TEXT
		 * 
		 */
		String SQL = "SELECT * FROM Aerobico where (usuarioPersonal = '" + usuarioPersonal + "' or usuarioPersonal = 'default') and ativo = 'ativado'";
		ArrayList<Aerobico> aerobicos = new ArrayList<Aerobico>();
		
		Cursor dataset = null;
		dataset = b.querySQL(SQL);
		
		dataset.moveToFirst();
		
		//Log.i("select Exercicios", SQL);
		//DatabaseUtils.dumpCursor(dataset);
		
		int col_codExercicioAerobico = dataset.getColumnIndex("codExercicioAerobico");
		int col_nomeExercicio = dataset.getColumnIndex("nomeExercicio");
		int col_duracaoExercicio = dataset.getColumnIndex("duracaoExercicio");
		int col_descansoExercicio = dataset.getColumnIndex("descansoExercicio");
		int col_descricaoExercicio = dataset.getColumnIndex("descricaoExercicio");
		int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
	
	
		
		int numRows = dataset.getCount();
		
		//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
		dataset.moveToFirst();
		
		for(int c=0; c<numRows; c++) {
			int codExercicioAerobico = dataset.getInt(col_codExercicioAerobico);
			String duracaoExercicio = dataset.getString(col_duracaoExercicio);
			String nomeExercicio = dataset.getString(col_nomeExercicio);
			String descansoExercicio = dataset.getString(col_descansoExercicio);
			String descricaoExercicio = dataset.getString(col_descricaoExercicio);
			String usuarioP = dataset.getString(col_usuarioPersonal);
				
			
			
			Aerobico a = new Aerobico(codExercicioAerobico,nomeExercicio,descricaoExercicio,
					duracaoExercicio,descansoExercicio,usuarioP);
			
			dataset.moveToNext();
			aerobicos.add(a);
		}
		return aerobicos;
	}

	

	public Aerobico buscarExerciciosPorId(Banco b, int codExercicios){
	
	/*codExercicioAerobico INTEGER PRIMARY KEY AUTOINCREMENT,
	 * nomeExercicio TEXT,
	 * duracaoExercicio TEXT,
	 * descansoExercicio TEXT, 
	 * descricaoExercicio TEXT, 
	 * usuarioPersonal TEXT
	 * 
	 */
	String SQL = "SELECT * FROM Aerobico where codExercicioAerobico = " + codExercicios  + " and ativo = 'ativado'";
	
	Cursor dataset = b.querySQL(SQL);
	
	//Log.i("sql", SQL);
	
	
	int col_codExercicioAerobico = dataset.getColumnIndex("codExercicioAerobico");
	int col_nomeExercicio = dataset.getColumnIndex("nomeExercicio");
	int col_duracaoExercicio = dataset.getColumnIndex("duracaoExercicio");
	int col_descansoExercicio = dataset.getColumnIndex("descansoExercicio");
	int col_descricaoExercicio = dataset.getColumnIndex("descricaoExercicio");
	int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");

	
	int numRows = dataset.getCount();
	
	//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
	dataset.moveToFirst();
	
	if(numRows > 0){
		
		int codExercicioAerobico = dataset.getInt(col_codExercicioAerobico);
		String duracaoExercicio = dataset.getString(col_duracaoExercicio);
		String nomeExercicio = dataset.getString(col_nomeExercicio);
		String descansoExercicio = dataset.getString(col_descansoExercicio);
		String descricaoExercicio = dataset.getString(col_descricaoExercicio);
		String usuarioP = dataset.getString(col_usuarioPersonal);
			
		
		
		Aerobico a = new Aerobico(codExercicioAerobico,nomeExercicio,descricaoExercicio,
				duracaoExercicio,descansoExercicio,usuarioP);
		return a;
	}
	return null;
}
	
	
	//CUD Local
	public boolean salvarExercicio(Banco b, String usuario){
		
		
		String SQL = "INSERT INTO Aerobico (codExercicioAerobico,nomeExercicio,duracaoExercicio,DescansoExercicio,DescricaoExercicio, usuarioPersonal" +
				", ativo) VALUES ("+ super.getCodExercicio() + ",'"  + super.getNomeExercicio() + "','" + duracaoExercicio + "','" + super.getDescansoExercicio() + 
				"','" + super.getDescricaoExercicio() + "','" + usuario + "', 'ativado');";
				
		
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
		 * duracaoExercicio TEXT,
		 * descansoExercicio TEXT, 
		 * descricaoExercicio TEXT,
		 * codTreinamento INTEGER*/
		
		String SQL = "UPDATE Aerobico set nomeExercicio = '"  + super.getNomeExercicio() + "', duracaoExercicio = '" 
		+ duracaoExercicio+ "', descansoExercicio = '" + super.getDescansoExercicio() + "', descricaoExercicio = '" 
				+ super.getDescricaoExercicio() + "' where codExercicioAerobico = " + super.getCodExercicio() + ";";
				
		try{
		//	Log.i("sql Atualizar", SQL);
			b.execSQL(SQL);
			return true;
		}catch(Exception ex){
			return false;
		}
	}
	
	public boolean excluirExercicio(Banco b){
		
		/*codExercicio INTEGER PRIMARY KEY AUTOINCREMENT,
		 * nomeExercicio TEXT,
		 * duracaoExercicio TEXT,
		 * descansoExercicio TEXT, 
		 * descricaoExercicio TEXT,
		 * codTreinamento INTEGER*/
		
		String SQL = "UPDATE Aerobico set ativo = 'desativado' where codExercicioAerobico = " + super.getCodExercicio() + ";";
				
		try{
			//Log.i("sql Atualizar", SQL);
			b.execSQL(SQL);
			return true;
		}catch(Exception ex){
			return false;
		}
	}

	
	//CUD WebService
	
	public boolean atualizarExercicioAerobicoWeb(){
	
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"atualizarExercicioAerobico");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("ExercicioAerobico");
				p1.setValue(this);
				p1.setType(new Aerobico().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "ExercicioAerobico",new Aerobico().getClass());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("atualizarExercicioAerobico"), envelope);
					
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.i("Erro: atualizarExercicioAerobico", e.toString());
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

	public int salvarExercicioAerobicoWeb(){
		final Aerobico a = new Aerobico(super.getCodExercicio(),
				super.getNomeExercicio(),super.getDescricaoExercicio(),duracaoExercicio,
				super.getDescansoExercicio(),super.getUsuarioPersonal());
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"salvarExercicioAerobico");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Aerobico");
				p1.setValue(a);
				p1.setType(new Aerobico().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "Aerobico",new Aerobico().getClass());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("salvarExercicioAerobico"), envelope);
					
					 retornoLastId = Integer.parseInt(envelope.getResponse().toString());
					
				}catch(Exception e){
					e.printStackTrace();
					Log.e("Erro: salvarExercicioAerobico", e.toString());
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
	
	public boolean excluirExercicioAerobicoWeb(){
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"excluirExercicioAerobico");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("ExercicioAerobico");
				p1.setValue(this);
				p1.setType(new Aerobico().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "ExercicioAerobico",new Aerobico().getClass());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("excluirExercicioAerobico"), envelope);
					
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: excluirExercicioAerobico", e.toString());
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
	
	//Busca WebService
	
	public Aerobico buscarExerciciosPorIdWeb(int idExercicio){
		clear();
		final Aerobico a = new Aerobico();
		a.setCodExercicio(idExercicio);
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"buscarExercicioAerobicoPorId");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Aerobico");
				p1.setValue(a);
				p1.setType(new Aerobico().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "Aerobico",new Aerobico().getClass());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("buscarExercicioAerobicoPorId"), envelope);
					
					SoapObject res = new SoapObject();
					
					try{
						res = (SoapObject) envelope.getResponse();
						//Log.i("debug", "cheguei aki --- > " + res.toString());
						
								
					}catch(Exception e){
						Log.i("Erro: buscarExerciciosAerobicosPorId (UNICO) ", e.toString());
					}
			
						retornoAerobico = getSoapAerobico(res);
				        //Log.i("debug aluno", retornoAerobico.toString());
				         
				}catch(Exception e){
					Log.e("Erro: buscarExerciciosAerobicosPorId", e.toString());
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
		return retornoAerobico;
	}
	
	public ArrayList<Aerobico> buscarExerciciosPorPersonalWeb(String usuarioPersonal){
		clear();
		
		final Aerobico a = new Aerobico();
		a.setUsuarioPersonal(usuarioPersonal);
	
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"buscarExerciciosAerobicoPorPersonal");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Aerobico");
				p1.setValue(a);
				p1.setType(new Aerobico().getClass());
				
				request.addProperty(p1);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "Aerobico",new Aerobico().getClass());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				

				try{
					ht.call(WebService.getSoapAction("buscarExerciciosAerobicoPorPersonal"), envelope);
					
					SoapObject res = new SoapObject();
					Vector<SoapObject> response = new Vector<SoapObject>();
					
					try{
						res = (SoapObject) envelope.getResponse();
						//Log.i("debug", "cheguei aki --- > " + res.toString());
						
								
					}catch(Exception e){
						Log.i("Erro: buscarExerciciosAerobicoPorPersonal (Unico) ", e.toString());
					}
					
					try{
						
						response = (Vector<SoapObject>) envelope.getResponse();
						//Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
						
					}catch(Exception e){
						Log.i("Erro: buscarExerciciosAerobicoPorPersonal (Vetor)", e.toString());
					}

					if(response.isEmpty()){
						
						 	Aerobico item = getSoapAerobico(res);
					       // Log.i("alunos", item.toString());
					        retornoListaAerobico.add(item);
					         
					}else{
						
						for(SoapObject soapAerobico: response){  
					         Aerobico item = getSoapAerobico(soapAerobico);
					       //  Log.i("alunos", item.toString());
					         retornoListaAerobico.add(item);			       
				           }
						
					}
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: buscarExerciciosAerobicoPorPersonal", e.toString());
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
	
	
	//Clean
	public void clear(){
		retorno = false;
		retornoAerobico = new Aerobico();
		retornoLastId = 0;
		retornoListaAerobico.clear();
	}
	
	//gets e sets
	
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
	
	public String getDuracaoExercicio() {
		return duracaoExercicio;
	}
	
	public void setDuracaoExercicio(String duracaoExercicio) {
		this.duracaoExercicio = duracaoExercicio;
	}
	
	
	//KVM Serialization
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
        	return duracaoExercicio;
        case 5:
        	return super.getDescansoExercicio();   
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
            arg2.name = "duracaoExercicio";
            break;
        case 5:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "descansoExercicio";
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
            setDuracaoExercicio(arg1.toString());
            break;
        case 5:
            setDescansoExercicio(arg1.toString());
            break;
        default:
            break;
        }
		
	}

}
