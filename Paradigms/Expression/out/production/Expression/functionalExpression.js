"use strict";

var curry = function (f) {
    return function () {
        var newArguments = arguments;
        return function () {
            var newNewArguments = [];
            for (var i = 0; i < newArguments.length; i++) {
                newNewArguments.push(newArguments[i].apply(null, arguments));
            }
            return f.apply(null, newNewArguments);
        }
    }
};

var variable = function (name) {
    return function (x, y, z) {
        switch (name) {
            case 'x':
                return x;
            case 'y':
                return y;
            case 'z':
                return z;
        }
    }
};

var cnst = function (constValue) {
    return function () {
        return constValue;
    }
};

var negate = curry(function (value) {return -value});

var cube = curry(function (value) {return Math.pow(value, 3)});

var cuberoot = curry(function (value) {return Math.pow(value, 1 / 3)});

var add = curry(function (a,b) {return a + b});

var subtract = curry(function (a,b) {return a - b});

var multiply = curry(function (a,b) {return a * b});

var divide = curry(function (a,b) {return a / b});