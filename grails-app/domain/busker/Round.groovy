package busker

class Round implements Comparable{

    int roundNo
    BigDecimal rewardPaid
    BigDecimal regret

    static belongsTo = [experiment: Experiment, busker : Busker, rewardFrom: PlaceToPlay]

    static constraints = {
        roundNo()
        rewardPaid()
        regret()
        experiment()
        busker()
        rewardFrom()
    }

    String toString() {"${experiment}/Round${roundNo}"}

    int compareTo(obj) {
        id.compareTo(obj.id)
    }


}
