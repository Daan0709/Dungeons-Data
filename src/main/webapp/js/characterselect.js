async function loadCharacters(event){
    let accountId = 1;

    let response = await fetch("/restservices/characters/" + accountId).then(res => res.json()).then(data => showCharacters(data));
}

function showCharacters(myjson){
    let charactergrid = document.querySelector("#charactergrid");

    console.log(myjson);
    for (var i = 0; i < myjson.length; i++){
        let h2 = document.createElement("h2");
        h2.innerHTML = (`${myjson[i].naam} the ${myjson[i].race} ${myjson[i].klasse.type}`);
        h2.id = "character"
        charactergrid.append(h2);
    }

}

function addCharacter(event){
    window.location.href="addcharacter.html";
}