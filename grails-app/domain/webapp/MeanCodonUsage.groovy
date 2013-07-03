package webapp

class MeanCodonUsage {

    Integer organismId

    // use codon as key, e.g. "ACG"
    // there are 64 codons total
    Map<String, BigDecimal> distribution

    static constraints = {
        organismId unique: true, blank: false
    }
}
