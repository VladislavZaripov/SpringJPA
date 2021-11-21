package service;

import configuration.Configuration_Oracle;
import entity.SingerAudit;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.SingerAuditRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service("singerAuditService")
@Transactional
public class SingerAuditServiceImpl implements SingerAuditService {

    @Autowired
    private SingerAuditRepository singerAuditRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<SingerAudit> findAll() {
        List<SingerAudit> list = new ArrayList<>();
        singerAuditRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public SingerAudit findById(Long id) {
        return singerAuditRepository.findById(id).get();
    }

    @Override
    public SingerAudit save(SingerAudit singer) {
        return singerAuditRepository.save(singer);
    }

    @Transactional(readOnly = true)
    public SingerAudit findAuditByRevision(Long id, int revision) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        return auditReader.find(SingerAudit.class, id, revision);
    }

    @Override
    public List<Number> findAuditByObject(Long id) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        return auditReader.getRevisions(SingerAudit.class,id);
    }

    @Override
    public void deleteAll(GenericApplicationContext ctx) {
        JdbcTemplate jdbcTemplate = ctx.getBean("jdbcTemplate", JdbcTemplate.class);
        jdbcTemplate.execute("delete from SINGER_AUDIT");
        jdbcTemplate.execute("delete from SINGER_AUDIT_H");
        jdbcTemplate.execute("delete from REVINFO");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        singerAuditRepository.deleteAll();
    }
}