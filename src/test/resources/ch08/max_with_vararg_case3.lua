-- 简化版本，方便测试
-- page 165
-- https://github.com/zxh0/luago-book/blob/808487350f30a1ba51698cabcd70f11ad767c0a6/code/lua/ch08/test.lua
local function max(...)
    local args = {...}
    local val, idx
    for i = 1, #args do
        if val == nil or args[i] > val then
            val, idx = args[i], i
        end
    end
    return val, idx
end

local value = max(7, 128, 35)

-- main <max_with_vararg_case3.lua:0,0> (7 instructions at 0000000000ca8a10)
-- 0+ params, 5 slots, 1 upvalue, 2 locals, 3 constants, 1 function
-- 1       [13]    CLOSURE         0 0     ; 0000000000ca8bc0
-- 2       [15]    MOVE            1 0
-- 3       [15]    LOADK           2 -1    ; 7
-- 4       [15]    LOADK           3 -2    ; 128
-- 5       [15]    LOADK           4 -3    ; 35
-- 6       [15]    CALL            1 4 2
-- 7       [15]    RETURN          0 1
--
-- function <max_with_vararg_case3.lua:4,13> (21 instructions at 0000000000ca8bc0)
-- 0+ params, 8 slots, 0 upvalues, 7 locals, 2 constants, 0 functions
-- 1       [5]     NEWTABLE        0 0 0
-- 2       [5]     VARARG          1 0
-- 3       [5]     SETLIST         0 0 1   ; 1
-- 4       [6]     LOADNIL         1 1
-- 5       [7]     LOADK           3 -1    ; 1
-- 6       [7]     LEN             4 0
-- 7       [7]     LOADK           5 -1    ; 1
-- 8       [7]     FORPREP         3 8     ; to 17
-- 9       [8]     EQ              1 1 -2  ; - nil
-- 10      [8]     JMP             0 3     ; to 14
-- 11      [8]     GETTABLE        7 0 6
-- 12      [8]     LT              0 1 7
-- 13      [8]     JMP             0 3     ; to 17
-- 14      [9]     GETTABLE        7 0 6
-- 15      [9]     MOVE            2 6
-- 16      [9]     MOVE            1 7
-- 17      [7]     FORLOOP         3 -9    ; to 9
-- 18      [12]    MOVE            3 1
-- 19      [12]    MOVE            4 2
-- 20      [12]    RETURN          3 3
-- 21      [13]    RETURN          0 1
