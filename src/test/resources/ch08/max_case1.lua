-- max的简化版本，方便测试
-- page 165
-- https://github.com/zxh0/luago-book/blob/808487350f30a1ba51698cabcd70f11ad767c0a6/code/lua/ch08/test.lua
local function max(a, b)
    if (a > b) then
        return a;
    else
        return b;
    end
end

local value = max(-123, 64);
-- main <max_case1.lua:0,0> (6 instructions at 0000000000778a00)
-- 0+ params, 4 slots, 1 upvalue, 2 locals, 2 constants, 1 function
-- 1       [10]    CLOSURE         0 0     ; 0000000000778bb0
-- 2       [12]    MOVE            1 0
-- 3       [12]    LOADK           2 -1    ; -123
-- 4       [12]    LOADK           3 -2    ; 64
-- 5       [12]    CALL            1 3 2
-- 6       [12]    RETURN          0 1
--
-- function <max_case1.lua:4,10> (6 instructions at 0000000000778bb0)
-- 2 params, 2 slots, 0 upvalues, 2 locals, 0 constants, 0 functions
-- 1       [5]     LT              0 1 0
-- 2       [5]     JMP             0 2     ; to 5
-- 3       [6]     RETURN          0 2
-- 4       [6]     JMP             0 1     ; to 6
-- 5       [8]     RETURN          1 2
-- 6       [10]    RETURN          0 1
