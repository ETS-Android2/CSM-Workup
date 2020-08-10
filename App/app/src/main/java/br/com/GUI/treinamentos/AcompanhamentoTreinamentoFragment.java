package br.com.GUI.treinamentos;

import java.util.ArrayList;

import br.com.Banco.Banco;
import br.com.Classes.Avaliacoes;
import br.com.Classes.TreinamentoRealizado;
import br.com.GUI.avaliacoes.VisualizarAvaliacao;
import br.com.WorkUp.R;
import br.com.CustomListView.CustomAdapterAvaliacoes;
import br.com.CustomListView.CustomAdapterTreinamentosRealizado;
import br.com.CustomListView.RowItemAvaliacao;
import br.com.CustomListView.RowItemTreinamentoRealizado;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Switch;

public class AcompanhamentoTreinamentoFragment extends Fragment {
	
	private ListView lstAcompanhamentoAlunoTreinamentosRealizados;
	private Button btnIniciarTreinamentoAlunos;
	private Switch swcExibirTreinamentoOuAvaliacao;
	
	private ArrayList<TreinamentoRealizado> buscaTreinos = new ArrayList<TreinamentoRealizado>();	
	private String usuarioAluno;
	
	private SharedPreferences pref;
	private Banco b;
	
	private SharedPreferences avaliacoes;
	

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.activity_acompanhamento_aluno_fragment, container, false);
	        
	        return rootView;
	 }	
	
	
	public void mapearComponentes(){

		b = new Banco(getActivity(), null, null, 0);
		pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
		avaliacoes = getActivity().getApplicationContext().getSharedPreferences("acompanhamento", 0);
		
		
		lstAcompanhamentoAlunoTreinamentosRealizados = (ListView) getActivity().findViewById(R.id.lstAcompanhamentoAlunoTreinamentosRealizados);
		lstAcompanhamentoAlunoTreinamentosRealizados.setDivider(null);
		btnIniciarTreinamentoAlunos = (Button) getActivity().findViewById(R.id.btnIniciarTreinamentoAluno);
		swcExibirTreinamentoOuAvaliacao = (Switch) getActivity().findViewById(R.id.swcVisualizarTreinamentoOuAvaliacao);
		
		
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		  super.onActivityCreated(savedInstanceState);
		  getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		
		  mapearComponentes();
		  
		  if(pref.getString("tipo", null).equals("aluno")){
			swcExibirTreinamentoOuAvaliacao.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					if(arg1){
						atualizarAcompanhamento();
					}else{
						atualizarAvaliacao();
					}
					
				}
			});
		}
		
		
		
		
		if(pref.getString("tipo", null).equals("personal")){
			btnIniciarTreinamentoAlunos.setVisibility(View.GONE);
			swcExibirTreinamentoOuAvaliacao.setVisibility(View.GONE);
		}else if(pref.getString("tipo", null).equals("aluno")){
			btnIniciarTreinamentoAlunos.setVisibility(View.VISIBLE);
			swcExibirTreinamentoOuAvaliacao.setVisibility(View.VISIBLE);
		}
		
		btnIniciarTreinamentoAlunos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getActivity(),ConfirmarInicioTreinamento.class);
				startActivity(i);
				
			}
		});
		
		usuarioAluno = avaliacoes.getString("alunoSelecionado",null);
		
		
		atualizarAcompanhamento();
		  
		  
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
	    super.setUserVisibleHint(isVisibleToUser);
	    if(lstAcompanhamentoAlunoTreinamentosRealizados !=null){
		    onResume();
	    }
	}
	
	public void atualizarAvaliacao(){
		if(pref.getString("tipo", null).equals("personal")){
			String parametro = avaliacoes.getString("alunoSelecionado",null);
			
			Avaliacoes a = new Avaliacoes();
			a.setUsuarioAluno(parametro);
			
			ArrayList<Avaliacoes> busca = a.buscarAvaliacoesPorAluno(b, parametro);	
			
			atualizarInterfaceAvaliacao(busca);
		}else if(pref.getString("tipo", null).equals("aluno")){
			
			String parametro = pref.getString("usuario", null);
						
			ArrayList<Avaliacoes> busca = new Avaliacoes().buscarAvaliacoesPorAluno(b, parametro);	
			
			atualizarInterfaceAvaliacao(busca);
		}
		
	}

	public void atualizarInterfaceAvaliacao(final ArrayList<Avaliacoes> busca){
		ArrayList<RowItemAvaliacao> data = new ArrayList<RowItemAvaliacao>();
		
		Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.prancheta);
		
		for(Avaliacoes a :busca){
			
			RowItemAvaliacao item = new RowItemAvaliacao(
					bmp,
					a.getUsuarioAluno(),
					a.getDataAvaliacao(),
					a.getHoraAvaliacao(),
					a.getResultado()
					);
			
			data.add(item);
		}
		
		CustomAdapterAvaliacoes adapter = new CustomAdapterAvaliacoes(getActivity(), data);
		lstAcompanhamentoAlunoTreinamentosRealizados.setAdapter(adapter);
		
		lstAcompanhamentoAlunoTreinamentosRealizados.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i = new Intent(getActivity(),VisualizarAvaliacao.class);
				i.putExtra("codAvaliacao", busca.get(arg2).getCodAvaliacao());
				startActivity(i);
			}
		});
		
	}
	
	public void atualizarAcompanhamento(){
		if (pref.getString("tipo", null).equals("personal")){
			buscaTreinos = new TreinamentoRealizado()
			.buscarTreinoPorAluno(b, usuarioAluno);
			
			atualizarInterfaceAcompanhamento();
			
		}else if(pref.getString("tipo", null).equals("aluno")){
			buscaTreinos = new TreinamentoRealizado()
			.buscarTreinoPorAluno(b, pref.getString("usuario", null));
			atualizarInterfaceAcompanhamento();
		}
		
	}
		
	public void atualizarInterfaceAcompanhamento(){
		ArrayList<RowItemTreinamentoRealizado> lista = new ArrayList<RowItemTreinamentoRealizado>();
	
		for(TreinamentoRealizado t: buscaTreinos){
			RowItemTreinamentoRealizado a = new RowItemTreinamentoRealizado(t.getDataTreino(), t.getHoraInicio(), t.getHoraFim(), "Completo!");
			lista.add(a);
		}
		
		CustomAdapterTreinamentosRealizado adapter = new CustomAdapterTreinamentosRealizado(getActivity(), lista);
		lstAcompanhamentoAlunoTreinamentosRealizados.setAdapter(adapter);
		
		lstAcompanhamentoAlunoTreinamentosRealizados.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i = new Intent(getActivity(), VisualisarTreinamentoRealizados.class);
				i.putExtra("treino", buscaTreinos.get(arg2).getCodTreinamentoRealizado());
				startActivity(i);
				
			}
		});
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if(pref.getString("tipo", null).equals("personal")){
			atualizarAcompanhamento();
		}else if (pref.getString("tipo", null).equals("aluno")){
			if(swcExibirTreinamentoOuAvaliacao.isChecked()){
				atualizarAcompanhamento();
			}else{
				atualizarAvaliacao();
			}
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		/*if(id ==  android.R.id.home){
			finish();
			//NavUtils.navigateUpFromSameTask(getActivity());
	        return true;
		}*/
		return super.onOptionsItemSelected(item);
	}

}
