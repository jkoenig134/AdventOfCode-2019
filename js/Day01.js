const BufferedReader = require("buffered-reader").DataReader;
const args = process.argv.slice(2);

let fileName = args[0]
if(!fileName) fileName = "../input/Day01.txt"


let output = 0;
new BufferedReader (fileName, { encoding: "utf8" })
    .on ("error", function (error){
        console.log ("error: " + error);
    })
    .on ("line", function (line){
        expression=line;
        let number = Number.parseInt(line)
        let result = solveFuelCostRecursively(number);
        output += result;
    })
    .on ("end", function (){
        console.log("Fuel required calculated to be "+output)
    })
    .read ();

function solveFuelCostRecursively(fuel=0) {
    fuel = Math.floor(fuel/3)
    fuel -= 2;
    if(fuel <= 0) return 0
    return fuel+solveFuelCostRecursively(fuel);
}