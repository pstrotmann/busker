package busker

import grails.gorm.services.Service

@Service(Experiment)
interface ExperimentService {

    Experiment get(Serializable id)

    List<Experiment> list(Map args)

    Long count()

    void delete(Serializable id)

    Experiment save(Experiment experiment)

}