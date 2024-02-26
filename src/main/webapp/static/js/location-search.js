const API_URL = "http://localhost:8080/api"

let timer;
const waitTime = 1000;
const locationInput = document.getElementById('location-input');
let countryArr = []

locationInput.addEventListener('keyup', event => {
    if (!(event.keyCode == 8 || event.keyCode >= 69 && event.keyCode <= 90)) {
        return;
    }
    clearTimeout(timer);
    timer = setTimeout(() => {
        doneTyping(event.target.value);
    }, waitTime);
});

locationInput.addEventListener('change', event => {
    let hidden = document.getElementById("hidden");
    for(let i = 0; i < countryArr.length; i++) {
        if (countryArr[i].alias == event.target.value) {
            hidden.value = countryArr[i].lat + ',' + countryArr[i].lon
        }
    }
});

function doneTyping(value) {
    console.log(`The user is done typing: ${value}`);
    if (value == '' || value == null) {
        return;
    }
    $.ajax({
        url: `${API_URL}/location?name=`+value,
        type: 'GET',
        contentType: 'application/json',
        success: function(data) {
            let locationsDataset = document.getElementById('locations');
            locationsDataset.innerHTML = '';
            let json = JSON.parse(data);
            json.forEach((obj) => {
                let countryInfo = {}
                let str = '';
                if (obj.country != null) {
                    str += obj.country;
                }
                if (obj.state != null) {
                    str += ', ' + obj.state;
                }
                if (obj.name != null) {
                    str += ', ' + obj.name;
                }
                countryInfo.alias = str;
                countryInfo.lat = obj.lat;
                countryInfo.lon = obj.lon;
                countryArr.push(countryInfo);
            })

            for (let i = 0; i < countryArr.length; i++) {
                var option = document.createElement("option");
                option.value = countryArr[i].alias;
                option.label = countryArr[i].lat + ', ' + countryArr[i].lat;
                locationsDataset.appendChild(option);
            }

            let searchInput = document.getElementById("location-input");
            console.log(searchInput.value);
        }
    })
}
