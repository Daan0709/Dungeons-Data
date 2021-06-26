let characterJson = null;

function logout(){
    window.sessionStorage.removeItem("JWT");
    window.sessionStorage.removeItem("character");
}

function removeCharacter(){
    window.sessionStorage.removeItem("character");
}

function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
}

function cancelFunc(){
    window.location.href="character.html";
}

async function setCharacterJson(){
    let charactername = window.sessionStorage.getItem("character");

    let fetchOptions = {
        method: 'GET',
        headers: {
            "Authorization": "Bearer " + window.sessionStorage.getItem("JWT")
        }
    }

    await fetch(`restservices/characters/${charactername}`, fetchOptions)
        .then(res => res.json())
        .then(json => {
            characterJson = json;
        })
}

async function sendJsonData(){
    let charactername = window.sessionStorage.getItem("character");
    let formData = new FormData(document.querySelector("#itemform"))
    let jsonRequestBody = {};

    formData.forEach(((value, key) => jsonRequestBody[key] = value))

    let fetchOptions = {
        method: 'POST',
        body: JSON.stringify(jsonRequestBody),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem("JWT")
        }
    }

    await fetch(`restservices/${charactername}/skill/new`, fetchOptions)
        .then(res => {
            if (!res.ok){
                if (res.status === 400) {
                    let error = document.querySelector("#errordiv");
                    error.innerHTML = "Name and description required!"
                } else {
                    window.alert(`Something went wrong adding the skill! Status: ${res.status}`)
                }
            } else {
                window.location.href="character.html";
            }
        })
}
