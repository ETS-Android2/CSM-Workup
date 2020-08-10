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
package br.com.GUI.perfil;

import java.util.ArrayList;

import br.com.Banco.Banco;
import br.com.Classes.Aluno;
import br.com.Classes.Aula;
import br.com.Classes.Personal;
import br.com.Utilitarios.ImageUtils;
import br.com.WorkUp.R;
import br.com.WorkUp.R.id;
import br.com.WorkUp.R.layout;
import br.com.WorkUp.R.menu;
import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MeuPersonal extends Fragment {
	
	//Atributos de interface
	private ImageView foto;	
	private TextView usuario;
	private TextView nome;
	private TextView dataDeNascimento;
	private TextView sexo;
	private TextView email;
	private Button adicionarRemover;
	

	//Persistencias locais
	private Banco b;
	private SharedPreferences pref;

	//Atributos auxiliares
	private Personal p = new Personal();
	private Aluno a = new Aluno();
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.activity_meu_personal, container, false);
	        
	        return rootView;
	 }	
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);

		mapearComponentes();
		
		adicionarRemover.setOnClickListener(new OnClickListener() {
				
		@Override
		public void onClick(View arg0) {
			adicionarRemoverMeuPersonal();
			}
		});

		atualizarMeuPersonal();
		  
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
	    super.setUserVisibleHint(isVisibleToUser);
	    if(adicionarRemover != null){
	    	   onResume();
	    }
	 
	}
	
	public void mapearComponentes(){
		pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
		b = new Banco(getActivity(),null,null,0);
		foto = (ImageView)getActivity().findViewById(R.id.imgFotoMeuPersonal);
		usuario = (TextView)getActivity().findViewById(R.id.infoUsuarioMeuPersonal);
		nome = (TextView)getActivity().findViewById(R.id.infoNomeMeuPersonal);
		dataDeNascimento = (TextView) getActivity().findViewById(R.id.infoDataDeNascimentoMeuPersonal);
		sexo = (TextView) getActivity().findViewById(R.id.infoSexoMeuPersonal);
		email = (TextView) getActivity().findViewById(R.id.infoEmailMeuPersonal);
		adicionarRemover = (Button) getActivity().findViewById(R.id.btnAdicionarRemover);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "BebasNeue Bold.ttf");
		adicionarRemover.setTypeface(font);
		
		adicionarRemover.setVisibility(View.GONE);
	}
	
	public void atualizarMeuPersonal(){
	
		a = new Aluno().buscarAlunoEspecifico(b, pref.getString("usuario", null));
		
	//	Log.i("entrei para atualizar o personal dentro do meu personal ", "e o personal que está no banco é " + a.toString());
		if(a.getUsuarioPersonal()== null || 
				a.getUsuarioPersonal().equals("")|| 
				a.getUsuarioPersonal().equals("null")){
			
			Log.i("entrei pra limpar", "limapndo");
			usuario.setText("");
			nome.setText("");
			dataDeNascimento.setText("");
			sexo.setText("");
			email.setText("");
			adicionarRemover.setVisibility(View.GONE);
		
			
		}else{
			if(a.getConfirmacaoAluno() == 1 && a.getConfirmacaoPersonal() == 1){
				
				//	Log.i("entrei nos if", "o que tem dentro do usuarioPErsonal = " + a.getUsuarioPersonal() );
					p = new Personal().buscarPersonal(b, a.getUsuarioPersonal());
					
				//	Log.i("usuario a ", a.toString());
					if (p == null){
						p = new Personal().buscarPersonalWeb(a.getUsuarioPersonal());
						byte[] fotoPersonal = ImageUtils.bitmapToByteArray(p.buscarFotoPersonalWeb(a.getUsuarioPersonal()));
						p.salvar(b, fotoPersonal);
					}
					
					
					if(p != null){
						
						byte[] fotoPersonal = p.buscarFotoPersonal(b, p.getUsuario());
						Drawable d = ImageUtils.byteToDrawable(fotoPersonal);
						
						if (a.getUsuarioPersonal()== null){
							adicionarRemover.setBackground(getResources().getDrawable(R.drawable.shape_botao_azul));
							adicionarRemover.setText("Adicionar");
						}else if(a.getUsuarioPersonal().equals(p.getUsuario())) { 
							adicionarRemover.setBackground(getResources().getDrawable(R.drawable.shape_botao_vermelho));
							adicionarRemover.setText("Remover");	
							adicionarRemover.setVisibility(View.VISIBLE);
						}
						
						if(d != null){
							foto.setImageDrawable(d);
						}else{
							foto.setImageDrawable(getResources().getDrawable(R.drawable.profile));
						}
						
						usuario.setText(p.getUsuario());
						if(usuario.getText().equals("anyType{}")){
							usuario.setText("");
						}
						nome.setText(p.getNome());
						if(nome.getText().equals("anyType{}")){
							nome.setText("");
						}
						Log.i("data de nascimento", p.getDataDeNascimento());
						dataDeNascimento.setText(p.getDataDeNascimento());
						if(dataDeNascimento.getText().equals("anyType{}")){
							dataDeNascimento.setText("");
						}
						sexo.setText(p.getSexo());
						if(sexo.getText().equals("anyType{}")){
							sexo.setText("");
						}
						email.setText(p.getEmail());
						if(email.getText().equals("anyType{}")){
							email.setText("");
						}
						
						
						}
						
					}
			}
	}
	
	public void adicionarRemoverMeuPersonal(){
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
		alertDialog.setTitle("Confirme esta ação");
		alertDialog.setMessage("Você realmente deseja remover seu treinador?");
		alertDialog.setIcon(R.drawable.profile);
		alertDialog.setNegativeButton("Sim", new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog,int which) {
	        	 if (a.getUsuarioPersonal().equals(p.getUsuario())){
	     			
	     			if(a.removerAlunoWeb(a.getUsuario()) && a.removerAluno(b)){
	     				Toast.makeText(getActivity(), "Removido com Sucesso", Toast.LENGTH_SHORT).show();
	     				
	     				// Exclui todas as aulas marcadas com o personal
	     				ArrayList<Aula> aulasParaExcluir = new ArrayList<Aula>();
	     				
	     				aulasParaExcluir = new Aula().buscarAulasPorAlunoWeb(a.getUsuario(), "");
	     				for(Aula c : aulasParaExcluir){
	     					if(c.excluirAulaWeb() && c.excluirAula(b)){
	     						Log.i("INFORMAÇÃO", "Aulas excluidas com sucesso!");
	     					}
	     				}
	     				
	     				atualizarMeuPersonal();
	     			}else {
	     				Toast.makeText(getActivity(), "Falha ao Remover o aluno", Toast.LENGTH_SHORT).show();
	     			}
	     		}
	     		atualizarMeuPersonal();
	         }
		});
		alertDialog.setPositiveButton("Não", new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog,int which) {
	        	 dialog.cancel();
	         }
		});
		// Showing Alert Message
	        alertDialog.show();
		
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		atualizarMeuPersonal();
	}
	
	
	//MENUS
	@Override
	public void onCreateOptionsMenu(
	    Menu menu, MenuInflater inflater) {
	   inflater.inflate(R.menu.meu_personal_actions, menu);
	   
	   
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.actAdicionarPersonal) {
			Toast.makeText(getActivity(), "adicionando personal", Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
