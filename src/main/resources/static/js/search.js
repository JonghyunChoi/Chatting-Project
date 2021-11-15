function filter() {
    let value, card, title, i;

    value = document.getElementById("value").value.toUpperCase();
    card = document.getElementsByClassName("card");

    for (i = 0; i < card.length; i++) {
        title = card[i].getElementsByClassName("room-title");

        if(title[0].innerHTML.toUpperCase().indexOf(value) > -1) {
            card[i].style.display = "flex";
        } else
            card[i].style.display = "none";
    }
}