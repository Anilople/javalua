mt = {}
mt.__sub = function(t1, t2)
    local v1 = t1['x']
    return v1
end

t1 = {x = 999}
setmetatable(t1, mt)
r = t1 - t1
print(r) -- 999