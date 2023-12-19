package busker

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class BuskerServiceSpec extends Specification {

    BuskerService buskerService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Busker(...).save(flush: true, failOnError: true)
        //new Busker(...).save(flush: true, failOnError: true)
        //Busker busker = new Busker(...).save(flush: true, failOnError: true)
        //new Busker(...).save(flush: true, failOnError: true)
        //new Busker(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //busker.id
    }

    void "test get"() {
        setupData()

        expect:
        buskerService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Busker> buskerList = buskerService.list(max: 2, offset: 2)

        then:
        buskerList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        buskerService.count() == 5
    }

    void "test delete"() {
        Long buskerId = setupData()

        expect:
        buskerService.count() == 5

        when:
        buskerService.delete(buskerId)
        sessionFactory.currentSession.flush()

        then:
        buskerService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Busker busker = new Busker()
        buskerService.save(busker)

        then:
        busker.id != null
    }
}
