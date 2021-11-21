package service;

import entity.Singer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.SingerRepository;

import java.util.ArrayList;
import java.util.List;

@Service("jpaSingerServiceCrudRepository")
@Transactional
public class SingerServiceCrudRepositoryImpl implements SingerServiceCrudRepository {

    @Autowired
    private SingerRepository singerRepository;

    @Transactional(readOnly = true)
    public List<Singer> findAll() {
        List<Singer> list = new ArrayList<>();
        singerRepository.findAll().forEach(list::add);
        return list;
    }

    @Transactional(readOnly = true)
    public List<Singer> findByFirstName(String firstName) {
        return singerRepository.findByFirstName(firstName);
    }

    @Transactional(readOnly = true)
    public List<Singer> findByFirstNameAndLastName(String firstName, String lastName) {
        return singerRepository.findByFirstNameAndLastName(firstName, lastName);
    }
}