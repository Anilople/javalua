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

local function assert(v)
    if not v then fail() end
end

local v1 = max(3, 9, 7, 128, 35)
assert(v1 == 128)
local v2, i2 = max(3, 9, 7, 128, 35)
assert(v2 == 128 and i2 == 4)
local v3, i3 = max(max(3, 9, 7, 128, 35))
assert(v3 == 128 and i3 == 1)
local t = {max(3, 9, 7, 128, 35)}
assert(t[1] == 128 and t[2] == 4)

-- main <test.lua:0,0> (67 instructions at 0000000000a28a00)
-- 0+ params, 14 slots, 1 upvalue, 8 locals, 8 constants, 2 functions
-- 1       [12]    CLOSURE         0 0     ; 0000000000a28bb0
-- 2       [16]    CLOSURE         1 1     ; 0000000000a28e60
-- 3       [18]    MOVE            2 0
-- 4       [18]    LOADK           3 -1    ; 3
-- 5       [18]    LOADK           4 -2    ; 9
-- 6       [18]    LOADK           5 -3    ; 7
-- 7       [18]    LOADK           6 -4    ; 128
-- 8       [18]    LOADK           7 -5    ; 35
-- 9       [18]    CALL            2 6 2
-- 10      [19]    MOVE            3 1
-- 11      [19]    EQ              1 2 -4  ; - 128
-- 12      [19]    JMP             0 1     ; to 14
-- 13      [19]    LOADBOOL        4 0 1
-- 14      [19]    LOADBOOL        4 1 0
-- 15      [19]    CALL            3 2 1
-- 16      [20]    MOVE            3 0
-- 17      [20]    LOADK           4 -1    ; 3
-- 18      [20]    LOADK           5 -2    ; 9
-- 19      [20]    LOADK           6 -3    ; 7
-- 20      [20]    LOADK           7 -4    ; 128
-- 21      [20]    LOADK           8 -5    ; 35
-- 22      [20]    CALL            3 6 3
-- 23      [21]    MOVE            5 1
-- 24      [21]    EQ              0 3 -4  ; - 128
-- 25      [21]    JMP             0 2     ; to 28
-- 26      [21]    EQ              1 4 -6  ; - 4
-- 27      [21]    JMP             0 1     ; to 29
-- 28      [21]    LOADBOOL        6 0 1
-- 29      [21]    LOADBOOL        6 1 0
-- 30      [21]    CALL            5 2 1
-- 31      [22]    MOVE            5 0
-- 32      [22]    MOVE            6 0
-- 33      [22]    LOADK           7 -1    ; 3
-- 34      [22]    LOADK           8 -2    ; 9
-- 35      [22]    LOADK           9 -3    ; 7
-- 36      [22]    LOADK           10 -4   ; 128
-- 37      [22]    LOADK           11 -5   ; 35
-- 38      [22]    CALL            6 6 0
-- 39      [22]    CALL            5 0 3
-- 40      [23]    MOVE            7 1
-- 41      [23]    EQ              0 5 -4  ; - 128
-- 42      [23]    JMP             0 2     ; to 45
-- 43      [23]    EQ              1 6 -7  ; - 1
-- 44      [23]    JMP             0 1     ; to 46
-- 45      [23]    LOADBOOL        8 0 1
-- 46      [23]    LOADBOOL        8 1 0
-- 47      [23]    CALL            7 2 1
-- 48      [24]    NEWTABLE        7 0 0
-- 49      [24]    MOVE            8 0
-- 50      [24]    LOADK           9 -1    ; 3
-- 51      [24]    LOADK           10 -2   ; 9
-- 52      [24]    LOADK           11 -3   ; 7
-- 53      [24]    LOADK           12 -4   ; 128
-- 54      [24]    LOADK           13 -5   ; 35
-- 55      [24]    CALL            8 6 0
-- 56      [24]    SETLIST         7 0 1   ; 1
-- 57      [25]    MOVE            8 1
-- 58      [25]    GETTABLE        9 7 -7  ; 1
-- 59      [25]    EQ              0 9 -4  ; - 128
-- 60      [25]    JMP             0 3     ; to 64
-- 61      [25]    GETTABLE        9 7 -8  ; 2
-- 62      [25]    EQ              1 9 -6  ; - 4
-- 63      [25]    JMP             0 1     ; to 65
-- 64      [25]    LOADBOOL        9 0 1
-- 65      [25]    LOADBOOL        9 1 0
-- 66      [25]    CALL            8 2 1
-- 67      [25]    RETURN          0 1
--
-- function <test.lua:3,12> (21 instructions at 0000000000a28bb0)
-- 0+ params, 8 slots, 0 upvalues, 7 locals, 2 constants, 0 functions
-- 1       [4]     NEWTABLE        0 0 0
-- 2       [4]     VARARG          1 0
-- 3       [4]     SETLIST         0 0 1   ; 1
-- 4       [5]     LOADNIL         1 1
-- 5       [6]     LOADK           3 -1    ; 1
-- 6       [6]     LEN             4 0
-- 7       [6]     LOADK           5 -1    ; 1
-- 8       [6]     FORPREP         3 8     ; to 17
-- 9       [7]     EQ              1 1 -2  ; - nil
-- 10      [7]     JMP             0 3     ; to 14
-- 11      [7]     GETTABLE        7 0 6
-- 12      [7]     LT              0 1 7
-- 13      [7]     JMP             0 3     ; to 17
-- 14      [8]     GETTABLE        7 0 6
-- 15      [8]     MOVE            2 6
-- 16      [8]     MOVE            1 7
-- 17      [6]     FORLOOP         3 -9    ; to 9
-- 18      [11]    MOVE            3 1
-- 19      [11]    MOVE            4 2
-- 20      [11]    RETURN          3 3
-- 21      [12]    RETURN          0 1
--
-- function <test.lua:14,16> (5 instructions at 0000000000a28e60)
-- 1 param, 2 slots, 1 upvalue, 1 local, 1 constant, 0 functions
-- 1       [15]    TEST            0 1
-- 2       [15]    JMP             0 2     ; to 5
-- 3       [15]    GETTABUP        1 0 -1  ; _ENV "fail"
-- 4       [15]    CALL            1 1 1
-- 5       [16]    RETURN          0 1
