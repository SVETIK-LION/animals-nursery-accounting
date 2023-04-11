package AnimalsNurseryAccounting.controller;

import AnimalsNurseryAccounting.model.AbstractAnimal;
import AnimalsNurseryAccounting.model.AnimalGenius;
import AnimalsNurseryAccounting.model.implement.*;
import AnimalsNurseryAccounting.storage.Base;

import java.time.LocalDate;
import java.util.List;

/**
 * Отвечает за взаимодействие View и Model
 */
public class NurseryAccounting {

    private final Base dbNursery;

    public NurseryAccounting(Base dbNursery) {
        this.dbNursery = dbNursery;
    }

    public List<AbstractAnimal> getAnimals() {
        return dbNursery.getAllAnimals();
    }

    public boolean createAnimal(String name, LocalDate birthDay, AnimalGenius animalGenius) {
        AbstractAnimal animal = switch (animalGenius) {
            case CAT -> new Cat(name, birthDay);
            case DOG -> new Dog(name, birthDay);
            case HAMSTER -> new Hamster(name, birthDay);
            case HORSE -> new Horse(name, birthDay);
            case CAMEL -> new Camel(name, birthDay);
            case DONKEY -> new Donkey(name, birthDay);
        };

        return dbNursery.addAnimal(animal);
    }

    public int removeAnimal(AbstractAnimal animal) {
        if (animal == null) return -1;
        return  dbNursery.removeAnimal(animal);
    }
}
