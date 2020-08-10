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


import java.io.ByteArrayInputStream;
import java.io.InputStream;
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

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import br.com.Banco.*;
import br.com.Utilitarios.WebService;

public class Aluno extends Usuario implements KvmSerializable{
	
	//Atributos da classe
	private String usuarioPersonal;
	private int confirmacaoPersonal;
	private int confirmacaoAluno;
	private int codTreinamento;
	
	
	//Atributos auxiliares
	private SharedPreferences pref;
	private static boolean retorno;
	private static byte[] fotoAluno;
	private static Aluno retornoAluno;
	private static Personal retornoPersonal;
	private static ArrayList<Aluno> retornoListaAluno = new ArrayList<Aluno>();
	private static ArrayList<String> retornoListaNomes = new ArrayList<String>();
	
	
	//Construtores
	
	
	public Aluno(){
	}
	
	public Aluno(String telefone, String nome, String dataDeNascimento,
			String email, String sexo, String usuario, String senha,
			String usuarioPersonal,int confirmacaoPersonal,int confirmacaoAluno,String foto,
			int codTreinamento) {
		super(telefone, nome, dataDeNascimento, email, sexo, usuario, senha,foto);
		this.usuarioPersonal = usuarioPersonal;
		this.confirmacaoPersonal = confirmacaoPersonal;
		this.confirmacaoAluno = confirmacaoAluno;
		this.codTreinamento = codTreinamento;
	}

	//CUD Local
	public boolean salvar(Banco b,byte[] fotoAluno){
	
		
		
		 String SQL = "INSERT INTO Aluno ("
                 + "telefoneAluno,"
                 + "nomeAluno,"
                 + "dataDeNascimentoAluno," 
                 + "sexoAluno,"
                 + "emailAluno,"
                 + "senhaAluno,"
                 + "usuarioAluno,"
                 + "usuarioPersonal,"
                 + "confirmacaoPersonal,"
                 + "confirmacaoAluno,"
                 + "codTreinamento,"
                 + "fotoAluno,"
                 + "ativo) values "
                 + "(?, ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? ,?)";
          
       
         
         SQLiteStatement statement = b.getWritableDatabase().compileStatement(SQL);
         if(super.getTelefone() == null){
        	 statement.bindNull(1);
         }else{
        	 statement.bindString(1, super.getTelefone());
         }
         if(super.getNome() == null){
        	 statement.bindNull(2);
         }else{
        	 statement.bindString(2, super.getNome());
         }
         if(super.getDataDeNascimento() == null){
        	 statement.bindNull(3);
         }else{
        	 statement.bindString(3, super.getDataDeNascimento());
         }
         if(super.getSexo() == null){
        	 statement.bindNull(4);
         }else{
        	 statement.bindString(4, super.getSexo());
         }
         if(super.getEmail() == null ){
        	 statement.bindNull(5);
         }else{
        	 statement.bindString(5, super.getEmail());
         }
         statement.bindString(6, super.getSenha());
         statement.bindString(7,super.getUsuario());
         if(getUsuarioPersonal() == null){
        	 statement.bindNull(8);
         }else{
        	 statement.bindString(8,getUsuarioPersonal());
         }
        
         statement.bindLong(9, getConfirmacaoPersonal());
         statement.bindLong(10, getConfirmacaoAluno());
         statement.bindLong(11, getCodTreinamento());
         statement.bindBlob(12, fotoAluno);
         statement.bindString(13, "ativado");
         
        
      
         try{
             statement.executeInsert();
             return true;
         }catch(Exception ex){
            Log.e("Erro ao salvar personal", ex.toString());
            return false;
         }
	}
	
	public boolean atualizar(Banco b,byte[] fotoAluno){
		String SQL = "UPDATE Aluno set "
				+ "telefoneAluno = ?,"
				+ "nomeAluno = ?,"
				+ "dataDeNascimentoAluno = ?,"
				+ "emailAluno = ?,"
				+ "usuarioPersonal = ?,"
				+ "confirmacaoPersonal = ?, "
				+ "confirmacaoAluno = ?, "
				+ "codTreinamento = ?,"
				+ "fotoAluno = ?"
				+ " where usuarioAluno= ?";
		
		SQLiteStatement statement = b.getWritableDatabase().compileStatement(SQL);
		  statement.bindString(1, super.getTelefone());
		  statement.bindString(2, super.getNome());
		  statement.bindString(3, super.getDataDeNascimento());
		  statement.bindString(4, super.getEmail());
		  if(getUsuarioPersonal() == null || getUsuarioPersonal().equals("null")){
			  statement.bindNull(5);
		  }else{
			  statement.bindString(5, getUsuarioPersonal());
		  }
		 
		  statement.bindLong(6, getConfirmacaoPersonal());
		  statement.bindLong(7, getConfirmacaoAluno());
		  statement.bindLong(8, getCodTreinamento());
		  statement.bindBlob(9, fotoAluno);
		  statement.bindString(10, getUsuario());
		  
		  try{
		      statement.executeUpdateDelete();
		      return true;
		  }catch(Exception ex){
		      ex.printStackTrace();
		      return false;
		  }
	}
	
	public boolean atualizarTreinamento(Banco b){
		
		
		String SQL = "UPDATE Aluno set "
				+ "codTreinamento = ? "
				+ " where usuarioAluno= ?";
		
		SQLiteStatement statement = b.getWritableDatabase().compileStatement(SQL);
		  statement.bindLong(1, getCodTreinamento());
		  statement.bindString(2, getUsuario());
		  
		  try{
		      statement.executeUpdateDelete();
		      return true;
		  }catch(Exception ex){
		      ex.printStackTrace();
		      return false;
		  }
	}
	
