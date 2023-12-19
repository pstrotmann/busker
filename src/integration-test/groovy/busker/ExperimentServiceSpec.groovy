package busker

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ExperimentServiceSpec extends Specification {

    ExperimentService experimentService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Experiment(...).save(flush: true, failOnError: true)
        //new Experiment(...).save(flush: true, failOnError: true)
        //Experiment experiment = new Experiment(...).save(flush: true, failOnError: true)
        //new Experiment(...).save(flush: true, failOnError: true)
        //new Experiment(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //experiment.id
    }

    void "test get"() {
        setupData()

        expect:
        experimentService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Experiment> experimentList = experimentService.list(max: 2, offset: 2)

        then:
        experimentList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        experimentService.count() == 5
    }

    void "test delete"() {
        Long experimentId = setupData()

        expect:
        experimentService.count() == 5

        when:
        experimentService.delete(experimentId)
        sessionFactory.currentSession.flush()

        then:
        experimentService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Experiment experiment = new Experiment()
        experimentService.save(experiment)

        then:
        experiment.id != null
    }
}
