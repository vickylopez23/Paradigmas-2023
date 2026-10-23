module Pred (
  Pred,
  cambiar, anyFig, allFig, orP, andP
) where

import Dibujo (Dibujo, mapDib, foldDib, figura)
type Pred a = a -> Bool

-- Dado un predicado sobre básicas, cambiar todas las que satisfacen
-- el predicado por la figura básica indicada por el segundo argumento.
cambiar :: Pred a -> (a -> Dibujo a) -> Dibujo a -> Dibujo a
cambiar pred f = mapDib (fCambia pred f)

fCambia :: Pred a -> (a -> Dibujo a) -> a -> Dibujo a
fCambia pred f x = if pred x then f x else figura x

-- Alguna básica satisface el predicado.
anyFig ::  Pred a -> Dibujo a -> Bool
anyFig pred = foldDib pred id id id (\ _ _ dib1 dib2 -> dib1 || dib2) (\ _ _ dib1 dib2 -> dib1 || dib2) (||)

-- Todas las básicas satisfacen el predicado.
allFig :: Pred a -> Dibujo a -> Bool
allFig pred = foldDib pred id id id (\ _ _ dib1 dib2 -> dib1 && dib2) (\ _ _ dib1 dib2 -> dib1 && dib2) (&&)

-- Los dos predicados se cumplen para el elemento recibido.
andP :: Pred a -> Pred a -> Pred a
andP pred1 pred2 a = pred1 a && pred2 a

-- Algún predicado se cumple para el elemento recibido.
orP :: Pred a -> Pred a -> Pred a
orP pred1 pred2 a = pred1 a || pred2 a
