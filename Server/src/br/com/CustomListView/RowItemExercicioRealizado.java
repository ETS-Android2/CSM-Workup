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



public class RowItemExercicioRealizado {
	
	
		private Bitmap imagem;
	    private String nomeExercicio;
	    private String horaInicio;
	    private String horaFim;
	    private String tempoDeExecucao;
	    
	    
	    
	    
	    
		public RowItemExercicioRealizado(Bitmap imagem, String nomeExercicio,
				String horaInicio, String horaFim, String tempoDeExecucao) {
			super();
			this.imagem = imagem;
			this.nomeExercicio = nomeExercicio;
			this.horaInicio = horaInicio;
			this.horaFim = horaFim;
			this.tempoDeExecucao = tempoDeExecucao;
		}
		
		
		public Bitmap getImagem() {
			return imagem;
		}
		public void setImagem(Bitmap imagem) {
			this.imagem = imagem;
		}
		public String getNomeExercicio() {
			return nomeExercicio;
		}
		public void setNomeExercicio(String nomeExercicio) {
			this.nomeExercicio = nomeExercicio;
		}
		public String getHoraInicio() {
			return horaInicio;
		}
		public void setHoraInicio(String horaInicio) {
			this.horaInicio = horaInicio;
		}
		public String getHoraFim() {
			return horaFim;
		}
		public void setHoraFim(String horaFim) {
			this.horaFim = horaFim;
		}
		public String getTempoDeExecucao() {
			return tempoDeExecucao;
		}
		public void setTempoDeExecucao(String tempoDeExecucao) {
			this.tempoDeExecucao = tempoDeExecucao;
		}
	    
	    
	
	    
	  
		
}