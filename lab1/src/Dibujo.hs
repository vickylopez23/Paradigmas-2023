{-# LANGUAGE LambdaCase #-}
module Dibujo (
    Dibujo, -- Para que exporte constructores: Dibujo (..)
    figura, rotar, espejar, rot45, apilar, juntar, encimar,
    r180, r270,
    (.-.), (///), (^^^),
    cuarteto, encimar4, ciclar, comp,
    foldDib, mapDib,
    figuras
) where
import GHC.CmmToAsm.AArch64.Instr (Instr(ROR))

{-
Gramática de las figuras:
<Fig> ::= Figura <Bas> | Rotar <Fig> | Espejar <Fig> | Rot45 <Fig>
    | Apilar <Float> <Float> <Fig> <Fig> 
    | Juntar <Float> <Float> <Fig> <Fig> 
    | Encimar <Fig> <Fig>
-}

data Dibujo a =
          Figura a
        | Rotar (Dibujo a)
        | Espejar (Dibujo a)
        | Rotar45 (Dibujo a)
        | Apilar Float Float (Dibujo a) (Dibujo a)
        | Juntar Float  Float (Dibujo a) (Dibujo a)
        | Encimar (Dibujo a) (Dibujo a)
          deriving (Eq, Show)

-- Construcción de dibujo. Abstraen los constructores.

figura :: a -> Dibujo a
figura = Figura

rotar :: Dibujo a -> Dibujo a
rotar = Rotar

espejar :: Dibujo a -> Dibujo a
espejar  = Espejar

rot45 :: Dibujo a -> Dibujo a
rot45  = Rotar45

apilar ::Float -> Float -> Dibujo a -> Dibujo a -> Dibujo a
apilar  = Apilar

juntar :: Float -> Float -> Dibujo a -> Dibujo a -> Dibujo a
juntar = Juntar

encimar:: Dibujo a -> Dibujo a -> Dibujo a
encimar = Encimar

-- Composición n-veces de una función con sí misma. Componer 0 veces
-- es la función constante, componer 1 vez es aplicar la función 1 vez, etc.
-- Componer negativamente es un error!
comp :: (a -> a) -> Int -> a -> a
comp _ 0 x = x
comp f n x = if n>0 then f (comp f (n-1) x) else error "error: componer negativo"

-- Rotaciones de múltiplos de 90.
r180 :: Dibujo a -> Dibujo a
r180 d1 =  Rotar (Rotar d1)

r270 :: Dibujo a -> Dibujo a
r270 d1 = Rotar (Rotar (Rotar d1))

-- Pone una figura sobre la otra, ambas ocupan el mismo espacio.
(.-.) :: Dibujo a -> Dibujo a -> Dibujo a
(.-.) = apilar 100 100

-- Pone una figura al lado de la otra, ambas ocupan el mismo espacio.
(///) :: Dibujo a -> Dibujo a -> Dibujo a
(///) = juntar 100 100

-- Superpone una figura con otra.
(^^^) :: Dibujo a -> Dibujo a -> Dibujo a
(^^^) = encimar


-- Dadas cuatro figuras las ubica en los cuatro cuadrantes.
cuarteto ::Dibujo a -> Dibujo a -> Dibujo a -> Dibujo a -> Dibujo a
cuarteto d1 d2 d3 d4 = (.-.) ((///) d1 d2) ((///) d3 d4)

encimar4 :: Dibujo a -> Dibujo a
encimar4 d = (^^^) ((^^^) d (rotar d)) ((^^^) (r180 d) (r270 d))

-- cuadrado con la misma figura rotada $i$ por $90$ para $i \in \{1..3\}$.
-- No confundir con encimar4!
ciclar :: Dibujo a -> Dibujo a
ciclar d = (.-.) ((///) d (rotar d)) (r180 ((///) d (rotar d)))

-- Estructura general para la semántica (a no asustarse). Ayuda: 
-- pensar en foldr y las definiciones de Floatro a la lógica
--a función se encarga de recorrer recursivamente la figura y aplicar las funciones correspondientes a cada constructor.

foldDib :: (a -> b) ->  
           (b -> b) ->
           (b -> b) ->
           (b -> b) ->
           (Float -> Float -> b -> b -> b) ->
           (Float -> Float -> b -> b -> b) ->
           (b -> b -> b) ->
           Dibujo a ->
           b

foldDib fig _ _ _ _ _ _ (Figura a) = fig a
foldDib fig rot esp r45 api jun enc (Rotar d) = rot (foldDib fig rot esp r45 api jun enc d)
foldDib fig rot esp r45 api jun enc (Espejar d) = esp (foldDib fig rot esp r45 api jun enc d)
foldDib fig rot esp r45 api jun enc (Rotar45 d) = r45 (foldDib fig rot esp r45 api jun enc d)
foldDib fig rot esp r45 api jun enc (Apilar x y d1 d2) = api x y (foldDib fig rot esp r45 api jun enc d1) (foldDib fig rot esp r45 api jun enc d2)
foldDib fig rot esp r45 api jun enc (Juntar x y d1 d2) = jun x y (foldDib fig rot esp r45 api jun enc d1) (foldDib fig rot esp r45 api jun enc d2)
foldDib fig rot esp r45 api jun enc (Encimar d1 d2) = enc (foldDib fig rot esp r45 api jun enc d1) (foldDib fig rot esp r45 api jun enc d2)

-- Demostrar que `mapDib figura = id`
-- Verificar que satisfaga la siguiente igualdad:
-- mapDib figura = id    (id es la función identidad).

mapDib :: (a -> Dibujo b) -> Dibujo a -> Dibujo b
mapDib f (Figura a) = f a
mapDib f (Rotar d) = Rotar (mapDib f d)
mapDib f (Espejar d) = Espejar (mapDib f d)
mapDib f (Rotar45 d) = Rotar45 (mapDib f d)
mapDib f (Apilar x y d1 d2) = Apilar x y (mapDib f d1) (mapDib f d2)
mapDib f (Juntar x y d1 d2) = Juntar x y (mapDib f d1) (mapDib f d2)
mapDib f (Encimar d1 d2) = Encimar (mapDib f d1) (mapDib f d2)

-- Junta todas las figuras básicas de un dibujo.
figuras :: Dibujo a -> [a]
figuras = foldDib (: []) id id id (\ _ _ a b ->  a++b) (\ _ _ a b -> a++b) (++)

-- a++b son dos listas ya, por eso las concat
-- concat de un elemento a una lista
-- ghci> :t (:)
-- (:) :: a -> [a] -> [a]