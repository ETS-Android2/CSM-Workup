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
import java.util.HashMap;
import java.util.List;

import br.com.WorkUp.R;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapterExpandedAluno extends BaseExpandableListAdapter {
	
	private Context _context;
	private List<RowItemAluno> _listDataHeader;
	private HashMap<RowItemAluno, List<RowItemExpandedAluno>>_listDataChild;

	public CustomAdapterExpandedAluno(Context context, List<RowItemAluno> listaDataHeader, HashMap<RowItemAluno, List<RowItemExpandedAluno>> listChildData) {
		
		this._context = context;
		this._listDataHeader = listaDataHeader;
		this._listDataChild = listChildData;
		
	}
	
	
	@Override
	public int getGroupCount() {
		
		int count = this._listDataHeader.size();
		
		return count;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		
		int count = this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
		
		return count;
	}

	@Override
	public Object getGroup(int groupPosition) {
		
		return this._listDataHeader.get(groupPosition);
		
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {

		return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosition);
				
	}

	@Override
	public long getGroupId(int groupPosition) {

		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {

		return childPosition;

	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		RowItemAluno header = (RowItemAluno) getGroup(groupPosition);
		
		if(convertView == null) {
			
			LayoutInflater inflate = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflate.inflate(R.layout.list_item_aluno, null);
			
		}
		
		ImageView imgFotoPerfilAluno = (ImageView) convertView.findViewById(R.id.imgFotoPerfilAluno);
		TextView txtNomePerfilAluno = (TextView) convertView.findViewById(R.id.txtNomePerfilAluno);
		TextView txtUsuarioPerfilAluno = (TextView) convertView.findViewById(R.id.txtUsuarioPerfilAluno);
		
		imgFotoPerfilAluno.setImageBitmap(header.getImagemPerfil());
		txtNomePerfilAluno.setText(header.getNomePerfil());
		txtUsuarioPerfilAluno.setText(header.getUsuarioAluno());
		
		//lblListHeader.setTypeface(null, Typeface.BOLD);
		//lblListHeader.setText(headerTitle);
		
		return convertView;
		
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final RowItemExpandedAluno childItem = (RowItemExpandedAluno) getChild(groupPosition, childPosition);
		
		if(convertView == null) {
			LayoutInflater inflate = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflate.inflate(R.layout.list_item_expanded_aluno, null);
		}
		
		TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
		ImageView img = (ImageView) convertView.findViewById(R.id.imgItem);
		txtListChild.setText(childItem.getNome());
		img.setImageResource(childItem.getImageId());
		
		return convertView;
		
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}