package busker

class Busker {

    String name
    BigDecimal epsilon
    BigDecimal paymentsEarned

    SortedSet placesToPlay, roundsPlayed
    static hasMany = [placesToPlay : PlaceToPlay, roundsPlayed : Round]
    static belongsTo = [experiment: Experiment]

    static constraints = {
        name ()
        epsilon(min:0.0, max:1.0)
        paymentsEarned()
        placesToPlay()
        experiment()
    }

    String toString() {"${experiment}/Busker${id}"}

    void playAndGetPayed() {
        PlaceToPlay ptp = placeToPlay()
        if (ptp == null)
            return
        BigDecimal reward = ptp.pay()
        paymentsEarned += reward
        Busker.executeUpdate("update Busker b set b.paymentsEarned = ${paymentsEarned} where b.id = ${id}")
        Round round = new Round (roundNo: this.experiment.noOfRoundsPlayed,
                            rewardPaid: reward,
                            regret:regret,
                            experiment : this.experiment,
                            busker:this,
                            rewardFrom:ptp)
        round.save(flush:true)
    }

    PlaceToPlay placeToPlay() {
        PlaceToPlay ptp = null
        if ((BigDecimal)Math.random() < epsilon) {
            //select random place
            int randNo = randInt(1, placesToPlay.size())
            int i = 0

            placesToPlay.each {PlaceToPlay it ->
                i++
                if (randNo == i)
                    ptp = it
            }
        }
        else {
            BigDecimal max = 0
            PlaceToPlay itMax = null

            placesToPlay.each {PlaceToPlay it ->
                if (it.perRoundPayment > max) {
                    max = it.perRoundPayment
                    itMax = it
                }
            ptp = itMax ?:placesToPlay.first()
            }
        }
        return ptp
    }

    BigDecimal getRegret() {
        return experiment.maxReward * experiment.noOfRoundsPlayed - paymentsEarned
    }

    BigDecimal getAvgRegret() {
        return noOfRounds > 0 ? regret/noOfRounds : 0
    }

    int getNoOfRounds () {
        roundsPlayed.size()
    }

    int randInt(int min, int max) {
        return (int) ((Math.random() * (max + 1 - min)) + min)
    }
}