	public boolean atualizarDadosPessoais(Banco b){
		
		
		String SQL = "UPDATE Aluno set "
				+ "senhaAluno = ? "
				+ " where usuarioAluno= ?";
		
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

	public boolean atualizarFotoAluno(Banco b, byte[] fotoAluno){
		
		
		  String SQL = "UPDATE Aluno set "
		          + "fotoAluno = ? "
		          + "where usuarioAluno = ?";
		   
		  
		  SQLiteStatement statement = b.getWritableDatabase().compileStatement(SQL);
		  statement.bindBlob(1, fotoAluno);
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
	
	public boolean adicionarAluno(Banco b, String usuarioPersonal){
		String SQL = "UPDATE Aluno set usuarioPersonal = '" + usuarioPersonal + 
				"', confirmacaoPersonal = 1 where usuarioAluno = '" + 
				super.getUsuario() +"' and ativo = 'ativado'";
				
			//	Log.i("String de update", SQL);
				try{
					b.execSQL(SQL);
					return true;
				}catch(Exception ex){
					return false;
				}
	}
	
	public boolean adicionarPersonal(Banco b, String usuarioPersonal){
		String SQL = "UPDATE Aluno set usuarioPersonal = '" + usuarioPersonal + 
				"', confirmacaoAluno = 1 where usuarioAluno = '" + 
				super.getUsuario() +"' and ativo = 'ativado'";
				
			//	Log.i("String de update", SQL);
				try{
					b.execSQL(SQL);
					return true;
				}catch(Exception ex){
					return false;
				}
	}
	
	public boolean excluirAluno(Banco b){
		String SQL = "update Aluno set ativo = 'desativado' where usuarioAluno = '"
				+ super.getUsuario() +"'";
				
				//Log.i("String de update", SQL);
				try{
					b.execSQL(SQL);
					return true;
				}catch(Exception ex){
					return false;
				}
	}
	
	public boolean removerAluno(Banco b){
		String SQL = "update Aluno set usuarioPersonal = NULL, confirmacaoPersonal  = 0, confirmacaoAluno = 0, codTreinamento = 0 where usuarioAluno = '" + super.getUsuario() +"' and ativo = 'ativado'";
		
				//Log.i("String de update", SQL);
				try{
					b.execSQL(SQL);
					return true;
				}catch(Exception ex){
					ex.printStackTrace();
					return false;
				}
	}
	
	public boolean aceitarConvite(Banco b, String tipo){
		
		//Log.i("tipo ", tipo);
		String SQL = null;
		
		if(tipo.equals("personal")){
			SQL = "update Aluno set confirmacaoAluno = 1, confirmacaoPersonal = 1 where usuarioAluno = '" + super.getUsuario() +"' and ativo = 'ativado'";
		}else if(tipo.equals("aluno")){
			SQL = "update Aluno set confirmacaoPersonal = 1, confirmacaoAluno = 1 where usuarioAluno = '" + super.getUsuario() +"' and ativo = 'ativado'";
		}
		
		//Log.i("sql", SQL);
				
				//Log.i("String de update", SQL);
				try{
					b.execSQL(SQL);
					return true;
				}catch(Exception ex){
					Log.i("Erro: aceitarConvite(local", ex.toString()); 
					return false;
				}
	}
	
	public boolean rejeitarConvite(Banco b){
		String SQL = null;
		
			SQL = "update Aluno set usuarioPersonal = NULL confirmacaoAluno = 0, confirmacaoPersonal = 0 where usuarioAluno = '" + super.getUsuario() +"' and ativo = 'ativado'";
		
				
				//Log.i("String de update", SQL);
				try{
					b.execSQL(SQL);
					return true;
				}catch(Exception ex){
					return false;
				}
	}

	public boolean preescreverTreinamento(Banco b, int codTreinamento){
		String SQL = "UPDATE Aluno set codTreinamento = '" +
				codTreinamento + "' where usuarioAluno = '" + 
				super.getUsuario() +"' and ativo = 'ativado'";
				
			//	Log.i("String de update", SQL);
				try{
					b.execSQL(SQL);
					return true;
				}catch(Exception ex){
					return false;
				}
	}

	
	//BUSCAS Local
	
	public ArrayList<String> buscarNomesAlunosPersonal(Banco b, String usuarioPersonal,String filtro){
		String SQL = "SELECT * FROM Aluno where usuarioPersonal = '" + usuarioPersonal + "' and usuarioAluno like '%" + filtro
				+ "%' and ativo = 'ativado' and confirmacaoPersonal = 1 and confirmacaoAluno = 1";
		ArrayList<String> nomes = new ArrayList<String>();
		
		Cursor dataset = b.querySQL(SQL);
		

	
		int col_nomePersonal = dataset.getColumnIndex("usuarioAluno");

		
		int numRows = dataset.getCount();
		
		dataset.moveToFirst();
		
		for(int c=0; c<numRows; c++) {
			String nome = dataset.getString(col_nomePersonal);
			
			dataset.moveToNext();
			
			nomes.add(nome);
		}
		return nomes;
		
	}
	
	public ArrayList<Aluno> buscarAlunos(Banco b, String filtro){
		
		
		String SQL = "SELECT * FROM Aluno where usuarioAluno like '%" + filtro + "%' and ativo = 'ativado'";
		ArrayList<Aluno> alunos = new ArrayList<Aluno>();
		
		Cursor dataset = b.querySQL(SQL);
		
		
		
		int col_telefoneAluno = dataset.getColumnIndex("telefoneAluno");
		int col_nomeAluno = dataset.getColumnIndex("nomeAluno");
		int col_dataDeNascimentoAluno = dataset.getColumnIndex("dataDeNascimentoAluno");
		int col_sexoAluno = dataset.getColumnIndex("sexoAluno");
		int col_emailAluno = dataset.getColumnIndex("emailAluno");
		int col_senhaAluno = dataset.getColumnIndex("senhaAluno");
		int col_usuarioAluno = dataset.getColumnIndex("usuarioAluno");
		int col_confirmacaoPersonal = dataset.getColumnIndex("confirmacaoPersonal");
		int col_confirmacaoAluno = dataset.getColumnIndex("confirmacaoAluno");
		int col_fotoAluno = dataset.getColumnIndex("fotoAluno");
		int col_codTreinamento = dataset.getColumnIndex("codTreinamento");
		int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
		
		int numRows = dataset.getCount();
		
		//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
		dataset.moveToFirst();
		
		for(int c=0; c<numRows; c++) {
			String telefoneAluno = dataset.getString(col_telefoneAluno);
			String nomeAluno = dataset.getString(col_nomeAluno);
			String dataDeNascimentoAluno = dataset.getString(col_dataDeNascimentoAluno);
			String sexoAluno = dataset.getString(col_sexoAluno);
			String emailAluno = dataset.getString(col_emailAluno);
			String senhaAluno = dataset.getString(col_senhaAluno);
			String usuarioAluno = dataset.getString(col_usuarioAluno);
			int confirmacaoPersonal  = dataset.getInt(col_confirmacaoPersonal);
			int confirmacaoAluno  = dataset.getInt(col_confirmacaoAluno);
			byte[] encode = dataset.getBlob(col_fotoAluno);
			String fotoAluno = Base64.encodeToString(encode, 0);
			int codTreinamento = dataset.getInt(col_codTreinamento);
			String usuarioP;
			if (dataset.getString(col_usuarioPersonal) == null){
				usuarioP = "";
			}else{
				usuarioP = dataset.getString(col_usuarioPersonal);
			}
			 
			
			
			Aluno a = new Aluno(telefoneAluno,nomeAluno,dataDeNascimentoAluno,emailAluno,
					sexoAluno,usuarioAluno,senhaAluno,usuarioP,confirmacaoPersonal,confirmacaoAluno
					,fotoAluno,codTreinamento);
			
			dataset.moveToNext();
			alunos.add(a);
		}
		return alunos;
	}
	
	public ArrayList<Aluno> buscarAlunosPorPersonal(Banco b, String filtro,String usuarioPersonal){
		
		
		String SQL = "SELECT * FROM Aluno where usuarioPersonal = '" + usuarioPersonal + "' "
				+ "and usuarioAluno like '%" + filtro + "%' "
						+ "and ativo = 'ativado' "
						+ "and confirmacaoPersonal = 1 "
						+ "and confirmacaoAluno = 1";
		ArrayList<Aluno> alunos = new ArrayList<Aluno>();
		
		Cursor dataset = b.querySQL(SQL);
		
		
		
		int col_telefoneAluno = dataset.getColumnIndex("telefoneAluno");
		int col_nomeAluno = dataset.getColumnIndex("nomeAluno");
		int col_dataDeNascimentoAluno = dataset.getColumnIndex("dataDeNascimentoAluno");
		int col_sexoAluno = dataset.getColumnIndex("sexoAluno");
		int col_emailAluno = dataset.getColumnIndex("emailAluno");
		int col_senhaAluno = dataset.getColumnIndex("senhaAluno");
		int col_usuarioAluno = dataset.getColumnIndex("usuarioAluno");
		int col_codTreinamento = dataset.getColumnIndex("codTreinamento");
		int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
		int col_confirmacaoPersonal = dataset.getColumnIndex("confirmacaoPersonal");
		int col_confirmacaoAluno = dataset.getColumnIndex("confirmacaoAluno");
		int col_fotoAluno = dataset.getColumnIndex("fotoAluno");
		int numRows = dataset.getCount();
		
		//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
		dataset.moveToFirst();
		
		for(int c=0; c<numRows; c++) {
			String telefoneAluno = dataset.getString(col_telefoneAluno);
			String nomeAluno = dataset.getString(col_nomeAluno);
			String dataDeNascimentoAluno = dataset.getString(col_dataDeNascimentoAluno);
			String sexoAluno = dataset.getString(col_sexoAluno);
			String emailAluno = dataset.getString(col_emailAluno);
			String senhaAluno = dataset.getString(col_senhaAluno);
			String usuarioAluno = dataset.getString(col_usuarioAluno);
			int codTreinamento = dataset.getInt(col_codTreinamento);
			int confirmacaoPersonal  = dataset.getInt(col_confirmacaoPersonal);
			int confirmacaoAluno  = dataset.getInt(col_confirmacaoAluno);
			byte[] encode = dataset.getBlob(col_fotoAluno);
			String fotoAluno = Base64.encodeToString(encode, 0);
			
			String usuarioP;
			
			if (dataset.getString(col_usuarioPersonal) == null){
				usuarioP = "";
			}else{
				usuarioP = dataset.getString(col_usuarioPersonal);
			}
			 
			
			
			Aluno a = new Aluno(telefoneAluno,nomeAluno,dataDeNascimentoAluno,emailAluno,
					sexoAluno,usuarioAluno,senhaAluno,usuarioP,confirmacaoPersonal,confirmacaoAluno
					,fotoAluno,codTreinamento);
			
			dataset.moveToNext();
			alunos.add(a);
			//Log.i("aluno buscado", a.toString());
		}
		return alunos;
	}
	
	public ArrayList<Aluno> buscarAlunoNaoConfirmadoPorPersonal(Banco b, String filtro,String usuarioPersonal){
		
		/*CREATE TABLE Aluno (telefoneAluno INTEGER PRIMARY KEY,nomeAluno TEXT,dataDeNascimentoAluno TEXT,
		 * sexoAluno TEXT NOT NULL,emailAluno TEXT,senhaAluno TEXT,usuarioAluno TEXT UNIQUE NOT NULL,
		 * codTreinamento INTEGER,paisAluno TEXT,estadoAluno TEXT,cidadeAluno TEXT,ruaAluno TEXT,
		 * numeroAluno INTEGER,complementoAluno TEXT);";
		 * 
		 */
		String SQL = "SELECT * FROM Aluno where usuarioPersonal = '" + usuarioPersonal + "' "
				+ "and usuarioAluno like '%" + filtro + "%' "
						+ "and ativo = 'ativado' "
						+ "and confirmacaoPersonal = 0 "
						+ "and confirmacaoAluno = 1";
		ArrayList<Aluno> alunos = new ArrayList<Aluno>();
		
		Cursor dataset = b.querySQL(SQL);
		
		
		
		int col_telefoneAluno = dataset.getColumnIndex("telefoneAluno");
		int col_nomeAluno = dataset.getColumnIndex("nomeAluno");
		int col_dataDeNascimentoAluno = dataset.getColumnIndex("dataDeNascimentoAluno");
		int col_sexoAluno = dataset.getColumnIndex("sexoAluno");
		int col_emailAluno = dataset.getColumnIndex("emailAluno");
		int col_senhaAluno = dataset.getColumnIndex("senhaAluno");
		int col_usuarioAluno = dataset.getColumnIndex("usuarioAluno");
		int col_codTreinamento = dataset.getColumnIndex("codTreinamento");
		int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
		int col_confirmacaoPersonal = dataset.getColumnIndex("confirmacaoPersonal");
		int col_confirmacaoAluno = dataset.getColumnIndex("confirmacaoAluno");
		int col_fotoAluno = dataset.getColumnIndex("fotoAluno");
		
		int numRows = dataset.getCount();
		
		//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
		dataset.moveToFirst();
		
		for(int c=0; c<numRows; c++) {
			String telefoneAluno = dataset.getString(col_telefoneAluno);
			String nomeAluno = dataset.getString(col_nomeAluno);
			String dataDeNascimentoAluno = dataset.getString(col_dataDeNascimentoAluno);
			String sexoAluno = dataset.getString(col_sexoAluno);
			String emailAluno = dataset.getString(col_emailAluno);
			String senhaAluno = dataset.getString(col_senhaAluno);
			String usuarioAluno = dataset.getString(col_usuarioAluno);
			int codTreinamento = dataset.getInt(col_codTreinamento);
			int confirmacaoPersonal  = dataset.getInt(col_confirmacaoPersonal);
			int confirmacaoAluno  = dataset.getInt(col_confirmacaoAluno);
			byte[] encode = dataset.getBlob(col_fotoAluno);
			String fotoAluno = Base64.encodeToString(encode, 0);
			
			
			String usuarioP;
			if (dataset.getString(col_usuarioPersonal) == null){
				usuarioP = "";
			}else{
				usuarioP = dataset.getString(col_usuarioPersonal);
			}
			 
			
			
			Aluno a = new Aluno(telefoneAluno,nomeAluno,dataDeNascimentoAluno,emailAluno,
					sexoAluno,usuarioAluno,senhaAluno,usuarioP,confirmacaoPersonal,confirmacaoAluno, 
					fotoAluno,codTreinamento);
			
			dataset.moveToNext();
			alunos.add(a);
			//Log.i("aluno buscado", a.toString());
		}
		return alunos;
	}
	
	public ArrayList<Aluno> buscarPersonalNaoConfirmadoPorAluno(Banco b, String filtro,String usuarioPersonal){
		
		/*CREATE TABLE Aluno (telefoneAluno INTEGER PRIMARY KEY,nomeAluno TEXT,dataDeNascimentoAluno TEXT,
		 * sexoAluno TEXT NOT NULL,emailAluno TEXT,senhaAluno TEXT,usuarioAluno TEXT UNIQUE NOT NULL,
		 * codTreinamento INTEGER,paisAluno TEXT,estadoAluno TEXT,cidadeAluno TEXT,ruaAluno TEXT,
		 * numeroAluno INTEGER,complementoAluno TEXT);";
		 * 
		 */
		String SQL = "SELECT * FROM Aluno where usuarioPersonal = '" + usuarioPersonal + "' "
				+ "and usuarioAluno like '%" + filtro + "%' "
						+ "and ativo = 'ativado' "
						+ "and confirmacaoPersonal = 1 "
						+ "and confirmacaoAluno = 0";
		ArrayList<Aluno> alunos = new ArrayList<Aluno>();
		
		Cursor dataset = b.querySQL(SQL);
		
		
		
		int col_telefoneAluno = dataset.getColumnIndex("telefoneAluno");
		int col_nomeAluno = dataset.getColumnIndex("nomeAluno");
		int col_dataDeNascimentoAluno = dataset.getColumnIndex("dataDeNascimentoAluno");
		int col_sexoAluno = dataset.getColumnIndex("sexoAluno");
		int col_emailAluno = dataset.getColumnIndex("emailAluno");
		int col_senhaAluno = dataset.getColumnIndex("senhaAluno");
		int col_usuarioAluno = dataset.getColumnIndex("usuarioAluno");
		int col_codTreinamento = dataset.getColumnIndex("codTreinamento");
		int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
		int col_confirmacaoPersonal = dataset.getColumnIndex("confirmacaoPersonal");
		int col_confirmacaoAluno = dataset.getColumnIndex("confirmacaoAluno");
		int col_fotoAluno = dataset.getColumnIndex("fotoAluno");
		
		int numRows = dataset.getCount();
		
		//Log.i("numero de rows" ,"Numero de Linhas" +  numRows);
		dataset.moveToFirst();
		
		for(int c=0; c<numRows; c++) {
			String telefoneAluno = dataset.getString(col_telefoneAluno);
			String nomeAluno = dataset.getString(col_nomeAluno);
			String dataDeNascimentoAluno = dataset.getString(col_dataDeNascimentoAluno);
			String sexoAluno = dataset.getString(col_sexoAluno);
			String emailAluno = dataset.getString(col_emailAluno);
			String senhaAluno = dataset.getString(col_senhaAluno);
			String usuarioAluno = dataset.getString(col_usuarioAluno);
			int codTreinamento = dataset.getInt(col_codTreinamento);
			int confirmacaoPersonal  = dataset.getInt(col_confirmacaoPersonal);
			int confirmacaoAluno  = dataset.getInt(col_confirmacaoAluno);
			byte[] encode = dataset.getBlob(col_fotoAluno);
			String fotoAluno = Base64.encodeToString(encode, 0);
			
			String usuarioP;
			if (dataset.getString(col_usuarioPersonal) == null){
				usuarioP = "";
			}else{
				usuarioP = dataset.getString(col_usuarioPersonal);
			}
			 
			
			
			Aluno a = new Aluno(telefoneAluno,nomeAluno,dataDeNascimentoAluno,emailAluno,
					sexoAluno,usuarioAluno,senhaAluno,usuarioP,confirmacaoPersonal, confirmacaoAluno,
					fotoAluno,codTreinamento);
			
			dataset.moveToNext();
			alunos.add(a);
			//Log.i("aluno buscado", a.toString());
		}
		return alunos;
	}
	
	public Aluno buscarAlunoEspecifico(Banco b, String usuarioAluno){
		clear();
		
		String SQL = "SELECT * FROM Aluno where usuarioAluno = '" + usuarioAluno + "' "
				+ "and ativo = 'ativado'";
		
		Cursor dataset = b.querySQL(SQL);
		
		

		
		
		int col_telefoneAluno = dataset.getColumnIndex("telefoneAluno");
		int col_foto = dataset.getColumnIndex("foto");
		int col_nomeAluno = dataset.getColumnIndex("nomeAluno");
		int col_dataDeNascimentoAluno = dataset.getColumnIndex("dataDeNascimentoAluno");
		int col_sexoAluno = dataset.getColumnIndex("sexoAluno");
		int col_emailAluno = dataset.getColumnIndex("emailAluno");
		int col_senhaAluno = dataset.getColumnIndex("senhaAluno");
		int col_usuarioAluno = dataset.getColumnIndex("usuarioAluno");
		int col_codTreinamento = dataset.getColumnIndex("codTreinamento");
		int col_usuarioPersonal = dataset.getColumnIndex("usuarioPersonal");
		int col_confirmacaoPersonal = dataset.getColumnIndex("confirmacaoPersonal");
		int col_confirmacaoAluno = dataset.getColumnIndex("confirmacaoAluno");
		int col_fotoAluno = dataset.getColumnIndex("fotoAluno");
		
		int numRows = dataset.getCount();
		
		dataset.moveToFirst();

			
			
		if(numRows == 0 ){
			return null;
		}else{
			String telefoneAluno = dataset.getString(col_telefoneAluno);
			String nomeAluno = dataset.getString(col_nomeAluno);
			String dataDeNascimentoAluno = dataset.getString(col_dataDeNascimentoAluno);
			String sexoAluno = dataset.getString(col_sexoAluno);
			String emailAluno = dataset.getString(col_emailAluno);
			String senhaAluno = dataset.getString(col_senhaAluno);
			String usuarioA = dataset.getString(col_usuarioAluno);
			int codTreinamento = dataset.getInt(col_codTreinamento);
			int confirmacaoPersonal  = dataset.getInt(col_confirmacaoPersonal);
			int confirmacaoAluno  = dataset.getInt(col_confirmacaoAluno);
			byte[] encode = dataset.getBlob(col_fotoAluno);
			String fotoAluno = Base64.encodeToString(encode, 0);
			
			
			String usuarioP;
			if (dataset.getString(col_usuarioPersonal) == null){
				usuarioP = "";
			}else{
				usuarioP = dataset.getString(col_usuarioPersonal);
			}
		
			Aluno a;
			
			return a = new Aluno(telefoneAluno,nomeAluno,dataDeNascimentoAluno,emailAluno,
					sexoAluno,usuarioA,senhaAluno,usuarioP,confirmacaoPersonal,confirmacaoAluno,
					fotoAluno,codTreinamento);

		}					
	}

	public byte[] buscarFotoAluno(Banco b, String usuarioAluno){
		
		
		String SQL = "SELECT fotoAluno FROM Aluno where usuarioAluno = '" + usuarioAluno + "' "
				+ "and ativo = 'ativado'";
		
		Cursor dataset = b.querySQL(SQL);
	
		int col_fotoAluno = dataset.getColumnIndex("fotoAluno");
		
		int numRows = dataset.getCount();
		
		dataset.moveToFirst();

			
			
		if(numRows == 0 ){
			return null;
		}else{
			
			byte[] encode = dataset.getBlob(col_fotoAluno);
			return encode;

		}					
	}

	//CUD Web Service
	public boolean atualizarAlunoWeb(){
		clear();
		final Aluno a = new Aluno(getTelefone(),getNome(),getDataDeNascimento(),
				getEmail(),getSexo(),getUsuario(),getSenha(),getUsuarioPersonal(),
				confirmacaoPersonal,confirmacaoAluno,null,codTreinamento);
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"atualizarAluno");
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
					ht.call(WebService.getSoapAction("atualizarAluno"), envelope);
					
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					e.printStackTrace();
					Log.e("Erro: Atualizar Aluno Web", e.toString());
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
	
	public boolean salvarAlunoWeb(final byte[] foto){
		clear();
		
		final Aluno a = new Aluno(getTelefone(),getNome(),getDataDeNascimento(),
				getEmail(),getSexo(),getUsuario(),getSenha(),getUsuarioPersonal(),
				confirmacaoPersonal,confirmacaoAluno,getFoto(),codTreinamento);
		
		//Log.i("dump Salvar Aluno Web", a.toString());
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"salvarAluno");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Aluno");
				p1.setValue(a);
				p1.setType(new Aluno().getClass());
				
				request.addProperty(p1);
				
				
				PropertyInfo p2 = new PropertyInfo();
				p2.setName("imagem");
				p2.setValue(foto);
				p2.setType(byte[].class);
				
				request.addProperty(p2);
				
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "Aluno",new Aluno().getClass());
				envelope.addMapping(WebService.getNamespace(), "imagem", byte[].class, new MarshalBase64());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("salvarAluno"), envelope);
					
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro Salvar Aluno Web", e.toString());
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
	
