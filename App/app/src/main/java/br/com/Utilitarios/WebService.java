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
package br.com.Utilitarios;

public class WebService {
	
	private static final String NAMESPACE = "http://WorkUp.com.br/";
	private static final String URL = "http://ec2-54-207-113-141.sa-east-1.compute.amazonaws.com/WorkUpWebService?wsdl";
	
	//facebook
	private static final String APP_ID = "1540202289528318"; // Replace your App ID here
	 
	
	public static String getNamespace() {
		return NAMESPACE;
	}
	public static String getUrl() {
		return URL;
	}
	
	public static String getAppId(){
		return APP_ID;
	}
	
	public static String getSoapAction(String method) {
	    return "\"" + WebService.getNamespace() + method + "\""; 
	}
	
}
