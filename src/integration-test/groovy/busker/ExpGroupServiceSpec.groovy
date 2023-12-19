package busker

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ExpGroupServiceSpec extends Specification {

    ExpGroupService expGroupService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ExpGroup(...).save(flush: true, failOnError: true)
        //new ExpGroup(...).save(flush: true, failOnError: true)
        //ExpGroup expGroup = new ExpGroup(...).save(flush: true, failOnError: true)
        //new ExpGroup(...).save(flush: true, failOnError: true)
        //new ExpGroup(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //expGroup.id
    }

    void "test get"() {
        setupData()

        expect:
        expGroupService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ExpGroup> expGroupList = expGroupService.list(max: 2, offset: 2)

        then:
        expGroupList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        expGroupService.count() == 5
    }

    void "test delete"() {
        Long expGroupId = setupData()

        expect:
        expGroupService.count() == 5

        when:
        expGroupService.delete(expGroupId)
        sessionFactory.currentSession.flush()

        then:
        expGroupService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ExpGroup expGroup = new ExpGroup()
        expGroupService.save(expGroup)

        then:
        expGroup.id != null
    }
}
