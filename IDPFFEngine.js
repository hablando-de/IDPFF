/* 
 * IDPFFENgine.js
 */



function rotateElement(elementId, degrees) {

    var element = $('#' + elementId);
    var value = "rotate(" + degrees + "deg)";
    element.css({"transform": value,
        "-webkit-transform": value,
        "-moz-transform": value,
        "-o-transform": value});
    
}

function rotateElementT(elementId, degrees, duration) {
    $('#' + elementId).css({"transition": "transform " + duration +"s",
        "-webkit-transition": "-webkit-transform " + duration +"s",
        "-moz-transition": "-moz-transform " + duration +"s",
        "-o-transition": "-o-transform " + duration +"s"});
    rotateElement(elementId,degrees);
    

}

function moveElement(elementId, moveX, moveY) {
    
    var element = $('#' + elementId);
    var value = "translate(" + moveX + "px,"+ moveY+"px)";
    element.css({"transform": value,
        "-webkit-transform": value,
        "-moz-transform": value,
        "-o-transform": value});
}

function moveElementT(elementId, moveX, moveY, duration) {
    
    $('#' + elementId).css({"transition": "transform " + duration +"s",
        "-webkit-transition": "-webkit-transform " + duration +"s",
        "-moz-transition": "-moz-transform " + duration +"s",
        "-o-transition": "-o-transform " + duration +"s"});
    moveElement(elementId,moveX,moveY);
}

function scaleElement(elementId, scaleX, scaleY) {
    
    var element = $('#' + elementId);
    var value = "scale(" + scaleX + ","+ scaleY+")";
    element.css({"transform": value,
        "-webkit-transform": value,
        "-moz-transform": value,
        "-o-transform": value});
}

function scaleElementT(elementId, scaleX, scaleY, duration) {
    
    $('#' + elementId).css({"transition": "transform " + duration +"s",
        "-webkit-transition": "-webkit-transform " + duration +"s",
        "-moz-transition": "-moz-transform " + duration +"s",
        "-o-transition": "-o-transform " + duration +"s"});
    scaleElement(elementId,scaleX,scaleY);
}



function transformElement(elementId, degrees, scaleX, scaleY, moveX, moveY){
    var element = $('#' + elementId);
    var value = " scale(" + scaleX + ","+ scaleY+") ";
    value +=  " rotate(" + degrees + "deg) ";
    value += " translate(" + moveX + "px, " + moveY + "px) ";
    element.css({"transform": value,
        "-webkit-transform": value,
        "-moz-transform": value,
        "-o-transform": value});
}

function setTransitionDurationToElement(elementId, duration) {    
    $('#' + elementId).css({"transition": "transform " + duration +"s",
        "-webkit-transition": "-webkit-transform " + duration +"s",
        "-moz-transition": "-moz-transform " + duration +"s",
        "-o-transition": "-o-transform " + duration +"s"});
}

function resetElement(elementId){
    transformElement(elementId,0,1,1,0,0);
}