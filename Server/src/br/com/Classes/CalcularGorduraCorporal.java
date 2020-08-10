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

import java.util.ArrayList;

public class CalcularGorduraCorporal {

   //atributos
    private String indicadoPara;
    private String descricao;
    private String sexo;
    private double peso;
    private double altura;
    private int idade;
    private double dobraAbdominal;
    private double dobraCoxa;
    private double dobraPeito;
    private double dobraSuprailiaca;
    private double dobraSubscapular;
    private double dobraTriceps;
    private double dobraLinhaAxilarMedia;
    private double dobraPanturrilha;
    private double resultado;
    
    
    
    //construtores
    public CalcularGorduraCorporal(){
    	
    }
    
    
    public CalcularGorduraCorporal(String sexo, double peso, double altura,
			int idade, double dobraAbdominal, double dobraCoxa,
			double dobraPeito, double dobraSuprailiaca,
			double dobraSubscapular, double dobraTriceps,
			double dobraLinhaAxilarMedia, double dobraPanturrilha,double resultado) {
		super();
		this.sexo = sexo;
		this.peso = peso;
		this.altura = altura;
		this.idade = idade;
		this.dobraAbdominal = dobraAbdominal;
		this.dobraCoxa = dobraCoxa;
		this.dobraPeito = dobraPeito;
		this.dobraSuprailiaca = dobraSuprailiaca;
		this.dobraSubscapular = dobraSubscapular;
		this.dobraTriceps = dobraTriceps;
		this.dobraLinhaAxilarMedia = dobraLinhaAxilarMedia;
		this.dobraPanturrilha = dobraPanturrilha;
		this.resultado = resultado;
	}





    //Array nome dos metodos
    
    public static ArrayList<String> metodosDeCalculo(){
    	ArrayList<String> n = new ArrayList<String>();
    	n.add("Durnin");
    	n.add("Jackson 4");
    	n.add("Jackson 6");
    	n.add("Jackson 7 Atletas");
    	n.add("Jackson 4 Atletas");
    	n.add("Slaughter");
    	n.add("Guedes");
    	
    	return n;
    
    }
    
    
    //Determinar Peso Gordo
    
    public double calcularPesoGordo(double gorduraCorporal){
        double PG = gorduraCorporal * peso /100;
        return PG;
    }
    
    //determinar Peso corporal Magro

