async function sendJsonData(event) {
    let formData = new FormData(document.querySelector("#characterform"))
    let jsonRequestBody = {};

    formData.forEach((value, key) => jsonRequestBody[key] = value);
    let fetchOptions = {
        method: 'POST',
        body: JSON.stringify(jsonRequestBody),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem("JWT")
        }
    }

    let response = await fetch("/restservices/addcharacter", fetchOptions);

    if (response.ok){
        window.location.href="characterselect.html";
    }

}

async function cancelFunc(event){
    window.location.href="characterselect.html";
}

function logout(){
    window.sessionStorage.setItem("JWT", "");
}

function addCharacter(event){
    window.location.href="addcharacter.html";
}

function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
}
