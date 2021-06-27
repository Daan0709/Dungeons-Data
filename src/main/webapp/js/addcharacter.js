async function sendJsonData(event) {
    let formData = new FormData(document.querySelector("#characterform"))
    let jsonRequestBody = {};                   // Make en empty json array, will be used to store all the formdata in later.

    formData.forEach((value, key) => jsonRequestBody[key] = value); // Store all the formdata in the json array
    let fetchOptions = {                                                                           // Set all the fetchoptions.
        method: 'POST',
        body: JSON.stringify(jsonRequestBody),                                                      // Stringify the array and send it along with the fetch request.
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem("JWT")
        }
    }

    let response = await fetch("/restservices/addcharacter", fetchOptions);                   // Fetch to the addAndDeleteCharacterResource

    if (response.ok){
        window.location.href="characterselect.html";                                                // When successful, go to the characterselect screen.
    } else {
        let errordiv = document.querySelector("#errordiv");                                 // Else, give an error that not all the fields were filled in.
        errordiv.innerHTML = "Please fill in all of the fields";
    }

}

async function cancelFunc(event){
    window.location.href="characterselect.html";
}

function logout(){
    window.sessionStorage.removeItem("JWT");            // Remove both the JWT and the character data from the sessionStorage
    window.sessionStorage.removeItem("character");
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
