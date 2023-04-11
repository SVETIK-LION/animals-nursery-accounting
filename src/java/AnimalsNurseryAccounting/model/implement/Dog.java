package AnimalsNurseryAccounting.model.implement;

import AnimalsNurseryAccounting.model.AbstractPet;
import AnimalsNurseryAccounting.model.AnimalGenius;

import java.time.LocalDate;

public class Dog extends AbstractPet {
    public Dog(String name, LocalDate birthDate) {
        super(name, birthDate);
        setAnimalGenius(AnimalGenius.DOG);
    }
}
