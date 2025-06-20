import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LOCOApp {
    static JFrame frame;

    public static void main(String[] args) {
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 16));
        SwingUtilities.invokeLater(() -> showRegisterPage());
    }

    static void showRegisterPage() {
        frame = new JFrame("🛒 LOCO - Register");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(255, 245, 250));
        frame.setLayout(null);

        JLabel title = new JLabel("Create Your LOCO Account 🦄");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setBounds(480, 50, 600, 40);
        frame.add(title);

        String[] labels = { "Name", "Email", "Age", "Address", "Password" };
        int y = 130;
        JTextField[] fields = new JTextField[4];
        JPasswordField passwordField = new JPasswordField();

        for (int i = 0; i < 4; i++) {
            JLabel lbl = new JLabel(labels[i] + ":");
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            lbl.setBounds(450, y, 100, 30);
            frame.add(lbl);

            JTextField tf = new JTextField();
            tf.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            tf.setBounds(550, y, 300, 30);
            fields[i] = tf;
            frame.add(tf);

            y += 50;
        }

        JLabel pwLbl = new JLabel("Password:");
        pwLbl.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        pwLbl.setBounds(450, y, 100, 30);
        frame.add(pwLbl);

        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setBounds(550, y, 300, 30);
        frame.add(passwordField);

        JButton register = new JButton("Register ✨");
        register.setFont(new Font("Segoe UI", Font.BOLD, 18));
        register.setBounds(550, y + 60, 180, 40);
        register.setBackground(new Color(255, 192, 203));
        register.setForeground(Color.WHITE);
        register.setFocusPainted(false);
        register.setBorder(BorderFactory.createLineBorder(Color.PINK, 2));
        frame.add(register);

        frame.setVisible(true);

        register.addActionListener(e -> showLoginPage());
    }

    static void showLoginPage() {
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.setTitle("🔐 LOCO - Login");
        frame.getContentPane().setBackground(new Color(245, 255, 250));

        JLabel title = new JLabel("Welcome Back to LOCO 💖");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setBounds(500, 100, 600, 40);
        frame.add(title);

        JLabel emailLbl = new JLabel("Email:");
        JTextField email = new JTextField();
        JLabel pwLbl = new JLabel("Password:");
        JPasswordField pw = new JPasswordField();

        JButton login = new JButton("Login 🚀");

        emailLbl.setFont(pwLbl.getFont());
        pwLbl.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        emailLbl.setBounds(450, 180, 100, 30);
        email.setBounds(550, 180, 300, 30);
        pwLbl.setBounds(450, 230, 100, 30);
        pw.setBounds(550, 230, 300, 30);
        login.setBounds(550, 280, 150, 40);

        login.setFont(new Font("Segoe UI", Font.BOLD, 18));
        login.setBackground(new Color(135, 206, 235));
        login.setForeground(Color.WHITE);
        login.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        login.setFocusPainted(false);

        frame.add(emailLbl);
        frame.add(email);
        frame.add(pwLbl);
        frame.add(pw);
        frame.add(login);

        login.addActionListener(e -> showMoodPopup());

        frame.setLayout(null);
        frame.setVisible(true);
    }

    static void showMoodPopup() {
        JDialog moodDialog = new JDialog(frame, "🛍️ Mood-Based Shopping", true);
        moodDialog.setSize(600, 300);
        moodDialog.setLayout(new FlowLayout());
        moodDialog.getContentPane().setBackground(new Color(255, 250, 205));

        JLabel msg = new JLabel("Pick your mood, and we'll surprise you!");
        msg.setFont(new Font("Segoe UI", Font.BOLD, 22));
        moodDialog.add(msg);

        String[] emojis = { "😊", "😍", "😎", "😢", "🤩" };
        String[][] suggestions = {
            { "Casual T-Shirt", "Denim Jeans", "White Sneakers", "Cute Cap", "Wrist Watch" },
            { "Heart Necklace", "Perfume", "Rings", "Scrunchies", "Mini Bag" },
            { "Shades", "Leather Jacket", "Laptop Bag", "Sneakers", "Hoodie" },
            { "Plushie", "Scented Candle", "Chocolates", "Self-Care Book", "Hot Cocoa Mug" },
            { "Smartphone", "Wireless Earbuds", "Power Bank", "Smartwatch", "Tablet" }
        };

        for (int i = 0; i < emojis.length; i++) {
            JButton btn = new JButton(emojis[i]);
            int index = i;
            btn.setPreferredSize(new Dimension(60, 60));
            btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
            moodDialog.add(btn);

            btn.addActionListener(e -> {
                StringBuilder s = new StringBuilder("🎁 Your Mood Picks:\n\n");
                for (String product : suggestions[index]) {
                    s.append("• ").append(product).append("\n");
                }
                JOptionPane.showMessageDialog(moodDialog, s.toString(), "Products", JOptionPane.PLAIN_MESSAGE);
            });
        }

        JButton skip = new JButton("❌ Skip");
        skip.setPreferredSize(new Dimension(80, 40));
        skip.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        skip.setBackground(Color.LIGHT_GRAY);
        skip.addActionListener(e -> {
            moodDialog.dispose();
            showSearchPage();
        });

        moodDialog.add(skip);
        moodDialog.setLocationRelativeTo(frame);
        moodDialog.setVisible(true);
    }

    static void showSearchPage() {
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.setTitle("🔎 LOCO - Search");

        JLabel lbl = new JLabel("Search Products You Love 💡");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lbl.setBounds(450, 50, 500, 40);
        frame.add(lbl);

        JTextField searchBar = new JTextField();
        JButton go = new JButton("Search");

        searchBar.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        searchBar.setBounds(400, 120, 300, 35);
        go.setBounds(710, 120, 100, 35);

        go.setBackground(new Color(144, 238, 144));
        go.setFont(new Font("Segoe UI", Font.BOLD, 16));
        go.setFocusPainted(false);

        frame.add(searchBar);
        frame.add(go);

        String[] cats = { "Electronics", "Clothing", "Home Decor", "Accessories", "Toys" };
        int y = 200;

        for (String cat : cats) {
            JButton btn = new JButton(cat);
            btn.setBounds(500, y, 180, 40);
            btn.setBackground(new Color(255, 222, 173));
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            btn.setFocusPainted(false);
            y += 50;
            frame.add(btn);
        }

        frame.setLayout(null);
        frame.setVisible(true);
    }
}