    public double calcularPesoMagro(double pesoGordo){
        double PCM  = peso - pesoGordo;
        return PCM;
    }
    
    
    //Classificacao
    public String getClassificacao(double porcentagemGordura, String sexo){
        if(sexo.equals("Masculino")){
            if(porcentagemGordura >= 5 && porcentagemGordura <= 6){
                return "Risco A";
            }else if(porcentagemGordura > 6 && porcentagemGordura <= 14){
                return "Abaixo da media";
            }else if(porcentagemGordura > 14 && porcentagemGordura <= 16){
                return "Media";
            }else if(porcentagemGordura > 16 && porcentagemGordura <=24){
                return "Acima da Media";
            }else if(porcentagemGordura > 24){
                return "Risco B";
            }
        }else if(sexo.equals("Feminino")){
            if(porcentagemGordura >= 8 && porcentagemGordura <=9){
                return "Risco A";
            }else if(porcentagemGordura > 9 && porcentagemGordura <=22){
                return "Abaixo da media";
            }else if (porcentagemGordura > 22 && porcentagemGordura <= 23){
                return "Media";
            }else if(porcentagemGordura > 23 && porcentagemGordura <= 31){
                return "Acima da Media";
            }else if(porcentagemGordura > 31){
                return "Risco B";
            }
        }
        
        return "sexo invalido";
    }
    
    
    //Durnin
    public double durnin() {
    	
    	if(sexo.equals("Masculino")){
    		 double r = 0;

    	        if (idade < 17) {
    	            r = (1.1533 - (0.0643 * (Math.log10(dobraAbdominal + dobraCoxa + dobraTriceps + dobraSuprailiaca))));
    	        } else if (idade >= 17 && idade <= 19) {
    	            r = (1.1620 - (0.0630 * Math.log10((dobraAbdominal + dobraCoxa + dobraTriceps + dobraSuprailiaca))));
    	        } else if (idade >= 20 && idade <= 29) {
    	            r = (1.1631 - (0.0632 * Math.log10((dobraAbdominal + dobraCoxa + dobraTriceps + dobraSuprailiaca))));
    	        } else if (idade >= 30 && idade <= 39) {
    	            r = (1.1422 - (0.0544 * Math.log10((dobraAbdominal + dobraCoxa + dobraTriceps + dobraSuprailiaca))));
    	        } else if (idade >= 40 && idade >= 49) {
    	            r = (1.1620 - (0.0700 * Math.log10((dobraAbdominal + dobraCoxa + dobraTriceps + dobraSuprailiaca))));
    	        } else if (idade > 50) {
    	            r = (1.1715 - (0.0779 * Math.log10((dobraAbdominal + dobraCoxa + dobraTriceps + dobraSuprailiaca))));
    	        }
    	        resultado = ((495 / r) - 450);
    	        return resultado;
    	    } else if(sexo.equals("Feminino")){
    		double r = 0;

            if (idade < 17) {
                r = (1.1369 - (0.0598 * (Math.log10(dobraAbdominal + dobraCoxa + dobraTriceps + dobraSuprailiaca))));
            } else if (idade >= 17 && idade <= 19) {
                r = (1.1549 - (0.0678 * (Math.log10(dobraAbdominal + dobraCoxa + dobraTriceps + dobraSuprailiaca))));
            } else if (idade >= 20 && idade <= 29) {
                r = (1.1599 - (0.0717 * (Math.log10(dobraAbdominal + dobraCoxa + dobraTriceps + dobraSuprailiaca))));
            } else if (idade >= 30 && idade <= 39) {
                r = (1.1423 - (0.0632 * (Math.log10(dobraAbdominal + dobraCoxa + dobraTriceps + dobraSuprailiaca))));
            } else if (idade >= 40 && idade >= 49) {
                r = (1.1333 - (0.0612 * (Math.log10(dobraAbdominal + dobraCoxa + dobraTriceps + dobraSuprailiaca))));
            } else if (idade > 50) {
                r = (1.1339 - (0.0645 * (Math.log10(dobraAbdominal + dobraCoxa + dobraTriceps + dobraSuprailiaca))));
            }
            resultado = ((495 / r) - 450);
            return resultado;
    		
    	}
    	return -1;
}

   
    
    //Jackson 4
    public double jackson4(){
            if (sexo.equals("Masculino")){
    		double ST = dobraAbdominal + dobraSuprailiaca + dobraTriceps+ dobraCoxa;
                double gordura = 0.29288 * ST - 0.0005 * (ST*ST) + 0.15845 * idade - 5.76377;
    	         
                
    	        return gordura;
    	}else if(sexo.equals("Feminino")){
    		double ST = dobraCoxa + dobraSuprailiaca + dobraSubscapular;
                
    	        double gordura = 0.29669 * ST - 0.00043 * (ST*ST) + 0.02963 * idade + 1.4072;
    	         
    	        return gordura;
    	}else{
            return 0;
        }
    }
    
    public static String getDescricaoJackson4(){
        String s = "Pessoas 18 a 50 anos de idade. Foi proposto por Jackson em 1980.";
        return s;
    }
    
    public static String getIndicacaoJackson4(){
        String s = "Metodo utilizado para mulheres e homens, brancos";
        return s;
    }
    
    
    
    
    //Jackson 6
    public double jackson6(){
            if (sexo.equals("Masculino")){
    		double ST = dobraPeito + dobraTriceps + dobraSuprailiaca+ dobraSubscapular + dobraAbdominal + dobraCoxa;
                double gordura = 3.64 + ST * 0.097;
    	         
                
    	        return gordura;
    	}else if(sexo.equals("Feminino")){
    		double ST = dobraCoxa + dobraSuprailiaca + dobraSubscapular;
                
    	        double gordura = 4.56 + ST * 0.143;
    	         
    	        return gordura;
    	}else{
            return 0;
        }
    }
    
    public static String getDescricaoJackson6(){
        String s = "Foi proposto por Jackson em 1980.";
        return s;
    }
    
