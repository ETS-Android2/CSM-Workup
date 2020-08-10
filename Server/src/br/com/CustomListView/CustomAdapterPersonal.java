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


import java.util.List;

import br.com.WorkUp.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapterPersonal extends BaseAdapter {

	Context context;
	List<RowItemPersonal> rowItems;

	public CustomAdapterPersonal(Context context, List<RowItemPersonal> rowItems) {
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
		ImageView imagemPerfil;
		TextView nomePerfil;
		TextView usuarioPersonal;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_personal, null);
			holder = new ViewHolder();

			holder.imagemPerfil = (ImageView) convertView
					.findViewById(R.id.imgFotoPerfilPersonal);
			holder.nomePerfil = (TextView) convertView
					.findViewById(R.id.txtNomePerfilPersonal);
			holder.usuarioPersonal = (TextView) convertView
					.findViewById(R.id.txtUsuarioPerfilPersonal);
			

			RowItemPersonal row_pos = rowItems.get(position);

			holder.imagemPerfil.setImageBitmap(row_pos.getImagemPerfil());
			holder.nomePerfil.setText(row_pos.getNomePerfil());
			holder.usuarioPersonal.setText(row_pos.getUsuarioPersonal());
			

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}

}
