console.log('Hello from script.js');

let currentTheme = getTheme();
changeTheme(currentTheme)

//change theme
function changeTheme() {
    //set to web page
    document.querySelector("html").classList.add(currentTheme);

    //set the listener to change theme button
    const changeThemeButton=document.querySelector("#theme_change_button");
    

    changeThemeButton.addEventListener("click",(event)=> {
        const oldTheme=currentTheme;
        console.log("change theme button clicked");
        if(currentTheme==="dark"){
            currentTheme="light";
        }else{
            currentTheme="dark";
        }
    //change the text
    changeThemeButton.querySelector("span").textContent=currentTheme=="light"?"Dark":"Light";

        //localstorage update
        setTheme(currentTheme);
        document.querySelector("html").classList.remove(oldTheme);

        document.querySelector("html").classList.add(currentTheme);

        

    });
}

//set theme to local storage
function setTheme() {
    localStorage.setItem("theme", currentTheme);
}

//get theme from local storage
function getTheme() {
    let theme = localStorage.getItem("theme");
   return theme? theme : "light";
}
