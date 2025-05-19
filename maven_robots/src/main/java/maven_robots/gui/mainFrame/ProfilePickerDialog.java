package maven_robots.gui.mainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ProfilePickerDialog extends JDialog {

    private static String profilesFileName;
    private static final int MAX_PROFILES = 10; // Ограничение на количество профилей

    private JList<String> profileList;
    private DefaultListModel<String> listModel; // Изменим на Vector для удобства с JList
    private JScrollPane scrollPane;

    // Элементы для режима выбора
    private JButton selectButton;
    private JButton newProfileButton;
    private JButton cancelSelectionButton; // Переименовали для ясности

    // Элементы для режима создания нового профиля
    private JPanel newProfilePanel; // Панель для полей ввода нового профиля
    private JTextField newProfileTextField;
    private JButton saveNewProfileButton;
    private JButton cancelCreationButton;

    private String selectedProfileName = null;
    private boolean isNewProfileSelectedAction = false; // Было ли выбрано действие "создать новый"

    // Конструктор
    public ProfilePickerDialog(Frame owner) {
        super(owner, "Выберите или создайте профиль", true); // Делаем диалог модальным

        String profilesFileName;

        try {
            String classPath = this
                    .getClass()
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()
                    .getPath()
                    .replace("out/production", "src")
                    .replace("/main/", "/main/java/");
            profilesFileName = classPath.substring(1) + "maven_robots/data/profiles/profiles.txt";


        } catch (URISyntaxException e) {
            e.printStackTrace();
            profilesFileName = "profiles.txt";
        }

        ProfilePickerDialog.profilesFileName = profilesFileName;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(600, 600));
        setResizable(false);

        // --- Загрузка профилей ---
        List<String> profiles = loadProfilesFromFile();
        listModel = new DefaultListModel<>();
        for (String profile : profiles) {
            listModel.addElement(profile);
        }

        // --- Создание UI элементов ---
        profileList = new JList<>(listModel);
        profileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        profileList.setLayoutOrientation(JList.VERTICAL);

        scrollPane = new JScrollPane(profileList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Существующие профили"));

        // Элементы для режима выбора
        selectButton = new JButton("Выбрать профиль");
        newProfileButton = new JButton("Создать новый");
        cancelSelectionButton = new JButton("Отмена");

        // Изначально кнопка "Выбрать" отключена, если список пуст или ничего не выбрано
        selectButton.setEnabled(!listModel.isEmpty());

        // Проверяем ограничение на количество профилей
        if (listModel.size() >= MAX_PROFILES) {
            newProfileButton.setEnabled(false);
            newProfileButton.setToolTipText("Достигнуто максимальное количество профилей (" + MAX_PROFILES + ")");
        }


        // Элементы для режима создания нового профиля
        newProfilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Используем FlowLayout для метки и поля
        newProfileTextField = new JTextField(20); // Ширина поля
        newProfilePanel.add(new JLabel("Имя профиля:"));
        newProfilePanel.add(newProfileTextField);
        // Скрываем панель изначально
        newProfilePanel.setVisible(false);

        saveNewProfileButton = new JButton("Сохранить новый");
        cancelCreationButton = new JButton("Отмена создания");
        // Скрываем кнопки создания изначально
        saveNewProfileButton.setVisible(false);
        cancelCreationButton.setVisible(false);


        // --- Размещение элементов ---
        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Используем центральную панель, которая будет показывать либо список, либо поле ввода
        JPanel centerPanel = new JPanel(new CardLayout()); // CardLayout не нужен, просто управляем видимостью
        JPanel listDisplayPanel = new JPanel(new BorderLayout()); // Панель для списка
        listDisplayPanel.add(scrollPane, BorderLayout.CENTER);

        // Добавляем обе панели в центральную область, управляя их видимостью
        // Хотя можно просто добавлять/удалять scrollPane или newProfilePanel в центр contentPane
        // Давайте попробуем управлять видимостью в рамках одного места в BorderLayout.CENTER
        contentPane.add(scrollPane, BorderLayout.CENTER); // Изначально показываем список
        // Панель нового профиля будет временно заменять scrollPane в CENTER при создании

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Кнопки справа
        buttonPanel.add(selectButton); // Кнопки выбора
        buttonPanel.add(newProfileButton);
        buttonPanel.add(cancelSelectionButton);

        buttonPanel.add(saveNewProfileButton); // Кнопки создания
        buttonPanel.add(cancelCreationButton);


        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Добавляем панель нового профиля, но она будет управляться отдельно
        // Возможно, лучше разместить newProfilePanel над buttonPanel?
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(newProfilePanel, BorderLayout.CENTER); // Панель ввода внизу
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH); // Кнопки под ней
        contentPane.add(bottomPanel, BorderLayout.SOUTH); // Добавляем в основной контент
        contentPane.add(scrollPane, BorderLayout.CENTER); // Список в центре

        setContentPane(contentPane);


        // --- Добавление слушателей событий ---

        // Слушатель для кнопки "Выбрать"
        selectButton.addActionListener(this::onSelectProfile);

        // Слушатель для кнопки "Создать новый"
        newProfileButton.addActionListener(this::onNewProfile);

        // Слушатель для кнопки "Отмена" (выбора)
        cancelSelectionButton.addActionListener(this::onCancelSelection);

        // Слушатель для кнопки "Сохранить новый"
        saveNewProfileButton.addActionListener(this::onSaveNewProfile);

        // Слушатель для кнопки "Отмена создания"
        cancelCreationButton.addActionListener(this::onCancelCreation);


        // Слушатель выбора элемента в списке (включает/отключает кнопку "Выбрать")
        profileList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Ждем завершения выбора
                selectButton.setEnabled(profileList.getSelectedIndex() != -1);
            }
        });

        // Слушатель двойного клика по элементу списка (аналогично нажатию "Выбрать")
        profileList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2 && selectButton.isEnabled() && profileList.getSelectedIndex() != -1) { // Двойной клик и кнопка включена
                    onSelectProfile(null); // Вызываем тот же обработчик, что и для кнопки
                }
            }
        });

        // --- Завершающие настройки окна ---
        pack();
        setLocationRelativeTo(owner);
    }

    // --- Переключение режимов UI ---

    private void showSelectionMode() {
        setTitle("Выберите или создайте профиль");
        scrollPane.setVisible(true);
        newProfilePanel.setVisible(false);

        selectButton.setVisible(true);
        newProfileButton.setVisible(true);
        cancelSelectionButton.setVisible(true);

        saveNewProfileButton.setVisible(false);
        cancelCreationButton.setVisible(false);

        newProfileTextField.setText(""); // Очищаем поле ввода нового профиля

        // Обновляем состояние кнопок выбора
        selectButton.setEnabled(profileList.getSelectedIndex() != -1);
        if (listModel.size() < MAX_PROFILES) {
            newProfileButton.setEnabled(true);
            newProfileButton.setToolTipText(null);
        } else {
            newProfileButton.setEnabled(false);
            newProfileButton.setToolTipText("Достигнуто максимальное количество профилей (" + MAX_PROFILES + ")");
        }


        // Перерисовываем панель для обновления видимости
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    private void showCreationMode() {
        // Проверяем еще раз лимит, если кнопка каким-то образом была кликнута при лимите
        if (listModel.size() >= MAX_PROFILES) {
            JOptionPane.showMessageDialog(this,
                    "Достигнуто максимальное количество профилей (" + MAX_PROFILES + "). Невозможно создать новый.",
                    "Лимит профилей",
                    JOptionPane.WARNING_MESSAGE);
            showSelectionMode(); // Возвращаемся в режим выбора
            return;
        }

        setTitle("Создать новый профиль");
        scrollPane.setVisible(false);
        newProfilePanel.setVisible(true);

        selectButton.setVisible(false);
        newProfileButton.setVisible(false);
        cancelSelectionButton.setVisible(false);

        saveNewProfileButton.setVisible(true);
        cancelCreationButton.setVisible(true);

        newProfileTextField.setText(""); // Убедимся, что поле ввода пустое
        newProfileTextField.requestFocusInWindow(); // Устанавливаем фокус на поле ввода

        // Перерисовываем панель для обновления видимости
        getContentPane().revalidate();
        getContentPane().repaint();
    }


    // --- Обработчики событий кнопок ---

    // Кнопка "Выбрать профиль" (в режиме выбора)
    private void onSelectProfile(ActionEvent e) {
        if (profileList.getSelectedIndex() != -1) {
            selectedProfileName = profileList.getSelectedValue();
            isNewProfileSelectedAction = false; // Не выбрано действие "создать новый"
            dispose(); // Закрываем диалог
        }
        // Валидация выбора уже происходит через enable/disable кнопки и проверку в MouseListener
    }

    // Кнопка "Создать новый" (в режиме выбора)
    private void onNewProfile(ActionEvent e) {
        isNewProfileSelectedAction = true; // Указываем, что было выбрано действие "создать новый"
        showCreationMode(); // Переключаемся в режим создания
    }

    // Кнопка "Отмена" (в режиме выбора)
    private void onCancelSelection(ActionEvent e) {
        selectedProfileName = null;
        isNewProfileSelectedAction = false;
        dispose(); // Закрываем диалог
    }

    // Кнопка "Сохранить новый" (в режиме создания)
    private void onSaveNewProfile(ActionEvent e) {
        String newName = newProfileTextField.getText().trim();

        // Валидация
        if (newName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Имя профиля не может быть пустым.",
                    "Ошибка ввода",
                    JOptionPane.WARNING_MESSAGE);
            newProfileTextField.requestFocusInWindow(); // Возвращаем фокус
            return; // Остаемся в режиме создания
        }

        if (listModel.contains(newName)) {
            JOptionPane.showMessageDialog(this,
                    "Профиль с именем '" + newName + "' уже существует.",
                    "Ошибка ввода",
                    JOptionPane.WARNING_MESSAGE);
            newProfileTextField.requestFocusInWindow(); // Возвращаем фокус
            return; // Остаемся в режиме создания
        }

        // Если валидация пройдена:
        listModel.addElement(newName); // Добавляем в модель списка
        saveProfileToFile(newName); // Сохраняем в файл

        selectedProfileName = newName; // Устанавливаем только что созданный профиль как выбранный
        isNewProfileSelectedAction = false; // Действие "создать новый" завершено, теперь профиль выбран

        dispose(); // Закрываем диалог
    }

    // Кнопка "Отмена создания" (в режиме создания)
    private void onCancelCreation(ActionEvent e) {
        isNewProfileSelectedAction = false; // Отменили действие создания
        showSelectionMode(); // Возвращаемся в режим выбора
    }

    // --- Метод для загрузки профилей из файла ---
    private List<String> loadProfilesFromFile() {
        List<String> profiles = new ArrayList<>();
        File file = new File(profilesFileName);

        if (!file.exists()) {
            System.out.println("Файл профилей '" + profilesFileName + "' не найден.");
            return profiles; // Возвращаем пустой список
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

    // --- Метод для сохранения нового профиля в файл ---
    // Предполагается, что новый профиль уже прошел валидацию и еще не в файле
    private void saveProfileToFile(String profileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(profilesFileName, true))) { // true для добавления в конец
            writer.println(profileName);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении профиля '" + profileName + "': " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Не удалось сохранить новый профиль: " + e.getMessage(),
                    "Ошибка сохранения",
                    JOptionPane.ERROR_MESSAGE);
            // В случае ошибки сохранения, возможно, нужно удалить профиль из listModel
            // listModel.removeElement(profileName);
            // или как-то иначе обработать ситуацию, когда профиль добавлен в UI, но не сохранен на диск.
            // Для простоты примера, просто выведем ошибку и продолжим.
        }
    }

    // --- Методы для получения результата ---

    /**
     * Возвращает имя выбранного профиля или null, если пользователь отменил выбор/создание.
     * Если был успешно создан новый профиль, возвращает его имя.
     */
    public String getSelectedProfileName() {
        return selectedProfileName;
    }

    public boolean isNewProfileSelectedAction() {
        return isNewProfileSelectedAction;
    }

    public boolean isCancelled() {
        return selectedProfileName == null; // Проверяем только, было ли установлено имя выбранного профиля
    }

}
