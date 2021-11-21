package service;

import entity.Singer;

import java.util.List;

public interface SingerServiceCrudRepository {

    List<Singer> findAll();
    List<Singer> findByFirstName(String firstName);
    List<Singer> findByFirstNameAndLastName(String firstName, String lastName);
}
