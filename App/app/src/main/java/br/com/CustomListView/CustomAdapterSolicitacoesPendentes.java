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
package br.com.CustomListView;


import java.util.ArrayList;

import br.com.WorkUp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapterSolicitacoesPendentes extends BaseAdapter {

	Context context;
	ArrayList<RowItemSolicitacaoPendente> rowItemSolicitacoesPendentes;

	public CustomAdapterSolicitacoesPendentes(Context context, ArrayList<RowItemSolicitacaoPendente> rowItemSolicitacaoPendente) {
		this.context = context;
		this.rowItemSolicitacoesPendentes = rowItemSolicitacaoPendente;
	}

	@Override
	public int getCount() {
		return rowItemSolicitacoesPendentes.size();
	}

	@Override
	public Object getItem(int position) {
		return rowItemSolicitacoesPendentes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return rowItemSolicitacoesPendentes.indexOf(getItem(position));
	}

	/* private view holder class */
	private class ViewHolder {
		ImageView imagemPerfil;
		TextView nomePerfil;
		TextView usuarioAlunoPerfil;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_solicitacoes_pendentes, null);
			holder = new ViewHolder();

			holder.imagemPerfil = (ImageView) convertView
					.findViewById(R.id.imgFotoSolicitacaoPendente);
			holder.nomePerfil = (TextView) convertView
					.findViewById(R.id.txtNomeSolicitacaoPendente);
			holder.usuarioAlunoPerfil = (TextView) convertView
					.findViewById(R.id.txtUsuarioSolicitacaoPendente);
			

			RowItemSolicitacaoPendente row_pos = rowItemSolicitacoesPendentes.get(position);

			holder.imagemPerfil.setImageBitmap(row_pos.getIcone());
			holder.nomePerfil.setText(row_pos.getNomePerfil());
			holder.usuarioAlunoPerfil.setText(row_pos.getUsuarioAluno());
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}

}
