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
package br.com.Utilitarios;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint.Join;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.renderscript.Sampler.Value;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import br.com.Banco.Banco;
import br.com.Classes.*;
import br.com.GUI.aulas.ConfirmarAula;
import br.com.GUI.perfil.AceitarRejeitarAmigo;
import br.com.GUI.perfil.AdicionarPersonal;
import br.com.GUI.perfil.BuscarUsuario;
import br.com.GUI.perfil.Login;
import br.com.WorkUp.R;

public class WorkUpService extends Service{
	private Updater updater;
	private static SharedPreferences pref;
	private static Banco b;
	private static Personal novoPersonalDoAluno = new Personal();
	private static ArrayList<Aluno> novosAlunosDoPersonal = new ArrayList<Aluno>();
	private static ArrayList<Aula> novasAulasDoPersonal = new ArrayList<Aula>(); 
	private static ArrayList<Aula> novasAulasDoAluno = new ArrayList<Aula>();

	//inicialização
	 @Override
	public void onCreate() {
		 
		 updater = new Updater();
		 
	  
	 }
	 
	 @Override
	public synchronized void onStart(Intent intent, int startId) {
		    super.onStart(intent, startId);
		    
		    pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		    b = new Banco(this,null,null,0);
		   
		    if(pref.getString("usuario", null) != null){
		    	new UpdateDatabase().execute();
		    }  
	}
	 
	
	//Controle de Atualizacoes
	 
	public void tenhoAtualizacoes() throws SQLException{
		
		clear();
		
		// pega atualizacoes do server
		
		ArrayList<Atualizacoes> atualizacoesAluno = new ArrayList<Atualizacoes>();
		ArrayList<Atualizacoes> atualizacoesPersonal = new ArrayList<Atualizacoes>();
		
		if(pref.getString("usuario", null) != null){
			if(pref.getString("tipo", null).equals("personal")){
				int tenhoAtualizacoes = new Atualizacoes().buscarAtualizacoesPendentesPersonalWeb(pref.getString("usuario",null));
				if(tenhoAtualizacoes > 0 ){
					atualizacoesPersonal = new Atualizacoes().getAtualizacoesPendentesPersonalWeb(pref.getString("usuario", null));
					for(Atualizacoes a: atualizacoesPersonal){
						
							a.salvarAtualizacao(b);
						
					}
				}
			}else if( pref.getString("tipo", null).equals("aluno")){
				int tenhoAtualizacoes = new Atualizacoes().buscarAtualizacoesPendentesAlunoWeb(pref.getString("usuario",null));
				if(tenhoAtualizacoes > 0 ){ 
					 atualizacoesAluno = new Atualizacoes().getAtualizacoesPendentesAlunoWeb(pref.getString("usuario", null));
					 for(Atualizacoes a : atualizacoesAluno){
						 
						 a.salvarAtualizacao(b);
					 }
				}
			}
		}
		
		atualizacoesAluno.clear();
		atualizacoesPersonal.clear();
		
		
		if(pref.getString("usuario", null)!= null){
			if(pref.getString("tipo", null).equals("personal")){
				int tenhoAtualizacoes = new Atualizacoes().buscarAtualizacoesPendentesPersonal(b,pref.getString("usuario",null));
				if(tenhoAtualizacoes > 0 ){
					atualizacoesPersonal = new Atualizacoes().getAtualizacoesPendentesPersonal(b,pref.getString("usuario", null));
					
					for(Atualizacoes n : atualizacoesPersonal){
						solucionarAtualizacoes(n);
					}
				}
			}else if(pref.getString("tipo", null).equals("aluno")){
			
				int tenhoAtualizacoes = new Atualizacoes().buscarAtualizacoesPendentesAluno(b,pref.getString("usuario",null));
				if(tenhoAtualizacoes > 0 ){
					atualizacoesAluno = new Atualizacoes().getAtualizacoesPendentesAluno(b,pref.getString("usuario", null));
					
					for(Atualizacoes n : atualizacoesAluno){
						solucionarAtualizacoes(n);
					}
				}
			}
		}	
	
	 }
	
