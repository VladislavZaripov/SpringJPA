import configuration.Configuration_Oracle;
import entity.Singer;
import entity.SingerAudit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import service.*;

import java.util.List;

import static service.CommonService.*;

public class Executor {

    private static Logger logger = LoggerFactory.getLogger(Executor.class);
    public static GenericApplicationContext ctx;

    public static void main(String[] args) throws InterruptedException {

        ctx = new AnnotationConfigApplicationContext(Configuration_Oracle.class);
//        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
//        ctx.load(new ClassPathResource("configuration/configuration_oracle.xml"));
//        ctx.refresh();

        singerService(ctx);
        singerSummaryUntype(ctx);
        singerSummaryService(ctx);
        singerServiceCrudRepository(ctx);
        albumServiceJpaRepository(ctx);
        singerAuditCrudRepository(ctx);

    }

    private static void singerService(GenericApplicationContext ctx) throws InterruptedException {
        SingerService singerService = ctx.getBean(SingerService.class);

        System.out.println("-- SingerService findAll --");
        listSingers(singerService.findAll());
        Thread.sleep(1000);

        System.out.println("-- SingerService findAllWithAlbum --");
        listSingersWithAlbums(singerService.findAllWithAlbum());
        Thread.sleep(1000);

        System.out.println("-- SingerService findById --");
        logSinger(singerService.findById(1l));
        Thread.sleep(1000);

        System.out.println("-- SingerService save --");
        Singer newSinger = singerService.save(createSinger());
        logSinger(newSinger);
        updateSinger(newSinger);
        logSinger(newSinger);
        Singer updatedSinger = singerService.save(newSinger);
        logSinger(updatedSinger);
        Singer singerForDelete = singerService.findById(updatedSinger.getId());
        logSinger(singerForDelete);
        singerService.delete(singerForDelete);
        Thread.sleep(1000);

//        System.out.println("-- SingerService findAllTestByNativeQuery --");
//        listSingers(singerService.findAllByNativeQuery());
//        listTests(singerService.findAllTestByNativeQuery());
//        Thread.sleep(1000);

        System.out.println("-- SingerService findAllByCriteriaQuery --");
        listSingers(singerService.findAllByCriteriaQuery("John", "Mayer"));
        listSingers(singerService.findAllByCriteriaQuery("John", null));
        Thread.sleep(1000);
    }

    private static void singerSummaryUntype(GenericApplicationContext ctx) throws InterruptedException {
        SingerSummaryUntypeImpl singerSummaryUntype = ctx.getBean(SingerSummaryUntypeImpl.class);

        System.out.println("-- SingerSummaryUntypeImpl displayAllSingerSummary --");
        singerSummaryUntype.displayAllSingerSummary();
        Thread.sleep(1000);
    }

    private static void singerSummaryService(GenericApplicationContext ctx) throws InterruptedException {
        SingerSummaryService singerSummaryService = ctx.getBean(SingerSummaryService.class);

        System.out.println("-- SingerSummaryService findAll --");
        listSingerSummary(singerSummaryService.findAll());
        Thread.sleep(1000);
    }

    private static void singerServiceCrudRepository(GenericApplicationContext ctx) throws InterruptedException {
        SingerServiceCrudRepository singerServiceCrudRepository = ctx.getBean(SingerServiceCrudRepository.class);

        System.out.println("-- SingerServiceCrudRepository findAll --");
        listSingers(singerServiceCrudRepository.findAll());
        Thread.sleep(1000);

        System.out.println("-- SingerServiceCrudRepository findByFirstName --");
        listSingers(singerServiceCrudRepository.findByFirstName("John"));
        Thread.sleep(1000);

        System.out.println("-- SingerServiceCrudRepository findByFirstNameAndLastName --");
        listSingers(singerServiceCrudRepository.findByFirstNameAndLastName("John", "Mayer"));
        Thread.sleep(1000);
    }

    private static void albumServiceJpaRepository(GenericApplicationContext ctx) throws InterruptedException {
        AlbumService albumService = ctx.getBean(AlbumService.class);

        System.out.println("-- AlbumServiceJpaRepository findAll --");
        listAlbums(albumService.findAll());
        Thread.sleep(1000);

        System.out.println("-- AlbumServiceJpaRepository findByTitle --");
        listAlbums(albumService.findByTitle("The"));
        Thread.sleep(1000);
    }

    private static void singerAuditCrudRepository(GenericApplicationContext ctx) throws InterruptedException {
        SingerAuditService singerAuditService = ctx.getBean(SingerAuditService.class);

        System.out.println("-- SingerAuditCrudRepository deleteAll --");
        singerAuditService.deleteAll(ctx);
        Thread.sleep(1000);

        System.out.println("-- SingerAuditCrudRepository findAll --");
        listSingerAudit(singerAuditService.findAll());
        Thread.sleep(1000);

        System.out.println("-- SingerAuditCrudRepository save --");
        logSingerAudit(singerAuditService.save(createSingerAudit()));
        Thread.sleep(1000);

        System.out.println("-- SingerAuditCrudRepository findAll --");
        List<SingerAudit> singerAudits = singerAuditService.findAll();
        listSingerAudit(singerAuditService.findAll());
        Thread.sleep(1000);

        System.out.println("-- SingerAuditCrudRepository findById --");
        SingerAudit singerAudit = singerAuditService.findById(singerAudits.get(0).getId());
        logSingerAudit(singerAudit);
        Thread.sleep(1000);

        System.out.println("-- SingerAuditCrudRepository update --");
        singerAudit.setLastName("Queen");
        logSingerAudit(singerAuditService.save(singerAudit));
        Thread.sleep(1000);

        System.out.println("-- SingerAuditCrudRepository findAll --");
        listSingerAudit(singerAuditService.findAll());
        Thread.sleep(1000);

        System.out.println("-- SingerAuditCrudRepository findAuditByObject --");
        List<Number> revisions = singerAuditService.findAuditByObject(singerAudit.getId());
        revisions.forEach(x -> logger.info(x.toString()));
        Thread.sleep(1000);

        System.out.println("-- SingerAuditCrudRepository findAuditByRevision --");
        logSingerAudit(singerAuditService.findAuditByRevision(singerAudit.getId(), (int) revisions.get(0)));
        Thread.sleep(1000);

        System.out.println("-- SingerAuditCrudRepository findAuditByRevision --");
        logSingerAudit(singerAuditService.findAuditByRevision(singerAudit.getId(), (int) revisions.get(1)));
        Thread.sleep(1000);
    }
}