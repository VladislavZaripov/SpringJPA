package service;

import entity.SingerAudit;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

public interface SingerAuditService {

    List<SingerAudit> findAll();

    SingerAudit findById(Long id);

    SingerAudit save(SingerAudit singer);

    SingerAudit findAuditByRevision(Long id, int revision);

    List<Number> findAuditByObject(Long id);

    void deleteAll(GenericApplicationContext ctx);
}