let characterJson = null;

function logout(){
    window.sessionStorage.removeItem("JWT");            // Remove both the JWT and the character data from the sessionStorage
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
    let charactername = window.sessionStorage.getItem("character");     // Get the charactername from the sessionstorage, will be used to fetch properly

    let fetchOptions = {                                                     // Declare the fetch options to be used.
        method: 'GET',
        headers: {
            "Authorization": "Bearer " + window.sessionStorage.getItem("JWT")   // Pass the JWT to gain access
        }
    }

    await fetch(`restservices/characters/${charactername}`, fetchOptions)    // fetch to the CharactersResource
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

    await fetch(`restservices/${charactername}/item/new`, fetchOptions)
        .then(res => {
            if (!res.ok){
                if (res.status === 400) {
                    let error = document.querySelector("#errordiv");            // Error code 400 is thrown when name or description are not filled in:
                    error.innerHTML = "Name and description required!"                  // Display the error to the user.
                } else {
                    window.alert(`Something went wrong adding the item! Status: ${res.status}`)
                }
            } else {
                window.location.href="character.html";
            }
        })
}
