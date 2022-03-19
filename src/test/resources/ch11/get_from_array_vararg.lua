local function second(...)
    local args = {...}
    return args[2]
end
local value = second(5555, 666666, 7777777, 333)
print(value) -- 666666
