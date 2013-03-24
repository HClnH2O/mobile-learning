package mobile;


import java.awt.Desktop;
import java.io.*;
import java.net.URI;
import java.util.*;
import javax.comm.*;

import java.sql.*;

class MySerialCheck implements SerialPortEventListener{
	Statement st2;
	 static Enumeration portList;
     static CommPortIdentifier portId;
     InputStream inputStream;
     static SerialPort serialPort;
     static OutputStream outputStream;
    String output;
    String a[];
    int i;
    Map m1;
    Map m2;
     byte[] readBuffer ;
     StringBuffer sb=new StringBuffer();
    


     public MySerialCheck() {


    	 System.out.println("Entered");
    	 portList = CommPortIdentifier.getPortIdentifiers();
    	 System.out.println(""+portList.hasMoreElements());
		 while (portList.hasMoreElements()) {
		 System.out.println("Entered");
		 portId = (CommPortIdentifier) portList.nextElement();
			 if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
			 	 	System.out.println("Entered");

				 if (portId.getName().equals("COM16")) {


		 	System.out.println("Entered");


    				 try {
    					 serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
    				 } catch (PortInUseException e)
    				 	{

    				 	}
    				 try {
    					 inputStream = serialPort.getInputStream();
                         outputStream = serialPort.getOutputStream();
    				 } catch (IOException e)
    				 	{

    				 	}

    				 try {
    					 serialPort.addEventListener(this);
    				 } catch (TooManyListenersException e) {}
    				 serialPort.notifyOnDataAvailable(true);

    				 try {
    					 serialPort.setSerialPortParams(9600,
    							 SerialPort.DATABITS_8,
    							 SerialPort.STOPBITS_1,
    							 SerialPort.PARITY_NONE);
    				 } catch (UnsupportedCommOperationException e) {}


    		
                   }
                   else
                   {
                   	System.out.println("Port Not Matched");
                   }


			 }
		 }
	 }




     public void serialEvent(SerialPortEvent event) {
    	 switch (event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:
			try {
				try {
					//Thread.sleep(1000);
				} catch (Exception e) {

				}

				while (inputStream.available() > 0) {
					int size = inputStream.available();
					readBuffer = new byte[size];
					int numBytes = inputStream.read(readBuffer);
					output = new String(readBuffer);

					//sb.append(output);
					//  String stringData = new String(sb);
				String p=output;
					//  while(st.hasMoreTokens()){
					//   System.out.println(st.nextToken(","));

					//	   a[i]=st.nextToken(",");
					//	   i++;
					// }
					//System.out.println(" : "+a);
					System.out.println(" : " + output);
					
					try {
						String url = "jdbc:mysql://127.0.0.1:3306/mobile";
						Class.forName("com.mysql.jdbc.Driver");
						Connection con = (Connection) DriverManager
								.getConnection(url, "root", "root");
						Statement st2 = (Statement) con.createStatement();
						
						String t[] = output.split(",");
						String t1[] = output.split(",");
                                                String t2[] = output.split(",");
						for (int j = 0; j <3; j++) {

							System.out.println(t[j]);

						}
						
						
						if(t[0].equals("ask")){
							
						System.out.println("Insert into ask values ('"+t[1]+"','"+t[2]+"')");
						st2.executeUpdate("Insert into ask values ('"+t[1]+"','"+t[2]+"')");}else if(t[0].equals("contact")){
							
						//System.out.println("Insert into contact values ('"+t[1]+"','"+t[2]+"')");
						st2.executeUpdate("Insert into contact values ('"+t[1]+"','"+t[2]+"')");} else{ 
													
							for (int j = 0; j<22; j++) {

								//System.out.println("SDSDSDSDSDSDSD"+t[j]);
								System.out.println("can"+t[j]);
							}
							for (int o = 22; o <t.length; o++) {

								//System.out.println("SDSDSDSDSDSDSD"+t[o]);
								System.out.println("can"+t[o]);
							}
							if(t[22].equals("quiz")){
								
								for (int k=20; k <t.length; k++) {

									System.out.println("quiz"+t1[k]);
									
								}
							
							ResultSet rs = st2.executeQuery("select * from quiz where quiz='"+t1[20].toUpperCase()+"'");
							int b = 0;
							int c = 0, d = 0;
							while (rs.next()) {
								String pv1 = rs.getString(2);
								String pv = rs.getString(8);

								int vt = Integer.parseInt(pv1);

								if (pv.equalsIgnoreCase(t[b])) {
									c++;
								} else {
									d++;
								}
								b++;
							}
								System.out.println("Right" + c);
								System.out.println("wrong" + d);
						st2.executeUpdate("Insert into result values ('"+t1[21]+"','"+t1[20]+"','"+c+"')");}
						}} catch (Exception e) {
						e.printStackTrace();
					}
				} /* End-of-while loop*/

			} catch (Exception e) {
				/*Switch End*/
			}
		}
     } /*Serial Event End*/

   

    public static void main(String args[])
    {
    	new MySerialCheck();
    }
}