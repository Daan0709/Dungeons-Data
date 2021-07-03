let characterJson = null;

window.onclick = function(event) {                      // Function that closes any modal window that's currently opened when clicked outside of it
    let modal = document.querySelector(".modal")
    let spellslotmodal = document.querySelector("#spellslotModal")
    let xpmodal = document.querySelector("#xpModal")
    if (event.target == modal) {
        modal.style.display = "none";
    } else if (event.target == spellslotmodal) {
        spellslotmodal.style.display = "none";
    } else if (event.target == xpmodal) {
        xpmodal.style.display = "none";
    }
}

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

async function loadCharacter(event){
    await setCharacterJson();
    showCharacter(characterJson);
    console.log(characterJson);
}

function showCharacter(characterJson){
    // Selecting all the different IDs in the character.html file, then setting the
    // correct corresponding data.
    document.title = window.sessionStorage.getItem("character");
    let characterName = document.querySelector("#character");
    let hitpoints = document.querySelector("#hitpoints");
    let strength = document.querySelector("#strength")
    let strengthModifier = document.querySelector("#strengthmodifier")
    let dexterity = document.querySelector("#dexterity")
    let dexterityModifier = document.querySelector("#dexteritymodifier")
    let constitution = document.querySelector("#constitution")
    let constitutionModifier = document.querySelector("#constitutionmodifier")
    let intelligence = document.querySelector("#intelligence")
    let intelligenceModifier = document.querySelector("#intelligencemodifier")
    let wisdom = document.querySelector("#wisdom")
    let wisdomModifier = document.querySelector("#wisdommodifier")
    let charisma = document.querySelector("#charisma")
    let charismaModifier = document.querySelector("#charismamodifier")
    let totalCurrency = document.querySelector("#totalcurrency")
    let platinum = document.querySelector("#platinum")
    let gold = document.querySelector("#gold")
    let silver = document.querySelector("#silver")
    let copper = document.querySelector("#copper")

    characterName.innerHTML = `${characterJson.naam} the level ${characterJson.level} ${characterJson.race} ${characterJson.klasse.type}`;
    hitpoints.innerHTML = `Hitpoints: ${characterJson.hitpoints}/${characterJson.maxHitpoints}`
    hitpoints.onclick = function(){adjustHp()}
    strength.innerHTML += characterJson.stats[0].score
    strengthModifier.innerHTML = characterJson.stats[0].modifier
    dexterity.innerHTML += characterJson.stats[1].score
    dexterityModifier.innerHTML = characterJson.stats[1].modifier
    constitution.innerHTML += characterJson.stats[2].score
    constitutionModifier.innerHTML = characterJson.stats[2].modifier
    intelligence.innerHTML += characterJson.stats[3].score
    intelligenceModifier.innerHTML = characterJson.stats[3].modifier
    wisdom.innerHTML += characterJson.stats[4].score
    wisdomModifier.innerHTML = characterJson.stats[4].modifier
    charisma.innerHTML += characterJson.stats[5].score
    charismaModifier.innerHTML = characterJson.stats[5].modifier
    totalCurrency.innerHTML = "Total gold: " + characterJson.totalCurrency
    platinum.innerHTML = characterJson.currency[0].aantal
    gold.innerHTML = characterJson.currency[1].aantal
    silver.innerHTML = characterJson.currency[2].aantal
    copper.innerHTML = characterJson.currency[3].aantal

    setWeight(); // Set the weight
    setItems(); // Set all the items
    setSkills(); // Set all the skills
    setSpells(); // Set all the spells

    // Setting the correct amount of spellslots:
    setSpellslots();
}
async function increaseItem(itemname){
    let charactername = window.sessionStorage.getItem("character");
    let fetchOptions = {
        method: 'PUT',
        headers: {
            "Authorization": "Bearer " + window.sessionStorage.getItem("JWT")
        }
    }

    await fetch(`restservices/${charactername}/item/increase/${itemname}`, fetchOptions)
        .then(async res => {
            if (!res.ok){
                window.alert(`Something went wrong increasing the item amount! Status: ${res.status}`)
            }
            await setCharacterJson();  // When successful, update the characterJson.
            setItems();                // Overwrite the current itemlist.
            setWeight();               // Update the weight.
        })
}