    public static String getIndicacaoJackson6(){
        String s = "Metodo utilizado para mulheres e homens, brancos, de 18 a 30 anos de idade.";
        return s;
    }
    
    
    //Jackson e pollock 7 atletas
    public double jacksonPollock7atletas(){
            if (sexo.equals("Masculino")){
    		double ST = dobraPeito + dobraLinhaAxilarMedia + dobraTriceps + 
                        dobraSubscapular + dobraSuprailiaca + dobraAbdominal + dobraCoxa;
                double dc = 1.112 - 0.00043499 * ST + 0.00000055 * (ST*ST) - 0.00028826 * idade;
                
                if(idade >= 7 && idade <= 8 ){
                    resultado = (5.38/dc) - 4.97 * 100;
                }else if( idade >= 9 && idade <= 10){
                    resultado = (5.30/dc) - 4.89 * 100;
                }else if(idade >=11 && idade <= 12){
                    resultado = (5.23/dc) - 4.81 * 100;
                }else if(idade >= 13 && idade <= 14){
                    resultado = (5.07/dc) - 4.64 * 100;
                }else if(idade >= 15 && idade <=16){
                    resultado = (5.03/dc) - 4.59*100;
                }else if(idade >= 17 && idade <= 19){
                    resultado = (4.98/dc) - 4.53 * 100;
                }else if ( idade >=20 && idade <= 50){
                    resultado = (4.95/dc) - 4.50 * 100;
                }
                
    	        return resultado;
    	}else{
            return 0;
        }
    }
    
    public static String getDescricaoJacksonPollock7Atletas(){
        String s = "Foi proposto por Jackson e Pollock em 1978.";
        return s;
    }
    
    public static String getIndicacaoJacksonPollock7Atletas(){
        String s = "Metodo utilizado para homens, atletas,de todos os esportes, de 18 a 29 anos de idade.";
        return s;
    }
    
    
     //Jackson 4 atletas
    public double jackson4Atletas(){
            if (sexo.equals("Feminino")){
    		double ST = dobraTriceps + dobraSuprailiaca + dobraAbdominal + 
                        dobraCoxa;
                double dc = 1.096095 - 0.0006952 * ST + 0.0000011 + (ST*ST) * 0.0000714 * idade;
                
                
    	        if(idade >= 7 && idade <= 8 ){
                    resultado = (5.43/dc) - 5.03 * 100;
                }else if( idade >= 9 && idade <= 10){
                    resultado = (5.35/dc) - 4.95 * 100;
                }else if(idade >=11 && idade <= 12){
                    resultado = (5.25/dc) - 4.84 * 100;
                }else if(idade >= 13 && idade <= 14){
                    resultado = (5.12/dc) - 4.69 * 100;
                }else if(idade >= 15 && idade <=16){
                    resultado = (5.07/dc) - 4.64*100;
                }else if(idade >= 17 && idade <= 19){
                    resultado = (5.05/dc) - 5.05 * 100;
                }else if ( idade >=20 && idade <= 50){
                    resultado = (5.03/dc) - 4.59 * 100;
                }
               
                
    	        return resultado;
    	}else{
            return 0;
        }
    }
    
    public static String getDescricaoJackson4Atletas(){
        String s = "Foi proposto por Jackson 1980.";
        return s;
    }
    
    public static String getIndicacaoJackson4Atletas(){
        String s = "Metodo utilizado para mulheres, atletas, de todos os esportes, de 18 a 29 anos de idade.";
        return s;
    }
    
    
    
     //Slaughter criancas
    public double slaughter(){
            if (sexo.equals("Masculino")){
    		double ST = dobraTriceps + dobraPanturrilha;
                
                resultado = 0.735 * ST + 1;
                
    	        return resultado;
    	}else if (sexo.equals("Feminino")){
            double ST = dobraTriceps + dobraPanturrilha;
               
            resultado  = 0.610 * ST + 5.1;
            
            return resultado;
        }else {
            return 0;
        }
    }
    
    public static String getDescricaoSlaughter(){
    	String s = "Foi proposto por Slaughter em  1988.";
        return s;
    }
    
    public static String getIndicacaoSlaughter(){
        String s = "Metodo utilizado para criancas, meninos e meninas, brancas e negras.";
        return s;
    }
    
    
    
    //guedes
    
