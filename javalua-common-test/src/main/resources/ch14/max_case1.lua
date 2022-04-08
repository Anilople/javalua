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