async function decreaseItem(itemname){
    let charactername = window.sessionStorage.getItem("character");
    let fetchOptions = {
        method: 'PUT',
        headers: {
            "Authorization": "Bearer " + window.sessionStorage.getItem("JWT")
        }
    }

    await fetch(`restservices/${charactername}/item/decrease/${itemname}`, fetchOptions)
        .then(async res => {
            if (!res.ok){
                window.alert(`Something went wrong decreasing the item amount! Status: ${res.status}`)
            }
            await setCharacterJson();  // When successful, update the characterJson.
            setItems();                // Overwrite the current itemlist.
            setWeight();               // Update the weight.
        })
}

async function removeItem(itemname){
    let charactername = window.sessionStorage.getItem("character");
    let fetchOptions = {
        method: 'DELETE',
        headers: {
            "Authorization": "Bearer " + window.sessionStorage.getItem("JWT")
        }
    }

    await fetch(`restservices/${charactername}/item/remove/${itemname}`, fetchOptions)
        .then(async res => {
            if (!res.ok){
                window.alert(`Something went wrong removing the item! Status: ${res.status}`)
            }
            await setCharacterJson();  // When successful, update the characterJson.
            setItems();                // Overwrite the current itemlist.
            setWeight();               // Update the weight.
        })
}

function closeModal(){
    let modal = document.querySelector(".modal")
    modal.style.display="none";
}

async function removeSkill(name){
    let charactername = window.sessionStorage.getItem("character");
    let fetchOptions = {
        method: 'DELETE',
        headers: {
            "Authorization": "Bearer " + window.sessionStorage.getItem("JWT")
        }
    }

    await fetch(`restservices/${charactername}/skill/remove/${name}`, fetchOptions)
        .then(async res => {
            if (!res.ok){
                window.alert(`Something went wrong removing the skill! Status: ${res.status}`)
            } else {
                await setCharacterJson();  // When successful, update the characterJson.
                setSkills();                // Overwrite the current list of skills.
            }
        })
}

async function removeSpell(name){
    let charactername = window.sessionStorage.getItem("character");
    let fetchOptions = {
        method: 'DELETE',
        headers: {
            "Authorization": "Bearer " + window.sessionStorage.getItem("JWT")
        }
    }

    await fetch(`restservices/${charactername}/spell/remove/${name}`, fetchOptions)
        .then(async res => {
            if (!res.ok){
                window.alert(`Something went wrong removing the spell! Status: ${res.status}`)
            } else {
                await setCharacterJson();  // When successful, update the characterJson.
                setSpells();                // Overwrite the current list of spells.
            }
        })
}

function adjustStats(){
    window.location.href="adjuststats.html";
}

function adjustCurrency(){
    window.location.href="adjustcurrency.html";
}

function adjustHp(){
    let modalcurrenthp = document.querySelector("#currenthp")           // Selecting the input boxes
    let modalmaxhp = document.querySelector("#maxhp")
    modalcurrenthp.setAttribute("value", characterJson.hitpoints);  // Updating their values to corresponding values
    modalmaxhp.setAttribute("value", characterJson.maxHitpoints);
    let modal = document.querySelector(".modal")
    modal.style.display="block";                                                // Open the hitpoints modal window
}

