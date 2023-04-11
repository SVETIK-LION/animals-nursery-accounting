package AnimalsNurseryAccounting.model.implement;

import AnimalsNurseryAccounting.model.AbstractPackAnimal;
import AnimalsNurseryAccounting.model.AnimalGenius;

import java.time.LocalDate;

public class Horse extends AbstractPackAnimal {
    public Horse(String name, LocalDate birthDate) {
        super(name, birthDate);
        setAnimalGenius(AnimalGenius.HORSE);
    }
}
