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
        let h2 = document.createElement("h2");
        h2.innerHTML = (`${myjson[i].naam} the ${myjson[i].race} ${myjson[i].klasse.type}`);
        h2.className = "character";
        charactergrid.append(h2);
    }
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
