module Dibujos.Escher
  ( escherConf,
  )
where

import Dibujo (Dibujo, apilar, encimar, espejar, figura, juntar, rot45, r270, rotar, cuarteto,r180)
import FloatingPic (Output, half, zero)
import Graphics.Gloss (Picture (Blank), blue, color, line, pictures, red, white, polygon, text, translate, scale, black)
import qualified Graphics.Gloss.Data.Point.Arithmetic as V
import Grilla (grilla)
import Interp (Conf (..), interp)

-- Supongamos que eligen.
type Escher = Bool

blank = figura False
baseEscher = figura True


-- El dibujoT.
dibujoT :: Dibujo Escher -> Dibujo Escher
dibujoT = dibujoTAux 2

dibujoTAux :: Int -> Dibujo a -> Dibujo a
dibujoTAux 0 p = p
dibujoTAux n p = encimar (rotar45n n False p) (dibujoTAux (n-1) p)

-- El dibujoU.
dibujoU :: Dibujo Escher -> Dibujo Escher
dibujoU = dibujoUAux 3

dibujoUAux :: Int -> Dibujo a -> Dibujo a
dibujoUAux 0 p = rotar45n 0 True p
dibujoUAux n p = encimar (rotar45n n True p) (dibujoUAux (n-1) p)

rotar45n :: Int -> Bool -> Dibujo a -> Dibujo a
rotar45n 0 _ p = espejar $ rot45 p
rotar45n n der p  | der = rotar (rotar45n (n-1) True p)
                  | otherwise = r270 (rotar45n (n-1) True p)



-- Esquina con nivel de detalle en base a la figura p.
esquina :: Int -> Dibujo Escher
esquina 0 = blank
esquina n = cuarteto (esquina (n-1)) (lado (n-1)) (rotar (lado (n-1))) (dibujoU baseEscher) 


-- Lado con nivel de detalle.
-- lado :: Int -> Dibujo Escher -> Dibujo Escher
-- lado n p = undefined
lado :: Int -> Dibujo Escher
lado 0 = blank
lado n = cuarteto (lado (n-1)) (lado (n-1)) (rotar $ dibujoT baseEscher) (dibujoT baseEscher)
 

noneto p q r s t u v w x = grilla [[p,q,r],
                                   [s,t,u],
                                   [v,w,x]]

-- El dibujo de Escher:
escher :: Int -> Dibujo Escher
escher n =  noneto (esquina n) (lado n) (espejar $ esquina n) (rotar (lado n)) (dibujoU baseEscher) (r270 $ lado n) (rotar $ esquina n) (r180 $ lado n) (r180 $ esquina n)

interpEscher :: Output Escher
interpEscher False _ _ _  = Blank
interpEscher True a b c = pictures [line $ triangulo a b c, cara a b c]
  where
      triangulo a b c = map (a V.+) [zero, c, b, zero]
      cara a b c = polygon $ triangulo (a V.+ half c) (half b) (half c)

escherConf :: Conf
escherConf =
  Conf
    { name = "Escher",
      pic = interp interpEscher $ escher 6
    }

