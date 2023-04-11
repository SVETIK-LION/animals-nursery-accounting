package AnimalsNurseryAccounting.storage;

import AnimalsNurseryAccounting.model.AbstractAnimal;
import AnimalsNurseryAccounting.model.AbstractPackAnimal;
import AnimalsNurseryAccounting.model.AbstractPet;
import AnimalsNurseryAccounting.model.implement.*;
import AnimalsNurseryAccounting.model.Skill;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Реализация интерфейса Base
 * Хранилище реализовано на HashMap
 */
public class NurseryBase implements Base {
    Map<Integer, AbstractAnimal> dbAnimals = new HashMap<>();

    public NurseryBase() {
        init();
    }

    private void init(){
        AbstractPet cat = new Cat("Киса", LocalDate.of(2023, 1, 11));

        cat.learnSkill(new Skill("Поймать мышь"));
        cat.learnSkill(new Skill("Лечебный Ур-Ур-Ур"));
        dbAnimals.put(cat.getId(), cat);

        AbstractPet dog = new Dog("Шаня", LocalDate.of(2021, 3, 14));
        dog.learnSkill(new Skill("Поймать фрисби"));
        dog.learnSkill(new Skill("Лежать"));
        dbAnimals.put(dog.getId(), dog);

        AbstractPet hamster = new Hamster("Хома", LocalDate.of(2022, 4, 1));
        hamster.learnSkill(new Skill("Сделать норку из обрывков газеты"));
        hamster.learnSkill(new Skill("Притвориться мертвым"));
        dbAnimals.put(hamster.getId(), hamster);

        AbstractPackAnimal horse = new Horse("Стрела", LocalDate.of(2021, 2, 4));
        horse.setLoadCapacity(300);
        horse.learnSkill(new Skill("Бег трусцой"));
        horse.learnSkill(new Skill("Прыжок через препятствие"));
        horse.learnSkill(new Skill("Бег галопом"));
        dbAnimals.put(horse.getId(), horse);

        AbstractPackAnimal horse2 = new Horse("Плотва", LocalDate.of(2022, 12, 1));
        horse2.setLoadCapacity(400);
        horse2.learnSkill(new Skill("Шуточный укус наездника"));
        horse2.learnSkill(new Skill("Неожиданно появиться ниоткуда"));
        dbAnimals.put(horse2.getId(), horse2);

        AbstractPackAnimal donkey = new Donkey("Осел", LocalDate.of(2020, 8, 24));
        donkey.setLoadCapacity(500);
        donkey.learnSkill(new Skill("Встать на задние ноги"));
        donkey.learnSkill(new Skill("Спеть песню"));
        donkey.learnSkill(new Skill("Приручить дракона"));
        dbAnimals.put(donkey.getId(), donkey);

        AbstractPackAnimal camel = new Camel("Самец", LocalDate.of(2020, 5, 20));
        camel.setLoadCapacity(1000);
        camel.learnSkill(new Skill("Плюнуть на 5 метров"));
        camel.learnSkill(new Skill("Поиграть горбами"));
        dbAnimals.put(camel.getId(), camel);
    }
    @Override
    public List<AbstractAnimal> getAllAnimals() {
        return new ArrayList<>(dbAnimals.values());
    }

    @Override
    public AbstractAnimal getAnimalById(int animalId) {
        return dbAnimals.getOrDefault(animalId, null);
    }

    @Override
    public boolean addAnimal(AbstractAnimal animal) {
        if (dbAnimals.containsKey(animal.getId())) {return false;}
        dbAnimals.put(animal.getId(), animal);
        return true;
    }

    @Override
    public int removeAnimal(AbstractAnimal animal) {
        if (!dbAnimals.containsKey(animal.getId())) {
            return -1;
        }
        AbstractAnimal removed = dbAnimals.remove(animal.getId());
        return removed.getId();
    }
}
