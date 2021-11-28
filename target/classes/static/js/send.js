const requestURL = 'http://localhost:0080/ConvertService.ashx'

function sendRequest(method, url, body = null) {
    return new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest()

        xhr.open(method, url)


        xhr.setRequestHeader('Content-Type', 'application/json')


        xhr.onload = () => {
            if (xhr.status >= 400) {
                reject(xhr.response)
            } else {
                resolve(xhr.response)
            }
        }

        xhr.onerror = () => {
            reject(xhr.response)
        }

        xhr.send(JSON.stringify(body))
    })
}

// sendRequest('POST', requestURL, body)
//     .then(data => console.log(data))
//     .catch(err => console.log(err))

const body = {


    "async" : false,
    "key": "Khirz6zTPdfd7",
     "filetype": "docx",
     "outputtype": "pdf",
     "title": "Example Document Title.docx",
    "url": "http://localhost:8080/covers/1.docx"
}
const body2 = {
    "c":"version"
}
//https://documentserver/ConvertService.ashx?async=true&key=hi&filetype=docx&outputtype=pdf&title=xex&url=localhost:8080/covers/1.docx
sendRequest('POST', requestURL, body)
    .then(data => console.log(data))
    .catch(err => console.log(err))
sendRequest('POST', requestURL, body2)
    .then(data => console.log(data))
    .catch(err => console.log(err))
