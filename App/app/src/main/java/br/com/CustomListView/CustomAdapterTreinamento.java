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


import java.util.List;

//import com.google.android.gms.drive.internal.OnListParentsResponse;

import br.com.WorkUp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapterTreinamento extends BaseAdapter {
	
	//private ArrayList<Integer> coloredItems = new ArrayList<Integer>();
	Context context;
	List<RowItemTreinamento> rowItems;

	public CustomAdapterTreinamento(Context context, List<RowItemTreinamento> rowItems) {
		this.context = context;
		this.rowItems = rowItems;
	}

	@Override
	public int getCount() {
		return rowItems.size();
	}

	@Override
	public Object getItem(int position) {
		return rowItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return rowItems.indexOf(getItem(position));
	}

	/* private view holder class */
	private class ViewHolder {
		TextView nomeTreinamento;
		TextView numeroDeExercicios;
		
		
	}
	
	
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			
		 
		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_treinamento, null);
			holder = new ViewHolder();

			
			holder.nomeTreinamento = (TextView) convertView
					.findViewById(R.id.txtNomeTreinamento);
			holder.numeroDeExercicios = (TextView) convertView
					.findViewById(R.id.txtNumeroDeExercicios);
			
			

			RowItemTreinamento row_pos = rowItems.get(position);

			
			
			holder.nomeTreinamento.setText(row_pos.getNomeTreinamento());
			holder.numeroDeExercicios.setText(row_pos.getNumeroDeExercicios());

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}

}
