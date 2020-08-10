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



public class RowItemAvaliacao {
	    private Bitmap icone;
	    private String dataAvaliacao;
	    private String horaAvaliacao;
	   
	    
	    public RowItemAvaliacao(){
	    	
	    }


		public RowItemAvaliacao(Bitmap icone, String usuarioAvaliado,
				String dataAvaliacao, String horaAvaliacao,
				double resultadoAvaliacao) {
			super();
			this.icone = icone;
			this.dataAvaliacao = dataAvaliacao;
			this.horaAvaliacao = horaAvaliacao;
			
		}


		public Bitmap getIcone() {
			return icone;
		}


		public void setIcone(Bitmap icone) {
			this.icone = icone;
		}


		public String getDataAvaliacao() {
			return dataAvaliacao;
		}


		public void setDataAvaliacao(String dataAvaliacao) {
			this.dataAvaliacao = dataAvaliacao;
		}


		public String getHoraAvaliacao() {
			return horaAvaliacao;
		}


		public void setHoraAvaliacao(String horaAvaliacao) {
			this.horaAvaliacao = horaAvaliacao;
		}


		
		
}