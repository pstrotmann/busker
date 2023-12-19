package busker

import grails.gorm.services.Service

@Service(Round)
interface RoundService {

    Round get(Serializable id)

    List<Round> list(Map args)

    Long count()

    void delete(Serializable id)

    Round save(Round round)

}