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

local value = max(99)

