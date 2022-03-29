-- __eq 总是返回 true
mt = {}
mt.__eq = function(t1, t2)
    return true;
end

t3 = {x=1}
t4 = {y=2}
print(t3 == t4) -- false
setmetatable(t3, mt)
print(t3 == t4) -- true
