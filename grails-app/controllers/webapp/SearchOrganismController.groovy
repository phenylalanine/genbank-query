package webapp

class SearchOrganismController {

    def index() {
        def query = request.getParameter('query')
        def sciNames = Organism.list().collect { it.scientificName }
        def matches = []

        if (query == null) {
            response.sendError(400, "Bad Request: 'query' not found")
        }

        if (query.size() > 0) {
            matches = sciNames.findAll { it.startsWith(query) }
        }

        render(contentType: "application/json") {
            results = matches
        }
    }
}