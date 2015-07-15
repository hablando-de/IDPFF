/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



function rotateElement(elementId, degrees) {

    var element = $('#' + elementId);
    var value = "rotate(" + degrees + "deg)";
    element.css({"transform": value,
        "-webkit-transform": value,
        "-moz-transform": value,
        "-o-transform": value});
    
}

function rotateElement(elementId, degrees, duration) {
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

function moveElement(elementId, moveX, moveY, duration) {
    
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

function scaleElement(elementId, scaleX, scaleY, duration) {
    
    $('#' + elementId).css({"transition": "transform " + duration +"s",
        "-webkit-transition": "-webkit-transform " + duration +"s",
        "-moz-transition": "-moz-transform " + duration +"s",
        "-o-transition": "-o-transform " + duration +"s"});
    moveElement(elementId,moveX,moveY);
}

