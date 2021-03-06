/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package availityAssessment;

import java.net.*;
import java.io.*;

public class MLLPTransfer {

    public static void main(String[] args) {
		Socket mirthSocket = null;
		FileReader fileRead = null;
		BufferedReader bRead = null;
		try {
			mirthSocket = new Socket("localhost", 10301);
			System.out.println("Connected to Mirth" + " on port " + mirthSocket.getPort());
			
			File directory = new File ("C:\\inbox");
			File messageList[] = directory.listFiles();
			StringBuffer hl7Message = new StringBuffer();

			for(int i = 0; i < messageList.length; i++) {
				fileRead = new FileReader(messageList[i]);
				bRead = new BufferedReader(fileRead);
				String fileInput;
				hl7Message.append('\u000b');
				while ((fileInput = bRead.readLine()) != null) {
					hl7Message.append(fileInput + '\r');
				}
				hl7Message.append('\u001c');
				hl7Message.append('\r');
				bRead.close();
				messageList[i].delete();
			}		
			InputStream in = mirthSocket.getInputStream();
			OutputStream out = mirthSocket.getOutputStream();
			out.write(hl7Message.toString().getBytes());
			byte[] ack = new byte[150];
			in.read(ack);
			System.out.println("Response from Mirth: " + new String(ack));
			in.close();
			out.close();
			mirthSocket.close();
		} catch (IOException e) {
			System.out.println("Unable to find server: " + e.getMessage());
		} 
	}
}
