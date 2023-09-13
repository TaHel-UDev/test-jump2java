import java.io.*;

public class Main {
    private static final String FILE_PATH = "best_attempts.txt";

    public static void main(String[] args) {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Ошибка при создании файла: " + e.getMessage());
            }
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int minNumber = 1;
        int maxNumber = 100;
        int randomNumber = generateRandomNumber(minNumber, maxNumber);
        int attempts = 0;
        int bestAttempts = loadBestAttempts();

        System.out.println(randomNumber);

        System.out.println("Компьютер загадал число от " + minNumber + " до " + maxNumber);

        while (true) {
            System.out.print("Ваш вариант: ");
            String input = null;

            try {
                input = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (input.equalsIgnoreCase("QUIT")) {
                System.out.println("Игра завершена. Лучшее количество попыток: " + bestAttempts);
                saveBestAttempts(bestAttempts);
                break;
            } else if (input.equalsIgnoreCase("RESULT")) {
                System.out.println("Текущее количество попыток: " + attempts);
                System.out.println("Лучшее количество попыток: " + bestAttempts);
                continue;
            }

            try {
                int userGuess = Integer.parseInt(input);
                attempts++;

                if (userGuess == randomNumber) {
                    System.out.println("Поздравляю, вы угадали число! Загаданное число: " + randomNumber);
                    if (attempts < bestAttempts) {
                        bestAttempts = attempts;
                        saveBestAttempts(bestAttempts);
                    }
                    System.out.println("Количество попыток: " + attempts);
                    System.out.println("Лучшее количество попыток: " + bestAttempts);
                    break;
                } else if (userGuess < randomNumber) {
                    System.out.println("Я сам в шоке, но, загаданное число больше, брат");
                } else {
                    System.out.println("Не ожидал от тебя такого. Загаданное число меньше, брат");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите корректное целое число или команду (RESULT, QUIT).");
            }
        }
    }

    private static int generateRandomNumber(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    private static int loadBestAttempts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = reader.readLine();
            if (line != null) {
                return Integer.parseInt(line);
            } else {
                System.out.println("Файл '" + FILE_PATH + "' пуст.");
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return Integer.MAX_VALUE;
    }

    private static void saveBestAttempts(int bestAttempts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(Integer.toString(bestAttempts));
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}
