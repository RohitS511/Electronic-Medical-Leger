import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;

class Login implements ActionListener {
    static String username;
    static int B;
    static String challenge;

    JFrame f = new JFrame();

    JLabel l1 = new JLabel("Wallet Username");
    JLabel l2 = new JLabel("Wallet Password");

    JTextField t1 = new JTextField();
    JTextField t2 = new JTextField();

    JButton b1 = new JButton("Login");
    JButton b4 = new JButton("SignUp");
    JButton b2 = new JButton("Quit");
    JButton b3 = new JButton("See Ledger");

    Login() {
        b3.setBounds(50, 30, 300, 20);

        l1.setBounds(50, 110, 300, 20);
        t1.setBounds(50, 135, 300, 20);

        l2.setBounds(50, 160, 300, 20);
        t2.setBounds(50, 185, 300, 20);

        b1.setBounds(50, 220, 300, 20);
        b4.setBounds(50, 250, 300, 20);
        b2.setBounds(50, 300, 300, 20);

        f.add(l1);
        f.add(l2);
        f.add(t1);
        f.add(t2);
        f.add(b1);
        f.add(b2);
        f.add(b3);
        f.add(b4);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);

        f.setLayout(null);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(400, 400);
        f.setLocation((dim.width - f.getWidth()) / 2, (dim.height - f.getHeight()) / 2);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            if (verifyUserCredentials(t1.getText(), t2.getText())) {
                System.out.println("Current User:- " + username);
                new Wallet();
                f.setVisible(false);
            } else {
                System.out.println("Invalid " + t1.getText() + " and Password.\nYou need to SignUp.");
                f.dispose();
                new Login();
            }
        } else if (e.getSource() == b3) {
            BlockChain.viewLedger();
        } else if (e.getSource() == b2) {
            System.exit(0);
        } else {
            try {
                File f3 = new File("Master/userDB.txt");
                BufferedReader buffer1 = new BufferedReader(new FileReader(f3));
                String readLine1 = "";
                int flag = 0;
                while ((readLine1 = buffer1.readLine()) != null) {
                    String[] line_seg = readLine1.split(":");

                    if (line_seg[0].equals(t1.getText())) {
                        System.out.println("Patient Already Exist.\nPlease Try to Login.");
                        flag = 1;
                        buffer1.close();
                        break;
                    }
                }
                buffer1.close();
                if (flag == 1)
                    return;
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            String uspass = t1.getText() + ":" + t2.getText();
            challenge = StringUtil.generate_challenge();
            String hashA = StringUtil.applyHmacSha256(challenge, "secretKey");
            String hashB = StringUtil.applyHmacSha256(t2.getText(), "secretKey");
            String line = hashA + "," + hashB + ",";
            File f = new File("Master/userDB.txt");
            int cnt_line = 0;
            try (BufferedReader buffer = new BufferedReader(new FileReader(f))) {
                String readLine = "";
                while ((readLine = buffer.readLine()) != null) {
                    cnt_line++;
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            cnt_line++;
            line = line.concat(Integer.toString(cnt_line)).concat(",");
            int token_cnt = ((int) Math.pow(2, cnt_line)) % (11);
            line = line.concat(Integer.toString(token_cnt));
            try {
                FileWriter fw = new FileWriter(f.getAbsoluteFile(), true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(line);
                bw.newLine();
                bw.close();
                System.out.println("Patient appended to the file successfully.");

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static boolean verifyUserCredentials(String a, String b) {
        try {
            String hashA = StringUtil.applyHmacSha256(a, "secretKey");
            String hashB = StringUtil.applyHmacSha256(b, "secretKey");

            File f = new File("Master/userDB.txt");
            BufferedReader buffer = new BufferedReader(new FileReader(f));
            String readLine = "";

            while ((readLine = buffer.readLine()) != null) {
                String[] line = readLine.split(",");

                if (line[0].equals(hashA) && line[1].equals(hashB)) {
                    username = a;
                    B = Integer.parseInt(line[3]);
                    buffer.close();
                    return true;
                } else if (!(line[0].equals(hashA))) {
                    System.out.println("Incorrect Patient ID\n");
                } else if (!(line[1].equals(hashB))) {
                    System.out.println("Incorrect Password\n");
                }
            }
            buffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String args[]) {
        BlockChain.initiateBlockChain();
        new Login();
    }
}
