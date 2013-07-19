class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

        "/" {
            controller = "main"
        }
		"/grails"(view:"/grails-index")
		"500"(view:'/error')
	}
}
