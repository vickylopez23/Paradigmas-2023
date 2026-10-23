module Dibujos.Prueba
  ( pruebaConf,
  )
where

import Dibujo (Dibujo, apilar, encimar, espejar, figura, juntar, rot45, rotar)
import FloatingPic (Output, half, zero)
import Graphics.Gloss (Picture, blue, color, line, pictures, red, white, translate, polygon)
import qualified Graphics.Gloss.Data.Point.Arithmetic as V
import Grilla (grilla)
import Interp (Conf (..), interp)
import Data.Traversable (for)

-- Les ponemos colorcitos para que no sea _tan_ prueba
data Color = Azul | Rojo
  deriving (Show, Eq)

data BasicaSinColor = Rectangulo | Cruz | Triangulo | Efe
  deriving (Show, Eq)

type Basica = (BasicaSinColor, Color)

colorear :: Color -> Picture -> Picture
colorear Azul = color blue
colorear Rojo = color red

-- Las coordenadas que usamos son:
--
--  x + y
--  |
--  x --- x + w
--
-- por ahi deban ajustarlas
interpBasicaSinColor :: Output BasicaSinColor
interpBasicaSinColor Rectangulo x y w = line [x, x V.+ y, x V.+ y V.+ w, x V.+ w, x]
interpBasicaSinColor Cruz x y w = pictures [line [x, x V.+ y V.+ w], line [x V.+ y, x V.+ w]]
interpBasicaSinColor Triangulo x y w = line $ map (x V.+) [(0, 0), y V.+ half w, w, (0, 0)]
interpBasicaSinColor Efe x y w =
  line . map (x V.+) $
    [ zero,
      uX,
      p13,
      p33,
      p33 V.+ uY,
      p13 V.+ uY,
      uX V.+ 4 V.* uY,
      uX V.+ 5 V.* uY,
      x4 V.+ y5,
      x4 V.+ 6 V.* uY,
      6 V.* uY,
      zero
    ]
  where
    p33 = 3 V.* (uX V.+ uY)
    p13 = uX V.+ 3 V.* uY
    x4 = 4 V.* uX
    y5 = 5 V.* uY
    uX = (1 / 6) V.* y
    uY = (1 / 6) V.* w

-- Diferentes tests para ver que estÃ©n bien las operaciones
figRoja :: BasicaSinColor -> Dibujo Basica
figRoja b = figura (b, Rojo)

figAzul :: BasicaSinColor -> Dibujo Basica
figAzul b = figura (b, Azul)

apilados n m b = apilar n m (figAzul b) (figRoja b)

juntados n m b = juntar 1 1 (figAzul b) (figRoja b)

efe :: Dibujo Basica
efe = figura (Efe, Azul)

mioa :: (Eq int, Num int) => int -> Dibujo Basica
mioa 0 = rotar $ espejar $ apilados 1 1 Rectangulo
mioa n = rotar (mioa (n-1))

miofinal :: (Num int, Eq int) => int -> Dibujo Basica
miofinal n = if n==1 then encimar (mioa 1) (mioa 0)
            else encimar (mioa n) (miofinal (n-1))

-- testAll :: Dibujo Basica
-- testAll =
--   grilla
--     [ [cruzTangulo, rot45 cruzTangulo, efe, rot45 efe],
--       [apilados Rectangulo, apilados2 Rectangulo, juntados Rectangulo, juntados2 Rectangulo],
--       [flipante1 Rectangulo, flipante2 Rectangulo, figRoja Triangulo, rotar $ figAzul Triangulo],
--       [rotar $ apilados Efe, apilados2 Efe, juntados Efe, juntados2 Efe]
--     ]


interpBas :: Output Basica
interpBas (b, c) x y w = colorear c $ interpBasicaSinColor b x y w

pruebaConf :: Conf
pruebaConf =
  Conf
    { name = "Prueba",
      pic = interp interpBas $ miofinal 3
    }