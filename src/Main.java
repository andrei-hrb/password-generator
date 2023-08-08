import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        JLabel title = new JLabel("Password Generator");
        title.setBounds( 10, 10, 380, 20);
        title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
        frame.add(title);

        JLabel subtitle = new JLabel("by @hirbu");
        subtitle.setBounds(10, 35, 380, 20);
        subtitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        subtitle.setForeground(new Color(128, 128, 128));
        frame.add(subtitle);

        JLabel use = new JLabel("Use:");
        use.setBounds(10, 70, 380, 20);
        frame.add(use);

        JCheckBox alphabeticCheckBox = new JCheckBox("Alphabetic Characters");
        alphabeticCheckBox.setBounds(45, 70, 300, 20);
        alphabeticCheckBox.setSelected(true);
        frame.add(alphabeticCheckBox);

        JCheckBox numericCheckBox = new JCheckBox("Numeric Characters");
        numericCheckBox.setBounds(45, 100, 380, 20);
        numericCheckBox.setSelected(true);
        frame.add(numericCheckBox);

        JCheckBox specialCheckBox = new JCheckBox("Special Characters");
        specialCheckBox.setBounds(45, 130, 380, 20);
        specialCheckBox.setSelected(true);
        frame.add(specialCheckBox);

        JSlider sizeSlider = new JSlider();
        sizeSlider.setBounds(5, 184, 390, 30);
        sizeSlider.setMinimum(6);
        sizeSlider.setMaximum(50);
        sizeSlider.setValue(32);

        JLabel sizeSliderLabel = new JLabel("Size of the password " + 32);
        sizeSliderLabel.setBounds(10, 160, 380, 30);
        frame.add(sizeSliderLabel);

        sizeSlider.addChangeListener(e -> sizeSliderLabel.setText("Size of the password " + sizeSlider.getValue()));
        frame.add(sizeSlider);

        JTextArea passwordTextArea = new JTextArea("Your password will show here!");
        passwordTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        passwordTextArea.setBounds(10, 280, 380, 20);
        passwordTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
        passwordTextArea.setEditable(false);
        frame.add(passwordTextArea);

        JButton generatePasswordButton = new JButton("Generate Password");
        generatePasswordButton.setBounds(10, 220, 380, 40);
        generatePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean useAlphabetic = alphabeticCheckBox.isSelected();
                boolean useNumeric = numericCheckBox.isSelected();
                boolean useSpecial = specialCheckBox.isSelected();
                int sizeOfPassword = sizeSlider.getValue();

                if (!useAlphabetic && !useNumeric && !useSpecial) {
                    JOptionPane.showMessageDialog(frame, "Please select at least one type of characters!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    passwordTextArea.setText(generatePassword(useAlphabetic, useNumeric, useSpecial, sizeOfPassword));
                }
            }
        });
        frame.add(generatePasswordButton);

        frame.setSize(400, 330);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private static String generatePassword(boolean useAlphabetic, boolean useNumeric, boolean useSpecial, int sizeOfPassword) {
        StringBuilder symbolsBuilder = new StringBuilder();

        if (useAlphabetic) {
            for (char lower = 'a'; lower <= 'z'; lower++) {
                symbolsBuilder.append(lower);
            }

            for (char upper = 'A'; upper <= 'Z'; upper++) {
                symbolsBuilder.append(upper);
            }
        }

        if (useNumeric) {
            for (char number = '0'; number <= '9'; number++) {
                symbolsBuilder.append(number);
            }
        }

        if (useSpecial) {
            for (char special : "~`!@#$%^&*()-_+={}[]|\\/:;\"'<>,.?".toCharArray()) {
                symbolsBuilder.append(special);
            }
        }

        String symbols = symbolsBuilder.toString();

        try {
            SecureRandom random = SecureRandom.getInstanceStrong();

            StringBuilder passwordBuilder = new StringBuilder(sizeOfPassword);
            for(int i = 0; i < sizeOfPassword; i++) {
                int randomIndex = random.nextInt(symbols.length());
                passwordBuilder.append(symbols.charAt(randomIndex));
            }

            return passwordBuilder.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }
}