	public void solucionarAtualizacoes(Atualizacoes n){
		if(n.getTipoAtualizacao().equals("atualizarPerfil") ||
				n.getTipoAtualizacao().equals("atualizarAlunos") || 
					n.getTipoAtualizacao().equals("atualizarTreinamentoPreescrito") || 
						n.getTipoAtualizacao().equals("atualizarAluno")){
			
			
			solucionarAtualizacaoPerfil(n);
		
		
		}else if(n.getTipoAtualizacao().equals("atualizarAvaliacao") || 
				n.getTipoAtualizacao().equals("atualizarTodasAvaliacoes")){
			
			solucionarAtualizacaoAvaliacao(n);
			
		}else if(n.getTipoAtualizacao().equals("atualizarAulasPersonal") || 
					n.getTipoAtualizacao().equals("atualizarAulasAluno")){
			
			solucionarAtualizacaoAula(n);
		
			
		}else if(n.getTipoAtualizacao().equals("atualizarTodosTreinamentos") ||
				 n.getTipoAtualizacao().equals("atualizarAcompanhamento")||
				 n.getTipoAtualizacao().equals("atualizarTodosAcompanhamentos")){
			
			solucionarAtualizacaoTreinamento(n);
			
			
		}
		
	}
	
	
	public void solucionarAtualizacaoPerfil(Atualizacoes n){
		if(n.getTipoAtualizacao().equals("atualizarPerfil")){
			
			if(pref.getString("tipo", null).equals("personal")){
				
				
				Personal p = new Personal().buscarPersonalWeb(pref.getString("usuario", null));
				
				Bitmap foto = new Personal().buscarFotoPersonalWeb(pref.getString("usuario", null));
		
				byte[] fotoPersonal = ImageUtils.bitmapToByteArray(foto);
				
				if(p.atualizar(b,fotoPersonal)){ 
					Log.i("Serviço 'AtualizarPersonal'", "sucesso!");
				}else{
					Log.i("Serviço 'AtualizarPersonal'", "falha!");
				}
				
				
									
			}else if(pref.getString("tipo", null).equals("aluno")){
				Aluno a = new Aluno().buscarAlunoEspecificoWeb(pref.getString("usuario", null));
				
				Bitmap foto = new Aluno().buscarFotoAlunoWeb(pref.getString("usuario", null));
				
				
				byte[] fotoAluno = ImageUtils.bitmapToByteArray(foto);
				
				if(a.atualizar(b,fotoAluno)){ 
					if(a.getUsuarioPersonal() != null || a.getUsuarioPersonal().equals("")|| a.getUsuarioPersonal().equals("null")){
						Personal meuPersonal = new Personal().buscarPersonalWeb(a.getUsuarioPersonal());
						
						Bitmap fotoMeuPersonal = new Personal().buscarFotoPersonalWeb(a.getUsuarioPersonal());
						
						meuPersonal.atualizar(b, ImageUtils.bitmapToByteArray(fotoMeuPersonal));
					}
					Log.i("Serviço 'AtualizarPersonal'", "sucesso!");
				}else{
					Log.i("Serviço 'AtualizarPersonal'", "falha!");
				}
			}
			
		// Setar visualizado
			n.visualizarAtualizacoes(b, n.getCodAtualizacao());
			n.visualizarAtualizacoesWeb(n.getCodAtualizacaoServidor());
			
		//-----------
		}else if(n.getTipoAtualizacao().equals("atualizarAlunos")){
			if(n.getExtra().equals("null") || 
					n.getExtra() == null || 
					n.getExtra().equals("") || 
					n.getExtra().equals("anyType{}")){
				
				ArrayList<Aluno> listaWeb = new Aluno().buscarAlunoPorPersonalWeb("",n.getDestinoAtualizacao());
				for(Aluno a : listaWeb){
					Aluno f = new Aluno().buscarAlunoEspecifico(b, a.getUsuario());
					if (f == null){
						byte[] fotoAluno = ImageUtils.bitmapToByteArray(new Aluno().buscarFotoAlunoWeb(a.getUsuario()));
						a.salvar(b, fotoAluno);
					}else{
						byte[] fotoAluno = ImageUtils.bitmapToByteArray(new Aluno().buscarFotoAlunoWeb(a.getUsuario()));
						a.atualizar(b,fotoAluno);
					}
				}
			}else{
				
				Aluno f = new Aluno().buscarAlunoEspecificoWeb(n.getExtra());
				if(f != null ){
					byte[] fotoAluno = ImageUtils.bitmapToByteArray(new Aluno().buscarFotoAlunoWeb(f.getUsuario()));
					f.atualizar(b,fotoAluno);
				}
			}
			
			// Setar visualizado
			n.visualizarAtualizacoes(b, n.getCodAtualizacao());
			if(n.getLocalAtualizacao().equals("server")){
				n.visualizarAtualizacoesWeb(n.getCodAtualizacaoServidor());
			}
			
			//-----------
		} else if(n.getTipoAtualizacao().equals("atualizarTreinamentoPreescrito")){
			Aluno a = new Aluno().buscarAlunoEspecificoWeb(n.getDestinoAtualizacao());
			if(a.getCodTreinamento() > 0 ){
				Treinamento t = new Treinamento().buscarTreinamentoPorIdWeb(a.getCodTreinamento());
				ArrayList<Aerobico> exeAerobicos = new Treinamento().buscarExerciciosAerobicoPorTreinamentoWeb(a.getCodTreinamento());
				ArrayList<Anaerobico> exeAnaerobicos = new Treinamento().buscarExerciciosAnaerobicoPorTreinamentoWeb(a.getCodTreinamento());
				
				for(Aerobico x : exeAerobicos){
					x.salvarExercicio(b, a.getUsuarioPersonal());
				}
				for(Anaerobico x : exeAnaerobicos){
					x.salvarExercicio(b, a.getUsuarioPersonal());
				}
				
				t.setExerciciosAerobicos(exeAerobicos);
				t.setExerciciosAnaerobicos(exeAnaerobicos);
				
				t.salvarTreinamento(b, a.getUsuarioPersonal());
				
				
			}
			
			
			n.visualizarAtualizacoes(b, n.getCodAtualizacao());
			if(n.getLocalAtualizacao().equals("server")){
				n.visualizarAtualizacoesWeb(n.getCodAtualizacaoServidor());
			}
			
		}else if (n.getTipoAtualizacao().equals("atualizarAluno")){
			Aluno c = new Aluno().buscarAlunoEspecificoWeb(pref.getString("usuario", null));
			byte[] foto = c.getFoto().getBytes();
			
			c.atualizar(b,foto);
		
			// Setar visualizado
			n.visualizarAtualizacoes(b, n.getCodAtualizacao());
			if(n.getLocalAtualizacao().equals("server")){
				n.visualizarAtualizacoesWeb(n.getCodAtualizacaoServidor());
			}
			//-----------
		}
		
	}
	
