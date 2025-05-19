package maven_robots.gui.mainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class ProfilePickerDialog extends JDialog {

    private static String profilesFileName;
    private static final int MAX_PROFILES = 10;

    private JList<String> profileList;
    private DefaultListModel<String> listModel;
    private JScrollPane scrollPane;

    private JButton selectButton;
    private JButton newProfileButton;
    private JButton deleteButton;
    private JButton cancelSelectionButton;

    private JPanel newProfilePanel;
    private JTextField newProfileTextField;
    private JButton saveNewProfileButton;
    private JButton cancelCreationButton;

    private String selectedProfileName = null;
    private boolean isNewProfileSelectedAction = false;
    private final String path;

    public ProfilePickerDialog(Frame owner, String path) {
        super(owner, "Выберите или создайте профиль", true);

        this.path = path + "/profiles";

        ProfilePickerDialog.profilesFileName =  this.path + "/profiles.txt";
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(600, 600));
        setResizable(false);

        List<String> profiles = loadProfilesFromFile();
        listModel = new DefaultListModel<>();
        for (String profile : profiles) {
            listModel.addElement(profile);
        }

        profileList = new JList<>(listModel);

        profileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        profileList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                deleteButton.setEnabled(true);
                selectButton.setEnabled(true);
            }
        });

        profileList.setLayoutOrientation(JList.VERTICAL);

        scrollPane = new JScrollPane(profileList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Существующие профили"));

        selectButton = new JButton("Выбрать профиль");
        newProfileButton = new JButton("Создать новый");
        deleteButton = new JButton("Удалить профиль");
        cancelSelectionButton = new JButton("Отмена");

        selectButton.setEnabled(false);
        deleteButton.setEnabled(false);

        if (listModel.size() >= MAX_PROFILES) {
            newProfileButton.setEnabled(false);
            newProfileButton.setToolTipText("Достигнуто максимальное количество профилей (" + MAX_PROFILES + ")");
        }

        newProfilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        newProfileTextField = new JTextField(20);
        newProfilePanel.add(new JLabel("Имя профиля:"));
        newProfilePanel.add(newProfileTextField);
        newProfilePanel.setVisible(false);

        saveNewProfileButton = new JButton("Сохранить новый");
        cancelCreationButton = new JButton("Отмена создания");
        saveNewProfileButton.setVisible(false);
        cancelCreationButton.setVisible(false);

        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel listDisplayPanel = new JPanel(new BorderLayout());
        listDisplayPanel.add(scrollPane, BorderLayout.CENTER);

        contentPane.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(selectButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(newProfileButton);
        buttonPanel.add(cancelSelectionButton);

        buttonPanel.add(saveNewProfileButton);
        buttonPanel.add(cancelCreationButton);


        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(newProfilePanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        setContentPane(contentPane);

        selectButton.addActionListener(this::onSelectProfile);

        newProfileButton.addActionListener(this::onNewProfile);

        deleteButton.addActionListener(this::onDelete);

        cancelSelectionButton.addActionListener(this::onCancelSelection);

        saveNewProfileButton.addActionListener(this::onSaveNewProfile);

        cancelCreationButton.addActionListener(this::onCancelCreation);

        profileList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectButton.setEnabled(profileList.getSelectedIndex() != -1);
            }
        });

        profileList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2 && selectButton.isEnabled() && profileList.getSelectedIndex() != -1) {
                    onSelectProfile(null);
                }
            }
        });

        pack();
        setLocationRelativeTo(owner);
    }

    private void showSelectionMode() {
        setTitle("Выберите или создайте профиль");
        scrollPane.setVisible(true);
        newProfilePanel.setVisible(false);

        selectButton.setVisible(true);
        newProfileButton.setVisible(true);
        cancelSelectionButton.setVisible(true);
        deleteButton.setVisible(true);

        saveNewProfileButton.setVisible(false);
        cancelCreationButton.setVisible(false);

        newProfileTextField.setText("");

        selectButton.setEnabled(profileList.getSelectedIndex() != -1);
        if (listModel.size() < MAX_PROFILES) {
            newProfileButton.setEnabled(true);
            newProfileButton.setToolTipText(null);
        } else {
            newProfileButton.setEnabled(false);
            newProfileButton.setToolTipText("Достигнуто максимальное количество профилей (" + MAX_PROFILES + ")");
        }

        getContentPane().revalidate();
        getContentPane().repaint();
    }

    private void showCreationMode() {
        if (listModel.size() >= MAX_PROFILES) {
            JOptionPane.showMessageDialog(this,
                    "Достигнуто максимальное количество профилей (" + MAX_PROFILES + "). Невозможно создать новый.",
                    "Лимит профилей",
                    JOptionPane.WARNING_MESSAGE);
            showSelectionMode();
            return;
        }

        setTitle("Создать новый профиль");
        scrollPane.setVisible(false);
        newProfilePanel.setVisible(true);

        selectButton.setVisible(false);
        newProfileButton.setVisible(false);
        cancelSelectionButton.setVisible(false);
        deleteButton.setVisible(false);

        saveNewProfileButton.setVisible(true);
        cancelCreationButton.setVisible(true);

        newProfileTextField.setText("");
        newProfileTextField.requestFocusInWindow();

        getContentPane().revalidate();
        getContentPane().repaint();
    }

    private void onSelectProfile(ActionEvent e) {
        if (profileList.getSelectedIndex() != -1) {
            selectedProfileName = profileList.getSelectedValue();
            isNewProfileSelectedAction = false;
            dispose();
        }
    }

    private void onNewProfile(ActionEvent e) {
        isNewProfileSelectedAction = true;
        showCreationMode();
    }

    private void onDelete(ActionEvent event) {
        String folderPath = path + "/" + profileList.getSelectedValue();
        Path path = Paths.get(folderPath);

        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
            System.out.println("Папка '" + folderPath + "' и её содержимое успешно удалены.");
        } catch (IOException e) {
            System.err.println("Ошибка при удалении папки: " + e.getMessage());
        }

        try (BufferedReader reader = Files.newBufferedReader(
                Paths.get(this.path + "/profiles.txt"), StandardCharsets.UTF_8
        );
             BufferedWriter writer = Files.newBufferedWriter(
                 Paths.get(this.path + "/profiles_temp.txt"), StandardCharsets.UTF_8)
        ) {

            String line;
            String deletedProfile = profileList.getSelectedValue();

            while ((line = reader.readLine()) != null) {
                if (!line.equals(deletedProfile)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при обновлении файла profiles.txt: " + e.getMessage());
            return;
        }

        try{
            Files.move(
                Paths.get(this.path + "/profiles_temp.txt"), Paths.get(this.path + "/profiles.txt"),
                StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e){
            System.err.println("Ошибка при переименовании файла profiles_temp.txt: " + e.getMessage());
        }

        selectedProfileName = null;
        listModel.removeElement(profileList.getSelectedValue());


    }

    private void onCancelSelection(ActionEvent e) {
        selectedProfileName = null;
        isNewProfileSelectedAction = false;
        dispose();
    }

    private void onSaveNewProfile(ActionEvent e) {
        String newName = newProfileTextField.getText().trim();

        if (newName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Имя профиля не может быть пустым.",
                    "Ошибка ввода",
                    JOptionPane.WARNING_MESSAGE);
            newProfileTextField.requestFocusInWindow();
            return;
        }

        if (listModel.contains(newName)) {
            JOptionPane.showMessageDialog(this,
                    "Профиль с именем '" + newName + "' уже существует.",
                    "Ошибка ввода",
                    JOptionPane.WARNING_MESSAGE);
            newProfileTextField.requestFocusInWindow();
            return;
        }

        listModel.addElement(newName);
        saveProfileToFile(newName);

        selectedProfileName = newName;
        isNewProfileSelectedAction = false;

        dispose();
    }

    private void onCancelCreation(ActionEvent e) {
        isNewProfileSelectedAction = false;
        showSelectionMode();
    }

    private List<String> loadProfilesFromFile() {
        List<String> profiles = new ArrayList<>();
        File file = new File(profilesFileName);

        if (!file.exists()) {
            System.out.println("Файл профилей '" + profilesFileName + "' не найден.");
            return profiles;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();
                if (!trimmedLine.isEmpty()) {
                    profiles.add(trimmedLine);
                }
            }
        } catch (IOException ex) {
            System.err.println("Ошибка при чтении файла профилей: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Не удалось загрузить профили: " + ex.getMessage(),
                    "Ошибка загрузки",
                    JOptionPane.ERROR_MESSAGE);
        }
        return profiles;
    }

    private void saveProfileToFile(String profileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(profilesFileName, true))) {
            writer.println(profileName);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении профиля '" + profileName + "': " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Не удалось сохранить новый профиль: " + e.getMessage(),
                    "Ошибка сохранения",
                    JOptionPane.ERROR_MESSAGE);

            listModel.removeElement(profileName);
        }
    }

    public String getSelectedProfileName() {
        return selectedProfileName;
    }

    public boolean isNewProfileSelectedAction() {
        return isNewProfileSelectedAction;
    }

    public boolean isCancelled() {
        return selectedProfileName == null;
    }

}
