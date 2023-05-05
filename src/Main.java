import java.util.*;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }


        //количество несовершеннолетних (т.е. людей младше 18 лет)
        long countMinor = persons.stream()
                .filter(p -> (p.getAge() < 18))
                .count();

        //список фамилий призывников (т.е. мужчин от 18 и до 27 лет)
        List<String> conscripts = persons.stream()
                .filter(person -> (person.getAge() > 17 && person.getAge() < 28 && person.getSex() == Sex.MAN))
                .map(Person::getFamily)
                .collect(Collectors.toList());

        //отсортированный по фамилии список потенциально работоспособных людей
        //с высшим образованием в выборке (т.е. людей с высшим образованием
        //от 18 до 60 лет для женщин и до 65 лет для мужчин)
        List<Person> workers = persons.stream()
                .filter(person -> (person.getEducation() == Education.HIGHER && person.getAge() > 17))
                .filter(person -> (person.getSex() == Sex.WOMAN && person.getAge() < 61) ||
                        (person.getSex() == Sex.MAN && person.getAge() < 66))
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());

        //демонстрация работы
        System.out.println();
        System.out.println("   Всего человек в списке: \n- " + persons.size());
        System.out.println("   Из них:");

        System.out.println("- несовершеннолетних: " + countMinor);

        System.out.println("- призывников: " + conscripts.size());

        System.out.println();
        System.out.println("   Отсортированный по фамилии список потенциально работоспособных людей,");
        System.out.println("   вывод на печать каждого 300000-ого (для демонстрации):");
        for (int i = 0; i < workers.size(); i += 300000) {
            System.out.println(workers.get(i));
        }


    }
}