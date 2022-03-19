-- __eq 总是返回 false
mt = {}
mt.__eq = function(t1, t2)
    return false;
end

t = {}
print(t == t) -- true
setmetatable(t, mt)
print(t == t) -- true 因为只有table不是同一个时，才会走元方法 __eq