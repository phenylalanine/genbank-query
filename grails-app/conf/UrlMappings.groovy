class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

        "/_search_name" {
            controller = "searchOrganism"
        }

        "/" {
            controller = "main"
        }
		"/grails"(view:"/grails-index")
		"500"(view:'/error')
	}
}
