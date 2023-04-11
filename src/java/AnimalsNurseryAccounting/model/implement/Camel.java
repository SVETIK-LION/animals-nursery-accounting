package AnimalsNurseryAccounting.model.implement;

import AnimalsNurseryAccounting.model.AbstractPackAnimal;
import AnimalsNurseryAccounting.model.AnimalGenius;

import java.time.LocalDate;

public class Camel extends AbstractPackAnimal {
    public Camel(String name, LocalDate birthDate) {
        super(name, birthDate);
        setAnimalGenius(AnimalGenius.CAMEL);
    }
}
