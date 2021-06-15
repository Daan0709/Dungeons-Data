async function sendJsonData(event) {
    let formData = new FormData(document.querySelector("#registerform"))
    let jsonRequestBody = {};

    formData.forEach((value, key) => jsonRequestBody[key] = value);
    let fetchOptions = {
        method: 'POST',
        body: JSON.stringify(jsonRequestBody),
        headers: {'Content-Type': 'application/json'}
    }

    await fetch("restservices/account/register", fetchOptions)
        .then(response => {
            if (response.status === 401){
                window.alert("Passwords do not match!");
            }
            else if (response.status === 400){
                window.alert("Fill in all the fields!");
            }
            else{
                window.location.href="index.html";
            }})
}
