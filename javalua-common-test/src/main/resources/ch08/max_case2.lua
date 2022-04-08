-- max的简化版本，方便测试
-- page 165
-- https://github.com/zxh0/luago-book/blob/808487350f30a1ba51698cabcd70f11ad767c0a6/code/lua/ch08/test.lua
local function max3(a, b, c)
    local max_value = a;
    if (max_value < b) then
        max_value = b;
    end
    if (max_value < c) then
        max_value = c;
    end
    return max_value;
end

local value = max3(1, 2, 3);

-- main <max_case2.lua:0,0> (7 instructions at 0000000000c18a00)
-- 0+ params, 5 slots, 1 upvalue, 2 locals, 3 constants, 1 function
-- 1       [13]    CLOSURE         0 0     ; 0000000000c18bb0
-- 2       [15]    MOVE            1 0
-- 3       [15]    LOADK           2 -1    ; 1
-- 4       [15]    LOADK           3 -2    ; 2
-- 5       [15]    LOADK           4 -3    ; 3
-- 6       [15]    CALL            1 4 2
-- 7       [15]    RETURN          0 1
--
-- function <max_case2.lua:4,13> (9 instructions at 0000000000c18bb0)
-- 3 params, 4 slots, 0 upvalues, 4 locals, 0 constants, 0 functions
-- 1       [5]     MOVE            3 0
-- 2       [6]     LT              0 3 1
-- 3       [6]     JMP             0 1     ; to 5
-- 4       [7]     MOVE            3 1
-- 5       [9]     LT              0 3 2
-- 6       [9]     JMP             0 1     ; to 8
-- 7       [10]    MOVE            3 2
-- 8       [12]    RETURN          3 2
-- 9       [13]    RETURN          0 1
