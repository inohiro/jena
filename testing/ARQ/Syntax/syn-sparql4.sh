#!/bin/bash

## ==== Good
N=0

## ---- Expressions in SELECT

N=0

N=$((N+1)) ; testGood11 $(fname "syntax-select-expr-" $N arq) <<EOF
SELECT (?x +?y AS ?z) {}
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-select-expr-" $N arq) <<EOF
SELECT ?x ?y (?x +?y AS ?z) {}
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-select-expr-" $N arq) <<EOF
SELECT (datatype(?x +?y) AS ?z) {}
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-select-expr-" $N arq) <<EOF
PREFIX : <http://example/>
SELECT (:function(?x +?y) AS ?F) ?z {}
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-select-expr-" $N arq) <<EOF
PREFIX : <http://example/>
SELECT (COUNT(*) AS ?count) {}
EOF

## ---- Aggregates
N=0

N=$((N+1)) ; testGood11 $(fname "syntax-aggregate-" $N arq) <<EOF
SELECT (COUNT(*) AS ?count) {}
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-aggregate-" $N arq) <<EOF
SELECT (COUNT(DISTINCT *) AS ?count) {}
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-aggregate-" $N arq) <<EOF
SELECT (COUNT(?x) AS ?count) {}
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-aggregate-" $N arq) <<EOF
SELECT (COUNT(DISTINCT ?x) AS ?count) {}
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-aggregate-" $N arq) <<EOF
SELECT (SUM(?x) AS ?y) {}
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-aggregate-" $N arq) <<EOF
SELECT (SUM(DISTINCT ?x) AS ?y) {}
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-aggregate-" $N arq) <<EOF
SELECT (MIN(?x) AS ?y) {}
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-aggregate-" $N arq) <<EOF
SELECT (MIN(DISTINCT ?x) AS ?y) {}
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-aggregate-" $N arq) <<EOF
SELECT (MAX(?x) AS ?y) {}
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-aggregate-" $N arq) <<EOF
SELECT (MAX(DISTINCT ?x) AS ?y) {}
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-aggregate-" $N arq) <<EOF
SELECT (AVG(?x) AS ?y) {}
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-aggregate-" $N arq) <<EOF
SELECT (AVG(DISTINCT ?x) AS ?y) {}
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-aggregate-" $N arq) <<EOF
SELECT (GROUP_CONCAT(?x) AS ?y) {}
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-aggregate-" $N arq) <<EOF
SELECT (GROUP_CONCAT(DISTINCT ?x) AS ?y) {}
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-aggregate-" $N arq) <<EOF
SELECT (GROUP_CONCAT(?x; SEPARATOR=';') AS ?y) {}
EOF

## ---- Subquery
N=0

N=$((N+1)) ; testGood11 $(fname "syntax-subquery-" $N arq) <<EOF
SELECT * { SELECT * { ?s ?p ?o } }
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-subquery-" $N arq) <<EOF
SELECT * { {} {SELECT * { ?s ?p ?o } }
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-subquery-" $N arq) <<EOF
SELECT * { {} OPTIONAL {SELECT * { ?s ?p ?o }} }
EOF

## ---- Negation: NOT EXISTS / EXISTS

N=0

N=$((N+1)) ; testGood11 $(fname "syntax-not-exists-" $N arq) <<EOF
SELECT * { ?s ?p ?o FILTER(NOT EXISTS{?s ?p ?o}) }
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-not-exists-" $N arq) <<EOF
SELECT * { ?s ?p ?o FILTER NOT EXISTS{?s ?p ?o} }
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-not-exists-" $N arq) <<EOF
SELECT * { ?s ?p ?o FILTER(NOT EXISTS{?s ?p ?o}) }
EOF

N=0
N=$((N+1)) ; testGood11 $(fname "syntax-exists-" $N arq) <<EOF
SELECT * { ?s ?p ?o FILTER(EXISTS{?s ?p ?o}) }
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-exists-" $N arq) <<EOF
SELECT * { ?s ?p ?o FILTER EXISTS{?s ?p ?o} }
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-not-exists-" $N arq) <<EOF
SELECT * { ?s ?p ?o FILTER(NOT EXISTS{?s ?p ?o}) }
EOF

## ---- Negation: MINUS
N=0

N=$((N+1)) ; testGood11 $(fname "syntax-minus-" $N arq) <<EOF
SELECT * { ?s ?p ?o FILTER(NOT EXISTS{?s ?p ?o}) }
EOF

## ---- IN , NOT IN

N=0

N=$((N+1)) ; testGood11 $(fname "syntax-oneof-" $N arq) <<EOF
SELECT * { ?s ?p ?o FILTER(?o NOT IN(1,2,?s+57)) }
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-oneof-" $N arq) <<EOF
SELECT * { ?s ?p ?o FILTER(?o NOT IN()) }
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-oneof-" $N arq) <<EOF
SELECT * { ?s ?p ?o FILTER(?o IN(1,<x>)) }
EOF

## ---- UNION without left {}
N=0
N=$((N+1)) ; testGood11 $(fname "syntax-union11-" $N arq) <<EOF
SELECT * { ?s ?p ?o UNION { ?s ?p ?o } }
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-union11-" $N arq) <<EOF
SELECT * { ?s1 ?p1 ?o1 . ?s ?p ?o UNION { ?s ?p ?o } }
EOF

## ---- SERVICE

N=0
N=$((N+1)) ; testGood11 $(fname "syntax-service-" $N arq) <<EOF
SELECT * { SERVICE <g> { ?s ?p ?o } }
EOF

N=$((N+1)) ; testGood11 $(fname "syntax-service-" $N arq) <<EOF
SELECT * { ?s ?p ?o SERVICE <g> { ?s ?p ?o } ?s ?p ?o }
EOF

## ---- BINDING

N=$((N+1)) ; testGood11 $(fname "syntax-service-" $N arq) <<EOF
SELECT * { } BINDING (?x ?y) { 
EOF

## == Bad
N=0

N=$((N+1)) ; testBad11 $(fname "syn-bad-" $N) <<EOF
SELECT (?x +?y) {}
EOF

N=$((N+1)) ; testBad11 $(fname "syn-bad-" $N) <<EOF
SELECT COUNT(*) {}
EOF

N=$((N+1)) ; testBad11 $(fname "syn-bad-" $N) <<EOF
SELECT (SUM(?x,?y) AS ?S) {}
EOF

N=$((N+1)) ; testBad11 $(fname "syn-bad-" $N arq) <<EOF
SELECT * { {} SELECT * { ?s ?p ?o } }
EOF

N=$((N+1)) ; testBad11 $(fname "syn-bad-" $N arq) <<EOF
SELECT * { ?s ?p ?o UNION ?s ?p ?o  }
EOF