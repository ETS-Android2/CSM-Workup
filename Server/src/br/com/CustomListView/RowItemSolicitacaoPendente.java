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
package CustomListView;

import android.graphics.Bitmap;



public class RowItemSolicitacaoPendente {
	    private Bitmap icone;
	    private String nomePerfil;
	    private String usuarioAluno;
	    
	    
	    public RowItemSolicitacaoPendente(){
	    	
	    }


		public RowItemSolicitacaoPendente(Bitmap icone,
				String usuarioSolicitante, String nomePerfil,
				String usuarioAluno, String telefoneAluno) {
			super();
			this.icone = icone;
			this.nomePerfil = nomePerfil;
			this.usuarioAluno = usuarioAluno;
		}





		public Bitmap getIcone() {
			return icone;
		}


		public void setIcone(Bitmap icone) {
			this.icone = icone;
		}


		public String getNomePerfil() {
			return nomePerfil;
		}


		public void setNomePerfil(String nomePerfil) {
			this.nomePerfil = nomePerfil;
		}


		public String getUsuarioAluno() {
			return usuarioAluno;
		}


		public void setUsuarioAluno(String usuarioAluno) {
			this.usuarioAluno = usuarioAluno;
		}
		
}