package busker

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class PlaceToPlayServiceSpec extends Specification {

    PlaceToPlayService placeToPlayService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new PlaceToPlay(...).save(flush: true, failOnError: true)
        //new PlaceToPlay(...).save(flush: true, failOnError: true)
        //PlaceToPlay placeToPlay = new PlaceToPlay(...).save(flush: true, failOnError: true)
        //new PlaceToPlay(...).save(flush: true, failOnError: true)
        //new PlaceToPlay(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //placeToPlay.id
    }

    void "test get"() {
        setupData()

        expect:
        placeToPlayService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<PlaceToPlay> placeToPlayList = placeToPlayService.list(max: 2, offset: 2)

        then:
        placeToPlayList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        placeToPlayService.count() == 5
    }

    void "test delete"() {
        Long placeToPlayId = setupData()

        expect:
        placeToPlayService.count() == 5

        when:
        placeToPlayService.delete(placeToPlayId)
        sessionFactory.currentSession.flush()

        then:
        placeToPlayService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        PlaceToPlay placeToPlay = new PlaceToPlay()
        placeToPlayService.save(placeToPlay)

        then:
        placeToPlay.id != null
    }
}
