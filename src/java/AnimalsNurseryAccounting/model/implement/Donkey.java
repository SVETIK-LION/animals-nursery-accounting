package AnimalsNurseryAccounting.model.implement;

import AnimalsNurseryAccounting.model.AbstractPackAnimal;
import AnimalsNurseryAccounting.model.AnimalGenius;

import java.time.LocalDate;

public class Donkey extends AbstractPackAnimal {
    public Donkey(String name, LocalDate birthDate) {
        super(name, birthDate);
        setAnimalGenius(AnimalGenius.DONKEY);
    }
}
