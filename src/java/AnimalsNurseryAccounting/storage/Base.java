package AnimalsNurseryAccounting.storage;

import AnimalsNurseryAccounting.model.AbstractAnimal;

import java.util.List;

public interface Base {
    /**
     * Возвращает список животных из питомника
     * @return List Список животных
     */
    List<AbstractAnimal> getAllAnimals();

    /**
     * Возвращает животное по его id из базы
     * @return Животное или null если животное с таким id не найдено
     */
    AbstractAnimal getAnimalById(int animalId);

    /**
     * Добавляет животное в базу
     * @return bool true в случае успеха или false - в противном
     */
    boolean addAnimal(AbstractAnimal animal);

    /**
     * Удаляет животное из базы
     * @return int id удаленного животного, или -1 удаляемого животного нет в хранилище
     */
    int removeAnimal(AbstractAnimal animal);
}
