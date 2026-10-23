-- Para ejecutar este test hay que usar (dentro de la carpeta tests):
-- ghc TestDibujo.hs ../src/Dibujo.hs && ./TestDibujo
-- A su vez, se debe cambiar Dibujo.hs para que exporte sus constructores y puedan ser utilizados para testear.

import Test.HUnit
import GHC.RTS.Flags (GCFlags(compact))
import Dibujo

tests :: Test
tests = test [
  -- Funciones para testear las funciones constructoras:
  figura 2 ~?= Figura 2,
  rotar (figura 5) ~?= Rotar (Figura 5),
  espejar (figura 5) ~?= Espejar (Figura 5),
  rot45 (figura 5) ~?= Rotar45 (Figura 5),
  encimar (figura 20) (rotar $ figura 5) ~?= Encimar (Figura 20) (Rotar (Figura 5)),
  apilar 0.5 1 (figura 20) (rotar $ figura 5) ~?= Apilar 0.5 1 (Figura 20) (Rotar (Figura 5)),
  juntar 1 0.5 (figura 20) (rotar $ figura 5) ~?= Juntar 1 0.5 (Figura 20) (Rotar (Figura 5)),
  comp rot45 4 (figura 20) ~?= Rotar45 (Rotar45 (Rotar45 (Rotar45 (Figura 20)))),
  r180 (figura 20) ~?= Rotar (Rotar (Figura 20)),
  r270 (figura 20) ~?= Rotar (Rotar (Rotar (Figura 20))),
  (.-.) (figura 20) (figura 10) ~?= Apilar 100 100 (Figura 20) (Figura 10),
  (///) (figura 20) (figura 10) ~?= Juntar 100 100 (Figura 20) (Figura 10),
  (^^^) (figura 20) (figura 10) ~?= Encimar (Figura 20) (Figura 10),
  -- (.-.) ((///) d1 d2) ((///) d3 d4)
  cuarteto (figura 40) (figura 30) (figura 20) (figura 10) ~?= Apilar 100 100 (Juntar 100 100 (Figura 40) (Figura 30)) (Juntar 100 100 (Figura 20) (Figura 10)),
  -- (^^^) ((^^^) d (rotar d)) ((^^^) (r180 d) (r270 d))
  encimar4 (figura 40) ~?= Encimar (Encimar (Figura 40) (Rotar (Figura 40))) (Encimar (Rotar (Rotar (Figura 40))) (Rotar (Rotar (Rotar (Figura 40))))),
  -- (.-.) ((///) d (rotar d)) (r180 ((///) d (rotar d)))
  ciclar (figura 10) ~?= Apilar 100 100 (Juntar 100 100 (Figura 10) (Rotar (Figura 10))) (Rotar (Rotar (Juntar 100 100 (Figura 10) (Rotar (Figura 10))))),
  -- foldDib fig rot esp r45 api jun enc (Encimar d1 d2) = enc (foldDib fig rot esp r45 api jun enc d1) (foldDib fig rot esp r45 api jun enc d2)
  foldDib figura rotar espejar rot45 apilar juntar encimar (encimar (juntar 1 2 (rot45 (figura 50)) (espejar (figura 40))) (apilar 2 1 (rotar (figura 20)) (figura 10))) ~?= Encimar (Juntar 1 2 (Rotar45 (Figura 50)) (Espejar (Figura 40))) (Apilar 2 1 (Rotar (Figura 20)) (Figura 10)),
  -- mapDib :: (a -> Dibujo b) -> Dibujo a -> Dibujo b
  mapDib (rotar . espejar . figura) (encimar (juntar 1 2 (rot45 (figura 50)) (espejar (figura 40))) (apilar 2 1 (rotar (figura 20)) (figura 10))) ~?= Encimar (Juntar 1 2 (Rotar45 (Rotar (Espejar (Figura 50)))) (Espejar (Rotar (Espejar (Figura 40))))) (Apilar 2 1 (Rotar (Rotar (Espejar (Figura 20)))) (Rotar (Espejar (Figura 10)))),
  -- figuras = foldDib (: []) id id id (\ _ _ a b ->  a++b) (\ _ _ a b -> a++b) (++)
  figuras (encimar (juntar 1 2 (rot45 (figura 50)) (espejar (figura 40))) (apilar 2 1 (rotar (figura 20)) (figura 10))) ~?= [50, 40, 20, 10]
  ]

main :: IO Counts
main = runTestTT tests