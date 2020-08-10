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

package br.com.Banco;

	
	import java.util.ArrayList;
	
	import android.content.Context;
	import android.database.Cursor;
	import android.database.sqlite.SQLiteDatabase;
	import android.database.sqlite.SQLiteDatabase.CursorFactory;
	import android.database.sqlite.SQLiteOpenHelper;
	import android.util.Log;
	
	public class Banco extends SQLiteOpenHelper{
	
	private static final String DB_NAME = "banco.db";
	private static final int DB_VERSION = 1;
	
	private SQLiteDatabase readDB = getReadableDatabase();
	private SQLiteDatabase writeDB = getWritableDatabase();
	
	public Banco(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DB_NAME, null, DB_VERSION);
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		ArrayList<String> script = new ArrayList<String>();
	
	

	script.add("CREATE TABLE Aerobico ("
			+ "codExercicioAerobico INTEGER PRIMARY KEY ,"
			+ "nomeExercicio TEXT,"
			+ "duracaoExercicio TEXT,"
			+ "descansoExercicio TEXT, "
			+ "descricaoExercicio TEXT, "
			+ "usuarioPersonal TEXT, "
			+ "ativo TEXT);");
	
	script.add("CREATE TABLE Anaerobico ("
			+ "codExercicioAnaerobico INTEGER PRIMARY KEY,"
			+ "nomeExercicio TEXT, "
			+ "descansoExercicio TEXT ,"
			+ "repeticoesExercicio INTEGER,"
			+ "descricaoExercicio TEXT,"
			+ "usuarioPersonal TEXT, "
			+ "ativo TEXT);");
	
	script.add( "CREATE TABLE Treinamento ("
			+ "codTreinamento INTEGER PRIMARY KEY,"
			+ "nomeTreinamento TEXT NOT NULL,"
			+ "usuarioPersonal TEXT, "
			+ "ativo TEXT);");
	
	script.add( "CREATE TABLE treinamentoTemExercicios ("
			+ "codAerobico INTEGER,"
			+ "codAnaerobico INTEGER, "
			+ "codTreinamento INTEGER, "
			+ "FOREIGN KEY(codAerobico) references Aerobico(codExercicioAerobico), "
			+ "FOREIGN KEY(codAnaerobico) references Anaerobico(codExercicioAnaerobico),"
			+ "FOREIGN KEY(codTreinamento) references Treinamento(codTreinamento));");
	
	
	script.add("CREATE TABLE TreinamentoRealizado ( "
			+ "codTreinamentoRealizado INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "dataTreino TEXT,"
			+ "horaInicio TEXT,"
			+ "horaFim TEXT,"
			+ "usuarioPersonal TEXT,"
			+ "usuarioAluno TEXT,"
			+ "ativo TEXT, "
			+ "codTreinamento INTEGER,"
			+ "FOREIGN KEY(codTreinamento) references Treinamento(codTreinamento));");
	
	script.add("CREATE TABLE exercicioRealizado ("
			+ "codExercicioRealizado INTEGER PRIMARY KEY,"
			+ "nomeExercicio TEXT NOT NULL,"
			+ "inicioExercicio TEXT NOT NULL,"
			+ "fimExercicio TEXT NOT NULL,"
			+ "duracaoExercicio INTEGER,"
			+ "usuarioPersonal TEXT NOT NULL,"
			+ "usuarioAluno TEXT NOT NULL,"
			+ "tipoExercicio TEXT,"
			+ "codExercicio INTEGER,"
			+ "ativo TEXT,"
			+ "codTreinamentoRealizado INTEGER,"
			+ "FOREIGN KEY (codTreinamentoRealizado) references treinamentoRealizado(codTreinamentoRealizado));");
	
	script.add("CREATE TABLE realizaExercicioAnaerobico ("
			+ "relCodExercicioAnaerobico INTEGER,"
			+ "relCodExercicioRealizadoAnaerobico INTEGER,"
			+ "FOREIGN KEY (relCodExercicioAnaerobico) references Anaerobico (codExercicioAnaerobico)"
			+ "FOREIGN KEY (relCodExercicioRealizadoAnaerobico) references exercicioRealizado (codExercicioRealizado));");
	
	script.add( "CREATE TABLE realizaExercicioAerobico ("
			+ "relCodExercicioAerobico INTEGER,"
			+ "relCodExercicioRealizadoAerobico INTEGER,"
			+ "FOREIGN KEY (relCodExercicioAerobico) references Aerobico (codExercicioAerobico)"
			+ "FOREIGN KEY (relCodExercicioRealizadoAerobico) references exercicioRealizado (codExercicioRealizado));");
	
	script.add( "CREATE TABLE Aluno ("
			+ "telefoneAluno TEXT,"
			+ "nomeAluno TEXT,"
			+ "dataDeNascimentoAluno TEXT,"
			+ "sexoAluno TEXT NOT NULL,"
			+ "emailAluno TEXT,"
			+ "senhaAluno TEXT,"
			+ "usuarioAluno TEXT PRIMARY KEY,"
			+ "codTreinamento INTEGER,"
			+ "usuarioPersonal TEXT, "
			+ "confirmacaoPersonal INTEGER,"
			+ "confirmacaoAluno INTEGER,"
			+ "fotoAluno BLOB,"
			+ "ativo TEXT, "
			+ "FOREIGN KEY(usuarioPersonal) REFERENCES Personal(usuarioPersonal) );");
	
	script.add("CREATE TABLE avaliacoes ("
			+ "codAvaliacao INTEGER PRIMARY KEY, "
			+ "horaAvaliacao TEXT,"
			+ "dataAvaliacao TEXT,"
			+ "usuarioPersonal TEXT, "
			+ "usuarioAluno TEXT,"
			+ "ativo TEXT, "
			+ "FOREIGN KEY(usuarioPersonal) REFERENCES Personal (usuarioPersonal),"
			+ "FOREIGN KEY(usuarioAluno) REFERENCES Aluno (usuarioAluno));");
			
	
	script.add("CREATE TABLE GorduraCorporal("
			//gordura
			+ "codAvaliacao INTEGER PRIMARY KEY,"
			+ "sexo TEXT,"
			+ "peso REAL,"
			+ "altura REAL,"
			+ "idade INTEGER,"
			+ "dobraAbdominal REAL, "
			+ "dobraCoxa REAL,"
			+ "dobraPeito REAL,"
			+ "dobraSuprailiaca REAL,"
			+ "dobraSubscapular REAL,"
			+ "dobraTriceps REAL, "
			+ "dobraLinhaAxilarMedia REAL,"
			+ "dobraPanturrilha REAL,"
			+ "resultadoAvaliacao REAL);");
	
	script.add("CREATE TABLE Perimetria("
			+ "codAvaliacao INTEGER PRIMARY KEY,"
			+ "bicepsContraidoDireito REAL,"
			+ "coxaDistalEsquerda REAL,"
			+ "antebraco REAL,"
			+ "bicepsContraidoEsquerdo REAL,"
			+ "coxaProximalEsquerda REAL,"
			+ "cintura REAL,"
			+ "coxaProximalDireita REAL,"
			+ "panturrilhaEsquerda REAL,"
			+ "peito REAL,"
			+ "quadril REAL,"
			+ "panturrilhaDireita REAL,"
			+ "idade INTEGER,"
			+ "coxaDistalDireita REAL,"
			+ "coxaMedialEsquerda REAL,"
			+ "coxaMedialDireita REAL,"
			+ "ombro REAL,"
			+ "bicepsRelaxadoEsquerdo REAL,"
			+ "abdomen REAL,"
			+ "bicepsRelaxadoDireito REAL);");
	
	script.add("CREATE TABLE QuestionarioQPAF("
			+ "codAvaliacao INTEGER PRIMARY KEY,"
			+ "questao1 TEXT,"
			+ "questao2 TEXT,"
			+ "questao3 TEXT,"
			+ "questao4 TEXT,"
			+ "questao5 TEXT,"
			+ "questao6 TEXT,"
			+ "questao7 TEXT);");
	
	script.add("CREATE TABLE SituacaoCoronaria("
			+ "codAvaliacao INTEGER PRIMARY KEY,"
			+ "objetivoDoTreinamento TEXT,"
			+ "pressaoSistolicaMaxima INTEGER,"
			+ "pressaoDiastolicaMaxima INTEGER,"
			+ "pressaoSistolicaDeRepouso INTEGER,"
			+ "pressaoDiastolicaDeRepouso INTEGER);");
	
	script.add("CREATE TABLE FotosAvaliacao("
			+ "codAvaliacao INTEGER PRIMARY KEY,"
			+ "bicepsContraido BLOB,"
			+ "bicepsRelaxado BLOB,"
			+ "panturrilha BLOB,"
			+ "coxaProximal BLOB,"
			+ "coxaMedial BLOB,"
			+ "coxaDistal BLOB,"
			+ "antebracoOmbro BLOB,"
			+ "quadrilAbdomenPeito BLOB );");
	
	script.add( "CREATE TABLE Personal ("
			+ "telefonePersonal TEXT,"
			+ "nomePersonal TEXT NOT NULL,"
			+ "dataDeNascimentoPersonal TEXT,"
			+ "emailPersonal TEXT,"
			+ "sexoPersonal TEXT NOT NULL,"
			+ "senhaPersonal TEXT NOT NULL,"
			+ "usuarioPersonal TEXT PRIMARY KEY,"
			+ "fotoPersonal BLOB,"
			+ "ativo TEXT);");

	
	script.add( "CREATE TABLE Aula ("
			+ "codAula INTEGER PRIMARY KEY,"
			+ "diaAula INTEGER,"
			+ "mesAula INTEGER,"
			+ "anoAula INTEGER,"
			+ "horaAula INTEGER,"
			+ "minutoAula INTEGER,"
			+ "duracaoAula INTEGER,"
			+ "confirmacaoAulaPersonal INTEGER,"
			+ "confirmacaoAulaAluno INTEGER,"
			+ "usuarioPersonal TEXT,"
			+ "usuarioAluno TEXT,"
			+ "ativo TEXT,"
			+ "FOREIGN KEY(usuarioPersonal) REFERENCES Personal(usuarioPersonal),"
			+ "FOREIGN KEY(usuarioAluno) REFERENCES Aluno(usuarioAluno));");
	
	
	script.add("CREATE TABLE atualizacoes( "
			+ "codAtualizacao INTEGER PRIMARY KEY AUTOINCREMENT,"  
			+ "localAtualizacao varchar(50)," 
			+ "destinoAtualizacao varchar(50),"  
			+ "tipoAtualizacao varchar(50),"
			+ "extra TEXT,"
			+ "codAtualizacaoServidor INTEGER,"  
			+ "visualizada INTEGER);");

	
	try{
		for(String s : script) {
			//Log.i("sql", s);
			arg0.execSQL(s);
		}
		
	}catch(Exception e){
		Log.i("erro execucao do banco", e.toString());
	}

	
	}



	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	public void execSQL(String SQL) {
		writeDB.execSQL(SQL);
	}
	
	public Cursor querySQL(String SQL) {
		return readDB.rawQuery(SQL, null);
	}

}
