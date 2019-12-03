const BufferedReader = require("buffered-reader").DataReader;

const args = process.argv.slice(2);

let fileName = args[0]
if(!fileName) fileName = "../input/Day02.txt"


let data = [];
let resultForDefaults;
run(0,0);
function run(param1, param2) {
    data = [];
    new BufferedReader (fileName, { encoding: "utf8" })
    .on ("error",console.error)
    .on ("line", line => data.push(...line.split(",")))
    .on ("end", () => {
        console.log("Data read, "+data.length+" inputs.")
        data[1] = param1;
        data[2] = param2;
        for(let i = 0; i < data.length; i++) {
            data[i] = Number.parseInt(data[i]);
        }
        solve(0);
        if(data[0] == 19690720) {
            console.log("The output is equal to 19690720 for these parameters:",param1, param2);
            console.log("For the parameters 12 at position 1 and 2 at position 2, the output is",resultForDefaults)
        } else {
            if(param1 == 12 && param2 == 2) // Solution of the first half of the problem.
                resultForDefaults = data[0];


            let nextInput1 = param1+1, nextInput2 = param2;
            if(param1 == 99) {
                nextInput1 = 0;
                nextInput2 ++;
            }
            console.log("Failed for ",param1, param2)
            run(nextInput1, nextInput2)
        }
    })
    .read ();
}

function solve(position) {
    let command = data[position]
    if(!command) return;
    if(command == 99) return;
    if(command != 1 && command != 2) {
        console.log("Command "+command+" invalid.")
        return;
    }
    if(data.length < position+3) {
        console.log("ArrayIndexOutOfBoundsException.")
        return;
    }
    let pos1 = data[position+1]
    let pos2 = data[position+2]
    let savePos = data[position+3]  
    if(command == 1)
        data[savePos] = data[pos1]+data[pos2]
    else
        data[savePos] = data[pos1]*data[pos2];
    
    solve(position+4);
}