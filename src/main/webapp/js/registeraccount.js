async function sendJsonData(event) {
    let formData = new FormData(document.querySelector("#registerform"))
    let jsonRequestBody = {};

    formData.forEach((value, key) => jsonRequestBody[key] = value);
    let fetchOptions = {
        method: 'POST',
        body: JSON.stringify(jsonRequestBody),
        headers: {'Content-Type': 'application/json'}
    }

    await fetch("restservices/registeraccount", fetchOptions)
        .then(response => {if (response.status === 401){
            window.alert("Passwords do not match!");
    }
        else{
            window.location.href="index.html";
        }})
}