    public double guedes3(){
        if (sexo.equals("Masculino")){
    		double ST = dobraTriceps + dobraSuprailiaca + dobraAbdominal;
                double dc = 1.1714 - 0.0671 * Math.log10(ST);
    	         
                 if(idade >= 7 && idade <= 8 ){
                    resultado = (5.38/dc) - 4.97 * 100;
                }else if( idade >= 9 && idade <= 10){
                    resultado = (5.30/dc) - 4.89 * 100;
                }else if(idade >=11 && idade <= 12){
                    resultado = (5.23/dc) - 4.81 * 100;
                }else if(idade >= 13 && idade <= 14){
                    resultado = (5.07/dc) - 4.64 * 100;
                }else if(idade >= 15 && idade <=16){
                    resultado = (5.03/dc) - 4.59*100;
                }else if(idade >= 17 && idade <= 19){
                    resultado = (4.98/dc) - 4.53 * 100;
                }else if ( idade >=20 && idade <= 50){
                    resultado = (4.95/dc) - 4.50 * 100;
                }
    	        return resultado;
    	}else if(sexo.equals("Feminino")){
    		double ST = dobraCoxa + dobraSuprailiaca + dobraSubscapular;
                
    	        double dc = 1.1665 - 0.0706 * Math.log10(ST);
    	       
    	        if(idade >= 7 && idade <= 8 ){
                    resultado = (5.43/dc) - 5.03 * 100;
                }else if( idade >= 9 && idade <= 10){
                    resultado = (5.35/dc) - 4.95 * 100;
                }else if(idade >=11 && idade <= 12){
                    resultado = (5.25/dc) - 4.84 * 100;
                }else if(idade >= 13 && idade <= 14){
                    resultado = (5.12/dc) - 4.69 * 100;
                }else if(idade >= 15 && idade <=16){
                    resultado = (5.07/dc) - 4.64*100;
                }else if(idade >= 17 && idade <= 19){
                    resultado = (5.05/dc) - 5.05 * 100;
                }else if ( idade >=20 && idade <= 50){
                    resultado = (5.03/dc) - 4.59 * 100;
                }
    	        return resultado;
    	}else{
            return 0;
        }
    }
    
    public static String getDescricaoGuedes3(){
        String s = " Foi proposto por Guedes em 1985.";
        return s;
    }
    
    public static String getIndicacaoGuedes3(){
        String s = "Metodo utilizado para mulheres e homens, estudantes universitarios de 17 a 29 anos de idade.";
        return s;
    }
    
    
    //Gets e sets
    public String getSexo() {
		return sexo;
	}
    public void setSexo(String sexo) {
		this.sexo = sexo;
	}
    public double getPeso() {
		return peso;
	}
    public void setPeso(double peso) {
		this.peso = peso;
	}
    public double getAltura() {
		return altura;

	}
    public void setAltura(double altura) {
		this.altura = altura;
	}
    public int getIdade() {
		return idade;
	}
    public void setIdade(int idade) {
		this.idade = idade;
	}
    public double getDobraAbdominal() {
		return dobraAbdominal;
	}
    public void setDobraAbdominal(double dobraAbdominal) {
		this.dobraAbdominal = dobraAbdominal;
	}
    public double getDobraCoxa() {
		return dobraCoxa;
	}
    public void setDobraCoxa(double dobraCoxa) {
		this.dobraCoxa = dobraCoxa;
	}
    public double getDobraPeito() {
		return dobraPeito;
	}
    public void setDobraPeito(double dobraPeito) {
		this.dobraPeito = dobraPeito;
	}
    public double getDobraSuprailiaca() {
		return dobraSuprailiaca;
	}
    public void setDobraSuprailiaca(double dobraSuprailiaca) {
		this.dobraSuprailiaca = dobraSuprailiaca;
	}
    public double getDobraSubscapular() {
		return dobraSubscapular;
	}
    public void setDobraSubscapular(double dobraSubscapular) {
		this.dobraSubscapular = dobraSubscapular;
	}
    public double getDobraTriceps() {
		return dobraTriceps;
	}
    public void setDobraTriceps(double dobraTriceps) {
		this.dobraTriceps = dobraTriceps;
	}
    public double getDobraLinhaAxilarMedia() {
		return dobraLinhaAxilarMedia;
	}
    public void setDobraLinhaAxilarMedia(double dobraLinhaAxilarMedia) {
		this.dobraLinhaAxilarMedia = dobraLinhaAxilarMedia;
	}
    public double getResultado() {
		return resultado;
	}
    public void setResultado(double resultado) {
		this.resultado = resultado;
	}

}