	public void solucionarAtualizacaoAvaliacao(Atualizacoes n){
		if (n.getTipoAtualizacao().equals("atualizarAvaliacao")){
			Log.i("valor do n", "n = " + n.getExtra());
			Avaliacoes f = new Avaliacoes().buscarAvaliacoesPorId(b, Integer.parseInt(n.getExtra()));
			if (f == null){
				
				Avaliacoes salvar = new Avaliacoes().buscarAvaliacoesPorIdWeb(Integer.parseInt(n.getExtra()));
				
				salvar.salvarAvaliacoes(b);
				
				GorduraCorporal g = new GorduraCorporal().buscarGorduraCorporalPorIdWeb(Integer.parseInt(n.getExtra()));
				if(g != null){
					if(g.salvarGorduraCorporal(b)){
						Log.i("sucesso", "gordura salva com sucesso");
					}else{
						Log.i("Erro: enviarAvaliacoesLocal", "Houve um erro ao enviar a gordura corporal avaliacao ");
					}
				}
				
				Perimetria p = new Perimetria().buscarPerimetriaPorIdWeb(Integer.parseInt(n.getExtra()));
				if(p != null) {
					if(p.salvarPerimetria(b)){
						Log.i("sucesso", "perimetria salva com sucesso");
					}else{
						Log.i("Erro: enviarAvaliacoes", "Houve um erro ao enviar a perimetria avaliacao ");
					}
				}
				
				QuestionarioQPAF q = new QuestionarioQPAF().buscarQuestionarioQPAFPorIdWeb(Integer.parseInt(n.getExtra()));
				if(q != null){
					if(q.salvarQuestinarioQPAF(b)){
						Log.i("sucesso", "QPAF salva com sucesso");
					}else{
						Log.i("Erro: enviarAvaliacoes", "Houve um erro ao enviar  o questionario avaliacao ");
					}
				}
				
				SituacaoCoronaria s = new SituacaoCoronaria().buscarSituacaoCoronariaPorIdWeb(Integer.parseInt(n.getExtra()));
				if(s != null){
					if(s.salvarSituacaoCoronaria(b)){
						Log.i("sucesso", "Situação coronaria salva com sucesso");
					}else{
						Log.i("Erro: enviarAvaliacoes LOcal", "Houve um erro ao enviar a situacao  coronaria avaliacao ");
					}
				}
				
				FotosAvaliacao fotos = new FotosAvaliacao().buscarFotosAvaliacaoPorIdWeb(Integer.parseInt(n.getExtra()));
				if(fotos != null){
					if(fotos.salvarAvaliacoes(b)){
						Log.i("sucesso", "gordura salva com sucesso");
					}else{
						Log.i("Erro: enviarAvaliacoes", "Houve um erro ao enviar as fotos da avaliacao ");
					}
				}
			}else{
				
				//editar
				
				GorduraCorporal g = new GorduraCorporal().buscarGorduraCorporalPorIdWeb(Integer.parseInt(n.getExtra()));
				if(g != null){
					if(g.editarGorduraCorporal(b)){
						Log.i("sucesso", "gordura editada com sucesso");
					}else{
						Log.i("Erro: enviarAvaliacoesLocal", "Houve um erro ao enviar a gordura corporal avaliacao ");
					}
				}
				
				Perimetria p = new Perimetria().buscarPerimetriaPorIdWeb(f.getCodAvaliacao());
				if(p != null) {
					if(p.editar(b)){
						Log.i("sucesso", "perimetria salva com sucesso");
					}else{
						Log.i("Erro: enviarAvaliacoes", "Houve um erro ao enviar a perimetria avaliacao ");
					}
				}
				
				QuestionarioQPAF q = new QuestionarioQPAF().buscarQuestionarioQPAFPorIdWeb(f.getCodAvaliacao());
				if(q != null){
					if(q.editar(b)){
						Log.i("sucesso", "QPAF salva com sucesso");
					}else{
						Log.i("Erro: enviarAvaliacoes", "Houve um erro ao enviar  o questionario avaliacao ");
					}
				}
				
				SituacaoCoronaria s = new SituacaoCoronaria().buscarSituacaoCoronariaPorIdWeb(f.getCodAvaliacao());
				if(s != null){
					if(s.editar(b)){
						Log.i("sucesso", "Situação coronaria salva com sucesso");
					}else{
						Log.i("Erro: enviarAvaliacoes LOcal", "Houve um erro ao enviar a situacao  coronaria avaliacao ");
					}
				}
				
				FotosAvaliacao fotos = new FotosAvaliacao().buscarFotosAvaliacaoPorIdWeb(f.getCodAvaliacao());
				if(fotos != null){
					if(fotos.editar(b)){
						Log.i("sucesso", "gordura salva com sucesso");
					}else{
						Log.i("Erro: enviarAvaliacoes", "Houve um erro ao enviar as fotos da avaliacao ");
					}
				}
			}
	
		
		// Setar visualizado
			n.visualizarAtualizacoes(b, n.getCodAtualizacao());
			if(n.getLocalAtualizacao().equals("server")){
				n.visualizarAtualizacoesWeb(n.getCodAtualizacaoServidor());
			}
		//-----------
	}else if (n.getTipoAtualizacao().equals("atualizarTodasAvaliacoes")){
		ArrayList<Avaliacoes> listaAvaliacoes = new ArrayList<Avaliacoes>();
		if(pref.getString("tipo", null).equals("personal")){
			listaAvaliacoes = new Avaliacoes().buscarAvaliacoesCompletasPorPersonalWeb(pref.getString("usuario", null), "");
		}else{
			listaAvaliacoes = new Avaliacoes().buscarAvaliacoesCompletasPorAlunoWeb(pref.getString("usuario", null));
		}
		
		if(!listaAvaliacoes.isEmpty()){
			for(Avaliacoes a : listaAvaliacoes){
				if (a != null){
					a.salvarAvaliacoes(b);
					//Log.i("passos","salvei a avaliacao");
					GorduraCorporal g = new GorduraCorporal().buscarGorduraCorporalPorIdWeb(a.getCodAvaliacao());
					if(g != null){
						if(g.salvarGorduraCorporal(b)){
							Log.i("sucesso", "gordura salva com sucesso");
						}else{
							Log.i("Erro:  sincronizar", "Houve um erro ao sincronizar a gordura corporal avaliacao ");
						}
					}
					//Log.i("passos","salvei a gordura Corporal");
					Perimetria p = new Perimetria().buscarPerimetriaPorIdWeb(a.getCodAvaliacao());
					if(p != null) {
						if(p.salvarPerimetria(b)){
							Log.i("sucesso", "perimetria salva com sucesso");
						}else{
							Log.i("Erro: sincronizar", "Houve um erro ao enviar a perimetria avaliacao ");
						}
					}
				//	Log.i("passos","salvei a perimetria");
					QuestionarioQPAF q = new QuestionarioQPAF().buscarQuestionarioQPAFPorIdWeb(a.getCodAvaliacao());
					if(q != null){
						if(q.salvarQuestinarioQPAF(b)){
							Log.i("sucesso", "QPAF salva com sucesso");
						}else{
							Log.i("Erro: sincronizar", "Houve um erro ao enviar  o questionario avaliacao ");
						}
					}
					//Log.i("passos","salvei a Questionario QPAF");
					SituacaoCoronaria s = new SituacaoCoronaria().buscarSituacaoCoronariaPorIdWeb(a.getCodAvaliacao());
					if(s != null){
						if(s.salvarSituacaoCoronaria(b)){
							Log.i("sucesso", "Situação coronaria salva com sucesso");
						}else{
							Log.i("Erro: enviarAvaliacoes LOcal", "Houve um erro ao enviar a situacao  coronaria avaliacao ");
						}
					}
					
					//Log.i("passos","salvei a Situacao Coronaria");
					FotosAvaliacao fotos = new FotosAvaliacao().buscarFotosAvaliacaoPorIdWeb(a.getCodAvaliacao());
					if(fotos != null){
						if(fotos.salvarAvaliacoes(b)){
							Log.i("sucesso", "Fotos salvas com sucesso");
						}else{
							Log.i("Erro: sincronizar", "Houve um erro ao enviar as fotos da avaliacao ");
						}
					}
				}
			}
		}
		
		// Setar visualizado
		n.visualizarAtualizacoes(b, n.getCodAtualizacao());
		if(n.getLocalAtualizacao().equals("server")){
			n.visualizarAtualizacoesWeb(n.getCodAtualizacaoServidor());
		}		
		}
	}
	