async function removeSpellslot(level){
    let charactername = window.sessionStorage.getItem("character");
    let fetchOptions = {
        method: 'PUT',
        headers: {
            "Authorization": "Bearer " + window.sessionStorage.getItem("JWT")
        }
    }
    await fetch(`restservices/spellslot/${charactername}/${level}/remove`, fetchOptions)  // Webservice for using 1 spellslot.
        .then(async res => {
            if (!res.ok){
                window.alert(`Something went wrong using the spellslot! Status: ${res.status}`);
            }
            await setCharacterJson();     // When successful, set characterJson to the updated one.
            setSpellslots();              // Overwrite the current spellslots.
        })
}

async function useSpellslot(level){
    let charactername = window.sessionStorage.getItem("character");
    let fetchOptions = {
        method: 'PUT',
        headers: {
            "Authorization": "Bearer " + window.sessionStorage.getItem("JWT")
        }
    }
    await fetch(`restservices/spellslot/${charactername}/${level}/use`, fetchOptions)  // Webservice for using 1 spellslot.
        .then(async res => {
            if (!res.ok){
                window.alert(`Something went wrong removing the spellslot! Status: ${res.status}`);
            }
            await setCharacterJson();     // When successful, set characterJson to the updated one.
            setSpellslots();              // Overwrite the current spellslots.
        })
}

function setSpellslots(){
    document.querySelectorAll('.spellslotrow').forEach(e => e.remove());  // Remove all spellslots first to prevent duplication
    let spellslotContainer = document.querySelector(".spellslotcontainer")
    let spellslots = characterJson.spellslots;
    for (var key in spellslots){
        let spellslotRow = document.createElement("div");
        spellslotRow.className = "spellslotrow"
        spellslotContainer.append(spellslotRow)
        var spellslot = spellslots[key]
        let level = spellslot["level"];
        let maxAmount = spellslot["maxAmount"];
        let usedAmount = spellslot["usedAmount"];
        let levelLabel = document.createElement("div");
        levelLabel.innerText = "lvl " + level + ": ";
        levelLabel.className = "levellabel";                    // Give it a classname to remove it whenever setSpellSlots is called.
        spellslotRow.append(levelLabel);
        let spellslotsBubbles = document.createElement("div");
        spellslotsBubbles.className = "spellslotsbubbles";
        spellslotRow.append(spellslotsBubbles);
        for (var j = 0; maxAmount > j; j++) {
            let spellslot = document.createElement("div");
            spellslot.className = "spellslot";
            spellslot.id = "" + level + j;
            spellslot.onclick = function () {
                useSpellslot(level)
            }
            spellslotsBubbles.append(spellslot);
        }

        for (var j = 0; usedAmount > j; j++) {
            let spellslot = document.getElementById(`${level}${j}`)
            spellslot.className += " used"
            spellslot.onclick = function () {
                removeSpellslot(level)
            }
        }
    }
}

function setItems(){
    let itemList = characterJson.itemlist;
    let itemContainer = document.querySelector("#items") // Selecting the div that should contain all items
    itemContainer.innerHTML = "";                           // Clear the container first to stop duplication
    for (var item in itemList){
        let itemDesc = document.createElement("div")
        itemDesc.innerHTML = item;                          // Div contains the item.toString()
        let itemName = item.split(":")[0]

        let quantity = document.createElement("div")
        quantity.innerHTML = itemList[item]                 // Div contains the item quantity

        let buttondiv = document.createElement("div") // Div will contain all the buttons associated with the item
        buttondiv.className = "buttondiv";
        let plusminus = document.createElement("div") // Div container for the plus and minus buttons
        let plus = document.createElement("input")
        let minus = document.createElement("input")
        let remove = document.createElement("input")
        plus.type = "button"; plus.value="+"; plus.onclick=function() {increaseItem(itemName)};
        minus.type = "button"; minus.value="-"; minus.onclick=function() {decreaseItem(itemName)};
        remove.type = "button"; remove.className = "button"; remove.value = "remove"; remove.onclick=function() {removeItem(itemName)}
        plusminus.append(plus)
        plusminus.append(minus)
        buttondiv.append(quantity)
        buttondiv.append(plusminus)
        buttondiv.append(remove)
        itemContainer.append(itemDesc)
        itemContainer.append(buttondiv)
    }
}

