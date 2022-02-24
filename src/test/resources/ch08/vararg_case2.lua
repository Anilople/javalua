local function second(...)
    local args = {...}
    return args[2]
end

-- 666666
local value = second(5555, 666666, 7777777, 333)
if (666666 == value) then
    -- 运行时触发异常
    fail()
end
