package lt.viko.eif;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket server;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    public Server(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for the client");
            socket = server.accept();
            System.out.println("Client connected");

            out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            byte[] receivedData = receiveXMLData();
            String xmlString = new String(receivedData);

            Student student = TransformToPOJO.transformToPOJO(new ByteArrayInputStream(receivedData));

            System.out.println("Received Student Details:");
            System.out.println("ID: " + student.getStudentId());
            System.out.println("Name: " + student.getName());
            System.out.println("Subjects:");
            for (Subject subject : student.getSubjects()) {
                System.out.println(" - Name: " + subject.getName() + ", Grade: " + subject.getGrade());
            }

            String transformedXML = JAXBTransformer.transformToXML(student);
            System.out.println("Transformed XML:");
            System.out.println(transformedXML);

            sendXMLData(transformedXML.getBytes());

            System.out.println("File sent");

        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                if (socket != null) socket.close();
                if (server != null) server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] receiveXMLData() throws IOException {
        int length = in.readInt();
        byte[] data = new byte[length];
        in.readFully(data);
        return data;
    }

    private void sendXMLData(byte[] xmlData) throws IOException {
        out.writeInt(xmlData.length);
        out.flush();
        out.write(xmlData, 0, xmlData.length);
        out.flush();
    }

    public static void main(String[] args) {
        Server server = new Server(5000);
    }
}
