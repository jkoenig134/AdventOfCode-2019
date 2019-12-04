const BufferedReader = require("buffered-reader").DataReader;
const args = process.argv.slice(2);

let fileName = args[0]
if(!fileName) fileName = "../input/Day03.txt"

const matrix = [ // Beinhaltet ALLE Punkte des 1. Kabels !!! (wird SEHR lang)
    {
        x: 0,
        y: 0,
        steps: 0
    }
]
const intersections = [];
let nearestIntersection = {steps: 10000000000000};
let currentCable = 1; // 1 = Punkt in die Matrix einfügen. 2=Punkt mit der Matrix abchecken

console.log("------------------- WICHTIG: Dieses Script braucht *sehr* lange zum Ausführen!!! (Bei mir etwa 3 Minuten)")

new BufferedReader (fileName, { encoding: "utf8" })
    .on ("error", function (error){
        console.log ("error: " + error);
    })
    .on ("line", function (line){
        let steps = 0;
        let directions = line.split(",");
        let currentPoint = {x: 0, y: 0};
        for(direction of directions) {
            let amount = Number.parseInt(direction.substring(1));
            for(let i = 1; i <= amount; i++) {
                steps++;
                let newPoint;
                if(direction[0] == "R") {
                    newPoint = {x: currentPoint.x + 1, y: currentPoint.y, steps}
                    currentPoint.x ++;
                } else if(direction[0] == "L") {
                    newPoint = {x: currentPoint.x - 1, y: currentPoint.y, steps}
                    currentPoint.x --;
                } else if(direction[0] == "U") {
                    newPoint = {y: currentPoint.y + 1, x: currentPoint.x, steps}
                    currentPoint.y ++;
                } else if(direction[0] == "D") {
                    newPoint = {y: currentPoint.y - 1, x: currentPoint.x, steps}
                    currentPoint.y --;
                } else {
                    console.error("INPUT INVALID. INPUT =",direction[0]);
                }
                if(currentCable == 1) {
                    if(!pointExists(newPoint)) // Wegen den Steps: Der 1. Punkt wird offensichtlicherweise auch die kleinste Step-Anzahl haben. Weitere Punkte fallen also weg.
                        matrix.push(newPoint);
                } else {
                    let otherPoint = pointExists(newPoint);
                    if(otherPoint) {
                        intersections.push(newPoint);
                        if(nearestIntersection.steps > (otherPoint.steps + newPoint.steps)) { // Naher Schnittpunkt
                            console.log("New lowest intersection at",otherPoint.steps + newPoint.steps)
                            nearestIntersection = newPoint;
                            nearestIntersection.steps = otherPoint.steps + newPoint.steps
                        }
                    }
                }
            }
        }
        console.log("Line processed. This line has a length of "+steps)
        currentCable++;
    })
    .on ("end", function (){
        let lowestManhattan = 1000000000;
        for(intersection of intersections) {
            let mahattanDistance = Math.abs(intersection.x-0)+Math.abs(intersection.y-0);
            if(mahattanDistance < lowestManhattan) 
                lowestManhattan = mahattanDistance;
        }
        console.log("Solution to part 1 (lowest Manhattan value):",lowestManhattan);
        console.log("Solution to part 2 (Intersection with smallest amount of steps):",nearestIntersection.steps);
    })
    .read ();

function pointExists(point) {
    for (node of matrix) {
        if(node.x == point.x && node.y == point.y) {
            return node;
        }
    }
    return false;
}