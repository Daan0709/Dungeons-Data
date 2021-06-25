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
