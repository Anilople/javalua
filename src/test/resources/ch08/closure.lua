-- page 152
local a,b,c
local function f() end
local g = function() end
-- main <closure.lua:0,0> (4 instructions at 0000000000b78a00)
-- 0+ params, 5 slots, 1 upvalue, 5 locals, 0 constants, 2 functions
-- 1       [2]     LOADNIL         0 2
-- 2       [3]     CLOSURE         3 0     ; 0000000000b78b70
-- 3       [4]     CLOSURE         4 1     ; 0000000000b78de0
-- 4       [4]     RETURN          0 1
--
-- function <closure.lua:3,3> (1 instruction at 0000000000b78b70)
-- 0 params, 2 slots, 0 upvalues, 0 locals, 0 constants, 0 functions
-- 1       [3]     RETURN          0 1
--
-- function <closure.lua:4,4> (1 instruction at 0000000000b78de0)
-- 0 params, 2 slots, 0 upvalues, 0 locals, 0 constants, 0 functions
