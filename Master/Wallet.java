import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import com.google.gson.GsonBuilder;
import java.awt.Dimension;
import java.awt.Toolkit;

class Wallet implements ActionListener {
    JFrame wallet = new JFrame();
    JLabel labelInsert = new JLabel("Enter New Diagnosis");
    JTextField tfInsert = new JTextField();
    JLabel labelToken = new JLabel("Enter Token");
    JTextField tfToken = new JTextField();

    JButton buttonAddTrans = new JButton("Add New Diagnosis");
    JButton buttonViewUserTrans = new JButton("View Diagnosis History");
    JButton buttonMenu = new JButton("Logout");

    public Wallet() {
        generateAWTWindow();
    }

    public void generateAWTWindow() {
        labelInsert.setBounds(50, 10, 250, 20);
        tfInsert.setBounds(50, 30, 250, 20);
        labelToken.setBounds(50, 50, 250, 20);
        tfToken.setBounds(50, 70, 250, 20);

        buttonAddTrans.setBounds(50, 100, 250, 25);
        buttonViewUserTrans.setBounds(50, 180, 250, 25);
        buttonMenu.setBounds(50, 250, 250, 25);

        wallet.add(labelInsert);
        wallet.add(tfInsert);
        wallet.add(labelToken);
        wallet.add(tfToken);
        wallet.add(buttonAddTrans);
        wallet.add(buttonViewUserTrans);
        wallet.add(buttonMenu);

        buttonAddTrans.addActionListener(this);
        buttonViewUserTrans.addActionListener(this);
        buttonMenu.addActionListener(this);

        wallet.setLayout(null);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        wallet.setSize(400, 400);
        wallet.setLocation((dim.width - wallet.getWidth()) / 2, (dim.height - wallet.getHeight()) / 2);
        wallet.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonAddTrans) {
            String diagnosis = tfInsert.getText();
            int token = Integer.parseInt(tfToken.getText());
            if (ZKP(token, Login.B) != true) {
                System.out.println("Illegal Access Token. Unable to verify Signature.\n");
                wallet.setVisible(false);
                new Wallet();
            } else {
                BlockChain.createBlock(Login.username, diagnosis);
                wallet.setVisible(false);
                new Wallet();
            }
        } else if (e.getSource() == buttonViewUserTrans) {
            viewUserTransactions();
        } else if (e.getSource() == buttonMenu) {
            wallet.setVisible(false);
            new Login();
        }
    }

    public static Boolean ZKP(int token, int B) {
        System.out.println("token:" + (token) + " B:" + (B));
        int r = (int) ((Math.random() * (10 - 0)) + 0);
        int h = (int) ((Math.pow(2, r)) % 11);

        int bit;
        if (Math.random() < 0.5) {
            bit = 0;
        } else {
            bit = 1;
        }

        String challenge = StringUtil.generate_challenge();
        String response = StringUtil.create_response(challenge, bit);
        boolean isValid = verify_response(challenge, response, B, bit);

        System.out.println("\nIsValid: " + isValid);
        if (!isValid) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean verify_response(String challenge, String response, int B, int bit) {
        String expectedResponse = StringUtil.create_response(challenge, bit);
        return response.equals(expectedResponse);
    }

    public static void viewUserTransactions() {
        System.out.println("\n\n****USER MEDICAL HISTORY****");
        System.out.println("Current User:- " + Login.username);

        ArrayList<Block> userData = BlockChain.getUserData(Login.username);
        String userDataJson = new GsonBuilder().setPrettyPrinting().create().toJson(userData);
        System.out.println(userDataJson);
    }

    public static void main(String args[]) {
        new Wallet();
    }
}
