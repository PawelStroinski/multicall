# multicall

Like multimethods, but can register multiple calls with the same dispatch value.

[![Clojars Project](https://img.shields.io/clojars/v/multicall.svg)](https://clojars.org/multicall)

## Usage

    (use 'multicall.core)
    (defmulticall add (fn [x _] x))
    (defcall add with-inc 1 [_ y] (inc y))
    (defcall add with-plus 1 [_ y] (+ 1 y))
    (add 1 2)
    => (3 3)

Please refer to the unit tests for more examples.

## Limitations in comparison with multimethods

Hierarchies are not supported.

Registered call inspection/removal can be done manually by accessing the
registry atom but no functions are provided.

## License

Copyright © 2018 Paweł Stroiński

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