function setSpells(){
    let spellContainer = document.querySelector("#spells")
    let spellList = characterJson.spells;
    spellContainer.innerHTML = "";                  // Clear the container first to stop duplication and remove old spells.
    for (var spell in spellList){
        let spellDiv = document.createElement("div");
        spellDiv.innerHTML = `<strong>${spellList[spell].naam}</strong>: ${spellList[spell].beschrijving}. <strong>Duration</strong>: ${spellList[spell].duration}`
        spellContainer.append(spellDiv);

        let remove = document.createElement("input")
        remove.type = "button"; remove.className = "button"; remove.value = "remove"; remove.onclick=function() {removeSpell(spellList[spell].naam)}
        spellContainer.append(remove);
    }
}

function setSkills(){
    let skillContainer = document.querySelector("#skills") // Selecting the div that should contain all skills
    let skillList = characterJson.skills;
    skillContainer.innerHTML = "";                                 // Clear the container to stop duplication and remove old skills.
    for (var skill in skillList){
        let skillDiv = document.createElement("div")
        skillDiv.innerHTML = `<strong>${skillList[skill].naam}</strong>: ${skillList[skill].beschrijving}`
        skillContainer.append(skillDiv)

        let remove = document.createElement("input")
        remove.type = "button"; remove.className = "button"; remove.value = "remove"; remove.onclick=function() {removeSkill(skillList[skill].naam)}
        skillContainer.append(remove)
    }
}
async function clearSpellslots(){
    let charactername = window.sessionStorage.getItem("character");
    let fetchOptions = {
        method: 'PUT',
        headers: {
            "Authorization": "Bearer " + window.sessionStorage.getItem("JWT")
        }
    }
    await fetch(`restservices/spellslot/${charactername}/clearspellslots`, fetchOptions) // Webservice to set the usedSpellslots to 0.
        .then(async res => {
            if (!res.ok){
                window.alert(`Something went wrong clearing the spellslots! Status: ${res.status}`)
            }
            await setCharacterJson();   // When successful, set characterJson to the updated one.
            setSpellslots();            // Call setSpellslots() to overwrite the current spellslots.
        })
}

function setWeight(){
    let weight = document.querySelector("#weight")
    weight.innerHTML = weight.innerHTML = `Weight: ${characterJson.currentWeight}/${characterJson.maxGewicht}`
}

async function sendJsonData(event){
    let charactername = window.sessionStorage.getItem("character")
    let formData = new FormData(document.querySelector("#hpform"))
    let jsonRequestBody = {};

    formData.forEach((value, key) => jsonRequestBody[key] = value);
    let fetchOptions = {
        method: 'PUT',
        body: JSON.stringify(jsonRequestBody),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem("JWT")
        }
    }

    await fetch(`restservices/characters/${charactername}/hp`, fetchOptions)
        .then(async res => {
            if (!res.ok){
                window.alert(`Something went wrong adjusting the HP values! Status: ${res.status}`)
            } else {
                await setCharacterJson();
                let hitpoints = document.querySelector("#hitpoints");
                hitpoints.innerHTML = `Hitpoints: ${characterJson.hitpoints}/${characterJson.maxHitpoints}`;
                closeModal();
            }
        })
}

function addItem(){
    window.location.href="additem.html";
}

function addSpell(){
    window.location.href="addspell.html";
}

function addSkill(){
    window.location.href="addskill.html";
}

function adjustSpellslots(){
    let error = document.querySelector("#spellsloterror")
    error.innerHTML = "";                                                    // Clear the error in case it was still there.
    let modal = document.querySelector("#spellslotModal")
    modal.style.display = "block";                                           // Open the spellslots modal window
}

function closeSpellslotModal(){
    let modal = document.querySelector("#spellslotModal")
    modal.style.display = "none";
}

