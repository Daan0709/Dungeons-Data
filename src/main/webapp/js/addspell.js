let characterJson = null;

function logout(){
    window.sessionStorage.removeItem("JWT");            // Remove the JWT and character data from the sessionStorage
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
    let charactername = window.sessionStorage.getItem("character");         // Retrieve the charactername from the sessionstorage, will be used to fetch properly.
    let formData = new FormData(document.querySelector("#itemform"))
    let jsonRequestBody = {};                                                    // Declare an empty json array, will be filled in with formdata

    formData.forEach(((value, key) => jsonRequestBody[key] = value))    // Make a json object for each element in the formData object.

    let fetchOptions = {                                                        // Declare the fetch options.
        method: 'POST',
        body: JSON.stringify(jsonRequestBody),                                  // Stringify the jsonRequestBody containing all the formdata and pass it along with fetch
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem("JWT")
        }
    }

    await fetch(`restservices/${charactername}/spell/new`, fetchOptions)  // Fetch to the spellResource
        .then(res => {
            if (!res.ok){
                if (res.status === 400) {
                    let error = document.querySelector("#errordiv");    // Error code 400 is thrown when name or description are not filled in:
                    error.innerHTML = "Name and description required!"          // error is displayed to the user
                } else {
                    window.alert(`Something went wrong adding the spell! Status: ${res.status}`)
                }
            } else {
                window.location.href="character.html";
            }
        })
}
