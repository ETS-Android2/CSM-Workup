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



public class RowItemTreinamentoRealizado {
	    private String dataTreinamento;
	    private String horaInicioTreinamento;
	    private String horaFimTreinamento;
	    private String statusTreinamento;
	    
	    
	    
		public RowItemTreinamentoRealizado( String dataTreinamento, String horaInicioTreinamento,
				String horaFimTreinamento, String statusTreinamento) {
			super();
			this.dataTreinamento = dataTreinamento;
			this.horaInicioTreinamento = horaInicioTreinamento;
			this.horaFimTreinamento = horaFimTreinamento;
			this.statusTreinamento = statusTreinamento;
		}


		public String getDataTreinamento() {
			return dataTreinamento;
		}



		public void setDataTreinamento(String dataTreinamento) {
			this.dataTreinamento = dataTreinamento;
		}



		public String getHoraInicioTreinamento() {
			return horaInicioTreinamento;
		}



		public void setHoraInicioTreinamento(String horaInicioTreinamento) {
			this.horaInicioTreinamento = horaInicioTreinamento;
		}



		public String getHoraFimTreinamento() {
			return horaFimTreinamento;
		}



		public void setHoraFimTreinamento(String horaFimTreinamento) {
			this.horaFimTreinamento = horaFimTreinamento;
		}



		public String getStatusTreinamento() {
			return statusTreinamento;
		}



		public void setStatusTreinamento(String statusTreinamento) {
			this.statusTreinamento = statusTreinamento;
		}
		
}