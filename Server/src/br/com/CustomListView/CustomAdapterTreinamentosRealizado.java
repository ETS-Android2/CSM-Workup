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
package CustomListView;


import java.util.ArrayList;
import java.util.List;

//import com.google.android.gms.drive.internal.OnListParentsResponse;

import br.com.WorkUp.R;
import br.com.WorkUp.R.color;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CustomAdapterTreinamentosRealizado extends BaseAdapter {
	
	//private ArrayList<Integer> coloredItems = new ArrayList<Integer>();
	Context context;
	List<RowItemTreinamentoRealizado> rowItems;

	public CustomAdapterTreinamentosRealizado(Context context, List<RowItemTreinamentoRealizado> rowItems) {
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
		TextView dataTreinamento;
		TextView horaInicioTreinamento;
		TextView horaFimTreinamento;
		TextView statusTreinamento;
		
	}
	
	
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			
		 
		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_treinamentos_realizados, null);
			holder = new ViewHolder();

			
			holder.dataTreinamento = (TextView) convertView
					.findViewById(R.id.listView_dataTreinamento);
			holder.horaInicioTreinamento = (TextView) convertView
					.findViewById(R.id.listView_horaInicioTreinamento);
			holder.horaFimTreinamento = (TextView) convertView
					.findViewById(R.id.listView_horaFimTreinamento);
			holder.statusTreinamento = (TextView) convertView
					.findViewById(R.id.listView_statusTreinamento);
			

			RowItemTreinamentoRealizado row_pos = rowItems.get(position);

			
			holder.dataTreinamento.setText(row_pos.getDataTreinamento());
			holder.horaInicioTreinamento.setText(row_pos.getHoraInicioTreinamento());
			holder.horaFimTreinamento.setText(row_pos.getHoraFimTreinamento());
			holder.statusTreinamento.setText(row_pos.getStatusTreinamento());
			

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}

}
