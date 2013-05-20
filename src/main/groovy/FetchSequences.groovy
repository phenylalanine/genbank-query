/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 5/18/13
 * Time: 10:50 PM
 * To change this template use File | Settings | File Templates.
 */
enum FetchSequences {
    PROTEIN("protein"),
    NUCCORE("nuccore"),
    NUCGSS("nucgss"),
    NUCEST("nucest"),
    GENOME("genome"),
    POPSET("popset")

    private final String value

    FetchSequences(String value) {
        this.value = value
    }

    def String value(){
        return value
    }

    def String toString(){
        return value
    }
}
