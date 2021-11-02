fetch('./main.wasm')
  .then(response => response.arrayBuffer())
  .then(bytes => WebAssembly.instantiate(bytes,{
    "js":{
      "gm": new WebAssembly.Global({value:'i32', mutable:true}, 0),
      "gmm": new WebAssembly.Global({value:'i32', mutable:true}, 0),
      "globalf32": new WebAssembly.Global({value:'f32', mutable:true}, 0)
    }}))
  .then(results => {
    instance = results.instance;
    // document.getElementById("container").textContent = instance.exports.add(1,1);
    document.getElementById("container").textContent = instance.exports.printAlgo();
    // alert(instance.exports.add(1,1));
  }).catch(console.error);
