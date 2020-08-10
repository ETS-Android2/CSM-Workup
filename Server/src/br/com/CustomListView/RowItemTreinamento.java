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



public class RowItemTreinamento {
	    private String nomeTreinamento;
	    private String numeroDeExercicios;
		
	    public RowItemTreinamento(String nomeTreinamento,
				String numeroDeExercicios) {
			super();
			this.nomeTreinamento = nomeTreinamento;
			this.numeroDeExercicios = numeroDeExercicios;
		}

		public String getNomeTreinamento() {
			return nomeTreinamento;
		}

		public void setNomeTreinamento(String nomeTreinamento) {
			this.nomeTreinamento = nomeTreinamento;
		}

		public String getNumeroDeExercicios() {
			return numeroDeExercicios;
		}

		public void setNumeroDeExercicios(String numeroDeExercicios) {
			this.numeroDeExercicios = numeroDeExercicios;
		} 
		
}