package busker

class Experiment implements Comparable {

    int expNo
    int noOfPlacesToPlay
    int minMeanPayOfOnePlace
    int maxMeanPayOfOnePlace
    String buskerName
    BigDecimal busker_ε
    int noOfRoundsPlayed

    SortedSet placesToPlay, rounds
    static hasMany = [placesToPlay: PlaceToPlay, rounds: Round]
    static belongsTo = [expGroup: ExpGroup, busker:Busker]

    static constraints = {
        expNo()
        noOfPlacesToPlay(min:2)
        minMeanPayOfOnePlace()
        maxMeanPayOfOnePlace()
        busker(nullable:true)
        buskerName()
        busker_ε()
        expGroup(nullable:true)
        noOfRoundsPlayed()
        placesToPlay(nullable:true)
        rounds(nullable:true)
    }

    String toString() {"Exp${expNo}"}

    int compareTo(obj) {
        id.compareTo(obj.id)
    }

    void generatePlacesToPlay () { //+ 1 Busker to play there
        Busker b = new Busker (name : buskerName,
                                epsilon: busker_ε,
                                paymentsEarned: 0,
                                experiment:this)
        b.save()
        Experiment.executeUpdate("update Experiment e set e.busker = ${b} where e.id = ${id}")
        for (i in 1..<noOfPlacesToPlay+1) {
            PlaceToPlay ptp =
            new PlaceToPlay  (placeNo:i,
                              meanPayment: randInt(minMeanPayOfOnePlace,maxMeanPayOfOnePlace),
                              perRoundPayment: 0.0,
                              timesPlayed: 0,
                              sumPaid:0,
                              busker:b,
                              experiment: this)
            ptp.save()
        }
    }

    void runMult (int noOfRuns) {
        for (i in 1..<noOfRuns+1) {
            runDay()
        }
    }

    void runDay () {
        noOfRoundsPlayed++
        Experiment.executeUpdate("update Experiment e set e.noOfRoundsPlayed = ${noOfRoundsPlayed} where e.id = ${id}")
        Busker b = Busker.find("from Busker as b where b.experiment.id = ${id}")
        if (b != null)
            b.playAndGetPayed()
    }

    BigDecimal getMaxReward () {
        BigDecimal max = 0.0
        placesToPlay.each {PlaceToPlay it ->
            if (it.meanPayment > max)
                max = it.meanPayment
        }
        max
    }

    int randInt(int min, int max) {
        return (int) ((Math.random() * (max + 1 - min)) + min)
    }
}
