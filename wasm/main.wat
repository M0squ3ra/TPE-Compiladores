(module
    (global $gm (import "js" "gm") (mut i32))
    (global $gmm (import "js" "gmm") (mut i32))
    (global $gm2 (import "js" "globalf32") (mut f32))
    (global $g  i32 (i32.const 1))
    (func $printAlgo (result i32)
        i32.const 3
        global.set  $gm
        i32.const 5
        global.set $gmm
        call $add
      )
    (func $add (result i32)
        global.get $gm
        global.get $gmm
        i32.add)
  (export "printAlgo" (func $printAlgo))
)