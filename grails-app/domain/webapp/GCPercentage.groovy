package webapp

class GCPercentage {
    Integer organismId
    String gcPercentage

    static constraints = {
        organismId unique: true, blank: false
        gcPercentage blank: false
    }
}
