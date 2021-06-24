async function loadCharacters(event){

    let fetchOptions = {
        method: 'GET',
        headers: {
            "Authorization": "Bearer " + window.sessionStorage.getItem("JWT")
        }
    }
    await fetch("/restservices/characters", fetchOptions)
        .then(res => res.json())
        .then(data => showCharacters(data));
}

function showCharacters(myjson){
    let charactergrid = document.querySelector("#charactergrid");

    for (var i = 0; i < myjson.length; i++){
        let div = document.createElement("div");
        div.innerHTML = (`${myjson[i].naam} the ${myjson[i].race} ${myjson[i].klasse.type}`);
        let naam = myjson[i].naam;
        div.className = "character";
        div.onclick = function() {
            window.sessionStorage.setItem("character", naam);
            window.location.href = "character.html";
        }
        charactergrid.append(div);
    }
}

function logout(){
    window.sessionStorage.removeItem("JWT");
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
