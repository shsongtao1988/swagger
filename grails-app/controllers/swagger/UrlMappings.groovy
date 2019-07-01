package swagger

class UrlMappings {

    static mappings = {
//        "/$controller/$action?/$id?(.$format)?"{
//            constraints {
//                // apply constraints here
//            }
//        }

        get "/api/user/myDemo"(controller:"myDemo",action:"index")
        get "/api/user/myDemo/$id"(controller:"myDemo",action:"show")

        post "/api/user/$controller/"(action: "save")
        put "/api/user/$controller/$id"(action: "update")
        patch "/api/user/$controller/$id"(action: "patch")


        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
