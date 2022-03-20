-- page 206
t = {}; mt = {}
setmetatable(t, mt)
print(getmetatable(t) == mt) --> true
debug.setmetatable(100, mt)
print(getmetatable(100) == mt) --> true
