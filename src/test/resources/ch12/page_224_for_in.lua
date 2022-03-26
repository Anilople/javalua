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
-- 与 Java 的 for-each 类似
for i, v in ipairs(t) do
    print(i, v)
end
