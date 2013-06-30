package webapp

class MeanCodonUsage {

    Integer organismId
    Map distribution    // use codon as key, e.g. "ACG"
    // there are 64 codons total

    static hasMany = [distribution: double]

    static constraints = {
        organismId unique: true, blank: false
    }
}
