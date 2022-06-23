const root = "http://localhost:8080/api/"

const endpoints = {
    getItems : "item/",
    deleteItem : "item/",
    postItem : "item/",
    postList : "list/",
    deleteList : "list/",
    getLists : "list/"
}

const getEndpoint = (name) => {
    if (endpoints[name] === undefined) {
        console.warn(`Unknown endpoint name: ${name}`)
        return ""
    }
    return root + endpoints[name]
}

export {getEndpoint}