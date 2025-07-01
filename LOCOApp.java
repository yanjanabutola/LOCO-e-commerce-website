import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.plaf.basic.BasicButtonUI; // For custom button UI to handle hover/selection

public class LOCOApp {

    // --- Color Palette (More professional, soft tones) ---
    static final Color PRIMARY_PINK = new Color(233, 30, 99); // Deeper Pink
    static final Color LIGHT_PINK_BG = new Color(255, 228, 237); // Very light pink for main background
    static final Color ACCENT_PINK_CARD = new Color(255, 243, 247); // Lighter pink for cards
    static final Color HOVER_PINK = new Color(255, 200, 220); // Pink for hover states
    static final Color TEXT_COLOR_DARK = new Color(50, 50, 50); // Dark grey for general text
    static final Color TEXT_COLOR_LIGHT = new Color(120, 120, 120); // Lighter grey for descriptions
    static final Color BORDER_COLOR = new Color(220, 220, 220); // Light grey border
    static final Color BG_WHITE = Color.WHITE;
    static final Color LOGIN_BG_COLOR = new Color(245, 255, 250); // Light green-ish for login
    static final Color REGISTER_BG_COLOR = new Color(255, 245, 250); // Light pink-ish for register
    static final Color MOOD_POPUP_BG_COLOR = new Color(255, 250, 205); // Light yellow for mood popup
    static final Color BUTTON_REGISTER_BG = new Color(255, 192, 203); // Soft pink for register button
    static final Color BUTTON_LOGIN_BG = new Color(135, 206, 235); // Light blue for login button
    static final Color BUTTON_SEARCH_BG = new Color(144, 238, 144); // Light green for search button
    static final Color BUTTON_CATEGORY_BG = new Color(255, 222, 173); // Orange-ish for category buttons

