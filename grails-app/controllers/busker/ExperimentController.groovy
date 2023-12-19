package busker

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class ExperimentController {

    ExperimentService experimentService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond experimentService.list(params), model:[experimentCount: experimentService.count()]
    }

    def show(Long id) {
        respond experimentService.get(id)
    }

    def create() {
       respond new Experiment(params)
    }

    def save(Experiment experiment) {
        if (experiment == null) {
            notFound()
            return
        }

        try {
            experimentService.save(experiment)
        } catch (ValidationException e) {
            respond experiment.errors, view:'create'
            return
        }

        if (experiment.placesToPlay == null)
            experiment.generatePlacesToPlay()

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'experiment.label', default: 'Experiment'), experiment.id])
                redirect experiment
            }
            if (experiment.expNo == 0)
                Experiment.executeUpdate("update Experiment e set e.expNo = ${(int)experiment.id} where e.id = ${experiment.id} ")
            '*' { respond experiment, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond experimentService.get(id)
    }

    def update(Experiment experiment) {
        if (experiment == null) {
            notFound()
            return
        }

        try {
            experimentService.save(experiment)
        } catch (ValidationException e) {
            respond experiment.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'experiment.label', default: 'Experiment'), experiment.id])
                redirect experiment
            }
            '*'{ respond experiment, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        experimentService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'experiment.label', default: 'Experiment'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'experiment.label', default: 'Experiment'), params.id])
                redirect action: "index", method: "GET"
            }

            '*'{ render status: NOT_FOUND }
        }
    }

    def run1(Long id) {
        Experiment experiment = Experiment.find("from Experiment where id = ${id}")
        experiment.runMult(1)
        redirect(uri: "/experiment/show/${id}")
    }

    def run10(Long id) {
        Experiment experiment = Experiment.find("from Experiment where id = ${id}")
        experiment.runMult(10)
        redirect(uri: "/experiment/show/${id}")
    }

    def run100(Long id) {
        Experiment experiment = Experiment.find("from Experiment where id = ${id}")
        experiment.runMult(100)
        redirect(uri: "/experiment/show/${id}")
    }

    def run1000(Long id) {
        Experiment experiment = Experiment.find("from Experiment where id = ${id}")
        experiment.runMult(1000)
        redirect(uri: "/experiment/show/${id}")
    }
}
