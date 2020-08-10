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



public class RowItemAgendamentos {
		private Bitmap aulaAgendada;
	    private String nomeAluno;
	    private String dataAula;
	    private String horaAula;
	    private String confirmacaoAula;
	    
	    
	    
	    
		public RowItemAgendamentos(Bitmap aulaAgendada, String nomeAluno,
				String dataAula, String horaAula, String confirmacaoAula) {
			super();
			this.aulaAgendada = aulaAgendada;
			this.nomeAluno = nomeAluno;
			this.dataAula = dataAula;
			this.horaAula = horaAula;
			this.confirmacaoAula = confirmacaoAula;
		}
		public Bitmap getAulaAgendada() {
			return aulaAgendada;
		}
		public void setAulaAgendada(Bitmap aulaAgendada) {
			this.aulaAgendada = aulaAgendada;
		}
		public String getNomeAluno() {
			return nomeAluno;
		}
		public void setNomeAluno(String nomeAluno) {
			this.nomeAluno = nomeAluno;
		}
		public String getDataAula() {
			return dataAula;
		}
		public void setDataAula(String dataAula) {
			this.dataAula = dataAula;
		}
		public String getHoraAula() {
			return horaAula;
		}
		public void setHoraAula(String horaAula) {
			this.horaAula = horaAula;
		}
		public String getConfirmacaoAula() {
			return confirmacaoAula;
		}
		public void setConfirmacaoAula(String confirmacaoAula) {
			this.confirmacaoAula = confirmacaoAula;
		}
	    
	    
	    
		
	    
	    
	    
	    
		
	    
	    
		
}