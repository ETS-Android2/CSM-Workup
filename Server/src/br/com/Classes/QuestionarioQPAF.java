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

public class QuestionarioQPAF implements KvmSerializable{
	
	private int codAvaliacao;
	private String questao1;
	private String questao2;
	private String questao3;
	private String questao4;
	private String questao5;
	private String questao6;
	private String questao7;
	
	//Atributos Auxiliares
	private boolean retorno;
	private QuestionarioQPAF questionarioQPAF;
	
	
	
	
	
	
	@Override
	public String toString() {
		return "QuestionarioQPAF [codAvaliacao=" + codAvaliacao + ", questao1="
				+ questao1 + ", questao2=" + questao2 + ", questao3="
				+ questao3 + ", questao4=" + questao4 + ", questao5="
				+ questao5 + ", questao6=" + questao6 + ", questao7="
				+ questao7 + ", retorno=" + retorno + ", questionarioQPAF="
				+ questionarioQPAF + "]";
	}

	public QuestionarioQPAF(){
		
	}
	
	public QuestionarioQPAF(int codAvaliacao, String questao1, String questao2,
			String questao3, String questao4, String questao5, String questao6,
			String questao7) {
		super();
		this.codAvaliacao = codAvaliacao;
		this.questao1 = questao1;
		this.questao2 = questao2;
		this.questao3 = questao3;
		this.questao4 = questao4;
		this.questao5 = questao5;
		this.questao6 = questao6;
		this.questao7 = questao7;
	}
	
