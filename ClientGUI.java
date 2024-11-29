import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class ClientGUI {
    private Socket socket;
    private BufferedReader br;
    private PrintWriter out;

    private JFrame frame;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;

    public ClientGUI(String serverIP, int port) {
        try {
            // Connecting to the server
            socket = new Socket(serverIP, port);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            // Setting up the GUI
            frame = new JFrame("Chat Client");
            chatArea = new JTextArea();
            chatArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(chatArea);

            messageField = new JTextField();
            sendButton = new JButton("Send");

            frame.setLayout(new BorderLayout());
            frame.add(scrollPane, BorderLayout.CENTER);

            JPanel bottomPanel = new JPanel(new BorderLayout());
            bottomPanel.add(messageField, BorderLayout.CENTER);
            bottomPanel.add(sendButton, BorderLayout.EAST);

            frame.add(bottomPanel, BorderLayout.SOUTH);

            frame.setSize(400, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            // Action for Send Button
            sendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String message = messageField.getText();
                    out.println(message);
                    out.flush();
                    messageField.setText("");

                    if (message.equalsIgnoreCase("exit")) {
                        try {
                            socket.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

            // Start reading from the server
            startReading();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public void startReading() {
        Runnable r1 = () -> {
            try {
                String message;
                while ((message = br.readLine()) != null) {
                    chatArea.append("Server: " + message + "\n");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Connection closed.");
            }
        };
        new Thread(r1).start();
    }

    public static void main(String[] args) {
        try {
            String serverIP = JOptionPane.showInputDialog("Enter Server IP:");
            int port = Integer.parseInt(JOptionPane.showInputDialog("Enter Server Port:"));

            new ClientGUI(serverIP, port);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Check your input entries.");
        }
    }
}
