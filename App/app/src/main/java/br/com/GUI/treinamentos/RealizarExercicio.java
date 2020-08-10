package br.com.GUI.treinamentos;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.Banco.Banco;
import br.com.Classes.Aerobico;
import br.com.Classes.Aluno;
import br.com.Classes.Anaerobico;
import br.com.Classes.ExercicioRealizado;
import br.com.WorkUp.R;
import br.com.WorkUp.R.id;
import br.com.WorkUp.R.layout;
import br.com.WorkUp.R.menu;
import br.com.WorkUp.R.string;
import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class RealizarExercicio extends Activity {
	
	//Atributos de interface
	private TextView nomeExercicio;
	private TextView descansoExercicio;
	private TextView duracaoRepeticaoExercicio;
	private TextView descricaoExercicio;
	private Chronometer timerTempoExercicio;
	private ImageButton iniciarExercicio;
	private ImageButton pausarExercicio;
	private ImageButton finalizarExercicio;
	
	//Atributos auxiliares
	private Aerobico exercicioAerobico;
	private Anaerobico exercicioAnaerobico;
	private String tipoExercicio;
	private int codTreinamento;
	private int codTreinamentoRealizado;
	private int codExercicio;
	private boolean isRunning = false;
	private ExercicioRealizado exR;
	
	//Persistencias
	private SharedPreferences pref;
	private Banco b;
	
	//Creates and Resume
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_realizar_exercicio);
		mapearComponentes();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent i = getIntent();
		Bundle parametros = i.getExtras();
		tipoExercicio = parametros.getString("tipoExercicio");
		codTreinamentoRealizado = parametros.getInt("codTreinamentoRealizado");
		
		Log.i("tipoExercicio", tipoExercicio);
		
		if(tipoExercicio.equals("aerobico")){
			codExercicio = parametros.getInt("exercicioAerobico");
			//Log.i("codExercicio", ""+ codExercicio);
			exercicioAerobico = new Aerobico().buscarExerciciosPorId(b,codExercicio);
		}else if (tipoExercicio.equals("anaerobico")){
			codExercicio = parametros.getInt("exercicioAnaerobico");
			//Log.i("codExercicio", ""+ codExercicio);
			exercicioAnaerobico = new Anaerobico().buscarExerciciosPorId(b,codExercicio);
		}
		
		
		if(tipoExercicio.equals("anaerobico")){
			nomeExercicio.setText(exercicioAnaerobico.getNomeExercicio());
			descansoExercicio.setText(exercicioAnaerobico.getDescansoExercicio() + " minutos(s)");
			duracaoRepeticaoExercicio.setText(exercicioAnaerobico.getRepeticoesExercicio() + " minutos(s)");
			descricaoExercicio.setText(exercicioAnaerobico.getDescricaoExercicio());
		
		}else if(tipoExercicio.equals("aerobico")) {
			nomeExercicio.setText(exercicioAerobico.getNomeExercicio());
			descansoExercicio.setText(exercicioAerobico.getDescansoExercicio() + " minutos(s)");
			duracaoRepeticaoExercicio.setText(exercicioAerobico.getDuracaoExercicio() + " minuto(s)");
			descricaoExercicio.setText(exercicioAerobico.getDescricaoExercicio());
		}
		
		
		
	}

	public void mapearComponentes(){
		b = new Banco(this, null, null,0);
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		
		nomeExercicio = (TextView) findViewById(R.id.txtNomeExercicioRealizar);
		descansoExercicio = (TextView) findViewById(R.id.txtDescansoExercicioRealizar);
		duracaoRepeticaoExercicio = (TextView) findViewById(R.id.txtDuracaoRepeticoesExercicioRealizar);
		descricaoExercicio = (TextView) findViewById(R.id.txtDescricaoExercicioRealizar);
		timerTempoExercicio = (Chronometer)findViewById(R.id.timerTempoExercicio);
		
		//Typeface font = Typeface.createFromAsset(getAssets(), "BebasNeue Bold.ttf");
		
		iniciarExercicio = (ImageButton) findViewById(R.id.iniciarExercicio);
		iniciarExercicio.setEnabled(true);
		finalizarExercicio = (ImageButton) findViewById(R.id.finalizarExercicio);
		finalizarExercicio.setEnabled(false);
		pausarExercicio = (ImageButton) findViewById(R.id.pausarExercicio);
		pausarExercicio.setEnabled(false);
		
		
		mecanicaDosBotoes();
	}
	
	public void mecanicaDosBotoes(){
			iniciarExercicio.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Calendar c = Calendar.getInstance();
				
				String data = c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR);
				String hora = c.get(Calendar.HOUR_OF_DAY) + "h " + c.get(Calendar.MINUTE) + "min";
				
				finalizarExercicio.setEnabled(true);
				pausarExercicio.setEnabled(true);
				iniciarExercicio.setEnabled(false);
				timerTempoExercicio.start();
				
				
				
				if(pref.getString("tipo", null).equals("personal")){
					exR = new ExercicioRealizado(
							0,
							nomeExercicio.getText().toString(),
							hora,
							null,
							0,
							pref.getString("usuario", null),
							"null",
							tipoExercicio,
							codExercicio,
							"ativado", 
							codTreinamentoRealizado);
					
				}else if(pref.getString("tipo", null).equals("aluno")){
					
					exR = new ExercicioRealizado(
							0,
							nomeExercicio.getText().toString(),
							hora,
							null,
							0,
							new Aluno().buscarAlunoEspecifico(b, pref.getString("usuario", null)).getUsuarioPersonal(),
							pref.getString("usuario", null),
							tipoExercicio,
							codExercicio,
							"ativado", 
							codTreinamentoRealizado);
				}
				Log.i("usuario", exR.toString());
				Toast.makeText(RealizarExercicio.this, "Exercicio Iniciado!", Toast.LENGTH_SHORT).show();
				iniciarExercicio.setEnabled(false);
			}
		});
		
		finalizarExercicio.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				finalizarExercicio();
			}
		});
		
		pausarExercicio.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(isRunning){
					pausarExercicio.setBackground(getResources().getDrawable(R.drawable.refresh));
					timerTempoExercicio.stop();
					isRunning = false;
				}else{
					pausarExercicio.setBackground(getResources().getDrawable(R.drawable.pause));
					timerTempoExercicio.start();
					isRunning = true;
				}
				
			}
		});
	}
	
	public void finalizarExercicio(){
		Calendar c = Calendar.getInstance();
		
		String data = c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR);
		String hora = c.get(Calendar.HOUR_OF_DAY) + "h " + c.get(Calendar.MINUTE) + "min";
						
		timerTempoExercicio.stop();
		
		long timeElapsed = SystemClock.elapsedRealtime() - timerTempoExercicio.getBase();
		int hours = (int) (timeElapsed / 3600000);
		int duracaoExercicio = (int) (timeElapsed - hours * 3600000) / 60000;
		
		
		exR.setFimExercicio(hora);
		exR.setDuracaoExercicio(duracaoExercicio);
		try{
			int id = exR.salvarExercicioRealizadoAlunoWeb();
			if(id > 0 ){
				exR.setCodExercicio(id);
				exR.salvarExercicioRealizado(b);
				Toast.makeText(RealizarExercicio.this, "Exercicio Anaerobico Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
			}
			
			}catch(Exception e){
				e.printStackTrace();
				Log.i("Erro ao salvar o exercicio realizado", e.toString());
			}
		finish();
	}
	
	@Override
	public void onBackPressed(){
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Atenção");
        alertDialog.setMessage("Você tem certeza que deseja finalizar este Exercicio?");
        alertDialog.setIcon(R.drawable.critical);
        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog,int which) {
        		finalizarExercicio();
            }
        });
        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	dialog.cancel();
            }
        });
 
        alertDialog.show();
       
		
		
	}
	
	//MENUS
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.realizar_exercicio, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		if(id ==  android.R.id.home){
			onBackPressed();
			
			//NavUtils.navigateUpFromSameTask(this);
	        return true;
		}
		return super.onOptionsItemSelected(item);
	}



}
