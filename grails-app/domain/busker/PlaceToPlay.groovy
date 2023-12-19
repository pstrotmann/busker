package busker

import javax.transaction.Transactional

@Transactional
class PlaceToPlay implements Comparable {

    int placeNo
    BigDecimal meanPayment //hidden to busker
    BigDecimal perRoundPayment //known to busker
    int timesPlayed
    BigDecimal sumPaid

    static belongsTo = [experiment: Experiment, busker:Busker]

    static constraints = {
        placeNo()
        meanPayment()
        perRoundPayment()
        timesPlayed()
        sumPaid()
        busker()
        experiment()
    }

    String toString() {"${experiment}/Place${placeNo}"}

    int compareTo(obj) {
        id.compareTo(obj.id)
    }

    BigDecimal pay () {
        BigDecimal payment = randDec(0, 2 * meanPayment)
        sumPaid += payment
        timesPlayed++
        perRoundPayment = sumPaid/timesPlayed

        PlaceToPlay.executeUpdate("update PlaceToPlay p set p.sumPaid = ${sumPaid}, p.timesPlayed = ${timesPlayed}, p.perRoundPayment = ${perRoundPayment}  where p.id = ${id} ")
        return payment
    }

    BigDecimal randDec(BigDecimal min, BigDecimal max) {
        return (BigDecimal) ((Math.random() * (max + 1 - min)) + min)
    }
}
