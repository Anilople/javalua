-- page 225
function pairs(t)
    local k, v
    return function()
        k, v = next(t, k)
        return k, v
    end
end

t = {a=10, b=20, c=30}
for k, v in pairs(t) do
    print(k, v)
end