async function sendSpellslotData(){
    let charactername = window.sessionStorage.getItem("character")
    let formData = new FormData(document.querySelector("#spellslotsform"))
    let jsonRequestBody = {};

    formData.forEach((value, key) => jsonRequestBody[key] = value);
    let fetchOptions = {
        method: 'PUT',
        body: JSON.stringify(jsonRequestBody),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem("JWT")
        }
    }

    await fetch(`restservices/characters/${charactername}/spellslot`, fetchOptions)
        .then(async res => {
            if (!res.ok){
                if (res.status === 400){
                    let error = document.querySelector("#spellsloterror");
                    error.innerHTML = "Amount can't go below 0!";                   // 400 is thrown when a negative number is set, give this warning when that happens.
                } else {
                    window.alert(`Something went wrong adjusting the amount of spellslots! Status: ${res.status}`)
                }
            } else {
                await setCharacterJson();
                setSpellslots();                    // Adjust the amount of spellslots to the new values.
                closeSpellslotModal();              // Close the modal when successful
            }
        })
}

function deletePopup(){
    let modal = document.querySelector("#deleteCharModal")
    modal.style.display = "block";                                           // Open the spellslots modal window
}

function closeDeleteCharModal(){
    let modal = document.querySelector("#deleteCharModal")
    modal.style.display = "none";
}

async function deleteCharacter(){
    let charactername = window.sessionStorage.getItem("character")

    let fetchOptions = {
        method: 'DELETE',
        headers: {
            'Authorization': 'Bearer ' + window.sessionStorage.getItem("JWT")
        }
    }

    await fetch(`restservices/addcharacter/${charactername}/delete`, fetchOptions)
        .then(res => {
            if (!res.ok){
                window.alert(`Something went wrong deleting the character! Status: ${res.status}`)
            } else {
                window.location.href="characterselect.html";
            }
        })
}

function openXpModal(){
    let error = document.querySelector("#xperror")
    error.innerHTML = "";                                                    // Clear the error in case it was still there.
    let currentxp = document.querySelector("#currentxp")
    currentxp.innerHTML = characterJson.experience                           // Set the values of the inputs and labels with corresponding data
    let setxp = document.querySelector("#setxp")
    setxp.setAttribute("value", characterJson.experience);       // ^^^^^
    let modal = document.querySelector("#xpModal")
    modal.style.display = "block";
}

function closeXpModal(){
    let addxp = document.querySelector("#addxp")
    addxp.value = 0
    let setxp = document.querySelector("#setxp")
    setxp.value = characterJson.experience                              // Reset the value of setxp and addxp each time the modal window closes.
    let characterName = document.querySelector("#character")
    characterName.innerHTML = `${characterJson.naam} the level ${characterJson.level} ${characterJson.race} ${characterJson.klasse.type}`;
    let modal = document.querySelector("#xpModal")
    modal.style.display = "none";
}

async function sendXpData(){
    let charactername = window.sessionStorage.getItem("character")
    let formData = new FormData(document.querySelector("#xpform"))
    let jsonRequestBody = {};

    formData.forEach((value, key) => jsonRequestBody[key] = value);
    let fetchOptions = {
        method: 'PUT',
        body: JSON.stringify(jsonRequestBody),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem("JWT")
        }
    }

    await fetch(`restservices/characters/${charactername}/xp`, fetchOptions)
        .then(async res => {
            if (!res.ok){
                if (res.status === 400){
                    let error = document.querySelector("#xperror");
                    error.innerHTML = "Can't set xp below 0!";                   // 400 is thrown when a negative number is set, give this warning when that happens.
                } else {
                    window.alert(`Something went wrong adjusting the amount of spellslots! Status: ${res.status}`)
                }
            } else {
                await setCharacterJson();
                closeXpModal();              // Close the modal when successful
            }
        })
}