    // --- Fonts ---
    static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 36);
    static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 24); // Slightly smaller subtitle
    static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 28); // For main headers like "Select Your Mood"
    static final Font FONT_LABEL = new Font("Segoe UI", Font.PLAIN, 18);
    static final Font FONT_TEXT_FIELD = new Font("Segoe UI", Font.PLAIN, 16);
    static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 18);
    static final Font FONT_PRODUCT_NAME = new Font("Segoe UI", Font.BOLD, 18);
    static final Font FONT_PRODUCT_DESC = new Font("Segoe UI", Font.PLAIN, 13);
    static final Font FONT_EMOJI_CARD = new Font("Segoe UI Emoji", Font.PLAIN, 36); // Larger emoji on cards
    static final Font FONT_EMOJI_DIALOG = new Font("Segoe UI Emoji", Font.PLAIN, 60); // Very large emoji in dialog

    static JFrame mainFrame; // Renamed for clarity as it's now the main app frame

    // --- Data Classes (from original Main.java) ---
    static class Mood {
        String name;
        String emoji; // Used for fallback if image fails, or general purpose
        ImageIcon imageIcon; // For custom image icons

        Mood(String name, String emoji, String imagePath) {
            this.name = name;
            this.emoji = emoji;
            try {
                // Adjust path based on where you put your images in the project
                // For example, if images are in a folder named 'images' directly in your project root
                this.imageIcon = new ImageIcon(LOCOApp.class.getResource("/images/" + imagePath));
                // Scale image if it's too large/small
                if (this.imageIcon.getImage() != null) {
                    Image img = this.imageIcon.getImage();
                    Image scaledImg = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH); // Adjust size as needed
                    this.imageIcon = new ImageIcon(scaledImg);
                }
            } catch (Exception e) {
                System.err.println("Error loading image for mood " + name + ": " + imagePath);
                // Fallback to emoji if image fails
                this.imageIcon = null;
            }
        }

        // Constructor for moods without specific images (if any)
        Mood(String name, String emoji) {
            this(name, emoji, null); // Call the other constructor with null image path
        }

        public String toString() { return emoji + " " + name; }
    }

    static class Product {
        String name, category, color, size, emoji, description;
        double price;
        Mood mood;
        Product(String name, String category, String color, String size, double price, String emoji, String description, Mood mood) {
            this.name = name; this.category = category; this.color = color; this.size = size;
            this.price = price; this.emoji = emoji; this.description = description; this.mood = mood;
        }
        @Override
        public String toString() { return emoji + " " + name; }
    }

    // --- Global Data (Combined and enhanced) ---
    static Mood[] moods = {
        new Mood("Happy", "😊", "happy.jpeg"),
        new Mood("Excited", "🤩", "excited.jpeg"),
        new Mood("Sad", "😔", "sad.jpeg"),
        new Mood("Tired", "🥱", "tired.jpeg"), // Renamed Lazy to Tired for consistency with image
        new Mood("Angry", "😡", "angry.jpeg"), // Added Angry mood with image
        new Mood("Romantic", "🥰"), // No specific image for this one, so use emoji fallback
        new Mood("Sunny", "🌞"),
        new Mood("Rainy", "🌧️"),
        new Mood("Spring", "🌸"),
        new Mood("Winter", "❄️")
    };

    static String[] categories = {"Fashion", "Books", "Comfort", "Events", "Gifts", "Electronics", "Home Decor", "Accessories", "Toys"}; // Combined/Expanded categories
    static String[] colors = {"Red", "Blue", "Black", "Pink", "Yellow", "Green", "White", "Gray"}; // Expanded colors
    static String[] sizes = {"XS", "S", "M", "L", "XL", "XXL", "-"}; // Expanded sizes, added '-' for N/A

    static List<Product> allProducts = Arrays.asList(
        new Product("Colorful T-shirt", "Fashion", "Red", "M", 999.00, "👕", "Bright and cheerful t-shirt", moods[0]), // Happy
        new Product("Fun Phone Case", "Fashion", "Pink", "M", 499.00, "📱", "Playful phone case with cute designs", moods[0]),
        new Product("Concert Tickets", "Events", "Yellow", "-", 2999.00, "🎫", "Tickets to your favorite concert", moods[1]), // Excited
        new Product("Motivational Book", "Books", "Blue", "-", 399.00, "📖", "Book to lift your spirits", moods[2]), // Sad
        new Product("Soft Blanket", "Comfort", "Blue", "L", 1499.00, "🛏️", "Cozy blanket for comfort", moods[2]),
        new Product("Comfy Pajamas", "Comfort", "Pink", "L", 1299.00, "🛏️", "Soft and comfortable pajamas", moods[3]), // Tired
        new Product("Stress Ball Set", "Gifts", "Green", "-", 299.00, "😫", "Set of stress relief balls", moods[4]), // Angry
        new Product("Love Notes Jar", "Gifts", "Red", "-", 499.00, "💌", "Jar to collect love notes", moods[5]), // Romantic
        new Product("Sunglasses", "Fashion", "Black", "-", 1499.00, "🕶️", "Stylish sunglasses for sunny days", moods[6]), // Sunny
        new Product("Raincoat", "Fashion", "Yellow", "M", 1999.00, "🧥", "Waterproof raincoat", moods[7]), // Rainy
        new Product("Spring Collection Top", "Fashion", "Pink", "S", 2499.00, "🌸", "Limited edition spring collection top", moods[8]), // Spring
        new Product("Winter Boots", "Fashion", "Black", "L", 2999.00, "🥾", "Warm winter boots for snowy days", moods[9]), // Winter
        new Product("Gaming Headset", "Electronics", "Black", "-", 3500.00, "🎧", "Immersive gaming audio", moods[1]), // Excited
        new Product("Cozy Throw Pillow", "Home Decor", "Gray", "-", 750.00, "🛋️", "Soft pillow for your couch", moods[3]), // Tired
        new Product("Designer Scarf", "Fashion", "Red", "-", 1800.00, "🧣", "Elegant scarf for a romantic evening", moods[5]) // Romantic
    );

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 16));
        SwingUtilities.invokeLater(LOCOApp::showRegisterPage); // Start with registration
    }

    // --- Main Frame Setup ---
    private static void setupMainFrame() {
        mainFrame = new JFrame("LOCO Mood Shopping");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 750); // Increased frame size for more spacious feel
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setBackground(LIGHT_PINK_BG); // Set background for the frame content pane
    }

    // --- Page Transitions ---
    private static void showPanel(JPanel panel, String title) {
        if (mainFrame == null) {
            setupMainFrame();
        }
        mainFrame.setTitle(title);
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(panel, BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
        mainFrame.setVisible(true);
    }

    // --- Registration Page (Refactored with Layout Managers) ---
    static void showRegisterPage() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(REGISTER_BG_COLOR);
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Create Your LOCO Account 🦄");
        title.setFont(FONT_TITLE);
        title.setForeground(PRIMARY_PINK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span two columns
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(title, gbc);

        gbc.gridwidth = 1; // Reset to 1 column width
        gbc.anchor = GridBagConstraints.WEST; // Align labels to the left
        String[] labels = { "Name:", "Email:", "Age:", "Address:", "Password:" };
        JTextField[] fields = new JTextField[4];
        JPasswordField passwordField = new JPasswordField();

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(FONT_LABEL);
            lbl.setForeground(TEXT_COLOR_DARK);
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            panel.add(lbl, gbc);

            if (labels[i].equals("Password:")) {
                passwordField.setFont(FONT_TEXT_FIELD);
                passwordField.setPreferredSize(new Dimension(300, 35));
                passwordField.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(BORDER_COLOR, 1), new EmptyBorder(5, 10, 5, 10)));
                gbc.gridx = 1;
                panel.add(passwordField, gbc);
            } else {
                JTextField tf = new JTextField();
                tf.setFont(FONT_TEXT_FIELD);
                tf.setPreferredSize(new Dimension(300, 35));
                tf.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(BORDER_COLOR, 1), new EmptyBorder(5, 10, 5, 10)));
                fields[i] = tf;
                gbc.gridx = 1;
                panel.add(tf, gbc);
            }
        }

        JButton register = new JButton("Register ✨");
        register.setFont(FONT_BUTTON);
        register.setBackground(BUTTON_REGISTER_BG);
        register.setForeground(BG_WHITE);
        register.setFocusPainted(false);
        register.setBorder(BorderFactory.createLineBorder(PRIMARY_PINK, 2, true));
        register.setUI(new StyledButtonUI()); // Apply custom button UI
        gbc.gridx = 0;
        gbc.gridy = labels.length + 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(25, 5, 10, 5); // More top padding
        panel.add(register, gbc);

        register.addActionListener(e -> showLoginPage());
        showPanel(panel, "🛒 LOCO - Register");
    }

    // --- Login Page (Refactored with Layout Managers) ---
    static void showLoginPage() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(LOGIN_BG_COLOR);
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Welcome Back to LOCO 💖");
        title.setFont(FONT_TITLE);
        title.setForeground(PRIMARY_PINK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel emailLbl = new JLabel("Email:");
        emailLbl.setFont(FONT_LABEL);
        emailLbl.setForeground(TEXT_COLOR_DARK);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(emailLbl, gbc);

        JTextField emailField = new JTextField();
        emailField.setFont(FONT_TEXT_FIELD);
        emailField.setPreferredSize(new Dimension(300, 35));
        emailField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1), new EmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        JLabel pwLbl = new JLabel("Password:");
        pwLbl.setFont(FONT_LABEL);
        pwLbl.setForeground(TEXT_COLOR_DARK);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(pwLbl, gbc);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(FONT_TEXT_FIELD);
        passwordField.setPreferredSize(new Dimension(300, 35));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1), new EmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JButton login = new JButton("Login 🚀");
        login.setFont(FONT_BUTTON);
        login.setBackground(BUTTON_LOGIN_BG);
        login.setForeground(BG_WHITE);
        login.setBorder(BorderFactory.createLineBorder(BUTTON_LOGIN_BG.darker(), 2, true));
        login.setFocusPainted(false);
        login.setUI(new StyledButtonUI()); // Apply custom button UI
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(25, 5, 10, 5);
        panel.add(login, gbc);

        login.addActionListener(e -> showMoodPopup());
        showPanel(panel, "🔐 LOCO - Login");
    }

    // --- Mood Selection Popup (Refactored for better appearance) ---
    static void showMoodPopup() {
        JDialog moodDialog = new JDialog(mainFrame, "🛍️ Mood-Based Shopping", true);
        moodDialog.setSize(650, 400); // Increased size
        moodDialog.setLayout(new BorderLayout(20, 20)); // Add padding
        moodDialog.getContentPane().setBackground(MOOD_POPUP_BG_COLOR);
        moodDialog.setLocationRelativeTo(mainFrame);
        moodDialog.setResizable(false);

        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(25, 25, 25, 25)); // Padding for content

        JLabel msg = new JLabel("Pick your mood, and we'll surprise you!");
        msg.setFont(FONT_SUBTITLE);
        msg.setForeground(TEXT_COLOR_DARK);
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(msg);
        contentPanel.add(Box.createVerticalStrut(25));

        JPanel emojiButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15)); // More spacing
        emojiButtonPanel.setOpaque(false);

        // Define which moods from 'moods' array will be shown in the popup
        // These are the original 5 from the user's LOCOApp.java, mapped to our expanded moods array
        Mood[] popupMoods = {moods[0], moods[1], moods[2], moods[3], moods[4]}; // Happy, Excited, Sad, Tired, Angry

        for (int i = 0; i < popupMoods.length; i++) {
            Mood currentMood = popupMoods[i];
            JButton btn = new JButton();
            if (currentMood.imageIcon != null) {
                btn.setIcon(currentMood.imageIcon);
            } else {
                btn.setText(currentMood.emoji);
                btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28)); // Fallback emoji font
            }
            btn.setPreferredSize(new Dimension(80, 80)); // Larger buttons
            btn.setBackground(ACCENT_PINK_CARD);
            btn.setOpaque(true);
            btn.setBorder(BorderFactory.createLineBorder(PRIMARY_PINK, 2, true));
            btn.setToolTipText(currentMood.name);
            btn.setFocusPainted(false);
            btn.setUI(new StyledButtonUI()); // Apply custom button UI for hover

            int index = i; // For lambda expression
            btn.addActionListener(e -> {
                // Product suggestions, mapping from the popupMoods array
                String[] suggestionsForMood;
                switch (index) {
                    case 0: suggestionsForMood = new String[]{ "Casual T-Shirt", "Denim Jeans", "White Sneakers", "Cute Cap", "Wrist Watch" }; break; // Happy
                    case 1: suggestionsForMood = new String[]{ "Smartphone", "Wireless Earbuds", "Smartwatch", "Gaming Headset", "Drone" }; break; // Excited
                    case 2: suggestionsForMood = new String[]{ "Plushie", "Scented Candle", "Chocolates", "Self-Care Book", "Hot Cocoa Mug" }; break; // Sad
                    case 3: suggestionsForMood = new String[]{ "Comfy Pajamas", "Soft Blanket", "Cozy Socks", "Warm Beverage Mug", "Eye Mask" }; break; // Tired
                    case 4: suggestionsForMood = new String[]{ "Stress Ball Set", "Punching Bag", "Heavy Metal Playlist", "Voodoo Doll", "Therapy Session Gift Card" }; break; // Angry
                    default: suggestionsForMood = new String[]{ "No specific suggestions for this mood." }; break;
                }

                StringBuilder s = new StringBuilder("🎁 Your Mood Picks:\n\n");
                for (String product : suggestionsForMood) {
                    s.append("• ").append(product).append("\n");
                }
                JOptionPane.showMessageDialog(moodDialog, s.toString(), "Products for " + currentMood.name, JOptionPane.PLAIN_MESSAGE);
            });
            emojiButtonPanel.add(btn);
        }
        contentPanel.add(emojiButtonPanel);
        contentPanel.add(Box.createVerticalStrut(25));

        JButton skip = new JButton("Explore All Products 🚀");
        skip.setFont(FONT_BUTTON);
        skip.setPreferredSize(new Dimension(250, 45)); // Larger skip button
        skip.setBackground(BUTTON_LOGIN_BG);
        skip.setForeground(BG_WHITE);
        skip.setBorder(BorderFactory.createLineBorder(BUTTON_LOGIN_BG.darker(), 2, true));
        skip.setFocusPainted(false);
        skip.setUI(new StyledButtonUI());
        skip.setAlignmentX(Component.CENTER_ALIGNMENT);
        skip.addActionListener(e -> {
            moodDialog.dispose();
            showMainShoppingApp(); // Go to the main tabbed shopping app
        });
        contentPanel.add(skip);

        moodDialog.add(contentPanel, BorderLayout.CENTER);
        moodDialog.setVisible(true);
    }

    // --- Unified Main Shopping App (Tabbed Interface) ---
    static void showMainShoppingApp() {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(BG_WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(40, 40, 40, 40) // Increased padding
        ));
        cardPanel.setPreferredSize(new Dimension(800, 650)); // Adjusted card size
        cardPanel.setMaximumSize(new Dimension(900, 700));
        cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(FONT_SUBTITLE);
        tabs.setForeground(TEXT_COLOR_DARK);
        tabs.setBackground(BG_WHITE);
        tabs.setOpaque(true);
        tabs.addTab("Mood Shopping", createMoodPanel()); // Pass frame
        tabs.addTab("Browse All", createBrowsePanel());   // Pass frame
        cardPanel.add(tabs);

        JPanel mainContentPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout for centering
        mainContentPanel.setBackground(LIGHT_PINK_BG);
        mainContentPanel.add(cardPanel);

        showPanel(mainContentPanel, "LOCO Mood Shopping"); // Display the main shopping interface
    }


    // --- Mood Shopping Panel (from original Main.java, adapted) ---
    private static JPanel createMoodPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Select Your Mood 😁");
        title.setFont(FONT_HEADER);
        title.setForeground(PRIMARY_PINK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createVerticalStrut(20));

        JPanel moodButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        moodButtonPanel.setOpaque(false);
        moodButtonPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        ButtonGroup moodGroup = new ButtonGroup();
        JToggleButton[] moodBtns = new JToggleButton[moods.length];

        for (int i = 0; i < moods.length; i++) {
            Mood currentMood = moods[i];
            moodBtns[i] = new JToggleButton();
            if (currentMood.imageIcon != null) {
                moodBtns[i].setIcon(currentMood.imageIcon);
            } else {
                moodBtns[i].setText(currentMood.emoji);
                moodBtns[i].setFont(new Font("Segoe UI Emoji", Font.PLAIN, 38)); // Fallback emoji font
            }
            moodBtns[i].setPreferredSize(new Dimension(70, 70)); // Consistent size with popup buttons
            moodBtns[i].setFocusPainted(false);
            moodBtns[i].setBackground(ACCENT_PINK_CARD);
            moodBtns[i].setOpaque(true);
            moodBtns[i].setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1, true));
            moodBtns[i].setToolTipText(currentMood.name);
            moodBtns[i].setUI(new StyledToggleButtonUI()); // Custom UI for hover/selection

            moodGroup.add(moodBtns[i]);
            moodButtonPanel.add(moodBtns[i]);
        }
        panel.add(moodButtonPanel);
        panel.add(Box.createVerticalStrut(20));

        JLabel recLabel = new JLabel("Recommended Products:");
        recLabel.setFont(FONT_SUBTITLE);
        recLabel.setForeground(TEXT_COLOR_DARK);
        recLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(recLabel);
        panel.add(Box.createVerticalStrut(15));

        JPanel productCardPanel = new JPanel();
        productCardPanel.setOpaque(false);
        productCardPanel.setLayout(new BoxLayout(productCardPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(productCardPanel);
        scrollPane.setPreferredSize(new Dimension(700, 300));
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scrollPane);

        // Mood selection logic
        for (int i = 0; i < moods.length; i++) {
            int idx = i;
            moodBtns[i].addActionListener(e -> {
                productCardPanel.removeAll();
                boolean anyMatch = false;
                for (Product p : allProducts) {
                    if (p.mood == moods[idx]) {
                        productCardPanel.add(createProductCard(p));
                        productCardPanel.add(Box.createVerticalStrut(15));
                        anyMatch = true;
                    }
                }
                if (!anyMatch) {
                    JLabel noResultLabel = new JLabel("No products found for this mood yet.");
                    noResultLabel.setFont(FONT_TEXT_FIELD);
                    noResultLabel.setForeground(TEXT_COLOR_LIGHT);
                    noResultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    productCardPanel.add(Box.createVerticalStrut(50));
                    productCardPanel.add(noResultLabel);
                }
                productCardPanel.revalidate();
                productCardPanel.repaint();
            });
        }
        moodBtns[0].setSelected(true);
        moodBtns[0].doClick();

        return panel;
    }

    // --- Browse All Panel (from original Main.java, adapted) ---
    private static JPanel createBrowsePanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterPanel.setOpaque(false);
        filterPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel searchLabel = new JLabel("🔍");
        searchLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        filterPanel.add(searchLabel);
        JTextField searchField = new JTextField(15);
        searchField.setFont(FONT_TEXT_FIELD);
        searchField.setPreferredSize(new Dimension(180, 35));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(5, 8, 5, 8)
        ));
        filterPanel.add(searchField);

        filterPanel.add(Box.createHorizontalStrut(15));
        filterPanel.add(new JLabel("Category:"));
        JComboBox<String> categoryBox = new JComboBox<>();
        categoryBox.addItem("All Categories");
        for (String cat : categories) categoryBox.addItem(cat);
        categoryBox.setFont(FONT_TEXT_FIELD);
        filterPanel.add(categoryBox);

        filterPanel.add(Box.createHorizontalStrut(15));
        filterPanel.add(new JLabel("Color:"));
        JComboBox<String> colorBox = new JComboBox<>();
        colorBox.addItem("All Colors");
        for (String c : colors) colorBox.addItem(c);
        colorBox.setFont(FONT_TEXT_FIELD);
        filterPanel.add(colorBox);

        filterPanel.add(Box.createHorizontalStrut(15));
        filterPanel.add(new JLabel("Size:"));
        JComboBox<String> sizeBox = new JComboBox<>();
        sizeBox.addItem("All Sizes");
        for (String s : sizes) sizeBox.addItem(s);
        sizeBox.setFont(FONT_TEXT_FIELD);
        filterPanel.add(sizeBox);

        filterPanel.add(Box.createHorizontalStrut(15));
        JLabel priceLabel = new JLabel("Price:");
        JTextField minPrice = createPriceTextField();
        JTextField maxPrice = createPriceTextField();
        filterPanel.add(priceLabel);
        filterPanel.add(minPrice);
        filterPanel.add(new JLabel("-"));
        filterPanel.add(maxPrice);

        panel.add(filterPanel);
        panel.add(Box.createVerticalStrut(10));

        JPanel productCardPanel = new JPanel();
        productCardPanel.setOpaque(false);
        productCardPanel.setLayout(new BoxLayout(productCardPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(productCardPanel);
        scrollPane.setPreferredSize(new Dimension(700, 380));
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scrollPane);

        Runnable filter = () -> {
            String search = searchField.getText().trim().toLowerCase();
            String cat = (String) categoryBox.getSelectedItem();
            String color = (String) colorBox.getSelectedItem();
            String size = (String) sizeBox.getSelectedItem();
            String minP = minPrice.getText().trim();
            String maxP = maxPrice.getText().trim();
            productCardPanel.removeAll();
            boolean anyMatch = false;
            for (Product p : allProducts) {
                boolean match = true;
                if (!search.isEmpty() && (!p.name.toLowerCase().contains(search) && !p.description.toLowerCase().contains(search))) match = false;
                if (!cat.equals("All Categories") && !p.category.equals(cat)) match = false;
                if (!color.equals("All Colors") && !p.color.equals(color)) match = false;
                if (!size.equals("All Sizes") && !p.size.equals(size)) match = false;
                if (!minP.isEmpty()) {
                    try {
                        if (p.price < Double.parseDouble(minP)) match = false;
                    } catch (NumberFormatException ignored) {}
                }
                if (!maxP.isEmpty()) {
                    try {
                        if (p.price > Double.parseDouble(maxP)) match = false;
                    } catch (NumberFormatException ignored) {}
                }

                if (match) {
                    productCardPanel.add(createProductCard(p));
                    productCardPanel.add(Box.createVerticalStrut(15));
                    anyMatch = true;
                }
            }
            if (!anyMatch) {
                JLabel noResultLabel = new JLabel("No products found matching your criteria.");
                noResultLabel.setFont(FONT_TEXT_FIELD);
                noResultLabel.setForeground(TEXT_COLOR_LIGHT);
                noResultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                productCardPanel.add(Box.createVerticalStrut(50));
                productCardPanel.add(noResultLabel);
            }
            productCardPanel.revalidate();
            productCardPanel.repaint();
        };

        searchField.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) { filter.run(); } });
        categoryBox.addActionListener(e -> filter.run());
        colorBox.addActionListener(e -> filter.run());
        sizeBox.addActionListener(e -> filter.run());
        minPrice.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) { filter.run(); } });
        maxPrice.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) { filter.run(); } });

        filter.run();

        return panel;
    }

    // Helper to create styled price text fields
    private static JTextField createPriceTextField() {
        JTextField field = new JTextField(4);
        field.setFont(FONT_TEXT_FIELD);
        field.setPreferredSize(new Dimension(60, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(5, 5, 5, 5)
        ));
        return field;
    }

    // Product card (from original Main.java, adapted)
    private static JPanel createProductCard(Product p) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(15, 0));
        card.setBackground(ACCENT_PINK_CARD);
        card.setBorder(new LineBorder(PRIMARY_PINK, 2, true));
        card.setPreferredSize(new Dimension(650, 80));
        card.setMaximumSize(new Dimension(700, 80));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel emojiPanel = new JPanel(new GridBagLayout());
        emojiPanel.setOpaque(false);
        emojiPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
        JLabel emoji = new JLabel(p.emoji);
        emoji.setFont(FONT_EMOJI_CARD);
        emojiPanel.add(emoji);
        card.add(emojiPanel, BorderLayout.WEST);

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBorder(new EmptyBorder(10, 0, 10, 0));
        JLabel name = new JLabel(p.name + "   ₹" + String.format("%.2f", p.price));
        name.setFont(FONT_PRODUCT_NAME);
        name.setForeground(TEXT_COLOR_DARK);
        JLabel desc = new JLabel("<html><span style='color:" + toHex(TEXT_COLOR_LIGHT) + ";'>" + p.description + "</span></html>");
        desc.setFont(FONT_PRODUCT_DESC);
        info.add(name);
        info.add(Box.createVerticalStrut(5));
        info.add(desc);
        card.add(info, BorderLayout.CENTER);

        JPanel metaInfo = new JPanel();
        metaInfo.setOpaque(false);
        metaInfo.setLayout(new BoxLayout(metaInfo, BoxLayout.Y_AXIS));
        metaInfo.setBorder(new EmptyBorder(10, 0, 10, 10));
        JLabel moodLabel = new JLabel("<html><span style='font-size:12px; color:" + toHex(PRIMARY_PINK) + ";'>" + p.mood.emoji + " " + p.mood.name + "</span></html>");
        moodLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        JLabel categoryLabel = new JLabel("<html><span style='font-size:12px; color:" + toHex(TEXT_COLOR_LIGHT) + ";'>" + p.category + "</span></html>");
        categoryLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        metaInfo.add(moodLabel);
        metaInfo.add(Box.createVerticalStrut(2));
        metaInfo.add(categoryLabel);
        card.add(metaInfo, BorderLayout.EAST);

        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                showProductDialog(p); // No need to pass frame, it's a global mainFrame now
            }
            public void mouseEntered(MouseEvent e) {
                card.setBackground(HOVER_PINK);
                card.setBorder(new LineBorder(PRIMARY_PINK.darker(), 2, true));
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(ACCENT_PINK_CARD);
                card.setBorder(new LineBorder(PRIMARY_PINK, 2, true));
            }
        });

        return card;
    }

    // Helper to convert Color to Hex for HTML
    private static String toHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    // Product details dialog (from original Main.java, adapted)
    private static void showProductDialog(Product p) {
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        dialogPanel.setBackground(BG_WHITE);

        JLabel emojiLabel = new JLabel(p.emoji);
        emojiLabel.setFont(FONT_EMOJI_DIALOG);
        emojiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dialogPanel.add(emojiLabel);
        dialogPanel.add(Box.createVerticalStrut(15));

        JLabel nameLabel = new JLabel(p.name);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 28)); // Specific font for dialog name
        nameLabel.setForeground(PRIMARY_PINK);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dialogPanel.add(nameLabel);
        dialogPanel.add(Box.createVerticalStrut(10));

        dialogPanel.add(createDetailLabel("Category:", p.category));
        dialogPanel.add(createDetailLabel("Color:", p.color));
        dialogPanel.add(createDetailLabel("Size:", p.size));
        dialogPanel.add(createDetailLabel("Price:", "₹" + String.format("%.2f", p.price)));
        dialogPanel.add(createDetailLabel("Description:", p.description));
        dialogPanel.add(createDetailLabel("Mood:", p.mood.emoji + " " + p.mood.name));

        JOptionPane.showMessageDialog(mainFrame, // Use mainFrame as parent
            dialogPanel,
            "Product Details",
            JOptionPane.PLAIN_MESSAGE
        );
    }

    // Helper to create consistent detail labels for dialog
    private static JPanel createDetailLabel(String title, String value) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setOpaque(false);
        JLabel titleLabel = new JLabel("<html><b>" + title + "</b></html>");
        titleLabel.setFont(FONT_TEXT_FIELD); // Consistent font
        titleLabel.setForeground(TEXT_COLOR_DARK);
        JLabel valueLabel = new JLabel(" " + value);
        valueLabel.setFont(FONT_TEXT_FIELD); // Consistent font
        valueLabel.setForeground(TEXT_COLOR_LIGHT);
        panel.add(titleLabel);
        panel.add(valueLabel);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }

    // --- Custom Button UI for hover/pressed effects ---
    static class StyledButtonUI extends BasicButtonUI {
        @Override
        public void paint(Graphics g, JComponent c) {
            JButton b = (JButton) c;
            ButtonModel model = b.getModel();
            Color originalBg = b.getBackground();

            if (model.isRollover()) {
                g.setColor(HOVER_PINK); // Hover color
                g.fillRect(0, 0, b.getWidth(), b.getHeight());
            } else if (model.isPressed()) {
                g.setColor(PRIMARY_PINK.darker()); // Pressed color
                g.fillRect(0, 0, b.getWidth(), b.getHeight());
            } else {
                g.setColor(originalBg);
                g.fillRect(0, 0, b.getWidth(), b.getHeight());
            }
            super.paint(g, c);
        }
    }

    // Custom Toggle Button UI for hover/selected effects
    static class StyledToggleButtonUI extends BasicButtonUI {
        @Override
        public void paint(Graphics g, JComponent c) {
            JToggleButton b = (JToggleButton) c;
            ButtonModel model = b.getModel();
            Color originalBg = b.getBackground();

            if (model.isSelected()) {
                g.setColor(PRIMARY_PINK); // Selected color
                g.fillRect(0, 0, b.getWidth(), b.getHeight());
                b.setForeground(BG_WHITE); // White text when selected
            } else if (model.isRollover()) {
                g.setColor(HOVER_PINK); // Hover color
                g.fillRect(0, 0, b.getWidth(), b.getHeight());
                b.setForeground(TEXT_COLOR_DARK); // Default text color on hover
            } else {
                g.setColor(originalBg);
                g.fillRect(0, 0, b.getWidth(), b.getHeight());
                b.setForeground(TEXT_COLOR_DARK); // Default text color
            }
            super.paint(g, c);
        }
    }
}