	public void solucionarAtualizacaoAula(Atualizacoes n){
		if (n.getTipoAtualizacao().equals("atualizarAulasPersonal")){
			ArrayList<Aula> listaAulas = new Aula().buscarAulasPorPersonalWeb(n.getDestinoAtualizacao(),"");
			for(Aula a : listaAulas){
				Log.i("aula", a.toString());
				Aula f = new Aula().buscarAulasPorId(b, a.getCodAula());
				
				if (f == null){
					a.agendarAula(b);
				}else{
					Log.i("Aula que voltou no f","aula" +  f.toString());
					a.atualizarAula(b);
				}
			}
			
			
			// Setar visualizado
			n.visualizarAtualizacoes(b, n.getCodAtualizacao());
			if(n.getLocalAtualizacao().equals("server")){
				n.visualizarAtualizacoesWeb(n.getCodAtualizacaoServidor());
			}
			//-----------
		}else if (n.getTipoAtualizacao().equals("atualizarAulasAluno")){
			Log.i("AtualizarAulasAluno", "Executando...");
			ArrayList<Aula> listaAulas = new Aula().buscarAulasPorAlunoWeb(n.getDestinoAtualizacao(),"");
			for(Aula a : listaAulas){
				Aula f = new Aula().buscarAulasPorId(b, a.getCodAula());
				if (f == null){
					a.agendarAula(b);
				}else{
					a.atualizarAula(b);
				}
			}
			
			n.visualizarAtualizacoes(b, n.getCodAtualizacao());
			if(n.getLocalAtualizacao().equals("server")){
				n.visualizarAtualizacoesWeb(n.getCodAtualizacaoServidor());
			}
		
			
		}
	}
	
