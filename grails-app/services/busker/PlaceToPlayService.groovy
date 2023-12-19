package busker

import grails.gorm.services.Service

@Service(PlaceToPlay)
interface PlaceToPlayService {

    PlaceToPlay get(Serializable id)

    List<PlaceToPlay> list(Map args)

    Long count()

    void delete(Serializable id)

    PlaceToPlay save(PlaceToPlay placeToPlay)

}