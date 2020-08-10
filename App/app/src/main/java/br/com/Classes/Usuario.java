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

public class Usuario {
	
	
	//Atributos da classe
	private String telefone;
	private String nome;
	private String dataDeNascimento;
	private String email;
	private String sexo;
	private String usuario;
	private String senha;
	private String foto;
	
	
	//Construtores
	public Usuario(String telefone, String nome, String dataDeNascimento,
			String email, String sexo, String usuario, String senha, String foto) {
		super();
		this.telefone = telefone;
		this.nome = nome;
		this.dataDeNascimento = dataDeNascimento;
		this.email = email;
		this.sexo = sexo;
		this.usuario = usuario;
		this.senha = senha;
		this.foto = foto;
	}
	
	public Usuario(){
		
	}
	
	//toString
	@Override
	public String toString() {
		return "Usuario [telefone=" + telefone + ", nome=" + nome
				+ ", dataDeNascimento=" + dataDeNascimento + ", email=" + email
				+ ", sexo=" + sexo + ", usuario=" + usuario + ", senha="
				+ senha +  "]";
	}
	
	
	
	//Gets e Sets
	public String getTelefone() {
		return telefone;
	}
	

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDataDeNascimento() {
		return dataDeNascimento;
	}
	public void setDataDeNascimento(String dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	
}
