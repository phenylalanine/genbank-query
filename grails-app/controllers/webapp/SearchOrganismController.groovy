package webapp

class SearchOrganismController {

    static String WILDCARD = "*"

    def index() {
        def query = request.getParameter('query')
        def matches = []

        if (query == null) {
            response.sendError(400, "Bad Request: 'query' not found")
        }

        if (query.size() > 0) {
            matches = Organism.searchEvery(WILDCARD + query + WILDCARD).collect { it.scientificName }
        }

        render(contentType: "application/json") {
            results = matches
        }
    }
}