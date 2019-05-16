package io.connection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;


public class ServerBD {
	
	public boolean webService ;
	public ServerSocket serverSocketWebService;
	public static final int PORT_WEB_SERVICE = 7000;
	private HiloDespliegueAppWeb hiloDespliegueAppWeb;
	
	public ServerBD() throws InterruptedException, IOException {
		
		webService = true;
		try {
			serverSocketWebService = new ServerSocket(PORT_WEB_SERVICE);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
		hiloDespliegueAppWeb = new HiloDespliegueAppWeb(this);
		hiloDespliegueAppWeb.start();
	}

	
	public boolean isWebService() {
		return webService;
	}
	//http://www.larutadelsorigens.cat/filelook/full/76/760973/iron-man-ultra-hd-wallpapers.jpg


	public void setWebService(boolean webService) {
		this.webService = webService;
	}



	public ServerSocket getServerSocketWebService() {
		return serverSocketWebService;
	}



	public void setServerSocketWebService(ServerSocket serverSocketWebService) {
		this.serverSocketWebService = serverSocketWebService;
	}
	
	public ArrayList<String> partidas(String email){
	
		File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        ArrayList<String> Final = new ArrayList<String>();
        
        ArrayList<String> partidas = new ArrayList<String>();
        try {
            
            archivo = new File ("../Data/archivo.txt");
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            String linea;
        	String p = "";
        	boolean fin = true;
            while(fin) {
            	if((linea=br.readLine())!=null) {
            	  	if(!linea.equals("Partida") && !linea.equals(null)) {

            			 p+= linea+"&";
            		
            	}else{
            	if(!p.equals("")) {
            		 partidas.add(p);
            		 p= "";
            	}
            	}
            	}else {
            	fin = false;
            	if(!p.equals("")) {
           		 partidas.add(p);
           		 p= "";
           	}
            	}
       
            }
            
           for(int i = 0; i < partidas.size(); i++) {
        	   System.out.println(partidas.get(i));
           }
        	System.out.println(partidas.size());
            boolean esta = false;
            for (int i = 0; i < partidas.size(); i++) {
            	
            	if(!partidas.get(i).contains(email)) {
            		partidas.remove(i);
            	}
			}
            
           
            for(int i = 0; i < partidas.size();i++) {
            	 String adversarios = "";
            	 String actu = "";
            	String[] usu = partidas.get(i).split("&");
            	for(int j = 0; j< usu.length; j++) {
            		
            		if(usu[j].contains(email)) {
            			actu = usu[j];
            		}else {
            			adversarios += usu[j].split(",")[0]+"-";
            		}
            	}
            	
            	Final.add(actu +"/"+adversarios);
            }
            
          
               
         }
         catch(Exception e){
            e.printStackTrace();
         }finally{
            try{                    
               if( null != fr ){   
                  fr.close();     
               }                  
            }catch (Exception e2){ 
               e2.printStackTrace();
            }
        
         }
        return Final;
	}
	
	public boolean leer(String email, String pass) {
    	boolean existe = false;
    	
    	File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
          
           archivo = new File ("../Data/Usuarios.txt");
           fr = new FileReader (archivo);
           br = new BufferedReader(fr);

           String linea;
           while((linea=br.readLine())!=null) {
        	   
        	   String[] sep = linea.split(",");
        	   if(sep[0].equals(email) && sep[1].equals(pass)) {
        		   existe = true;
        	   }
        	
           }
              
        }
        catch(Exception e){
           e.printStackTrace();
        }finally{
           try{                    
              if( null != fr ){   
                 fr.close();     
              }                  
           }catch (Exception e2){ 
              e2.printStackTrace();
           }
        }
   
    	
    	return existe;
    }
	
	

	
	public static void main(String[] args) {
		try {
			ServerBD s = new ServerBD();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
