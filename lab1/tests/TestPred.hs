
-- Para ejecutar este test hay que usar (dentro de la carpeta tests):
-- ghc TestPred.hs ../src/Pred.hs ../src/Dibujo.hs && ./TestPred
import Test.HUnit
import Pred
import Dibujo

tests :: Test
tests = test [  
  -- Funciones para testear cambiar:
  -- Test sobre figura, cambiamos (figura 1) por (figura 2)
  cambiar (==1) (const (figura 2)) (figura 1) ~?= figura 2,
  -- Test sobre rotar, cambiamos (figura 1) por espejar (figura 2), donde (figura 1) esta dentro de rotar.
  cambiar (==1) (const (espejar (figura 2))) (rotar $ figura 1) ~?= rotar (espejar (figura 2)),

  -- Funciones para testear anyFig:
  anyFig (==1) (figura 2) ~?= False,
  anyFig (==1) (figura 1) ~?= True,
  anyFig (==1) (juntar 1 1(figura 1) (figura 2)) ~?= True,
  anyFig (==3) (juntar 1 1 (figura 2) (figura 1)) ~?= False,


  --test sobre allDDib
  allFig (==1) (figura 2) ~?= False,
  allFig (==1) (figura 1) ~?= True,
  allFig (==1) (juntar 1 1(figura 1) (figura 1)) ~?= True,
  allFig (==1) (juntar 1 1 (figura 2) (figura 1)) ~?= False,

  --test sobre andP
  --Ambos tienen que cumplir con el predicado dado
  andP (==1) (==2) 1 ~?= False,  
  andP (==1) (==1) 1 ~?= True,
  andP (==2) (==2) 2 ~?= True,


  --test sobre orP
  --al menos uno tiene que cumplir con el predicador
  orP (==1) (==2) 3 ~?= False, 
  orP (==1) (==2) 1 ~?= True,
  orP (==1) (==2) 2 ~?= True

  ]
  

main :: IO Counts
main = runTestTT tests