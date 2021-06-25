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

async function loadStats(){             // Function to set all the values of the number inputs to the existing values of the character
    await setCharacterJson();
    let strength = document.querySelector("#strength")
    let dexterity = document.querySelector("#dexterity")
    let constitution = document.querySelector("#constitution")
    let intelligence = document.querySelector("#intelligence")
    let wisdom = document.querySelector("#wisdom")
    let charisma = document.querySelector("#charisma")

    strength.setAttribute("value", characterJson.stats[0].score)
    dexterity.setAttribute("value", characterJson.stats[1].score)
    constitution.setAttribute("value", characterJson.stats[2].score)
    intelligence.setAttribute("value", characterJson.stats[3].score)
    wisdom.setAttribute("value", characterJson.stats[4].score)
    charisma.setAttribute("value", characterJson.stats[5].score)
}

function cancelFunc(){
    window.location.href="character.html";
}

async function sendJsonData(){
    let charactername = window.sessionStorage.getItem("character");
    let formData = new FormData(document.querySelector("#statsform"))
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

    await fetch(`restservices/characters/${charactername}/stats`, fetchOptions)
        .then(res => {
            if (!res.ok){
                if (res.status === 400){
                    window.alert("Your stats can't go below -30 or above 30");
                } else {
                    window.alert(`Something went wrong changing the stats! Status: ${res.body}`)
                }
            } else {
                window.location.href="character.html";
            }
        })
}
