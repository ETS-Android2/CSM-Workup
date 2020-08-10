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
package br.com.Classes;

public class Exercicio {
	
	//Atributos da Classe
	private int codExercicio;
	private String nomeExercicio;
	private String descricaoExercicio;
	private String descansoExercicio;
	private String usuarioPersonal;
	
	
	//Construtores
	public Exercicio(){
		
	}
	
	public Exercicio (int codExercicio, String nomeExercicio, String descricaoExercicio,String descansoExercicio, String usuarioPersonal){
		this.codExercicio = codExercicio;
		this.nomeExercicio = nomeExercicio;
		this.descricaoExercicio = descricaoExercicio;
		this.usuarioPersonal = usuarioPersonal;
		this.descansoExercicio = descansoExercicio;
		
	}
	
	//toString
	
	@Override
	public String toString() {
		return "Exercicio [codExercicio=" + codExercicio + ", nomeExercicio="
				+ nomeExercicio + ", descricaoExercicio=" + descricaoExercicio
				+ ", descansoExercicio=" + descansoExercicio
				+ ", usuarioPersonal=" + usuarioPersonal + "]";
	}

	
	//Gets And Sets
	public int getCodExercicio() {
		return codExercicio;
	}

	public void setCodExercicio(int codExercicio) {
		this.codExercicio = codExercicio;
	}
	public String getNomeExercicio() {
		return nomeExercicio;
	}
	public void setNomeExercicio(String nomeExercicio) {
		this.nomeExercicio = nomeExercicio;
	}
	public String getDescricaoExercicio() {
		return descricaoExercicio;
	}
	public void setDescricaoExercicio(String descricaoExercicio) {
		this.descricaoExercicio = descricaoExercicio;
	}
	public String getUsuarioPersonal() {
		return usuarioPersonal;
	}
	public void setUsuarioPersonal(String usuarioPersonal) {
		this.usuarioPersonal = usuarioPersonal;
	}
	public String getDescansoExercicio(){
		return descansoExercicio;
	}
	public void setDescansoExercicio(String descansoExercicio){
		this.descansoExercicio = descansoExercicio;
	}
}
