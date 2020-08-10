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

import android.graphics.Bitmap;



public class RowItemAluno {
	    private Bitmap imagemPerfil;
	    private String nomePerfil;
	    private String usuarioAluno;
	    
	    
	    public RowItemAluno(){
	    	
	    }
	    
		public RowItemAluno(Bitmap imagemPerfil, String nomePerfil, String usuarioAluno,
				String localDeTrabalhoPerfil) {
			super();
			this.imagemPerfil = imagemPerfil;
			this.nomePerfil = nomePerfil;
			this.usuarioAluno = usuarioAluno;
		
		}



		public Bitmap getImagemPerfil() {
			return imagemPerfil;
		}



		public void setImagemPerfil(Bitmap imagemPerfil) {
			this.imagemPerfil = imagemPerfil;
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