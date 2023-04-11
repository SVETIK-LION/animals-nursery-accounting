package AnimalsNurseryAccounting.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * Описывает абстрактное животное
 */
public abstract class AbstractAnimal {
    private static int counter;
    private final int id = ++counter;

    private AnimalGenius animalGenius;

    /**
     * Имя животного
     */
    private final String name;

    /**
     * Дата рождения животного
     */
    private LocalDate birthDate;

    /**
     * Список выполняемых команд животного
     */
    private final List<Skill> animalSkills;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public AnimalGenius getAnimalGenius() {
        return animalGenius;
    }

    public void setAnimalGenius(AnimalGenius animalGenius) {
        this.animalGenius = animalGenius;
    }

    public List<Skill> getAnimalSkills() {
        return animalSkills;
    }

    public AbstractAnimal(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
        animalSkills = new ArrayList<>();
    }

    public String getBirthDateAsString() {
        return String.format("%02d-%02d-%4d", birthDate.getDayOfMonth(), birthDate.getMonthValue(),
                birthDate.getYear());
    }

    /**
     * Возвращает возраст живионого в месяцах
     * @return int возраст
     */
    public int getAge() {
        int years = Period.between(birthDate, LocalDate.now()).getYears();
        int months = Period.between(birthDate, LocalDate.now()).getMonths();
        return years * 12 + months;
    }

    /**
     * Добавляет новую команду
     *
     * @param newSkill команды
     */
    public void learnSkill(Skill newSkill) {
        if (animalSkills.contains(newSkill)){
            return;
        }
        animalSkills.add(newSkill);
    }

    @Override
    public String toString() {
        return "AbstractAnimal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