	public boolean editarFotoAlunoWeb(final byte[] foto){
		clear();
		final Personal a = new Personal(super.getTelefone(),super.getNome(),super.getDataDeNascimento(),
				super.getEmail(),super.getSexo(),super.getUsuario(),super.getSenha(),getFoto());
		//Log.i("foto dum dentro do salvar", a.getFoto());
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"editarFotoAluno");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Aluno");
				p1.setValue(a);
				p1.setType(new Aluno().getClass());
				
				request.addProperty(p1);
				
				PropertyInfo p2 = new PropertyInfo();
				p2.setName("imagem");
				p2.setValue(foto);
				p2.setType(byte[].class);
				
				request.addProperty(p2);
				
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "imagem", byte[].class, new MarshalBase64());
				envelope.addMapping(WebService.getNamespace(), "Aluno",new Aluno().getClass());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				ht.debug=true;
				
				try{
					ht.call(WebService.getSoapAction("editarFotoAluno"), envelope);
					//Log.i("envelope debug", ht.requestDump);
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					e.printStackTrace();
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: editar Foto Aluno", e.toString());
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
	
	public boolean adicionarAlunoWeb(final String usuarioPersonal,final String usuarioAluno){
		clear();
		final Aluno a = new Aluno(getTelefone(),getNome(),getDataDeNascimento(),
				getEmail(),getSexo(),usuarioAluno,getSenha(),usuarioPersonal,
				confirmacaoPersonal,confirmacaoAluno,getFoto(),codTreinamento);
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"adicionarAluno");
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
					ht.call(WebService.getSoapAction("adicionarAluno"), envelope);
					
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: AdicionarAluno Web", e.toString());
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
	
	public boolean adicionarPersonalWeb(final String usuarioPersonal,final String usuarioAluno){
		clear();
		final Aluno a = new Aluno(getTelefone(),getNome(),getDataDeNascimento(),
				getEmail(),getSexo(),usuarioAluno,getSenha(),usuarioPersonal,
				confirmacaoPersonal,confirmacaoAluno,getFoto(),codTreinamento);
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"adicionarPersonal");
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
					ht.call(WebService.getSoapAction("adicionarPersonal"), envelope);
					
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: adicionarPersonalWeb", e.toString());
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
	
	public boolean excluirAlunoWeb(){
		final Aluno a = new Aluno(getTelefone(),getNome(),getDataDeNascimento(),
				getEmail(),getSexo(),getUsuario(),getSenha(),getUsuarioPersonal(),
				confirmacaoPersonal,confirmacaoAluno,getFoto(),codTreinamento);
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
							
				SoapObject request = new SoapObject(WebService.getNamespace(),"excluirAluno");
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
					ht.call(WebService.getSoapAction("excluirAluno"), envelope);
					
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: ExcluirAluno Web", e.toString());
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

	public boolean removerAlunoWeb(String usuarioAluno){
		clear();
		final Aluno a = new Aluno(getTelefone(),getNome(),getDataDeNascimento(),
				getEmail(),getSexo(),usuarioAluno,getSenha(),getUsuarioPersonal(),
				confirmacaoPersonal,confirmacaoAluno,getFoto(),codTreinamento);
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"removerAluno");
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
					ht.call(WebService.getSoapAction("removerAluno"), envelope);
					
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: RemoverAlunoWeb", e.toString());
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
	
	public boolean aceitarConviteWeb(String usuarioAluno , final String tipo){
		clear();
		final Aluno a = new Aluno(getTelefone(),getNome(),getDataDeNascimento(),
				getEmail(),getSexo(),usuarioAluno,getSenha(),getUsuarioPersonal(),
				1,1,getFoto(),codTreinamento);
		
		//Log.i("Entrei", "aceitarConviteWeb");
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"aceitarConvite");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Aluno");
				p1.setValue(a);
				p1.setType(new Aluno().getClass());
				
				request.addProperty(p1);
				
				PropertyInfo p2 = new PropertyInfo();
				p2.setName("tipo");
				p2.setValue(tipo);
				p2.setType(new String().getClass());
				
				request.addProperty(p2);
				
				//Log.i("adicionei", "propriedades adicionadas");
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
			//	envelope.addMapping(WebService.getNamespace(), "Aluno",new Aluno().getClass());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				//Log.i("montei o envelope", "montei o envelope");
				
				try{
					
					ht.call(WebService.getSoapAction("aceitarConvite"), envelope);
					
					retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.i("Erro: aceitarConviteWeb ", e.toString());
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
	
	public boolean rejeitarConviteWeb(String usuarioAluno, final String tipo){
		clear();
		final Aluno a = new Aluno(getTelefone(),getNome(),getDataDeNascimento(),
				getEmail(),getSexo(),usuarioAluno,getSenha(),getUsuarioPersonal(),
				confirmacaoPersonal,confirmacaoAluno,getFoto(),codTreinamento);
		
		Thread threadWs = new Thread(){
			
			@Override		
			public void run(){
				
				
				
				SoapObject request = new SoapObject(WebService.getNamespace(),"rejeitarConvite");
				PropertyInfo p1 = new PropertyInfo();
				p1.setName("Aluno");
				p1.setValue(a);
				p1.setType(new Aluno().getClass());
				
				request.addProperty(p1);
				
				PropertyInfo p2 = new PropertyInfo();
				p1.setName("tipo");
				p1.setValue(tipo);
				p1.setType(String.class);
				
				request.addProperty(p2);
	
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(WebService.getNamespace(), "Aluno",new Aluno().getClass());
				
				HttpTransportSE ht = new HttpTransportSE(WebService.getUrl());
				
				
				try{
					ht.call(WebService.getSoapAction("rejeitarConvite"), envelope);
					
					 retorno = Boolean.valueOf(envelope.getResponse().toString());
					
				}catch(Exception e){
					//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
					Log.e("Erro: rejeitarConviteWeb", e.toString());
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
	
	public boolean preescreverTreinamentoWeb(int codTreinamento){
		clear();
		final Aluno a = new Aluno(getTelefone(),getNome(),getDataDeNascimento(),
				getEmail(),getSexo(),getUsuario(),getSenha(),getUsuarioPersonal(),
				confirmacaoPersonal,confirmacaoAluno,getFoto(),codTreinamento);
			
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"preescreverTreinamento");
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
						ht.call(WebService.getSoapAction("preescreverTreinamento"), envelope);
						
						 retorno = Boolean.valueOf(envelope.getResponse().toString());
						
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("Erro: Preescrever Treinamento Web", e.toString());
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
	
	public Aluno buscarAlunoEspecificoWeb(String usuarioAluno){
		clear();
		
		final Aluno a = new Aluno(getTelefone(),getNome(),getDataDeNascimento(),
				getEmail(),getSexo(),usuarioAluno,getSenha(),getUsuarioPersonal(),
				confirmacaoPersonal,confirmacaoAluno,getFoto(),codTreinamento);
		
				Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"buscarAlunoEspecifico");
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
						ht.call(WebService.getSoapAction("buscarAlunoEspecifico"), envelope);
						
						SoapObject res = new SoapObject();
						
						try{
							res = (SoapObject) envelope.getResponse();
							//Log.i("debug", "cheguei aki --- > " + res.toString());
							
									
						}catch(Exception e){
							Log.i("Erro: buscarAlunoEspecifico (UNICO)", e.toString());
						}
				
						retornoAluno = getSoapAluno(res);
				        //Log.i("debug aluno", retornoAluno.toString());
					         
					}catch(Exception e){
						e.printStackTrace();
						Log.e("Erro: buscarAlunoEspecifico (VETOR)", e.toString());
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
			return retornoAluno;
	}
	
	public ArrayList<Aluno> buscarAlunosWeb(final String filtro){
		clear();
		final ArrayList<Aluno> retornoListaAluno = new ArrayList<Aluno>();
		final Aluno a = new Aluno(getTelefone(),getNome(),getDataDeNascimento(),
				getEmail(),getSexo(),filtro,getSenha(),getUsuarioPersonal(),
				confirmacaoPersonal,confirmacaoAluno,getFoto(),codTreinamento);
		
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"buscarAlunos");
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
						ht.call(WebService.getSoapAction("buscarAlunos"), envelope);
						
						SoapObject res = new SoapObject();
						Vector<SoapObject> response = new Vector<SoapObject>();
						
						try{
							res = (SoapObject) envelope.getResponse();
							//Log.i("debug", "cheguei aki --- > " + res.toString());
							
									
						}catch(Exception e){
							Log.i("Erro : buscarALunos Web (unico) ", e.toString());
						}
						
						try{
							
							response = (Vector<SoapObject>) envelope.getResponse();
							//Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
							
						}catch(Exception e){
							Log.i("Erro: Buscar Alunos WEB (vetor) ", e.toString());
						}

						if(response.isEmpty()){
							
							 	Aluno item = getSoapAluno(res);
						        //Log.i("alunos", item.toString());
						        retornoListaAluno.add(item);
						         
						}else{
							
							for(SoapObject soapAluno: response){  
						         Aluno item = getSoapAluno(soapAluno);
						      //   Log.i("alunos", item.toString());
						         retornoListaAluno.add(item);			       
					           }
							
						}
					         
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("Erro: buscarAlunoEspecifico", e.toString());
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
			
			return retornoListaAluno;
	}
	
	public ArrayList<Aluno> buscarAlunoPorPersonalWeb( String filtro,String usuarioPersonal){
		clear();
		
		final ArrayList<Aluno> retornoListaAluno = new ArrayList<Aluno>();
		final Aluno a = new Aluno(getTelefone(),getNome(),getDataDeNascimento(),
				getEmail(),getSexo(),filtro,getSenha(),usuarioPersonal,
				confirmacaoPersonal,confirmacaoAluno,getFoto(),codTreinamento);
		
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"buscarAlunosPorPersonal");
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
						ht.call(WebService.getSoapAction("buscarAlunosPorPersonal"), envelope);
						
						SoapObject res = new SoapObject();
						Vector<SoapObject> response = new Vector<SoapObject>();
						
						try{
							res = (SoapObject) envelope.getResponse();
							//Log.i("debug", "cheguei aki --- > " + res.toString());
							
									
						}catch(Exception e){
							Log.i("Erro: buscarAlunosPorPersonal (unico)", e.toString());
						}
						
						try{
							
							response = (Vector<SoapObject>) envelope.getResponse();
							//Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
							
						}catch(Exception e){
							Log.i("Erro: BuscarAlunosPorPersonal (vetor)", e.toString());
						}

						if(response.isEmpty()){
							
							 	Aluno item = getSoapAluno(res);
						        //Log.i("alunos", item.toString());
						        retornoListaAluno.add(item);
						         
						}else{
							
							for(SoapObject soapAluno: response){  
						         Aluno item = getSoapAluno(soapAluno);
						       //  Log.i("alunos", item.toString());
						         retornoListaAluno.add(item);			       
					           }
							
						}
					         
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("Erro: BuscarAlunosPorPersonal", e.toString());
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
			
			return retornoListaAluno;
	}

	public ArrayList<Aluno> buscarAlunoNaoConfirmadoPorPersonalWeb( String filtro,String usuarioPersonal){
		clear();
		
		final ArrayList<Aluno> retornoListaAluno = new ArrayList<Aluno>();
		
		final Aluno a = new Aluno(getTelefone(),getNome(),getDataDeNascimento(),
				getEmail(),getSexo(),filtro,getSenha(),usuarioPersonal,
				confirmacaoPersonal,confirmacaoAluno,getFoto(),codTreinamento);
		
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"buscarAlunoNaoConfirmadoPorPersonal");
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
						ht.call(WebService.getSoapAction("buscarAlunoNaoConfirmadoPorPersonal"), envelope);
						
						SoapObject res = new SoapObject();
						Vector<SoapObject> response = new Vector<SoapObject>();
						
						try{
							res = (SoapObject) envelope.getResponse();
							//Log.i("debug", "cheguei aki --- > " + res.toString());
							
									
						}catch(Exception e){
							Log.i("Erro: buscarAlunoNaoConfirmadoPorPersonal (unico)", e.toString());
						}
						
						try{
							
							response = (Vector<SoapObject>) envelope.getResponse();
							//Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
							
						}catch(Exception e){
							Log.i("Erro: BuscarAlunoNaoConfirmadoPorPersonal (vetor)", e.toString());
						}

						if(response.isEmpty()){
							
							 	Aluno item = getSoapAluno(res);
						        //Log.i("alunos", item.toString());
						        retornoListaAluno.add(item);
						         
						}else{
							
							for(SoapObject soapAluno: response){  
						         Aluno item = getSoapAluno(soapAluno);
						       //  Log.i("alunos", item.toString());
						         retornoListaAluno.add(item);			       
					           }
							
						}
					         
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("Erro: BuscarAlunoNaoConfirmadoPorPersonal", e.toString());
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
			
			return retornoListaAluno;
	}
	
	public Personal buscarPersonalNaoConfirmadoPorAlunoWeb(String usuarioAluno){
		clear();
		
		
		final Aluno a = new Aluno(getTelefone(),getNome(),getDataDeNascimento(),
				getEmail(),getSexo(),usuarioAluno,getSenha(),getUsuarioPersonal(),
				confirmacaoPersonal,confirmacaoAluno,getFoto(),codTreinamento);
		
		
		
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"buscarPersonalNaoConfirmadoPorAluno");
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
						ht.call(WebService.getSoapAction("buscarPersonalNaoConfirmadoPorAluno"), envelope);
						
						SoapObject res = new SoapObject();
						
						try{
							res = (SoapObject) envelope.getResponse();
							//Log.i("debug", "cheguei aki --- > " + res.toString());
							
									
						}catch(Exception e){
							Log.i("Erro: buscarPersonalNaoConfirmadoPorAlunoWeb (unico)", e.toString());
						}
					
							 	retornoPersonal = getSoapPersonal(res);
						        
						
					         
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("Erro: buscarPersonalNaoConfirmadoPorAlunoWeb", e.toString());
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

	public Bitmap buscarFotoAlunoWeb(final String filtro){
		clear();
		
		final Aluno a = new Aluno(getTelefone(),getNome(),getDataDeNascimento(),
				getEmail(),getSexo(),filtro,getSenha(),getUsuarioPersonal(),
				confirmacaoPersonal,confirmacaoAluno,getFoto(),codTreinamento);
		
	
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"buscarFotoAluno");
					PropertyInfo p1 = new PropertyInfo();
					p1.setName("Aluno");
					p1.setValue(a);
					p1.setType(new Aluno().getClass());
					
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
						ht.call(WebService.getSoapAction("buscarFotoAluno"), envelope);
						
						//Log.i("debug da resposta", "nao tem nada" + envelope.toString());
						
						SoapPrimitive res;
						
						try{
							res = (SoapPrimitive) envelope.getResponse();
							//Log.i("debug", "cheguei aki --- > " + res.toString());
							
							fotoAluno = Base64.decode(res.toString(), Base64.DEFAULT);
							
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
			
			Bitmap bmp = BitmapFactory.decodeByteArray(fotoAluno, 0, fotoAluno.length);
			return bmp;
			
	}
	
	public ArrayList<String> buscarNomesAlunosPersonalWeb(String usuarioPersonal,String filtro){
		clear();
		
		final ArrayList<String> retornoListaNomes = new ArrayList<String>();
				
		final Aluno a = new Aluno(getTelefone(),getNome(),getDataDeNascimento(),
				getEmail(),getSexo(),getUsuario(),getSenha(),getUsuarioPersonal(),
				confirmacaoPersonal,confirmacaoAluno,getFoto(),codTreinamento);
		
			Thread threadWs = new Thread(){
				
				@Override		
				public void run(){
					
					SoapObject request = new SoapObject(WebService.getNamespace(),"buscarNomesAlunosPersonal");
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
						ht.call(WebService.getSoapAction("buscarNomesAlunosPersonal"), envelope);
						
						SoapObject res = new SoapObject();
						Vector<SoapObject> response = new Vector<SoapObject>();
						
						try{
							res = (SoapObject) envelope.getResponse();
						//	Log.i("debug", "cheguei aki --- > " + res.toString());
							
									
						}catch(Exception e){
							Log.i("Erro: buscarNomesAlunosPersonal (unico)", e.toString());
						}
						
						try{
							
							response = (Vector<SoapObject>) envelope.getResponse();
						//	Log.i("debug", "cheguei aki no vetor --- > " + response.toString());
							
						}catch(Exception e){
							Log.i("Erro: buscarNomesAlunosPersonal (Vetor) ", e.toString());
						}

						if(response.isEmpty()){
							
							 	String item = res.toString();
						       // Log.i("alunos", item.toString());
						        retornoListaNomes.add(item);
						         
						}else{
							
							for(SoapObject soapAluno: response){  
						         String item = soapAluno.toString();
						      //   Log.i("alunos", item.toString());
						         retornoListaNomes.add(item);			       
					           }
							
						}
					         
					}catch(Exception e){
						//Toast.makeText(BuscarUsuario.this, "Erro ao salvar no servidor", Toast.LENGTH_SHORT).show();
						Log.e("Erro: buscarNomesAlunosPersonal", e.toString());
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
			
			return retornoListaNomes;
	}
	
	
	//gets e sets
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
	            item.setFoto(res.getPropertyAsString("foto"));
	     }
	     
	     return item;
	}
	public Aluno getSoapAluno(SoapObject res){
		 Aluno item = new Aluno(); 
        		
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
        if (res.hasProperty("usuarioPersonal")) {
               item.setUsuarioPersonal(res.getPropertyAsString("usuarioPersonal"));
        }
        if (res.hasProperty("confirmacaoPersonal")) {
            item.setConfirmacaoPersonal(Integer.parseInt(res.getPropertyAsString("confirmacaoPersonal")));
        }
        if (res.hasProperty("confirmacaoAluno")) {
            item.setConfirmacaoAluno(Integer.parseInt(res.getPropertyAsString("confirmacaoAluno")));
        }
        if (res.hasProperty("foto")) {
            item.setFoto(res.getPropertyAsString("foto"));
        }
        if (res.hasProperty("codTreinamento")) {
               item.setCodTreinamento(Integer.parseInt(res.getPropertyAsString("codTreinamento")));
        }
        
        return item;
		
	}
	public String getUsuarioPersonal() {
		return usuarioPersonal;
	}
	public void setUsuarioPersonal(String usuarioPersonal) {
		this.usuarioPersonal = usuarioPersonal;
	}
	public int getCodTreinamento() {
		return codTreinamento;
	}
	public void setCodTreinamento(int codTreinamento) {
		this.codTreinamento = codTreinamento;
	}
	public int getConfirmacaoPersonal(){
		return confirmacaoPersonal;
	}
	public void setConfirmacaoPersonal(int confirmacaoPersonal){
		this.confirmacaoPersonal = confirmacaoPersonal;
	}
	public int getConfirmacaoAluno(){
		return confirmacaoAluno;
	}
	public void setConfirmacaoAluno(int confirmacaoAluno){
		this.confirmacaoAluno = confirmacaoAluno;
	}
	
	
	//clean
	public void clear(){
		retornoPersonal = null;
		retorno = false;
		retornoAluno = null;
		retornoListaAluno.clear();
		retornoListaNomes.clear();
	}
	
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
        	return usuarioPersonal;
        case 8:
        	return confirmacaoPersonal;
        case 9:
        	return confirmacaoAluno;
        case 10: 
        	return super.getFoto();
        case 11: 
        	return codTreinamento;
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
            arg2.name = "usuarioPersonal";
            break;
        case 8:
            arg2.type = PropertyInfo.INTEGER_CLASS;
            arg2.name = "confirmacaoPersonal";
            break;
        case 9:
            arg2.type = PropertyInfo.INTEGER_CLASS;
            arg2.name = "confirmacaoAluno";
            break;
        case 10:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "foto";
            break;
        case 11:
            arg2.type = PropertyInfo.STRING_CLASS;
            arg2.name = "codTreinamento";
            break;
        default:
        	break;
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
        	setUsuarioPersonal(arg1.toString());
            break;
        case 8:
        	setConfirmacaoPersonal(Integer.parseInt(arg1.toString()));
            break;
        case 9:
        	setConfirmacaoAluno(Integer.parseInt(arg1.toString()));
            break;
        case 10:
        	super.setFoto(arg1.toString());
            break;
        case 11:
        	setCodTreinamento(Integer.parseInt(arg1.toString()));
            break;
        default:
            break;
        }
		
	}

	@Override
	public String toString() {
		return  super.toString() + "Aluno [usuarioPersonal=" + usuarioPersonal
				+ ", confirmacaoPersonal=" + confirmacaoPersonal
				+ ", confirmacaoAluno=" + confirmacaoAluno
				+ ", codTreinamento=" + codTreinamento + "]";
	}

	
	
}
