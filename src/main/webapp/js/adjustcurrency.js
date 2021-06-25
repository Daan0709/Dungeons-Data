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

async function loadCurrency(){      // Function to load in all the existing values of the currencies
    await setCharacterJson();
    console.log(characterJson);
    let platinum = document.querySelector("#platinum");
    let gold = document.querySelector("#gold");
    let silver = document.querySelector("#silver");
    let copper = document.querySelector("#copper");

    platinum.setAttribute("value", characterJson.currency[0].aantal)
    gold.setAttribute("value", characterJson.currency[1].aantal)
    silver.setAttribute("value", characterJson.currency[2].aantal)
    copper.setAttribute("value", characterJson.currency[3].aantal)
}

async function sendJsonData() {
    let charactername = window.sessionStorage.getItem("character");
    let formData = new FormData(document.querySelector("#currencyform"))
    let jsonRequestBody = {};

    formData.forEach(((value, key) => jsonRequestBody[key] = value))

    let fetchOptions = {
        method: 'PUT',
        body: JSON.stringify(jsonRequestBody),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem("JWT")
        }
    }

    await fetch(`restservices/characters/${charactername}/currency`, fetchOptions)
        .then(res => {
            if (!res.ok){
                if (res.status === 400){
                    window.alert("Currencies can't go below 0!");
                } else {
                    window.alert(`Something went wrong adjusting the currency! Status: ${res.status}`)
                }
            } else {
                window.location.href="character.html";
            }
        })
}
