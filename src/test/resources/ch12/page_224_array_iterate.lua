-- page 224
function ipairs(t)
    local i = 0
    return function()
        i = i + 1
        if t[i] == nil then
            return nil, nil
        else
            return i, t[i]
        end
    end
end
t = {10, 20, 30}
iter = ipairs(t)
while true do
    local i, v = iter()
    if i == nil then
        break
    end
    print(i, v)
end
