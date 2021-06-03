async function sendTestJsonData(event) {
    let formData = new FormData(document.querySelector("#characterform"))
    let jsonRequestBody = {};

    formData.forEach((value, key) => jsonRequestBody[key] = value);
    console.log(jsonRequestBody);
    let fetchOptions = {
        method: 'POST',
        body: JSON.stringify(jsonRequestBody),
        headers: {'Content-Type': 'application/json'}
    }

    await fetch("/restservices/addcharacter/1", fetchOptions);
}