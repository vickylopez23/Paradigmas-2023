module Interp
  ( interp
  , Conf(..)
  , interpConf
  , initial
  ) where

import           Dibujo                               (Dibujo, foldDib)
import           FloatingPic                          (FloatingPic, Output,
                                                       grid, half, zero)
import           Graphics.Gloss                       (Display (InWindow),
                                                       Picture, color, display,
                                                       makeColorI, pictures,
                                                       translate, white, yellow)
import           Graphics.Gloss.Data.Point.Arithmetic as V

-- Interpretación de un dibujo
-- formulas sacadas del enunciado
-- a -> Vector -> Vector -> Vector -> Picture
-- Output a -> Output (Dibujo a)
-- interp :: (a -> Vector -> Vector -> Vector -> Picture) -> Dibujo a -> Vector -> Vector -> Vector -> Picture
interp :: Output a -> Output (Dibujo a)
interp intFigura = foldDib intFigura intRotar intEspejar intRotar45 intApilar intJuntar intEncimar


-- Vector -> Vector -> Vector -> Picture -> Vector -> Vector -> Vector -> Picture
intRotar :: FloatingPic -> FloatingPic
intRotar floatpic x w h = floatpic (x V.+ w) h (zero V.- w)

intEspejar :: FloatingPic -> FloatingPic
intEspejar floatpic x w = floatpic (x V.+ w) (zero V.- w)

intRotar45 :: FloatingPic -> FloatingPic
intRotar45 floatpic x w h = floatpic (x V.+ half (w V.+ h)) (half (w V.+ h)) (half (h V.- w))

intEncimar :: FloatingPic -> FloatingPic -> FloatingPic
intEncimar f g x w h = pictures [f x w h, g x w h]


--apilar(n, m, f, g)(x, w, h)
--f(x + h', w, r*h) ∪ g(x, w, h') con 
--r' = n/(m+n), r=m/(m+n), h'=r'*h


intApilar  :: Float -> Float -> FloatingPic -> FloatingPic -> FloatingPic
intApilar n m fp1 fp2 x w h = pictures [fp1 (x V.+ h') w (r V.* h), fp2 x w h']
  where
    r' = n/(m Prelude.+ n)
    r = m / (m Prelude.+ n)
    h' = r' V.* h

--juntar(n, m, f, g)(x, w, h)
--f(x, w', h) ∪ g(x+w', r'*w, h)
--con r'=n/(m+n), r=m/(m+n), w'=r*w
intJuntar :: Float -> Float -> FloatingPic -> FloatingPic -> FloatingPic
intJuntar  n m fp1 fp2 x w h = pictures [fp1 x w' h, fp2 (x V.+ w') (r' V.* w) h]
  where
    r' = n / (m Prelude.+ n)
    r = m / (m Prelude.+ n)
    w' = r V.* w




-- Configuración de la interpretación
data Conf =
  Conf
    { name :: String
    , pic  :: FloatingPic
    }

interpConf :: Conf -> Float -> Float -> Picture
interpConf (Conf _ p) x y = p (0, 0) (x, 0) (0, y)

-- Dada una computación que construye una configuración, mostramos por
-- pantalla la figura de la misma de acuerdo a la interpretación para
-- las figuras básicas. Permitimos una computación para poder leer
-- archivos, tomar argumentos, etc.
initial :: Conf -> Float -> IO ()
initial cfg size = do
  let n = name cfg
      win = InWindow n (ceiling size, ceiling size) (0, 0)
  display win white $ withGrid (interpConf cfg size size) size size
  where
    withGrid p x y =
      translate (-size / 2) (-size / 2) $
      pictures [p, color grey $ grid (ceiling $ size / 10) (0, 0) x 10]
    grey = makeColorI 120 120 120 120
