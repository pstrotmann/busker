package busker

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import org.apache.poi.hssf.usermodel.HSSFWorkbook

class ExpGroupController {

    ExpGroupService expGroupService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond expGroupService.list(params), model:[expGroupCount: expGroupService.count()]
    }

    def show(Long id) {
        respond expGroupService.get(id)
    }

    def create() {
        respond new ExpGroup(params)
    }

    def save(ExpGroup expGroup) {
        if (expGroup == null) {
            notFound()
            return
        }

        try {
            expGroupService.save(expGroup)
        } catch (ValidationException e) {
            respond expGroup.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'expGroup.label', default: 'ExpGroup'), expGroup.id])
                redirect expGroup
            }
            '*' { respond expGroup, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond expGroupService.get(id)
    }

    def update(ExpGroup expGroup) {
        if (expGroup == null) {
            notFound()
            return
        }

        try {
            expGroupService.save(expGroup)
        } catch (ValidationException e) {
            respond expGroup.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'expGroup.label', default: 'ExpGroup'), expGroup.id])
                redirect expGroup
            }
            '*'{ respond expGroup, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        expGroupService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'expGroup.label', default: 'ExpGroup'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'expGroup.label', default: 'ExpGroup'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def experimentsToExcel(Long id) {
        ExpGroup expGroup = ExpGroup.find("from ExpGroup where id = ${id}")
        HSSFWorkbook workBook = expGroup.experimentsToExcel()
        FileOutputStream fileOutputStream =  new FileOutputStream("expResults.xls")
        workBook.write(fileOutputStream)
        fileOutputStream.close()
        downloadFile("expResults.xls")
        //redirect(uri: "/expGroup/show/${id}")
    }

    private void downloadFile(String fileName) {
        def File file = new File(fileName)

        def os = response.outputStream
        response.setHeader("Content-Type", "application/octet-stream;")
        response.setHeader("Content-Disposition", "attachment;filename=${fileName}")
        response.setHeader("Content-Length", "${file.size()}")

        def bytes = file.readBytes()
        for(b in bytes) {
            os.write(b)
        }
    }
}
