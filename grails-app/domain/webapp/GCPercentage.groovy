package webapp

class GCPercentage {
    Integer organismId
    BigDecimal gcPercentage

    static constraints = {
        organismId unique: true, blank: false
        gcPercentage blank: false, range: 0..100
    }
}
