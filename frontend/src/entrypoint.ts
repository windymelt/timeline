console.log("hello, typescript!");

interface Router {
    tag: string,
    body: () => unknown
}

var router: Router[] = []

const route = (tag: string, body: () => unknown) => {
    router.push({ tag: tag, body: body })
}

const dispatch = (tag: string): void => {
    const handler = router.find((route) => route.tag === tag)
    if (handler) {
        handler.body()
    }
}

route("event_editor", () => {
    console.log("edit event dispatched")
});

document.addEventListener("DOMContentLoaded", () => {
    const pagetags = document.querySelector("body")!.dataset["pageTags"]
    const tags = pagetags.split(' ')
    tags.forEach((tag) => {
        dispatch(tag)
    })
});