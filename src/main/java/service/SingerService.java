package service;

import entity.Singer;
import entity.Test;

import java.util.List;

public interface SingerService {

    List<Singer> findAll();

    List<Singer> findAllWithAlbum();

    Singer findById(Long id);

    Singer save(Singer singer);

    void delete(Singer singer);

    List<Singer> findAllByNativeQuery();

    List<Test> findAllTestByNativeQuery();

    List<Singer> findAllByCriteriaQuery(String firstName, String lastName);

}