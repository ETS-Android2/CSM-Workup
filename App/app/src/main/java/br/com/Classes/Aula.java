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


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

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

	public class Aula implements KvmSerializable {
		
		//Atributos da classe
		
		private int codAula;
		private int diaAula;
		private int mesAula;
		private int anoAula;
		private int horaAula;
		private int minutoAula;
		private int duracaoAula;
		private int confirmacaoAulaPersonal;
		private int confirmacaoAulaAluno;
		private String ativo;
		private String usuarioAluno;
		private String usuarioPersonal;
		
		
		//Atributos Auxiliares
		private static boolean retorno;
		private static int retornoCod;
		private static Aula retornoAula;
		private static ArrayList<Aula> retornoListaAulas = new ArrayList<Aula>();
		
		
		


		@Override
		public String toString() {
			return "Aula [codAula=" + codAula + ", diaAula=" + diaAula
					+ ", mesAula=" + mesAula + ", anoAula=" + anoAula
					+ ", horaAula=" + horaAula + ", minutoAula=" + minutoAula
					+ ", duracaoAula=" + duracaoAula
					+ ", confirmacaoAulaPersonal=" + confirmacaoAulaPersonal
					+ ", confirmacaoAulaAluno=" + confirmacaoAulaAluno
					+ ", ativo=" + ativo + ", usuarioAluno=" + usuarioAluno
					+ ", usuarioPersonal=" + usuarioPersonal + "]";
		}


		public Aula(int codAula, int diaAula, int mesAula, int anoAula,
				int horaAula, int minutoAula, int duracaoAula,
				int confirmacaoAulaPersonal, int confirmacaoAulaAluno,
				String ativo, String usuarioAluno, String usuarioPersonal) {
			super();
			this.codAula = codAula;
			this.diaAula = diaAula;
			this.mesAula = mesAula;
			this.anoAula = anoAula;
			this.horaAula = horaAula;
			this.minutoAula = minutoAula;
			this.duracaoAula = duracaoAula;
			this.confirmacaoAulaPersonal = confirmacaoAulaPersonal;
			this.confirmacaoAulaAluno = confirmacaoAulaAluno;
			this.ativo = ativo;
			this.usuarioAluno = usuarioAluno;
			this.usuarioPersonal = usuarioPersonal;
		}


		public Aula(){
			
		}
		
		
		//CUD local
		public boolean agendarAula(Banco b){
			
			String SQL = "INSERT INTO Aula ("
					+ "codAula,"
					+ "diaAula,"
					+ "mesAula,"
					+ "anoAula,"
					+ "horaAula,"
					+ "minutoAula,"
					+ "duracaoAula,"
					+ "confirmacaoAulaPersonal,"
					+ "confirmacaoAulaAluno,"
					+ "usuarioPersonal,"
					+ "usuarioAluno,"
					+ "ativo)"
					+ " VALUES("
					+ codAula + ","
					+ diaAula + "," 
					+ mesAula + "," 
					+ anoAula + ","
					+ horaAula + ","
					+ minutoAula + ","
					+ duracaoAula + ","
					+ confirmacaoAulaPersonal + "," 
					+ confirmacaoAulaAluno + ",'" 
					+ usuarioPersonal + "','" 
					+ usuarioAluno + "',"
					+ "'ativado');";
			//Log.i("string",SQL);
			try{
				b.execSQL(SQL);
				return true;
			}catch(Exception ex){
				ex.printStackTrace();
				return false;
			}
		}
		
		public boolean confirmarCancelarAulaAluno(Banco b, int confirmaCancela){
			String SQL = "UPDATE Aula set confirmacaoAulaAluno = " + confirmaCancela + " where codAula = " + codAula ;	
			//Log.i("String de update", SQL);
					try{
						b.execSQL(SQL);
						return true;
					}catch(Exception ex){
						return false;
					}
		}
	
		
		
		public boolean atualizarAula(Banco b){
			String SQL = "UPDATE Aula set "
					+ "diaAula = " + diaAula +","
					+ "mesAula = " + mesAula +  ","
					+ "anoAula = " + anoAula + ","
					+ "horaAula = " + horaAula + ","
					+ "minutoAula = " + minutoAula + ","
					+ "duracaoAula = " + duracaoAula + ","
					+ "confirmacaoAulaPersonal = " + confirmacaoAulaPersonal + ","
					+ "confirmacaoAulaAluno = " + confirmacaoAulaAluno + ","
					+ "usuarioPersonal = '" + usuarioPersonal + "',"
					+ "usuarioAluno = '" + usuarioAluno + "',"
					+ "ativo = '" + ativo + "' where "
					+ "codAula = " + codAula;
					
				
				//	Log.i("String de update", SQL);
					try{
						b.execSQL(SQL);
						return true;
					}catch(Exception ex){
						ex.printStackTrace();
						return false;
					}
		}
		
		public boolean confirmarCancelarAulaPersonal(Banco b, int confirmaCancela){
			String SQL = "UPDATE Aula set confirmacaoAulaPersonal = " + confirmaCancela + " where codAula = " + codAula ;
					
					//Log.i("String de update", SQL);
					try{
						b.execSQL(SQL);
						return true;
					}catch(Exception ex){
						return false;
					}
		}
		
		public boolean excluirAula(Banco b){
			String SQL = "UPDATE Aula set ativo = 'desativado' where codAula = " + codAula ;
					
				//	Log.i("String de update", SQL);
					try{
						b.execSQL(SQL);
						return true;
					}catch(Exception ex){
						return false;
					}
		}
		
		
		//BUSCAS local
		
		public Aula buscarAulasPorId(Banco b, int filtro){
				
			
				String SQL = "SELECT * FROM Aula where codAula = " + filtro + " and ativo = 'ativado'";
				
				Cursor dataset = b.querySQL(SQL);
				
				
			
				
				int col_codAula = dataset.getColumnIndex("codAula");
				int col_diaAula = dataset.getColumnIndex("diaAula");
				int col_mesAula = dataset.getColumnIndex("mesAula");
				int col_anoAula = dataset.getColumnIndex("anoAula");
				int col_horaAula = dataset.getColumnIndex("horaAula");
				int col_minutoAula = dataset.getColumnIndex("minutoAula");
				int col_duracaoAula = dataset.getColumnIndex("duracaoAula");
				int col_confirmacaoAulaPersonal = dataset.getColumnIndex("confirmacaoAulaPersonal");
				int col_confirmacaoAulaAluno = dataset.getColumnIndex("confirmacaoAulaAluno");
				int col_ativo = dataset.getColumnIndex("ativo");
				int col_usuarioAluno = dataset.getColumnIndex("usuarioAluno");
				int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
				
				
				int numRows = dataset.getCount();
				
				//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
				dataset.moveToFirst();
				if(numRows > 0 ){
					Aula a = new Aula();
					
					a.setCodAula(dataset.getInt(col_codAula));
					a.setDiaAula(dataset.getInt(col_diaAula));
					a.setMesAula(dataset.getInt(col_mesAula));
					a.setAnoAula(dataset.getInt(col_anoAula));
					a.setHoraAula(dataset.getInt(col_horaAula));
					a.setMinutoAula(dataset.getInt(col_minutoAula));
					a.setDuracaoAula(dataset.getInt(col_duracaoAula));
					a.setConfirmacaoAulaPersonal(dataset.getInt(col_confirmacaoAulaPersonal));
					a.setConfirmacaoAulaAluno(dataset.getInt(col_confirmacaoAulaAluno));
					a.setAtivo(dataset.getString(col_ativo));
					a.setUsuarioAluno(dataset.getString(col_usuarioAluno));
					a.setUsuarioPersonal(dataset.getString(col_usuarioPersonal));
					
					dataset.moveToNext();
					return a;
				}else{
					return null;
				}
					
		}
			
		public ArrayList<Aula> buscarAulasPorAluno(Banco b, String alunoAula,String dtaAula){
				
				
				String SQL = "SELECT * FROM Aula where usuarioAluno = '" + alunoAula + "' and ativo = 'ativado'";
				ArrayList<Aula> aulas = new ArrayList<Aula>();
				
				Cursor dataset = b.querySQL(SQL);
				
				
				
				
				int col_codAula = dataset.getColumnIndex("codAula");
				int col_diaAula = dataset.getColumnIndex("diaAula");
				int col_mesAula = dataset.getColumnIndex("mesAula");
				int col_anoAula = dataset.getColumnIndex("anoAula");
				int col_horaAula = dataset.getColumnIndex("horaAula");
				int col_minutoAula = dataset.getColumnIndex("minutoAula");
				int col_duracaoAula = dataset.getColumnIndex("duracaoAula");
				int col_confirmacaoAulaPersonal = dataset.getColumnIndex("confirmacaoAulaPersonal");
				int col_confirmacaoAulaAluno = dataset.getColumnIndex("confirmacaoAulaAluno");
				int col_ativo = dataset.getColumnIndex("ativo");
				int col_usuarioAluno = dataset.getColumnIndex("usuarioAluno");
				int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
				
				
				int numRows = dataset.getCount();
				
				//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
				dataset.moveToFirst();
				
				for(int c=0; c<numRows; c++) {
					
					Aula a = new Aula();
					
					a.setCodAula(dataset.getInt(col_codAula));
					a.setDiaAula(dataset.getInt(col_diaAula));
					a.setMesAula(dataset.getInt(col_mesAula));
					a.setAnoAula(dataset.getInt(col_anoAula));
					a.setHoraAula(dataset.getInt(col_horaAula));
					a.setMinutoAula(dataset.getInt(col_minutoAula));
					a.setDuracaoAula(dataset.getInt(col_duracaoAula));
					a.setConfirmacaoAulaPersonal(dataset.getInt(col_confirmacaoAulaPersonal));
					a.setConfirmacaoAulaAluno(dataset.getInt(col_confirmacaoAulaAluno));
					a.setAtivo(dataset.getString(col_ativo));
					a.setUsuarioAluno(dataset.getString(col_usuarioAluno));
					a.setUsuarioPersonal(dataset.getString(col_usuarioPersonal));
					
					aulas.add(a);
					
					dataset.moveToNext();
					
				}
				return aulas;
			}
		
		public ArrayList<Aula> buscarAulasPorPersonal(Banco b,String personal, String aluno ){
			
			
			String SQL = "SELECT * FROM Aula where usuarioPersonal = '" + personal + "' and usuarioAluno like '%" + aluno + "%' and ativo = 'ativado'";
			ArrayList<Aula> aulas = new ArrayList<Aula>();
			
			Cursor dataset = b.querySQL(SQL);
			
			
			int col_codAula = dataset.getColumnIndex("codAula");
			int col_diaAula = dataset.getColumnIndex("diaAula");
			int col_mesAula = dataset.getColumnIndex("mesAula");
			int col_anoAula = dataset.getColumnIndex("anoAula");
			int col_horaAula = dataset.getColumnIndex("horaAula");
			int col_minutoAula = dataset.getColumnIndex("minutoAula");
			int col_duracaoAula = dataset.getColumnIndex("duracaoAula");
			int col_confirmacaoAulaPersonal = dataset.getColumnIndex("confirmacaoAulaPersonal");
			int col_confirmacaoAulaAluno = dataset.getColumnIndex("confirmacaoAulaAluno");
			int col_ativo = dataset.getColumnIndex("ativo");
			int col_usuarioAluno = dataset.getColumnIndex("usuarioAluno");
			int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
			
			
			
			int numRows = dataset.getCount();
			
		//	Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
			dataset.moveToFirst();
			
			for(int c=0; c<numRows; c++) {
				
				Aula a = new Aula();
				
				a.setCodAula(dataset.getInt(col_codAula));
				a.setDiaAula(dataset.getInt(col_diaAula));
				a.setMesAula(dataset.getInt(col_mesAula));
				a.setAnoAula(dataset.getInt(col_anoAula));
				a.setHoraAula(dataset.getInt(col_horaAula));
				a.setMinutoAula(dataset.getInt(col_minutoAula));
				a.setDuracaoAula(dataset.getInt(col_duracaoAula));
				a.setConfirmacaoAulaPersonal(dataset.getInt(col_confirmacaoAulaPersonal));
				a.setConfirmacaoAulaAluno(dataset.getInt(col_confirmacaoAulaAluno));
				a.setAtivo(dataset.getString(col_ativo));
				a.setUsuarioAluno(dataset.getString(col_usuarioAluno));
				a.setUsuarioPersonal(dataset.getString(col_usuarioPersonal));
				
				dataset.moveToNext();
				aulas.add(a);
			}
			
			Calendar c = Calendar.getInstance();
			
			ArrayList<Aula> aulasFiltradas = new ArrayList<Aula>();
			
			for(Aula a : aulas){
				boolean verificarAno = true;
				boolean verificarMes = true;
				boolean verificarDia = true;
				boolean verificarHora = true;
				boolean verificarMinutos = true;
				
				if(a.getAnoAula() < c.get(Calendar.YEAR)){
					verificarAno = false;
					Log.i("entrei no ano", "valor " + verificarAno );
				}
				if(a.getAnoAula() == c.get(Calendar.YEAR) 
						&& a.getMesAula() <= c.get(Calendar.MONTH)){
					verificarMes = false;
					Log.i("entrei no mes", "valor " + verificarMes );
				}
				if(a.getAnoAula() == c.get(Calendar.YEAR) 
						&& a.getMesAula() == c.get(Calendar.MONTH)
						&& a.getDiaAula() <= c.get(Calendar.DAY_OF_MONTH)){
					verificarDia = false;
					Log.i("entrei no dia", "valor " + verificarDia );
				}
				
				if(a.getAnoAula() == c.get(Calendar.YEAR) 
						&& a.getMesAula() == c.get(Calendar.MONTH)
						&& a.getDiaAula() == c.get(Calendar.DAY_OF_MONTH)
						&& a.getHoraAula() < c.get(Calendar.HOUR_OF_DAY)){
					verificarHora = false;
					Log.i("entrei no hora", "valor " + verificarHora );
				}
				
				if(a.getAnoAula() == c.get(Calendar.YEAR) 
						&& a.getMesAula() == c.get(Calendar.MONTH)
						&& a.getDiaAula()== c.get(Calendar.DAY_OF_MONTH)
						&& a.getHoraAula() == c.get(Calendar.HOUR_OF_DAY)
						&& a.getMinutoAula() < c.get(Calendar.MINUTE)){
					verificarMinutos = false;
					Log.i("entrei no minuto", "valor " + verificarMinutos );
				}
				
				if(verificarAno && verificarMes && verificarDia && verificarHora  && verificarMinutos){
					aulasFiltradas.add(a);
				}
			}
			return aulasFiltradas;
		}
	
	
		//CUD Web Service
		public int agendarAulaWeb(){
			clear();
			
			final Aula a = new Aula(codAula,diaAula,mesAula,anoAula,horaAula,minutoAula,duracaoAula,
					confirmacaoAulaPersonal,confirmacaoAulaAluno,ativo,usuarioAluno,usuarioPersonal);
			
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"AgendarAula");
					PropertyInfo p1 = new PropertyInfo();
					p1.setName("Aula");
					p1.setValue(a);
					p1.setType(new Aula().getClass());
					
					request.addProperty(p1);
					
		
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					envelope.addMapping(WebService.getNamespace(), "Aula",new Aula().getClass());
					
					HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
					
					
					try{
						ht.call(WebService.getSoapAction("AgendarAula"), envelope);
						
						 retornoCod = Integer.parseInt(envelope.getResponse().toString());
						
					}catch(Exception e){
						e.printStackTrace();
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("Erro: AgendarAula", e.toString());
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
			return retornoCod;
			
		}
		
		public boolean verificarDisponibilidadeWeb(){
			clear();
			
			final Aula a = new Aula(codAula,diaAula,mesAula,anoAula,horaAula,minutoAula,duracaoAula,
					confirmacaoAulaPersonal,confirmacaoAulaAluno,ativo,usuarioAluno,usuarioPersonal);
			
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"verificarDisponibilidade");
					PropertyInfo p1 = new PropertyInfo();
					p1.setName("Aula");
					p1.setValue(a);
					p1.setType(new Aula().getClass());
					
					request.addProperty(p1);
					
		
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					envelope.addMapping(WebService.getNamespace(), "Aula",new Aula().getClass());
					
					HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
					
					
					try{
						ht.call(WebService.getSoapAction("verificarDisponibildade"), envelope);
						
						 retorno = Boolean.valueOf(envelope.getResponse().toString());
						
					}catch(Exception e){
						e.printStackTrace();
						e.printStackTrace();
						Log.e("Erro: verificação de disponibilidade", e.toString());
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
		
		public boolean confirmarCancelarAulaAlunoWeb(final int confirmaCancela){
			clear();
			final Aula a = new Aula(codAula,diaAula,mesAula,anoAula,horaAula,minutoAula,duracaoAula,
					confirmacaoAulaPersonal,confirmaCancela,ativo,usuarioAluno,usuarioPersonal);
			
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"confirmarCancelarAulaAluno");
					PropertyInfo p1 = new PropertyInfo();
					p1.setName("Aula");
					p1.setValue(a);
					p1.setType(new Aula().getClass());
					
					request.addProperty(p1);
					
					PropertyInfo p2 = new PropertyInfo();
					p2.setName("ConfirmarAluno");
					p2.setValue(confirmaCancela);
					p2.setType(int.class);
					
					request.addProperty(p2);
		
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					envelope.addMapping(WebService.getNamespace(), "Aula",new Aula().getClass());
					
					HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
					
					
					try{
						ht.call(WebService.getSoapAction("confirmarCancelarAulaAluno"), envelope);
						
						 retorno = Boolean.valueOf(envelope.getResponse().toString());
						
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("Erro: confirmarCancelarAulaAluno", e.toString());
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
		
		public boolean confirmarCancelarAulaPersonalWeb(final int confirmaCancela){
			clear();
			final Aula a = new Aula(codAula,diaAula,mesAula,anoAula,horaAula,minutoAula,duracaoAula,
					confirmaCancela,confirmacaoAulaAluno,ativo,usuarioAluno,usuarioPersonal);
			
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"confirmarCancelarAulaPersonal");
					PropertyInfo p1 = new PropertyInfo();
					p1.setName("Aula");
					p1.setValue(a);
					p1.setType(new Aula().getClass());
					
					request.addProperty(p1);
					
					PropertyInfo p2 = new PropertyInfo();
					p2.setName("ConfirmarPersonal");
					p2.setValue(confirmaCancela);
					p2.setType(int.class);
					
					request.addProperty(p2);
					
		
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					envelope.addMapping(WebService.getNamespace(), "Aula",new Aula().getClass());
					
					HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
					
					
					try{
						ht.call(WebService.getSoapAction("confirmarCancelarAulaPersonal"), envelope);
						
						 retorno = Boolean.valueOf(envelope.getResponse().toString());
						
					}catch(Exception e){
						e.printStackTrace();
						Log.e("Erro: ConfirmarCancelarAulaPersonal", e.toString());
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
		
		public boolean excluirAulaWeb(){
			clear();
			final Aula a = new Aula(codAula,diaAula,mesAula,anoAula,horaAula,minutoAula,duracaoAula,
					confirmacaoAulaPersonal,confirmacaoAulaAluno,ativo,usuarioAluno,usuarioPersonal);
				
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"excluirAula");
					PropertyInfo p1 = new PropertyInfo();
					p1.setName("Aula");
					p1.setValue(a);
					p1.setType(new Aula().getClass());
					
					request.addProperty(p1);
					
		
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					envelope.addMapping(WebService.getNamespace(), "Aula",new Aula().getClass());
					
					HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
					
					
					try{
						ht.call(WebService.getSoapAction("excluirAula"), envelope);
						
						 retorno = Boolean.valueOf(envelope.getResponse().toString());
						
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("Erro: ExcluirAula", e.toString());
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
		
		//BUSCAS Web Service
		public Aula buscarAulasPorIdWeb(int filtro){
			clear();
			final Aula a = new Aula();
			a.setCodAula(getCodAula());
		
				Thread threadWs = new Thread(){
					
					@Override		
					public void run(){
						
						
						
						SoapObject request = new SoapObject(WebService.getNamespace(),"buscarAulaPorId");
						PropertyInfo p1 = new PropertyInfo();
						p1.setName("Aula");
						p1.setValue(a);
						p1.setType(new Aula().getClass());
						
						request.addProperty(p1);
						
			
						SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
						envelope.setOutputSoapObject(request);
						envelope.addMapping(WebService.getNamespace(), "Aula",new Personal().getClass());
						
						HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
						
						
						try{
							ht.call(WebService.getSoapAction("buscarAulaPorId"), envelope);
							
							SoapObject res = new SoapObject();
							
							try{
								res = (SoapObject) envelope.getResponse();
								//Log.i("debug", "cheguei aki --- > " + res.toString());
								
										
							}catch(Exception e){
								Log.i("Erro: buscarAulaPorId (UNICO) ", e.toString());
							}
					
							retornoAula = getSoapAula(res);
					       // Log.i("debug aluno", retornoAula.toString());
						         
						}catch(Exception e){
							//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
							Log.e("Erro: buscarAulaPorId", e.toString());
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
				return retornoAula;
		
		}
		
		public ArrayList<Aula> buscarAulasPorAlunoWeb(String alunoAula,String dtaAula){
			clear();
			final Aula a = new Aula(codAula,diaAula,mesAula,anoAula,horaAula,minutoAula,duracaoAula,
					confirmacaoAulaPersonal,confirmacaoAulaAluno,ativo,alunoAula,usuarioPersonal);
			
				Thread threadWs = new Thread(){
					
					@Override		
					public void run(){
						
						SoapObject request = new SoapObject(WebService.getNamespace(),"buscarAulasPorAluno");
						PropertyInfo p1 = new PropertyInfo();
						p1.setName("Aula");
						p1.setValue(a);
						p1.setType(new Aula().getClass());
						
						request.addProperty(p1);
						
			
						SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
						envelope.setOutputSoapObject(request);
						envelope.addMapping(WebService.getNamespace(), "Aula",new Personal().getClass());
						
						HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
						
						
						try{
							ht.call(WebService.getSoapAction("buscarAulasPorAluno"), envelope);
							
							SoapObject res = new SoapObject();
							Vector<SoapObject> response = new Vector<SoapObject>();
							
							try{
								res = (SoapObject) envelope.getResponse();
								//Log.i("debug", "cheguei aki --- > " + res.toString());
								
										
							}catch(Exception e){
								Log.i("Erro: buscarAulasPorAluno (UNICO)", e.toString());
							}
							
							try{
								
								response = (Vector<SoapObject>) envelope.getResponse();
							//	Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
								
							}catch(Exception e){
								Log.i("buscarAulasPorAluno (VETOR) ", e.toString());
							}

							if(response.isEmpty()){
								
								 	Aula item = getSoapAula(res);
							      //  Log.i("alunos", item.toString());
							        retornoListaAulas.add(item);
							         
							}else{
								
								for(SoapObject soapAula: response){  
							         Aula item = getSoapAula(soapAula);
							        // Log.i("alunos", item.toString());
							         retornoListaAulas.add(item);			       
						           }
								
							}
						         
						}catch(Exception e){
							//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
							Log.e("Erro: buscarAulasPorAluno", e.toString());
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
				return retornoListaAulas;
		}
		
		public ArrayList<Aula> buscarAulasPorPersonalWeb(String personal, String aluno ){
			clear();
			final Aula a = new Aula(codAula,diaAula,mesAula,anoAula,horaAula,minutoAula,duracaoAula,
					confirmacaoAulaPersonal,confirmacaoAulaAluno,ativo,aluno,personal);
			
				Thread threadWs = new Thread(){
					
					@Override		
					public void run(){
						
						SoapObject request = new SoapObject(WebService.getNamespace(),"buscarAulasPorPersonal");
						PropertyInfo p1 = new PropertyInfo();
						p1.setName("Aula");
						p1.setValue(a);
						p1.setType(new Aula().getClass());
						
						request.addProperty(p1);
						
			
						SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
						envelope.setOutputSoapObject(request);
						envelope.addMapping(WebService.getNamespace(), "Aula",new Personal().getClass());
						
						HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
						
						
						try{
							ht.call(WebService.getSoapAction("buscarAulasPorPersonal"), envelope);
							
							SoapObject res = new SoapObject();
							Vector<SoapObject> response = new Vector<SoapObject>();
							
							try{
								res = (SoapObject) envelope.getResponse();
								//Log.i("debug", "cheguei aki --- > " + res.toString());
								
										
							}catch(Exception e){
								Log.i("Erro:buscarAulasPorPersonal (UNICO)  ", e.toString());
							}
							
							try{
								
								response = (Vector<SoapObject>) envelope.getResponse();
								//Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
								
							}catch(Exception e){
								Log.i("Erro: buscarAulasPorPersonal (VETOR) ", e.toString());
							}

							if(response.isEmpty()){
								
								 	Aula item = getSoapAula(res);
							    //    Log.i("alunos", item.toString());
							        retornoListaAulas.add(item);
							         
							}else{
								
								for(SoapObject soapAula: response){  
							         Aula item = getSoapAula(soapAula);
							    //     Log.i("alunos", item.toString());
							         retornoListaAulas.add(item);			       
						        }
								
							}
						         
						}catch(Exception e){
							e.printStackTrace();
							Log.e("Erro: buscarAulasPorPersonal", e.toString());
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
				return retornoListaAulas;
		}
		
		//clean
		public void clear(){
			retorno = false;
			retornoAula = new Aula();
			retornoCod = 0;
			retornoListaAulas.clear();
		}
		
		//Gets e sets		
		public int getCodAula() {
			return codAula;
		}
		public void setCodAula(int codAula) {
			this.codAula = codAula;
		}
		public int getDiaAula() {
			return diaAula;
		}
		public void setDiaAula(int diaAula) {
			this.diaAula = diaAula;
		}
		public int getMesAula() {
			return mesAula;
		}
		public void setMesAula(int mesAula) {
			this.mesAula = mesAula;
		}
		public int getAnoAula() {
			return anoAula;
		}
		public void setAnoAula(int anoAula) {
			this.anoAula = anoAula;
		}
		public int getHoraAula() {
			return horaAula;
		}
		public void setHoraAula(int horaAula) {
			this.horaAula = horaAula;
		}
		public int getMinutoAula() {
			return minutoAula;
		}
		public void setMinutoAula(int minutoAula) {
			this.minutoAula = minutoAula;
		}
		public int getDuracaoAula() {
			return duracaoAula;
		}
		public void setDuracaoAula(int duracaoAula) {
			this.duracaoAula = duracaoAula;
		}
		public int getConfirmacaoAulaPersonal() {
			return confirmacaoAulaPersonal;
		}
		public void setConfirmacaoAulaPersonal(int confirmacaoAulaPersonal) {
			this.confirmacaoAulaPersonal = confirmacaoAulaPersonal;
		}
		public int getConfirmacaoAulaAluno() {
			return confirmacaoAulaAluno;
		}
		public void setConfirmacaoAulaAluno(int confirmacaoAulaAluno) {
			this.confirmacaoAulaAluno = confirmacaoAulaAluno;
		}
		public String getAtivo() {
			return ativo;
		}
		public void setAtivo(String ativo) {
			this.ativo = ativo;
		}
		public String getUsuarioAluno() {
			return usuarioAluno;
		}
		public void setUsuarioAluno(String usuarioAluno) {
			this.usuarioAluno = usuarioAluno;
		}
		public String getUsuarioPersonal() {
			return usuarioPersonal;
		}
		public void setUsuarioPersonal(String usuarioPersonal) {
			this.usuarioPersonal = usuarioPersonal;
		}

		
		public Aula getSoapAula(SoapObject res){
			Aula item = new Aula();
			if (res.hasProperty("codAula")) {
	               item.setCodAula(Integer.parseInt(res.getPropertyAsString("codAula")));
	        }
			if (res.hasProperty("diaAula")) {
	               item.setDiaAula(Integer.parseInt(res.getPropertyAsString("diaAula")));
	        }
			if (res.hasProperty("mesAula")) {
	               item.setMesAula(Integer.parseInt(res.getPropertyAsString("mesAula")));
	        }
			if (res.hasProperty("anoAula")) {
	               item.setAnoAula(Integer.parseInt(res.getPropertyAsString("anoAula")));
	        }
			if (res.hasProperty("horaAula")) {
	               item.setHoraAula(Integer.parseInt(res.getPropertyAsString("horaAula")));
	        }
			if (res.hasProperty("minutoAula")) {
	               item.setMinutoAula(Integer.parseInt(res.getPropertyAsString("minutoAula")));
	        }
			if (res.hasProperty("duracaoAula")) {
	               item.setDuracaoAula(Integer.parseInt(res.getPropertyAsString("duracaoAula")));
	        }
			if (res.hasProperty("confirmacaoAulaPersonal")) {
	               item.setConfirmacaoAulaPersonal(Integer.parseInt(res.getPropertyAsString("confirmacaoAulaPersonal")));
	        }
			if (res.hasProperty("confirmacaoAulaAluno")) {
	               item.setConfirmacaoAulaAluno(Integer.parseInt(res.getPropertyAsString("confirmacaoAulaAluno")));
	        }
			if (res.hasProperty("ativo")) {
	               item.setAtivo(res.getPropertyAsString("ativo"));
	        }
			if (res.hasProperty("usuarioAluno")) {
	               item.setUsuarioAluno(res.getPropertyAsString("usuarioAluno"));
	        }
			if (res.hasProperty("usuarioPersonal")) {
	               item.setUsuarioPersonal(res.getPropertyAsString("usuarioPersonal"));
	        }
			
			return item;
		}
		
		
		//KVM
		
		@Override
		public Object getProperty(int arg0) {
			switch(arg0){
	        case 0:
	            return codAula;
	        case 1:
	            return diaAula;
	        case 2:
	         return mesAula;
	        case 3:
	        	return anoAula;
	        case 4:
	           	return horaAula;
	        case 5:
	        	return minutoAula;
	        case 6:
	        	return duracaoAula;
	        case 7: 
	        	return confirmacaoAulaPersonal;
	        case 8: 
	        	return confirmacaoAulaAluno;
	        case 9: 
	        	return ativo;
	        case 10: 
	        	return usuarioAluno;
	        case 11: 
	        	return usuarioPersonal;
	        default: 
	        	return null;
	        		
	        }
		}

		
		@Override
		public int getPropertyCount() {
			return 12;
		}
		

		@Override
		public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
			switch(arg0){
	        case 0:
	            arg2.type = PropertyInfo.INTEGER_CLASS;
	            arg2.name = "codAula";
	            break;
	        case 1:
	            arg2.type = PropertyInfo.STRING_CLASS;
	            arg2.name = "diaAula";
	            break;
	        case 2:
	            arg2.type = PropertyInfo.STRING_CLASS;
	            arg2.name = "mesAula";
	            break;
	        case 3:
	            arg2.type = PropertyInfo.STRING_CLASS;
	            arg2.name = "anoAula";
	            break;
	        case 4:
	            arg2.type = PropertyInfo.STRING_CLASS;
	            arg2.name = "horaAula";
	            break;
	        case 5:
	            arg2.type = PropertyInfo.STRING_CLASS;
	            arg2.name = "minutoAula";
	            break;
	        case 6:
	            arg2.type = PropertyInfo.STRING_CLASS;
	            arg2.name = "duracaoAula";
	            break;
	        case 7:
	            arg2.type = PropertyInfo.STRING_CLASS;
	            arg2.name = "confirmacaoAulaPersonal";
	            break;
	        case 8:
	            arg2.type = PropertyInfo.STRING_CLASS;
	            arg2.name = "confirmacaoAulaAluno";
	            break;
	        case 9:
	            arg2.type = PropertyInfo.STRING_CLASS;
	            arg2.name = "ativo";
	            break;
	        case 10:
	            arg2.type = PropertyInfo.STRING_CLASS;
	            arg2.name = "usuarioAluno";
	            break;
	        case 11:
	            arg2.type = PropertyInfo.STRING_CLASS;
	            arg2.name = "usuarioPersonal";
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
	            setCodAula(Integer.parseInt(arg1.toString()));
	            break;
	        case 1:
	        	setDiaAula(Integer.parseInt(arg1.toString()));
	            break;
	        case 2:
	            setMesAula(Integer.parseInt(arg1.toString()));
	            break;
	        case 3:
	            setAnoAula(Integer.parseInt(arg1.toString()));
	            break;
	        case 4:
	            setHoraAula(Integer.parseInt(arg1.toString()));
	            break;
	        case 5:
	            setMinutoAula(Integer.parseInt(arg1.toString()));
	            break;
	        case 6:
	        	setDuracaoAula(Integer.parseInt(arg1.toString()));
	            break;
	        case 7:
	        	setConfirmacaoAulaPersonal(Integer.parseInt(arg1.toString()));
	            break;
	        case 8:
	        	setConfirmacaoAulaAluno(Integer.parseInt(arg1.toString()));
	            break;
	        case 9:
	        	setAtivo(arg1.toString());
	            break;
	        case 10:
	        	setUsuarioAluno(arg1.toString());
	            break;
	        case 11:
	        	setUsuarioPersonal(arg1.toString());
	            break;
	        default:
	            break;
	        }
		}

}
