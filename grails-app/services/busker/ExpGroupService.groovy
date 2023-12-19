package busker

import grails.gorm.services.Service

@Service(ExpGroup)
interface ExpGroupService {

    ExpGroup get(Serializable id)

    List<ExpGroup> list(Map args)

    Long count()

    void delete(Serializable id)

    ExpGroup save(ExpGroup expGroup)

}