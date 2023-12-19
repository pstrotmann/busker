package busker

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class PlaceToPlayController {

    PlaceToPlayService placeToPlayService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 100, 1000)
        respond placeToPlayService.list(params), model:[placeToPlayCount: placeToPlayService.count()]
    }

    def show(Long id) {
        respond placeToPlayService.get(id)
    }

    def create() {
        respond new PlaceToPlay(params)
    }

    def save(PlaceToPlay placeToPlay) {
        if (placeToPlay == null) {
            notFound()
            return
        }

        try {
            placeToPlayService.save(placeToPlay)
        } catch (ValidationException e) {
            respond placeToPlay.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'placeToPlay.label', default: 'PlaceToPlay'), placeToPlay.id])
                redirect placeToPlay
            }
            '*' { respond placeToPlay, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond placeToPlayService.get(id)
    }

    def update(PlaceToPlay placeToPlay) {
        if (placeToPlay == null) {
            notFound()
            return
        }

        try {
            placeToPlayService.save(placeToPlay)
        } catch (ValidationException e) {
            respond placeToPlay.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'placeToPlay.label', default: 'PlaceToPlay'), placeToPlay.id])
                redirect placeToPlay
            }
            '*'{ respond placeToPlay, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        placeToPlayService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'placeToPlay.label', default: 'PlaceToPlay'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'placeToPlay.label', default: 'PlaceToPlay'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
