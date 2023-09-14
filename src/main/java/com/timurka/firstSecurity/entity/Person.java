package com.timurka.firstSecurity.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Stanislav Rakitov
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PERSONS")
@IdClass(PersonId.class)
@Entity
public class Person {
    @Id
    @Column(length = 50)
    private String name;

    @Id
    @Column(length = 50)
    private String surname;

    @Id
    private int age;

    @Column(name = "phone_number", length = 25)
    private String phoneNumber;

    @Column(name = "city_of_living", length = 100)
    private String cityOfLiving;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (age != person.age) return false;
        if (!name.equals(person.name)) return false;
        if (!surname.equals(person.surname)) return false;
        if (!Objects.equals(phoneNumber, person.phoneNumber)) return false;
        return Objects.equals(cityOfLiving, person.cityOfLiving);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + age;
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (cityOfLiving != null ? cityOfLiving.hashCode() : 0);
        return result;
    }
}