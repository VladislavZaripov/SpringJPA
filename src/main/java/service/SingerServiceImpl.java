package service;

import entity.Singer;
import entity.Singer_;
import entity.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

@Service("jpaSingerService")
@Transactional
public class SingerServiceImpl implements SingerService {

    final static String ALL_SINGER_NATIVE_QUERY = "SELECT ID, FIRST_NAME, LAST_NAME, BIRTH_DATE, VERSION FROM Singer";

    private static Logger logger = LoggerFactory.getLogger(SingerServiceImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Singer> findAll() {
        return em
                .createNamedQuery(Singer.FIND_ALL, Singer.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public List<Singer> findAllWithAlbum() {
        return em
                .createNamedQuery(Singer.FIND_ALL_WITH_ALBUM, Singer.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public Singer findById(Long id) {
        return (Singer) em
                .createNamedQuery(Singer.FIND_SINGER_BY_ID)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Transactional(readOnly = false)
    public Singer save(Singer singer) {
        if (singer.getId() == null) {
            logger.info("Inserting new Singer");
            em.persist(singer);
        } else {
            em.merge(singer);
            logger.info("Updating existing Singer");
        }

        logger.info("Singer saved with id: " + singer.getId());
        return singer;
    }

    @Transactional(readOnly = false)
    public void delete(Singer singer) {
        Singer mergedSinger = em.merge(singer);
        em.remove(mergedSinger);
    }

    @Transactional(readOnly = true)
    public List<Singer> findAllByNativeQuery() {
        return em.createNativeQuery(ALL_SINGER_NATIVE_QUERY, Singer.class).getResultList();
    }

    @Transactional(readOnly = true)
    public List<Test> findAllTestByNativeQuery() {
        return em.createNativeQuery("select id, text from Test", Test.class).getResultList();
    }

    @Transactional(readOnly = true)
    public List<Singer> findAllByCriteriaQuery(String firstName, String lastName) {
        // add setting -> Build -> Compile, then build Project, metamodels are generated in target.generated-sources.annotations.entity

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Singer> criteriaQuery = cb.createQuery(Singer.class);

        Root<Singer> singerRoot = criteriaQuery.from(Singer.class);
        singerRoot.fetch(Singer_.albums, JoinType.LEFT);
        singerRoot.fetch(Singer_.instruments, JoinType.LEFT);

        criteriaQuery.select(singerRoot).distinct(true);

        Predicate criteria = cb.conjunction();
        if (firstName != null) {
            Predicate p = cb.equal(singerRoot.get(Singer_.firstName), firstName);
            criteria = cb.and(criteria, p);
        }
        if (lastName != null) {
            Predicate p = cb.equal(singerRoot.get(Singer_.lastName), lastName);
            criteria = cb.and(criteria, p);
        }

        criteriaQuery.where(criteria);

        return em.createQuery(criteriaQuery).getResultList();
    }
}













