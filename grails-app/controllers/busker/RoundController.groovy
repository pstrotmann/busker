package busker

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class RoundController {

    RoundService roundService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 100, 1000)
        respond roundService.list(params), model:[roundCount: roundService.count()]
    }

    def show(Long id) {
        respond roundService.get(id)
    }

    def create() {
        respond new Round(params)
    }

    def save(Round round) {
        if (round == null) {
            notFound()
            return
        }

        try {
            roundService.save(round)
        } catch (ValidationException e) {
            respond round.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'round.label', default: 'Round'), round.id])
                redirect round
            }
            '*' { respond round, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond roundService.get(id)
    }

    def update(Round round) {
        if (round == null) {
            notFound()
            return
        }

        try {
            roundService.save(round)
        } catch (ValidationException e) {
            respond round.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'round.label', default: 'Round'), round.id])
                redirect round
            }
            '*'{ respond round, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        roundService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'round.label', default: 'Round'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'round.label', default: 'Round'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