	public void solucionarAtualizacaoTreinamento(Atualizacoes n){

		if (n.getTipoAtualizacao().equals("atualizarTodosTreinamentos")){
			
			
			ArrayList<Treinamento> sincronizar = new Treinamento().buscarTreinamentosWeb(pref.getString("usuario", null),"");
			try{
				
			
				
				if(!sincronizar.isEmpty()){	
					for(Treinamento x: sincronizar){
						//Declara os arrays de exercicio
						ArrayList<Aerobico> exeAerobicos = new ArrayList<Aerobico>();
						ArrayList<Anaerobico> exeAnaerobicos = new ArrayList<Anaerobico>();
						
						//busca os exercicios
						try{
							exeAerobicos = new Treinamento().buscarExerciciosAerobicoPorTreinamentoWeb(x.getCodTreinamento());
							exeAnaerobicos = new Treinamento().buscarExerciciosAnaerobicoPorTreinamentoWeb(x.getCodTreinamento());
						}catch(Exception e){
							e.printStackTrace();
						}
					
						for(Aerobico y : exeAerobicos){
							y.salvarExercicio(b, pref.getString("usuario", null));
						}
						for(Anaerobico y: exeAnaerobicos){
							y.salvarExercicio(b, pref.getString("usuario", null));
						}
						
						x.setExerciciosAerobicos(exeAerobicos);
						x.setExerciciosAnaerobicos(exeAnaerobicos);
						
						x.salvarTreinamento(b, pref.getString("usuario", null));
						
					}
				}	
			}catch(Exception e){
				e.printStackTrace();
			}
			n.visualizarAtualizacoes(b, n.getCodAtualizacao());
			if(n.getLocalAtualizacao().equals("server")){
				n.visualizarAtualizacoesWeb(n.getCodAtualizacaoServidor());
			}
		}else if(n.getTipoAtualizacao().equals("atualizarAcompanhamento")){
			
			Log.i("atualizando acompanhamento...", "atualizando acompanhamento...");
			
			TreinamentoRealizado treinamentoSalvar = new TreinamentoRealizado().buscarTreinamentoRealizadoPorIdWeb(Integer.parseInt(n.getExtra()));
			Log.i("Treinamento salvo", "sendo salvo = " + treinamentoSalvar.toString());
			
			if(treinamentoSalvar != null){
				if(treinamentoSalvar.salvarTreinamentoRealizado(b)){
					ArrayList<ExercicioRealizado> exerciciosSalvar = new TreinamentoRealizado().buscarExerciciosRealizadoPorTreinamentoWeb(Integer.parseInt(n.getExtra()));
					
					for(ExercicioRealizado e : exerciciosSalvar){
						Log.i("exercicio realizado", "sendo salvo = " + e.toString());
						try{
							if(e.salvarExercicioRealizado(b)){
								Log.i("Exercicio Realizado", "sincronizado!");
							}
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}
					
				}
			}
			
			n.visualizarAtualizacoes(b, n.getCodAtualizacao());
			if(n.getLocalAtualizacao().equals("server")){
				n.visualizarAtualizacoesWeb(n.getCodAtualizacaoServidor());
			}
		
		}else if(n.getTipoAtualizacao().equals("atualizarTodosAcompanhamentos")){
			
			Log.i("atualizando todos os acompanhamentos...", "atualizando todos os acompanhamentos...");
			
			
			if(pref.getString("tipo", null).equals("personal")){
				ArrayList<Aluno> meusAlunos = new Aluno().buscarAlunosPorPersonal(b, "", pref.getString("usuario", null));
				
				for(Aluno al : meusAlunos){
					
					ArrayList<TreinamentoRealizado> treinamentosSalvar = new TreinamentoRealizado().buscarTreinamentoPorAlunoWeb(al.getUsuario());
					
						if(treinamentosSalvar.isEmpty()){
							Log.i("Warning", "o aluno " + al.getUsuario() + "não possui nenhum treinamento realizado");
						}else{
							
							for(TreinamentoRealizado r : treinamentosSalvar){
								if(r.salvarTreinamentoRealizado(b)){
									ArrayList<ExercicioRealizado> exerciciosSalvar = new TreinamentoRealizado().buscarExerciciosRealizadoPorTreinamentoWeb(Integer.parseInt(n.getExtra()));
									
									for(ExercicioRealizado e : exerciciosSalvar){
										try{
											if(e.salvarExercicioRealizado(b)){
												Log.i("Exercicio Realizado", "sincronizado!");
											}
										}catch(Exception ex){
											ex.printStackTrace();
										}
									}
									
								
								}else{
									Log.i("Erro ao sincronizar treinamento realizado", "erro ao sincronizar Treinamento Realizado");
								}
							}
						}
				}	
					
			}else if(pref.getString("tipo", null).equals("aluno")){
				Aluno eu  = new Aluno().buscarAlunoEspecifico(b, pref.getString("usuario", null));
				
				ArrayList<TreinamentoRealizado> treinamentosSalvar = new TreinamentoRealizado().buscarTreinamentoPorAlunoWeb(eu.getUsuario());
				
				
				for(TreinamentoRealizado r : treinamentosSalvar){
					if(r.salvarTreinamentoRealizado(b)){
						ArrayList<ExercicioRealizado> exerciciosSalvar = new TreinamentoRealizado().buscarExerciciosRealizadoPorTreinamentoWeb(Integer.parseInt(n.getExtra()));
						
						for(ExercicioRealizado e : exerciciosSalvar){
							try{
								if(e.salvarExercicioRealizado(b)){
									Log.i("Exercicio Realizado", "sincronizado!");
								}
							}catch(Exception ex){
								ex.printStackTrace();
							}
						}
						
					
					}else{
						Log.i("Erro ao sincronizar treinamento realizado", "erro ao sincronizar Treinamento Realizado");
					}
				}
			}
			
			n.visualizarAtualizacoes(b, n.getCodAtualizacao());
			if(n.getLocalAtualizacao().equals("server")){
				n.visualizarAtualizacoesWeb(n.getCodAtualizacaoServidor());
			}
			
		}
			
		
			
		
	}
	
	// Controle de notificações
	public void criarNotificacoes(Notificacoes n, 
									String titulo,
									String texto, 
									String ticker,
									String tipo,
									String extra){
		
	    // Build notification
		NotificationCompat.Builder  noti = new NotificationCompat.Builder(this);
	 		  noti.setContentTitle(titulo);
	 	      noti.setContentText(texto);
	 	      noti.setTicker(ticker);
	 	      noti.setSmallIcon(R.drawable.ic_launcher);
	 	      Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	 	      noti.setSound(alarmSound);
	
	    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	   
	    Intent resultIntent = null;
	    if(tipo.equals("novoPersonal")){
	    	 // Creates an expnovaAvaliacaolicit intent for an Activity in your app
	    		resultIntent = new Intent(this, AceitarRejeitarAmigo.class);
			    resultIntent.putExtra("usuario", n.getOrigemNotificacao());
			    resultIntent.putExtra("tipo", "aluno");
	    }
	    
	    if (tipo.equals("novoAluno")){
	    			    
			    resultIntent = new Intent(this, AceitarRejeitarAmigo.class);
			    resultIntent.putExtra("usuario", n.getOrigemNotificacao());
			    resultIntent.putExtra("tipo", "personal");
	    	
	    }
	    
	    if(tipo.equals("novaAula")){
	    	 // Creates an explicit intent for an Activity in your app
	    		resultIntent = new Intent(this, ConfirmarAula.class);
			    resultIntent.putExtra("codAula", extra);
	    }
	   
	    //This ensures that navigating backward from the Activity leads out of the app to Home page
	    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
	    
	   if(tipo.equals("novoPersonal")|| tipo.equals("novoAluno")){
		   // Adds the back stack for the Intent
		    stackBuilder.addParentStack(AceitarRejeitarAmigo.class);
		    
		    // Adds the Intent that starts the Activity to the top of the stack
		    stackBuilder.addNextIntent(resultIntent);
		    PendingIntent resultPendingIntent =
		    stackBuilder.getPendingIntent(
		    0,
		    PendingIntent.FLAG_ONE_SHOT //can only be used once
		    );
		    // start the activity when the user clicks the notification text
		    noti.setContentIntent(resultPendingIntent);
	   }
	   
	   
	   // pass the Notification object to the system
	    notificationManager.notify(0, noti.build());
	    
	    n.visualizarNotificacao(n.getCodNotificacao());
	    
	    //----------------------------------------------------------------------------------
	}
	
	public void tenhoNotificacoes(){
		
		
		clear();
		//pegar atualizacoes no servidor
		
		ArrayList<Notificacoes> notificacoesAluno = new ArrayList<Notificacoes>();
		ArrayList<Notificacoes> notificacoesPersonal = new ArrayList<Notificacoes>();
		
		
		if(pref.getString("usuario", null)!= null){
			if(pref.getString("tipo", null).equals("personal")){
				int tenhoNotificacoes = new Notificacoes().buscarNotificacoesPendentesPersonal(pref.getString("usuario",null));
				if(tenhoNotificacoes > 0 ){
					try {
						notificacoesPersonal = new Notificacoes().getNotificacoesPendentesPersonal(pref.getString("usuario", null));
					} catch (SQLException e) {
						Log.i("Erro: receber notificacoes Personal", " Erro ao receber as notificacoes pendentes do Personal");
						e.printStackTrace();
					}
					
					for(Notificacoes n : notificacoesPersonal){
						solucionarNotificacao(n);
					}
				}
			}else if(pref.getString("tipo", null).equals("aluno")){
			
				int tenhoNotificacoes = new Notificacoes().buscarNotificacoesPendentesAluno(pref.getString("usuario",null));
				if(tenhoNotificacoes > 0 ){
					try {
						
						notificacoesAluno= new Notificacoes().getNotificacoesPendentesAluno(pref.getString("usuario", null));
					} catch (SQLException e) {
						Log.i("Erro: receber notificacoes Aluno", " Erro ao receber as notificacoes pendentes do Aluno");
						e.printStackTrace();
					}
					
					for(Notificacoes n : notificacoesAluno){
						solucionarNotificacao(n);
					}
				}
			}
		}	
	
	 }
	
	public void solucionarNotificacao(Notificacoes n){
		Log.i("notificacao",n.toString() );
		if(n.getTipoNotificacao().equals("novoTreinamento")){
			criarNotificacoes(n,
					"Você possui um novo Treinamento", 
					n.getDescricaoNotificacao(), 
					n.getDescricaoNotificacao(), 
					n.getTipoNotificacao(), 
					null);
			
		
			
		}else if( n.getTipoNotificacao().equals("novoPersonal")){
			
			criarNotificacoes(n,
					"Nova solicitação de amizade", 
					n.getDescricaoNotificacao(), 
					n.getDescricaoNotificacao(), 
					n.getTipoNotificacao(), 
					null);
		
			
		}else if(n.getTipoNotificacao().equals("novoAluno")){
			criarNotificacoes(n,
					"Nova solicitação de amizade",
					n.getDescricaoNotificacao(), 
					n.getDescricaoNotificacao(), 
					n.getTipoNotificacao(), 
					null);
			
		}else if(n.getTipoNotificacao().equals("conviteAceitoPersonal")){
			criarNotificacoes(n,
					"Solicitação confirmada", 
					n.getDescricaoNotificacao(), 
					n.getDescricaoNotificacao(), 
					n.getTipoNotificacao(),  
					null);
			
			
		}else if(n.getTipoNotificacao().equals("conviteAceitoAluno")){
			criarNotificacoes(n,
					"Solicitação confirmada", 
					n.getDescricaoNotificacao(), 
					n.getDescricaoNotificacao(), 
					n.getTipoNotificacao(),  
					null);
			
			
		}else if(n.getTipoNotificacao().equals("novaAvaliacao")){
			criarNotificacoes(n,
					"Nova avaliação disponível", 
					n.getDescricaoNotificacao(), 
					n.getDescricaoNotificacao(), 
					n.getTipoNotificacao(), 
					null);
			
		
		}else if(n.getTipoNotificacao().equals("novaAula")){
			criarNotificacoes(n,
					"Nova aula agendada", 
					n.getDescricaoNotificacao(), 
					n.getDescricaoNotificacao(), 
					n.getTipoNotificacao(), 
					null);
		}else if(n.getTipoNotificacao().equals("exclusaoDeAula")){
			criarNotificacoes(n,
					"Sua aula foi excluida", 
					n.getDescricaoNotificacao(), 
					n.getDescricaoNotificacao(), 
					n.getTipoNotificacao(), 
					null);
		}else if(n.getTipoNotificacao().equals("aulaConfirmada")){
			criarNotificacoes(n,
					"Sua aula foi confirmada", 
					n.getDescricaoNotificacao(), 
					n.getDescricaoNotificacao(), 
					n.getTipoNotificacao(), 
					null);
		}else if(n.getTipoNotificacao().equals("aulaCancelada")){
			criarNotificacoes(n,
					"Sua aula foi cancelada!", 
					n.getDescricaoNotificacao(), 
					n.getDescricaoNotificacao(), 
					n.getTipoNotificacao(), 
					null);
		}



		
	}
	
	//serviço
	@Override
	public synchronized void onDestroy() {
	    super.onDestroy();

	    // Stop the updater
	    if (updater.isRunning()) {
	      updater.interrupt();
	    }

	    updater = null;

	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		
		
		return null;
	}

	public void clear(){
		novoPersonalDoAluno = new Personal();
		novosAlunosDoPersonal.clear();
		novasAulasDoPersonal.clear();
		novasAulasDoAluno.clear();
	}
	
	
	
	//Background Service
		
	private class UpdateDatabase extends AsyncTask<String, Integer, String> {
			
		
		    @Override
		    protected String doInBackground(String... status) {
		    	 // Start the updater
			    	if (!updater.isRunning()) {
			    		//Log.i("entrei", "entrei");
			    		//Log.i("is running", String.valueOf(updater.isRunning));
					    	 updater.start();
					}
			    return null;
		    }
		    
		    @Override
		    protected void onProgressUpdate(Integer... values) {
		      super.onProgressUpdate(values);
		     
		    }
	
		    @Override
		    protected void onPostExecute(String result) {
		      super.onPostExecute(result);
		      
		      
		    }
		  }
	
	private class Updater extends Thread {
		    private static final long DELAY = 7000; // 3 segundos
		    private boolean isRunning = false;
		   

		    public Updater() {
		      super("Updater");
		    }
		    
		  

		    @Override
		    public void run() {
		      isRunning = true;
		      while (isRunning) {
		        try {
		        	//Log.i("entrei no wil", "while");

		        	
		        	tenhoNotificacoes();
		        	
		        	tenhoAtualizacoes();
		        	

		          // Sleep
		          Thread.sleep(DELAY);
		        } catch (InterruptedException e) {
		          // Interrupted
		          isRunning = false;
		          onDestroy();
		        } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      } // while
		    }

		    public boolean isRunning() {
		      return this.isRunning;
		    }

		  }

	}
