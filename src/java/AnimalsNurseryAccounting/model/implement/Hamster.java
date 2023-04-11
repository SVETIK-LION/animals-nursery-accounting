package AnimalsNurseryAccounting.model.implement;

import AnimalsNurseryAccounting.model.AbstractPet;
import AnimalsNurseryAccounting.model.AnimalGenius;

import java.time.LocalDate;

public class Hamster extends AbstractPet {
    public Hamster(String name, LocalDate birthDate) {
        super(name, birthDate);
        setAnimalGenius(AnimalGenius.HAMSTER);
    }
}
