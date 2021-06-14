async function sendJsonData(event) {
    let formData = new FormData(document.querySelector("#characterform"))
    let jsonRequestBody = {};

    formData.forEach((value, key) => jsonRequestBody[key] = value);
    let fetchOptions = {
        method: 'POST',
        body: JSON.stringify(jsonRequestBody),
        headers: {'Content-Type': 'application/json'}
    }

    await fetch("/restservices/addcharacter/1", fetchOptions);

    window.location.href="characterselect.html";

}

async function cancelFunc(event){
    window.location.href="characterselect.html";
}
