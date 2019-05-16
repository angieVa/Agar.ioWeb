package io.connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class HiloClientHandler extends Thread{
	
	private Socket socket;
	private ServerBD server;
	
	public HiloClientHandler(Socket socket, ServerBD server) {
		
		this.socket = socket;
		this.server = server;
	}

	public void run() {

	//le quite el while true
		handleRequest(this.socket);
	
	
	}


	private void handleRequest(Socket socket2) {
	try {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String headerLine = in.readLine();
		// A tokenizer is a process that splits text into a series of tokens
		StringTokenizer tokenizer =  new StringTokenizer(headerLine);
		//The nextToken method will return the next available token
		String httpMethod = tokenizer.nextToken();
		// The next code sequence handles the GET method. A message is displayed on the
		// server side to indicate that a GET method is being processed
		if(httpMethod.equals("GET"))
		{
			System.out.println("Get method processed");
			String httpQueryString = tokenizer.nextToken();
			System.out.println(httpQueryString);
			if(httpQueryString.equals("/"))
			{
				StringBuilder responseBuffer =  new StringBuilder();
				String str="";
				BufferedReader buf = new BufferedReader(new FileReader("../web/Formulario.html"));			
				while ((str = buf.readLine()) != null) {
					responseBuffer.append(str);
			    }
				sendResponse(socket, 200, responseBuffer.toString());		
			    buf.close();
			}
			//permite obtener el dato ingresado en el submit
			if(httpQueryString.contains("/?email="))
			{
				//?email=alexandra1408%40outlook.com&pass=joli
				System.out.println("Get method processed");
				String[] response =  httpQueryString.split("=");
				//String mensajeObtenido = server.pedirDatosAlServerBD(response[1]);
				String pass = response[2];
				String em = response[1].split("&")[0];
				String[] e = em.split("%40");
				String email = e[0] +"@"+ e[1];
				StringBuilder responseBuffer =  new StringBuilder();
				if(server.leer(email, pass)) {
				ArrayList<String> res = server.partidas(email);
				for(int i = 0; i < res.size(); i++) {
					System.out.println(res.get(i));
				}
					responseBuffer
					.append("<html>")
					.append("<head>")
					.append("<style>")
					.append("body{")
					.append("	background-image: url(\"https://images2.alphacoders.com/973/973928.jpg\");")
					.append("background-size: cover;")
					.append("}")
					.append("</style>")
					.append("<title>Reporte</title>")
					.append("</head>")
					.append("<body>")
					.append("<h1>Reporte por partidas de " + email + "</h1>")
					.append("<table>")
					.append("<tr>")
					.append("<td><strong>Nickname<strong></strong></td>")
					.append("<td><strong>Score</strong></td>")
					.append("<td><strong>Fecha</strong></td>")
					.append("<td><strong>Adversarios</strong></td>")
					.append("<td><strong>GANO?</strong></td>");
					agregarlista(server.partidas(email),responseBuffer);
					responseBuffer.append("<body>")
					.append("</table>")
					.append("</body>")
					.append("</html>");
					sendResponse(socket, 200, responseBuffer.toString());		
				}else {
				responseBuffer
				.append("<html>")
				.append("<head>")
				.append("<style>")
				.append("body{")
				.append("	background-image: url(\"http://www.larutadelsorigens.cat/filelook/full/76/761416/iron-man-pictures-wallpaper.jpg\");")
				.append("background-size: cover;")
				.append("}")
				.append("h1{")
				.append("color : white ;")
				.append("font-size: 40px;")
				.append("}")
				.append("</style>")
				.append("<title>Error</title>")
				.append("</head>")
				.append("<body>")
				.append("<h1>Usuario no encontrado :c</h1>")
				.append("</body>")
				.append("</html>");
				sendResponse(socket, 200, responseBuffer.toString());		
			}
			}
		}
		else
		{
			System.out.println("The HTTP method is not recognized");
			sendResponse(socket, 405, "Method Not Allowed");
		}
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	
	}
	
	private void agregarlista(ArrayList<String> lista , StringBuilder responseBuffer) {
		for (int i = 0; i < lista.size(); i++) {
			System.out.println("CHEQUEO LISTA :" + lista.get(i));
			
			
				responseBuffer.append("<tr>");
				responseBuffer.append("<td>"+lista.get(i).split(",")[0]+"</td>");
				responseBuffer.append("<td>"+lista.get(i).split(",")[3]+"</td>");
				responseBuffer.append("<td>"+lista.get(i).split(",")[4]+"</td>");
				String[] win = lista.get(i).split(",")[5].split("@/");
				responseBuffer.append("<td>"+win[1]+"</td>");
				responseBuffer.append("<td>"+win[0]+"</td>");
				responseBuffer.append("<tr>");
			
		}
		
	}

	public void sendResponse(Socket socket, int statusCode, String responseString)
	{
		String statusLine;
		String serverHeader = "Server: WebServer\r\n";
		String contentTypeHeader = "Content-Type: text/html\r\n";
		
		try {
			DataOutputStream out =  new DataOutputStream(socket.getOutputStream());
			if (statusCode == 200) 
			{
				statusLine = "HTTP/1.0 200 OK" + "\r\n";
				String contentLengthHeader = "Content-Length: "
				+ responseString.length() + "\r\n";
				out.writeBytes(statusLine);
				out.writeBytes(serverHeader);
				out.writeBytes(contentTypeHeader);
				out.writeBytes(contentLengthHeader);
				out.writeBytes("\r\n");
				out.writeBytes(responseString);
				} 
			else if (statusCode == 405) 
			{
				statusLine = "HTTP/1.0 405 Method Not Allowed" + "\r\n";
				out.writeBytes(statusLine);
				out.writeBytes("\r\n");
			} 
			else 
			{
				statusLine = "HTTP/1.0 404 Not Found" + "\r\n";
				out.writeBytes(statusLine);
				out.writeBytes("\r\n");
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
