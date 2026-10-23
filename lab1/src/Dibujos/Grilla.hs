
module Dibujos.Grilla (grillaConf) where

import Dibujo (Dibujo, figura, rotar, rot45, r180, r270, ciclar, cuarteto, (.-.), (///), apilar, juntar, encimar, espejar)
import FloatingPic (Output, half, zero)
import Graphics.Gloss (Picture (Blank), blue, color, line, pictures, red, white, polygon, text, translate, scale)
import qualified Graphics.Gloss.Data.Point.Arithmetic as V
import Grilla (grilla)
import Interp (Conf (..), interp)

-- Esto hace una matriz de Dibujo (x, y) de tamaño n
--matrizGrilla :: int -> [[Dibujo a]]
matrizGrilla n = map row [0, 1..n]
  where row index = map (figura . (index,)) [0, 1..n]

--dibujoGrilla :: Dibujo a
dibujoGrilla = grilla (matrizGrilla 7)

-- a -> FloatingPic
-- a -> Vector -> Vector -> Vector -> Picture
--interpGrilla :: Output a
interpGrilla idgrilla (x, y) _ _ =  translate (x+5) (y+18) (scale 0.15 0.15 (text (show idgrilla)))

grillaConf :: Conf
grillaConf =
  Conf
    { name = "Grilla",
      pic = interp interpGrilla dibujoGrilla
    }