	//CUD LOCaL
	public boolean salvarQuestinarioQPAF(Banco b){
		try{
			
			String SQL = "INSERT INTO QuestionarioQPAF ("
					+ "codAvaliacao,"
					+ "Questao1,"
					+ "Questao2,"
					+ "Questao3,"
					+ "Questao4,"
					+ "Questao5,"
					+ "Questao6,"
					+ "questao7) VALUES("
					+ codAvaliacao + ",'"
					+ questao1 + "','"
					+ questao2 + "','"
					+ questao3 + "','"
					+ questao4 + "','"
					+ questao5 + "','"
					+ questao6 + "','"
					+ questao7 + "');";
					
			
			b.execSQL(SQL);
			
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		
	}
	
	public boolean editar(Banco b){
		String SQL = "UPDATE questionarioQPAF set "
				+ " Questao1 = '" + questao1 
				+ "', Questao2 = '" + questao2
				+ "', Questao3 = '" + questao3
				+ "', Questao4 = '" + questao4 
				+ "', Questao5 = '" + questao5 
				+ "', Questao6 = '" + questao6
				+ "', Questao7 = '" + questao7 
				+ "' where codAvaliacao = " + codAvaliacao;
				
		try{
			b.execSQL(SQL);
			return true;
		}catch(Exception e){
			Log.i("Erro: ExcluirAvaliacoes",e.toString());
			return false;
		}
		
	}
	
	
public QuestionarioQPAF buscarQuestionarioQPAFPorId(Banco b, int codAvaliacao){
		
		
		String SQL = "SELECT * FROM QuestionarioQPAF where codAvaliacao = " + codAvaliacao ;
		
		
		Cursor dataset = b.querySQL(SQL);

		
		
		int col_questao1 = dataset.getColumnIndex("questao1");
		int col_questao2 = dataset.getColumnIndex("questao2");
		int col_questao3 = dataset.getColumnIndex("questao3");
		int col_questao4 = dataset.getColumnIndex("questao4");
		int col_questao5 = dataset.getColumnIndex("questao5");
		int col_questao6 = dataset.getColumnIndex("questao6");
		int col_questao7 = dataset.getColumnIndex("questao7");
		
		int numRows = dataset.getCount();
		
		//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
		dataset.moveToFirst();
		
		if(dataset.getCount() > 0){
			QuestionarioQPAF a = new QuestionarioQPAF();
			a.setCodAvaliacao(codAvaliacao);
			a.setQuestao1(dataset.getString(col_questao1));
			a.setQuestao2(dataset.getString(col_questao2));
			a.setQuestao3(dataset.getString(col_questao3));
			a.setQuestao4(dataset.getString(col_questao4));
			a.setQuestao5(dataset.getString(col_questao5));
			a.setQuestao6(dataset.getString(col_questao6));
			a.setQuestao7(dataset.getString(col_questao7));
			
			return a;
		}
			
		return null;
	}
	


	//CUD Web Service
	public boolean salvarQuestinarioQPAFWeb(){
			clear();
			final QuestionarioQPAF a = new QuestionarioQPAF(codAvaliacao,questao1,questao2,questao3,questao4,
					questao5,questao6,questao7);
			
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"salvarQuestionarioQPAF");
					PropertyInfo p1 = new PropertyInfo();
					p1.setName("QuestionarioQPAF");
					p1.setValue(a);
					p1.setType(new QuestionarioQPAF().getClass());
					
					request.addProperty(p1);
					
		
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					//envelope.addMapping(WebService.getNamespace(), "GorduraCorporal",new Avaliacoes().getClass());
					
					HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
					
					
					try{
						ht.call(WebService.getSoapAction("QuestionarioQPAF"), envelope);
						
						 retorno = Boolean.parseBoolean(envelope.getResponse().toString());
						
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("Erro: salvarQuestionarioQPAF", e.toString());
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
		
	public boolean editarQuestionarioQPAFWeb(){
			clear();
			final QuestionarioQPAF a = new QuestionarioQPAF(codAvaliacao,questao1,questao2,questao3,questao4,
					questao5,questao6,questao7);
			
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"editarQuestionarioQPAF");
					PropertyInfo p1 = new PropertyInfo();
					p1.setName("QuestionarioQPAF");
					p1.setValue(a);
					p1.setType(new QuestionarioQPAF().getClass());
					
					request.addProperty(p1);
					
		
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					//envelope.addMapping(WebService.getNamespace(), "QuestionarioQPAF",new Avaliacoes().getClass());
					
					HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
					
					
					try{
						ht.call(WebService.getSoapAction("editarQuestionarioQPAF"), envelope);
						
						 retorno = Boolean.parseBoolean(envelope.getResponse().toString());
						
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("Erro: editarQuestionarioQPAF", e.toString());
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
	public QuestionarioQPAF buscarQuestionarioQPAFPorIdWeb(int codAvaliacao){
			final QuestionarioQPAF a = new QuestionarioQPAF();
			a.setCodAvaliacao(codAvaliacao);
			
					
					Thread threadWs = new Thread(){
						
						@Override		
						public void run(){
							
							
							
							SoapObject request = new SoapObject(WebService.getNamespace(),"buscarQuestionarioQPAFPorId");
							PropertyInfo p1 = new PropertyInfo();
							p1.setName("QuestionarioQPAF");
							p1.setValue(a);
							p1.setType(new QuestionarioQPAF().getClass());
							
							request.addProperty(p1);
							
				
							SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
							envelope.setOutputSoapObject(request);
							envelope.addMapping(WebService.getNamespace(), "QuestionarioQPAF",new QuestionarioQPAF().getClass());
							//MarshalDouble marshaldDouble = new MarshalDouble();
							//marshaldDouble.register(envelope);
							
							HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
							
							
							try{
								ht.call(WebService.getSoapAction("buscarQuestionarioQPAFPorId"), envelope);
								
								SoapObject res = new SoapObject();
								
								try{
									res = (SoapObject) envelope.getResponse();
									//Log.i("debug", "cheguei aki --- > " + res.toString());
									
											
								}catch(Exception e){
									Log.i("Erro: buscarQuestionarioWeb (UNICO) ", e.toString());
								}
						
								questionarioQPAF = getSoapQuestionarioQPAF(res);
						       // Log.i("debug aluno", retornoAvaliacoes.toString());
							         
							}catch(Exception e){
								//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
								Log.e("Erro: buscar Questionario QPAF(VETOR) ", e.toString());
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
					return questionarioQPAF;
		}
	
	public void clear(){
		retorno = false;
		questionarioQPAF = new QuestionarioQPAF();
	}
	
	
	public QuestionarioQPAF getSoapQuestionarioQPAF(SoapObject res){
		QuestionarioQPAF item = new QuestionarioQPAF();
		if (res.hasProperty("codAvaliacao")) {
            item.setCodAvaliacao(Integer.parseInt(res.getPropertyAsString("codAvaliacao")));
		}
		if (res.hasProperty("questao1")) {
            item.setQuestao1(res.getPropertyAsString("questao1"));
		}
		if (res.hasProperty("questao2")) {
            item.setQuestao2(res.getPropertyAsString("questao2"));
		}
		if (res.hasProperty("questao3")) {
            item.setQuestao3(res.getPropertyAsString("questao3"));
		}
		if (res.hasProperty("questao4")) {
            item.setQuestao4(res.getPropertyAsString("questao4"));
		}
		if (res.hasProperty("questao5")) {
            item.setQuestao5(res.getPropertyAsString("questao5"));
		}
		if (res.hasProperty("questao6")) {
            item.setQuestao6(res.getPropertyAsString("questao6"));
		}
		if (res.hasProperty("questao7")) {
            item.setQuestao7(res.getPropertyAsString("questao7"));
		}
		return item;
		
	}
		
	
	
	public int getCodAvaliacao() {
		return codAvaliacao;
	}


	public void setCodAvaliacao(int codAvaliacao) {
		this.codAvaliacao = codAvaliacao;
	}


	public String getQuestao1() {
		return questao1;
	}


	public void setQuestao1(String questao1) {
		this.questao1 = questao1;
	}


	public String getQuestao2() {
		return questao2;
	}


	public void setQuestao2(String questao2) {
		this.questao2 = questao2;
	}


	public String getQuestao3() {
		return questao3;
	}


	public void setQuestao3(String questao3) {
		this.questao3 = questao3;
	}


	public String getQuestao4() {
		return questao4;
	}


	public void setQuestao4(String questao4) {
		this.questao4 = questao4;
	}


	public String getQuestao5() {
		return questao5;
	}


	public void setQuestao5(String questao5) {
		this.questao5 = questao5;
	}


	public String getQuestao6() {
		return questao6;
	}


	public void setQuestao6(String questao6) {
		this.questao6 = questao6;
	}


	public String getQuestao7() {
		return questao7;
	}


	public void setQuestao7(String questao7) {
		this.questao7 = questao7;
	}
	
	
	//KVM
	

	@Override
	public Object getProperty(int arg0) {
		switch(arg0){
        case 0:
            return codAvaliacao;
        case 1:
            return questao1;
        case 2:
         return questao2;
        case 3:
        	return questao3;
        case 4:
           	return questao4;
        case 5:
        	return questao5;
        case 6:
        	return questao6;
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
		switch(arg0){
        case 0:
            arg2.type = PropertyInfo.INTEGER_CLASS;
            arg2.name = "codAvaliacao";
            break;
        case 1:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "questao1";
            break;
        case 2:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "questao2";
            break;
        case 3:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "questao3";
            break;
        case 4:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "questao4";
            break;
        case 5:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "questao5";
            break;
        case 6:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "questao6";
            break;
        case 7:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "questao7";
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
        	setQuestao1(arg1.toString());
            break;
        case 2:
            setQuestao2(arg1.toString());
            break;
        case 3:
            setQuestao3(arg1.toString());
            break;
        case 4:
            setQuestao4(arg1.toString());
            break;
        case 5:
            setQuestao5(arg1.toString());
            break;
        case 6:
            setQuestao6(arg1.toString());
            break;
        case 7:
            setQuestao7(arg1.toString());
            break;
        default:
            break;
        }
	}
	
}
