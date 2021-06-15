async function login() {
    let formData = new FormData(document.querySelector("#loginform"));
    let jsonRequestBody = {};

    formData.forEach((value, key) => jsonRequestBody[key] = value);
    let fetchOptions = {
        method: 'POST',
        body: JSON.stringify(jsonRequestBody),
        headers: {'Content-Type': 'application/json'}
    }

    let response = await fetch("restservices/login", fetchOptions);

    if (response.ok){
        let myJson = await response.json();
        window.sessionStorage.setItem("JWT", myJson.JWT);
        window.location.href="characterselect.html";
    } else {
        window.alert("Invalid Login!")
    }
}
