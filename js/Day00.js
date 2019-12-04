// Test file, prints out command line args.
const args = process.argv.slice(2); // First 2 args are set by node, not the user.
console.log(args);