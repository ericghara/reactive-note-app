
const iconRoot = "/icons/"
const iconFilename =  {
    arrowDown : "arrow-ios-downward-outline.svg",
    arrowUp : "arrow-ios-upward-outline.svg",
    add : "file-add-outline.svg",
    checkMark : "checkmark-circle-outline.svg",
    edit : "edit-2-outline.svg",
    more: "more-vertical-outline.svg",
    send: "paper-plane-outline.svg",
    save : "save-outline.svg",
    trash : "trash-outline.svg",
    undo : "undo-outline.svg"
}

const get = (iconName) => iconRoot + iconFilename[iconName]

const IconService = { get }

export { IconService }