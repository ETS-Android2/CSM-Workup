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
package br.com.GUI.avaliacoes;

import java.util.ArrayList;

import br.com.Banco.Banco;
import br.com.Classes.Avaliacoes;
import br.com.GUI.treinamentos.ConfirmarInicioTreinamento;
import br.com.WorkUp.R;
import br.com.WorkUp.R.id;
import br.com.WorkUp.R.layout;
import br.com.WorkUp.R.menu;
import CustomListView.CustomAdapterAvaliacoes;
import CustomListView.RowItemAvaliacao;
import android.app.Activity;
import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.os.Build;

public class AcompanhamentoAvaliacao extends Fragment {
	
	//atributos de interface
	private ListView lstListaAvaliacoesRealizadas;
	
	//atributos auxiliares
	private SharedPreferences avaliacoes;
	private Editor editor;
	
	private Banco b;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.activity_acompanhamento_avaliacao, container,
				false);
		return rootView;
	}
	
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		  super.onActivityCreated(savedInstanceState);
		  getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		
		mapearComponentes();
		
		encontrarAvaliacoes();
		
		  
	}
	
	public void mapearComponentes(){
		lstListaAvaliacoesRealizadas = (ListView) getActivity().findViewById(R.id.lstListaAvaliacoesRealizadas);
		avaliacoes = getActivity().getApplicationContext().getSharedPreferences("acompanhamento", 0);
		editor = avaliacoes.edit();
		
		b = new Banco (getActivity(),null,null,0);
	
	}
	
	
	public void encontrarAvaliacoes(){
		String parametro = avaliacoes.getString("alunoSelecionado",null);
		
		Avaliacoes a = new Avaliacoes();
		a.setUsuarioAluno(parametro);
		
		ArrayList<Avaliacoes> busca = a.buscarAvaliacoesPorAluno(b, parametro);	
		
		preencherInterface(busca);
	}

	public void preencherInterface(final ArrayList<Avaliacoes> busca){
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
		lstListaAvaliacoesRealizadas.setAdapter(adapter);
		
		lstListaAvaliacoesRealizadas.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i = new Intent(getActivity(),VisualizarAvaliacao.class);
				i.putExtra("codAvaliacao", busca.get(arg2).getCodAvaliacao());
				startActivity(i);
			}
		});
		
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
		return super.onOptionsItemSelected(item);
	}



}
