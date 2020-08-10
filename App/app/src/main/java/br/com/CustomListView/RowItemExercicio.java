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


public class RowItemExercicio {
	   
	    private String nomeExercicio;
	    private String duracaoExercicio;
	    private String descansoExercicio;
	    
	    
	    
	    public RowItemExercicio(){
	    	
	    }
	    

		public RowItemExercicio(String nomeExercicio, String duracaoExercicio,
				String descansoExercicio, String descricaoExercicio) {
			super();
			this.nomeExercicio = nomeExercicio;
			this.duracaoExercicio = duracaoExercicio;
			this.descansoExercicio = descansoExercicio;
		}



		public String getNomeExercicio() {
			return nomeExercicio;
		}



		public void setNomeExercicio(String nomeExercicio) {
			this.nomeExercicio = nomeExercicio;
		}



		public String getDuracaoExercicio() {
			return duracaoExercicio;
		}



		public void setDuracaoExercicio(String duracaoExercicio) {
			this.duracaoExercicio = duracaoExercicio;
		}



		public String getDescansoExercicio() {
			return descansoExercicio;
		}



		public void setDescansoExercicio(String descansoExercicio) {
			this.descansoExercicio = descansoExercicio;
		}


}