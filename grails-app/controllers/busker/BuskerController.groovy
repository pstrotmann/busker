package busker

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class BuskerController {

    BuskerService buskerService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond buskerService.list(params), model:[buskerCount: buskerService.count()]
    }

    def show(Long id) {
        respond buskerService.get(id)
    }

    def create() {
        respond new Busker(params)
    }

    def save(Busker busker) {
        if (busker == null) {
            notFound()
            return
        }

        try {
            buskerService.save(busker)
        } catch (ValidationException e) {
            respond busker.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'busker.label', default: 'Busker'), busker.id])
                redirect busker
            }
            '*' { respond busker, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond buskerService.get(id)
    }

    def update(Busker busker) {
        if (busker == null) {
            notFound()
            return
        }

        try {
            buskerService.save(busker)
        } catch (ValidationException e) {
            respond busker.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'busker.label', default: 'Busker'), busker.id])
                redirect busker
            }
            '*'{ respond busker, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        buskerService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'busker.label', default: 'Busker'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'busker.label', default: 'Busker'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
