package busker

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

class ExpGroup {

    String name

    SortedSet experiments
    static hasMany = [experiments: Experiment]

    static constraints = {
        name()
        experiments(nullable:true)
    }

    String toString() {"ExpGroup:${name}"}

    HSSFWorkbook experimentsToExcel () {

        if (experiments.isEmpty())
            return

        Workbook wb = new HSSFWorkbook()
        Sheet sheet = wb.createSheet("Rounds")

        Experiment expFirst = experiments.first()
        for (i in 0..<expFirst.noOfRoundsPlayed + 1) {
            sheet.createRow(i)
        }

        int i = 0
        experiments.each {Experiment it ->
            i++
            Cell c0
            c0 = sheet.getRow(0).createCell((i-1)*3)
            c0.setCellValue("ε=${it.busker_ε},reward")
            c0 = sheet.getRow(0).createCell((i-1)*3+1)
            c0.setCellValue("ε=${it.busker_ε},regret")
            c0 = sheet.getRow(0).createCell((i-1)*3+2)
            c0.setCellValue("ε=${it.busker_ε},meanRegret")
            it.rounds.each {Round round -> {
                Cell c
                c = sheet.getRow(round.roundNo).createCell((i-1)*3)
                c.setCellValue(round.rewardPaid as BigDecimal)
                c = sheet.getRow(round.roundNo).createCell((i-1)*3+1)
                c.setCellValue(round.regret as BigDecimal)
                c = sheet.getRow(round.roundNo).createCell((i-1)*3+2)
                c.setCellValue(round.regret/round.roundNo as BigDecimal)
                }
            }
        }
        wb
    }
}
