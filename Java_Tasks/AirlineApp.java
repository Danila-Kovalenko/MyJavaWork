import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Упрощенное консольное Java-приложение для управления пользователями и аэропортами
 * с использованием JDBC для взаимодействия с базой данных MySQL.
 */
public class AirlineApp {

    // Параметры подключения к базе данных
    private static final String URL = "jdbc:mysql://localhost:3306/AirlineDB?useSSL=false&serverTimezone=UTC";
    private static final String USER = "ваш_пользователь"; // Замените на вашего пользователя MySQL
    private static final String PASSWORD = "ваш_пароль";   // Замените на ваш пароль MySQL

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UsersDAO usersDAO = new UsersDAO();
        AirportsDAO airportsDAO = new AirportsDAO();

        int choice = -1;

        while (choice != 0) {
            printMenu();
            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addUser(scanner, usersDAO);
                        break;
                    case 2:
                        updateUser(scanner, usersDAO);
                        break;
                    case 3:
                        deleteUser(scanner, usersDAO);
                        break;
                    case 4:
                        searchUsers(scanner, usersDAO);
                        break;
                    case 5:
                        addAirport(scanner, airportsDAO);
                        break;
                    case 6:
                        searchAirports(scanner, airportsDAO);
                        break;
                    case 0:
                        System.out.println("Выход из приложения.");
                        break;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите число.");
            }
        }

        scanner.close();
    }

    /**
     * Выводит меню приложения.
     */
    private static void printMenu() {
        System.out.println("\n=== Airline Booking System ===");
        System.out.println("1. Добавить пользователя");
        System.out.println("2. Обновить пользователя");
        System.out.println("3. Удалить пользователя");
        System.out.println("4. Поиск пользователей");
        System.out.println("5. Добавить аэропорт");
        System.out.println("6. Поиск аэропортов");
        System.out.println("0. Выход");
        System.out.print("Выберите опцию: ");
    }

    /**
     * Добавляет нового пользователя.
     */
    private static void addUser(Scanner scanner, UsersDAO usersDAO) {
        System.out.println("\n--- Добавление Пользователя ---");
        System.out.print("Имя: ");
        String firstName = scanner.nextLine();
        System.out.print("Фамилия: ");
        String lastName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Телефон: ");
        String phone = scanner.nextLine();

        User user = new User(firstName, lastName, email, phone);
        usersDAO.addUser(user);
    }

    /**
     * Обновляет информацию о существующем пользователе.
     */
    private static void updateUser(Scanner scanner, UsersDAO usersDAO) {
        System.out.println("\n--- Обновление Пользователя ---");
        System.out.print("Введите ID пользователя для обновления: ");
        int userId;
        try {
            userId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ID.");
            return;
        }

        System.out.print("Новое имя: ");
        String firstName = scanner.nextLine();
        System.out.print("Новая фамилия: ");
        String lastName = scanner.nextLine();
        System.out.print("Новый Email: ");
        String email = scanner.nextLine();
        System.out.print("Новый телефон: ");
        String phone = scanner.nextLine();

        User user = new User(userId, firstName, lastName, email, phone);
        usersDAO.updateUser(user);
    }

    /**
     * Удаляет пользователя по его ID.
     */
    private static void deleteUser(Scanner scanner, UsersDAO usersDAO) {
        System.out.println("\n--- Удаление Пользователя ---");
        System.out.print("Введите ID пользователя для удаления: ");
        int userId;
        try {
            userId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ID.");
            return;
        }

        usersDAO.deleteUser(userId);
    }

    /**
     * Ищет пользователей по имени и/или фамилии.
     */
    private static void searchUsers(Scanner scanner, UsersDAO usersDAO) {
        System.out.println("\n--- Поиск Пользователей ---");
        System.out.print("Имя (или часть имени): ");
        String firstName = scanner.nextLine();
        System.out.print("Фамилия (или часть фамилии): ");
        String lastName = scanner.nextLine();

        List<User> users = usersDAO.searchUsers(firstName, lastName);

        if (users.isEmpty()) {
            System.out.println("Пользователи не найдены.");
        } else {
            System.out.println("\nНайденные Пользователи:");
            for (User user : users) {
                System.out.println(user);
            }
        }
    }

    /**
     * Добавляет новый аэропорт.
     */
    private static void addAirport(Scanner scanner, AirportsDAO airportsDAO) {
        System.out.println("\n--- Добавление Аэропорта ---");
        System.out.print("Название аэропорта: ");
        String name = scanner.nextLine();
        System.out.print("Город: ");
        String city = scanner.nextLine();
        System.out.print("Страна: ");
        String country = scanner.nextLine();
        System.out.print("Код аэропорта (IATA): ");
        String code = scanner.nextLine();

        Airport airport = new Airport(name, city, country, code);
        airportsDAO.addAirport(airport);
    }

    /**
     * Ищет аэропорты по названию, городу или коду.
     */
    private static void searchAirports(Scanner scanner, AirportsDAO airportsDAO) {
        System.out.println("\n--- Поиск Аэропортов ---");
        System.out.print("Название (или часть названия): ");
        String name = scanner.nextLine();
        System.out.print("Город (или часть города): ");
        String city = scanner.nextLine();
        System.out.print("Код (или часть кода): ");
        String code = scanner.nextLine();

        List<Airport> airports = airportsDAO.searchAirports(name, city, code);

        if (airports.isEmpty()) {
            System.out.println("Аэропорты не найдены.");
        } else {
            System.out.println("\nНайденные Аэропорты:");
            for (Airport airport : airports) {
                System.out.println(airport);
            }
        }
    }

    /**
     * Класс доступа к данным для управления пользователями.
     */
    static class UsersDAO {

        /**
         * Добавляет нового пользователя в базу данных.
         *
         * @param user объект User для добавления
         */
        public void addUser(User user) {
            String sql = "INSERT INTO Users (first_name, last_name, email, phone) VALUES (?, ?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                // Установка параметров запроса
                pstmt.setString(1, user.getFirstName());
                pstmt.setString(2, user.getLastName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getPhone());

                // Выполнение запроса
                int affectedRows = pstmt.executeUpdate();

                // Получение сгенерированного ID
                if (affectedRows > 0) {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            user.setUserId(rs.getInt(1));
                            System.out.println("Пользователь добавлен с ID: " + user.getUserId());
                        }
                    }
                }

            } catch (SQLException e) {
                System.out.println("Ошибка при добавлении пользователя: " + e.getMessage());
            }
        }

        /**
         * Обновляет информацию о существующем пользователе.
         *
         * @param user объект User для обновления
         */
        public void updateUser(User user) {
            String sql = "UPDATE Users SET first_name = ?, last_name = ?, email = ?, phone = ? WHERE user_id = ?";

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // Установка параметров запроса
                pstmt.setString(1, user.getFirstName());
                pstmt.setString(2, user.getLastName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getPhone());
                pstmt.setInt(5, user.getUserId());

                // Выполнение запроса
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Пользователь с ID " + user.getUserId() + " обновлён.");
                } else {
                    System.out.println("Пользователь с ID " + user.getUserId() + " не найден.");
                }

            } catch (SQLException e) {
                System.out.println("Ошибка при обновлении пользователя: " + e.getMessage());
            }
        }

        /**
         * Удаляет пользователя из базы данных по его ID.
         *
         * @param userId ID пользователя для удаления
         */
        public void deleteUser(int userId) {
            String sql = "DELETE FROM Users WHERE user_id = ?";

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // Установка параметра запроса
                pstmt.setInt(1, userId);

                // Выполнение запроса
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Пользователь с ID " + userId + " удалён.");
                } else {
                    System.out.println("Пользователь с ID " + userId + " не найден.");
                }

            } catch (SQLException e) {
                System.out.println("Ошибка при удалении пользователя: " + e.getMessage());
            }
        }

        /**
         * Ищет пользователей по имени и фамилии.
         *
         * @param firstName часть или полное имя
         * @param lastName  часть или полное имя
         * @return список найденных пользователей
         */
        public List<User> searchUsers(String firstName, String lastName) {
            List<User> users = new ArrayList<>();
            String sql = "SELECT * FROM Users WHERE first_name LIKE ? AND last_name LIKE ?";

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // Установка параметров запроса с использованием подстановочных символов
                pstmt.setString(1, "%" + firstName + "%");
                pstmt.setString(2, "%" + lastName + "%");

                // Выполнение запроса
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        User user = new User(
                                rs.getInt("user_id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("email"),
                                rs.getString("phone")
                        );
                        users.add(user);
                    }
                }

            } catch (SQLException e) {
                System.out.println("Ошибка при поиске пользователей: " + e.getMessage());
            }

            return users;
        }
    }

    /**
     * Модель класса User, представляющая пользователя системы.
     */
    static class User {
        private int userId;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;

        // Конструктор без ID (для добавления нового пользователя)
        public User(String firstName, String lastName, String email, String phone) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phone = phone;
        }

        // Конструктор с ID (для существующих пользователей)
        public User(int userId, String firstName, String lastName, String email, String phone) {
            this.userId = userId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phone = phone;
        }

        // Геттеры и сеттеры

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        /**
         * Переопределение метода toString для удобного вывода информации о пользователе.
         */
        @Override
        public String toString() {
            return "User [ID=" + userId + ", Имя=" + firstName + ", Фамилия=" + lastName +
                    ", Email=" + email + ", Телефон=" + phone + "]";
        }
    }

    /**
     * Класс доступа к данным для управления аэропортами.
     */
    static class AirportsDAO {

        /**
         * Добавляет новый аэропорт в базу данных.
         *
         * @param airport объект Airport для добавления
         */
        public void addAirport(Airport airport) {
            String sql = "INSERT INTO Airports (name, city, country, code) VALUES (?, ?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                // Установка параметров запроса
                pstmt.setString(1, airport.getName());
                pstmt.setString(2, airport.getCity());
                pstmt.setString(3, airport.getCountry());
                pstmt.setString(4, airport.getCode());

                // Выполнение запроса
                int affectedRows = pstmt.executeUpdate();

                // Получение сгенерированного ID
                if (affectedRows > 0) {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            airport.setAirportId(rs.getInt(1));
                            System.out.println("Аэропорт добавлен с ID: " + airport.getAirportId());
                        }
                    }
                }

            } catch (SQLException e) {
                System.out.println("Ошибка при добавлении аэропорта: " + e.getMessage());
            }
        }

        /**
         * Ищет аэропорты по названию, городу или коду.
         *
         * @param name   часть или полное название аэропорта
         * @param city   часть или полное название города
         * @param code   часть или полный код аэропорта
         * @return список найденных аэропортов
         */
        public List<Airport> searchAirports(String name, String city, String code) {
            List<Airport> airports = new ArrayList<>();
            String sql = "SELECT * FROM Airports WHERE name LIKE ? AND city LIKE ? AND code LIKE ?";

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // Установка параметров запроса с использованием подстановочных символов
                pstmt.setString(1, "%" + name + "%");
                pstmt.setString(2, "%" + city + "%");
                pstmt.setString(3, "%" + code + "%");

                // Выполнение запроса
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Airport airport = new Airport(
                                rs.getInt("airport_id"),
                                rs.getString("name"),
                                rs.getString("city"),
                                rs.getString("country"),
                                rs.getString("code")
                        );
                        airports.add(airport);
                    }
                }

            } catch (SQLException e) {
                System.out.println("Ошибка при поиске аэропортов: " + e.getMessage());
            }

            return airports;
        }
    }

    /**
     * Модель класса Airport, представляющая аэропорт.
     */
    static class Airport {
        private int airportId;
        private String name;
        private String city;
        private String country;
        private String code;

        // Конструктор без ID (для добавления нового аэропорта)
        public Airport(String name, String city, String country, String code) {
            this.name = name;
            this.city = city;
            this.country = country;
            this.code = code;
        }

        // Конструктор с ID (для существующих аэропортов)
        public Airport(int airportId, String name, String city, String country, String code) {
            this.airportId = airportId;
            this.name = name;
            this.city = city;
            this.country = country;
            this.code = code;
        }

        // Геттеры и сеттеры

        public int getAirportId() {
            return airportId;
        }

        public void setAirportId(int airportId) {
            this.airportId = airportId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        /**
         * Переопределение метода toString для удобного вывода информации об аэропорте.
         */
        @Override
        public String toString() {
            return "Airport [ID=" + airportId + ", Название=" + name + ", Город=" + city +
                    ", Страна=" + country + ", Код=" + code + "]";
        }
    }
}
