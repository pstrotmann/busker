package busker

import grails.gorm.services.Service

@Service(Busker)
interface BuskerService {

    Busker get(Serializable id)

    List<Busker> list(Map args)

    Long count()

    void delete(Serializable id)

    Busker save(Busker busker)

}