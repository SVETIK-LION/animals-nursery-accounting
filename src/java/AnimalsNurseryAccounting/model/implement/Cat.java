package AnimalsNurseryAccounting.model.implement;

import AnimalsNurseryAccounting.model.AbstractPet;
import AnimalsNurseryAccounting.model.AnimalGenius;

import java.time.LocalDate;

public class Cat extends AbstractPet {
    public Cat(String name, LocalDate birthDate) {
        super(name, birthDate);
        setAnimalGenius(AnimalGenius.CAT);
    }
}
