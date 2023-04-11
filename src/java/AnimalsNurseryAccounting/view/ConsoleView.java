package AnimalsNurseryAccounting.view;

import AnimalsNurseryAccounting.controller.NurseryAccounting;
import AnimalsNurseryAccounting.model.AbstractAnimal;
import AnimalsNurseryAccounting.model.AnimalGenius;
import AnimalsNurseryAccounting.model.Skill;
import AnimalsNurseryAccounting.util.Counter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConsoleView implements View {
    public static final int SIZE_LINE = 80;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ROW_FORMAT = "%-6d%-16s%-20s%-20d%-20s";
    public static final String COLUMN_HEADER_FORMAT = "%-6s%-16s%-20s%-20s%-20s";
    public static final String RED_COLOR = "\u001B[31m";
    public static final String GREEN_COLOR = "\u001B[32m";
    private final NurseryAccounting nurseryAccounting;
    private Scanner scanner;

    public ConsoleView(NurseryAccounting nurseryAccounting) {
        this.nurseryAccounting = nurseryAccounting;
        scanner = new Scanner(System.in);
    }

    record AnimalData(String name, LocalDate birthDate, AnimalGenius animalGenius){}

    @Override
    public void showNurseryRegistry() {
        clearConsole();
        printLineWithSymbol("=");
        printCaption("Питомник для животных", " ");
        printLineWithSymbol("=");
        printRegistryHeader();
        printLineWithSymbol("-");

        List<AbstractAnimal> animals = nurseryAccounting.getAnimals();
        for (int i = 0; i < animals.size(); i++) {
            var item = animals.get(i);
            String row = String.format(ROW_FORMAT, i + 1, item.getName(), item.getBirthDateAsString(), item.getAge(),
                    item.getAnimalGenius().getTitle());
            printLine(row);
        }
        printLineWithSymbol("-");
    }

    @Override
    public MainMenuCommand showMainMenuWithResult() {
        String menu = String.format(
                "%d-[%s]\t%d-[%s]\t%d-[%s]\t%d-[%s]\n",
                MainMenuCommand.ADD_ANIMAL.ordinal(),
                MainMenuCommand.ADD_ANIMAL.getTag(),
                MainMenuCommand.SHOW_SKILLS.ordinal(),
                MainMenuCommand.SHOW_SKILLS.getTag(),
                MainMenuCommand.REMOVE_ANIMAL.ordinal(),
                MainMenuCommand.REMOVE_ANIMAL.getTag(),
                MainMenuCommand.EXIT.ordinal(),
                MainMenuCommand.EXIT.getTag());
        printLineWithSymbol("=");
        printColorLine("Доступные действия:", GREEN_COLOR);
        printColorLine(menu, GREEN_COLOR);
        while (true) {
            try {
                System.out.printf("%s (%d - %d): ", "Выберите действие: ", MainMenuCommand.ADD_ANIMAL.ordinal(),
                        MainMenuCommand.EXIT.ordinal());
                scanner = new Scanner(System.in);
                return MainMenuCommand.values()[scanner.nextInt()];
            } catch (ArrayIndexOutOfBoundsException | InputMismatchException e) {
                printColorLine("Некорректное действие!", RED_COLOR);
            }
        }
    }

    @Override
    public boolean showAddAnimalDialog() {
        String infoMessage = "Введите параметры животного в виде строки: \"имя день_рождения род_животного\"\n" +
                "День_рождения имеет вид: dd-mm-yyyy (12-03-2022): \n" +
                "Род_животного может принимать значения: " + Arrays.asList(AnimalGenius.values());
        System.out.println(infoMessage);
        while (true) {
            Counter counter = new Counter();
            try (counter){
                System.out.print("Ввод: ");
                scanner = new Scanner(System.in);
                String inputData = scanner.nextLine();
                AnimalData parsedData = parseAnimalData(inputData);
                counter.add();
                return nurseryAccounting.createAnimal(parsedData.name, parsedData.birthDate, parsedData.animalGenius);
            } catch (DateTimeParseException e) {
                System.out.println("Неправильный формат даты рождения");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    @Override
    public void showDetailInfoAnimalDialog() {
        String infoMessage = String.format("Введите номер животного (1 - %d)", nurseryAccounting.getAnimals().size());
        System.out.println(infoMessage);
        while (true) {
            try {
                scanner = new Scanner(System.in);
                int animalNumber = scanner.nextInt();
                AbstractAnimal animal = nurseryAccounting.getAnimals().get(animalNumber - 1);
                showAnimalInfo(animal);

                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Запись с таким номером отсутствует");
            }
        }
    }

    @Override
    public void showAnimalInfo(AbstractAnimal animal) {
        while (true) {
            printAnimalInfo(animal);
            AddSkillMenuCommand code = showAddSkillMenu(animal);
            switch (code) {
                case ADD_SKILL -> {
                    boolean result = showAddSkillDialog(animal);
                    String resMessage = result ? "Команда добавлена" : "Ошибка при добавлении команды";
                    System.out.println(resMessage);
                    if (!result) return;
                }
                case EXIT -> {
                    return;
                }
            }
        }
    }

    @Override
    public AddSkillMenuCommand showAddSkillMenu(AbstractAnimal animal) {
        String menu = String.format(
                "%d-[%s]\t%d-[%s]\n",
                AddSkillMenuCommand.ADD_SKILL.ordinal(), AddSkillMenuCommand.ADD_SKILL.getTag(),
                AddSkillMenuCommand.EXIT.ordinal(), AddSkillMenuCommand.EXIT.getTag()) ;
        printLineWithSymbol("=");
        printColorLine("Доступные действия:", GREEN_COLOR);
        printColorLine(menu, GREEN_COLOR);
        while (true) {
            try {
                System.out.printf("%s (%d - %d): ", "Выберите действие: ", AddSkillMenuCommand.ADD_SKILL.ordinal(),
                        AddSkillMenuCommand.EXIT.ordinal());
                scanner = new Scanner(System.in);
                return AddSkillMenuCommand.values()[scanner.nextInt()];
            } catch (ArrayIndexOutOfBoundsException | InputMismatchException e) {
                printColorLine("Некорректное действие!", RED_COLOR);
            }
        }
    }

    @Override
    public boolean showAddSkillDialog(AbstractAnimal animal) {
        System.out.println("Введите название команды: ");
        scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.isBlank()) return false;
        String[] skillData = input.split(":");
        if (skillData.length == 1) {
            animal.learnSkill(new Skill(skillData[0]));
        } else if (skillData.length == 2) {
            animal.learnSkill(new Skill(skillData[0], skillData[1]));
        } else {
            System.out.println("Слишком много параметров");
            return false;
        }
        return true;
    }

    @Override
    public int showRemoveAnimalDialog() {
        String infoMessage = String.format("Введите номер животного (1 - %d)", nurseryAccounting.getAnimals().size());
        System.out.println(infoMessage);
        while (true) {
            try {
                scanner = new Scanner(System.in);
                int animalNumber = scanner.nextInt();
                AbstractAnimal animal = nurseryAccounting.getAnimals().get(animalNumber - 1);

                return nurseryAccounting.removeAnimal(animal);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Запись с таким номером отсутствует");
            }
        }
    }

    /**
     * Парсит входные данные при добавлении животного
     * @param inputData Входная строка имеет вид "имя_животного дата_рождения род_животного"
     *                  имя_животного - не содержит пробелов
     *                  дата_рождения - имеет фоормат dd-MM-yyyy (12-03-2022)
     *                  род_животного - содержит значения из пепечисления AnimalGenius
     * @return возвращает record AnimalData, содержащую провалидированные параметры животного
     */
    private AnimalData parseAnimalData(String inputData) {
        String[] input = inputData.split(" ");

        if (input.length < 3) {
            throw new IllegalArgumentException("Недостаточное количество данных");
        }
        if (input.length > 3) {
            throw new IllegalArgumentException("Слишком много данных");
        }
        String animalName = input[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate birthDay = LocalDate.parse(input[1], formatter);
        AnimalGenius genius = AnimalGenius.valueOf(input[2].toUpperCase());
        return new AnimalData(animalName, birthDay, genius);
    }
    private void clearConsole() {
        System.out.print("\033[H\033[J");
    }

    private void printLineWithSymbol(String symbol) {
        System.out.println(symbol.repeat(ConsoleView.SIZE_LINE));
    }

    private void printCaption(String caption, String padSymb) {
        int spaceSize = (SIZE_LINE - caption.length()) / 2;
        String captionLine = padSymb.repeat(spaceSize) + caption + padSymb.repeat(spaceSize);
        System.out.println(captionLine);
    }

    private void printRegistryHeader() {
        String header = String.format(COLUMN_HEADER_FORMAT, "№", "Имя", "Дата рождения", "Возраст(в месяцах)",
                "Род животного");
        System.out.println(header);
    }

    private void printLine(String row) {
        System.out.printf("%s\n", row);
    }

    private void printColorLine(String row, String displayColor) {
        System.out.printf("%s%s%s\n", displayColor, row, ANSI_RESET);
    }

    private void printAnimalInfo(AbstractAnimal animal) {
        clearConsole();
        printCaption("Detail info", "~");
        System.out.printf("Род животного: %s\n", animal.getAnimalGenius().getTitle());
        System.out.printf("Имя: %s\n", animal.getName());
        System.out.printf("Дата рождения: %s\n", animal.getBirthDateAsString());
        System.out.printf("Возраст (в месяцах): %d\n", animal.getAge());
        System.out.printf("Умения: %s\n", animal.getAnimalSkills());
        printLineWithSymbol("~");
    }
}
